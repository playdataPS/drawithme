package com.view;



import com.vo.Data;
import com.vo.GameStatus;
import com.vo.Status;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;


public class SideAnswerController {
	
	@FXML	
	private TextField inputanswer;
	
	private static SideAnswerController instance;
	
	public TextField getInputanswer() {
		return inputanswer;
	}
	
	public void setInputanswer(TextField inputanswer) {
		this.inputanswer = inputanswer;
	}
	
	public SideAnswerController() {
		// TODO Auto-generated constructor stub
		instance = this;
	}
	
	public static SideAnswerController getInstance() {
		return instance;
	}
	
	public void clearInputAnswer() {
		Platform.runLater(()->{
			inputanswer.setText("");
		});
	}
	
	
	@FXML
	public void clickanswer() {
		String answer  = inputanswer.getText();
		System.out.println(answer);
		Data requestData = new Data();
		requestData.setAnswer(answer);
		requestData.setStatus(Status.PLAYING);
		requestData.setGameStatus(GameStatus.ANSWER);
		ClientListener.getInstance().sendData(requestData);
		
	}
	
}
