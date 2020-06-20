package com.dao;

import java.sql.*;

import com.vo.User;

import static common.JDBCTemplate.*;

public class UserDao implements UserSql {
   private Connection conn;

   public UserDao(Connection conn) {
      this.conn = conn;
   }


	// check ip  exist
	public int getIP(String ip) {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		int cnt = 0;
		try {
			pstm = conn.prepareStatement(ch_ip);
			pstm.setString(1, ip);
			System.out.println("넘어와" + ip);
			rs = pstm.executeQuery();
			while (rs.next()) {
				cnt = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println(e.toString());
		} finally {
			Close(rs);
			Close(pstm);
		}
		return cnt;
	}

	// check nickname
	public String getNickname(String ip) {
		PreparedStatement pstm = null;
		ResultSet res = null;
		String ret = "";
		try {
			pstm = conn.prepareStatement(ch_nick);
			pstm.setString(1, ip);
			res = pstm.executeQuery();
			while (res.next()) {
				ret = res.getString(1);
			}
		} catch (SQLException e) {
			System.out.println(e.toString());
		} finally {
			Close(res);
			Close(pstm);
		}
		return ret;
	}

	public User getNickIP(String ip) {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		User usernick = null;
//		String vo = null;

		try {
			pstm = conn.prepareStatement(ch_nickip);
			pstm.setString(1, ip);

			rs = pstm.executeQuery();
			while (rs.next()) {
				usernick = new User(rs.getString(1), rs.getString(2));
			}
		} catch (SQLException e) {
			System.out.println(e.toString());
		} finally {
			Close(rs);
			Close(pstm);
		}

		return usernick;
	}

	public int Insert_AllInfo(String ip, String nick) {
		int ret = 0;
		PreparedStatement pstm = null;
		try {
			pstm = conn.prepareStatement(insert_allinfo);
			pstm.setString(1, ip);
			pstm.setString(2, nick);
			ret = pstm.executeUpdate();
			System.out.println(ret);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Commit(conn);
		}
		return ret;

	}

}