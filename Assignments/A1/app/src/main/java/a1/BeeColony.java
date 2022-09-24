package a1;

/*
 * Main game loop
 * Author: Lancer Guo
 */

import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import javax.swing.BoxLayout;
import javax.swing.Timer;

enum EnumBeeType {
  busyBee,
  angryBee,
  tankyBee
}

public class BeeColony extends Frame {

  private static final int RES_X = 900;
  private static final int RES_Y = 600;

  private static final int BUSYBEE_COST = 2;
  private static final int ANGRYBEE_COST = 1;
  private static final int TANKYBEE_COST = 3;

  private static final int ANGRYBEE_ATTACK = 1;
  private static final int TANKYBEE_ATTACK = 1;
  private static final int TANKYBEE_ARMOR = 1;

  private static final int HORNET_ATTACK = 1;
  private static final int HORNET_HEALTH = 5;

  static final int STARTING_FOOD = 5;
  static final int FOOD_PER_TURN = 1;

  private boolean _inSelection = false;
  private EnumBeeType _selectedBeeType;

  private int canvasX;
  private int canvasY;
  private int canvasWidth;
  private int canvasHeight;
  private static GameCanvas _boardCanvas;
  private static Label _msgPanel;
  private static Label _foodPanel;
  private static TextArea _logPanel;
  private static Timer tick;
  private static Button busyBeeBtn;
  private static Button angryBeeBtn;
  private static Button tankyBeeBtn;
  private static Button startBtn;
  private static Button endBtn;
  private static Button spawnBtn;

  private long _frameCount;
  private int _timePerFrame = 5000; // 5 seconds per frame, increase to slow down the game
  private boolean _gameOver;

  private int _totalFood;

  private LinkedList<Insect> deadInsects;
  private static Map map;
  private static LinkedList<Hornet> hornets;
  private static LinkedList<HoneyBee> bees;
  private static StringBuffer logBuffer;

  public BeeColony() {
    initUI();
    initGame();
  }

  /*************************** Main ***************************/
  public static void main(String args[]) {
    BeeColony game = new BeeColony();
  }

  /****************** Game Logic ******************/

  private void initMap() {
    map = new Map(canvasWidth, canvasHeight);
    if (map.build()) {
      map.draw(_boardCanvas);
    } else {
      endGame();
      System.out.println("Cannot initilize map");
    }
  }

  private void initGame() {
    _frameCount = 0;
    _gameOver = true;

    // update and show food
    _totalFood = STARTING_FOOD;
    UpdateFood(0);

    // disable and enable UI
    busyBeeBtn.setEnabled(false);
    angryBeeBtn.setEnabled(false);
    tankyBeeBtn.setEnabled(false);
    spawnBtn.setEnabled(false);
    startBtn.setEnabled(true);
    endBtn.setEnabled(false);

    initMap();

    deadInsects = new LinkedList<Insect>();
    hornets = new LinkedList<Hornet>();
    bees = new LinkedList<HoneyBee>();
    logBuffer = new StringBuffer();
  }

