package de.keithpaterson.veneer;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.EventType;
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

public class FXMLController
        implements Initializable {

    @FXML
    private ImageView imagepane;
    @FXML
    private Accordion accordion_menu;
    @FXML
    private ScrollPane scrollpane;
    @FXML
    private TreeView tree;
    
    HashMap<Path, TreeItem> nodes = new HashMap<Path, TreeItem>();

    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("de.keithpaterson.veneer.FXMLController.initialize()" + this.imagepane);
        try {
            if (this.imagepane != null) {
                Image i = new Image("file:///D:\\git\\osm2city-data\\tex.src\\de\\industrial\\facade_industrial_red_white_24x18m.jpg");
                this.imagepane.setImage(i);
            }
            TreeItem<String> rootNode
                    = new TreeItem<String>("Images");

            Path root = Paths.get("D:\\git\\osm2city-data\\tex.src");

            try {
                Files.walkFileTree(root, new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        return super.visitFile(file, attrs); //To change body of generated methods, choose Tools | Templates.
                    }

                });
            } catch (Exception e) {
            }

            nodes.put(root, rootNode);
                    
            Files.walk(root)
                    .filter(Files::isReadable)
                    .forEach(this::addFile);


            tree.setEditable(true);
            tree.setCellFactory(new Callback<TreeView<String>, TreeCell<String>>() {
                @Override
                public TreeCell<String> call(TreeView<String> p) {
                    return new TextFieldTreeCellImpl(FXMLController.this);
                }
            });
            tree.setRoot(rootNode);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    public void addFile(Path p ){
        System.out.println(p);
        TreeItem<String> treeItem = new TreeItem<String>(p.getFileName().toString());
        TreeItem node = nodes.get(p.getParent());
        if(node==null)
            return;
        
        node.getChildren().add(treeItem);        
        nodes.put(p, treeItem);
    }
}
