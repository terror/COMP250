package a1;

public class Tile {
  private int food;
  private boolean hasBeeHive;
  private boolean hasHornetNest;
  private boolean isPartOfBeeHiveToNestPath;
  private Tile nextTowardHive;
  private Tile nextTowardNest;
  private HoneyBee bee;
  private SwarmOfHornets swarm;

  public Tile() {
    food = 0;
    hasBeeHive = hasHornetNest = isPartOfBeeHiveToNestPath = false;
    nextTowardHive = nextTowardNest = null;
    bee = null;
    swarm = null;
  }

  public Tile(
      int food,
      boolean hasBeeHive,
      boolean hasHornetNest,
      boolean isPartOfBeeHiveToNestPath,
      Tile nextTowardHive,
      Tile nextTowardNest,
      HoneyBee bee,
      SwarmOfHornets swam) {
    this.food = food;
    this.hasBeeHive = hasBeeHive;
    this.hasHornetNest = hasHornetNest;
    this.isPartOfBeeHiveToNestPath = isPartOfBeeHiveToNestPath;
    this.nextTowardHive = nextTowardHive;
    this.nextTowardNest = nextTowardNest;
    this.bee = bee;
    this.swarm = swarm;
  }

  public boolean isHive() {
    return hasBeeHive;
  }

  public boolean isNext() {
    return hasHornetNest;
  }

  public void buildHive() {
    hasBeeHive = true;
  }

  public void buildNest() {
    hasHornetNest = true;
  }

  public boolean isOnThePath() {
    return isPartOfBeeHiveToNestPath;
  }

  public Tile towardTheHive() {
    return nextTowardHive;
  }

  public Tile towardTheNest() {
    return nextTowardNest;
  }

  public void createPath(Tile nextTowardHive, Tile nextTowardNest) {
    this.nextTowardHive = nextTowardHive;
    this.nextTowardNest = nextTowardNest;
  }

  public void storeFood(int food) {
    this.food += food;
  }

  public HoneyBee getBee() {
    return bee;
  }

  public Hornet getHornet() {
    return swarm.getFirstHornet();
  }

  public int getNumOfHornets() {
    return swarm.sizeOfSwarm();
  }

  public boolean addInsect(Insect insect) {
    if (insect instanceof HoneyBee) return addBee((HoneyBee) insect);
    if (insect instanceof Hornet) return addHornet((Hornet) insect);
    return false;
  }

  public boolean removeInsect(Insect insect) {
    if (insect instanceof HoneyBee) return removeBee((HoneyBee) insect);
    if (insect instanceof Hornet) return removeHornet((Hornet) insect);
    return false;
  }

  private boolean addBee(HoneyBee bee) {
    if (bee != null || hasHornetNest) return false;
    this.bee = bee;
    return true;
  }

  private boolean addHornet(Hornet hornet) {
    if (!hasBeeHive && !hasHornetNest && !(nextTowardHive != null || nextTowardNest != null))
      return false;
    swarm.addHornet(hornet);
    return true;
  }

  private boolean removeBee(HoneyBee bee) {
    if (this.bee == null) return false;
    this.bee = null;
    return true;
  }

  private boolean removeHornet(Hornet hornet) {
    if (!this.swarm.removeHornet(hornet)) return false;
    return true;
  }
}
