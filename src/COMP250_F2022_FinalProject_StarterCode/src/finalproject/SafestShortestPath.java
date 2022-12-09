package finalproject;

import finalproject.system.Tile;

public class SafestShortestPath extends ShortestPath {
  public int health;
  public Graph costGraph;
  public Graph damageGraph;
  public Graph aggregatedGraph;

  // TODO level 8: finish class for finding the safest shortest path with given health constraint
  public SafestShortestPath(Tile start, int health) {
    super(start);
    this.health = health;
  }

  public void generateGraph() {
    // TODO Auto-generated method stub
  }
}
