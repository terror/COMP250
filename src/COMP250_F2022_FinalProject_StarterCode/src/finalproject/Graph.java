package finalproject;

import finalproject.system.Tile;
import finalproject.tiles.MetroTile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Graph {
  HashMap<Tile, HashSet<Edge>> edges;

  public Graph(ArrayList<Tile> vertices) {
    edges = new HashMap<>();
    for (Tile v : vertices) edges.put(v, new HashSet<>());
  }

  public Graph(ArrayList<Tile> vertices, String type) {
    edges = new HashMap<>();

    for (Tile v : vertices) {
      for (Tile u : v.neighbors) {
        boolean metro = v instanceof MetroTile && u instanceof MetroTile;
        if (metro) ((MetroTile) u).fixMetro(v);
        if (u.isWalkable()) addEdge(v, u, getCost(u, type, metro));
      }
    }
  }

  public double getCost(Edge e, String type) {
    boolean metro = e.origin instanceof MetroTile && e.destination instanceof MetroTile;

    switch (type) {
      case "damage":
        return e.destination.damageCost;
      case "distance":
        return metro ? ((MetroTile) e.destination).metroDistanceCost : e.destination.distanceCost;
      case "time":
        return metro ? ((MetroTile) e.destination).metroTimeCost : e.destination.timeCost;
      default:
        throw new IllegalArgumentException();
    }
  }

  private double getCost(Tile t, String type, boolean metro) {
    switch (type) {
      case "damage":
        return t.damageCost;
      case "distance":
        return metro ? ((MetroTile) t).metroDistanceCost : t.distanceCost;
      case "time":
        return metro ? ((MetroTile) t).metroTimeCost : t.timeCost;
      default:
        throw new IllegalArgumentException();
    }
  }

  public void addEdge(Tile origin, Tile destination, double weight) {
    if (!edges.containsKey(origin)) edges.put(origin, new HashSet<>());
    edges.get(origin).add(new Edge(origin, destination, weight));
  }

  public ArrayList<Edge> getAllEdges() {
    ArrayList<Edge> all = new ArrayList<>();
    for (HashSet<Edge> curr : edges.values()) for (Edge e : curr) all.add(e);
    return all;
  }

  public ArrayList<Tile> getAllVertices() {
    ArrayList<Tile> ret = new ArrayList<>();
    for (Tile t : edges.keySet()) ret.add(t);
    return ret;
  }

  public double getWeight(Tile u, Tile v) {
    for (Edge x : edges.get(u)) if (x.destination == v) return x.weight;
    return 0;
  }

  public ArrayList<Tile> getNeighbors(Tile t) {
    ArrayList<Tile> neighbors = new ArrayList<>();
    for (Edge e : edges.get(t)) if (e.destination.isWalkable()) neighbors.add(e.destination);
    return neighbors;
  }

  public double computePathCost(ArrayList<Tile> path) {
    double cost = 0;
    for (int i = 0; i < path.size() - 1; ++i) cost += getWeight(path.get(i), path.get(i + 1));
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
