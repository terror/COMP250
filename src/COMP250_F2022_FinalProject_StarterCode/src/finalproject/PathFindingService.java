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

  public ArrayList<Tile> findPath(Tile startNode) {
    TilePriorityQ queue = new TilePriorityQ();

    for (Tile t : g.getAllVertices()) {
      if (t == startNode) t.costEstimate = 0;
      else t.costEstimate = Double.MAX_VALUE;
      t.predecessor = null;
      queue.insert(t);
    }

    Tile endNode = null;

    while (!queue.isEmpty()) {
      Tile u = queue.removeMin();

      for (Tile v : g.getNeighbors(u)) {
        if (v.isDestination) endNode = v;

        if (u.costEstimate + g.getWeight(u, v) < v.costEstimate)
          queue.updateKeys(v, u, u.costEstimate + g.getWeight(u, v));
      }
    }

    ArrayList<Tile> path = new ArrayList<>();

    if (endNode.predecessor != null || endNode == startNode) {
      while (endNode != null) {
        path.add(0, endNode);
        endNode = endNode.predecessor;
      }
    }

    return path;
  }

  public ArrayList<Tile> findPath(Tile start, Tile end) {
    TilePriorityQ queue = new TilePriorityQ();

    for (Tile t : g.getAllVertices()) {
      if (t == start) t.costEstimate = 0;
      else t.costEstimate = Double.MAX_VALUE;
      t.predecessor = null;
      queue.insert(t);
    }

    while (!queue.isEmpty()) {
      Tile u = queue.removeMin();

      for (Tile v : g.getNeighbors(u))
        if (u.costEstimate + g.getWeight(u, v) < v.costEstimate)
          queue.updateKeys(v, u, u.costEstimate + g.getWeight(u, v));
    }

    ArrayList<Tile> path = new ArrayList<>();

    if (end.predecessor != null || end == start) {
      while (end != null) {
        path.add(0, end);
        end = end.predecessor;
      }
    }

    return path;
  }

  public ArrayList<Tile> findPath(Tile start, LinkedList<Tile> waypoints) {
    return null;
  }
}

