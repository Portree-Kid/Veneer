package de.keithpaterson.veneer;

import java.io.PrintStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FXMLController
  implements Initializable
{
  @FXML
  private ImageView imagepane;
  @FXML
  private Accordion accordion_menu;
  @FXML
  private ScrollPane scrollpane;
  @FXML
  private TreeView tree;
  
  public void initialize(URL url, ResourceBundle rb)
  {
    System.out.println("de.keithpaterson.veneer.FXMLController.initialize()" + this.imagepane);
    try
    {
      if (this.imagepane != null)
      {
        Image i = new Image("file:///D:\\git\\osm2city-data\\tex.src\\de\\industrial\\facade_industrial_red_white_24x18m.jpg");
        this.imagepane.setImage(i);
      }
          TreeItem<String> rootNode = 
        new TreeItem<String>("Images");

    }
    
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
}
