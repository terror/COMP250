package finalproject;

import finalproject.system.Tile;
import finalproject.system.TileType;

import finalproject.tiles.PlainTile;
import finalproject.tiles.DesertTile;
import finalproject.tiles.MountainTile;
import finalproject.tiles.FacilityTile;
import finalproject.tiles.MetroTile;
import finalproject.tiles.ZombieInfectedRuinTile;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

public class Tester {
  public static void main(String[] args) {
    int passed = 0, total = 0;

    ArrayList<String> failed = new ArrayList<>();

    ArrayList<String> filtered = new ArrayList<>(Arrays.asList("main", "buildGraph", "buildWorld"));

    for (Method method : Tester.class.getMethods()) {
      if (!Modifier.isStatic(method.getModifiers()) || filtered.contains(method.getName())) continue;

      System.out.printf("%n======= %s =======%n", method.getName());
      System.out.flush();

      try {
        if ((boolean) method.invoke(null)) ++passed;
        else failed.add(method.getName());
      } catch (AssertionError e) {
        System.out.println(e);
        failed.add(method.getName());
      } catch (Exception e) {
        e.printStackTrace(System.out);
        failed.add(method.getName());
      }

      ++total;
    }

    System.out.printf("%n%n%d of %d tests passed.%n", passed, total);

    System.out.println("\nTests failed:");

    for (String t : failed)
      System.out.println(t);
  }

  public static class TestTile extends Tile {
    // For testing
  }

  public static boolean testTile1() { // 1 point
    Tile t;

    t = new PlainTile();
    if (t.distanceCost != 3 || t.timeCost != 1 || t.damageCost != 0) {
      System.out.println("Wrong costs for PlainTile.");
      return false;
    }

    t = new DesertTile();
    if (t.distanceCost != 2 || t.timeCost != 6 || t.damageCost != 3) {
      System.out.println("Wrong costs for DesertTile.");
      return false;
    }

    t = new MountainTile();
    if (t.distanceCost != 100 || t.timeCost != 100 || t.damageCost != 100) {
      System.out.println("Wrong costs for MountainTile.");
      return false;
    }

    return true;
  }

  public static boolean testTile2() { // 1 point
    Tile t;

    t = new FacilityTile();
    if (t.distanceCost != 1 || t.timeCost != 2 || t.damageCost != 0) {
      System.out.println("Wrong costs for FacilityTile.");
      return false;
    }

    t = new MetroTile();
    if (t.distanceCost != 1 || t.timeCost != 1 || t.damageCost != 2) {
      System.out.println("Wrong costs for MetroTile.");
      return false;
    }

    t = new ZombieInfectedRuinTile();
    if (t.distanceCost != 1 || t.timeCost != 3 || t.damageCost != 5) {
      System.out.println("Wrong costs for ZombieInfectedRuinTile.");
      return false;
    }

    return true;
  }

  // 0 -- 1 -- 2 -- 3 -- 4
  public static boolean testBFS1() { // 1 point
    Tile[] tiles = new Tile[5];
    for (int i = 0; i < tiles.length; ++i) {
      tiles[i] = new TestTile();
      tiles[i].nodeID = i;
    }
    for (int i = 0; i < tiles.length - 1; ++i)
      tiles[i].addNeighbor(tiles[i + 1]);
    ArrayList<Tile> arr = GraphTraversal.BFS(tiles[0]);

    System.out.println("Expected:");
    for (int i = 0; i < tiles.length; ++i) {
      System.out.print(tiles[i].nodeID + " ");
    }
    System.out.println();
    System.out.println("Yours:");
    for (int i = 0; i < arr.size(); ++i) {
      System.out.print(arr.get(i).nodeID + " ");
    }
    System.out.println();

    if (arr.size() != tiles.length) return false;
    for (int i = 0; i < tiles.length; ++i) {
      if (tiles[i] != arr.get(i)) {
        return false;
      }
    }
    return true;
  }

