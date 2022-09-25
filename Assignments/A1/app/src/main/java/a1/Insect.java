package a1;

public abstract class Insect {
  private Tile tile;
  private int healthPoints;

  /** The super constructor used for `Insect` subclasses. */
  public Insect(Tile tile, int healthPoints) {
    this.tile = tile;
    this.healthPoints = healthPoints;
    if (!tile.addInsect(this)) throw new IllegalArgumentException();
  }

  /**
   * Return the position of this insect.
   *
   * @return The position as a `Tile` instance.
   */
  public final Tile getPosition() {
    return this.tile;
  }

  /**
   * Return the current health of this insect.
   *
   * @return The health of this insect as an integer.
   */
  public final int getHealth() {
    return this.healthPoints;
  }

  /**
   * Set the position of this insect.
   *
   * @param tile The tile in which this insect should be placed on.
   */
  public void setPosition(Tile tile) {
    this.tile = tile;
  }

  /**
   * Take damage from some external caller.
   *
   * @param damage The amount of damage to be taken as an interger.
   */
  public void takeDamage(int damage) {
    healthPoints -=
        this instanceof HoneyBee && tile.isHive() ? Math.floor(damage - (damage * 0.10)) : damage;
    if (healthPoints <= 0) tile.removeInsect(this);
  }

  /** Method subclasses should override if they are to perform actions. */
  public abstract boolean takeAction();

  @Override
  public boolean equals(Object object) {
    if (object == this) return true;
    if (!(object instanceof Insect)) return false;
    Insect insect = (Insect) object;
    return (insect.healthPoints == healthPoints && insect.tile.equals(tile));
  }
}
