package finalproject;

import finalproject.system.Tile;
import finalproject.tiles.*;
import java.util.ArrayList;

public class Graph {
  // TODO level 2: Add fields that can help you implement this data type

  // TODO level 2: initialize and assign all variables inside the constructor
  public Graph(ArrayList<Tile> vertices) {}

  // TODO level 2: add an edge to the graph
  public void addEdge(Tile origin, Tile destination, double weight) {}

  // TODO level 2: return a list of all edges in the graph
  public ArrayList<Edge> getAllEdges() {
    return null;
  }

  // TODO level 2: return list of tiles adjacent to t
  public ArrayList<Tile> getNeighbors(Tile t) {
    return null;
  }

  // TODO level 2: return total cost for the input path
  public double computePathCost(ArrayList<Tile> path) {
    return 0.0;
  }

  public static class Edge {
    Tile origin;
    Tile destination;
    double weight;

    // TODO level 2: initialize appropriate fields
    public Edge(Tile s, Tile d, double cost) {}

    // TODO level 2: getter function 1
    public Tile getStart() {
      return null;
    }

    // TODO level 2: getter function 2
    public Tile getEnd() {
      return null;
    }
  }
}
