package a1;

public class AngryBee extends HoneyBee {
  int attackDamage;

  public AngryBee(Tile tile, int attackDamage) {
    super(tile, 10, 1);
    this.attackDamage = attackDamage;
  }

  public boolean takeAction() {
    Tile position = getPosition();

    if (!position.isOnThePath() && !position.isHive()) {
      return false;
    }

    Hornet candidate = position.getHornet();

    if (candidate != null && candidate.getPosition().isNest()) {
      return false;
    }

    candidate.takeDamage(attackDamage);

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
