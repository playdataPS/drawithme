package com.view;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.main.MainApp;
import com.vo.Room;
import com.vo.User;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class RoomListController {
	@FXML
	private TableView<Room> roomList;
	@FXML
	private TableColumn<Room, Integer> roomNo;
	@FXML
	private TableColumn<Room, String> roomName;
	@FXML
	private TableColumn<Room, String> userCnt;
	@FXML
	private TableColumn<Room, String> enterButton;
	@FXML
	private Label noticeLabel;
	@FXML
	private TableView<User> userList;
	@FXML
	private TableColumn<User, String> userName;
	@FXML
	private Button galleryButton;
	@FXML
	private Button createRoomButton;
	@FXML
	private Button randomMatchingButton;

	private MainApp mainApp;

	private Stage roomListStage;

	private User user;

	public RoomListController() {
		super();
	}

	@FXML
	private void initialize() {
//		roomNo.setCellValueFactory(cellData -> cellData.getValue().getNo().asObject());
//		roomName.setCellValueFactory(cellData -> cellData.getValue().getName());
//		userCnt.setCellValueFactory(cellData -> cellData.getValue().getUserCnt());
//		enterButton.setCellValueFactory(cellData -> cellData.getValue().getEnterButton());
//		noticeLabel.setText("빠른 실행을 위해서는 랜덤매칭을 눌러주세요.");
//		// userName.setCellValueFactory(cellData -> cellData.getValue().getNickname());
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;

//		roomList.setItems(mainApp.getRoomData());
//		userList.setItems(mainApp.getUserListOfRoomList());
//
//		userName.setText("접속자 (" + userList.getItems().size() + "명)");
	}

	public Stage getRoomListStage() {
		return roomListStage;
	}

	public void setRoomListStage(Stage roomListStage) {
		this.roomListStage = roomListStage;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@FXML
	private void goToGallery() {

	}

	@FXML
	private void createRoom() {
		mainApp.initSetting(user);
	}

	@FXML
	private void randomMatch() {

	}
}
