package a1;

public abstract class Insect {
  private Tile position;
  private int healthPoints;

  /**
   * The super constructor used for `Insect` subclasses.
   *
   * @param position The position of this insect.
   * @param healthPoints The amount of health points this insect has.
   */
  public Insect(Tile position, int healthPoints) {
    this.position = position;
    this.healthPoints = healthPoints;
    if (!position.addInsect(this)) throw new IllegalArgumentException();
  }

  /**
   * Return the position of this insect.
   *
   * @return The position as a `Tile` instance.
   */
  public final Tile getPosition() {
    return this.position;
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
   * @param position The tile in which this insect should be placed on.
   */
  public void setPosition(Tile position) {
    this.position = position;
  }

  /**
   * Take damage from some external caller. If the health of this insect drops below zero we remove
   * it from its position.
   *
   * @param damage The amount of damage to be taken as an interger.
   */
  public void takeDamage(int damage) {
    healthPoints -=
        this instanceof HoneyBee && position.isHive() ? (int) (damage - (damage * 0.10)) : damage;
    if (healthPoints <= 0) position.removeInsect(this);
  }

  /*
   * A method subclasses should override if they are to perform actions.
   *
   * @return A boolean indicating whether or not this action succeeded.
   */
  public abstract boolean takeAction();

  @Override
  public boolean equals(Object object) {
    if (object == this) return true;
    if (!(object instanceof Insect)) return false;
    Insect insect = (Insect) object;
    return (insect.healthPoints == healthPoints && insect.position.equals(position));
  }
}