  // 0 -- 1 -- 2 -- 3 -- 4
  public static boolean testDFS1() { // 1 point
    Tile[] tiles = new Tile[5];
    for (int i = 0; i < tiles.length; ++i) {
      tiles[i] = new TestTile();
      tiles[i].nodeID = i;
    }
    for (int i = 0; i < tiles.length - 1; ++i)
      tiles[i].addNeighbor(tiles[i + 1]);
    ArrayList<Tile> arr = GraphTraversal.DFS(tiles[0]);

    System.out.println("Expected:");
    for (int i = 0; i < tiles.length; ++i) {
      System.out.print(tiles[i].nodeID + " ");
    }
    System.out.println();
    System.out.println("Yours:");
    for (int i = 0; i < arr.size(); ++i) {
      System.out.print(arr.get(i).nodeID + " ");
    }
    System.out.println();

    if (arr.size() != tiles.length) return false;
    for (int i = 0; i < tiles.length; ++i) {
      if (tiles[i] != arr.get(i)) {
        return false;
      }
    }
    return true;
  }


  /*
   *       4
   *       |
   *  3 -- 0 -- 1
   *       |
   *       2
   */
  public static boolean testBFS2() { // 1 point
    Tile[] tiles = new Tile[5];
    for (int i = 0; i < tiles.length; ++i) {
      tiles[i] = new TestTile();
      tiles[i].nodeID = i;
    }
    for (int i = 1; i < tiles.length; ++i)
      tiles[0].addNeighbor(tiles[i]);
    ArrayList<Tile> arr = GraphTraversal.BFS(tiles[0]);

    System.out.println("Expected:");
    for (int i = 0; i < tiles.length; ++i) {
      System.out.print(tiles[i].nodeID + " ");
    }
    System.out.println();
    System.out.println("Yours:");
    for (int i = 0; i < arr.size(); ++i) {
      System.out.print(arr.get(i).nodeID + " ");
    }
    System.out.println();

    if (arr.size() != tiles.length) return false;
    if (tiles[0] != arr.get(0)) {
      return false;
    }
    return true;
  }

  /*
   *       4
   *       |
   *  3 -- 0 -- 1
   *       |
   *       2
   */
  public static boolean testDFS2() { // 1 point
    Tile[] tiles = new Tile[5];
    for (int i = 0; i < tiles.length; ++i) {
      tiles[i] = new TestTile();
      tiles[i].nodeID = i;
    }
    for (int i = 1; i < tiles.length; ++i)
      tiles[0].addNeighbor(tiles[i]);
    ArrayList<Tile> arr = GraphTraversal.DFS(tiles[0]);

    System.out.println("Expected:");
    for (int i = 0; i < tiles.length; ++i) {
      System.out.print(tiles[i].nodeID + " ");
    }
    System.out.println();
    System.out.println("Yours:");
    for (int i = 0; i < arr.size(); ++i) {
      System.out.print(arr.get(i).nodeID + " ");
    }
    System.out.println();

    if (arr.size() != tiles.length) return false;
    if (tiles[0] != arr.get(0)) {
      return false;
    }
    return true;
  }

  public static Graph graph = null;
  public static HashMap<Tile, Integer> tile2id = null;
  public static ArrayList<Tile> tiles = null;

  /*
   *     0  1  2  3  4
   *   +---------------
   *  0|    3     2
   *  1|       1  5
   *  2|          4  5
   *  3|             2
   *  4|
   */
  public static void buildGraph() {
    if (graph != null) return;
    tiles = new ArrayList<Tile>();
    tile2id = new HashMap<Tile, Integer>();
    for (int i = 0; i < 5; ++i) {
      TestTile t = new TestTile();
      t.nodeID = i;
      tile2id.put(t, i);
      tiles.add(t);
    }

    graph = new Graph(tiles);
    graph.addEdge(tiles.get(0), tiles.get(1), 3);
    graph.addEdge(tiles.get(0), tiles.get(3), 2);
    graph.addEdge(tiles.get(1), tiles.get(2), 1);
    graph.addEdge(tiles.get(1), tiles.get(3), 5);
    graph.addEdge(tiles.get(2), tiles.get(3), 4);
    graph.addEdge(tiles.get(2), tiles.get(4), 5);
    graph.addEdge(tiles.get(3), tiles.get(4), 2);
  }

