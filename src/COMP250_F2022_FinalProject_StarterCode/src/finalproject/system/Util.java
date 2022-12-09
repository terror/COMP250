package finalproject.system;

import javafx.scene.control.Button;

public class Util {
  static final int BUTTON_PRE_WIDTH = 200;
  static final int BUTTON_PRE_HEIGHT = 200;

  public static Button createButton(String name, int width, int height, int x, int y) {
    Button b = new Button(name);
    b.setTranslateX(x);
    b.setTranslateY(y);
    b.setPrefSize(width, height);
    b.setMaxSize(1000, 500);
    return b;
  }

  public static Button createButton(String name, int x, int y) {
    Button b = new Button(name);
    b.setTranslateX(x);
    b.setTranslateY(y);
    b.setPrefSize(BUTTON_PRE_WIDTH, BUTTON_PRE_HEIGHT);
    b.setMaxSize(1000, 500);
    return b;
  }
}
