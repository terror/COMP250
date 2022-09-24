package a1;

public class TankyBee extends HoneyBee {
  private int attackDamage;
  private int armor;

  public TankyBee(Tile tile, int attackDamage, int armor) {
    super(tile, 30, 3);
    this.attackDamage = attackDamage;
    this.armor = armor;
  }

  public boolean takeAction() {
    return false;
  }

  @Override
  public boolean equals(Object object) {
    if (object == this) return true;
    if (!(object instanceof TankyBee)) return false;
    TankyBee bee = (TankyBee) object;
    if (bee.getHealth() != getHealth()
        || bee.getPosition() != getPosition()
        || bee.getCost() != getCost()
        || bee.attackDamage != attackDamage
        || bee.armor != armor) return false;
    return true;
  }
}
