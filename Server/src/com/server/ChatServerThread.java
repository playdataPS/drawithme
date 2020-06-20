package com.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.vo.Message;
import com.vo.MessageType;
import com.vo.User;

public class ChatServerThread implements Runnable {
	private User chatuser;
	// 방 번호 등의 정보는 message 에 저장하면 될거가튼데
	private String message;
	private Socket socket;
	private List<User> userList;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private boolean exit = false;

	public ChatServerThread() {
	}

	public ChatServerThread(User user, String message, Socket socket, ArrayList<User> userList, ObjectOutputStream oos,
			ObjectInputStream ois) {
		super();
		this.chatuser = user;
		this.message = message;
		this.socket = socket;
		this.userList = userList;
		this.oos = oos;
		this.ois = ois;
	}

	// status - WAITING || READY
	@Override
	public void run() {
		while (!exit) {
			System.out.println("chat start");
			try {
				chatuser = (User) ois.readObject();
				System.out.println(chatuser.getType());
//				MessageType messType = chatuser.getType();
//				switch (messType) {
//				case CONNECTED:
//					broadCasting(); // 입장알림
//					System.out.println("chat connect");
//					break;
//				case DISCONNECTED:
//					System.out.println("chat exit");
//					break;
//				case NOTICE:
//					System.out.println("chat notice");
//					break;
//				case USER:
//					System.out.println("chat user");
//					break;
//				}
				message = chatuser.getMessage();
				System.out.println(message);
				Chatting();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void Chatting() {
		for(User user : userList) {
			try {
				user.getOos().writeObject(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}