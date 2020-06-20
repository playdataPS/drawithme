package com.vo;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Room {
	private int no; // room number
	private String name;// room name
	private int userCnt; // user count
	private String enterButton; // ?
	private int[] userCntArr; // ?

	public Room(String roomName, int maxNum) {
		this.enterButton = "";
		this.no = 1;
		this.name = roomName;
		this.userCnt = maxNum;
	}

	public Room() {
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the userCnt
	 */
	public int getUserCnt() {
		return userCnt;
	}

	/**
	 * @param userCnt the userCnt to set
	 */
	public void setUserCnt(int userCnt) {
		this.userCnt = userCnt;
	}

	public int[] getUserCntArr(StringProperty userCnt) {
		userCntArr = new int[2];
		int currNum = Integer.parseInt(userCnt.get().substring(0, 1));
		int maxNum = Integer.parseInt(userCnt.get().substring(userCnt.get().length() - 1, userCnt.get().length()));
		userCntArr[0] = currNum;
		userCntArr[1] = maxNum;
		return userCntArr;
	}

}
