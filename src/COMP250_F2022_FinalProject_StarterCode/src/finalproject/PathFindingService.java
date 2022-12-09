package finalproject;

import finalproject.system.Tile;
import java.util.ArrayList;
import java.util.LinkedList;

public abstract class PathFindingService {
  Tile source;
  Graph g;

  public PathFindingService(Tile start) {
    this.source = start;
  }

  public abstract void generateGraph();

  // TODO level 4: Implement basic dijkstra's algorithm to find a path to the final unknown
  // destination
  public ArrayList<Tile> findPath(Tile startNode) {
    return null;
  }

  // TODO level 5: Implement basic dijkstra's algorithm to path find to a known destination
  public ArrayList<Tile> findPath(Tile start, Tile end) {
    return null;
  }

  // TODO level 5: Implement basic dijkstra's algorithm to path find to the final destination
  // passing through given waypoints
  public ArrayList<Tile> findPath(Tile start, LinkedList<Tile> waypoints) {
    return null;
  }
}
