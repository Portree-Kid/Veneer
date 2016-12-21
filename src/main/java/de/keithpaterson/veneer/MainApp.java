package de.keithpaterson.veneer;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp
  extends Application
{
  public void start(Stage stage)
    throws Exception
  {
    Parent root = (Parent)FXMLLoader.load(getClass().getResource("/fxml/Scene.fxml"));
    
    Scene scene = new Scene(root);
    scene.getStylesheets().add("/styles/Styles.css");
    
    stage.setTitle("Java FX osm2city Texture Builder");
    stage.setScene(scene);
    stage.show();
  }
  
  public static void main(String[] args)
  {
    launch(args);
  }
}
