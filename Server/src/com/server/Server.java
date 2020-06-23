package com.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.controller.UserController;
import com.vo.Data;
import com.vo.Game;
import com.vo.Status;
import com.vo.User;

public class Server {
	private boolean serverStarted = false;
	private static List<User> userList; // 소켓 킨 유저들 목록

	boolean exit = false;
	String userip = "";
	private static String usernickname = "";
	private static List<ClientHandler> clientList;
	private static List<Data> buffer;
	private static Queue<Game> que;
//	private static final int THREAD_CNT = 8; //최대 스레드 개수 
	private static ExecutorService threadPool; //스레드풀 
	private static ServerSocket serverSocket;
	
	private static Map<String, Integer> scoreMap;
	

	public Server() {
		
	}
	
	public static void init() {
		threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		userList = new ArrayList<User>();
		clientList = new Vector<Server.ClientHandler>();	
		buffer = new Vector<Data>();
		que = null;
		scoreMap = new HashMap<String, Integer>();
	}

	public static void Start(int port) {
//		if(clientList.size()==8) serverStarted = true; // 방에 8명 있으면 서버 시작 안함 
		
//		if (serverStarted) {
//			System.out.println("Server has already started!");
//			return;
//		}
		init();
		System.out.println("Thread Size : "+Thread.getAllStackTraces().size());
		clientList = new Vector<Server.ClientHandler>();
		try {
			serverSocket = new ServerSocket(port);
			
		
		} catch (IOException e1) {
			stopServer();
			return;
		}
		
		Runnable runnable = new Runnable() {
			
			@Override
			public void run() {
				System.out.println("Server Start!!");
				while(true) {
					try {
						Socket socket = serverSocket.accept();// Client 수락
						String userip = socket.getInetAddress().toString().replace("/", "");
						System.out.println("[ "+socket.getInetAddress() + "가 접속했습니다 : "+Thread.currentThread().getName()+" ]");
						
						// Login
						// 처음부터 thread를 만들면 안되는데.. 
						// nickname을 먼저 받아와야해...
						String login = UserController.LoginIP(userip);
						if (login.equals("next1")) {
							ClientHandler clientHandler = new ClientHandler(userip, socket);
							login = UserController.LoginNickname(userip, usernickname);
							if (login.equals("next2")) {
								// 여기서 thread 생성.. 근데 위에 nickname을 먼저 알아와야하는데...
								// 어떻게 해야하지..?
								clientList.add(clientHandler);
								threadPool.submit(clientHandler);
								
							} else {
								// thread 생성하는 걸로 넘어가면 안돼..
								continue;
								
							}
						} else {
							ClientHandler clientHandler = new ClientHandler(userip, socket);
							UserController.Insert(userip, usernickname);
							clientList.add(clientHandler);
							threadPool.submit(clientHandler);
						}
						
//						ClientHandler clientHandler = new ClientHandler(userip, socket);
//						clientList.add(clientHandler);
//						
//						System.out.println("Client 개수 "+clientList.size());
//						threadPool.submit(clientHandler);
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						if(!serverSocket.isClosed())	stopServer();
						break;
					}//try~catch end 
				}//while end
			}
		}; //runnable
//		
		threadPool.submit(runnable);
	
	}//Start() end 

	public static void stopServer() { // 서버 종료 시 호출
		try {
			// 모든 소켓 닫기
			Iterator<ClientHandler> iterator = clientList.iterator();
			while(iterator.hasNext()) {
				ClientHandler client = iterator.next();
				client.socket.close();
				iterator.remove();
			}
			// 서버 소켓 닫기
			if(serverSocket!=null && !serverSocket.isClosed()) { 
				serverSocket.close(); 
			}
			// 스레드풀 종료
			if(threadPool!=null && !threadPool.isShutdown()) { 
				threadPool.shutdown(); 
			}
			System.out.println("[서버 멈춤]");
		} catch (Exception e) { }
	}//stopServer() end
	
	
	public static class ClientHandler implements Runnable {
		String userName;
        Socket socket;
        String ip;
        ObjectOutputStream oos;
        ObjectInputStream ois;
        Data data;
        boolean exit = false;
        public ClientHandler() {
        	
        }
        public ClientHandler(String ip, Socket socket) {
			this.socket = socket;
			this.ip = ip;
			try {
				ois = new ObjectInputStream(socket.getInputStream());
                oos = new ObjectOutputStream(socket.getOutputStream());
               
            } catch (IOException e) {
                // TODO Auto-generated catch block
//                e.printStackTrace();
                System.out.println("[ Failed to get in/out stream of client socket. ]");
            }//try~catch end 
		}
		
		@Override
		public void run() { // 클라이언트 요청이 서버에게 오는 곳입니다. 
			
			while (!exit) {
				
				try {
					System.out.println("[Client's request: " + socket.getRemoteSocketAddress() + ": " + Thread.currentThread().getName() + "]");
					data = (Data) ois.readObject();
					Status state = data.getStatus();
					usernickname = data.getNickname();
					String nowNickname = data.getNickname();
					System.out.println(state);
					switch (state) {
					case CONNECTED:
						System.out.println("Lobby");
						broadCasting();
						break;

					case PLAYING:
						System.out.println("게임 시작함");
						
						
						break;

					case LOBBY_CHAT:
						break;
					case RANKING:
						// 지혜언니코드
						
						
						int turnscore = scoreMap.get(nowNickname);
						int dbscore = UserController.getScore(nowNickname);
						int newscore = dbscore + turnscore;
						UserController.scoreUpdate(nowNickname, newscore);
						
						break;
					case DISCONNECTION:
						System.out.println("DISCONNECTED");
						clientList.remove(ClientHandler.this);
						broadCasting();

						try {
							stopServer();
							exit = true;
						} catch (Exception e) {
							e.printStackTrace();
						} // try~catch end
						break;

				

					default:
						System.out.println("error");
						break;

					}

				} catch (Exception e) {
					try {				
						exit = true;
						e.printStackTrace();
						clientList.remove(ClientHandler.this);
						socket.close();
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
				} //try~catch end 
				
			}//while end 
			
		}//run() end
		
		

		public void broadCasting() {// 전체 데이터를 보내줌
			System.out.println("broad + " + userList.size());
			// 전체 채팅 회원에게 내용 출력
			for (ClientHandler client : clientList) {
				try {
					client.oos.writeObject(data);
				} catch (IOException e) {
					e.printStackTrace();
				}

			} // for end
		}//broadCasting() end 
		
		
		
	}//ClientHandler end
}//Server end 
