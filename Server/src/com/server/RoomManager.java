package com.server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import com.vo.Room;
import com.vo.User;
/*
 * 방 생성, 삭제, 방리스트 
 * 방장 정보, 방장 교체 
 * 현재 방 크기
 * 방의 상태(waiting / playing)  
 * 
 * */
public class RoomManager {
	private static List roomList;// 방리스트는 한개만 
	private static AtomicInteger atomicInteger;
	
	static {
        roomList = new ArrayList();
        atomicInteger = new AtomicInteger();
    }
	
	public RoomManager() {
		
	}
	
	public static void createRoom(User roomOwner, ObjectInputStream ois, ObjectOutputStream oos) {
		int roomNo = atomicInteger.incrementAndGet();
		Room gameRoom = new Room(roomNo, roomOwner, roomOwner.getGameRoom().getTitle(), 
				roomOwner.getGameRoom().getMaxSize(), roomOwner.getGameRoom().getStatus());
		Thread t = new Thread(new RoomThread(gameRoom, ois, oos));
		t.start();
	}
	
	
}
