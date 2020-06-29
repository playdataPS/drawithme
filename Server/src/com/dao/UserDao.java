package com.dao;

import static common.JDBCTemplate.*;

import java.sql.*;

import com.vo.User;

public class UserDao implements UserSql {
   private Connection conn;

   public UserDao(Connection conn) {
      this.conn = conn;
   }

   // String sign_up_gu = "BEGIN SIGN_UP_GU(?, ?); END;";
   public int getSignUp(User user) {
	int res = 0;
	CallableStatement cstmt = null;
	try {
		cstmt = conn.prepareCall(sign_up_gu);
		
		cstmt.setString(1, user.getIp());
		cstmt.setString(2, user.getNickname());
		
		cstmt.execute();
		Commit(conn);
	} catch (Exception e) {
		Rollback(conn);
	} finally {
		Close(cstmt);
	}
	return res;
   }
   
   // String log_in_gu = "{ ? = CALL LOG_IN_GU(?, ?) }";
   public int getLogIn(User user) {
	   int res = 0;
	   CallableStatement cstmt = null;
	   try {
		   cstmt = conn.prepareCall(log_in_gu);
		   cstmt.setQueryTimeout(1800);
		   cstmt.setString(2, user.getIp());
		   cstmt.setString(3, user.getNickname());
		   cstmt.registerOutParameter(1, java.sql.Types.INTEGER);
		   
		   int i = cstmt.executeUpdate();
		   System.out.println(i);
			System.out.println("check1");
		   res = cstmt.getInt(1);
			System.out.println("check2");
			
			Commit(conn);
		} catch (Exception e) {
			e.printStackTrace();
			Rollback(conn);
		} finally {
			Close(cstmt);
		}
	   return res;
   }

	// check ip  exist
	public int getIP(String ip) {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		int cnt = 0;
		try {
			pstm = conn.prepareStatement(ch_ip);
			pstm.setString(1, ip);
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
	// Insert User
		public int getInsertAll(User user) {
			int res = 0;
			PreparedStatement pstm = null;

			try {
				pstm = conn.prepareStatement(insertAll);
				pstm.setString(1, user.getIp());
				pstm.setString(2, user.getNickname());

				res = pstm.executeUpdate();
				if (res > 0) {
					Commit(conn);
				}

			} catch (Exception e) {
				Rollback(conn);

			} finally {
				Close(pstm);
			}

			return res;
		}

}