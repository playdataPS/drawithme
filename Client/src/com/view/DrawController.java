package com.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.concurrent.ExecutionException;

import com.vo.Data;
import com.vo.Game;
import com.vo.GameStatus;
import com.vo.Status;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class DrawController implements Initializable {
	@FXML
	private ProgressBar bar;
	@FXML
	private Label user;
	@FXML
	private Button out;
	@FXML
	private Button send;
	@FXML
	private TextField inputchat;
	@FXML
	private TextArea chat;
	@FXML
	private Label word_num2;
	@FXML
	private Label word_num3;
	@FXML
	private Label word_num4;
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
	private Label user11;
	@FXML
	private Canvas canvas;

	@FXML
	private ArrayList<Label> nowPlayerList;

	@FXML
	private ArrayList<Label> wordBox;

	private GraphicsContext gc;

	private Boolean turnOver = false;
	private Thread thread;

	double startX, startY, lastX, lastY, oldX, oldY;
	double hg;

	String color;

	double lineW;

	private String word = "바나나";

	private Stage drawStage;

	private static DrawController instance;

	private String drawer;
	private String challenger;
	private Task<Void> task;
	

	public void setChallenger(String challenger) {
		this.challenger = challenger;
	}

	public String getChallenger() {
		return challenger;
	}

	public void setGc() {
		gc = canvas.getGraphicsContext2D();
	}

	public static DrawController getInstance() {
		return instance;
	}

	public DrawController() {
		this.word = "바나나";
		instance = this;
	}

	public String getDrawer() {
		return drawer;
	}

	public void setDrawer(String drawer) {
		this.drawer = drawer;
	}

	public void setDrawStage(Stage drawStage) {
		this.drawStage = drawStage;
	}

	public void freeDrawing() {
		Platform.runLater(() -> {
//			ColorPicker cPick = SideColorPickerController.getInstance().getcPick();
//			Slider slider = SideColorPickerController.getInstance().getSlider();
//			System.out.println("cPick : "+cPick+" , slider : "+slider);
			gc.setLineWidth(lineW);
			gc.setStroke(Paint.valueOf(color));
			gc.strokeLine(oldX, oldY, lastX, lastY);
			oldX = lastX;
			oldY = lastY;

		});
	}

	public void freeDrawing(Data gameData) {
		Platform.runLater(() -> {

			System.out.println("Drawing: " + gameData.getLineW());
			gc.setLineWidth(gameData.getLineW());
			gc.setStroke(Paint.valueOf(gameData.getColor()));
			gc.strokeLine(gameData.getOldX(), gameData.getOldY(), gameData.getLastX(), gameData.getLastY());
			oldX = lastX;
			oldY = lastY;

			Data requestData = new Data();
			requestData.setStatus(Status.DRAWING);
			requestData.setOldX(gameData.getLastX());
			requestData.setOldY(gameData.getLastY());

			ClientListener.getInstance().sendData(requestData);
		});
	}

	@FXML
	private void onMousePressedListener(MouseEvent e) {

		if (LoginController.getInstance().getPlayerName().equals(drawer)) {
			ColorPicker sideCPick = SideColorPickerController.getInstance().getcPick();
			Slider sideSlider = SideColorPickerController.getInstance().getSlider();

//        Game game = new Game(cPick.getValue(), slider.getValue(), startX, startY);
			Data requestData = new Data();
			requestData.setColor(sideCPick.getValue().toString());
			requestData.setLineW(sideSlider.getValue());

			requestData.setStartX(e.getX());
			requestData.setStartY(e.getY());
			requestData.setOldX(e.getX());
			requestData.setOldY(e.getY());
			requestData.setStatus(Status.PRESSED);
//        requestData.setGameStatus(GameStatus.PRESSED);
			ClientListener.getInstance().sendData(requestData);
		}
	}

	public void setPressedData(Data gameData) {
		Platform.runLater(() -> {
			this.lineW = gameData.getLineW();
			this.color = gameData.getColor();
			this.startX = gameData.getStartX();
			this.startY = gameData.getStartY();
			this.oldX = gameData.getOldX();
			this.oldY = gameData.getOldY();
		});
	}

	@FXML
	private void onMouseDraggedListener(MouseEvent e) {

		if (LoginController.getInstance().getPlayerName().equals(drawer)) {

//			System.out.println("Draw click!");
//        Game game = new Game(oldX, oldY, lastX, lastY);
			Data requestData = new Data();
			requestData.setLastX(e.getX());
			requestData.setLastY(e.getY());

			requestData.setStatus(Status.DRAGGED);
//        requestData.setGameStatus(GameStatus.DRAGGED);
			ClientListener.getInstance().sendData(requestData);
//        freeDrawing();
		}
	}

	public void setDraggedData(Data gameData) {
		Platform.runLater(() -> {
			this.lastX = gameData.getLastX();
			this.lastY = gameData.getLastY();
		});
	}

	public void setCanvasSetting() {
		gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
	}

	public void setMainApp() {
		inputchat.requestFocus();
	}

	public void setNowPlayerList(List<String> players) {
		int idx = 0;
		for (String player : players) {
			nowPlayerList.get(idx).setText(player);
			idx++;
		}
	}

	public void setGameWord(String word) {
		Platform.runLater(() -> {
			char[] charArr = word.toCharArray();

			if (String.valueOf(charArr[0]) != null) {
				word_num2.setText(String.valueOf(charArr[0]));
			}
			if (String.valueOf(charArr[1]) != null) {
				word_num3.setText(String.valueOf(charArr[1]));
			}
			if (String.valueOf(charArr[2]) != null) {
				word_num4.setText(String.valueOf(charArr[2]));
			}
		});

	}

	public void setDrawTurn(String userturn) {
		user.setText(userturn);
	}

	@FXML
	private void submitBtn() {
		chat.appendText(inputchat.getText());
	}

	@FXML
	private void exitbtn() {
		drawStage.hide();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}// initialize() end

	public Boolean getTurnOver() {
		return turnOver;
	}

	public void setTurnOver(Boolean turnOver) {
		this.turnOver = turnOver;
	}

	public void timer() {

		task = new Task<Void>() {

			@Override
			protected Void call() throws Exception {
				if(!Thread.interrupted()) {
					
					for (int i = 0; i <= 50; i++) {
						
						if(isCancelled()) {break;}
						
						try {
							updateProgress(i, 50);
							Thread.sleep(160);
							
						} catch (InterruptedException e) {
							if (isCancelled()) { break; }
						}

					} // for end
					
					cancel();

					if (isCancelled()) {
						System.out.println("cancel");
						if (LoginController.getInstance().getPlayerName().equals(drawer) || 
								(drawer.equals("정답을 맞추세요~")&& LoginController.getInstance().getPlayerName().equals(challenger))) 
						{
							Data requestData = new Data();
							requestData.setStatus(Status.PLAYING);
							requestData.setGameStatus(GameStatus.TURN);
							ClientListener.getInstance().sendData(requestData);
						} // if end
						
						return null;
					}else {
						return null;
					}
				}else {
					return null;
				}
				
			}//

		};// task end
		bar.progressProperty().bind(task.progressProperty());
		thread = new Thread(task);
		thread.setDaemon(true);
		thread.start();
		

	}// timer() end
	
	public void stopTimer() {
		if(thread!=null) {
			thread.interrupt();
			thread = null;
		}
	}
	
	public Task<Void> getTask() {
		return task;
	}
	

}
