package a2;

import java.util.Random;

public class Deck {
  private final int MAX_CARDS_PER_SUIT = 13;
  private final int MAX_NUM_OF_SUITS = 4;
  private final int MIN_CARDS_PER_SUIT = 1;
  private final int MIN_NUM_OF_SUITS = 1;

  public static Random gen = new Random();
  public static String[] suitsInOrder = {"clubs", "diamonds", "hearts", "spades"};

  public Card head;
  public int numOfCards;

  /*
   * The default constructor.
   */
  public Deck() {}

  /*
   * Implements a copy constructor for Deck using Card.getCopy().
   *
   * This method runs in O(n), where n is the number of cards in `d`.
   */
  public Deck(Deck d) {
    Card curr = d.head;

    if (curr != null) {
      do {
        addCard(curr.getCopy());
        curr = curr.next;
      } while (curr != d.head);
    }
  }

  /*
   * Implements a constructor that builds a deck with configuration values:
   * `numOfCardsPerSuit` and `numOfSuits`.
   *
   * This method runs in O(nm) where n = the number of cards per suit and
   * m = the total number of suits.
   */
  public Deck(int numOfCardsPerSuit, int numOfSuits) {
    if (numOfCardsPerSuit < MIN_CARDS_PER_SUIT
        || numOfCardsPerSuit > MAX_CARDS_PER_SUIT
        || numOfSuits < MIN_NUM_OF_SUITS
        || numOfSuits > MAX_NUM_OF_SUITS)
      throw new IllegalArgumentException();

    for (int i = 0; i < numOfSuits; ++i)
      for (int j = 1; j <= numOfCardsPerSuit; ++j)
        addCard(new PlayingCard(suitsInOrder[i], j));

    addCard(new Joker("red"));
    addCard(new Joker("black"));
  }

  /*
   * Adds the specified card at the bottom of the deck.
   *
   * This method runs in O(1).
   */
  public void addCard(Card card) {
    ++numOfCards;

    if (head == null) {
      card.next = card.prev = card;
      head = card;
    } else {
      Card last = head.prev;
      card.next = head;
      head.prev = card;
      card.prev = last;
      last.next = card;
    }
  }

  /*
   * Shuffles the deck using the Fisher-Yates shuffle algorithm.
   *
   * This method runs in O(n) and uses O(n) space, where `n` is the total
   * number of cards in the deck.
   */
  public void shuffle() {
    Card[] cards = new Card[numOfCards];

    Card curr = head;

    if (curr == null) return;

    int index = 0;

    do {
      cards[index++] = curr;
      curr = curr.next;
    } while (curr != head);

    for (int i = cards.length - 1; i > 0; --i) {
      int j = gen.nextInt(i + 1);
      Card temp = cards[i];
      cards[i] = cards[j];
      cards[j] = temp;
    }

    clear();

    for (int i = 0; i < cards.length; ++i)
      addCard(cards[i]);
  }

  /*
   * Returns a reference to the joker with the specified color in
   * the deck.
   *
   * This method runs in O(n), where n is the total number of cards
   * in the deck.
   *
   * @return The located joker, or null if no joker was found.
   */
  public Joker locateJoker(String color) {
    Card curr = head;

    if (curr != null) {
      do {
        if (curr instanceof Joker && ((Joker) curr).redOrBlack.equals(color)) return (Joker) curr;
        curr = curr.next;
      } while (curr != head);
    }

    return null;
  }

  /*
   * Move the specified Card, p positions down the deck. You can
   * assume that the input Card does belong to the deck (hence the deck is
   * not empty).
   *
   * This method runs in O(p).
   *
   * @param c The card to move.
   * @param p The number of positions to move `c`.
   */
  public void moveCard(Card c, int p) {
    Card curr = c;

    for (int i = 0; i < p; ++i)
      curr = curr.next;

    remove(c);

    insertAfter(curr, c);
  }

  /*
   * Performs a triple cut on the deck using the two input cards. You
   * can assume that the input cards belong to the deck and the first one is
   * nearest to the top of the deck.
   *
   * This method runs in O(1).
   *
   * @param left The card closer to head.
   * @param right The card closer to the tail.
   */
  public void tripleCut(Card left, Card right) {
    Card h = head, t = head.prev;

    if (left == null || (left == h && right == t))
      return;

    if (right == t) {
      head = left;
      return;
    }

    if (left == h) {
      head = right.next;
      return;
    }

    Card a = left.prev, b = right.next;

    h.prev = right;
    right.next = h;

    t.next = left;
    left.prev = t;

    a.next = b;
    b.prev = a;

    head = b;
  }

