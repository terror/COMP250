package a1;

public abstract class Insect {
  private Tile tile;
  private int healthPoints;

  public Insect(Tile tile, int healthPoints) {
    this.tile = tile;
    this.healthPoints = healthPoints;
    if (!tile.addInsect(this)) throw new IllegalArgumentException();
  }

  public final Tile getPosition() {
    return this.tile;
  }

  public final int getHealth() {
    return this.healthPoints;
  }

  public void setPosition(Tile tile) {
    this.tile = tile;
  }

  public void takeDamage(int damage) {
    healthPoints -= this instanceof HoneyBee && tile.isHive() ? damage * 0.10 : damage;
    if (healthPoints <= 0) tile.removeInsect(this);
  }

  public abstract boolean takeAction();

  @Override
  public boolean equals(Object object) {
    if (object == this) return true;
    if (!(object instanceof Insect)) return false;
    Insect insect = (Insect) object;
    return (insect.healthPoints == healthPoints && insect.tile.equals(tile));
  }
}
