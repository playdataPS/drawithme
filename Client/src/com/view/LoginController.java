
package com.view;

import com.client.ClientListener;
import com.main.MainApp;
import com.vo.Room;
import com.vo.User;

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
			User user = new User();
			user.setNickname(nickname);
			playerName = nickname;
			ClientListener.getInstance().createConnect("127.0.0.1", 5555, nickname, MainApp.getInstance());
			ClientListener.getInstance().startHandler();
//			Room room = new Room("�뱾�뼱�삤�꽭�슂", 8);
//			MainApp.getInstance().initWaitingRoom(room);

			MainApp.switchToLobby();

		}

	}

}
