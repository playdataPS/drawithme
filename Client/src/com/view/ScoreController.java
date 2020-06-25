package com.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.main.MainApp;
import com.vo.Data;
import com.vo.Room;
import com.vo.User;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ScoreController {
	//private static ScoreController instance;

	private static ScoreController instance;
	@FXML
	private Button out;
	@FXML
	private Label user1;
	@FXML
	private Label user2;
	@FXML
	private Label user3;
	@FXML
	private Label user4;
	@FXML
	private Label user5;
	@FXML
	private Label user6;
	@FXML
	private Label user7;
	@FXML
	private Label user8;
	@FXML
	private Label score1;
	@FXML
	private Label score2;
	@FXML
	private Label score3;
	@FXML
	private Label score4;
	@FXML
	private Label score5;
	@FXML
	private Label score6;
	@FXML
	private Label score7;
	@FXML
	private Label score8;
//	Map<String,String> map = new HashMap<String,String>(); 	
//	
//	Map<String,String> finalScore= map;
	@FXML
	private ArrayList<Label> nowPlayerList;

	@FXML
	private ArrayList<Label> scoreList;

	
	public ScoreController() {
		instance = this;
	}
	public static ScoreController getInstance() {
		return instance;
	}

	
	public void getRanking(Data responseData) {
		Platform.runLater(()->{
			System.out.println("setScore: "+responseData.getScore());
			String score = responseData.getScore();
	
		String text= score.replace('=',',');
		List<String> list = new ArrayList<String>();
		
		String[] splitStr = text.split(",");
		for(int i=0; i<splitStr.length; i++){
			list.add(splitStr[i]);
			if(i%2==0) {
				nowPlayerList.get(i).setText(list.get(i));
			}else {
				scoreList.get(i).setText(list.get(i));
			}
			
		}
//		user1.setText(list.get(0));
//		user2.setText(list.get(2));
//		user3.setText(list.get(4));
//		user4.setText(list.get(6));
//		user5.setText(list.get(8));
//		user6.setText(list.get(10));
//		user7.setText(list.get(12));
//		user8.setText(list.get(14));
		
//		score1.setText(list.get(1));
//		score2.setText(list.get(3));
//		score3.setText(list.get(5));
//		score4.setText(list.get(7));
//		score5.setText(list.get(9));
//		score6.setText(list.get(11));
//		score7.setText(list.get(13));
//		score8.setText(list.get(15));
		
		
		/*
		 * score1.setText((String) finalScore.keySet().toArray()[0]);
		 * score2.setText((String) finalScore.keySet().toArray()[1]);
		 * score3.setText((String) finalScore.keySet().toArray()[2]);
		 * score4.setText((String) finalScore.keySet().toArray()[3]);
		 * score5.setText((String) finalScore.keySet().toArray()[4]);
		 * score6.setText((String) finalScore.keySet().toArray()[5]);
		 * score7.setText((String) finalScore.keySet().toArray()[6]);
		 * score8.setText((String) finalScore.keySet().toArray()[7]);
		 */
		
		});
	}

}