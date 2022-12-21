package finalproject;

import finalproject.system.Tile;
import java.util.ArrayList;

public class TilePriorityQ {
  public ArrayList<Tile> items;

  public TilePriorityQ() {
    items = new ArrayList<>();
  }

  public TilePriorityQ(ArrayList<Tile> vertices) {
    items = new ArrayList<>();
    for (Tile t : vertices) insert(t);
  }

  public void insert(Tile t) {
    items.add(t);

    int curr = items.size() - 1;

    int par = par(curr);

    while (par != curr && items.get(curr).costEstimate < items.get(par).costEstimate) {
      swap(curr, par);
      curr = par;
      par = par(curr);
    }
  }

  public Tile removeMin() {
    if (isEmpty()) throw new IllegalStateException("Heap is empty");

    if (items.size() == 1) return items.remove(0);

    Tile ret = items.get(0);
    items.set(0, items.remove(items.size() - 1));

    heapify(0);

    return ret;
  }

  public void updateKeys(Tile t, Tile newPred, double newEstimate) {
    if (!items.contains(t)) return;
    t.predecessor = newPred;
    t.costEstimate = newEstimate;
    build();
  }

  public boolean isEmpty() {
    return items.isEmpty();
  }

  private void swap(int a, int b) {
    Tile t = items.get(a);
    items.set(a, items.get(b));
    items.set(b, t);
  }

  private void heapify(int i) {
    int l = left(i), r = right(i), m;

    m = (l < items.size() && items.get(l).costEstimate < items.get(i).costEstimate) ? l : i;

    if (r < items.size() && items.get(r).costEstimate < items.get(m).costEstimate) {
      m = r;
    }

    if (m != i) {
      swap(i, m);
      heapify(m);
    }
  }

  private void build() {
    for (int i = items.size() / 2; i >= 0; --i) {
      heapify(i);
    }
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
}
