package a1;

public class SwarmOfHornets {
  private Hornet[] hornets;
  private int size;

  public SwarmOfHornets() {
    this.size = 0;
    this.hornets = new Hornet[0];
  }

  public int sizeOfSwarm() {
    return this.size;
  }

  public Hornet getFirstHornet() {
    return this.size != 0 ? this.hornets[0] : null;
  }

  public void addHornet(Hornet hornet) {
    // TODO
  }

  public boolean removeHornet() {
    return false;
  }
}
