package a1;

public class BusyBee extends HoneyBee {
  public BusyBee(Tile tile) {
    super(tile, 5, 2);
  }

  public boolean takeAction() {
    getPosition().storeFood(2);
    return true;
  }
}
