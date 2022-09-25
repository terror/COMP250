package a1;

public class AngryBee extends HoneyBee {
  int attackDamage;

  /**
   * Constructor for a `AngryBee` instance.
   *
   * @param tile The position of this bee.
   * @param attackDamage How much damage this bee can do.
   * @return A new `AngryBee` instance with the specified fields.
   */
  public AngryBee(Tile tile, int attackDamage) {
    super(tile, 10, 1);
    this.attackDamage = attackDamage;
  }

  /**
   * Attempt to sting the first hornet on our path.
   *
   * @return Whether or not we successfully stung a hornet that is along our path.
   */
  public boolean takeAction() {
    Tile position = getPosition();

    if (!position.isOnThePath() && !position.isHive()) {
      return false;
    }

    while (true) {
      if (position == null) return false;
      Hornet candidate = position.getHornet();
      if (candidate == null) position = position.towardTheHive();
      else {
        candidate.takeDamage(attackDamage);
        break;
      }
    }

    return true;
  }

  @Override
  public boolean equals(Object object) {
    if (object == this) return true;
    if (!(object instanceof AngryBee)) return false;
    AngryBee bee = (AngryBee) object;
    return (bee.getHealth() == getHealth()
        && bee.getPosition().equals(getPosition())
        && bee.getCost() == getCost()
        && bee.attackDamage == attackDamage);
  }
}
