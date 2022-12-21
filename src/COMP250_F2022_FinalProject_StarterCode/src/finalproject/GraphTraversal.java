package finalproject;

import finalproject.system.Tile;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

public class GraphTraversal {
  public static ArrayList<Tile> BFS(Tile s) {
    ArrayList<Tile> ret = new ArrayList<>();

    LinkedList<Tile> queue = new LinkedList<>();
    queue.add(s);

    HashSet<Tile> visited = new HashSet<>();
    visited.add(s);

    while (!queue.isEmpty()) {
      Tile top = queue.removeFirst();
      ret.add(top);
      for (Tile u : top.neighbors) {
        if (!visited.contains(u) && u.isWalkable()) {
          visited.add(u);
          queue.add(u);
        }
      }
    }

    return ret;
  }

  public static ArrayList<Tile> DFS(Tile s) {
    ArrayList<Tile> ret = new ArrayList<>();

    LinkedList<Tile> stack = new LinkedList<>();
    stack.add(s);

    HashSet<Tile> visited = new HashSet<>();
    visited.add(s);

    while (!stack.isEmpty()) {
      Tile top = stack.removeFirst();
      ret.add(top);
      for (Tile u : top.neighbors) {
        if (!visited.contains(u) && u.isWalkable()) {
          visited.add(u);
          stack.addFirst(u);
        }
      }
    }

    return ret;
  }
}
