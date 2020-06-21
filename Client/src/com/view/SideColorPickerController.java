package com.view;

import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;

public class SideColorPickerController {
	
	
	@FXML
	private ColorPicker cPick;
	@FXML
	private Slider slider;
	private static SideColorPickerController instance;
	
	public SideColorPickerController() {
		// TODO Auto-generated constructor stub
		
		instance = this;
	}
	public static SideColorPickerController getInstance() {
		return instance;
	}
	
	public void settingTool() {
		slider.setMin(1);
		slider.setMax(50);
		
		cPick.setValue(Color.BLACK);
	}
	
	public Slider getSlider() {
		return slider;
	}
	
	public void setSlider(Slider slider) {
		this.slider = slider;
	}
	
	public ColorPicker getcPick() {
		return cPick;
	}
	public void setcPick(ColorPicker cPick) {
		this.cPick = cPick;
	}
}
