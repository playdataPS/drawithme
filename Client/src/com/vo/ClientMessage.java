package com.vo;

public class ClientMessage {
	private String nickName;
	private String ip;
	private String msg;
	public ClientMessage(String nickName, String ip, String msg) {
		super();
		this.nickName = nickName;
		this.ip = ip;
		this.msg = msg;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	@Override
	public String toString() {
		return String.format("ClientMessage [nickName=%s, ip=%s, msg=%s]", nickName, ip, msg);
	}
}
