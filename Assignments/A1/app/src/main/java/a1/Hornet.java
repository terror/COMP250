package a1;

public class Hornet extends Insect {
  private int attackDamage;

  public Hornet(Tile tile, int healthPoints, int attackDamage) {
    super(tile, healthPoints);
    this.attackDamage = attackDamage;
  }

  public boolean takeAction() {
    return false;
  }

  @Override
  public boolean equals(Object object) {
    if (object == this) return true;
    if (!(object instanceof Hornet)) return false;
    Hornet hornet = (Hornet) object;
    if (hornet.getHealth() != getHealth()
        || hornet.getPosition() != getPosition()
        || hornet.attackDamage != attackDamage) return false;
    return true;
  }
}
