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
import java.util.HashMap;
import java.util.ResourceBundle;

import org.python.core.PyException;
import org.python.core.PySystemState;
import org.python.util.PythonInterpreter;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

public class FXMLController implements Initializable {

	@FXML
	private ImageView imagepane;
	@FXML
	private Accordion accordion_menu;
	@FXML
	private ScrollPane scrollpane;
	@FXML
	private TreeView<LabelNode> tree;

	HashMap<Path, TreeItem> nodes = new HashMap<Path, TreeItem>();
	private PythonInterpreter interpreter = new PythonInterpreter();

	public void initialize(URL url, ResourceBundle rb) {
		System.out.println("de.keithpaterson.veneer.FXMLController.initialize()" + this.imagepane);
		PySystemState.initialize();
		try {
			if (this.imagepane != null) {
				Image i = new Image(
						"file:///D:\\git\\osm2city-data\\tex.src\\de\\industrial\\facade_industrial_red_white_24x18m.jpg");
				this.imagepane.setImage(i);
			}
			TreeItem<LabelNode> rootNode = new TreeItem<LabelNode>();
//			rootNode.setValue(new LabelNode("Images"));

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
//			tree.setCellFactory(new Callback<TreeView<LabelNode>, TreeCell<LabelNode>>() {
//				@Override
//				public TreeCell<LabelNode> call(TreeView<LabelNode> p) {
//					return new TextFieldTreeCellImpl(FXMLController.this);
//				}
//			});
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
            tex.setValue(new LabelNode( p.toAbsolutePath().toString()) );
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
			TreeItem<LabelNode> treeItem = new TreeItem<LabelNode>(new LabelNode( p.getFileName().toString()));
			nodes.put(p, treeItem);
			// Directory
			node.getChildren().add(treeItem);
		}
	}

}
