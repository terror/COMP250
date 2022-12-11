package finalproject;

import finalproject.system.Tile;

import java.util.ArrayList;

public class TilePriorityQ {
  private Tile[] items;
  private int index = 0;

  public TilePriorityQ(ArrayList<Tile> vertices) {
    items = new Tile[vertices.size()];
    for (Tile v : vertices) insert(v);
  }

  private int left(int i) {
    return (i * 2) + 1;
  }

  private int right(int i) {
    return (i * 2) + 2;
  }

  private int par(int i) {
    return (i - 1) / 2;
  }

  private boolean isLeafNode(int i) {
    return right(i) >= items.length || left(i) >= items.length;
  }

  private void insert(Tile t) {
    if (index >= items.length) return;

    items[index] = t;

    int curr = index;

    while (items[curr].costEstimate < items[par(curr)].costEstimate) {
      swap(curr, par(curr));
      curr = par(curr);
    }

    ++index;
  }

  private void swap(int a, int b) {
    Tile t = items[a];
    items[a] = items[b];
    items[b] = t;
  }

  public Tile removeMin() {
    Tile ret = items[0];
    items[0] = items[--index];
    fix(0);
    return ret;
  }

  private void fix(int i) {
    if (isLeafNode(i)) return;

    if (!(items[i].costEstimate > items[left(i)].costEstimate || items[i].costEstimate > items[right(i)].costEstimate))
      return;

    if (items[left(i)].costEstimate < items[right(i)].costEstimate) {
      swap(i, left(i));
      fix(left(i));
    } else {
      swap(i, right(i));
      fix(right(i));
    }
  }

  private void runFix() {
    for (int i = (index - 1 / 2); i >= 1; --i) fix(i);
  }

  public void updateKeys(Tile t, Tile newPred, double newEstimate) {
    for (int i = 0; i < items.length; ++i) {
      if (items[i] == t) {
        items[i].predecessor = newPred;
        items[i].costEstimate = newEstimate;
        runFix();
      }
    }
  }
}
