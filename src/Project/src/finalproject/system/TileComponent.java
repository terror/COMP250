package finalproject.system;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class TileComponent extends StackPane {

  private Rectangle background;
  private Text text;
  private Text displayText;
  private VBox textHolder;
  private VBox displayTextHolder;
  private Color bgColor;
  public TileType type;

  public TileComponent() {
    hoverProperty()
        .addListener(
            (ov, old_val, new_val) -> {
              if (StateManager.getInstance().isHighlightEnabled.get()) {
                if (new_val) getMouseHovered();
                else getMouseUnhovered();
              }
            });
  }

  public void initComponent(int x, int y, int width, int height) {
    translateXProperty().set(x);
    translateYProperty().set(y);

    // rectangle background property
    background = new Rectangle(0, 0, width, height);
    //        background.setStyle(" -fx-stroke: black;-fx-stroke-width: 0;");

    // optional text property
    text = new Text("Label");
    textHolder = new VBox();
    text.setWrappingWidth(width / 2);
    text.setTextAlignment(TextAlignment.LEFT);
    setAlignment(text, Pos.TOP_LEFT);
    textHolder.setVisible(StateManager.getInstance().isTileTextEnabled.get());
    text.setFill(Color.BLACK);
    setAlignment(textHolder, Pos.TOP_LEFT);
    textHolder.setMaxSize(1, 1);
    textHolder.translateXProperty().set(10);
    textHolder.translateYProperty().set(10);
    textHolder.setBackground(
        new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
    textHolder.getChildren().add(text);

    displayText = new Text("");
    displayTextHolder = new VBox();
    displayText.setWrappingWidth(width / 3);
    displayText.setTextAlignment(TextAlignment.RIGHT);
    displayText.setFill(Color.BLACK);
    setAlignment(displayTextHolder, Pos.TOP_RIGHT);
    displayTextHolder.setMaxSize(1, 1);
    displayTextHolder.translateXProperty().set(-10);
    displayTextHolder.translateYProperty().set(10);
    displayTextHolder.setBackground(
        new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
    displayTextHolder.getChildren().add(displayText);
    displayTextHolder.setVisible(false);

    update();

    getChildren().addAll(background, textHolder, displayTextHolder);
  }

  public String getText() {
    return text.getText();
  }

  public void setText(String t) {
    displayText.setText(t);
    displayTextHolder.setVisible(true);
  }

  public void resetText() {
    displayText.setText("");
    displayTextHolder.setVisible(false);
  }

  public void enableText(boolean b) {
    textHolder.setVisible(b);
  }

  private void getMouseHovered() {
    // Logger.getInstance().logSystemMessage("Mouse hovered");
    background.setStyle("-fx-stroke: red;-fx-stroke-width: 0;"); // highlight tile
    if (!StateManager.getInstance().isTileTextEnabled.get()) textHolder.setVisible(true);
  }

  private void getMouseUnhovered() {
    background.setStyle("-fx-stroke: black;-fx-stroke-width: 0;"); // highlight tile
    if (!StateManager.getInstance().isTileTextEnabled.get()) textHolder.setVisible(false);
  }

  public TileType getTileType() {
    return type;
  }

  protected void update() {
    //        System.out.println(type);
    if (type == null) {
      Logger.getInstance().logErrorMessage("Seems that you haven't set up everything properly.");
      throw new NullPointerException();
    }
    Image image = null;
    switch (type) {
      case Facility:
        // background.getStyleClass().add("facilityTile");
        image = new Image("file:./resource/factory.png");
        bgColor = Color.ROSYBROWN;
        text.setText("Facility");
        break;
      case Moutain:
        // background.getStyleClass().add("mountainTile");
        image = new Image("file:./resource/mountain.png");
        bgColor = Color.GRAY;
        text.setText("Moutain");
        break;
      case Desert:
        // background.getStyleClass().add("desertTile");
        image = new Image("file:./resource/desert.png");
        bgColor = Color.YELLOW;
        text.setText("Desert");
        break;
      case ZombieInfectedRuin:
        // background.getStyleClass().add("ruinTile");
        image = new Image("file:./resource/zombie2.png");
        bgColor = Color.DARKRED;
        text.setText("Zombie infected ruin");
        break;
      case Plain:
        // background.getStyleClass().add("plainTile");
        image = new Image("file:./resource/plains.png");
        bgColor = Color.LIGHTGREEN;
        text.setText("Plain");
        break;
      case Metro:
        // background.getStyleClass().add("metroTile");
        image = new Image("file:./resource/metro.png");
        bgColor = (Color.LIGHTBLUE);
        text.setText("Metro");
        break;
      default:
        image = new Image("file:./resource/plains.png");
        break;
    }
    //        background.setFill(bgColor);
    background.setFill(new ImagePattern(image));
  }

  public void setWaypoint(boolean b) {
    if (b) {
      setText("W" + StateManager.getInstance().currentWaypoints.size());
    } else {
      resetText();
    }
  }
}
