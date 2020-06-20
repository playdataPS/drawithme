package com.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.vo.Game;
import com.vo.GameStatus;
import com.vo.RoomStatus;
import com.vo.Status;
import com.vo.User;

public class Server {

	private int port;
	private boolean serverStarted = false;
	private static List<User> userList; // 소켓 킨 유저들 목록
	private Thread serverThread;
	private boolean gameStart;
	User userdata;
	ObjectInputStream ois;
	ObjectOutputStream oos;
	boolean exit = false;
	String userip = "";
	private Map<Integer, Thread> threadList; 
	private static List<ClientHandler> clientList;
	Queue<Game> que;
//	private static final int THREAD_CNT = 8; //최대 스레드 개수 
	private static ExecutorService threadPool; //스레드풀 
	private ServerSocket serverSocket;
	

	public Server(int port) {
		this.port = port;
		userList = new ArrayList<User>();
		clientList = new Vector<Server.ClientHandler>();
		
		gameStart = false;
		userdata = null;
		ois = null;
		oos = null;
		que = null;

		
	}

	public void Start() {
//		if(clientList.size()==8) serverStarted = true; // 방에 8명 있으면 서버 시작 안함 
		
		if (serverStarted) {
			System.out.println("Server has already started!");
			return;
		}
		threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		System.out.println("Thread Size : "+Thread.getAllStackTraces().size());
				
		try {
			serverSocket = new ServerSocket(this.port);
			
		
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		
		Runnable runnable = new Runnable() {
			
			@Override
			public void run() {
				System.out.println("Server Start!!");
				while(true) {
					try {
						Socket socket = serverSocket.accept();// Client 수락
						String userip = socket.getInetAddress().toString().replace("/", "");
						 System.out.println(socket.getInetAddress() + "가 접속했습니다 : "+Thread.currentThread().getName());
						ClientHandler clientHandler = new ClientHandler(userip, socket);
						clientList.add(clientHandler);
						System.out.println("Client 개수 "+clientList.size());
						threadPool.submit(clientHandler);
						
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
		threadPool.execute(runnable);
	
	}//Start() end 

	public void stopServer() { // 서버 종료 시 호출
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
	
	
	public class ClientHandler implements Runnable {
		String userName;
        Socket socket;
        String ip;
        ObjectOutputStream oos;
        ObjectInputStream ois;
        boolean exit = false;
		
        public ClientHandler(String ip, Socket socket) {
			this.socket = socket;
			this.ip = ip;
			try {
				ois = new ObjectInputStream(socket.getInputStream());
                oos = new ObjectOutputStream(socket.getOutputStream());
               
            } catch (IOException e) {
                // TODO Auto-generated catch block
//                e.printStackTrace();
                System.out.println("> Failed to get in/out stream of client socket.");
            }//try~catch end 
		}
		
		@Override
		public void run() {
			
			while (!exit) {
				
				try {
					
					userdata = (User) ois.readObject();
					Status state = userdata.getStatus();
					String nowNickname = userdata.getNickname();
					System.out.println(state);
					switch (state) {
					case CONNECTED:
						// login

						// userdata = new UserBiz().getLoginUser(userdata);
						System.out.println("CONNECTED");
						List<User> tmp = new ArrayList<User>();

						userList.add(userdata);
						for (User data : userList) {
							tmp.add(data);
//	                 System.out.println("userList"+data.getNickname());

						} // for end

						userdata.setUserList(tmp);
						userdata.setRoomStatus(RoomStatus.WAITING);
						userdata.setStatus(Status.CONNECTED); // 방으로 넘어감
						userdata.setOos(oos);
						System.out.println("tmp size " + tmp.size());
						broadCasting();
						break;

					case PLAYING:
						System.out.println("게임 시작함");
						User gameUser = new User(ip, nowNickname, Status.PLAYING);
						try {
							Game gameData = que.poll();
							String seekerName = gameData.getSeeker().getNickname();
							System.out.println("seekerName " + seekerName);
							tmp = new ArrayList<User>();
							for (User u : userList) {
								u.setRoomStatus(RoomStatus.WAITING);
								u.setStatus(Status.PLAYING);
								System.out.println(u.getNickname());
								if (u.getNickname().equals(seekerName)) {
									u.setGameStatus(GameStatus.SEEK);
								} else {
									u.setGameStatus(GameStatus.HIDE);
								}
								tmp.add(u);
							} // for end

							
							
							if (userdata.getNickname().equals(seekerName)) {
								gameUser.setGameStatus(GameStatus.SEEK);
							} else {
								gameUser.setGameStatus(GameStatus.HIDE);
							}
							gameUser.setSeeker(gameData.getSeeker());
							gameUser.setWord(gameData.getWord());
							//userdata.setDrowingUserQue(gameData.getDrowingUserQue());
							
							gameUser.setUserList(tmp);

//							for (User u : userdata.getUserList()) {
//								System.out.println(u.getNickname() + " : " + u.getGameStatus());
//							}
							broadCasting(gameUser);
						} catch (Exception e) {
							userdata.setStatus(Status.ROBY);
							broadCasting();
						}
						
						
						break;

					case ROBY:
						int cnt = 0;
						RoomStatus roomstatus = userdata.getRoomStatus();
						if (roomstatus == null)
							roomstatus = RoomStatus.WAITING;
						System.out.println("nickname: " + nowNickname + "roomstatus: " + roomstatus);
						for (User u : userList) {
							u.setStatus(Status.ROBY);
							System.out.println("nickname" + u.getNickname() + " roomStatus : " + u.getRoomStatus());
						}

						switch (roomstatus) {
						case READY:
							for (User u : userList) {

								if (u.getNickname().equals(nowNickname)) {
									u.setRoomStatus(RoomStatus.READY);
								}
								if (u.getRoomStatus().equals(RoomStatus.READY)) {
									cnt++;
								}

							} // for end

							break;

						case WAITING:

							for (User u : userList) {
								if (u.getNickname().equals(userdata.getNickname())) {
									u.setRoomStatus(RoomStatus.WAITING);
									break;
								}

							} // for end

							break;

						}
						System.out.println("game count : " + cnt);
						tmp = new ArrayList<User>();
						if (cnt > 1 && cnt == userList.size()) {
							// 게임 시작
							System.out.println("game start all : " + userList.size());
							userdata.setStatus(Status.PLAYING);
							for (User u : userList) {
								System.out.println("game user : " + u.getNickname());

								u.setStatus(Status.PLAYING);
								tmp.add(u);
							} // for end
							que = new LinkedList<Game>();
							que = createGameQue();
							System.out.println("que size : " + que.size());

							userList.clear();
							userList.addAll(tmp);
						}

						userdata.setUserList(userList);
						broadCasting();
						break;

					case DISCONNECTED:
						System.out.println("DISCONNECTED");
						String name = userdata.getNickname();

						for (User u : userList) {
							if (u.getNickname().equals(name)) {
								userList.remove(u);
								break;
							}
						} // for end
						broadCasting();

						try {
							ois.close();
							oos.close();
							socket.close();
							exit = true;
						} catch (Exception e) {
							e.printStackTrace();
						} // try~catch end
						break;

					case CHAT:
						System.out.println("CHAT 상태");
						userdata.setStatus(Status.CHAT);
						//boardChating();

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
		
		public void startGame() {
			
		}
		
		public Queue<Game> createGameQue() {
			List<User> tmp = new ArrayList<User>();
			List<String> dict = new ArrayList<String>();
			dict.add("연장전");
			dict.add("죽부인");
			dict.add("마법사");
			dict.add("부동산");
			dict.add("두바이");
			dict.add("달팽이");
			dict.add("오이도");
			dict.add("감자탕");
			dict.add("대기층");

			Queue<Game> gameQue = new LinkedList<Game>();
			tmp.addAll(userList);
			Collections.shuffle(dict);
			Collections.shuffle(tmp);

			for (int i = 0; i < userList.size(); i++) {
				Game gameData = new Game();
				gameData.setSeeker(tmp.get(i));
				gameData.setWord(dict.get(i));
				List<User> tmp2 = new ArrayList<User>();
				tmp2.addAll(tmp);
				tmp2.remove(i);
				gameData.mixDrowingUser(tmp2);

				gameQue.add(gameData);
				System.out.println("Seeker + " + gameData.getSeeker().getNickname());
			}

			return gameQue;
		}// createGameQue() end

		public void broadCasting() {// 전체 데이터를 보내줌
			System.out.println("broad + " + userList.size());
			// 전체 채팅 회원에게 내용 출력
			for (User data : userList) {
				try {
					System.out.println("send to clients");
					System.out.println(data.getNickname() + " : " + data.getStatus());
					data.getOos().writeObject(userdata);
				} catch (IOException e) {
					System.out.println("broadCasting() " + data.getOos() + " userdata " + userdata.getUserList());
					e.printStackTrace();
				}

			} // for end
		}//broadCasting() end 
		
		public void broadCasting(User userdata) {// 전체 데이터를 보내줌
			System.out.println("broad + " + userList.size());
			
			
			
			// 전체 채팅 회원에게 내용 출력
			for (User data : userList) {
				try {
					System.out.println("send to clients");
					System.out.println(data.getNickname() + " : " + data.getStatus());
					data.getOos().writeObject(userdata);
				} catch (IOException e) {
					System.out.println("broadCasting() " + data.getOos() + " userdata " + userdata.getUserList());
					e.printStackTrace();
				}

			} // for end
		}
		
	}
}
