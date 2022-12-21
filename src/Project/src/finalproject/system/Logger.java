package finalproject.system;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class Logger {
  private static Logger instance;
  private VBox textPanel;
  private ScrollPane scrollPanel;
  private boolean showSystemLog;

  private Logger() {
    showSystemLog = true;
  }

  public static Logger getInstance() {
    if (instance == null) instance = new Logger();
    return instance;
  }

  public void init(VBox panel, ScrollPane scroll) {
    if (textPanel == null) textPanel = panel;
    if (scrollPanel == null) scrollPanel = scroll;
  }

  public void logMessage(String msg) {
    Text t = new Text(msg);
    t.setFill(Color.BLACK);
    t.getStyleClass().add("logText");
    textPanel.getChildren().add(t);
    scrollPanel.setVvalue(1.0);
  }

  public void logSystemMessage(String msg) {
    if (!showSystemLog) return;
    Text t = new Text("[System] " + msg);
    t.setFill(Color.web("#8aa9d4"));
    t.getStyleClass().add("logText");
    textPanel.getChildren().add(t);
    scrollPanel.setVvalue(1.0);
  }

  public void logErrorMessage(String msg) {
    if (!showSystemLog) return;
    Text t = new Text("[Error] " + msg);
    t.setFill(Color.RED);
    t.getStyleClass().add("logText");
    textPanel.getChildren().add(t);
    scrollPanel.setVvalue(1.0);
  }

  public void toggleSystemLog() {
    showSystemLog = !showSystemLog;
  }
}
