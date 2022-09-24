package a1;

import a1.HoneyBee;

public class TankyBee extends HoneyBee {
  private int attackDamage;
  private int armor;

  public TankyBee(Tile tile, int attackDamage, int armor) {
    super(tile, 30, 3);
    this.attackDamage = attackDamage;
    this.armor = armor;
  }
}
