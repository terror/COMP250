package finalproject;

import finalproject.system.Tile;

public class ShortestPath extends PathFindingService {
  public ShortestPath(Tile start) {
    super(start);
    generateGraph();
  }

  @Override
  public void generateGraph() {
    g = new Graph(GraphTraversal.DFS(source), "distance");
  }
}
