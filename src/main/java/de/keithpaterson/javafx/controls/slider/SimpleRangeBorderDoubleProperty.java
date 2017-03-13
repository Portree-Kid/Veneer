package de.keithpaterson.javafx.controls.slider;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Pos;

public class SimpleRangeBorderDoubleProperty extends SimpleDoubleProperty {

	private Pos direction;

	/**
	 * @return the direction
	 */
	public Pos getDirection() {
		return direction;
	}

	public SimpleRangeBorderDoubleProperty(double currentValue, Pos d) {
		super(currentValue);
		direction = d;
	}

	public void setDirection(Pos p) {
		this.direction = p;
	}

}
