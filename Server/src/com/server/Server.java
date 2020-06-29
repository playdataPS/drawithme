package com.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.biz.UserBiz;
import com.vo.Data;
import com.vo.GameStatus;
import com.vo.Status;
import com.vo.User;
import com.vo.UserStatus;

public class Server {

	boolean exit = false;
	String userip = "";
	private static List<ClientHandler> clientList;
	private static Map<ClientHandler, String> clientMap;
	private static Map<String, UserStatus> userStatusMap;
	private static final int THREAD_CNT = 9; // 최대 스레드 개수
	private static ExecutorService threadPool;// 스레드풀
	private static ServerSocket serverSocket;
	private static int tmpc;
	private static GameSession gameSession;
	private static List<UserStatus> userStatusList;

	public static void init() {
//		threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		threadPool = Executors.newFixedThreadPool(THREAD_CNT);
		clientList = new Vector<Server.ClientHandler>();
		clientMap = new LinkedHashMap<Server.ClientHandler, String>();
		userStatusMap = new LinkedHashMap<String, UserStatus>();
		tmpc = 0;
		userStatusList = new ArrayList<UserStatus>();

	}

	public static void startServer(int port) {
//		if(clientList.size()==8) serverStarted = true; // 방에 8명 있으면 서버 시작 안함 

//		if (serverStarted) {
//			System.out.println("Server has already started!");
//			return;
//		}
		init();

//		clientList = new Vector<Server.ClientHandler>();
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
				while (true) {
					try {
						Socket socket = serverSocket.accept();// Client 수락
						String userip = socket.getInetAddress().toString().replace("/", "");
						// System.out.println("[ " + userip + "가 접속했습니다 : " +
						// Thread.currentThread().getName() + " ]");
						// 건동코드 시작
						// 건동코드 끝
						// 로그인 성공시, 클라이언트 스레드 생성
						ClientHandler clientHandler = new ClientHandler(userip, socket);
						clientList.add(clientHandler);

						// System.out.println("Client 개수 " + clientList.size());
						threadPool.submit(clientHandler);

					} catch (IOException e) {
						e.printStackTrace();
						if (!serverSocket.isClosed())
							stopServer();
						break;
					} // try~catch end
//					catch (ClassNotFoundException e) {
//						e.printStackTrace();
//					}
				} // while end
			}
		}; // runnable
//		
		threadPool.submit(runnable);

	}// Start() end

	public static void stopServer() { // 서버 종료 시 호출
		try {
			// 모든 소켓 닫기
			Iterator<ClientHandler> iterator = clientList.iterator();
			while (iterator.hasNext()) {
				ClientHandler client = iterator.next();
				client.socket.close();
				iterator.remove();
			}
			// 서버 소켓 닫기
			if (serverSocket != null && !serverSocket.isClosed()) {
				serverSocket.close();
			}
			// 스레드풀 종료
			if (threadPool != null && !threadPool.isShutdown()) {
				threadPool.shutdown();
			}
			System.out.println("[서버 멈춤]");
		} catch (Exception e) {
		}
	}// stopServer() end

