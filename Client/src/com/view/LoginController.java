
package com.view;

import com.vo.User;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

	private static LoginController instance;

	@FXML
	private TextField nicknameTextField;
	@FXML
	private Button startButton;
	@FXML
	private Label noticeLabel;

	private Stage loginStage;
	private String playerName;
	private User user;

	public LoginController() {
		instance = this;
	}

	public static LoginController getInstance() {
		return instance;
	}

	public String getPlayerName() {
		return playerName;
	}

	@FXML
	private void initialize() {
		nicknameTextField.setText("");
		noticeLabel.setText("로그인하세용.");
	}

	public void setLoginStage(Stage loginStage) {
		this.loginStage = loginStage;
	}
	
	public User getUser() {
		return user;
	}
	
	// 건동코드 시작
	public void changeNotice() {
		Platform.runLater(() -> {
			noticeLabel.setText("닉네임이 틀렸습니다.");			
		});
	}
	// 건동코드 끝

	@FXML
	private void gameStart() {
		String nickname = nicknameTextField.getText();

		boolean check = nickname.isEmpty();

		if (check) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(loginStage);
			alert.setTitle("ERRORS");
			alert.setContentText("아니예용");
			alert.showAndWait();
		} else {
			User user = new User(nickname);
			playerName = nickname;
			ClientListener.getInstance().createConnect("192.168.0.249", 5555, nickname, MainApp.getInstance());
			ClientListener.getInstance().startHandler();
			// 건동코드 시작
			// 코드삭제
			// 건동코드 끝
		}

	}

}
