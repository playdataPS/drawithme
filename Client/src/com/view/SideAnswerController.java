package com.view;

import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;

public class SideAnswerController {
	
	
	private static SideAnswerController instance;
	
	public SideAnswerController() {
		// TODO Auto-generated constructor stub
		instance = this;
	}
	
	public static SideAnswerController getInstance() {
		return instance;
	}
	
	
	
	@FXML
	public void clickanswer() {
		
	}
	
}
