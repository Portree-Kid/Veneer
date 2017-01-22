package de.keithpaterson.javafx.controls.slider;

import java.util.List;

import com.sun.javafx.scene.control.behavior.BehaviorBase;
import com.sun.javafx.scene.control.behavior.KeyBinding;
import com.sun.javafx.util.Utils;

import javafx.beans.property.DoubleProperty;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class MultiThumbSliderBehavior extends BehaviorBase<MultiThumbSlider>{

	public MultiThumbSliderBehavior(MultiThumbSlider arg0, List<KeyBinding> arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public void thumbPressed(MouseEvent me, float f) {
		// TODO Auto-generated method stub
		
	}

	public void thumbDragged(MouseEvent me, double position) {
        final MultiThumbSlider slider = getControl();
        StackPane thumb = (StackPane) me.getSource();
        DoubleProperty object = (DoubleProperty) thumb.getProperties().get(MultiThumbSliderSkin.VALUE);
        object.setValue(Utils.clamp(slider.getMin(), (position * (slider.getMax() - slider.getMin())) + slider.getMin(), slider.getMax()));
	}

}
