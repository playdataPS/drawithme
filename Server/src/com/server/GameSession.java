package com.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Vector;

import com.vo.Game;
import com.vo.User;

public class GameSession {


	public Queue<Game> createGameQue(List<String> nowPlayers) {
		List<String> mixedPlayerList = new Vector<String>();
		List<String> dict = new ArrayList<String>();
		Queue<Game> gameQue = new LinkedList<Game>();
		
		dict.add("연장전");
		dict.add("죽부인");
		dict.add("마법사");
		dict.add("부동산");
		dict.add("두바이");
		dict.add("달팽이");
		dict.add("오이도");
		dict.add("감자탕");
		dict.add("대기층");

		
		mixedPlayerList.addAll(nowPlayers);
		Collections.shuffle(dict);
		Collections.shuffle(mixedPlayerList);

		for (int i = 0; i < nowPlayers.size(); i++) {
			Game gameData = new Game();
			gameData.setChallenger(mixedPlayerList.get(i));
			gameData.setWord(dict.get(i));
			List<String> drawerList = new Vector<String>();
			drawerList.addAll(mixedPlayerList);
			drawerList.remove(i);
			gameData.mixDrowingUser(drawerList);

			gameQue.add(gameData);
			System.out.println("[ challenger + " + gameData.getChallenger()+" ]");
		}

		return gameQue;
	}// createGameQue() end
}