//	public static void stopClient(){
//		try{
//
//			if(socket !=null&& !socket.isClosed()){
//				socket.close();
//			}
//
//			if (serverSocket != null && !serverSocket.isClosed()) {
//				serverSocket.close();
//			}
//
//		}catch(Exception e){
//
//		}
//	}//stopClient() end 

	public static class ClientHandler implements Runnable {
		String userName;
		Socket socket;
		String ip;
		ObjectOutputStream oos;
		ObjectInputStream ois;
		Data data;
		// List<UserStatus> userStatusList;
//		List<String> playerList;
//		Queue<String> drawers;

		boolean exit = false;
		int check = 0;

		public ClientHandler() {

		}

		public ClientHandler(String ip, Socket socket) {
			System.out.println("[ First to get in/out stream of client socket. ]");
			this.socket = socket;
			this.ip = ip;
			try {
				ois = new ObjectInputStream(socket.getInputStream());
				oos = new ObjectOutputStream(socket.getOutputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("[ Failed to get in/out stream of client socket. ]");
			} // try~catch end
		}

		@Override
		public void run() { // 클라이언트 요청이 서버에게 오는 곳입니다.

			while (!exit) {

				try {
					System.out.println("[Client's request: " + socket.getRemoteSocketAddress() + ": "
							+ Thread.currentThread().getName() + "]");
					data = (Data) ois.readObject();
					Status state = data.getStatus();
					String nowNickname = data.getNickname();
					String ip = socket.getInetAddress().toString().substring(1, socket.getInetAddress().toString().length());
//					String ip = "771.0919.00" + tmpc; // test
					tmpc++;
					System.out.println(state);
					switch (state) {
					case CONNECTED:
						User user = new User(ip, nowNickname);
						int checkIP = new UserBiz().ConfirmUserIP(ip);
//						int checkIP = 0; // test
						List<String> playerList = new Vector<String>();
						List<UserStatus> playerStatus = new ArrayList<UserStatus>();
						if (checkIP > 0) {
							System.out.println("db에 ip존재");
							int checkUSER = new UserBiz().getLogIn(user);
							if (checkUSER > 0) {
								System.out.println("로그인 성공");
								System.out.println("Lobby");

								if (clientList.size() != clientMap.size()) {
									clientMap.put(this, data.getNickname());
									if (data.getUserStatus() == null)
										userStatusMap.put(data.getNickname(), UserStatus.WAITING);
									// userStatusList.add(UserStatus.WAITING);// 초기 유저상태 설정
								}
								for (Map.Entry<ClientHandler, String> enty : clientMap.entrySet()) {
									System.out.println("client nickname : " + enty.getValue());
									playerList.add(enty.getValue());
								}

								for (Map.Entry<String, UserStatus> enty : userStatusMap.entrySet()) {
									System.out.println("client Status : " + enty.getValue());
									playerStatus.add(enty.getValue());
								}
								data.setUserStatusList(playerStatus);
								data.setGameUserList(playerList);
								broadCasting();
							} else {
								System.out.println("로그인 실패");
								data.setStatus(Status.INCORRECT);
								clientList.remove(ClientHandler.this);
								Thread.currentThread().interrupt();
								broadCasting();

								socket.close();
								exit = true;
							}
						} else {
							int res = new UserBiz().getInsertAll(user);
							// int res = new UserBiz().getSignUp(user);
							if (res > 0) {
								System.out.println("회원가입 성공");
								System.out.println("Lobby");

								if (clientList.size() != clientMap.size()) {
									clientMap.put(this, data.getNickname());
									if (data.getUserStatus() == null)
										userStatusMap.put(data.getNickname(), UserStatus.WAITING);
									// userStatusList.add(UserStatus.WAITING);// 초기 유저상태 설정
								}
								for (Map.Entry<ClientHandler, String> enty : clientMap.entrySet()) {
									System.out.println("client nickname : " + enty.getValue());
									playerList.add(enty.getValue());
								}
								for (Map.Entry<String, UserStatus> enty : userStatusMap.entrySet()) {
									System.out.println("client Status : " + enty.getValue());
									playerStatus.add(enty.getValue());
								}
								data.setUserStatusList(playerStatus);
								data.setGameUserList(playerList);

								broadCasting();
							}else{
								
								System.out.println("회원가입 실패!");
								
							}//if ~ else end 

						}//if~else end 
						break;
					case LOBBY:// 로비 버튼 이벤트 발생 시
						int gameCount = 0;
						UserStatus userStatus = data.getUserStatus();
						playerList = new Vector<String>();
						playerStatus = new ArrayList<UserStatus>();
						data.setStatus(Status.LOBBY);
						String clickedUser = data.getNickname();
						System.out.println("clickedUser : " + clickedUser);

						for (Map.Entry<ClientHandler, String> enty : clientMap.entrySet()) {
							playerList.add(enty.getValue());
						}

						switch (userStatus) {
						case WAITING:
							for (Map.Entry<String, UserStatus> entry : userStatusMap.entrySet()) {
								if (entry.getKey().equals(clickedUser)) {
									userStatusMap.put(clickedUser, UserStatus.WAITING);
								}
							}

							break;
						case READY:
							for (Map.Entry<String, UserStatus> enty : userStatusMap.entrySet()) {
								if (enty.getKey().equals(clickedUser)) {
									userStatusMap.put(clickedUser, UserStatus.READY);

								}
								if (enty.getValue().equals(UserStatus.READY)) {
									gameCount++;
								}
								System.out.println(enty.getValue());

								playerStatus.add(enty.getValue());

							} //

							break;
						}// userStatus switch end
						System.out.println("gameCount " + gameCount);
						if (clientList.size() > 1 && gameCount == clientList.size()) {
							// 게임 시작
							gameSession = new GameSession(playerList);
							gameSession.createGameQue();
							data.setStatus(Status.PLAYING); // 모두 플레잉

						}
						userStatusList.clear();
						userStatusList.addAll(userStatusMap.values());
						data.setUserStatusList(userStatusList);
						data.setGameUserList(playerList);
						broadCasting();

						break; // LOBBY

					case PLAYING:

						GameStatus gameStatus = data.getGameStatus();

						if (!gameSession.getTurn()) {
							System.out.println("[ 게임 끝 ]");
							// 랭킹 구현
							data = new Data();
							data.setStatus(Status.RANKING);
							broadCasting();

						} else {

							if (!gameSession.getDrawerQue().isEmpty()) { // 한 turn

								if (gameStatus == null)
									gameStatus = GameStatus.START;
								switch (gameStatus) {
								case TURN:
									System.out.println("Turn");
									gameSession.getDrawerQue().poll();
									if (gameSession.getDrawerQue().isEmpty()) { // 턴 하나가 끝남
										turnOver();
									} else {
										data = gameSession.getGameData();
									}

									broadCasting();
									break;

								case ANSWER:
									String nowWord = gameSession.getNowTurn().getWord();
									String nowChallenger = gameSession.getNowTurn().getChallenger();
									if (data.getAnswer().trim().equals(nowWord)) { // 정답 맞췄음
										gameSession.getScoreMap().put(nowChallenger, 10);
										data.setMessage(nowChallenger + "님이 정답을 맞췄습니다 : " + 10 + " 점 ");
										gameSession.getDrawerQue().clear();// 지금 턴의 그림 유저 전부 비움
										turnOver();
									} else {
										data.setStatus(Status.NOANSWER);
										data.setMessage(nowChallenger + "님 답은 " + data.getAnswer() + " 이(가) 아닙니다!");
									}
									broadCasting();
									break;

								case START:
									data = gameSession.getGameData();
									sendToClient();
									break;

								default:
									break;
								}// switch

							} // if end

						} // if~else end

						break;
					case PRESSED:

						broadCasting();
						break;

					case DRAGGED:

						broadCasting();
						break;

					case LOBBY_CHAT:
						broadCasting();
						break;

					case RANKING:
						// value 내림차순으로 정렬하고, value가 같으면 key 오름차순으로 정렬
						// scoreMap.put("a", 100);
						// scoreMap.put("b", 120);
						List<Map.Entry<String, Integer>> list = new LinkedList<>(gameSession.getScoreMap().entrySet());
						Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
							@Override
							public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
								int comparision = (o1.getValue() - o2.getValue()) * -1;
								return comparision == 0 ? o1.getKey().compareTo(o2.getKey()) : comparision;
							}
						});
						// 순서유지를 위해 LinkedHashMap을 사용
						// Map<String, Integer> sortedMap = new LinkedHashMap<>();
						String score = "";
						int i = 1;
						for (Iterator<Map.Entry<String, Integer>> iter = list.iterator(); iter.hasNext();) {
							
							Map.Entry<String, Integer> entry = iter.next();
							// sortedMap.put(entry.getKey(), entry.getValue());
							System.out.println(entry.getKey());
							score += entry.getKey() + "=" + entry.getValue();// kim=90,park=85
							
							if (gameSession.getScoreMap().size() != i) {
								score += ",";
							}
							i++;
						}
						System.out.println(score);
						data.setGameUserList(gameSession.getNowPlayerList());
						data.setStatus(Status.RANKING);
						data.setScore(score);
						sendToClient();// 클라이언트들에게 서버의 data 전송!

						break;

					case DISCONNECTION:
						System.out.println("DISCONNECTED");
						clientList.remove(ClientHandler.this);
						clientMap.remove(ClientHandler.this);
						//broadCasting();

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
				} // try~catch end

			} // while end

		}// run() end

		public void turnOver() {

			gameSession.getGameQue().poll();// 이전 턴을 제거
			boolean turnCheck = gameSession.getTurn();
			System.out.println("[ 턴 하나 끝 ]" + turnCheck);
			if (!turnCheck) {
				data.setStatus(Status.RANKING);
			} else {
				data = gameSession.getGameData();
			}

		}

		public synchronized void sendToClient() {
			for (Map.Entry<ClientHandler, String> entry : clientMap.entrySet()) {
				if (entry.getKey().equals(this)) {
					try {
						System.out.println("[ Server send data to " + entry.getValue() + " ]");
						entry.getKey().oos.writeObject(data);
						entry.getKey().oos.flush();
						break;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			} // for end

		}// sendToClient end

		public synchronized void broadCasting(Data data) {// 전체 데이터를 보내줌
			// 전체 채팅 회원에게 내용 출력
			for (ClientHandler client : clientList) {
				try {
					System.out.println("[ Server send data to " + clientMap.get(client) + " ]");
					client.oos.writeObject(data);
					client.oos.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}

			} // for end
		}// broadCasting() end

		public synchronized void broadCasting() {// 전체 데이터를 보내줌
			// 전체 채팅 회원에게 내용 출력
			for (ClientHandler client : clientList) {
				try {
					System.out.println("[ Server send data to " + clientMap.get(client) + " ]");
					client.oos.writeObject(data);
					client.oos.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}

			} // for end
		}// broadCasting() end

	}// ClientHandler end
}// Server end
