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

  /**
   * Default constructor.
   *
   * @return A default `Tile` instance.
   */
  public Tile() {
    swarm = new SwarmOfHornets();
  }

  /**
   * Constructor with all fields.
   *
   * @return A `Tile` instance with specified fields.
   */
  public Tile(
      int food,
      boolean hasBeeHive,
      boolean hasHornetNest,
      boolean isPartOfBeeHiveToNestPath,
      Tile nextTowardHive,
      Tile nextTowardNest,
      HoneyBee bee,
      SwarmOfHornets swarm) {
    this.food = food;
    this.hasBeeHive = hasBeeHive;
    this.hasHornetNest = hasHornetNest;
    this.isPartOfBeeHiveToNestPath = isPartOfBeeHiveToNestPath;
    this.nextTowardHive = nextTowardHive;
    this.nextTowardNest = nextTowardNest;
    this.bee = bee;
    this.swarm = swarm;
  }

  /**
   * Returns whether or not a hive has been built on this tile.
   *
   * @return A boolean `true` if we have a hive, `false` otherwise.
   */
  public boolean isHive() {
    return hasBeeHive;
  }

  /**
   * Returns whether or not a nest has been built on this tile.
   *
   * @return A boolean `true` if we have a nest, `false` otherwise.
   */
  public boolean isNest() {
    return hasHornetNest;
  }

  /*
   * Setter for the `hasBeeHive` field.
   */
  public void buildHive() {
    hasBeeHive = true;
  }

  /** Setter for the `hasHornetNest` field. */
  public void buildNest() {
    hasHornetNest = true;
  }

  /**
   * Getter for the `isPartOfBeeHiveToNestPath` field.
   *
   * @return Whether or not this tile is part of a beehive to nest path.
   */
  public boolean isOnThePath() {
    return isPartOfBeeHiveToNestPath;
  }

  /**
   * Getter for the `nextToward` hive field.
   *
   * @return A tile that is next toward the beehive.
   */
  public Tile towardTheHive() {
    return nextTowardHive;
  }

  /*
   * Getter for the `nextTowardNest` field.
   * @return A tile that is next toward the hornets nest.
   */
  public Tile towardTheNest() {
    return nextTowardNest;
  }

  /** Make this tile become a part of the path of two input tiles. */
  public void createPath(Tile nextTowardHive, Tile nextTowardNest) {
    this.nextTowardHive = nextTowardHive;
    this.nextTowardNest = nextTowardNest;
    this.isPartOfBeeHiveToNestPath = true;
  }

  /**
   * Getter for the `food` field.
   *
   * @return The food on this tile.
   */
  public int collectFood() {
    return food;
  }

  /** Store food in this tile. */
  public void storeFood(int food) {
    this.food += food;
  }

  /**
   * Getter for the `bee` field.
   *
   * @return The HoneyBee on this tile.
   */
  public HoneyBee getBee() {
    return bee;
  }

  /**
   * Retrieve the first hornet in this tiles swarm.
   *
   * @return The first hornet in the swarm on this tile, null if we don't have a swarm set.
   */
  public Hornet getHornet() {
    return swarm.getFirstHornet();
  }

  /**
   * Get the number of hornets in this tiles swarm
   *
   * @return The number of hornets in this tiles swarm as an interger.
   */
  public int getNumOfHornets() {
    return swarm.sizeOfSwarm();
  }

  /**
   * Add an insect to this tile.
   *
   * @return Whether or not we were able to add the insect.
   */
  public boolean addInsect(Insect insect) {
    if (insect instanceof HoneyBee) return addBee((HoneyBee) insect);
    if (insect instanceof Hornet) return addHornet((Hornet) insect);
    return false;
  }

  /**
   * Remove an insect from this tile.
   *
   * @return Whether or not we were able to remove the insect.
   */
  public boolean removeInsect(Insect insect) {
    if (insect instanceof HoneyBee) return removeBee();
    if (insect instanceof Hornet) return removeHornet((Hornet) insect);
    return false;
  }

  /**
   * Add a bee to to this tile.
   *
   * @return Whether or not we were able to a bee to this tile.
   */
  private boolean addBee(HoneyBee bee) {
    if (this.bee != null || hasHornetNest) return false;
    this.bee = bee;
    return true;
  }

  /*
   * Add a hornet to this tile.
   * @return Whether or not we were able to
   * add a hornet to this tile.
   */
  private boolean addHornet(Hornet hornet) {
    if (!hasBeeHive && !hasHornetNest && !isPartOfBeeHiveToNestPath) return false;
    swarm.addHornet(hornet);
    return true;
  }

  /**
   * Remove a bee from this tile.
   *
   * @return Whether or not we were able to remove a bee from this tile.
   */
  private boolean removeBee() {
    if (bee == null) return false;
    bee = null;
    return true;
  }

  /**
   * Remove a hornet from this tile.
   *
   * @return Whether or not we were able to remove a hornet from this tile.
   */
  private boolean removeHornet(Hornet hornet) {
    return swarm.removeHornet(hornet);
  }
}
