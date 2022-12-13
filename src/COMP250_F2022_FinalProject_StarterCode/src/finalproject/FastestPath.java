package finalproject;

import finalproject.system.Tile;

public class FastestPath extends PathFindingService {
  public FastestPath(Tile start) {
    super(start);
    generateGraph();
  }

  @Override
  public void generateGraph() {
    g = new Graph(GraphTraversal.DFS(source), "time");
  }
}
