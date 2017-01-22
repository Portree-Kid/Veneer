package de.keithpaterson.veneer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import org.python.core.PyException;
import org.python.core.PyFloat;
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.core.PySystemState;
import org.python.util.PythonInterpreter;

import de.keithpaterson.javafx.controls.slider.MultiThumbSlider;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import textures.texture.Texture;

public class FXMLController implements Initializable {

	@FXML
	private ImageView imagepane;
	@FXML
	private Accordion accordion_menu;
	@FXML
	private ScrollPane scrollpane;
	@FXML
	private MultiThumbSlider slider_left;
	@FXML
	private MultiThumbSlider slider_top;
	@FXML
	private TreeView<LabelNode> tree;

	HashMap<Path, TreeItem> nodes = new HashMap<Path, TreeItem>();
	private PythonInterpreter interpreter = new PythonInterpreter();

	public void initialize(URL url, ResourceBundle rb) {
		System.out.println("de.keithpaterson.veneer.FXMLController.initialize()" + this.imagepane);
		PySystemState.initialize();
		try {
//			if (this.imagepane != null) {
//				Image i = new Image(
//						"file:///D:\\git\\osm2city-data\\tex.src\\de\\industrial\\facade_industrial_red_white_24x18m.jpg");
//				this.imagepane.setImage(i);
//			}
			EventHandler<MouseEvent> mouseEventHandle = (MouseEvent event) -> {
				handleMouseClicked(event);
			};

			scrollpane.viewportBoundsProperty().addListener(new ChangeListener<Bounds>() {

				@Override
				public void changed(ObservableValue<? extends Bounds> observable, Bounds oldValue,
						Bounds newValue) {
					System.out.println(newValue);
					
				}
			});
			ChangeListener<Number> hlistener = new ChangeListener<Number>() {

				@Override
				public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
					slider_top.setPrefWidth(newValue.doubleValue());
					double not_visible = imagepane.getBoundsInParent().getWidth() - scrollpane.getViewportBounds().getWidth();
					System.out.println(newValue.doubleValue());
					double minX = not_visible*newValue.doubleValue();
					double maxX = not_visible - minX;
					System.out.println(minX + "\t" + maxX );
					slider_top.setMin((int) minX);
					slider_top.setMax((int) maxX);
				}
			};
			scrollpane.hvalueProperty().addListener(hlistener);
//			scrollpane.widthProperty().addListener(hlistener);
			ChangeListener<Number> vlistener = new ChangeListener<Number>() {

				@Override
				public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
					slider_left.setPrefHeight(newValue.doubleValue());
				}
			};
			scrollpane.heightProperty().addListener(vlistener);
			tree.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEventHandle);

			TreeItem<LabelNode> rootNode = new TreeItem<LabelNode>();
			// rootNode.setValue(new LabelNode("Images"));

			Path root = Paths.get("D:\\git\\osm2city-data\\tex.src");

			try {

				Files.walkFileTree(root, new SimpleFileVisitor<Path>() {
					@Override
					public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

						return super.visitFile(file, attrs); // To change body
																// of generated
																// methods,
																// choose Tools
																// | Templates.
					}

				});
			} catch (Exception e) {
			}

			nodes.put(root, rootNode);

			Files.walk(root).filter(p -> p.getFileName().toString().endsWith("py") || p.toFile().isDirectory())
					.forEach(this::addFile);

			tree.setEditable(true);
			// tree.setCellFactory(new Callback<TreeView<LabelNode>,
			// TreeCell<LabelNode>>() {
			// @Override
			// public TreeCell<LabelNode> call(TreeView<LabelNode> p) {
			// return new TextFieldTreeCellImpl(FXMLController.this);
			// }
			// });
			tree.setRoot(rootNode);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addFile(Path p) {
		System.out.println(p);
		TreeItem node = nodes.get(p.getParent());
		if (node == null)
			return;

		if (p.toFile().isFile()) {

			TextureFileTreeItem tex = new TextureFileTreeItem();
			tex.setValue(new LabelNode(p.getFileName().toString()));
			node.getChildren().add(tex);

			try {
				interpreter.set("facades", new NodeAdder(tex));
				interpreter.execfile(new FileInputStream(p.toAbsolutePath().toString()));
				interpreter.close();
			} catch (final PyException pyException) {
				pyException.printStackTrace();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			TreeItem<LabelNode> treeItem = new TreeItem<LabelNode>(new LabelNode(p.getFileName().toString()));
			nodes.put(p, treeItem);
			// Directory
			node.getChildren().add(treeItem);
		}
	}

	private void handleMouseClicked(MouseEvent event) {
		Node node = event.getPickResult().getIntersectedNode();
		// Accept clicks only on node cells, and not on empty spaces of the
		// TreeView
		if (node instanceof Text || (node instanceof TreeCell && ((TreeCell) node).getText() != null)) {
			Object value = ((TreeItem) tree.getSelectionModel().getSelectedItem()).getValue();
			if (value instanceof LabelNode) {
				LabelNode label = (LabelNode) value;
				System.out.println("Node click: " + label);
			}
			if (value instanceof Texture) {
				Texture t = (Texture) value;
				System.out.println("Node click: " + t.getName().getString());
				Image i = new Image("file:///D:\\git\\osm2city-data\\tex.src\\" + t.getName().getString());
				this.imagepane.setImage(i);
				ArrayList<SimpleDoubleProperty> hpos = new ArrayList<>();
				if (t.getH_cuts() != null) {
					for (PyObject hcut : t.getH_cuts().getArray()) {
						hpos.add(new SimpleDoubleProperty((double) ((PyInteger) hcut).getValue()));
					}
				}
				this.slider_top.getThumbPositions().setAll(hpos);
				ArrayList<SimpleDoubleProperty> vpos = new ArrayList<>();
				if (t.getV_cuts() != null) {
					for (PyObject hcut : t.getV_cuts().getArray()) {
						vpos.add(new SimpleDoubleProperty((double) ((PyInteger) hcut).getValue()));
					}
				}
				this.slider_left.getThumbPositions().setAll(vpos);
			}

		}
	}
}
