package com.biz;

import static common.JDBCTemplate.Close;
import static common.JDBCTemplate.getConnection;

import java.sql.Connection;

import com.dao.UserDao;
import com.vo.User;

public class UserBiz {
	
	public int ConfirmUserIP(String IP) {
		Connection conn = getConnection();
		
		int confirm = new UserDao(conn).getIP(IP);
		
		Close(conn);
		
		return confirm;
	}
	
	public String ConfirmUserNickname(String IP) {
		Connection conn = getConnection();
		
		String nickname = new UserDao(conn).getNickname(IP);
		
		Close(conn);
		
		return nickname;
	}
	
	public int getScore(String nickname) {
		Connection conn = getConnection();
		
		int score = new UserDao(conn).getScore(nickname);
		
		Close(conn);
		
		return score;
	}
	
	public int getInsertAll(User user) {
		Connection conn = getConnection();
		
		int res = new UserDao(conn).getInsertAll(user);
		
		Close(conn);
		
		return res;
	}
	
	public int getUpdate(User user) {
		Connection conn = getConnection();
		
		int res = new UserDao(conn).getUpdate(user);
		
		Close(conn);
		
		return res;
	}
}
