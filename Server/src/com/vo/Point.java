package com.vo;

import java.io.Serializable;

public class Point implements Serializable {
	private double oldX, oldY;
	private double lastX,lastY;
	public Point(double oldX, double oldY ,double lastX, double lastY) {
		this.oldX = oldX;
		this.oldY = oldY;
		this.lastX = lastX;
		this.lastY = lastY;
	}
	
	public void setLastX(double lastX) {
		this.lastX = lastX;
	}
	public void setLastY(double lastY) {
		this.lastY = lastY;
	}
	public void setOldX(double oldX) {
		this.oldX = oldX;
	}
	public void setOldY(double oldY) {
		this.oldY = oldY;
	}
	
	public double getLastX() {
		return lastX;
	}
	public double getLastY() {
		return lastY;
	}
	public double getOldX() {
		return oldX;
	}
	public double getOldY() {
		return oldY;
	}
}
