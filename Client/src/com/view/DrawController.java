package com.view;

import java.net.URL;
import java.util.ResourceBundle;

import com.main.MainApp;
import com.vo.Point;

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
	private ColorPicker cPick;
	@FXML
	private Slider slider;
	
	private GraphicsContext gc;
	
	Point point;
	
    double startX, startY, lastX,lastY,oldX,oldY;
    double hg;

	private MainApp mainApp;
	
	private String word="바나나";
	
	private Stage drawStage;
	
	private static DrawController instance;
	
	
	public static DrawController getInstance() {
		return instance;
	}
	
	public DrawController() {
		instance = this;
	}
	
    public void setDrawStage(Stage drawStage) {
		this.drawStage = drawStage;
	}

	private void freeDrawing(){
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
        
        freeDrawing();
    }
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	
		GameWord();
		
		gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		
		slider.setMin(1);
		slider.setMax(50);
		
		cPick.setValue(Color.BLACK);
		
		inputchat.requestFocus();
	}	
	
	public void GameWord() {
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
	}
	
	public void Turn(String userturn) {
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
		GameWord();
		
		Turn("jihye");
		
		
		Task<Boolean> task = new Task<Boolean>() {

			@Override
			protected Boolean call() throws Exception {
				boolean flag = false;
				for (int i = 0; i <= 50; i++) {
					updateProgress(i, 50);
					Thread.sleep(160);
					flag = true;
				}
				return flag;
			}

		};// task end
		
		bar.progressProperty().bind(task.progressProperty());
		Thread thread = new Thread(task);
		thread.start();
	}
}
