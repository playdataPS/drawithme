package com.main;

import java.io.IOException;
import java.util.List;

import com.client.ClientListener;
import com.view.AnswerController;
import com.view.DrawController;
import com.view.LoginController;
import com.view.SettingController;
import com.view.WaitingRoomController;
import com.vo.GameUser;
import com.vo.Room;
import com.vo.Status;
import com.vo.User;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MainApp extends Application {

	private static MainApp instance;
	private static Parent gameRoot;
	private static Parent sideColorPickerRoot;
	private static Parent sideAnswerRoot;
	
	private static Stage primaryStage;
	private static Scene loginScene, lobbyScene, gameScene;
	 public static Stage window;

	public Stage getPrimaryStage() {
		return primaryStage;
	}
	
	public static MainApp getInstance() {
		return instance;
	}

	// 방 데이터 추가는 생성자에서!!
	public MainApp() {
		
		instance = this;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
	//	initLogin();
//		initAnswer();
//		initDraw();
//		initScore();
		
	   Parent loginRoot = FXMLLoader.load(getClass().getResource("../view/Login.fxml"));
	        loginScene = new Scene(loginRoot);
	        
		Parent lobbyRoot = FXMLLoader.load(getClass().getResource("../view/WaitingRoom.fxml"));
        lobbyScene = new Scene(lobbyRoot);
        
        sideColorPickerRoot = (AnchorPane) FXMLLoader.load(getClass().getResource("../view/SideColorPicker.fxml"));
        sideAnswerRoot = FXMLLoader.load(getClass().getResource("../view/SideAnswer.fxml"));
        gameRoot = (AnchorPane) FXMLLoader.load(getClass().getResource("../view/Draw.fxml"));
      //  ((AnchorPane) gameRoot).getRightAnchor(sideAnswerRoot);
        
       AnchorPane.setRightAnchor(sideAnswerRoot, 13.0);
       ((AnchorPane) gameRoot).getChildren().add(sideAnswerRoot);
        gameScene = new Scene(gameRoot);
        
        window.setScene(loginScene);
        window.setTitle("Login");
        window.show();
        
        window.setOnCloseRequest(e -> {
        	System.exit(0);
        });
//        List<String> players = new ArrayList<>();
//        players.add("player1");
//        players.add("player2");
//        switchToGame(players);

        // start client listener
        ClientListener clientListener = new ClientListener();
        Thread x = new Thread(clientListener);
        x.start();
		
	}
	 /* move the window to the center of the screen */
    public static void moveToCenter() {
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        window.setX((primScreenBounds.getWidth() - window.getWidth()) / 2);
        window.setY((primScreenBounds.getHeight() - window.getHeight()) / 2);
    }

    /* swith to the lobby scene */
    public static void switchToLobby() {
        // set transparent before relocate, for visually comfortability
        window.setOpacity(0.0);
    	
        // set title and scene
        window.setTitle("Lobby: " + LoginController.getInstance().getPlayerName());
        window.setScene(lobbyScene);
        // relocate window and show
        moveToCenter();
        window.setOpacity(1.0);
        
    }
	
    public static void switchToGame() {
    	Platform.runLater(()->{
    		window.setOpacity(0.0);
        	window.setScene(gameScene);
        	 moveToCenter();
             window.setOpacity(1.0);
    	});
    }
    
	public void initLogin() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("../view/Login.fxml"));
			AnchorPane root = (AnchorPane) loader.load();

			primaryStage.setTitle("Login");

			Scene scene = new Scene(root);
			primaryStage.setScene(scene);

			LoginController controller = loader.getController();
//			controller.setMainApp(this);
			controller.setLoginStage(primaryStage);
//			controller.Update();// 사용자 추가될때마다 업데이트 되어야함

			primaryStage.show();
			primaryStage.setResizable(false);
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void initWaitingRoom(Room room) {

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("../view/WaitingRoom.fxml"));
			AnchorPane root;
			root = (AnchorPane) loader.load();
			Stage waitingRoomStage = new Stage();
			waitingRoomStage.setTitle("Waiting Room");
			waitingRoomStage.initModality(Modality.WINDOW_MODAL);
			waitingRoomStage.initOwner(primaryStage);

			Scene scene = new Scene(root);
			waitingRoomStage.setScene(scene);



			primaryStage.hide();
			waitingRoomStage.show();
			waitingRoomStage.setResizable(false);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public void WatingRommUpdate() {

		System.out.println("update 되는거 호출해요");
	}

	public void initAnswer() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("../view/Answer.fxml"));
			AnchorPane root = (AnchorPane) loader.load();

			Scene scene = new Scene(root);
			primaryStage.setScene(scene);

			AnswerController controller = loader.getController();
			controller.setMainApp(this);

			primaryStage.show();
			primaryStage.setResizable(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void initDraw() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("../view/Draw.fxml"));
			AnchorPane root = (AnchorPane) loader.load();

			Scene scene = new Scene(root);
			primaryStage.setScene(scene);

			DrawController controller = loader.getController();
			controller.setMainApp(this);
			controller.setDrawStage(primaryStage);

			primaryStage.show();
			primaryStage.setResizable(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void initRoomList(User user) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("../view/RoomList.fxml"));
			BorderPane root = (BorderPane) loader.load();
			Stage roomListStage = new Stage();
			roomListStage.setTitle("Room List");
			roomListStage.initModality(Modality.WINDOW_MODAL);
			roomListStage.initOwner(primaryStage);
			Scene scene = new Scene(root);
			roomListStage.setScene(scene);

//			RoomListController controller = loader.getController();
//			controller.setRoomListStage(roomListStage);
			// controller.setUser(user);
			// Client cli = new Client(user, Status.CONNECTED);
//	        ClientListener listener = 
//	        getUserListOfRoomList().add(user);
//	        User userData = new User("127.0.0.1", "eunhye");
//			
//			new ClientListener("127.0.0.1", 5555, userData).createConnect();

//			controller.setMainApp(this);
			primaryStage.hide();
			roomListStage.showAndWait();
			roomListStage.setResizable(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void initSetting(User user) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("../view/Setting.fxml"));
			AnchorPane root = (AnchorPane) loader.load();

			Stage settingStage = new Stage();
			settingStage.setTitle("Setting");
			settingStage.initModality(Modality.WINDOW_MODAL);
			settingStage.initOwner(primaryStage);
			Scene scene = new Scene(root);
			settingStage.setScene(scene);

			SettingController controller = loader.getController();
			controller.setSettingStage(settingStage);
			// controller.setUser(user);

			controller.setMainApp(this);
			settingStage.showAndWait();

			settingStage.setResizable(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void initScore() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("../view/Score.fxml"));
			AnchorPane root = (AnchorPane) loader.load();

			Scene scene = new Scene(root);
			primaryStage.setScene(scene);

			// MainAppController controller = loader.getController();
			// controller.setMainApp(this);

			primaryStage.show();
			primaryStage.setResizable(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
