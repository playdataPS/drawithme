package com.vo;

import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Message implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6140182571179822249L;
	private int state;
	private String nickname;
	private String msg;
	private MessageType type;
	private ArrayList<User2> userList;
	private transient ObjectOutputStream oos;
	
	
	public Message(String nickname, String msg, MessageType type, ObjectOutputStream oos) {
		this.nickname = nickname;
		this.msg = msg;
		this.type = type;
		this.oos = oos;
	}
	
	public Message(String nickname, String msg, MessageType type) {
		this(nickname, msg, type, null);
	}
	
	
	public Message() {
		// TODO Auto-generated constructor stub
	}
	
	public void setOos(ObjectOutputStream oos) {
		this.oos = oos;
	}
	public ObjectOutputStream getOos() {
		return oos;
	}
	
	public void setUserList(ArrayList<User2> userList) {
		this.userList = userList;
	}
	
	public ArrayList<User2> getUserList() {
		return userList;
	}



	public int getState() {
		return state;
	}



	public void setState(int state) {
		this.state = state;
	}



	public String getNickname() {
		return nickname;
	}



	public void setNickname(String nickname) {
		this.nickname = nickname;
	}



	public String getMsg() {
		return msg;
	}



	public void setMsg(String msg) {
		this.msg = msg;
	}



	public MessageType getType() {
		return type;
	}



	public void setType(MessageType type) {
		this.type = type;
	}
	
	
	
	
}
