package com.vo;

import java.io.Serializable;

public class Data implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private User2 user;
	
	public Data() {
		// TODO Auto-generated constructor stub
	}
	
	public void setUser(User2 user) {
		this.user = user;
	}
	
	public User2 getUser() {
		return user;
	}
	
}
