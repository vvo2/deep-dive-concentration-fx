package edu.cnm.deepdive.controller;

import edu.cnm.deepdive.util.Resources;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;

/**
 * This is the controller class for the view embodied by the
 * <code>main.fxml</code> layout file. To complete this class, the
 * implementations of the {@link #handle(ActionEvent)} and {@link
 * #newGame(ActionEvent)} methods must be completed. These methods (along with
 * any additional helper methods needed) will embody both the model of the
 * Concentration game, and the controller logic that connects the model to the
 * view.
 * <p>
 * A method must also be written to handle the Exit menu command.
 */
public class Main implements EventHandler<ActionEvent> {

  /** Location of FXML layout file supported by this controller class. */
  public static final String LAYOUT_PATH = Resources.LAYOUT_DIRECTORY + "main.fxml";

  @FXML
  private FlowPane tileTable;
  @FXML
  private Text totalStatus;
  @FXML
  private Text currentStatus;

  private List<ToggleButton> tiles;
  private String totalStatusFormat;
  private String currentStatusFormat;

  /**
   * Initialize the controller for the main view (layout). This includes loading
   * the set of tiles, each of which is a {@link ToggleButton} instance.
   * However, these tiles are not shuffled at this point; that should be done at
   * the start of every game.
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

  @Override
  public void handle(ActionEvent event) {
    // TODO Add logic for handling clicks on tiles.
  }

  @FXML
  private void newGame(ActionEvent event) {
    tileTable.getChildren().clear();
    for (ToggleButton tile : tiles) {
      tileTable.getChildren().add(tile);
    }
  }

}
