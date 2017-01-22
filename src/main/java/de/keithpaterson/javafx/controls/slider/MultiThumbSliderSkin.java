package de.keithpaterson.javafx.controls.slider;

import java.util.ArrayList;
import java.util.List;

import com.sun.javafx.scene.control.skin.BehaviorSkinBase;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

/**
 * 
 * @author keith.paterson
 *
 */

public class MultiThumbSliderSkin extends BehaviorSkinBase<MultiThumbSlider, MultiThumbSliderBehavior> {

	public static final String VALUE = "VALUE";

	protected MultiThumbSliderSkin(MultiThumbSlider s, MultiThumbSliderBehavior sb) {
		super(s, sb);
		redraw();
		s.getThumbPositions().addListener(new ListChangeListener<DoubleProperty>() {
			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends DoubleProperty> c) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						redraw();
					}
				});
			}
		});
	}

	private StackPane track;

	private double thumbHeight;

	private double trackLength;

	private double trackStart;

	private double thumbTop;

	private double thumbWidth;

	private double thumbLeft;

	private ArrayList<StackPane> thumbs = new ArrayList<>();

	private double thumbPrefWidth;

	private double preDragThumbPos;
	private javafx.geometry.Point2D dragStart; // in skin coordinates
	
	/**If true the values for the thumbs are not percent of max but the */
