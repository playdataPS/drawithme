package com.vo;

import java.io.Serializable;
import java.sql.Date;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javafx.scene.paint.Color;


public class Game implements Serializable{

	private int no;
	private int dictNo;
	private String word;
	private String img;
	private Date regdate;
	private Color color;
	private Point point;
	private User seeker;
	private List<User> drowingUserQue;
	
	public Game() {
		// TODO Auto-generated constructor stub
	}
	
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	
	public User getSeeker() {
		return seeker;
	}
	public void setSeeker(User seeker) {
		this.seeker = seeker;
	}
	
	//그림 그릴 유저들만 랜덤으로 순서 부여 
	public void mixDrowingUser(List<User> userList) {//drowing user
		drowingUserQue = new LinkedList<User>();
		Collections.shuffle(userList);		
		this.drowingUserQue.addAll(userList);
	}
	
	public List<User> getDrowingUserQue() {
		return drowingUserQue;
	}
	
	public void setDrowingUserQue(List<User> drowingUserQue) {
		this.drowingUserQue = drowingUserQue;
	}
	
	public Point getPoint() {
		return point;
	}
	
	public void setPoint(Point point) {
		this.point = point;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public int getDictNo() {
		return dictNo;
	}

	public void setDictNo(int dictNo) {
		this.dictNo = dictNo;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public Date getRegdate() {
		return regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	
	
	
	
}
