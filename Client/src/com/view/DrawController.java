package com.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.concurrent.ExecutionException;

import com.client.ClientListener;
import com.main.MainApp;
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
import javafx.stage.Stage;

public class DrawController implements Initializable{
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
	
    double startX, startY, lastX,lastY,oldX,oldY;
    double hg;

	
	private String word = "바나나";
	
	private Stage drawStage;
	
	
	private static DrawController instance;
	
	private String drawer;
	
	public static DrawController getInstance() {
		return instance;
	}
	
	public DrawController() {
		this.word ="바나나";
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

	private void freeDrawing(){
		Platform.runLater(()->{
			ColorPicker cPick = SideColorPickerController.getInstance().getcPick();
			Slider slider = SideColorPickerController.getInstance().getSlider();
			System.out.println("cPick : "+cPick+" , slider : "+slider);
	        gc.setLineWidth(slider.getValue());
	        gc.setStroke(cPick.getValue());
	        gc.strokeLine(oldX, oldY, lastX, lastY);
	        oldX = lastX;
	        oldY = lastY;
	        System.out.println(String.format("oldX : %f, oldY : %f, lastX : %f, lastY : %f", oldX, oldY, lastX, lastY));
	        
	        System.out.println("color: "+cPick.getValue());
			
		});
    }
	
	@FXML
    private void onMousePressedListener(MouseEvent e){
		  ColorPicker cPick = SideColorPickerController.getInstance().getcPick();
			Slider slider = SideColorPickerController.getInstance().getSlider();
        this.startX = e.getX();
        this.startY = e.getY();
        this.oldX = e.getX();
        this.oldY = e.getY();
        Game game = new Game(cPick.getValue(), slider.getValue(), startX, startY);
        Data requestData = new Data(game);
        requestData.setStatus(Status.PLAYING);
        requestData.setGameStatus(GameStatus.DRAWER);
      //  ClientListener.getInstance().sendData(requestData);
    }
	
    @FXML
    private void onMouseDraggedListener(MouseEvent e){
    	
        this.lastX = e.getX();
        this.lastY = e.getY();
      
        System.out.println("Draw click!");
        Game game = new Game(oldX, oldY, lastX, lastY);
        Data requestData = new Data(game);
        requestData.setStatus(Status.PLAYING);
        requestData.setGameStatus(GameStatus.DRAWER);
   //     ClientListener.getInstance().sendData(requestData); 
//        freeDrawing();
    }
    
    public void setCanvasSetting() {
    	gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }
	
	public void setMainApp() {		
		inputchat.requestFocus();
	}	
	
	
	public void setNowPlayerList( List<String> players ) {
		int idx  = 0;
		for(String player : players) {
			nowPlayerList.get(idx).setText(player);
			idx++;
		}
	}
	
	public void setGameWord(String word) {
		Platform.runLater(()->{
			char[] charArr = word.toCharArray();
			
			if(String.valueOf(charArr[0]) != null) {
				word_num2.setText(String.valueOf(charArr[0]));
			}
			if(String.valueOf(charArr[1]) != null) {
				word_num3.setText(String.valueOf(charArr[1]));
			}
			if(String.valueOf(charArr[2]) != null) {
				word_num4.setText(String.valueOf(charArr[2]));
			}	
		});
			
	}
	
	public void setDrawTurn(String userturn) {
		
		Platform.runLater(()->{
			user.setText(userturn);
		});
		
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
		
	}//initialize() end
	
	
	public Boolean getTurnOver() {
		return turnOver;
	}
	public void setTurnOver(Boolean turnOver) {
		this.turnOver = turnOver;
	}


	public void timer() {
	
		Task<Void> task = new Task<Void>() {

			@Override
			protected Void call() throws Exception {
				
				for (int i = 0; i <= 50; i++) {
					updateProgress(i, 50);
					try {
						if(isCancelled()) {
							
							break;
						}
						Thread.sleep(160);
						
					} catch (InterruptedException e) {
						if (isCancelled()) { break; }
					}

				}//for end 
				
				cancel();
				
				if(isCancelled()) {
					System.out.println("cancel");
					
					Data requestData = new Data();
					requestData.setStatus(Status.PLAYING);
					requestData.setGameStatus(GameStatus.TURN);
					ClientListener.getInstance().sendData(requestData);
					
					return null;
				}
				
				
				return null;
			}
			

		};// task end
		
		bar.progressProperty().bind(task.progressProperty());
		Thread thread = new Thread(task);
		thread.start();
		
	}//timer() end 
}
