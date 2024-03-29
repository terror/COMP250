package a1;

public class Hornet extends Insect {
  private int attackDamage;

  /**
   * Constructor for a `Hornet` instance.
   *
   * @param tile The position of this hornet.
   * @param healthPoints The current health of this hornet.
   * @param attackDamage The amount of damage this hornet can do.
   * @return A new `Hornet` instance with specified fields.
   */
  public Hornet(Tile position, int healthPoints, int attackDamage) {
    super(position, healthPoints);
    this.attackDamage = attackDamage;
  }

  /**
   * Try to sting the bee at this position or move toward the hive.
   *
   * @return Whether or not we were able to sting a bee or move toward the bee hive.
   */
  public boolean takeAction() {
    Tile position = getPosition();

    if (position == null || (position.getBee() == null && position.isHive())) {
      return false;
    }

    if (position.getBee() != null) {
      position.getBee().takeDamage(attackDamage);
    } else {
      Tile next = position.towardTheHive();
      position.removeInsect(this);
      next.addInsect(this);
      setPosition(next);
    }

    return true;
  }

  @Override
  public boolean equals(Object object) {
    if (object == this) return true;
    if (!(object instanceof Hornet)) return false;
    Hornet hornet = (Hornet) object;
    return super.equals(hornet) && attackDamage == hornet.attackDamage;
  }
}
