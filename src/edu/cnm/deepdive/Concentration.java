package edu.cnm.deepdive;

import edu.cnm.deepdive.controller.Main;
import edu.cnm.deepdive.controller.Tile;
import edu.cnm.deepdive.util.Resources;
import java.lang.ModuleLayer.Controller;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Concentration extends Application {

  private static final String APP_TITLE_KEY = "appTitle";

  @Override
  public void start(Stage stage) throws Exception {
    ClassLoader classLoader = getClass().getClassLoader();
    ResourceBundle bundle = ResourceBundle.getBundle(Resources.UI_STRINGS_BASENAME);
    Parent root = new FXMLLoader(classLoader.getResource(Main.LAYOUT_PATH), bundle).load();
    Scene scene = new Scene(root);
    stage.setTitle(bundle.getString(APP_TITLE_KEY));
    stage.setResizable(false);
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }

}
