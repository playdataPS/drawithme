package com.vo;

import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;

public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int no;
	private String ip;
	private String nickname;
	private int score;
	private Date regdate;
	private transient ObjectOutputStream oos;
	private Status status;
	private int roomNo;
	private String Message;
	private MessageType type;
	private Room gameRoom;
	private List<User> userList;
	private GameStatus gameStatus;
	private RoomStatus roomStatus;

	private String word;
	private Color color;
	private Point point;
	private User seeker;
	

	class Point {

		/**
		 * 
		 */
		private double oldX, oldY;
		private double lastX, lastY;

		public Point(double oldX, double oldY, double lastX, double lastY) {
			this.oldX = oldX;
			this.oldY = oldY;
			this.lastX = lastX;
			this.lastY = lastY;
		}

		public void setLastX(double lastX) {
			this.lastX = lastX;
		}

		public void setLastY(double lastY) {
			this.lastY = lastY;
		}

		public void setOldX(double oldX) {
			this.oldX = oldX;
		}

		public void setOldY(double oldY) {
			this.oldY = oldY;
		}

		public double getLastX() {
			return lastX;
		}

		public double getLastY() {
			return lastY;
		}

		public double getOldX() {
			return oldX;
		}

		public double getOldY() {
			return oldY;
		}
	}

	public User() {
		// TODO Auto-generated constructor stub
	}

	public User(String ip, String nickname, Status status, ObjectOutputStream oos) {
		this.ip = ip;
		this.nickname = nickname;
		this.status = status;
		this.oos = oos;
		this.userList = new ArrayList<User>();

	}

	public User(String ip, String nickname, Status status) {
		this(ip, nickname, status, null);
	}

	public User(String message) {
		super();
		Message = message;
	}

	public User(String ip, String nickname) {
		super();
		this.ip = ip;
		this.nickname = nickname;
	}

	public User(int no, String ip, String nickname, int score, Date regdate, Status status, ObjectOutputStream oos) {
		this.no = no;
		this.ip = ip;
		this.nickname = nickname;
		this.score = score;
		this.regdate = regdate;
		this.status = status;
		this.oos = oos;
	}

	public User(int no, String ip, String nickname, int score, Date regdate, Status status) {
		this(no, ip, nickname, score, regdate, status, null);
	}

	public RoomStatus getRoomStatus() {
		return roomStatus;
	}

	public void setRoomStatus(RoomStatus roomStatus) {
		this.roomStatus = roomStatus;
	}

	public int getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(int roomNo) {
		this.roomNo = roomNo;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public User getSeeker() {
		return seeker;
	}

	public void setSeeker(User seeker) {
		this.seeker = seeker;
	}


	public GameStatus getGameStatus() {
		return gameStatus;
	}

	public void setGameStatus(GameStatus gameStatus) {
		this.gameStatus = gameStatus;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public Room getGameRoom() {
		return gameRoom;
	}

	public void setGameRoom(Room gameRoom) {
		this.gameRoom = gameRoom;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Date getRegdate() {
		return regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return Message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		Message = message;
	}

	/**
	 * @return the type
	 */
	public MessageType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(MessageType type) {
		this.type = type;
	}

	/**
	 * @return the oos
	 */
	public ObjectOutputStream getOos() {
		return oos;
	}

	/**
	 * @param oos the oos to set
	 */
	public void setOos(ObjectOutputStream oos) {
		this.oos = oos;
	}

}
