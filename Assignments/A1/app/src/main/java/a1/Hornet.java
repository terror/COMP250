package a1;

public class Hornet extends Insect {
  private int attackDamage;

  public Hornet(Tile tile, int healthPoints, int attackDamage) {
    super(tile, healthPoints);
    this.attackDamage = attackDamage;
  }
}
