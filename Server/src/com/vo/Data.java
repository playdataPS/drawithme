package com.vo;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

public class Data implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private  int userNo;
	private  String ip;
	private  String nickname;
	private  String score;
	private  Date regdate;
	private  String word;
	private  String color; // �듅�씠�궗�빆 �븘�옒 �깮�꽦�옄 李멸퀬
	private  double lineW;
	private  String challenger;
	private  String drawer;
	private  List<String> gameUserList;
	private  double startX;
	private  double startY;
	private  double oldX;
	private  double oldY;
	private  double lastX;
	private  double lastY;
	private  String message; // Data 媛앹껜留� 媛숆퀬 �엳�뒗 寃�1
	private  Status status; // Data 媛앹껜留� 媛숆퀬 �엳�뒗 寃�2
	private  GameStatus gameStatus; // Data 媛앹껜留� 媛숆퀬 �엳�뒗 寃�3
	private  UserStatus userStatus; // Data 媛앹껜留� 媛숆퀬 �엳�뒗 寃�4
	public Data() {
		super();
	}
	public Data(User user) {
		super();
		this.userNo = user.getNo();
		this.ip = user.getIp();
		this.nickname = user.getNickname();
		this.score = String.valueOf(user.getScore());
		this.regdate = user.getRegdate();
	}
	public Data(Game game) {
		super();
		this.word = game.getWord();
		// javafx.scene.paint.Color瑜� toString()�븯硫� hex web value瑜� 臾몄옄�뿴濡� 諛쏆븘�삱 �닔 �엳�쓬
		this.color = game.getColor().toString(); 
		this.lineW = game.getLineW();
		this.challenger = game.getChallenger();
		this.drawer = game.getDrawer();
		this.gameUserList = game.getGameUserList();
		this.startX = game.getStartX();
		this.startY = game.getStartY();
		this.oldX = game.getOldX();
		this.oldY = game.getOldY();
		this.lastX = game.getLastX();
		this.lastY = game.getLastY();
	}
	public Data(User user, Game game) {
		super();
		this.userNo = user.getNo();
		this.ip = user.getIp();
		this.nickname = user.getNickname();
		this.score = String.valueOf(user.getScore());
		this.regdate = user.getRegdate();
		this.word = game.getWord();
		this.color = game.getColor().toString();
		this.lineW = game.getLineW();
		this.challenger = game.getChallenger();
		this.drawer = game.getDrawer();
		this.gameUserList = game.getGameUserList();
		this.startX = game.getStartX();
		this.startY = game.getStartY();
		this.oldX = game.getOldX();
		this.oldY = game.getOldY();
		this.lastX = game.getLastX();
		this.lastY = game.getLastY();
	}
	public int getUserNo() {
		return userNo;
	}
	public void setUserNo(int userNo) {
		this.userNo = userNo;
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
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public double getLineW() {
		return lineW;
	}
	public void setLineW(double lineW) {
		this.lineW = lineW;
	}
	public String getChallenger() {
		return challenger;
	}
	public void setChallenger(String challenger) {
		this.challenger = challenger;
	}
	public String getDrawer() {
		return drawer;
	}
	public void setDrawer(String drawer) {
		this.drawer = drawer;
	}
	public List<String> getGameUserList() {
		return gameUserList;
	}
	public void setGameUserList(List<String> gameUserList) {
		this.gameUserList = gameUserList;
	}
	public double getStartX() {
		return startX;
	}
	public void setStartX(double startX) {
		this.startX = startX;
	}
	public double getStartY() {
		return startY;
	}
	public void setStartY(double startY) {
		this.startY = startY;
	}
	public double getOldX() {
		return oldX;
	}
	public void setOldX(double oldX) {
		this.oldX = oldX;
	}
	public double getOldY() {
		return oldY;
	}
	public void setOldY(double oldY) {
		this.oldY = oldY;
	}
	public double getLastX() {
		return lastX;
	}
	public void setLastX(double lastX) {
		this.lastX = lastX;
	}
	public double getLastY() {
		return lastY;
	}
	public void setLastY(double lastY) {
		this.lastY = lastY;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public GameStatus getGameStatus() {
		return gameStatus;
	}
	public void setGameStatus(GameStatus gameStatus) {
		this.gameStatus = gameStatus;
	}
	public UserStatus getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(UserStatus userStatus) {
		this.userStatus = userStatus;
	}
}
