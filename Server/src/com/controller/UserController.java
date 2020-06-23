package com.controller;

import com.biz.UserBiz;
import com.vo.User;

public class UserController {
	public static String LoginIP(String IP) {
		int checkIP = new UserBiz().ConfirmUserIP(IP);
		String status = "start";

		// DB에 IP가 있는 상황
		if (checkIP > 0) {
			status = "next1";
			
		} else { // DB에 IP가 없는 상황
			status = "input";
		}

		return status;
	}
	
	public static String LoginNickname(String IP, String nickname) {
		String user = new UserBiz().ConfirmUserNickname(IP);
		String status = "start";
		
		// DB에 있는 nickname과 맞는 상황
		if (nickname.equals(user)) {
			status = "next2";
			
		} else { // DB에 있는 nickname과 맞지 않는 상황
			status = "notPass";
		}
		
		return status;
	}
	
	public static int getScore(String nickname) {
		int score = new UserBiz().getScore(nickname);
		
		return score;
	}

	public static void Insert(String IP, String user) {
		User info = new User(IP, user);

		int res = new UserBiz().getInsertAll(info);

		if (res > 0) {
			System.out.println("INSERT DB");
		}
	}
	
	public static void scoreUpdate(String user, int score) {
		User info = new User(user, score);
		
		int res = new UserBiz().getUpdate(info);
		
		if (res > 0) {
			System.out.println("UPDATE DB");
		}
	}
}
