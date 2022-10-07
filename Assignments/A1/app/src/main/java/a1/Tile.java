package a1;

public class Tile {
  private int food;
  private boolean isHive;
  private boolean isNest;
  private boolean isOnThePath;
  private Tile towardTheHive;
  private Tile towardTheNest;
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
      boolean isHive,
      boolean isNest,
      boolean isOnThePath,
      Tile towardTheHive,
      Tile towardTheNest,
      HoneyBee bee,
      SwarmOfHornets swarm) {
    this.food = food;
    this.isHive = isHive;
    this.isNest = isNest;
    this.isOnThePath = isOnThePath;
    this.towardTheHive = towardTheHive;
    this.towardTheNest = towardTheNest;
    this.bee = bee;
    this.swarm = swarm;
  }

  /**
   * Returns whether or not a hive has been built on this tile.
   *
   * @return A boolean `true` if we have a hive, `false` otherwise.
   */
  public boolean isHive() {
    return isHive;
  }

  /**
   * Returns whether or not a nest has been built on this tile.
   *
   * @return A boolean `true` if we have a nest, `false` otherwise.
   */
  public boolean isNest() {
    return isNest;
  }

  /*
   * Setter for the `isHive` field.
   */
  public void buildHive() {
    isHive = true;
  }

  /** Setter for the `isNest` field. */
  public void buildNest() {
    isNest = true;
  }

  /**
   * Getter for the `isOnThePath` field.
   *
   * @return Whether or not this tile is part of a beehive to nest path.
   */
  public boolean isOnThePath() {
    return isOnThePath;
  }

  /**
   * Getter for the `towardTheHive` hive field.
   *
   * @return A tile that is next toward the beehive.
   */
  public Tile towardTheHive() {
    return towardTheHive;
  }

  /*
   * Getter for the `towardTheNest` field.
   * @return A tile that is next toward the hornets nest.
   */
  public Tile towardTheNest() {
    return towardTheNest;
  }

  /** Make this tile become a part of the path of two input tiles. */
  public void createPath(Tile towardTheHive, Tile towardTheNest) {
    this.towardTheHive = towardTheHive;
    this.towardTheNest = towardTheNest;
    this.isOnThePath = true;
  }

  /**
   * Collect and return all the food on this tile.
   *
   * @return The food on this tile.
   */
  public int collectFood() {
    int ret = this.food;
    this.food = 0;
    return ret;
  }

  /**
   * Store food in this tile.
   *
   * @param food The amount of food to store on this tile.
   */
  public void storeFood(int food) {
    this.food += food;
  }

  /**
   * Getter for the `bee` field.
   *
   * @return The `HoneyBee` instance on this tile, null if its not set.
   */
  public HoneyBee getBee() {
    return bee;
  }

  /**
   * Retrieve the first hornet in this tiles swarm.
   *
   * @return The first `Hornet` instance in the swarm on this tile, null if it's not in the swarm.
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
    if (insect instanceof HoneyBee) return removeBee((HoneyBee) insect);
    if (insect instanceof Hornet) return removeHornet((Hornet) insect);
    return false;
  }

  /**
   * Add a bee to to this tile.
   *
   * @return Whether or not we were able to a bee to this tile.
   */
  private boolean addBee(HoneyBee bee) {
    if (this.bee != null || isNest) return false;
    this.bee = bee;
    this.bee.setPosition(this);
    return true;
  }

  /*
   * Add a hornet to this tile.
   * @return Whether or not we were able to add a hornet to this tile.
   */
  private boolean addHornet(Hornet hornet) {
    if (!isHive && !isNest && !isOnThePath) return false;
    swarm.addHornet(hornet);
    hornet.setPosition(this);
    return true;
  }

  /**
   * Remove a bee from this tile.
   *
   * @return Whether or not we were able to remove a bee from this tile.
   */
  private boolean removeBee(HoneyBee bee) {
    if (this.bee == null || bee != this.bee) return false;
    this.bee.setPosition(null);
    this.bee = null;
    return true;
  }

  /**
   * Remove a hornet from this tile.
   *
   * @return Whether or not we were able to remove a hornet from this tile.
   */
  private boolean removeHornet(Hornet hornet) {
    if (!swarm.removeHornet(hornet)) return false;
    hornet.setPosition(null);
    return true;
  }
}