  public static boolean testEdges() { // 2 points
    int[][] edges = new int[5][5];
    for (int i = 0; i < 5; ++i)
      for (int j = 0; j < 5; ++j)
        edges[i][j] = 0;
    edges[0][1] = 3;
    edges[0][3] = 2;
    edges[1][2] = 1;
    edges[1][3] = 5;
    edges[2][3] = 4;
    edges[2][4] = 5;
    edges[3][4] = 2;

    buildGraph();
    ArrayList<Graph.Edge> allEdges = graph.getAllEdges();
    if (allEdges.size() != 7) {
      System.out.println("There should be 7 edges.");
      return false;
    }
    for (Graph.Edge e : allEdges) {
      Tile s = e.getStart();
      Tile t = e.getEnd();
      int i, j;
      if (!tile2id.containsKey(s)) return false;
      else i = tile2id.get(s);
      if (!tile2id.containsKey(t)) return false;
      else j = tile2id.get(t);
      if (edges[i][j] == e.weight) edges[i][j] = 0;
      else {
        System.out.println("Wrong edge or duplicate edge.");
        return false;
      }
    }

    return true;
  }

  public static boolean testNeighbors1() { // 1 points
    buildGraph();
    ArrayList<Tile> neighbors = graph.getNeighbors(tiles.get(0));
    if (neighbors.size() != 2) {
      System.out.println("Tile 0 should have 2 neighbors");
      return false;
    }
    if (neighbors.get(0) == tiles.get(1) && neighbors.get(1) == tiles.get(3)) return true;
    if (neighbors.get(0) == tiles.get(3) && neighbors.get(1) == tiles.get(1)) return true;
    System.out.println("Tile 0 should have tiles 1 and 3 as neighbors.");
    return false;
  }

  public static boolean testNeighbors2() { // 1 points
    buildGraph();
    ArrayList<Tile> neighbors = graph.getNeighbors(tiles.get(3));
    if (neighbors.size() != 1) {
      System.out.println("Tile 3 should have 1 neighbor");
      return false;
    }
    if (neighbors.get(0) == tiles.get(4)) return true;
    System.out.println("Tile 3 should have tile 4 as neighbor.");
    return false;
  }

  public static boolean testPathCost() { // 2 points
    buildGraph();
    ArrayList<Tile> path = new ArrayList<Tile>();
    for (int i = 0; i < 5; ++i)
      path.add(tiles.get(i));
    double cost = graph.computePathCost(path);
    if (cost != 10) {
      System.out.println("Cost of path 0-1-2-3-4 should be 10.");
      return false;
    }
    return true;
  }

  public static boolean testRemoveMin() { // 3 points
    ArrayList<Tile> tiles = new ArrayList<Tile>();
    int a[] = {3, 1, 8, 4, 9, 5, 2, 6, 10, 7};
    for (int i = 0; i < a.length; ++i) {
      Tile t = new TestTile();
      t.costEstimate = a[i];
      tiles.add(t);
    }
    Arrays.sort(a);
    TilePriorityQ q = new TilePriorityQ(tiles);
    for (int i = 0; i < a.length; ++i) {
      Tile t = q.removeMin();
      if (t.costEstimate != a[i]) return false;
    }

    return true;
  }

