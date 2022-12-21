package finalproject.system;

import java.util.ArrayList;
import java.util.LinkedList;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
// import sun.java2d.pipe.SpanShapeRenderer;

public class StateManager {
  private static StateManager instance;
  public BooleanProperty isMapInitialized = new SimpleBooleanProperty(false);
  public BooleanProperty isHighlightEnabled = new SimpleBooleanProperty(true);
  public BooleanProperty isInWaypointSelection = new SimpleBooleanProperty(false);
  public BooleanProperty isInComputation = new SimpleBooleanProperty(false);
  public BooleanProperty isInSimulation = new SimpleBooleanProperty(false);
  public BooleanProperty isTileTextEnabled = new SimpleBooleanProperty(false);
  public BooleanProperty isGridDisplayEnabled = new SimpleBooleanProperty(false);
  public BooleanProperty isDangerFactorEnabled = new SimpleBooleanProperty(true);

  public Controller controller;
  private Tile currentMap;
  public LinkedList<Tile> currentWaypoints = new LinkedList<>();
  public ArrayList<Tile> currentPath;
  private int currentMapTileNum;

  private StateManager() {}

  public static StateManager getInstance() {
    if (instance == null) instance = new StateManager();
    return instance;
  }

  public void registerController(Controller c) {
    if (controller != null) return;
    controller = c;
  }

  public void registerMap(Tile startTile, int tileNum) {
    currentMap = startTile;
    currentMapTileNum = tileNum;
  }

  public Tile getCurrentMap() {
    return currentMap;
  }

  public int getCurrentMapTileNum() {
    return currentMapTileNum;
  }
}
