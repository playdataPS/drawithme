package com.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.vo.Game;
import com.vo.GameStatus;
import com.vo.User;

public class GameController {

	private List<User> userList;
	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;

	public GameController() {
		// TODO Auto-generated constructor stub
	}

	public GameController(List<User> users, Socket socket) {
		this.userList = users;
		this.socket = socket;
		try {
			this.ois = new ObjectInputStream(socket.getInputStream());
			this.oos = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 게임 Seeker
	public Queue<Game> createGameQue() {
		List<User> tmp = this.userList;
		List<String> dict = new ArrayList<String>();
		dict.add("연장전");
		dict.add("죽부인");
		dict.add("마법사");
		dict.add("부동산");
		dict.add("두바이");
		dict.add("달팽이");
		dict.add("오이도");
		dict.add("감자탕");
		dict.add("대기층");
		
		Queue<Game> gameQue = new LinkedList<Game>();
		Collections.shuffle(dict);
		Collections.shuffle(tmp);
		
		for (int i = 0; i < userList.size(); i++) {
			 Game gameData = new Game();
			 gameData.setSeeker(tmp.get(i));
			 gameData.setWord(dict.get(i));
			 List<User> tmp2 = tmp;
			 tmp2.remove(i);
			 gameData.mixDrowingUser(tmp2);
			 
			 gameQue.add(gameData);
		}
		
		return gameQue;
	}// createGameQue() end

	public Game startGame(Queue<Game> gameQue) {
		if(!gameQue.isEmpty()) {
			Game gameTurn = gameQue.poll();
			return gameTurn;
		}// if end 
		
		return null;
	}

	public void broadCast(List<User> userList) {// 유저리스트
		for (User user : userList) {
			try {
				user.getOos().writeObject(this);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // 유저들의 상태
		}
	}
}
