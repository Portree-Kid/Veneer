package de.keithpaterson.javafx.controls.slider;

import com.sun.javafx.collections.TrackableObservableList;

import javafx.beans.InvalidationListener;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

/**
 * 
 * @author keith.paterson
 */

public class MultiThumbSlider extends Control {

	private static final String DEFAULT_STYLE_CLASS = "slider";
	ObservableList<SimpleRangeBorderDoubleProperty> thumbPositions = new TrackableObservableList<SimpleRangeBorderDoubleProperty>() {

		@Override
		protected void onChanged(Change<SimpleRangeBorderDoubleProperty> c) {
			for(DoubleProperty val: c.getList())
			{
				min.setValue(Math.min(min.get(), val.doubleValue()));
				max.setValue(Math.max(max.get(), val.doubleValue()));
	
			}
		}
	};

	/**
	 * @return the thumbPositions
	 */
	public ObservableList<SimpleRangeBorderDoubleProperty> getThumbPositions() {
		return thumbPositions;
	}

	private Orientation orientation = Orientation.HORIZONTAL;
	private DoubleProperty max = new SimpleDoubleProperty();
	
	private DoubleProperty min = new SimpleDoubleProperty();

	public MultiThumbSlider() {
		min.setValue( Integer.MAX_VALUE);
		max.setValue( 0);

		setPrefSize(10, 10);

		getStyleClass().setAll(DEFAULT_STYLE_CLASS);
	}

	/** {@inheritDoc} */
	@Override
	protected Skin<?> createDefaultSkin() {
		return new MultiThumbSliderSkin(this, new MultiThumbSliderBehavior(this, null));
	}

	public Orientation getOrientation() {
		return orientation;
	}

	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}

	public double getMax() {
		return max.doubleValue();
	}

	public DoubleProperty getMaxProperty() {
		return max;
	}

	public double getMin() {
		return min.doubleValue();
	}

	public DoubleProperty getMinProperty() {
		return min;
	}

	/**
	 * @param max
	 *            the max to set
	 */
	public void setMax(int max) {
		this.max.set(max);
	}

	/**
	 * @param min
	 *            the min to set
	 */
	public void setMin(int min) {
		this.min.set(min);
	}
	
	@Override
	protected void setWidth(double value) {
		if( orientation.equals(Orientation.HORIZONTAL))
		{}
		super.setWidth(value);
	}
	
	@Override
	protected void setHeight(double value) {
		if( orientation.equals(Orientation.VERTICAL))
		{}
		super.setHeight(value);
	}
}