  /*
   * Performs a count cut on the deck. Note that if the value of the
   * bottom card is equal to a multiple of the number of cards in the deck,
   * then the method should not do anything.
   *
   * This method runs in O(n).
   */
  public void countCut() {
    Card curr = head;

    if (numOfCards == 0)
      return;

    int value = head.prev.getValue() % numOfCards;

    if (value == 0 || (numOfCards - 1) == value)
      return;

    for (int i = 0; i < value - 1; i++)
      curr = curr.next;

    Card nh = curr.next, nt = curr;
    Card ot = head.prev, oh = head;

    oh.prev = ot.prev;
    ot.prev.next = oh;

    nh.prev = ot;
    ot.next = nh;

    nt.next = ot;
    ot.prev = nt;

    head = nh;
  }

  /*
   * Returns the card that can be found by looking at the value of the
   * card on the top of the deck, and counting down that many cards. If the
   * card found is a Joker, then the method returns null, otherwise it returns
   * the Card found.
   *
   * This method runs in O(n).
   *
   * @return The found card, or null if its a joker.
   */
  public Card lookUpCard() {
    Card top = head;

    int value = top.getValue();

    while (value != 0) {
      top = top.next;
      --value;
    }

    return top instanceof Joker ? null : top;
  }

  /*
   * Uses the Solitaire algorithm to generate one value for the keystream
   * using this deck.
   *
   * This method runs in O(n).
   *
   * @return The next keystream value.
   */
  public int generateNextKeystreamValue() {
    Joker redJoker = locateJoker("red");

    moveCard(redJoker, 1);

    Joker blackJoker = locateJoker("black");

    moveCard(blackJoker, 2);

    Card first = head;

    while (first != redJoker && first != blackJoker)
      first = first.next;

    tripleCut(first, first == redJoker ? blackJoker : redJoker);

    countCut();

    Card curr = lookUpCard();

    return curr == null ? generateNextKeystreamValue() : curr.getValue();
  }

  /*
   * Removes a card from the deck. Assumes the input `c` is
   * already part of the deck.
   *
   * This method runs in O(1).
   *
   * @card c The card to remove
   */
  private void remove(Card c) {
    c.next.prev = c.prev;
    c.prev.next = c.next;
  }

  /*
   * Insert a card `toInsert` directly after the input card `c`.
   * Assumes `c` and `toInsert` are part of the deck.
   *
   * This method runs in O(1).
   *
   * @param c The card to insert another one after
   * @param toInsert The card to insert
   */
  private void insertAfter(Card c, Card toInsert) {
    toInsert.next = c.next;
    c.next.prev = toInsert;
    toInsert.prev = c;
    c.next = toInsert;
  }

  /*
   * Clears the deck.
   *
   * This method runs in O(1).
   */
  private void clear() {
    head = null;
    numOfCards = 0;
  }

  /*
   * Converts and returns a deck as a string.
   *
   * This method runs in O(n).
   *
   * @return The deck as a string.
   */
  @Override
  public String toString() {
    String ret = "";

    Card curr = head;

    if (curr != null) {
      do {
        ret += curr.toString() + " ";
        curr = curr.next;
      } while (curr != head);
    }

    return ret;
  }

  public abstract class Card {
    public Card next;
    public Card prev;

    public abstract Card getCopy();
    public abstract int getValue();
  }

  public class PlayingCard extends Card {
    public String suit;
    public int rank;

    public PlayingCard(String s, int r) {
      this.suit = s.toLowerCase();
      this.rank = r;
    }

    public String toString() {
      String info = "";

      if (this.rank == 1) {
        info += "A";
      } else if (this.rank > 10) {
        String[] cards = {"Jack", "Queen", "King"};
        info += cards[this.rank - 11].charAt(0);
      } else {
        info += this.rank;
      }

      return (info + this.suit.charAt(0)).toUpperCase();
    }

    public PlayingCard getCopy() {
      return new PlayingCard(this.suit, this.rank);
    }

    public int getValue() {
      int i;

      for (i = 0; i < suitsInOrder.length; i++) {
        if (this.suit.equals(suitsInOrder[i])) break;
      }

      return this.rank + 13 * i;
    }
  }

  public class Joker extends Card {
    public String redOrBlack;

    public Joker(String c) {
      if (!c.equalsIgnoreCase("red") && !c.equalsIgnoreCase("black"))
        throw new IllegalArgumentException("Jokers can only be red or black");
      this.redOrBlack = c.toLowerCase();
    }

    public String toString() {
      return (this.redOrBlack.charAt(0) + "J").toUpperCase();
    }

    public Joker getCopy() {
      return new Joker(this.redOrBlack);
    }

    public int getValue() {
      return numOfCards - 1;
    }

    public String getColor() {
      return this.redOrBlack;
    }
  }
}