  public static boolean testUpdateKeys() { // 3 points
    ArrayList<Tile> tiles = new ArrayList<Tile>();
    int a[] = {13, 7, 10, 9, 11, 5, 14, 12, 2, 15, 4, 6, 16, 3, 8};
    int b[] = {7, 14, 13, 2, 8, 6, 5, 4, 3, 11, 0, 10, 1, 12, 9};
    for (int i = 0; i < a.length; ++i) {
      Tile t = new TestTile();
      t.costEstimate = a[i];
      tiles.add(t);
    }
    TilePriorityQ q = new TilePriorityQ(tiles);
    Tile t0 = new TestTile();
    t0.costEstimate = 0.5;
    for (int i = 0; i < a.length; ++i) {
      Tile t1 = tiles.get(b[i]);
      q.updateKeys(t1, t0, 1);
      Tile t2 = q.removeMin();
      if (t2 != t1 || t2.costEstimate != 1) return false;
    }

    return true;
  }

  public static char[][] smMap = {{'s', 'd'}, {'z', 'e'},};
  public static char[][] lgMap = {{'s', 'M', 'p', 'z'}, {'f', 'm', 'p', 'd'}, {'f', 'f', 'd', 'p'}, {'d', 'f', 'M', 'e'},};
  public static char[][] dgMap = {{'s', 'z', 'z', 'p'}, {'p', 'm', 'z', 'p'}, {'p', 'p', 'z', 'z'}, {'z', 'p', 'p', 'e'},};
  public static ArrayList<Tile> world = null;
  public static Tile[][] tileArray = null;

  public static void buildWorld(char[][] map, boolean useMetro) {
    world = new ArrayList<Tile>();
    tileArray = new Tile[map.length][map[0].length];
    ArrayList<MetroTile> metros = new ArrayList<MetroTile>();

    int id = 0;
    for (int i = 0; i < map.length; ++i)
      for (int j = 0; j < map[0].length; ++j) {
        Tile t = null;
        switch (map[i][j]) {
          case 's':
            t = new FacilityTile();
            t.isStart = true;
            break;

          case 'e':
            t = new FacilityTile();
            t.isDestination = true;
            break;

          case 'p':
            t = new PlainTile();
            break;

          case 'd':
            t = new DesertTile();
            break;

          case 'm':
            t = new MountainTile();
            break;

          case 'f':
            t = new FacilityTile();
            break;

          case 'M':
            t = new MetroTile();
            metros.add((MetroTile) t);
            break;

          case 'z':
            t = new ZombieInfectedRuinTile();
            break;

          default:
            t = new MountainTile();
        }

        t.nodeID = id++;
        tileArray[i][j] = t;
        world.add(t);
      }

    for (int i = 0; i < map.length; ++i)
      for (int j = 0; j < map[0].length - 1; ++j)
        tileArray[i][j].addNeighbor(tileArray[i + 0][j + 1]);

    for (int i = 0; i < map.length - 1; ++i)
      for (int j = 0; j < map[0].length; ++j)
        tileArray[i][j].addNeighbor(tileArray[i + 1][j + 0]);

    if (useMetro) {
      for (int i = 0; i < metros.size() - 1; ++i)
        for (int j = i + 1; j < metros.size(); ++j) {
          MetroTile a = metros.get(i);
          MetroTile b = metros.get(j);
          a.fixMetro(b);
          if (a.metroTimeCost < 100 && a.metroDistanceCost < 100) a.addNeighbor(b);
        }
    }
  }

  // test ShortestPath
  public static boolean testSPathGenerateGraph1() { // 2 points
    buildWorld(smMap, false);
    ShortestPath shortest = new ShortestPath(world.get(0));
    ArrayList<Graph.Edge> edges = shortest.g.getAllEdges();
    if (edges.size() != 8) {
      System.out.println("There should be 8 edges.");
      return false;
    }
    for (Graph.Edge e : edges) {
      if (e.weight != e.destination.distanceCost) {
        System.out.println("Weight should be the distance cost.");
        return false;
      }
    }

    return true;
  }

  // test ShortestPath
  public static boolean testSPathGenerateGraph2() { // 3 points
    buildWorld(lgMap, false);
    ShortestPath shortest = new ShortestPath(world.get(0));
    ArrayList<Graph.Edge> edges = shortest.g.getAllEdges();
    if (edges.size() != 40) {
      System.out.println("There should be 40 edges. Note that MountainTile is not reachable.");
      return false;
    }
    for (Graph.Edge e : edges) {
      if (e.weight != e.destination.distanceCost) {
        System.out.println("Weight should be the distance cost.");
        return false;
      }
    }

    return true;
  }

