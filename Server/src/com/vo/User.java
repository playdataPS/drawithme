package com.vo;

import java.sql.Date;

public class User {
	private int no;
	private String ip;
	private String nickname;
	private  String score;
	private  Date regdate;
	public User() {
		super();
	}
	public User(String nickname) {
		super();
		this.nickname = nickname;
	}
	public User(String ip, String nickname) {
		super();
		this.ip = ip;
		this.nickname = nickname;
	}
	public User(int no, String ip, String nickname, String score, Date regdate) {
		super();
		this.no = no;
		this.ip = ip;
		this.nickname = nickname;
		this.score = score;
		this.regdate = regdate;
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
	@Override
	public String toString() {
		return String.format("User [no=%s, ip=%s, nickname=%s, score=%s, regdate=%s]", no, ip, nickname, score,
				regdate);
	}
}
