package a3;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class CatCafe implements Iterable<Cat> {
  /*
   * The root of the tree.
   */
  public CatNode root;

  /*
   * Default constructor.
   */
  public CatCafe() {}

  /*
   * Constructor that sets the root to the passed
   * in node.
   *
   * @param node The new root.
   */
  public CatCafe(CatNode node) {
    root = node;
  }

  /*
   * Constructor that makes a shallow copy of a CatCafe.
   *
   * => New CatNode objects, but same Cat objects.
   *
   *  @param cafe The cafe to perform a shallow copy of.
   */
  public CatCafe(CatCafe cafe) {
    hireMany(cafe.root);
  }

  /*
   * Add a cat to the cafe database.
   *
   * @param cat The cat to add.
   */
  public void hire(Cat cat) {
    if (root == null) root = new CatNode(cat);
    else root = root.hire(cat);
  }

  /*
   * Add many nodes to the current cafe.
   *
   * @param root The root of the tree.
   */
  private void hireMany(CatNode node) {
    if (node == null) return;
    hire(node.catEmployee);
    if (node.junior != null) hireMany(node.junior);
    if (node.senior != null) hireMany(node.senior);
  }

  /*
   * Retire the specified cat.
   *
   * @param c The cat to retire.
   */
  public CatNode retire(Cat c) {
    if (root != null) return root.retire(c);
    return null;
  }

  /*
   * Get the oldest hire in the cafe.
   *
   * @return The most senior cat.
   */
  public Cat findMostSenior() {
    if (root == null) return null;
    return root.findMostSenior();
  }

  /*
   * Get the newest hire in the cafe.
   *
   * @return The most junior cat.
   */
  public Cat findMostJunior() {
    if (root == null) return null;
    return root.findMostJunior();
  }

  /*
   * Returns a list of cats containing the top numOfCatsToHonor cats
   * in the cafe with the thickest fur. Cats are sorted in descending
   * order based on their fur thickness.
   *
   * @param numOfCatsToHonor
   * @return The cat hall of hame.
   */
  public ArrayList<Cat> buildHallOfFame(int numOfCatsToHonor) {
    return buildHallOfFameHelper(root, numOfCatsToHonor, new ArrayList<Cat>());
  }

  /*
   * Recursive helper method for `buildHallOfFame`.
   *
   * @param node The node to start from.
   * @param numOfCatsToHonor The number of cats to get.
   * @param acc An accumulator array.
   * @return The accumulator array full of cats.
   */
  private ArrayList<Cat> buildHallOfFameHelper(
      CatNode node, int numOfCatsToHonor, ArrayList<Cat> acc) {
    if (node == null || acc.size() == numOfCatsToHonor) return acc;
    acc.add(node.catEmployee);
    if (node.junior != null) buildHallOfFameHelper(node.junior, numOfCatsToHonor, acc);
    if (node.senior != null) buildHallOfFameHelper(node.senior, numOfCatsToHonor, acc);
    return acc;
  }

  /*
   * Returns the expected grooming cost the cafe has to incur in the next numDays days.
   *
   * @param numDays
   * @return The total amount of budget expenses with respect to the number of days.
   */
  public double budgetGroomingExpenses(int numDays) {
    return budgetGroomingExpensesHelper(root, numDays);
  }

  /*
   * Recursive helper method for `budgetGroomingExpenses`.
   *
   * @param node The node to start from.
   * @param numDays The number of days to restrict the condition with.
   * @return The total amount of budget expenses with respect to the number of days.
   */
  private double budgetGroomingExpensesHelper(CatNode node, int numDays) {
    if (node == null) return 0;
    return (node.catEmployee.getDaysToNextGrooming() <= numDays
            ? node.catEmployee.getExpectedGroomingCost()
            : 0)
        + budgetGroomingExpensesHelper(node.senior, numDays)
        + budgetGroomingExpensesHelper(node.junior, numDays);
  }

  /*
   * Returns a list of list of Cats.
   *
   * => The cats in the list at index 0 need be groomed in the next week.
   * => The cats in the list at index i need to be groomed in i weeks.
   * => Cats in each sublist are listed in from most senior to most junior.
   *
   * @return An arraylist of cats in the grooming schedule order.
   */
  public ArrayList<ArrayList<Cat>> getGroomingSchedule() {
    return getGroomingScheduleHelper(root, new ArrayList<ArrayList<Cat>>());
  }

  /*
   * Recursive helper method for `getGroomingSchedule`.
   *
   * @return An arraylist of cats in the grooming schedule order.
   */
  private ArrayList<ArrayList<Cat>> getGroomingScheduleHelper(
      CatNode node, ArrayList<ArrayList<Cat>> acc) {
    if (node == null) return acc;
    int curr = node.catEmployee.getDaysToNextGrooming() / 7;
    while (acc.size() < curr + 1) acc.add(new ArrayList<Cat>());
    if (node.junior != null) getGroomingScheduleHelper(node.junior, acc);
    acc.get(curr).add(node.catEmployee);
    if (node.senior != null) getGroomingScheduleHelper(node.senior, acc);
    return acc;
  }

  /*
   * Return our CatCafe iterator implementation.
   */
  public Iterator<Cat> iterator() {
    return new CatCafeIterator();
  }

  public class CatNode {
    public Cat catEmployee;
    public CatNode junior;
    public CatNode parent;
    public CatNode senior;

    public CatNode(Cat c) {
      this.catEmployee = c;
      this.junior = null;
      this.senior = null;
      this.parent = null;
    }

    /*
     * Add the `cat` to the tree rooted at this and returns the root
     * of the resulting tree.
     *
     * @param cat The cat to add.
     * @return The resulting root of the tree.
     */
    public CatNode hire(Cat cat) {
      CatNode curr = new CatNode(cat), root = add(curr);

      // Rotate the tree if necessary
      while (!isMaxHeap(root)) curr.rotate();

      return curr.parent == null ? curr : root;
    }

    /*
     * Add a node to this binary search tree.
     *
     * => Rank nodes based on seniority.
     * => Assumes no two nodes will have the same seniority.
     *
     * @param node The node to add to the tree.
     */
    private CatNode add(CatNode node) {
      CatNode root = this;

      if (node == null) return root;

      if (catEmployee.compareTo(node.catEmployee) > 0) {
        junior = junior == null ? node : junior.add(node);
        junior.parent = root;
      } else {
        senior = senior == null ? node : senior.add(node);
        senior.parent = root;
      }

      return root;
    }

    /*
     * Check if a node exists within this tree.
     *
     * @param root The root of the tree.
     * @param node A node to look for.
     * @return Whether or not the node exists in the tree.
     */
    private boolean exists(CatNode root, Cat node) {
      if (root == null) return false;
      if (root.catEmployee == node) return true;
      return exists(root.junior, node) || exists(root.senior, node);
    }

    /*
     * Accumulate all notes in a tree into a passed in array.
     *
     * @param root The root of the tree.
     * @param arr An accumulator array.
     */
    private void accumulate(CatNode root, ArrayList<Cat> arr) {
      if (root == null) return;
      accumulate(root.junior, arr);
      arr.add(root.catEmployee);
      accumulate(root.senior, arr);
    }

    /*
     * Checks whether or not this the tree rooted at this
     * is a valid max heap with regard to fur.
     *
     * => Each parent node must have greater fur than its children.
     *
     * @param node The node at which to start checking from.
     * @return Whether or not this tree is a max heap.
     */
    private boolean isMaxHeap(CatNode node) {
      int rt = 0;

      if (node == null) return true;

      rt = node.catEmployee.getFurThickness();

      // Property must hold for each subtree
      return (rt > (node.junior != null ? node.junior.catEmployee.getFurThickness() : 0)
              && rt > (node.senior != null ? node.senior.catEmployee.getFurThickness() : 0))
          ? isMaxHeap(node.junior) && isMaxHeap(node.senior)
          : false;
    }

    /*
     * Rotates the tree to fit the max heap property.
     *
     * => The parent node must have thicker fur than its children.
     *
     * @param node The node at which to perform the rotation.
     */
    private void rotate() {
      CatNode par = this.parent;

      if (this.parent.junior == null || !this.parent.junior.catEmployee.equals(this.catEmployee)) {
        par.senior = this.junior;
        if (this.junior != null) this.junior.parent = par;
        this.junior = par;
        if (par.parent != null)
          if (par.parent.junior == par) par.parent.junior = this;
          else par.parent.senior = this;
      } else {
        par.junior = this.senior;
        if (this.senior != null) this.senior.parent = par;
        this.senior = par;
        if (par.parent != null)
          if (par.parent.junior == par) par.parent.junior = this;
          else par.parent.senior = this;
      }

      this.parent = par.parent;
      par.parent = this;
    }

    /*
     * Remove c from the tree rooted at this and returns the root of the
     * resulting tree.
     *
     * @param cat The cat to retire.
     * @return The tree after the retire operation.
     */
    public CatNode retire(Cat cat) {
      CatNode curr = this;

      // Check if this node exists
      if (!exists(curr, cat)) return curr;

      // Retire down the junior subtree
      if (cat.compareTo(curr.catEmployee) < 0) {
        curr.junior = curr.junior.retire(cat);
        return curr;
      }

      // Retire down the senior subtree
      if (cat.compareTo(curr.catEmployee) > 0) {
        curr.senior = curr.senior.retire(cat);
        return curr;
      }

      // If we're trying to retire the root node
      if (root.catEmployee == cat) {
        // The root is the only node in the tree
        if (curr.senior == null && curr.junior == null) {
          root = null;
          return root;
        }

        // We have a junior subtree
        if (curr.senior == null && curr.junior != null) {
          root = curr.junior;
          curr = root;
          curr.parent = null;
          return root;
        }

        // We have a senior subtree
        if (curr.junior == null && curr.senior != null) {
          root = curr.senior;
          curr = root;
          curr.parent = null;
          return root;
        }

        // The accumulator array
        ArrayList<Cat> arr = new ArrayList<Cat>();

        // Take advantage of hire and accumulate respective subtrees
        if (curr.junior.catEmployee.getFurThickness() > curr.senior.catEmployee.getFurThickness()) {
          accumulate(curr.senior, arr);
          curr.junior.parent = null;
          root = curr.junior;
        } else {
          accumulate(curr.junior, arr);
          curr.senior.parent = null;
          root = curr.senior;
        }

        // Add back nodes to the tree
        for (int i = 0; i < arr.size(); i++) root.hire(arr.get(i));

        return root;
      }

      // Handle null cases
      if (curr.junior == null) {
        curr = curr.senior;
      } else if (curr.senior == null) {
        curr = curr.junior;
      } else {
        curr.catEmployee = curr.junior.findMostSenior();
        curr.junior = curr.junior.retire(curr.catEmployee);
      }

      return curr;
    }

    /*
     * Find the cat with highest seniority in the tree rooted at this.
     *
     * @return The most senior cat in the tree.
     */
    public Cat findMostSenior() {
      return findMostSeniorHelper(this);
    }

    /*
     * A recursive helper method for `findMostSenior`.
     *
     * @param node The node to start from.
     * @return The most senior cat in the tree.
     */
    private Cat findMostSeniorHelper(CatNode node) {
      if (node.senior == null) return node.catEmployee;
      return findMostSeniorHelper(node.senior);
    }

    /*
     * Find the cat with lowest seniority in the tree rooted at this.
     *
     * @return The most junior node in the tree.
     */
    public Cat findMostJunior() {
      return findMostJuniorHelper(this);
    }

    /*
     * A recursive helper method for `findMostJunior`.
     *
     * @param node The node to start from.
     * @return The most junior node in the tree.
     */
    private Cat findMostJuniorHelper(CatNode node) {
      if (node.junior == null) return node.catEmployee;
      return findMostJuniorHelper(node.junior);
    }

    /*
     * Public toString implementation for a CatNode.
     *
     * @return A string representation of the tree.
     */
    public String toString() {
      String result = this.catEmployee.toString() + "\n";

      if (this.junior != null) {
        result += "junior than " + this.catEmployee.toString() + " :\n";
        result += this.junior.toString();
      }

      if (this.senior != null) {
        result += "senior than " + this.catEmployee.toString() + " :\n";
        result += this.senior.toString();
      }

      return result;
    }
  }

  private class CatCafeIterator implements Iterator<Cat> {
    private ArrayList<Cat> cats = new ArrayList<Cat>();
    private int index = 0;

    /*
     * Default constructor.
     */
    private CatCafeIterator() {
      fill(root);
    }

    /*
     * Fill the iterator list with each node in the tree
     * via an inorder traversal.
     *
     * @param root The root of the tree.
     */
    private void fill(CatNode root) {
      if (root == null) return;
      if (root.junior != null) fill(root.junior);
      cats.add(root.catEmployee);
      if (root.senior != null) fill(root.senior);
    }

    /*
     * Advance the iterator and return the next
     * element.
     *
     * @return The next element in the array.
     */
    public Cat next() {
      if (hasNext()) return cats.get(index++);
      throw new NoSuchElementException();
    }

    /*
     * Check whether or not our current index
     * does not exceed the array size.
     *
     * @return Whether or not we have a next element.
     */
    public boolean hasNext() {
      return index < cats.size();
    }
  }
}