  // test findPath(Tile startNode)
  public static boolean testFindPath1Arg1() { // 2 points
    buildWorld(smMap, false);
    ShortestPath shortest = new ShortestPath(world.get(0));
    ArrayList<Tile> path = shortest.findPath(world.get(0));
    if (path.size() != 3) {
      System.out.println("Path length (number of vertices, including start and end) should be 3.");
      return false;
    }
    if (shortest.g.computePathCost(path) != 2) {
      System.out.println("Path cost should be 2.");
      return false;
    }
    return true;
  }

  // test findPath(Tile startNode)
  public static boolean testFindPath1Arg2() { // 3 points
    buildWorld(lgMap, false);
    ShortestPath shortest = new ShortestPath(world.get(0));
    ArrayList<Tile> path = shortest.findPath(world.get(0));
    if (path.size() == 6) { // to furthest (weighted) vertex
      /*
       * s
       * f
       * f f d p
       */
      Tile[] a = {tileArray[0][0], tileArray[1][0], tileArray[2][0], tileArray[2][1], tileArray[2][2], tileArray[2][3]};
      for (int i = 0; i < a.length; ++i)
        if (path.get(i) != a[i]) {
          System.out.println("Wrong path.");
          return false;
        }

      if (shortest.g.computePathCost(path) != 8) {
        System.out.println("Path cost should be 8.");
        return false;
      }

    } else if (path.size() == 7) { // to destination
      /* s
       * f
       * f f
       *   f M e
       */
      Tile[] a = {tileArray[0][0], tileArray[1][0], tileArray[2][0], tileArray[2][1], tileArray[3][1], tileArray[3][2], tileArray[3][3]};
      for (int i = 0; i < a.length; ++i)
        if (path.get(i) != a[i]) {
          System.out.println("Wrong path.");
          return false;
        }

      if (shortest.g.computePathCost(path) != 6) {
        System.out.println("Path cost should be 6.");
        return false;
      }

    } else {
      System.out.println("Wrong path length");
      return false;
    }
    return true;
  }

  // test findPath(Tile start, Tile end)
  public static boolean testFindPath2Args() { // 1 point
    buildWorld(lgMap, false);
    ShortestPath shortest = new ShortestPath(world.get(0));
    ArrayList<Tile> path = shortest.findPath(world.get(0), world.get(world.size() - 1));
    /*
     * s
     * f
     * f f
     *   f M e
     */
    if (path.size() != 7) {
      System.out.println("Path length (number of vertices, including start and end) should be 7.");
      return false;
    }
    if (shortest.g.computePathCost(path) != 6) {
      System.out.println("Path cost should be 6.");
      return false;
    }
    return true;
  }

  // test findPath(Tile start, LinkedList<Tile> waypoints)
  public static boolean testFindPath3Args() { // 1 point
    buildWorld(lgMap, false);
    ShortestPath shortest = new ShortestPath(world.get(0));
    LinkedList<Tile> waypoints = new LinkedList<Tile>();
    waypoints.add(tileArray[2][2]);
    ArrayList<Tile> path = shortest.findPath(world.get(0), waypoints);
    /*
     * s
     * f
     * f f d
     *     M e
     */
    if (path.size() != 7) {
      System.out.println(path.size());
      System.out.println("Path length (number of vertices, including start and end) should be 7.");
      return false;
    }
    if (shortest.g.computePathCost(path) != 7) {
      System.out.println("Path cost should be 7.");
      return false;
    }
    return true;
  }

