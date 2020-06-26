package com.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutionException;

import javax.xml.ws.soap.AddressingFeature.Responses;

import com.vo.Data;
import com.vo.Game;
import com.vo.GameStatus;
import com.vo.Room;
import com.vo.Status;
import com.vo.User;
import com.vo.UserStatus;

import javafx.application.Platform;
import javafx.scene.paint.Color;

public class ClientListener implements Runnable {
	private String serverIP;
	private String clientIP;
	private int serverPORT;
	private String nickname;
	private static Socket socket;
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

	public void createConnect(String serverIP, int serverPORT, String nickname, MainApp mainApp) {
		try {
			socket = new Socket(serverIP, serverPORT); // create client's socket
			oos = new ObjectOutputStream(socket.getOutputStream()); // send data to server socket
			ois = new ObjectInputStream(socket.getInputStream()); // receive data from server socket

			clientIP = socket.getLocalAddress().toString();

			User user = new User(clientIP, nickname);
			Data client = new Data(user);
			client.setStatus(Status.CONNECTED);

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
			Data response;
			Status status;
			boolean visited = false;
			while (!flag) {

				response = (Data) ois.readObject();

				status = response.getStatus();

				switch (status) {
				case CONNECTED:
					// 현재 접속 유저
					// List<User> nowUserList = user.getUserList();
					System.out.println("WaitingRoomController - login!! ");
					MainApp.switchToLobby();
					WaitingRoomController.getInstance().setFirstMessage(response.getNickname());
					
					WaitingRoomController.getInstance().changeLabel(response);
//					WaitingRoomController.getInstance().ChangeReadyColor(response);
					break;
				case INCORRECT:
					System.out.println("loginController - try again ");
					LoginController.getInstance().changeNotice();
					
					break;

				case LOBBY:
					
			
					 for(int i=0; i<response.getGameUserList().size(); i++) {
					 	if(response.getGameUserList().get(i).equals(response.getNickname())) {
					 		response.getUserStatusList().set(i, response.getUserStatus());
					 		break;
					 	}	
					 }
					
					
					WaitingRoomController.getInstance().changeReadyColor(response);

					break;
				case DISCONNECTION:

					endConnect();
					flag = true;
					break;

				case PLAYING: // game view update
					System.out.println("game playing GameController");
					System.out.println("Turn : " + DrawController.getInstance().getTurnOver());
					if (response.getChallenger() == null && response.getDrawer() == null) {
						visited = true;
						sendData(response);
					} else {
						String loginUser = LoginController.getInstance().getPlayerName();
						System.out.println("[ login nickname : " + loginUser + " , challenger : "
								+ response.getChallenger() + ", drawer : " + response.getDrawer() + " ] ");

						Game game = new Game(response.getWord(), response.getGameUserList());
						game.setChallenger(response.getChallenger());
						game.setDrawer(response.getDrawer());

						// UI update - 공통
						MainApp.switchToGame(game);

					}

					break;

				case NOANSWER:
					System.out.println(response.getMessage());
					SideAnswerController.getInstance().clearInputAnswer();

					break;
				case PRESSED:
					System.out.println("PRESSED");
					System.out.println(response.getColor());

					DrawController.getInstance().setPressedData(response);

					break;

				case DRAGGED:
					System.out.println("DRAGGED");
					System.out.println(response.getColor());

					DrawController.getInstance().setDraggedData(response);
					DrawController.getInstance().freeDrawing();

					break;

				case DRAWING:
					System.out.println("DRAWING : " + response.getLineW());
					DrawController.getInstance().setGc();
					DrawController.getInstance().freeDrawing(response);
					break;

				case GAME_CHAT:
					break;

				case LOBBY_CHAT:
					WaitingRoomController.getInstance().changeMessage(response.getNickname(), response.getMessage());
					break;
				case RANKING:
					System.out.println("RANKING");
					DrawController.getInstance().stopTimer();
					if (response.getScore() == null || response.getScore().isEmpty()) {

						System.out.println("First");
						MainApp.switchToScore();
//						DrawController.getInstance().timer(false);
						Data requestData = new Data();
						requestData.setStatus(Status.RANKING);
						 //sendData(requestData);
					} else {
						System.out.println(response.getScore());
						ScoreController.getInstance().getRanking(response.getScore());

					}

					break;
				default:
					System.out.println("error");
					break;

				}

			} // while end

		} catch (IOException | ClassNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
			endConnect();
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

	public synchronized void sendData(Data requestData) {
		try {

			System.out.println();
			oos.writeObject(requestData);
			oos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}