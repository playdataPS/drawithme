package com.vo;

import java.io.Serializable;
import java.util.ArrayList;

public class PlayingRoom implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 방장
	private User2 user;
	private String roomName;
	private ArrayList<User2> userList = new ArrayList<User2>();
	private ArrayList<String> words;
	
	// 방안의 플레이어
	private User2 turnUser;
	private int pointer = 0;
	private ArrayList<String> turnUserList = new ArrayList<String>();
	private int currUserNum;
	
	public PlayingRoom(User2 user, String roomName) {
		super();
		this.user = user;
		this.roomName = roomName;
	}
	
	// 게임 시작 시 실행
	public void turnUserSet() {
		for (User2 user1 : userList) {
			turnUserList.add(user1.getNickname());
		}
		currUserNum = userList.size();
	}

	public User2 getTurnUser() {
		return turnUser;
	}

	public void setTurnUser(User2 turnUser) {
		this.turnUser = turnUser;
	}

	public void setTurnUser() {
		// 나머지 값으로 다음 턴 유저의 인덱스를 정함 
		int index = pointer % currUserNum;
		pointer++;
		String currUserNickName = turnUserList.get(index);
		turnUser = userList.get(index);
		System.out.println(index + " : " + currUserNickName);
	}

	public int getCurrUserNum() {
		return currUserNum;
	}

	public void setCurrUserNum(int currUserNum) {
		this.currUserNum = currUserNum;
	}

	public User2 getUser() {
		return user;
	}

	public void setUser(User2 user) {
		this.user = user;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public ArrayList<User2> getUserList() {
		return userList;
	}

	public void setUserList(ArrayList<User2> userList) {
		this.userList = userList;
	}

	public ArrayList<String> getWords() {
		return words;
	}

	public void setWords(ArrayList<String> words) {
		this.words = words;
	}
	
	// 제시어
	public String getWord() {
		int index = pointer % (words.size() - 1);
		String word = words.get(index-1);
		return word;
	}
	
}