  // test FastestPath
  public static boolean testFPathGenerateGraph1() { // 1 point
    buildWorld(smMap, false);
    FastestPath shortest = new FastestPath(world.get(0));
    ArrayList<Graph.Edge> edges = shortest.g.getAllEdges();
    if (edges.size() != 8) {
      System.out.println("There should be 8 edges.");
      return false;
    }
    for (Graph.Edge e : edges) {
      if (e.weight != e.destination.timeCost) {
        System.out.println("Weight should be the time cost.");
        return false;
      }
    }

    return true;
  }

  // test FastestPath
  public static boolean testFPathGenerateGraph2() { // 1 point
    buildWorld(lgMap, false);
    FastestPath shortest = new FastestPath(world.get(0));
    ArrayList<Graph.Edge> edges = shortest.g.getAllEdges();
    if (edges.size() != 40) {
      System.out.println("There should be 40 edges. Note that MountainTile is not reachable.");
      return false;
    }
    for (Graph.Edge e : edges) {
      if (e.weight != e.destination.timeCost) {
        System.out.println("Weight should be the time cost.");
        return false;
      }
    }

    return true;
  }

  // test ShortestPath (with metro)
  public static boolean testSPathGenerateGraphWithMetro() { // 1 point
    buildWorld(lgMap, true);
    ShortestPath shortest = new ShortestPath(world.get(0));
    ArrayList<Graph.Edge> edges = shortest.g.getAllEdges();
    if (edges.size() != 42) {
      System.out.println("There should be 42 edges. Note that MountainTile is not reachable.");
      return false;
    }
    for (Graph.Edge e : edges) {
      if (e.origin.getTileType() == TileType.Metro && e.destination.getTileType() == TileType.Metro) {
        if (e.weight != ((MetroTile) e.destination).metroDistanceCost) {
          System.out.println("Weight should be the metro distance cost.");
          return false;
        }
      } else {
        if (e.weight != e.destination.distanceCost) {
          System.out.println("Weight should be the distance cost.");
          return false;
        }
      }
    }

    return true;
  }

  // test FastestPath (with metro)
  public static boolean testFPathGenerateGraphWithMetro() { // 1 point
    buildWorld(lgMap, true);
    FastestPath shortest = new FastestPath(world.get(0));
    ArrayList<Graph.Edge> edges = shortest.g.getAllEdges();
    if (edges.size() != 42) {
      System.out.println("There should be 42 edges. Note that MountainTile is not reachable.");
      return false;
    }
    for (Graph.Edge e : edges) {
      if (e.origin.getTileType() == TileType.Metro && e.destination.getTileType() == TileType.Metro) {
        if (e.weight != ((MetroTile) e.destination).metroTimeCost) {
          System.out.println("Weight should be the metro time cost.");
          return false;
        }
      } else {
        if (e.weight != e.destination.timeCost) {
          System.out.println("Weight should be the time cost.");
          return false;
        }
      }
    }

    return true;
  }

  // test SafestShortestPath
  public static boolean testSSPathGenerateGraph() { // 2 points
    buildWorld(lgMap, false);
    SafestShortestPath sspath = new SafestShortestPath(world.get(0), 100);
    ArrayList<Graph.Edge> edges;
    edges = sspath.costGraph.getAllEdges();
    if (edges.size() != 40) {
      System.out.println("There should be 40 edges. Note that MountainTile is not reachable.");
      return false;
    }
    for (Graph.Edge e : edges) {
      if (e.weight != e.destination.distanceCost) {
        System.out.println("Weight should be the distance cost.");
        return false;
      }
    }
    edges = sspath.damageGraph.getAllEdges();
    if (edges.size() != 40) {
      System.out.println("There should be 40 edges. Note that MountainTile is not reachable.");
      return false;
    }
    for (Graph.Edge e : edges) {
      if (e.weight != e.destination.damageCost) {
        System.out.println("Weight should be the damage cost.");
        return false;
      }
    }
    edges = sspath.aggregatedGraph.getAllEdges();
    if (edges.size() != 40) {
      System.out.println("There should be 40 edges. Note that MountainTile is not reachable.");
      return false;
    }
    for (Graph.Edge e : edges) {
      if (e.weight != e.destination.damageCost) {
        System.out.println("Weight should be the damage cost.");
        return false;
      }
    }

    return true;
  }

