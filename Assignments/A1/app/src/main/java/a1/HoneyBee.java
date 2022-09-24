package a1;

public abstract class HoneyBee extends Insect {
  private int cost;

  public HoneyBee(Tile tile, int healthPoints, int cost) {
    super(tile, healthPoints);
    this.cost = cost;
  }

  public int getCost() {
    return this.cost;
  }

  public boolean takeAction() {
    return false;
  }

  @Override
  public boolean equals(Object object) {
    if (object == this) return true;
    if (!(object instanceof HoneyBee)) return false;
    HoneyBee bee = (HoneyBee) object;
    if (bee.getHealth() != getHealth() || bee.getPosition() != getPosition() || bee.cost != cost)
      return false;
    return true;
  }
}
