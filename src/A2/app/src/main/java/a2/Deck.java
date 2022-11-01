package a2;

import java.util.Random;

public class Deck {
  public static String[] suitsInOrder = {"clubs", "diamonds", "hearts", "spades"};
  public static Random gen = new Random();

  public Card head;
  public int numOfCards;

  private final int MAX_CARDS_PER_SUIT = 13;
  private final int MAX_NUM_OF_SUITS = 4;
  private final int MIN_CARDS_PER_SUIT = 1;
  private final int MIN_NUM_OF_SUITS = 1;

  public Deck(int numOfCardsPerSuit, int numOfSuits) {
    if (numOfCardsPerSuit < MIN_CARDS_PER_SUIT
        || numOfCardsPerSuit > MAX_CARDS_PER_SUIT
        || numOfSuits < MIN_NUM_OF_SUITS
        || numOfSuits > MAX_NUM_OF_SUITS) throw new IllegalArgumentException();

    for (int i = 1; i <= numOfCardsPerSuit; ++i) {
      for (int j = 0; j < numOfSuits; ++j) {
        addCard(new PlayingCard(suitsInOrder[j], i));
        ++numOfCards;
      }
    }
  }

  /*
   * Implements a copy constructor for Deck using Card.getCopy().
   * This method runs in O(n), where n is the number of cards in d.
   */
  public Deck(Deck d) {
    Card curr = d.head;

    while (curr != null) {
      addCard(curr.getCopy());
      ++numOfCards;
      curr = curr.next;
    }
  }

  /*
   * For testing purposes we need a default constructor.
   */
  public Deck() {}

  /*
   * Adds the specified card at the bottom of the deck. This
   * method runs in O(1).
   */
  public void addCard(Card c) {
    if (head == null) {
      c.next = c.prev = c;
      head = c;
    } else {
      Card last = head.prev;
      c.next = head;
      head.prev = c;
      c.prev = last;
      last.next = c;
    }
  }

  /*
   * TODO: Shuffles the deck using the algorithm described in the pdf.
   * This method runs in O(n) and uses O(n) space, where n is the total
   * number of cards in the deck.
   */
  public void shuffle() {
    /**** ADD CODE HERE ****/
  }

  /*
   * Returns a reference to the joker with the specified color in
   * the deck. This method runs in O(n), where n is the total number of
   * cards in the deck.
   */
  public Joker locateJoker(String color) {
    Card curr = head;

    while (curr != null) {
      if (curr instanceof Joker && ((Joker) curr).redOrBlack.equals(color)) return (Joker) curr;
      curr = curr.next;
    }

    return null;
  }

  /*
   * TODO: Moved the specified Card, p positions down the deck. You can
   * assume that the input Card does belong to the deck (hence the deck is
   * not empty). This method runs in O(p).
   */
  public void moveCard(Card c, int p) {
    /**** ADD CODE HERE ****/
  }

  /*
   * TODO: Performs a triple cut on the deck using the two input cards. You
   * can assume that the input cards belong to the deck and the first one is
   * nearest to the top of the deck. This method runs in O(1)
   */
  public void tripleCut(Card firstCard, Card secondCard) {
    /**** ADD CODE HERE ****/
  }

  /*
   * TODO: Performs a count cut on the deck. Note that if the value of the
   * bottom card is equal to a multiple of the number of cards in the deck,
   * then the method should not do anything. This method runs in O(n).
   */
  public void countCut() {
    /**** ADD CODE HERE ****/
  }

  /*
   * TODO: Returns the card that can be found by looking at the value of the
   * card on the top of the deck, and counting down that many cards. If the
   * card found is a Joker, then the method returns null, otherwise it returns
   * the Card found. This method runs in O(n).
   */
  public Card lookUpCard() {
    /**** ADD CODE HERE ****/
    return null;
  }

  /*
   * TODO: Uses the Solitaire algorithm to generate one value for the keystream
   * using this deck. This method runs in O(n).
   */
  public int generateNextKeystreamValue() {
    /**** ADD CODE HERE ****/
    return 0;
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
        // info += "Ace";
        info += "A";
      } else if (this.rank > 10) {
        String[] cards = {"Jack", "Queen", "King"};
        // info += cards[this.rank - 11];
        info += cards[this.rank - 11].charAt(0);
      } else {
        info += this.rank;
      }
      // info += " of " + this.suit;
      info = (info + this.suit.charAt(0)).toUpperCase();
      return info;
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
      // return this.redOrBlack + " Joker";
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
