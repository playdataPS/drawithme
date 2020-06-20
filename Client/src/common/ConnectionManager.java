package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * 
 * ConnectionManger.getInstance()로 사용.
 * Connection을 사용하려면 getConnectionManger.getConnection으로.
 * con close 는 ConnectionManger.close()
 */
public class ConnectionManager {
	private static String url = "jdbc:oracle:thin:@127.0.0.1:1521:XE";
	private static String driver = "oracle.jdbc.driver.OracleDriver";
	private static String id = "BIG5";
	private static String pwd = "admin1234";
	// 로딩될 때 바로 객체 생성
	private static ConnectionManager cm = new ConnectionManager();
	
	// 외부에서 생성자 못 만들게 하기
	public ConnectionManager() {
		super();
	}
	
	// static initializer 클래스, 로딩타임때 한 번만 실행됨
	static {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}		
	}
	
	public static Connection getConnection() {
		Connection con = null;
		try {
			con = DriverManager.getConnection(url, id, pwd);
			con.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}

	public static void Close(Connection con) {
		try {
			if (!con.isClosed() && con != null) {
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void Close(Statement stmt) {
		try {
			if (stmt != null) {
				stmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void Close(ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void Commit(Connection con) {
		try {
			if (con != null && !con.isClosed()) {
				con.commit();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void Rollback(Connection con) {
		try {
			if (con != null && !con.isClosed()) {
				con.rollback();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
