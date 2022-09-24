package a1;

public class SwarmOfHornets {
  private int left, right, size;
  private Hornet[] hornets;

  /** Default constructor for a swarm of hornets. */
  public SwarmOfHornets() {
    left = right = size = 0;
    hornets = new Hornet[size];
  }

  /**
   * Get the size of the hornet swarm.
   *
   * @return The size of the swarm as an integer.
   */
  public int sizeOfSwarm() {
    return right;
  }

  /**
   * Retrieve the hornet at the front of the queue.
   *
   * @return The hornet at the front of the queue, null if the queue is empty.
   */
  public Hornet getFirstHornet() {
    return left != right ? hornets[left] : null;
  }

  /**
   * Add a hornet to the queue.
   *
   * @param hornet The hornet we want to add to the queue.
   */
  public void addHornet(Hornet hornet) {
    if (right == size) expand();
    hornets[right++] = hornet;
  }

  /**
   * Removes a hornet from the queue.
   *
   * @param hornet The hornet to remove.
   * @return Whether or not we were able to successfully remove the hornet from the queue.
   */
  public boolean removeHornet(Hornet hornet) {
    if (left == right) {
      return false;
    }

    Integer index = indexOf(hornet);

    if (index == null) {
      return false;
    }

    int left = 0;

    for (int i = 0; i < right; ++i) {
      if (hornets[i] != null) {
        Hornet temp = hornets[i];
        hornets[i] = hornets[left];
        hornets[left] = temp;
        ++left;
      }
    }

    --right;

    return true;
  }

  /** Expand the size of the queue by max(1, size * 2). */
  private void expand() {
    Hornet[] expanded = new Hornet[Math.max(1, size * 2)];

    for (int i = 0; i < size; ++i) {
      expanded[i] = hornets[i];
    }

    size = Math.max(1, size * 2);
  }

  /**
   * Return the index of the passed in hornet in the queue, null if its not present in the queue.
   *
   * @param hornet The hornet we want the index of.
   * @return The index of the passed in hornet, null if its not in the queue.
   */
  private Integer indexOf(Hornet hornet) {
    for (int i = 0; i < right; ++i) if (hornets[i] == hornet) return i;
    return null;
  }
}
