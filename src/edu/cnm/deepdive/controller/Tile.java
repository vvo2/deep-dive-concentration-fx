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

/**
 * Simple controller class supporting the <code>tile.fxml</code> layout. Static
 * methods are included for loading the full set of tile images, and creating
 * 2 instances of this class per image.
 */
public class Tile {

  private static final String IMAGE_REGEX = "^(?i)face_.*\\.png";
  private static final String BACKGROUND_PATH = Resources.IMAGE_DIRECTORY + "back.png";
  private static final String LAYOUT_PATH = Resources.LAYOUT_DIRECTORY + "tile.fxml";

  /**
   * Loads and returns a {@link List} of {@link ToggleButton} instances, 2 per
   * source image. Each <code>ToggleButton</code> has 2 images: a blank one for
   * the "back", and the loaded image for the "face".
   *
   * @return                    {@link List} of {@link ToggleButton} instances.
   * @throws URISyntaxException if the image directory is invalid.
   * @throws IOException        if an image file (or the <code>tile.fxml</code>
   *                            layout file) can't be read.
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

  /**
   *This method load the image, with exception for invalid file entries.
   *
   * @param directory         Directory for the image
   * @param pattern           Pattern of the string to match lowercase
   * @return                  Return URL directory and String pattern
   * @throws URISyntaxException     Throw a syntax exeception for invalid url
   */
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
