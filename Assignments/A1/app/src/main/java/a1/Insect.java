package a1;

import a1.Tile;

public abstract class Insect {
  private Tile tile;
  private int healthPoints;

  public Insect(Tile tile, int healthPoints) {
    this.tile = tile;
    this.healthPoints = healthPoints;
  }

  public final Tile getPosition() {
    return this.tile;
  }

  public final int getHealth() {
    return this.healthPoints;
  }

  public void setPosition(Tile tile) {
    this.tile = tile;
  }
}
