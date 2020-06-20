package com.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;

import com.main.MainApp;
import com.view.LoginController;
import com.view.WaitingRoomController;
import com.vo.GameStatus;
import com.vo.Room;
import com.vo.RoomStatus;
import com.vo.Status;
import com.vo.User;

public class ClientListener implements Runnable {
	private String serverIP;
	private String clientIP;
	private int serverPORT;
	private String nickname;
	private static Socket socket;
	private static User user;
	private ArrayList<User> userList;
//	private ArrayList<Room> roomList;
	private boolean flag;
	private Thread listener;
	private static ObjectInputStream ois;
	private static ObjectOutputStream oos;
	private LoginController login;
	private static ClientListener instance;
	private static MainApp mainApp;
	private static Room room;

	public ClientListener() {
		instance = this;

	}

	public static ClientListener getInstance() {
		return instance;
	}

	public User getUser() {
		return user;
	}

	public ClientListener(String serverIP, int serverPORT, User user) {
		this.serverIP = serverIP;
		this.serverPORT = serverPORT;
		this.user = user;
	}

	public ClientListener(String serverIP, int serverPORT, String nickname, MainApp mainApp) {
		this.serverIP = serverIP;
		this.serverPORT = serverPORT;
		this.nickname = nickname;
		this.mainApp = mainApp;

	}

	public ClientListener(String serverIP, int serverPORT, String nickname, ObjectOutputStream oos) {
		super();
		this.serverIP = serverIP;
		this.serverPORT = serverPORT;
		this.nickname = nickname;
		this.oos = oos;
	}

	public void createConnect(String serverIP, int serverPORT, String nickname, MainApp mainApp) {
		try {
			socket = new Socket(serverIP, serverPORT); // create client's socket
			oos = new ObjectOutputStream(socket.getOutputStream()); // send data to server socket
			ois = new ObjectInputStream(socket.getInputStream()); // receive data from server socket

			clientIP = socket.getLocalAddress().toString();
			User client = new User(clientIP, nickname, Status.CONNECTED);
			oos.writeObject(client);
			System.out.println("is connected the server socket");

		} catch (IOException e) {
			e.printStackTrace();
		}

//		//thread 
		listener = new Thread(this);
		listener.start();
	}

	public void startHandler() {
		// start a network handler thread
		Thread t = new Thread(() -> networkHandler(socket));
		t.start();
	}

	public void networkHandler(Socket s) {
		
		try {
			User user;
			Status status;
			String userNickname;
			while (!flag) {
				
				synchronized (ois) {
					user = (User) ois.readObject();
					
					status = user.getStatus();
					userNickname = user.getNickname();
				}
				

				switch (status) {
				case CONNECTED:
					// 현재 접속 유저
					// List<User> nowUserList = user.getUserList();
					System.out.println("WaitingRoomController - login!! ");
					List<User> usertmpList = user.getUserList();

					System.out.println(usertmpList.size());

					userList = new ArrayList<User>();
//					System.out.println(usertmpList.size());
					RoomStatus nowRoomStatus = user.getRoomStatus();

					for (User u : usertmpList) {
						userList.add(u);
					}

					WaitingRoomController.getInstance().setUser(user);
					if (nowRoomStatus == null) {
						user.setRoomStatus(RoomStatus.WAITING);
					}
					// WaitingRoomController.getInstance().ChangeReadyColor(user.getUserList());
					WaitingRoomController.getInstance().changeLabel(user.getUserList());

					break;
				case ROBY:
					System.out.println();

					for (User u : user.getUserList()) {
						// System.out.println("lobby status " + u.getNickname() + " : " +
						// u.getRoomStatus());
						if (u.getNickname().equals(userNickname)) {
							u.setRoomStatus(user.getRoomStatus());
						}

					}
					// System.out.println("lobby "+userNickname+" : "+user.getRoomStatus());

					WaitingRoomController.getInstance().ChangeReadyColor(user.getUserList());
					break;
				case INCORRECT:
					System.out.println("loginController - try again ");
					break;

				case DISCONNECTED:
					userList.remove(user);
					System.out.println("update userList");
					endConnect();
					flag = true;
					break;

				case PLAYING: // game view update
					System.out.println("game playing GameController");
					GameStatus gameStatus = null;
					nickname = LoginController.getInstance().getPlayerName();
					for (User u : user.getUserList()) {
						// System.out.println("lobby status " + u.getNickname() + " : " +
						// u.getRoomStatus());
						if (u.getNickname().equals(nickname)) {
							gameStatus = u.getGameStatus();
							break;
						}
					}
					System.out.println("nickname : " + LoginController.getInstance().getPlayerName());
					if (gameStatus == null) {
						// MainApp.switchToGame();
						for (User u : user.getUserList()) {
							// System.out.println("lobby status " + u.getNickname() + " : " +
							// u.getRoomStatus());
							if (u.getNickname().equals(userNickname)) {
								u.setRoomStatus(user.getRoomStatus());
							}
						}
						WaitingRoomController.getInstance().ChangeReadyColor(user.getUserList());
						
						
					}else {
						switch (gameStatus) {
						case HIDE:
							System.out.println("game playing GameController > role - HIDE ");
							break;
						case SEEK:
							System.out.println("game playing GameController > role - seek ");
							break;
						}
					}//if~else end 
					
					sendData(user);
					break;
				default:
					System.out.println("error");
					break;

				}	
				
			}//while end 
			
			
		} catch (IOException | ClassNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	// update UI according to the message from server
	@Override
	public void run() {
	}

	public void endConnect() {
		try {
			socket.close();
			oos.close();
			ois.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void setUser(User user) {
		ClientListener.user = user;
	}

	public synchronized void sendData(User userData) {
		try {
			System.out.println("click! data!!" + userData.getRoomStatus());
			System.out.println();
			oos.writeObject(userData);
			oos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendMessage(User userData) {
		try {
			oos.writeObject(userData);
			oos.flush();
			System.out.println("sendMessage222" + userData.getStatus() + ":" + userData.getNickname() + ":"
					+ userData.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}