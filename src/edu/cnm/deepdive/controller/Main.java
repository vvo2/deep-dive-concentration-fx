package edu.cnm.deepdive.controller;

import edu.cnm.deepdive.Concentration;
import edu.cnm.deepdive.util.Resources;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;

/**
 * This is the controller with the {@link #handle(ActionEvent)} containing
 * the model for the game. This class also embodied the <code>main.fxml</code>
 * layout file. The , and {@link #newGame(ActionEvent)} starts with a random shuffle.
 */
public class Main implements EventHandler<ActionEvent> {

  /** Location of FXML layout file supported by this controller class.
   *
   * Additional field (ToggleButton one and two, int counter, string tileOneUrl and tileTwo)
   * is for the handle method.
   * */
  public static final String LAYOUT_PATH = Resources.LAYOUT_DIRECTORY + "main.fxml";

  @FXML
  private FlowPane tileTable;
  @FXML
  private Text totalStatus;
  @FXML
  private Text currentStatus;
  @FXML
  private MenuItem quit;

  private List<ToggleButton> tiles;
  private String totalStatusFormat;
  private String currentStatusFormat;
  private ToggleButton button1;
  private ToggleButton button2;
  private String urlValue1;
  private String urlValue2;
  private int count = 1;
  private int overCount = 1;
  private int match = 0;
  private int guess = 0;
  private int gameSet = 0;
  private int allGuess = 0;

  /**
   * Initialize the controller for the main view (layout). This includes loading
   * the set of tiles, each of which is a {@link ToggleButton} instance.
   *
   * @throws URISyntaxException if the path to the tile images is invalid.
   * @throws IOException        if a tile image file can't be read.
   */
  public void initialize() throws URISyntaxException, IOException {
    tiles = Tile.getTiles();
    for (ToggleButton tile : tiles) {
      tile.setOnAction(this);
    }
    currentStatusFormat = currentStatus.getText();
    totalStatusFormat = totalStatus.getText();
    newGame(null);
  }

  /**
   * This quit method is to exit the application.
   */
  public void quit() {
    Platform.exit();
    System.exit(0);
  }

  /**
   * This handle method contains the model for the game where the clicked
   * ToggleButton gets disable and stay disable if the two image string url matches.
   * The matching image is stays flipped back when its a match. When two image string
   * is not a match then the toggleButton is re-enable and the image disable.
   * @param event             This is the main event of the main
   */
  @Override
  public void handle(ActionEvent event) {
    ToggleButton dis = ((ToggleButton) event.getSource());
    if (count == 1) {
      if (button1 != null) {
        button1.setSelected(!button1.isSelected());
        button1 = null;
      }
      if (button2 != null) {
        button2.setSelected(!button2.isSelected());
        button2 = null;
      }
      dis.setDisable(true);
      urlValue1 = ((ImageView) dis.getGraphic()).getImage().getUrl();
      button1 = dis;
      count = 2;
    } else {
      urlValue2 = ((ImageView) dis.getGraphic()).getImage().getUrl();
      button2 = dis;
      guess++;
      updateDisplay();
      if (urlValue1.equals(urlValue2)) {
        button2.setDisable(true);
        match++;
        updateDisplay();
        //  winSound();
      } else {
        button1.setDisable(false);
      }
//      two.setSelected(false);
//      one.setSelected(false);
      count = 1;
    }
  }

  /**
   *newGame runs when the application starts
   *along with a shuffle from the Collections.
   * @param event
   */
  @FXML
  private void newGame(ActionEvent event) {
    tileTable.getChildren().clear();
    Collections.shuffle(tiles);
    for (ToggleButton tile : tiles) {
      tileTable.getChildren().add(tile);
    }
  }

  /**
   * This method update the game display text by calling the
   * currentStatus which contains the parameters
   */
  private void updateDisplay() {
    double avgGuess = (gameSet > 0) ? (double) allGuess / gameSet : 0;
    currentStatus.setText(String.format(currentStatusFormat, guess, match));
    totalStatus.setText(String.format(totalStatusFormat, gameSet, avgGuess));
  }
}
