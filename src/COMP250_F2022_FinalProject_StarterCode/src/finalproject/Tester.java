package finalproject;

import finalproject.system.Tile;
import finalproject.tiles.DesertTile;
import finalproject.tiles.MetroTile;
import finalproject.tiles.MountainTile;
import finalproject.tiles.PlainTile;

import java.util.ArrayList;

public class Tester {
//  private static void PrintTest(TilePriorityQ pq) {
//    System.out.print("[");
//
//    for (Tile t : pq.items) {
//      if (t == null) {
//        System.out.print(" null ,");
//        continue;
//      }
//      System.out.print(" " + t.costEstimate + " ,");
//    }
//
//    System.out.println("]");
//
//    for (Tile t : pq.items) {
//      try {
//        System.out.print(t + " || Cost: " + t.costEstimate + " ");
//      } catch (Exception e) {
//        System.out.print("null || Cost: null");
//      }
//      try {
//        System.out.print(" || Parent: " + pq.items.get(pq.par(t)).costEstimate);
//      } catch (Exception e) {
//        System.out.print(" || Parent: null");
//      }
//      try {
//        System.out.print(" || Left Child: " + pq.items.get(pq.left(t)).costEstimate);
//      } catch (Exception e) {
//        System.out.print(" || Left Child: null");
//      }
//      try {
//        System.out.print(" || Right Child: " + pq.items.get(pq.right(t)).costEstimate + "\n");
//      } catch (Exception e) {
//        System.out.print(" || Right Child: null\n");
//      }
//    }
//  }

//  private static void testPriorityQueue() {
//    ArrayList<Tile> arr = new ArrayList<Tile>();
//
//    arr.add(new DesertTile());
//    arr.add(new PlainTile());
//    arr.add(new MountainTile());
//    arr.add(new MetroTile());
//    arr.add(new DesertTile());
//    arr.add(new PlainTile());
//    arr.add(new PlainTile());
//    arr.add(new PlainTile());
//    arr.add(new PlainTile());
//    arr.add(new PlainTile());
//    arr.add(new PlainTile());
//
//    int i = arr.size();
//
//    for (Tile t : arr)
//      t.costEstimate = i--;
//
//    TilePriorityQ PriorityQ = new TilePriorityQ(arr);
//
//    PrintTest(PriorityQ);
//
//    PriorityQ.removeMin();
//
//    System.out.println();
//    System.out.println();
//
//    PrintTest(PriorityQ);
//  }

  private static void testGraphConstruction() {
    Tile vertex1 = new DesertTile();
    Tile vertex2 = new DesertTile();
    Tile vertex3 = new PlainTile();
    Tile vertex4 = new PlainTile();

    ArrayList<Tile> vertices = new ArrayList<>();
    vertices.add(vertex1);
    vertices.add(vertex2);
    vertices.add(vertex3);
    vertices.add(vertex4);

    Graph weightedGraph = new Graph(vertices);

    weightedGraph.addEdge(vertex1, vertex2, 5);
    weightedGraph.addEdge(vertex2, vertex3, 5);
    weightedGraph.addEdge(vertex3, vertex4, 5);
    weightedGraph.addEdge(vertex4, vertex1, 5);

    System.out.print("Path length from tile 1 to tile 4:");
    System.out.println(weightedGraph.computePathCost(vertices));

    for (Graph.Edge edge : weightedGraph.getAllEdges())
      System.out.println("Edge linking: " + edge.origin + " and " + edge.destination + " with weight " + edge.weight);
  }

  private static void foo() {
    Tile T1 = new DesertTile();
    Tile T2 = new MetroTile();
    Tile T3 = new PlainTile();
    Tile T4 = new PlainTile();
    Tile T5 = new DesertTile();
    Tile T6 = new MetroTile();
    Tile T7 = new PlainTile();
    Tile T8 = new PlainTile();
    Tile T9 = new DesertTile();
    T1.costEstimate = 2.0;
    T2.costEstimate = 50.0;
    T3.costEstimate = 1.0;
    T4.costEstimate = 10.0;
    T5.costEstimate = 2.0;
    T6.costEstimate = 6.0;
    T7.costEstimate = 4.0;
    T8.costEstimate = 7.0;
    T9.costEstimate = 55.0;


    ArrayList<Tile> tiles = new ArrayList<>();

    tiles.add(T1);
    tiles.add(T2);
    tiles.add(T3);
    tiles.add(T4);
    tiles.add(T5);
    tiles.add(T6);
    tiles.add(T7);
    tiles.add(T8);
    tiles.add(T9);
        /*
        Starts: 2.0 50.0 1.0 10.0 2.0 6.0 4.0 7.0 55.0

        Should End: 1.0 2.0 2.0 7.0 50.0 6.0 4.0 10.0 55.0
         */

    TilePriorityQ q = new TilePriorityQ(tiles);

    if (q.items.get(1).equals(T3) && q.items.get(2).equals(T5) && q.items.get(3).equals(T1) && q.items.get(4).equals(T8) && q.items.get(5).equals(T2) && q.items.get(6).equals(T6) && q.items.get(7).equals(T7) && q.items.get(8).equals(T4) && q.items.get(9).equals(T9)) {
      System.out.println("You Passed");

    } else {
      System.out.println("Your Q was");
      for (Tile t : q.items) {
        if (t != null) {
          System.out.print(t.costEstimate + " ");
        }
      }
      System.out.println("");
      System.out.println("========");
      System.out.println("The correct Q is");
      System.out.println("1.0 2.0 2.0 7.0 50.0 6.0 4.0 10.0 55.0");
    }
  }


  public static void main(String args[]) {
    System.out.println("Testing Level 2...");
    testGraphConstruction();

    System.out.println();

    System.out.println("Testing Level 3...");
    foo();
    // testPriorityQueue();
  }
}
