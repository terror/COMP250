package a1;

public class BusyBee extends HoneyBee {
  /**
   * Constructor for a `BusyBee` instance.
   *
   * @param tile The position of this bee.
   * @return A `BusyBee` instance with a specified position.
   */
  public BusyBee(Tile tile) {
    super(tile, 5, 2);
  }

  /**
   * Store `2` foods on this bees position.
   *
   * @return Whether or not this action succeeded.
   */
  public boolean takeAction() {
    getPosition().storeFood(2);
    return true;
  }
}
