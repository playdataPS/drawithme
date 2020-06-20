package com.view;

import com.main.MainApp;
import com.vo.Room;
import com.vo.User;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ScoreController {
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
	
	private Stage scoreStage;
	
	private MainApp mainApp;
	
	private Room room;
	
	private User user;
	
	public ScoreController() {
		super();
	}
	
	@FXML
	private void initialize() {
		user1.setText("");
		user2.setText("");
		user3.setText("");
		user4.setText("");
		user5.setText("");
		user6.setText("");
		user7.setText("");
		user8.setText("");
		score1.setText("");
		score2.setText("");
		score3.setText("");
		score4.setText("");
		score5.setText("");
		score6.setText("");
		score7.setText("");
		score8.setText("");
		
	}
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		
	}
	
	

}
