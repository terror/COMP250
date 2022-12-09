package finalproject.system;

import java.util.ArrayList;

public abstract class Tile extends TileComponent {

  public double distanceCost;
  public double timeCost;
  public double damageCost;

  public boolean isDestination;
  public boolean isStart;
  public int xCoord;
  public int yCoord;
  public int nodeID;

  // These will become useful when implementing Dijkstra
  public Tile predecessor;
  public double costEstimate;

  // connect adjacent tiles with edge
  public ArrayList<Tile> neighbors = new ArrayList<>();

  public Tile() {}

  public Tile(double dist, double time, double dmg) {
    super();

    this.distanceCost = dist;
    this.timeCost = time;
    this.damageCost = dmg;

    onMouseClickedProperty()
        .set(
            event -> {
              // we can add a way point to this tile, for visualization
              if (StateManager.getInstance().isInWaypointSelection.get()) {
                // add waypoint to StateManager
                StateManager.getInstance().controller.addWaypoint((Tile) this);
                StateManager.getInstance().isInWaypointSelection.set(false);
              }
              Logger.getInstance()
                  .logSystemMessage(
                      "Clicked on a "
                          + getTileType()
                          + " region, distance "
                          + distanceCost
                          + ", time "
                          + timeCost
                          + ", risk damage "
                          + damageCost
                          + ".");
            });
  }

  @Override
  public void initComponent(int x, int y, int width, int height) {
    super.initComponent(x, y, width, height);
    if (isDestination) setText("End");
    if (isStart) setText("Start");
  }

  public void addNeighbor(Tile node) {
    if (neighbors.contains(node)) return;
    neighbors.add(node);
    node.addNeighbor(this);
  }

  public boolean isWalkable() {
    return (distanceCost < 100 && timeCost < 100 && damageCost < 100);
  }

  public void setNodeID(int num) {
    this.nodeID = num;
  }

  public int getNodeID() {
    return this.nodeID;
  }

  public void graphCoord(int x, int y) {
    this.xCoord = x;
    this.yCoord = y;
  }
}
