package finalproject.system;

import finalproject.*;
import finalproject.tiles.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Controller {
  @FXML
  VBox root;

  @FXML
  VBox textPanel;
  @FXML
  Pane mapPanel;
  @FXML
  HBox commandPanel;
  @FXML
  ScrollPane messagePanel;
  @FXML
  HBox interfacePanel;

  @FXML
  CheckMenuItem systemLogToggle;
  @FXML
  CheckMenuItem tileTextToggle;

  @FXML
  TextField healthInput;

  int tileSize = 100;
  int stroke = 0;
  ArrayList<Line> lineCache = new ArrayList<>();
  ArrayList<Transition> animationCache = new ArrayList<>();

  MediaPlayer mediaPlayer;
  MediaPlayer BGplayer;
  MediaPlayer deathPlayer;
  MediaPlayer yellMusic1Player;
  MediaPlayer yellMusic2Player;
  MediaPlayer yellMusic3Player;
  MediaPlayer yellMusic4Player;
  MediaPlayer arrivePlayer;
  MediaPlayer metroTransPlayer;
  MediaPlayer letsgoPlayer;

  Random rng = new Random(260877596);

  public void initialize() {
    System.out.println("init");
    StateManager.getInstance().registerController(this);

    systemLogToggle.selectedProperty().addListener((ov, old_val, new_val) -> {
      // show system log
      Logger.getInstance().toggleSystemLog();
      Logger.getInstance().logSystemMessage(systemLogToggle.getText() + " set to " + new_val);
    });

    tileTextToggle.selectedProperty().addListener((ov, old_val, new_val) -> {
      StateManager.getInstance().isTileTextEnabled.set(new_val);
      for (Node n : mapPanel.getChildren()) {
        if (n instanceof TileComponent) ((TileComponent) n).enableText(new_val);
      }
      Logger.getInstance().logSystemMessage(tileTextToggle.getText() + " set to " + new_val);
    });

    tileTextToggle.selectedProperty().set(StateManager.getInstance().isTileTextEnabled.get());

    Logger.getInstance().init(textPanel, messagePanel);

    mapPanel.prefWidthProperty().bind(root.widthProperty()); // bind width to its parent's width
    mapPanel.prefHeightProperty().bind(root.heightProperty().multiply(0.85)); // bind height to its parent's height
    interfacePanel.prefWidthProperty().bind(root.widthProperty());
    interfacePanel.prefHeightProperty().bind(root.heightProperty().multiply(0.15));
    commandPanel.prefWidthProperty().bind(((HBox) commandPanel.getParent()).widthProperty().multiply(0.5)); // bind width to its parent's width
    commandPanel.prefHeightProperty().bind(((HBox) commandPanel.getParent()).heightProperty()); // bind height to its parent's height
    messagePanel.prefWidthProperty().bind(((HBox) commandPanel.getParent()).widthProperty().multiply(0.5)); // bind width to its parent's width
    messagePanel.prefHeightProperty().bind(((HBox) commandPanel.getParent()).heightProperty()); // bind height to its parent's height

    StateManager.getInstance().isInWaypointSelection.addListener((ov, oldVal, newVal) -> {
      if (newVal) onAddingWaypoint();
      else doneAddingWaypoint();
    });

    root.onMouseClickedProperty().set(event -> {
      if (StateManager.getInstance().isInWaypointSelection.get() && event.getButton() == MouseButton.SECONDARY) {
        StateManager.getInstance().isInWaypointSelection.set(false);
      }
    });

    // sound effect set up
    String musicFile = "resource/BG_music.wav"; // For example
    Media sound = new Media(new File(musicFile).toURI().toString());
    BGplayer = new MediaPlayer(sound);
    BGplayer.setVolume(0.3);

    musicFile = "resource/death.wav"; // For example
    Media deathMuisc = new Media(new File(musicFile).toURI().toString());
    deathPlayer = new MediaPlayer(deathMuisc);
    deathPlayer.setVolume(0.7);

    musicFile = "resource/success.mp3"; // For example
    Media arrive = new Media(new File(musicFile).toURI().toString());
    arrivePlayer = new MediaPlayer(arrive);
    arrivePlayer.setVolume(0.3);

    musicFile = "resource/yell4.mp3"; // For example
    Media yellMusic4 = new Media(new File(musicFile).toURI().toString());
    yellMusic4Player = new MediaPlayer(yellMusic4);
    yellMusic4Player.setVolume(0.5);

    musicFile = "resource/yell3.mp3"; // For example
    Media yellMusic3 = new Media(new File(musicFile).toURI().toString());
    yellMusic3Player = new MediaPlayer(yellMusic3);
    yellMusic3Player.setVolume(0.5);

    musicFile = "resource/yell2.mp3"; // For example
    Media yellMusic2 = new Media(new File(musicFile).toURI().toString());
    yellMusic2Player = new MediaPlayer(yellMusic2);
    yellMusic2Player.setVolume(0.5);

    musicFile = "resource/yell1.mp3"; // For example
    Media yellMusic1 = new Media(new File(musicFile).toURI().toString());
    yellMusic1Player = new MediaPlayer(yellMusic1);
    yellMusic1Player.setVolume(0.5);

    musicFile = "resource/lets_go_mario.mp3"; // For example
    Media letsgo = new Media(new File(musicFile).toURI().toString());
    letsgoPlayer = new MediaPlayer(letsgo);
    letsgoPlayer.setVolume(0.5);

    musicFile = "resource/metro_switch.wav"; // For example
    Media metroTrans = new Media(new File(musicFile).toURI().toString());
    metroTransPlayer = new MediaPlayer(metroTrans);
    metroTransPlayer.setVolume(0.5);

    musicFile = "resource/flip.mp3"; // For example
    Media flipSound = new Media(new File(musicFile).toURI().toString());
    mediaPlayer = new MediaPlayer(flipSound);
    mediaPlayer.setVolume(1);
  }

  public void startSimulation() {
    if (!StateManager.getInstance().isMapInitialized.get()) return;
    if (StateManager.getInstance().currentPath == null) return;
    StateManager.getInstance().isInSimulation.set(true);
    Logger.getInstance().logSystemMessage("start simulation");

    ArrayList<MediaPlayer> yellSoundList = new ArrayList<MediaPlayer>(Arrays.asList(yellMusic1Player, yellMusic2Player, yellMusic3Player, yellMusic4Player, null, null, null));

    // spawn a agent that follows the current path based on the cost
    // setup animation
    int agentWidth = 50;
    int agentHeight = 50;
    Rectangle agent = new Rectangle(agentWidth, agentHeight);
    mapPanel.getChildren().add(agent);
    ArrayList<Tile> path = StateManager.getInstance().currentPath;

    // Setting Color and Stroke properties for the polygon
    Image img = new Image("file:./resource/hiker.png");
    agent.setFill(new ImagePattern(img));

    int currentHealth = Integer.parseInt(healthInput.getText());

    // Setting durations for the transitions
    Duration dur1 = Duration.millis(1000);
    Duration dur2 = Duration.millis(500);

    // Setting Rotate Transition
    RotateTransition rotate = new RotateTransition(dur2);
    rotate.setFromAngle(-20);
    rotate.setToAngle(20);
    rotate.setCycleCount(50);
    rotate.setAutoReverse(true);

    FadeTransition death = new FadeTransition(dur2);
    death.setFromValue(1.0f);
    death.setToValue(0.0f);
    death.setCycleCount(1);
    death.setAutoReverse(false);

    // Instantiating Sequential Transition class by passing the list of transitions into its
    // constructor
    int offset = (tileSize + stroke) / 2;
    SequentialTransition seqT = new SequentialTransition(agent);
    PauseTransition initPause = new PauseTransition(Duration.millis(500));
    seqT.getChildren().add(initPause);
    initPause.onFinishedProperty().set((status) -> {
      BGplayer.seek(Duration.ZERO);
      BGplayer.setCycleCount(10);
      BGplayer.play();
    });

    boolean dangerComputationEnabled = StateManager.getInstance().isDangerFactorEnabled.get();
    boolean safePath = true;
    for (int i = 0; i < path.size() - 1; i++) {
      TranslateTransition translate = new TranslateTransition(dur1);
      Tile t1 = path.get(i);
      Tile t2 = path.get(i + 1);

      // set transition
      translate.setFromX(t1.getTranslateX() + offset - agentWidth / 2);
      translate.setFromY(t1.getTranslateY() + offset - agentHeight / 2);
      translate.setToX(t2.getTranslateX() + offset - agentWidth / 2);
      translate.setToY(t2.getTranslateY() + offset - agentHeight / 2);
      translate.setCycleCount(1);
      translate.setAutoReverse(false);
      seqT.getChildren().add(translate);

      // set transition sound for metro
      if (t1.type == TileType.Metro && t2.type == TileType.Metro) {
        translate.statusProperty().addListener((state, oldVal, newVal) -> {
          if (newVal == Animation.Status.RUNNING) {
            metroTransPlayer.play();
          }
        });
      }

      // set pause
      PauseTransition pause = new PauseTransition(Duration.millis(500));
      seqT.getChildren().add(pause);

      if (dangerComputationEnabled) {
        // compute risk
        double damage = t2.damageCost;
        if (damage > 0) {
          currentHealth -= damage;
          FadeTransition takeD = new FadeTransition(Duration.millis(200));
          takeD.setToValue(0.0);
          takeD.setCycleCount(2);
          takeD.setAutoReverse(true);
          // play get hit sound
          takeD.statusProperty().addListener((status, oldVal, newVal) -> {
            if (newVal == Animation.Status.RUNNING) {
              int res = Math.abs(rng.nextInt()) % 7;
              if (res < 4) {
                yellSoundList.get(res).seek(Duration.ZERO);
                yellSoundList.get(res).play();
              }
            }
          });
          seqT.getChildren().add(takeD);
        }

        if (currentHealth <= 0) {
          // not safe path
          safePath = false;
          seqT.getChildren().add(death);
          break;
        }
      }
    }

    ParallelTransition parT = new ParallelTransition(agent, seqT, rotate);
    seqT.statusProperty().addListener((state, oldVal, newVal) -> {
      if (newVal == Animation.Status.STOPPED) {
        parT.stop();
        mapPanel.getChildren().remove(agent);
        StateManager.getInstance().isInSimulation.set(false);
      }
    });

    if (safePath) {
      seqT.setOnFinished((val) -> {
        Logger.getInstance().logMessage("Your agent has reached its destination safely.");
        //                    arrivePlayer.setVolume(10);
        arrivePlayer.seek(Duration.ZERO);
        arrivePlayer.play();
        BGplayer.stop();
      });
    } else {
      seqT.setOnFinished((val) -> {
        Logger.getInstance().logMessage("Your agent has died on its way....");
        deathPlayer.seek(Duration.ZERO);
        deathPlayer.play();
      });
    }

    seqT.statusProperty().addListener((state, oldVal, newVal) -> {
      if (newVal == Animation.Status.STOPPED) {
        BGplayer.stop();
      }
    });
    // playing the transition
    animationCache.add(parT);

    // add music
    letsgoPlayer.seek(Duration.ZERO);
    letsgoPlayer.play();
    parT.play();
  }

  public void resetAnimation() {
    for (Transition t : animationCache) t.stop();
    StateManager.getInstance().isInSimulation.set(false);
  }

  public void reset() {
    Logger.getInstance().logSystemMessage("reset");
    resetAnimation();
    resetWaypoints();
    StateManager.getInstance().currentPath = null;
    mapPanel.getChildren().clear();
    StateManager.getInstance().isMapInitialized.set(false);
  }

  public void resetPath() {
    Logger.getInstance().logSystemMessage("reset path");
    mapPanel.getChildren().removeAll(lineCache);
    resetAnimation();
    lineCache.clear();
  }

  public void resetWaypoints() {
    Logger.getInstance().logSystemMessage("reset waypoints");
    resetAnimation();
    for (Tile t : StateManager.getInstance().currentWaypoints) {
      t.setWaypoint(false);
    }
    StateManager.getInstance().currentWaypoints.clear();
  }

  public void exit() {
    System.exit(0);
  }

  public void drawMap1() {
    /*Map 1*/
    char[][] map = {{'s', 'p', 'p'}, {'d', 'm', 'd'}, {'p', 'p', 'e'},};

    drawMap(map);
  }

  public void drawMap2() {
    /*Map 2*/
    char[][] map = {{'s', 'd', 'd', 'p', 'm'}, {'d', 'd', 'm', 'p', 'm'}, {'d', 'p', 'd', 'd', 'm'}, {'p', 'd', 'p', 'd', 'p'}, {'d', 'p', 'p', 'p', 'e'},};
    drawMap(map);
  }

  public void drawMap3() {
    /*Map 2*/
    char[][] map = {{'s', 'd', 'd', 'M', 'd'}, {'d', 'd', 'm', 'p', 'p'}, {'p', 'm', 'd', 'd', 'p'}, {'m', 'M', 'p', 'd', 'd'}, {'p', 'd', 'p', 'p', 'e'},};
    drawMap(map);
  }

  public void drawMap4() {
    /*Map 2*/
    char[][] map = {{'p', 'p', 'r', 'r', 'x', 'x', 'x', 'x', 'x'}, {'f', 's', 'r', 'r', 'd', 'd', 'p', 'p', 'p'}, {'d', 'p', 'p', 'x', 'x', 'p', 'x', 'd', 'r'}, {'d', 'd', 'p', 'p', 'p', 'r', 'r', 'd', 'p'}, {'f', 'd', 'p', 'x', 'x', 'r', 'x', 'r', 'e'},};
    drawMap(map);
  }

  public void onBFSButtonClick() {
    if (!StateManager.getInstance().isMapInitialized.get()) return;
    if (StateManager.getInstance().isInSimulation.get()) return;
    StateManager.getInstance().isDangerFactorEnabled.set(false);
    StateManager.getInstance().isInComputation.set(true);
    resetPath();

    //        System.out.println(StateManager.getInstance().getCurrentMap());
    Tile parentNode = StateManager.getInstance().getCurrentMap();
    //        System.out.println(parentNode.getCoord());
    ArrayList<Tile> path = GraphTraversal.BFS(parentNode);

    // visualize the path
    visualizePath(path);
    StateManager.getInstance().isInComputation.set(false);
  }

  public void onDFSButtonClick() {
    if (!StateManager.getInstance().isMapInitialized.get()) return;
    if (StateManager.getInstance().isInSimulation.get()) return;
    StateManager.getInstance().isDangerFactorEnabled.set(false);
    StateManager.getInstance().isInComputation.set(true);
    resetPath();

    System.out.println(StateManager.getInstance().getCurrentMap());
    Tile parentNode = StateManager.getInstance().getCurrentMap();
    System.out.println(parentNode.getNodeID());
    ArrayList<Tile> path = GraphTraversal.DFS(parentNode);

    // visualize the path
    visualizePath(path);
    StateManager.getInstance().isInComputation.set(false);
  }

  public void onFastestPathButtonClick() {
    if (!StateManager.getInstance().isMapInitialized.get()) return;
    if (StateManager.getInstance().isInSimulation.get()) return;
    StateManager.getInstance().isDangerFactorEnabled.set(true);
    StateManager.getInstance().isInComputation.set(true);
    resetPath();

    ArrayList<Tile> path;
    PathFindingService pf = new FastestPath(StateManager.getInstance().getCurrentMap());

    LinkedList<Tile> waypoints = StateManager.getInstance().currentWaypoints;
    path = pf.findPath(StateManager.getInstance().getCurrentMap(), waypoints);

    // visualize the path if available
    visualizePath(path);
    StateManager.getInstance().isInComputation.set(false);
  }

  public void onShortestPathButtonClick() {
    if (!StateManager.getInstance().isMapInitialized.get()) return;
    if (StateManager.getInstance().isInSimulation.get()) return;
    StateManager.getInstance().isDangerFactorEnabled.set(true);
    StateManager.getInstance().isInComputation.set(true);
    resetPath();

    // main logic for calling pathfinding algorithm
    ArrayList<Tile> path;
    PathFindingService pf = new ShortestPath(StateManager.getInstance().getCurrentMap());

    // USE THIS TO TEST LEVEL 4
    path = pf.findPath(StateManager.getInstance().getCurrentMap());

    // USE THIS TO TEST LEVEL 5
    // LinkedList<Tile> waypoints = StateManager.getInstance().currentWaypoints;
    // path = pf.findPath(StateManager.getInstance().getCurrentMap(), waypoints);

    // visualize the path if available
    visualizePath(path);
    StateManager.getInstance().isInComputation.set(false);
  }

  public void onSafestPathButtonClick() {
    if (!StateManager.getInstance().isMapInitialized.get()) return;
    if (StateManager.getInstance().isInSimulation.get()) return;
    StateManager.getInstance().isDangerFactorEnabled.set(true);
    StateManager.getInstance().isInComputation.set(true);
    resetPath();

    // main logic for calling pathfinding algorithm
    ArrayList<Tile> path;
    PathFindingService pf = new SafestShortestPath(StateManager.getInstance().getCurrentMap(), Integer.parseInt(healthInput.getText()));

    LinkedList<Tile> waypoints = StateManager.getInstance().currentWaypoints;
    path = pf.findPath(StateManager.getInstance().getCurrentMap(), waypoints);

    // visualize the path if available
    visualizePath(path);
    StateManager.getInstance().isInComputation.set(false);
  }

  private void visualizePath(ArrayList<Tile> path) {
    if (path != null && path.size() > 1) {
      int offset = (tileSize + stroke) / 2;
      int dist = 0, time = 0;
      double risk = 0;
      for (int i = 0; i < path.size() - 1; i++) {
        // draw a link between two tiles
        Tile t1 = path.get(i);
        Tile t2 = path.get(i + 1);
        Line l = new Line(t1.getTranslateX() + offset, t1.getTranslateY() + offset, t2.getTranslateX() + offset, t2.getTranslateY() + offset);
        l.getStyleClass().add("pathline");
        mapPanel.getChildren().add(l);
        lineCache.add(l);

        // display stats
        dist += t1.distanceCost;
        time += t1.timeCost;
        risk += t1.damageCost;
      }
      Logger.getInstance().logMessage("Distance cost: " + dist);
      Logger.getInstance().logMessage("Time cost: " + time);
      Logger.getInstance().logMessage("Risk cost: " + risk);
      StateManager.getInstance().currentPath = path;
    } else {
      Logger.getInstance().logMessage("No path found!");
    }
  }

  public void onAddWaypointButtonClick() {
    StateManager.getInstance().isInWaypointSelection.set(true);
  }

  public void onAddingWaypoint() {
    Logger.getInstance().logSystemMessage("Adding a waypoint");
    for (Node n : commandPanel.getChildren()) {
      n.setDisable(true);
    }
  }

  public void addWaypoint(Tile t) {
    if (t.isStart || t.isDestination) {
      Logger.getInstance().logMessage("Can't assign waypoint to the start or the end.");
      return;
    }
    if (!t.isWalkable()) {
      Logger.getInstance().logMessage("Can't assign waypoint to non-walkable tile.");
      return;
    }
    if (StateManager.getInstance().currentWaypoints.contains(t)) return;
    StateManager.getInstance().currentWaypoints.add(t);
    t.setWaypoint(true);
  }

  public void doneAddingWaypoint() {
    Logger.getInstance().logSystemMessage("Done adding a waypoint");
    for (Node n : commandPanel.getChildren()) {
      n.setDisable(false);
    }
  }

  private void drawMap(char[][] map) {
    if (mapPanel.getChildren().size() > 0) {
      reset();
    }
    ArrayList<Tile> metroNeighbours = new ArrayList<>();

    int sizeX = map[0].length;
    int sizeY = map.length;
    int startX = sizeX / 2;
    int startY = sizeY / 2;

    int number = 0;
    double width = mapPanel.getWidth();
    double height = mapPanel.getHeight();

    int midX = (int) (width / 2) - tileSize / 2;
    int midY = (int) (height / 2) - tileSize / 2;

    int x = 0, y = 0;
    Tile map2 = null;
    for (int j = -startY; j < startY + 1; j++) {
      for (int i = -startX; i < startX + 1; i++) {
        Tile tile;
        switch (map[x][y]) {
          case 's':
            tile = new FacilityTile();
            map2 = tile;
            tile.isStart = true;
            break;
          case 'e':
            tile = new FacilityTile();
            tile.isDestination = true;
            break;
          case 'd':
            tile = new DesertTile();
            break;
          case 'f':
            tile = new FacilityTile();
            break;
          case 'p':
            tile = new PlainTile();
            break;
          case 'r':
            tile = new ZombieInfectedRuinTile();
            break;
          case 'M':
            tile = new MetroTile();
            metroNeighbours.add(tile);
            break;

          default:
            tile = new MountainTile();
        }

        tile.initComponent(midX + i * tileSize, midY + j * tileSize, tileSize - stroke, tileSize - stroke);
        tile.setNodeID(number);
        tile.graphCoord(x, y);
        number += 1;
        mapPanel.getChildren().add(tile);

        y++;
        if (y >= sizeX) y = 0;
      }
      x++;
      if (x >= sizeY) x = 0;
    }

    // add neighbors
    for (int i = 0; i < mapPanel.getChildren().size(); i++) {
      Tile tile = (Tile) mapPanel.getChildren().get(i);
      if ((i > 0 && i < sizeX) || (i >= sizeX && i % sizeX != 0))
        tile.addNeighbor((Tile) mapPanel.getChildren().get(i - 1)); // left neighbor
      if (i >= sizeX) {
        tile.addNeighbor((Tile) mapPanel.getChildren().get(i - sizeX)); // top neighbor
      }
      if (tile.type == TileType.Metro) {
        for (int j = 0; j < metroNeighbours.size(); j++) {
          if (metroNeighbours.get(j) != tile) {
            ((MetroTile) tile).fixMetro(metroNeighbours.get(j));
            if (((MetroTile) tile).metroTimeCost < 100 && ((MetroTile) tile).metroDistanceCost < 100) {
              tile.addNeighbor(metroNeighbours.get(j));
            }
          }
        }
      }
    }

    if (map2 == null) Logger.getInstance().logErrorMessage("No root for map 2 found!!!!");
    StateManager.getInstance().registerMap(map2, sizeX * sizeY);

    // start animation
    // play sound
    mediaPlayer.seek(Duration.ZERO);
    mediaPlayer.play();

    ParallelTransition entryAnimation = new ParallelTransition();
    for (Node n : mapPanel.getChildren()) {
      double xc = n.getTranslateX();
      double yc = n.getTranslateY();
      double diffx = xc - midX;
      double diffy = yc - midY;

      if (diffx == 0 && diffy == 0) {
        diffx = -100;
        diffy = -100;
      }

      TranslateTransition moveAnim = new TranslateTransition(Duration.millis(rng.nextDouble() * 500 + 500), n);
      moveAnim.setFromX(xc + diffx * 10);
      moveAnim.setFromY(yc + diffy * 10);
      moveAnim.setToX(xc);
      moveAnim.setToY(yc);
      moveAnim.setCycleCount(1);

      entryAnimation.getChildren().add(moveAnim);
    }
    entryAnimation.play();
    entryAnimation.statusProperty().addListener((state, oldVal, newVal) -> {
      if (newVal == Animation.Status.STOPPED) {
        StateManager.getInstance().isMapInitialized.set(true);
      }
    });
  }
}
