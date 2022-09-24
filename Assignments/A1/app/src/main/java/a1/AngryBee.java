package a1;

public class AngryBee extends HoneyBee {
  int attackDamage;

  public AngryBee(Tile tile, int attackDamage) {
    super(tile, 10, 1);
    this.attackDamage = attackDamage;
  }
}
