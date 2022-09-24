package a1;

public class AngryBee extends HoneyBee {
  int attackDamage;

  public AngryBee(Tile tile, int attackDamage) {
    super(tile, 10, 1);
    this.attackDamage = attackDamage;
  }

  public boolean takeAction() {
    return false;
  }

  @Override
  public boolean equals(Object object) {
    if (object == this) return true;
    if (!(object instanceof AngryBee)) return false;
    AngryBee bee = (AngryBee) object;
    if (bee.getHealth() != getHealth()
        || bee.getPosition() != getPosition()
        || bee.getCost() != getCost()
        || bee.attackDamage != attackDamage) return false;
    return true;
  }
}
