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
	
	public int getInsertAll(User user) {
		Connection conn = getConnection();
		
		int res = new UserDao(conn).getInsertAll(user);
		
		Close(conn);
		
		return res;
	}
	
//	public static void CheckUser(String ip, String nickname) {
//		Connection conn = getConnection();
//		System.out.println("DB CONNECTION");
//		
//		// 입력한 IP가 DB에 존재하는지 확인
//		int count = new UserDao(conn).getIP(ip);
//		
//		if (count > 0) {	// 존재한다면
//			String nick = new UserDao(conn).getNickname(ip);
//			System.out.println(nick);
//			if (nick.equals(nickname)) {
//				System.out.println("揶쎛�혢");
//				// room list
//			} else {
//				System.out.println("혢�됀�");
//				// nickname
//			}
//		} else {			// 존재하지 않는다면
//			System.out.println("혰혛혶혙혮혣");
//			int cnt = new UserDao(conn).Insert_AllInfo(ip, nickname);
//			System.out.println(cnt);
//		}
//		
//	}
//	
//	
//	public static String getNickname(String ip) {
//		Connection conn = getConnection();
//		System.out.println("DB CONNECTION");
//		String nickname = new UserDao(conn).getNickname(ip);
//		Close(conn);
//		System.out.println("DB CLOSE");
//		return nickname;
//	}


//	public static boolean getNickIp(User udata) {
//		Connection conn = getConnection();
//		User ret = new UserDao(conn).getNickIP(udata.getIp());
//		if (ret.getNickname() == udata.getNickname()) {
//			return true;
//		} else {
//			return false;
//		}
//	}

//	public User getLoginUser(User user) {
//		User loginUser = null;
//		String ip = user.getIp();
//		String nickname = user.getNickname();
//		System.out.println("getLoginUser" + ip + " " + nickname);
//		Connection conn = getConnection();
//		int count = new UserDao(conn).getIP(ip);
//		System.out.println("count : " + count);
//		if (count > 0) {
//			String nick = new UserDao(conn).getNickname(ip);
//			System.out.println(nick);
//			if (nick.equals(nickname)) {
//				System.out.println("媛숇떎");
//				// room list
//				loginUser = new UserDao(conn).getNickIP(ip);
//			} else {
//				System.out.println("�떖�씪");
//				// nickname
//
//			}
//		} else {
//			System.out.println("�뾾�쓣�븣");
//			int cnt = new UserDao(conn).Insert_AllInfo(ip, nickname);
//			System.out.println(cnt);
//			loginUser = new UserDao(conn).getNickIP(ip);
//		}
//		return loginUser;
//
//	}

//
//	public static void main(String[] args) {
//		String nickname = "혵�눖혙";
////		String nickname = "eunhye";
////		String ip = "192.168.0.249";
//		String ip = "192.168.0.249";
////		String ip = "192.168.0.5";
//		CheckUser(ip, nickname);
//	}


}
