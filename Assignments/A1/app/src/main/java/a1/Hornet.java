package a1;

public class Hornet extends Insect {
  private int attackDamage;

  /**
   * Constructor for a `Hornet` instance.
   *
   * @return A new `Hornet` instance with specified fields.
   */
  public Hornet(Tile tile, int healthPoints, int attackDamage) {
    super(tile, healthPoints);
    this.attackDamage = attackDamage;
  }

  /**
   * Try to sting the bee at this position or move toward the hive.
   *
   * @return Whether or not we were able to sting a bee or move toward the bee hive.
   */
  public boolean takeAction() {
    Tile position = getPosition();

    if (position.getBee() == null && position.isHive()) {
      return false;
    }

    if (position.getBee() != null) {
      position.getBee().takeDamage(attackDamage);
    } else {
      setPosition(position.towardTheHive());
    }

    return true;
  }

  @Override
  public boolean equals(Object object) {
    if (object == this) return true;
    if (!(object instanceof Hornet)) return false;
    Hornet hornet = (Hornet) object;
    return (hornet.getHealth() == getHealth()
        && hornet.getPosition().equals(getPosition())
        && hornet.attackDamage == attackDamage);
  }
}