  private void startGame() {
    if (_gameOver == false) return;

    _gameOver = false;

    toggleInput(true);

    tick =
        new Timer(
            _timePerFrame,
            new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                _frameCount++;

                updateUI();
                simulate();
                render();
              }
            });
    tick.start();
  }

  private void endGame() {
    if (_gameOver == true) return;
    tick.stop();
    resetMap();
    updateUI();
    _boardCanvas.repaint();

    _gameOver = true;
    _frameCount = 0;
    toggleInput(false);
  }

  // spawn one hornet
  private void spawnHornet() {
    // spawn hornets at the nest
    hornets.add(new Hornet(map.getNestTile(), HORNET_HEALTH, HORNET_ATTACK));
    logMessage("New hornet is spawned!\n");
  }

  // game logic
  private void simulate() {
    // TODO: simulate the map based on the rule and update map
    logMessage("\n************* Turn " + _frameCount + " *************\n");

    // for each bees, do actions
    logMessage("Bees start taking actions!\n");
    for (HoneyBee bee : bees) {
      bee.takeAction();
    }

    // update food
    int foodFromBee = 0;
    for (Tile tile : map.getAllPath()) {
      foodFromBee += tile.collectFood();
    }
    int food = FOOD_PER_TURN + foodFromBee;
    logMessage("Bees collected " + food + " food!\n");
    UpdateFood(food);

    // for each hornets, do actions
    logMessage("Hornets start taking actions!\n");
    for (Hornet hornet : hornets) {
      hornet.takeAction();
    }

    // collect dead insects
    collectDeadInsects();

    UpdateLog();
  }

  private void render() {
    _boardCanvas.repaint();
  }

  private Tile calculateSpawnLocation(int x, int y) {
    return map.getTileAtLocation(x, y);
  }

  private boolean spawnBee(Tile tileToSpawn) {
    if (_selectedBeeType == null) return false;

    if (!tileToSpawn.isOnThePath()) {
      _inSelection = false;
      _selectedBeeType = null;
      return false;
    }

    switch (_selectedBeeType) {
      case busyBee:
        System.out.println("Spawn busy bee");
        if (_totalFood >= BUSYBEE_COST) {
          UpdateFood(-BUSYBEE_COST);
          bees.add(new BusyBee(tileToSpawn));
          return true;
        }
        break;
      case angryBee:
        System.out.println("Spawn angry bee");
        if (_totalFood >= ANGRYBEE_COST) {
          UpdateFood(-ANGRYBEE_COST);
          bees.add(new AngryBee(tileToSpawn, ANGRYBEE_ATTACK));
          return true;
        }
        break;
      case tankyBee:
        System.out.println("Spawn tanky bee");
        if (_totalFood >= TANKYBEE_COST) {
          UpdateFood(-TANKYBEE_COST);
          bees.add(new TankyBee(tileToSpawn, TANKYBEE_ATTACK, TANKYBEE_ARMOR));
          return true;
        }
        break;
      default:
        break;
    }

    UpdateMessage("You do not have enough food!");
    return false;
  }

  private void updateUI() {
    if (_inSelection) {
      spawnBtn.setEnabled(false);
    } else {
      spawnBtn.setEnabled(true);
    }

    if (_gameOver) {
      startBtn.setEnabled(true);
      endBtn.setEnabled(false);
    } else {
      endBtn.setEnabled(true);
      startBtn.setEnabled(false);
    }
  }

  private void collectDeadInsects() {
    for (HoneyBee bee : bees) {
      if (bee.getHealth() <= 0) {
        deadInsects.add(bee);
        logMessage("A bee died");
      }
    }
    for (Hornet hornet : hornets) {
      if (hornet.getHealth() <= 0) {
        deadInsects.add(hornet);
        logMessage("A hornet died");
      }
    }
    for (Insect insect : deadInsects) {
      if (insect instanceof HoneyBee) bees.remove(insect);
      if (insect instanceof Hornet) hornets.remove(insect);
    }
    UpdateLog();
    deadInsects.clear();
  }

  private void resetMap() {
    for (HoneyBee bee : bees) {
      bee.takeDamage(10000);
    }
    for (Hornet hornet : hornets) {
      hornet.takeDamage(10000);
    }
    collectDeadInsects();
  }

  /**************************** Interface *************************/

  public boolean isGameOver() {
    return _gameOver;
  }

  // This method is used to log message to panel
  public static void logMessage(String s) {
    logBuffer.append(s);
    UpdateLog();
  }

  // This method is necessary to let the game know if any insect is dead
  public static void notifyDeath(Insect insect) {
    if (insect instanceof HoneyBee) {
      bees.remove(insect);
      // System.out.println("remove bee");
    } else {
      hornets.remove(insect);
      // System.out.println("remove hornet");
    }
  }

  /************************** UI control *********************/

  public void initUI() {
    _msgPanel = new Label();
    _msgPanel.setBounds(RES_X / 2 - 125, 30, 250, 20);
    _msgPanel.setAlignment(1);

    _foodPanel = new Label();
    _foodPanel.setBounds(RES_X - 100, 30, 100, 20);
    _foodPanel.setAlignment(1);

    // close window on button click
    addWindowListener(
        new WindowAdapter() {
          public void windowClosing(WindowEvent e) {
            endGame();
            dispose();
          }
        });

    // create canvas
    canvasX = RES_X / 20;
    canvasY = RES_Y / 8;
    canvasWidth = (int) (RES_X * 0.75);
    canvasHeight = (int) (RES_Y * 0.75);
    _boardCanvas = new GameCanvas(canvasX, canvasY, canvasWidth, canvasHeight);
    _boardCanvas.addMouseListener(
        new MouseAdapter() {
          @Override
          public void mousePressed(MouseEvent e) {
            // if click on the canvas then set inSelection to false
            // System.out.println(e.getX() + " " + e.getY());
            _inSelection = false;
            Tile tile = calculateSpawnLocation(e.getX(), e.getY());
            if (tile != null) {
              spawnBee(tile);
            }
          }
        });

    // create button panel
    Panel _buttonPanel = new Panel();
    _buttonPanel.setLayout(new BoxLayout(_buttonPanel, BoxLayout.Y_AXIS));
    _buttonPanel.setBounds(RES_X - 150, 150, 140, RES_Y / 3);
    createButtons(_buttonPanel);

    // create log panel
    _logPanel = new TextArea();
    _logPanel.setSize(400, 400);
    _logPanel.setBounds(150, RES_Y - 130, 600, 110);
    _logPanel.setVisible(true);
    _logPanel.setEditable(false);

    add(_logPanel);
    add(_boardCanvas);
    add(_buttonPanel);
    add(_foodPanel);
    add(_msgPanel);
    setSize(RES_X, RES_Y); // frame size 300 width and 300 height
    setLayout(null); // no layout manager
    setVisible(true); // now frame will be visible, by default not visible
  }

  public void UpdateMessage(String s) {
    _msgPanel.setText(s);
  }

  public void UpdateFood(int num) {
    _totalFood += num;
    _foodPanel.setText("Food left: " + Integer.toString(_totalFood));
  }

  public static void UpdateLog() {
    _logPanel.setText(logBuffer.toString());
  }

  private void createButtons(Panel buttonPanel) {

    startBtn = new Button("Start Game");
    startBtn.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            startGame();
          }
        });

    endBtn = new Button("End Game");
    endBtn.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            endGame();
          }
        });

    spawnBtn = new Button("Spawn Hornet");
    spawnBtn.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            spawnHornet();
          }
        });

    // button for honey bee
    busyBeeBtn = new Button("Busy Bee ($2)");
    busyBeeBtn.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            // set selected bee type
            // in selection to true
            _selectedBeeType = EnumBeeType.busyBee;
            _inSelection = true;
            UpdateMessage("You selected Busy Bee");
          }
        });

    // button for angry bee
    angryBeeBtn = new Button("Angry Bee ($1)");
    angryBeeBtn.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            // set selected bee type
            // in selection to true
            _selectedBeeType = EnumBeeType.angryBee;
            _inSelection = true;
            UpdateMessage("You selected Angry Bee");
          }
        });

    // button for tanky bee
    tankyBeeBtn = new Button("Tanky Bee ($3)");
    tankyBeeBtn.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            // set selected bee type
            // in selection to true
            _selectedBeeType = EnumBeeType.tankyBee;
            _inSelection = true;
            UpdateMessage("You selected Tanky Bee");
          }
        });

    buttonPanel.add(startBtn);
    buttonPanel.add(endBtn);
    buttonPanel.add(new Label(" "));
    buttonPanel.add(spawnBtn);
    buttonPanel.add(busyBeeBtn);
    buttonPanel.add(angryBeeBtn);
    buttonPanel.add(tankyBeeBtn);
  }

  private void toggleInput(boolean b) {
    busyBeeBtn.setEnabled(b);
    angryBeeBtn.setEnabled(b);
    tankyBeeBtn.setEnabled(b);
    spawnBtn.setEnabled(b);
  }
}