//	private boolean trueMode = true;

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}
	
	/**
	 * Readds our children if the number of nodes changes. 
	 */

	protected void redraw() {
		track = new StackPane();
		track.getStyleClass().setAll("track");
		List<StackPane> rootChildren = new ArrayList<StackPane>();

		for (DoubleProperty pos : getSkinnable().getThumbPositions()) {
			final StackPane thumb = new StackPane();
			thumb.getProperties().put(VALUE, pos);
			positionThumb(thumb, pos.get(), getSkinnable().getOrientation()==Orientation.HORIZONTAL);
			pos.addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
					positionThumb(thumb, newValue.doubleValue(), getSkinnable().getOrientation()==Orientation.HORIZONTAL);
				}
			});
			thumb.getStyleClass().setAll("thumb");
			rootChildren.add(thumb);
			thumb.setOnMousePressed(new javafx.event.EventHandler<MouseEvent>() {

				@Override
				public void handle(javafx.scene.input.MouseEvent me) {
					Object source = me.getSource();
					if (source instanceof StackPane) {
						StackPane thumb = (StackPane) source;
						double val = ((DoubleProperty) thumb.getProperties().get(VALUE)).get();
						getBehavior().thumbPressed(me, 0.0f);
						dragStart = thumb.localToParent(me.getX(), me.getY());
						preDragThumbPos = (val - getSkinnable().getMin())
								/ (getSkinnable().getMax() - getSkinnable().getMin());
					}
				}
			});
	        thumb.setOnMouseDragged(new EventHandler<MouseEvent>() {
	            @Override public void handle(MouseEvent me) {
	                Point2D cur = thumb.localToParent(me.getX(), me.getY());
	                double dragPos = (getSkinnable().getOrientation() == Orientation.HORIZONTAL)?
	                    cur.getX() - dragStart.getX() : -(cur.getY() - dragStart.getY());
	                    System.out.println(dragPos);
	                getBehavior().thumbDragged(me, preDragThumbPos + dragPos / trackLength);
	            }
	        });			
		}
		thumbs.clear();
		thumbs.addAll(rootChildren);
		rootChildren.add(0, track);
		getChildren().setAll(rootChildren);
	}	

	@Override
	protected void layoutChildren(final double x, final double y, final double w, final double h) {
		for (Node node : thumbs) {
			thumbWidth = snapSize(node.prefWidth(-1));
			thumbHeight = snapSize(node.prefHeight(-1));
			node.resize(thumbWidth, thumbHeight);
		}
		// we are assuming the is common radius's for all corners on the track
		double trackRadius = track.getBackground() == null ? 0
				: track.getBackground().getFills().size() > 0
						? track.getBackground().getFills().get(0).getRadii().getTopLeftHorizontalRadius() : 0;

		if (getSkinnable().getOrientation() == Orientation.HORIZONTAL) {
			double trackHeight = snapSize(track.prefHeight(-1));
			double trackAreaHeight = Math.max(trackHeight, thumbHeight);
			double startY = y + ((h - trackAreaHeight) / 2); // center slider in
																// available
																// height
																// vertically
			trackLength = snapSize(w - thumbWidth);
			trackStart = snapPosition(x + (thumbWidth / 2));
			double trackTop = (int) (startY + ((trackAreaHeight - trackHeight) / 2));
			thumbTop = (int) (startY + ((trackAreaHeight - thumbHeight) / 2));

			positionThumbs();
			// layout track
			track.resizeRelocate((int) (trackStart + trackRadius), trackTop, (int) (trackLength - 2 * trackRadius),
					trackHeight);
		} else {
			double trackWidth = snapSize(track.prefWidth(-1));
			double trackAreaWidth = Math.max(trackWidth, thumbWidth);
			double startX = x + ((w - trackAreaWidth) / 2); // center slider in
															// available width
															// horizontally
			trackLength = snapSize(h - thumbHeight);
			trackStart = snapPosition(y + (thumbHeight / 2));
			double trackLeft = (int) (startX + ((trackAreaWidth - trackWidth) / 2));
			thumbLeft = (int) (startX + ((trackAreaWidth - thumbWidth) / 2));

			positionThumbs();
			// layout track
			track.resizeRelocate(trackLeft, (int) (trackStart + trackRadius), trackWidth,
					(int) (trackLength - 2 * trackRadius));
		}
	}

	/**
	 * Called when ever either min, max or value changes, so thumb's layoutX, Y
	 * is recomputed.
	 */
	void positionThumbs() {
		MultiThumbSlider skinable = getSkinnable();
		boolean horizontal = skinable.getOrientation() == Orientation.HORIZONTAL;

		for (StackPane stackPane : thumbs) {
			DoubleProperty p = (DoubleProperty) stackPane.getProperties().get(VALUE);
			positionThumb(stackPane, p.get(), horizontal);
		}
	}
	
	/**
	 * 
	 */

	private void positionThumb(StackPane thumb, double pos, boolean horizontal) {
		MultiThumbSlider s = getSkinnable();
		final double endX = (horizontal) ? trackStart
				+ (((trackLength * ((pos - s.getMin()) / (s.getMax() - s.getMin()))) - thumbWidth / 2))
				: thumbLeft;
		final double endY = (horizontal) ? thumbTop
				: snappedTopInset() + trackLength
						- (trackLength * ((pos - s.getMin()) / (s.getMax() - s.getMin()))); // -
																										// thumbHeight/2
		thumb.setLayoutX(endX);
		thumb.setLayoutY(endY);
	}

	double minTrackLength() {
		return 2 * thumbPrefWidth;
	}

	@Override
	protected double computeMinWidth(double height, double topInset, double rightInset, double bottomInset,
			double leftInset) {
		if (getSkinnable().getOrientation() == Orientation.HORIZONTAL) {
			return (leftInset + minTrackLength() + thumbWidth + rightInset);
		} else {
			return (leftInset + thumbPrefWidth + rightInset);
		}
	}

	@Override
	protected double computeMinHeight(double width, double topInset, double rightInset, double bottomInset,
			double leftInset) {
		if (getSkinnable().getOrientation() == Orientation.HORIZONTAL) {
			return (topInset + thumbHeight + bottomInset);
		} else {
			return (topInset + minTrackLength() + thumbHeight + bottomInset);
		}
	}

	@Override
	protected double computePrefWidth(double height, double topInset, double rightInset, double bottomInset,
			double leftInset) {
		if (getSkinnable().getOrientation() == Orientation.HORIZONTAL) {
			return 140;
		} else {
			return leftInset + Math.max(thumbPrefWidth, track.prefWidth(-1)) + rightInset;
		}
	}

	@Override
	protected double computePrefHeight(double width, double topInset, double rightInset, double bottomInset,
			double leftInset) {
		if (getSkinnable().getOrientation() == Orientation.HORIZONTAL) {
			return topInset + Math.max(thumbHeight, track.prefHeight(-1)) + bottomInset;
		} else {
			return 140;
		}
	}

	@Override
	protected double computeMaxWidth(double height, double topInset, double rightInset, double bottomInset,
			double leftInset) {
		if (getSkinnable().getOrientation() == Orientation.HORIZONTAL) {
			return Double.MAX_VALUE;
		} else {
			return getSkinnable().prefWidth(-1);
		}
	}

	@Override
	protected double computeMaxHeight(double width, double topInset, double rightInset, double bottomInset,
			double leftInset) {
		if (getSkinnable().getOrientation() == Orientation.HORIZONTAL) {
			return getSkinnable().prefHeight(width);
		} else {
			return Double.MAX_VALUE;
		}
	}

}
