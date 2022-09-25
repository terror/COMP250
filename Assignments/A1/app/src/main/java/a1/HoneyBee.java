package a1;

public abstract class HoneyBee extends Insect {
  private int cost;

  /**
   * Constructor for a `HoneyBee` instance.
   *
   * @param tile The position of this bee.
   * @param healthPoints The current health of this bee.
   * @param cost The food cost of this bee.
   * @return A `HoneyBee` instance with specified fields.
   */
  public HoneyBee(Tile tile, int healthPoints, int cost) {
    super(tile, healthPoints);
    this.cost = cost;
  }

  /**
   * Getter for the `cost` field.
   *
   * @return The bee's food cost.
   */
  public int getCost() {
    return this.cost;
  }

  @Override
  public boolean equals(Object object) {
    if (object == this) return true;
    if (!(object instanceof HoneyBee)) return false;
    HoneyBee bee = (HoneyBee) object;
    return (bee.getHealth() == getHealth()
        && bee.getPosition().equals(getPosition())
        && bee.cost == cost);
  }
}
