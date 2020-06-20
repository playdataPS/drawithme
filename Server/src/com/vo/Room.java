package com.vo;

import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

public class Room implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int no;//방번호
	private List<User> userList;//유저 리스트 
	private User roomOwner;//방장
	private String title;//방이름
	private RoomStatus status;//방상태 
	private int maxSize;
	private transient ObjectOutputStream oos;
	
	public Room() {
		// TODO Auto-generated constructor stub
	}
	
	public Room(int no, User roomOwner, String title, int maxSize, RoomStatus status, ObjectOutputStream oos) {
		this.no = no;
		this.roomOwner = roomOwner;
		this.title = title;
		this.maxSize = maxSize;
		this.status = status;
		this.oos = oos;
	}
	public Room(User roomOwner, String title, int maxSize, RoomStatus status) {
		this(-1,roomOwner, title, maxSize, status, null);
	}
	public Room(int no, User roomOwner, String title, int maxSize, RoomStatus status) {
		this(no, roomOwner, title, maxSize, status, null);
	}
	
	public ObjectOutputStream getOos() {
		return oos;
	}
	public void setOos(ObjectOutputStream oos) {
		this.oos = oos;
	}
	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public User getRoomOwner() {
		return roomOwner;
	}

	public void setRoomOwner(User roomOwner) {
		this.roomOwner = roomOwner;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public RoomStatus getStatus() {
		return status;
	}

	public void setStatus(RoomStatus status) {
		this.status = status;
	}

	public int getMaxSize() {
		return maxSize;
	}
	
	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}
	
	
}
