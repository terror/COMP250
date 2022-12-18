package finalproject;

import finalproject.system.Tile;

import java.util.ArrayList;
import java.util.LinkedList;

public class SafestShortestPath extends ShortestPath {
  public int health;
  public Graph costGraph;
  public Graph damageGraph;
  public Graph aggregatedGraph;

  public SafestShortestPath(Tile start, int health) {
    super(start);
    this.health = health;
    generateGraph();
  }

  public void generateGraph() {
    ArrayList<Tile> nodes = GraphTraversal.DFS(source);
    costGraph = new Graph(nodes, "distance");
    damageGraph = new Graph(nodes, "damage");
    aggregatedGraph = new Graph(nodes, "damage");
  }

  public ArrayList<Tile> findPath(Tile start, LinkedList<Tile> waypoints) {
    g = costGraph;

    ArrayList<Tile> costGraphPath = super.findPath(source, waypoints);

    if (damageGraph.computePathCost(costGraphPath) < health) return costGraphPath;

    g = damageGraph;

    ArrayList<Tile> damageGraphPath = super.findPath(source, waypoints);

    if (damageGraph.computePathCost(damageGraphPath) > health) return null;

    g = aggregatedGraph;

    while (true) {
      double lambda = (costGraph.computePathCost(costGraphPath) - costGraph.computePathCost(damageGraphPath)) / (damageGraph.computePathCost(damageGraphPath) - damageGraph.computePathCost(costGraphPath));

      for (Graph.Edge e : aggregatedGraph.getAllEdges())
        e.weight = aggregatedGraph.getCost(e, "distance") + lambda * aggregatedGraph.getCost(e, "damage");

      ArrayList<Tile> aggregatedGraphPath = super.findPath(source, waypoints);

      if (aggregatedGraph.computePathCost(aggregatedGraphPath) == aggregatedGraph.computePathCost(costGraphPath))
        return damageGraphPath;
      else if (damageGraph.computePathCost(aggregatedGraphPath) <= health) damageGraphPath = aggregatedGraphPath;
      else costGraphPath = aggregatedGraphPath;
    }
  }
}
