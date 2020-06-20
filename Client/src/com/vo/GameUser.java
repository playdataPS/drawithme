package com.vo;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class GameUser {
	private final StringProperty player;
	
	public GameUser() {
		this(null);
	}

	public GameUser(String player) {
		this.player = new SimpleStringProperty(player);
	}
	public StringProperty getPlayer() {
		return player;
	}
	
	public void setPlayer(String player) {
		this.player.set(player);
	}


}