package finalproject;

import finalproject.system.Tile;

import java.util.ArrayList;
import java.util.HashMap;

public class Graph {
  HashMap<Tile, ArrayList<Edge>> edges;

  public Graph(ArrayList<Tile> vertices) {
    initialize(vertices, "");
  }

  public Graph(ArrayList<Tile> vertices, String type) {
    initialize(vertices, type);
  }

  private void initialize(ArrayList<Tile> vertices, String type) {
    edges = new HashMap<>();

    for (Tile v : vertices) {
      for (Tile u : v.neighbors) {
        if (!edges.containsKey(u)) edges.put(u, new ArrayList<>());
        if (!edges.containsKey(v)) edges.put(v, new ArrayList<>());
        addEdge(u, v, getCost(v, type));
        addEdge(v, u, getCost(u, type));
      }
    }
  }

  private double getCost(Tile t, String type) {
    if (type.equals("distance")) return t.distanceCost;
    if (type.equals("time")) return t.timeCost;
    if (type.equals("damage")) return t.damageCost;
    throw new IllegalArgumentException();
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

  public ArrayList<Tile> getAllVertices() {
    ArrayList<Tile> ret = new ArrayList<>();
    for (Tile t : edges.keySet()) ret.add(t);
    return ret;
  }

  public double getWeight(Tile u, Tile v) {
    for (Edge x : edges.get(u))
      if (x.destination == v) return x.weight;
    return 0;
  }

  public ArrayList<Tile> getNeighbors(Tile t) {
    ArrayList<Tile> neighbors = new ArrayList<>();
    for (Edge e : edges.get(t))
      if (e.destination.isWalkable()) neighbors.add(e.destination);
    return neighbors;
  }

  public double computePathCost(ArrayList<Tile> path) {
    double cost = 0;

    for (int i = 0; i < path.size() - 1; ++i) {
      ArrayList<Edge> curr = edges.get(path.get(i));
      for (Edge e : curr)
        if (e.destination == path.get(i + 1)) cost += e.weight;
    }

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
