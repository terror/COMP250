package a1;

import a1.Insect;

public abstract class HoneyBee extends Insect {
  private int cost;

  public HoneyBee(Tile tile, int healthPoints, int cost) {
    super(tile, healthPoints);
    this.cost = cost;
  }

  public int getCost() {
    return this.cost;
  }
}
