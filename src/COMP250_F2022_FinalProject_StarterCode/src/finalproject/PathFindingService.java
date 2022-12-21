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
      t.costEstimate = t == startNode ? 0 : Double.MAX_VALUE;
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
      t.costEstimate = t == start ? 0 : Double.MAX_VALUE;
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
    ArrayList<Tile> all = new ArrayList<>();

    all.add(start);

    for (Tile w : waypoints) all.add(w);

    for (Tile t : g.getAllVertices()) if (t.isDestination) all.add(t);

    ArrayList<Tile> path = new ArrayList<>();

    for (int i = 0; i < all.size() - 1; ++i) {
      ArrayList<Tile> curr = findPath(all.get(i), all.get(i + 1));
      for (int j = i > 0 ? 1 : 0; j < curr.size(); ++j) path.add(curr.get(j));
    }

    return path;
  }
}
