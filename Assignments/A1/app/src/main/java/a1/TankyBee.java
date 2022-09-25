package a1;

public class TankyBee extends HoneyBee {
  private int attackDamage;
  private int armor;

  /**
   * A constructor for a `TankyBee` instance.
   *
   * @param tile The position of this bee.
   * @param attackDamage The amount of damage this bee can do.
   * @param armor The amount of armor this bee has.
   * @return A new `TankyBee` instance with specified fields.
   */
  public TankyBee(Tile tile, int attackDamage, int armor) {
    super(tile, 30, 3);
    this.attackDamage = attackDamage;
    this.armor = armor;
  }

  /**
   * Attempt to sting a hornet on the same tile as this bee.
   *
   * @return Whether or not we stung a hornet on the same tile.
   */
  public boolean takeAction() {
    Hornet candidate = getPosition().getHornet();
    if (candidate == null) return false;
    candidate.takeDamage(attackDamage);
    return true;
  }

  /**
   * Take damage accounting for the fact that this bee is special and has armor.
   *
   * @param damage The amount of damage to be taken.
   */
  public void takeDamage(int damage) {
    super.takeDamage((int) Math.floor(damage * (100.0 / (100 + armor))));
  }

  @Override
  public boolean equals(Object object) {
    if (object == this) return true;
    if (!(object instanceof TankyBee)) return false;
    TankyBee bee = (TankyBee) object;
    return (bee.getHealth() == getHealth()
        && bee.getPosition().equals(getPosition())
        && bee.getCost() == getCost()
        && bee.attackDamage == attackDamage
        && bee.armor == armor);
  }
}
