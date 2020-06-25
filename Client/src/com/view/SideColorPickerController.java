package com.view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;

public class SideColorPickerController implements Initializable {
	
	
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
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	@FXML
	public void checkSlide() {
		
		System.out.println("slider value"+ slider.getValue());
//		slider.valueProperty().addListener(new ChangeListener<Number>() {
//			@Override
//			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
//				slider.setValue(newValue.doubleValue());
//				
//			}
//		});
	}
}
