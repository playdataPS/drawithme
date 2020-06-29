package com.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Vector;

import com.vo.Data;
import com.vo.Game;
import com.vo.Status;
import com.vo.User;

public class GameSession {

	private Queue<Game> gameQue;
	private Queue<String> drawerQue;
	private List<String> nowPlayerList;
	private Game nowTurn;
	private Map<String, Integer> scoreMap;
	
	public Map<String, Integer> getScoreMap() {
		return scoreMap;
	}
	public void setScoreMap(Map<String, Integer> scoreMap) {
		this.scoreMap = scoreMap;
	}
	
	public Game getNowTurn() {
		return nowTurn;
	}
	
	public void setNowTurn(Game nowTurn) {
		this.nowTurn = nowTurn;
	}
	
	public GameSession(List<String> nowPlayerList) {
		this.nowPlayerList = nowPlayerList;
	}
	
	public List<String> getNowPlayerList() {
		return nowPlayerList;
	}
	
	public void setNowPlayerList(List<String> nowPlayerList) {
		this.nowPlayerList = nowPlayerList;
	}
	
	public Queue<String> getDrawerQue() {
		return drawerQue;
	}
	public void setDrawerQue(Queue<String> drawerQue) {
		this.drawerQue = drawerQue;
	}
	
	public void setGameQue(Queue<Game> gameQue) {
		this.gameQue = gameQue;
	}
	public Queue<Game> getGameQue() {
		return gameQue;
	}
	

	
	
	public void createGameQue() {
		List<String> mixedPlayerList = new Vector<String>();
		List<String> dict = new ArrayList<String>();
		this.gameQue = new LinkedList<Game>();
		scoreMap = new HashMap<String, Integer>();
		
		dict.add("연장전");
		dict.add("죽부인");
		dict.add("마법사");
		dict.add("부동산");
		dict.add("두바이");
		dict.add("달팽이");
		dict.add("오이도");
		dict.add("감자탕");
		dict.add("대기층");

		
		mixedPlayerList.addAll(nowPlayerList);
		Collections.shuffle(dict);
		Collections.shuffle(mixedPlayerList);

		for (int i = 0; i < nowPlayerList.size(); i++) {
			Game gameData = new Game();
			gameData.setChallenger(mixedPlayerList.get(i));
			gameData.setWord(dict.get(i));
			List<String> drawerList = new Vector<String>();
			drawerList.addAll(mixedPlayerList);
			drawerList.remove(i);
			gameData.mixDrowingUser(drawerList);

			gameQue.add(gameData);
			scoreMap.put(gameData.getChallenger(), 0);
			System.out.println("[ challenger + " + gameData.getChallenger()+" ]");
		}
	}// createGameQue() end
	
	
	
	public boolean getTurn() {
		if(!gameQue.isEmpty()) {
			this.nowTurn = gameQue.peek(); //한 턴 
			this.drawerQue = nowTurn.getDrawerQue();
			return true;
		}else {
			return false;
		}
	}
	
	public Data getGameData() {
		Data data = new Data();
		String challenger = nowTurn.getChallenger();
		String word = nowTurn.getWord();
		
		if(!drawerQue.isEmpty()) {
			String drawer = drawerQue.peek();//한 턴의 그림 그리는 사람 한명 
			data.setWord(word);
			data.setDrawer(drawer);
			data.setChallenger(challenger);
			data.setGameUserList(nowPlayerList);
			data.setStatus(Status.PLAYING);
			
			this.nowTurn.setDrawer(drawer);
		}
		
		return data;
	}
	
}
