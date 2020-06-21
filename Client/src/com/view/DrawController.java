package com.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.concurrent.ExecutionException;

import com.client.ClientListener;
import com.main.MainApp;

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
	
	
	
    double startX, startY, lastX,lastY,oldX,oldY;
    double hg;

	private MainApp mainApp;
	
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
		ColorPicker cPick = SideColorPickerController.getInstance().getcPick();
		Slider slider = SideColorPickerController.getInstance().getSlider();
        gc.setLineWidth(slider.getValue());
        gc.setStroke(cPick.getValue());
        gc.strokeLine(oldX, oldY, lastX, lastY);
        oldX = lastX;
        oldY = lastY;
        System.out.println(String.format("oldX : %f, oldY : %f, lastX : %f, lastY : %f", oldX, oldY, lastX, lastY));
        
        System.out.println("color: "+cPick.getValue());
        
        
    }
	
	@FXML
    private void onMousePressedListener(MouseEvent e){
        this.startX = e.getX();
        this.startY = e.getY();
        this.oldX = e.getX();
        this.oldY = e.getY();
    }
	
    @FXML
    private void onMouseDraggedListener(MouseEvent e){
        this.lastX = e.getX();
        this.lastY = e.getY();
        System.out.println("Draw click!");
        freeDrawing();
    }
	
	public void setMainApp() {
//		this.mainApp = mainApp;
	
		//GameWord(word);
		
		gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		
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
	private Boolean turnOver = false;
	
	public Boolean getTurnOver() {
		return turnOver;
	}

	
	public void timer() {
	
		Task<Boolean> task = new Task<Boolean>() {

			@Override
			protected Boolean call() throws Exception {
				boolean flag = false;
				for (int i = 0; i <= 50; i++) {
					updateProgress(i, 50);
					try {
						Thread.sleep(160);
						
						if(isCancelled()) {
							flag = true;
							break;
						}
						
					} catch (InterruptedException e) {
						if (isCancelled()) { break; }
					}

				}
				
				
				return flag;
			}

		};// task end
		
		bar.progressProperty().bind(task.progressProperty());
		Thread thread = new Thread(task);
		thread.start();
		
//		try {
//			turnOver = task.get();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ExecutionException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
			
		return;
		
		
		
	}
}