  // test SafestShortestPath.findPath(Tile start, LinkedList<Tile> waypoints)
  public static boolean testSafeFindPath1() { // 1 point
    buildWorld(dgMap, false);
    ShortestPath shortest = new SafestShortestPath(world.get(0), 100);
    LinkedList<Tile> waypoints = new LinkedList<Tile>();
    ArrayList<Tile> path = shortest.findPath(world.get(0), waypoints);
    /*
     * s z z
     *     z
     *     z z
     *       e
     */
    if (path.size() != 7) {
      System.out.println("Path length (number of vertices, including start and end) should be 7.");
      return false;
    }

    Tile[] a = {tileArray[0][0], tileArray[0][1], tileArray[0][2], tileArray[1][2], tileArray[2][2], tileArray[2][3], tileArray[3][3]};
    for (int i = 0; i < a.length; ++i)
      if (path.get(i) != a[i]) {
        System.out.println("Wrong path.");
        return false;
      }

    if (shortest.g.computePathCost(path) != 6) {
      System.out.println("Path cost should be 6.");
      return false;
    }
    return true;
  }

  // test SafestShortestPath.findPath(Tile start, LinkedList<Tile> waypoints)
  public static boolean testSafeFindPath2() { // 1 point
    buildWorld(dgMap, false);
    ShortestPath shortest = new SafestShortestPath(world.get(0), 1);
    LinkedList<Tile> waypoints = new LinkedList<Tile>();
    ArrayList<Tile> path = shortest.findPath(world.get(0), waypoints);
    /*
     * s
     * p
     * p p
     *   p p e
     */
    if (path.size() != 7) {
      System.out.println("Path length (number of vertices, including start and end) should be 7.");
      return false;
    }
    if (shortest.g.computePathCost(path) != 16) {
      System.out.println("Path cost should be 16.");
      return false;
    }
    return true;
  }

  // test SafestShortestPath.findPath(Tile start, LinkedList<Tile> waypoints)
  public static boolean testSafeFindPath3() { // 1 point
    buildWorld(dgMap, false);
    ShortestPath shortest = new SafestShortestPath(world.get(0), 100);
    LinkedList<Tile> waypoints = new LinkedList<Tile>();
    waypoints.add(tileArray[3][2]);
    ArrayList<Tile> path = shortest.findPath(world.get(0), waypoints);
    /*
     * s z z
     *     z
     *     z
     *     p e
     */
    if (path.size() != 7) {
      System.out.println("Path length (number of vertices, including start and end) should be 7.");
      return false;
    }
    if (shortest.g.computePathCost(path) != 8) {
      System.out.println("Path cost should be 8.");
      return false;
    }
    return true;
  }

  // test SafestShortestPath.findPath(Tile start, LinkedList<Tile> waypoints)
  public static boolean testSafeFindPath4() { // 1 point
    buildWorld(dgMap, false);
    ShortestPath shortest = new SafestShortestPath(world.get(0), 100);
    LinkedList<Tile> waypoints = new LinkedList<Tile>();
    waypoints.add(tileArray[3][0]);
    waypoints.add(tileArray[3][2]);
    ArrayList<Tile> path = shortest.findPath(world.get(0), waypoints);
    /*
     * s
     * p
     * p
     * z p p e
     */
    if (path.size() != 7) {
      System.out.println("Path length (number of vertices, including start and end) should be 7.");
      return false;
    }

    Tile[] a = {tileArray[0][0], tileArray[1][0], tileArray[2][0], tileArray[3][0], tileArray[3][1], tileArray[3][2], tileArray[3][3]};
    for (int i = 0; i < a.length; ++i)
      if (path.get(i) != a[i]) {
        System.out.println("Wrong path.");
        return false;
      }

    if (shortest.g.computePathCost(path) != 14) {
      System.out.println("Path cost should be 14.");
      return false;
    }
    return true;
  }
}
