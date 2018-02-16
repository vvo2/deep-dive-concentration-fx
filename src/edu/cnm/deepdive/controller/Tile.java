package edu.cnm.deepdive.controller;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import edu.cnm.deepdive.util.Resources;
import javafx.scene.layout.Background;

/**
 *
 */
public class Tile {

  private static final String IMAGE_REGEX = "^(?i)face_.*\\.png";
  private static final String BACKGROUND_PATH = Resources.IMAGE_DIRECTORY + "back.png";
  private static final String LAYOUT_PATH = Resources.LAYOUT_DIRECTORY + "tile.fxml";

  /**
   *
   * @return
   * @throws URISyntaxException
   * @throws IOException
   */
  public static List<ToggleButton> getTiles() throws URISyntaxException, IOException {
    ClassLoader classLoader = Tile.class.getClassLoader();
    Image[] faces = load(classLoader.getResource(Resources.IMAGE_DIRECTORY), IMAGE_REGEX);
    Image back = new Image(BACKGROUND_PATH);
    List<ToggleButton> tiles = new ArrayList<>();
    for (Image face : faces) {
      for (int i = 0; i < 2; i++) {
        FXMLLoader fxmlLoader = new FXMLLoader(classLoader.getResource(LAYOUT_PATH));
        ToggleButton tile = fxmlLoader.load();
        ImageView imageView = new ImageView();
        tile.setGraphic(imageView);
        imageView.imageProperty().bind(
            Bindings.when(tile.selectedProperty()).then(face).otherwise(back));
        tiles.add(tile);
      }
    }
    return tiles;
  }

  private static Image[] load(URL directory, String pattern) throws URISyntaxException {
    ClassLoader loader = Tile.class.getClassLoader();
    FilenameFilter filter = (d, f) -> f.toLowerCase().matches(pattern);
    Function<File, Image> reader = (f) -> {
      try {
        return new Image(f.toURI().toURL().toString());
      } catch (MalformedURLException ex) {
        throw new RuntimeException(ex);
      }
    };
    File imageDirectory = new File(directory.toURI());
    return Arrays.stream(imageDirectory.listFiles(filter)).map(reader).toArray(Image[]::new);
  }

}
