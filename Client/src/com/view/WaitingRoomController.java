package com.view;

import java.net.URL;
import java.nio.Buffer;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import com.vo.Data;
import com.vo.GameStatus;
import com.vo.Room;
import com.vo.Status;
import com.vo.User;
import com.vo.UserStatus;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class WaitingRoomController {

	private static WaitingRoomController instance;
	@FXML
	private Label RoomNameLabel;
	@FXML
	private Label CurrUserCount;
	@FXML
	private Label MaxUserCount;

	@FXML
	private Label UserLabel1;
	@FXML
	private Label UserLabel2;
	@FXML
	private Label UserLabel3;
	@FXML
	private Label UserLabel4;
	@FXML
	private Label UserLabel5;
	@FXML
	private Label UserLabel6;
	@FXML
	private Label UserLabel7;
	@FXML
	private Label UserLabel8;

	@FXML
	private Label[] UserLabels = { UserLabel1, UserLabel2, UserLabel3, UserLabel4, UserLabel5, UserLabel6, UserLabel7,
			UserLabel8 };

	@FXML
	private TextArea chatArea;
	@FXML
	private TextField input;
	@FXML
	private Button submitButton;
	@FXML
	private Button readyStart;
	@FXML
	private Button exitButton;
	@FXML
	private Pane user1;
	@FXML
	private Pane user2;
	@FXML
	private Pane user3;
	@FXML
	private Pane user4;
	@FXML
	private Pane user5;
	@FXML
	private Pane user6;
	@FXML
	private Pane user7;
	@FXML
	private Pane user8;

	@FXML
	private GridPane grid;

	private Stage waitingRoomStage;

	private MainApp mainApp;

	private Room room;

	private User user;
	private final String CARD_STYLE = "-fx-background-radius: 5px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.5), 10, 0, 0, 0);";
	private final String CARD_BLUE = "-fx-background-color : #42A5F5; -fx-background-radius: 5px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.5), 10, 0, 0, 0);";
	private final String CARD_RED = "-fx-background-color : #EF5350; -fx-background-radius: 5px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.5), 10, 0, 0, 0);";
	private String state = "R";
	@FXML
	private List<Label> labelList;
	@FXML
	private List<Pane> userPaneList;

	public WaitingRoomController() {
		instance = this;
	}

	public static WaitingRoomController getInstance() {
		return instance;
	}

	@FXML
	private void initialize() {
//		System.out.println("initialize  " + getUser().getNickname());
		RoomNameLabel.setText("Waiting Room");
//		UserLabel1.setText(getUser().getNickname());
		UserLabel1.setText("");
		UserLabel2.setText("");
		UserLabel3.setText("");
		UserLabel4.setText("");
		UserLabel5.setText("");
		UserLabel6.setText("");
		UserLabel7.setText("");
		UserLabel8.setText("");
		input.setText("");
		input.requestFocus();
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
//		CurrUserCount.setText(String.valueOf(mainApp.getUserListOfRoomList().size()));
		MaxUserCount.setText("8");
		// UserLabel8.getStylesheets().add("LabelStyle.css");
		// UserLabel8.getStyleClass().add(".-rectPrepared");
	}

	public Stage getWaitingRoomStage() {
		return waitingRoomStage;
	}

	public void setWaitingRoomStage(Stage waitingRoomStage) {
		this.waitingRoomStage = waitingRoomStage;
	}

	public void changeRoomName() {
		RoomNameLabel.setText(room.getName());
	}

//	public void changeMaxNum() {
//		MaxUserCount.setText(room.getUserCnt().substring(room.getUserCnt() - 1,
//				room.getUserCnt()));
//	}

	public void changeCurrNum(int x) {
		CurrUserCount.setText(String.valueOf(x));
	}

	public void setFirstMessage(String nickname) { // 처음 입장한 사람이 있을때 "000" 님이 입장하셨습니다.
		String message = "님이 입장하셨습니다.";
		String Contents = chatArea.getText();
		if (Contents.length() > 0) {
			System.out.println("0>");
			chatArea.setText(Contents + "\n" + nickname + " " + message);
		} else {
			System.out.println("없졍");
			chatArea.setText(nickname + " " + message);
		}
	}

	public void setLastMessage(String nickname) {
		// 사용자가 나갔을때, remove 되어서 그 사용자의 정보를 알기가 어렵넹
	}

	@FXML
	public void submitAppend() { // 닉네임, 입력 메시지 내용 넘겨주기
		Platform.runLater(() -> {
			String curUser = LoginController.getInstance().getPlayerName();
			String mcontent = input.getText();
			ClientListener cli = new ClientListener();
			Data data = new Data();
			data.setStatus(Status.LOBBY_CHAT);
			data.setMessage(mcontent);
			data.setNickname(curUser);
			cli.sendData(data);
			input.setText("");
		});
	}

	public void changeMessage(String nickname, String message) { // 닉네임, 입력메시지 내용 출력
		// 채팅창의 내용을 업데이트
		System.out.println("changeMessage" + nickname + ":" + message);
		String Contents = chatArea.getText();
		chatArea.setText(Contents + "\n" + nickname + ":" + message);
	}

	public void changeLabel1() {
		UserLabel1.setText(user.getNickname());
	}

	public void changeLabel1(String nickname) {
		UserLabel1.setText(nickname);
	}

	public void changeLabel2() {
		UserLabel2.setText(user.getNickname());
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public void refreshConstraints() {

	}

	public void changeLabel(Data userStatusData) {
		// 유저가 입장하면 카드가 변화됨
		Platform.runLater(() -> {
//			RoomNameLabel.setText(user.getNickname());
			List<UserStatus> userStatusList  = userStatusData.getUserStatusList();
			List<String> users = userStatusData.getGameUserList();
			CurrUserCount.setText(String.valueOf(users.size()));
			for (int i = 0; i < users.size(); i++) {
				String nickname = users.get(i);
				System.out.println("nickname  :  " + nickname);
				if(userStatusList.get(i).equals(UserStatus.WAITING)) {
					readyStart.setText("준비");
					readyStart.setStyle(CARD_RED);
					userPaneList.get(i).setStyle(CARD_BLUE);
				}else {
					readyStart.setText("취소");
					readyStart.setStyle(CARD_BLUE);
					userPaneList.get(i).setStyle(CARD_RED);
				}
				
				labelList.get(i).setText(nickname);
			} // for end

		});

	}

	public void changeReadyColor(Data userStatusData) {
		//List<UserStatus> userStatusList, List<String> users
		
		// ready 버튼 클릭 후 카드 변화
		// 사용자가 몇번째 pane에 들어가는지 알아야 background 바꿀 수 있음.
		Platform.runLater(() -> {
			List<UserStatus> userStatusList  = userStatusData.getUserStatusList();
			List<String> users = userStatusData.getGameUserList();
			int idx = 0;
			StringBuffer sb = new StringBuffer();

			for (String uName : users) { // users들
				if(idx>=userStatusList.size())	continue;
				UserStatus userstatus = userStatusList.get(idx);
				System.out.println(" user "+uName+" : "+userstatus);
				//if (userstatus == null)	userstatus = UserStatus.WAITING; // room 상태가 null이면 wating
				if (userstatus.equals(UserStatus.WAITING) && uName.equals(labelList.get(idx).getText())) {
					userPaneList.get(idx).setStyle(CARD_BLUE);
					readyStart.setText("준비");
					readyStart.setStyle(CARD_RED);
				} else if(userstatus.equals(UserStatus.READY) && uName.equals(labelList.get(idx).getText())){
					readyStart.setText("취소");
					readyStart.setStyle(CARD_BLUE);
					userPaneList.get(idx).setStyle(CARD_RED);
				
				}
//				labelList.get(idx).setText(uName);
				idx++;
			}

			return;
		});

	}

	@FXML
	private void readyStartState() {

	}

	@FXML
	private void exitApp() {
		System.exit(0);
	}

	@FXML
	public void handleBtnStart(ActionEvent event) {
		// 준비 버튼 클릭 이벤트
		String nickname = LoginController.getInstance().getPlayerName();
		Data sendData = new Data();
		Status nowStatus = null;
		
		for(int i=0; i<labelList.size(); i++) {
			if (labelList.get(i).getText().equals(nickname)) {//클릭한 유저의 현재 상태를 확인 
				System.out.println(nickname+" CLICK! nowStatus "+userPaneList.get(i).getStyle());
				nowStatus = userPaneList.get(i).getStyle().equals(CARD_RED)? Status.READY: Status.WAITING;				
			}
		}

		
		switch (nowStatus) { //바꿔서 보내기 
		case WAITING:
			sendData.setUserStatus(UserStatus.READY);
//			sendData.setStatus(Status.READY);
			break;

		case READY:
			sendData.setUserStatus(UserStatus.WAITING);
//			sendData.setStatus(Status.WAITING);
			break;
		default:
			break;
		}
		sendData.setStatus(Status.LOBBY);
		sendData.setNickname(nickname);
		ClientListener.getInstance().sendData(sendData);
	}//handleBtnStart() end 

}
