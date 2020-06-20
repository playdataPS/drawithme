package com.view;

import com.main.MainApp;
import com.vo.Room;
import com.vo.User;
import com.vo.User2;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SettingController {
	@FXML
	private TextField roomTitleInput;
	@FXML
	private Label maxLimit;
	@FXML
	private Button minusButton;
	@FXML
	private Button plusButton;
	@FXML
	private Button createRoomButton;
	@FXML
	private Button cancelButton;
	
	private MainApp mainApp;
	
	private Stage settingStage;

	private User user;
	
	@FXML
	private void initialize() {
		maxLimit.setText("2");
		roomTitleInput.setText("");
	}
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	public Stage getSettingStage() {
		return settingStage;
	}

	public void setSettingStage(Stage settingStage) {
		this.settingStage = settingStage;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@FXML
	private void createRoom() {
		String roomName = roomTitleInput.getText();
		int maxNum = Integer.parseInt(maxLimit.getText());
		Room room = new Room(roomName, maxNum);
//		mainApp.initWaitingRoom(user, room);
		settingStage.hide();
//		RoomListController roomlist = new RoomListController();
//		roomlist.getRoomListStage().hide();
	}
	@FXML
	private void cancelClicked() {
		settingStage.hide();
	}
	@FXML
	private void minusClicked() {
		int maxNum = Integer.parseInt(maxLimit.getText());
		if (maxNum > 2) {
			maxNum -= 1;			
		}
		maxLimit.setText(String.valueOf(maxNum));
	}
	@FXML
	private void plusClicked() {
		int maxNum = Integer.parseInt(maxLimit.getText());
		if (maxNum < 8) {			
			maxNum += 1;
		}
		maxLimit.setText(String.valueOf(maxNum));
	}
}
