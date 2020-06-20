package com.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.vo.Room;
import com.vo.RoomStatus;
import com.vo.Status;
import com.vo.User;

public class RoomThread implements Runnable{
	
	private List<User> userList;//유저 리스트 
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private Socket socket;
	private User gameUser;
	private Room gameRoom;
	private boolean exit = false;
	
	private static List roomList;// 방리스트는 한개만 
	private static AtomicInteger atomicInteger;
	
	static {
        roomList = new ArrayList();
        atomicInteger = new AtomicInteger();
    }
	public RoomThread() {
		// TODO Auto-generated constructor stub
	}
	public RoomThread(Room gameRoom , Socket socket) {
		this.gameRoom = gameRoom;
		//this.socket = socket;
		
		try {
            ois = new ObjectInputStream(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            // TODO Auto-generated catch block
//            e.printStackTrace();
            System.out.println("> Failed to get in/out stream of client socket.");
        }
	}
	
	public RoomThread(User user) {
		gameUser = user;
	}
	
	public RoomThread(Room gameRoom, ObjectInputStream ois, ObjectOutputStream oos) {
		this.gameRoom = gameRoom;
		this.ois = ois;
		this.oos = oos;
	}
	
	
	@Override
	public void run() {
		System.out.println("game"+exit);
		while(!exit) {
			try {
				System.out.println("game"+gameRoom.getRoomOwner().getStatus());
				
				gameUser = (User) ois.readObject();
				
				RoomStatus roomStatus = gameUser.getGameRoom().getStatus();
				System.out.println(roomStatus);
				switch (roomStatus) {
				
				case WAITING:
					
					System.out.println(gameUser.getGameRoom().getTitle()+ " 방 입성!");
				break;

				default:
					break;
				}
				
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			//create room
		}
		
	}

	public void broadCast() {//유저리스트 
		for(User user: userList) {
			try {
				user.getOos().writeObject(this);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} //유저들의 상태
		}
	}
	
	public void enterRoom() {
		
	}


	


	
	

}
