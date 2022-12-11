package finalproject;

import finalproject.system.Tile;
import java.util.ArrayList;
import java.util.HashMap;

public class Graph {
  HashMap<Tile, ArrayList<Edge>> edges;

  public Graph(ArrayList<Tile> vertices) {
    // Initialize each array list
    for (Tile t : vertices)
      edges.put(t, new ArrayList<>());

    // Store all edges
    for (Tile v : vertices) {
      for (Tile u : v.neighbors) {
        addEdge(u, v, v.costEstimate);
        addEdge(v, u, u.costEstimate);
      }
    }
  }

  public void addEdge(Tile origin, Tile destination, double weight) {
    edges.get(origin).add(new Edge(origin, destination, weight));
  }

  public ArrayList<Edge> getAllEdges() {
    ArrayList<Edge> all = new ArrayList<>();
    for (ArrayList<Edge> curr : edges.values())
      for (Edge e : curr) all.add(e);
    return all;
  }

  public ArrayList<Tile> getNeighbors(Tile t) {
    ArrayList<Tile> neighbors = new ArrayList<>();
    for (Edge e : edges.get(t))
      if (e.destination.isWalkable())
        neighbors.add(e.destination);
    return neighbors;
  }

  public double computePathCost(ArrayList<Tile> path) {
    double cost = 0;
    for (Tile t : path) cost += t.costEstimate;
    return cost;
  }

  public static class Edge {
    Tile origin;
    Tile destination;
    double weight;

    public Edge(Tile origin, Tile destination, double weight) {
      this.origin = origin;
      this.destination = destination;
      this.weight = weight;
    }

    public Tile getStart() {
      return origin;
    }

    public Tile getEnd() {
      return destination;
    }
  }
}
