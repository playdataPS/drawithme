package com.view;

import java.io.IOException;
import java.util.List;

import com.vo.Data;
import com.vo.Game;
import com.vo.Room;
import com.vo.Status;
import com.vo.User;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MainApp extends Application {

	private static MainApp instance;
	private static Parent gameRoot;
	private static Parent sideColorPickerRoot;
	private static Parent sideAnswerRoot;
	
	
	private static Stage primaryStage;	
	private static Scene loginScene, lobbyScene, gameScene, scoreScene;
	 public static Stage window;
	public static String nowChallenger = "-1";
	
	
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
//		initLogin();
//		initAnswer();
//		initDraw();
//		initScore();
		
	   Parent loginRoot = FXMLLoader.load(getClass().getResource("Login.fxml"));
	        loginScene = new Scene(loginRoot);
	        
		Parent lobbyRoot = FXMLLoader.load(getClass().getResource("WaitingRoom.fxml"));
        lobbyScene = new Scene(lobbyRoot);
        
        Parent scoreRoot = FXMLLoader.load(getClass().getResource("Score.fxml"));
        scoreScene = new Scene(scoreRoot);
        
        sideColorPickerRoot = (AnchorPane) FXMLLoader.load(getClass().getResource("SideColorPicker.fxml"));
        sideAnswerRoot = FXMLLoader.load(getClass().getResource("SideAnswer.fxml"));
        gameRoot = (AnchorPane) FXMLLoader.load(getClass().getResource("Draw.fxml"));
      //  ((AnchorPane) gameRoot).getRightAnchor(sideAnswerRoot);
        
       AnchorPane.setBottomAnchor(sideColorPickerRoot, 246.0);
       AnchorPane.setRightAnchor(sideColorPickerRoot, 22.0);
       AnchorPane.setRightAnchor(sideAnswerRoot, 22.0);
       AnchorPane.setBottomAnchor(sideAnswerRoot, 246.0);
       
//       ((AnchorPane) gameRoot).getChildren().add(sideColorPickerRoot);
        gameScene = new Scene(gameRoot);
        
        // style setting code start
        Font.loadFont(getClass().getResourceAsStream("DoHyeon-Regular.ttf"), 20);
        loginScene.getStylesheets().add(getClass().getResource("LoginCSS.css").toString());
        lobbyScene.getStylesheets().add(getClass().getResource("WaitingCSS.css").toString());
        gameScene.getStylesheets().add(getClass().getResource("DrawCSS.css").toString());
        // style setting code end
        
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
    	// 건동코드 시작
    	Platform.runLater(()->{
    	// 건동코드 끝
        window.setOpacity(0.0);
    	
        // set title and scene
        window.setTitle("Lobby: " + LoginController.getInstance().getPlayerName());
        window.setScene(lobbyScene);
        // relocate window and show
        moveToCenter();
        window.setOpacity(1.0);
        // 건동코드 시작
    	});
    	// 건동코드 끝
    }
    
   public static void switchToScore() {
	  Platform.runLater(()->{
		  window.setOpacity(0.0);
		  window.setScene(scoreScene);
          window.setOpacity(1.0);
	  });    
   }
	
    public static void switchToGame(Game startGameData) {
    	Platform.runLater(()->{
    		String loginUserNickname = LoginController.getInstance().getPlayerName();
    		boolean flag = nowChallenger!=null&& !nowChallenger.equals(startGameData.getChallenger())? true:false;
    		nowChallenger = startGameData.getChallenger();
    		String drawer = startGameData.getDrawer();
    		
    		window.setOpacity(0.0);
    		
    		DrawController.getInstance().setDrawer(drawer);
    		DrawController.getInstance().setChallenger(nowChallenger);
        	DrawController.getInstance().setNowPlayerList(startGameData.getGameUserList());
        	//DrawController.getInstance().setMainApp();
        	DrawController.getInstance().setDrawTurn(drawer);
        	DrawController.getInstance().timer();
        	
        
        	System.out.println("flag "+flag);
        	if(flag)	DrawController.getInstance().setCanvasSetting();
        	
        	if(((AnchorPane) gameRoot).getChildren().contains(sideAnswerRoot)) {
    			((AnchorPane) gameRoot).getChildren().remove(sideAnswerRoot);
    		}else if(((AnchorPane) gameRoot).getChildren().contains(sideColorPickerRoot)) {
    			((AnchorPane) gameRoot).getChildren().remove(sideColorPickerRoot);
    		}
        	
        	if(loginUserNickname.equals(nowChallenger)) {
        		DrawController.getInstance().setGameWord("???");
        		((AnchorPane) gameRoot).getChildren().add(sideAnswerRoot);
        		
        	}else if(loginUserNickname.equals(drawer)) {
        		
        		DrawController.getInstance().setGameWord(startGameData.getWord());
        		SideColorPickerController.getInstance().settingTool();
        		((AnchorPane) gameRoot).getChildren().add(sideColorPickerRoot);
        	}
     
//             gameScene = new Scene(gameRoot);

            
             
        	window.setScene(gameScene);
//        	moveToCenter();
        	
//        	System.out.println("Turn : "+DrawController.getInstance().getTurnOver());
            window.setOpacity(1.0);
              	
    	});
    }
    
	public void initLogin() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("Login.fxml"));
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
			loader.setLocation(MainApp.class.getResource("WaitingRoom.fxml"));
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
			loader.setLocation(MainApp.class.getResource("Answer.fxml"));
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
			loader.setLocation(MainApp.class.getResource("Draw.fxml"));
			AnchorPane root = (AnchorPane) loader.load();

			Scene scene = new Scene(root);
			primaryStage.setScene(scene);

			DrawController controller = loader.getController();
			//controller.setMainApp(this);
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
			loader.setLocation(MainApp.class.getResource("RoomList.fxml"));
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
			loader.setLocation(MainApp.class.getResource("Setting.fxml"));
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
			loader.setLocation(MainApp.class.getResource("Score.fxml"));
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