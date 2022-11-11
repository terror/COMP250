package a2;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/* ======== TESTER #1 ======== */

class AddCard_AllRef implements Runnable {
  @Override
  public void run() {
    Deck deck = new Deck();
    Deck.Card c1 = deck.new PlayingCard(Deck.suitsInOrder[0], 1); // AC
    Deck.Card c2 = deck.new PlayingCard(Deck.suitsInOrder[0], 2); // 2C
    Deck.Card c3 = deck.new PlayingCard(Deck.suitsInOrder[0], 3); // 3C
    deck.addCard(c1);
    deck.addCard(c2);
    deck.addCard(c3);
    boolean c1ref = c1.next == c2 && c1.prev == c3;
    boolean c2ref = c2.next == c3 && c2.prev == c1;
    boolean c3ref = c3.next == c1 && c3.prev == c2;
    if (!(c1ref && c2ref && c3ref)) {
      throw new AssertionError("Circular doubly linked list references are not correctly set up.");
    }
    System.out.println("Test passed.");
  }
}

class AddCard_CheckHead implements Runnable {
  @Override
  public void run() {
    Deck deck = new Deck();
    Deck.Card c1 = deck.new PlayingCard(Deck.suitsInOrder[0], 1); // AC
    Deck.Card c8 = deck.new PlayingCard(Deck.suitsInOrder[0], 8); // 8C
    deck.addCard(c1);
    deck.addCard(c8);
    Deck.Card head = deck.head;
    if (head != c1) {
      throw new AssertionError(
          "addCard should add the input card to the bottom of the deck.\n"
              + "Expected head to be "
              + c1
              + " but got "
              + head);
    }
    System.out.println("Test passed.");
  }
}

class AddCard_Circular implements Runnable {
  @Override
  public void run() {
    Deck deck = new Deck();
    Deck.Card c1 = deck.new PlayingCard(Deck.suitsInOrder[0], 1); // AC
    Deck.Card c2 = deck.new PlayingCard(Deck.suitsInOrder[0], 2); // 2C
    Deck.Card c3 = deck.new PlayingCard(Deck.suitsInOrder[0], 3); // 3C
    Deck.Card c8 = deck.new PlayingCard(Deck.suitsInOrder[0], 8); // 8C
    deck.addCard(c1);
    deck.addCard(c2);
    deck.addCard(c3);
    deck.addCard(c8);
    if (!(c1.prev == c8 && c8.next == c1)) {
      throw new AssertionError("Circular references are not correctly set up.");
    }
    System.out.println("Test passed.");
  }
}

class AddCard_NumOfCards implements Runnable {
  @Override
  public void run() {
    Deck deck = new Deck();
    Deck.Card c1 = deck.new PlayingCard(Deck.suitsInOrder[0], 1); // AC
    Deck.Card c2 = deck.new PlayingCard(Deck.suitsInOrder[0], 2); // 2C
    Deck.Card c3 = deck.new PlayingCard(Deck.suitsInOrder[0], 3); // 3C
    Deck.Card d11 = deck.new PlayingCard(Deck.suitsInOrder[1], 11); // JD
    deck.addCard(c1);
    deck.addCard(c2);
    deck.addCard(d11);
    deck.addCard(c3);
    int expected = 4;
    int result = deck.numOfCards;
    if (expected != result) {
      throw new AssertionError("numOfCards is not correctly updated.");
    }
    System.out.println("Test passed.");
  }
}

class AddCard_SingleCard implements Runnable {
  @Override
  public void run() {
    Deck deck = new Deck();
    Deck.Card c1 = deck.new PlayingCard(Deck.suitsInOrder[0], 1); // AC
    deck.addCard(c1);
    if (!(c1.prev == c1 && c1.next == c1)) {
      throw new AssertionError(
          "Card references are not correctly set up when the deck contains only ONE card.");
    }
    System.out.println("Test passed.");
  }
}

class DeepCopy_CheckRefs implements Runnable {
  @Override
  public void run() {
    HashSet<Deck.Card> cardSet = new HashSet<>();
    Deck deck = new Deck();
    cardSet.add(deck.new PlayingCard(Deck.suitsInOrder[0], 1));
    cardSet.add(deck.new PlayingCard(Deck.suitsInOrder[0], 3));
    cardSet.add(deck.new PlayingCard(Deck.suitsInOrder[0], 5));
    cardSet.add(deck.new Joker("black"));
    cardSet.add(deck.new PlayingCard(Deck.suitsInOrder[1], 2));
    cardSet.add(deck.new PlayingCard(Deck.suitsInOrder[2], 4));
    cardSet.add(deck.new PlayingCard(Deck.suitsInOrder[3], 6));

    for (Deck.Card c : cardSet) {
      deck.addCard(c);
    }

    Deck copy = new Deck(deck); // should do a deep copy

    Deck.Card cur = copy.head;
    for (int i = 0; i < cardSet.size(); i++) {
      if (cardSet.contains(cur)) {
        throw new AssertionError("Deep copy must create new object.");
      }
      cur = cur.next;
    }

    System.out.println("Test passed.");
  }
}

class DeepCopy_CircularNext implements Runnable {
  @Override
  public void run() {

    Deck deck = new Deck();
    Deck.Card[] cards =
        new Deck.Card[] {
          deck.new PlayingCard(Deck.suitsInOrder[0], 1),
          deck.new PlayingCard(Deck.suitsInOrder[0], 3),
          deck.new PlayingCard(Deck.suitsInOrder[0], 5),
          deck.new Joker("black"),
          deck.new PlayingCard(Deck.suitsInOrder[1], 2),
          deck.new PlayingCard(Deck.suitsInOrder[2], 4),
          deck.new PlayingCard(Deck.suitsInOrder[3], 6)
        };

    for (Deck.Card c : cards) {
      deck.addCard(c);
    }

    Deck copy = new Deck(deck); // should do a deep copy

    Deck.Card cur = copy.head;
    for (int i = 0; i < cards.length; i++) {
      if (cur == null) {
        throw new AssertionError("Either head or one of the next pointers is null.");
      }

      if (cards[i].getClass()
          != cur.getClass()) { // Either one is Joker and other is PlayingCard or vice versa
        throw new AssertionError(
            "The card at the next position of ."
                + i
                + " from head, has type: "
                + cur.getClass().getName()
                + " but expected: "
                + cards[i].getClass().getName());
      }

      if (cur instanceof Deck.PlayingCard) { // both are PlayingCard
        if (cards[i].getValue() != cur.getValue()) {
          throw new AssertionError(
              "The card at the next position of ."
                  + i
                  + " from head must have value: "
                  + cards[i].getValue()
                  + " but got: "
                  + cur.getValue());
        }
      } else { // both are Joker
        String cardColor = ((Deck.Joker) cards[i]).getColor();
        String curColor = ((Deck.Joker) cur).getColor();
        if (!cardColor.equals(curColor)) {
          throw new AssertionError(
              "The joker card at the next position of ."
                  + i
                  + " from head must have color: "
                  + cardColor
                  + " but got: "
                  + curColor);
        }
      }
      cur = cur.next;
    }

    if (cur != copy.head) {
      throw new AssertionError("The last card's next does not point to the head.");
    }

    System.out.println("Test passed.");
  }
}

class DeepCopy_CircularPrev implements Runnable {
  @Override
  public void run() {

    Deck deck = new Deck();
    Deck.Card[] cards =
        new Deck.Card[] {
          deck.new PlayingCard(Deck.suitsInOrder[0], 1),
          deck.new PlayingCard(Deck.suitsInOrder[0], 3),
          deck.new PlayingCard(Deck.suitsInOrder[0], 5),
          deck.new Joker("black"),
          deck.new PlayingCard(Deck.suitsInOrder[1], 2),
          deck.new PlayingCard(Deck.suitsInOrder[2], 4),
          deck.new PlayingCard(Deck.suitsInOrder[3], 6)
        };

    for (Deck.Card c : cards) {
      deck.addCard(c);
    }

    Deck copy = new Deck(deck); // should do a deep copy

    Deck.Card cur = copy.head;
    for (int j = 0; j < cards.length; j++) {
      int i = Math.floorMod(-j, cards.length); // i goes 0, n-1, n-2, ..., 1
      if (cur == null) {
        throw new AssertionError("Either head or one of the prev pointers is null.");
      }

      if (cards[i].getClass()
          != cur.getClass()) { // Either one is Joker and other is PlayingCard or vice versa
        throw new AssertionError(
            "The card at the prev position of ."
                + j
                + " from head, has type: "
                + cur.getClass().getName()
                + " but expected: "
                + cards[i].getClass().getName());
      }

      if (cur instanceof Deck.PlayingCard) { // both are PlayingCard
        if (cards[i].getValue() != cur.getValue()) {
          throw new AssertionError(
              "The card at the prev position of ."
                  + j
                  + " from head must have value: "
                  + cards[i].getValue()
                  + " but got: "
                  + cur.getValue());
        }
      } else { // both are Joker
        String cardColor = ((Deck.Joker) cards[i]).getColor();
        String curColor = ((Deck.Joker) cur).getColor();
        if (!cardColor.equals(curColor)) {
          throw new AssertionError(
              "The joker card at the prev position of ."
                  + j
                  + " from head must have color: "
                  + cardColor
                  + " but got: "
                  + curColor);
        }
      }
      cur = cur.prev;
    }

    if (cur != copy.head) {
      throw new AssertionError("The last card's prev does not point to the head.");
    }

    System.out.println("Test passed.");
  }
}

class LocateJoker_Test1 implements Runnable {
  @Override
  public void run() {
    Deck tdeck = new Deck();
    Deck.Card c1 = tdeck.new PlayingCard(Deck.suitsInOrder[0], 1); // AC
    Deck.Card c2 = tdeck.new PlayingCard(Deck.suitsInOrder[0], 2); // 2C
    Deck.Card c3 = tdeck.new PlayingCard(Deck.suitsInOrder[0], 3); // 3C
    Deck.Card expected = tdeck.new Joker("red");

    tdeck.addCard(c1);
    tdeck.addCard(c2);
    tdeck.addCard(c3);
    tdeck.addCard(expected);

    Deck.Card received = tdeck.locateJoker("red");
    if (expected != received) {
      throw new AssertionError(
          "The reference returned was incorrect."
              + "Expected the card "
              + expected.toString()
              + " with reference "
              + expected.hashCode()
              + " but instead got the card "
              + received
              + " with reference "
              + received.hashCode());
    }
    System.out.println("Test passed.");
  }
}

class LocateJoker_Test2 implements Runnable {
  @Override
  public void run() {
    Deck tdeck = new Deck();
    Deck.Card c1 = tdeck.new PlayingCard(Deck.suitsInOrder[0], 1); // AC
    Deck.Card c2 = tdeck.new Joker("red");
    Deck.Card c3 = tdeck.new PlayingCard(Deck.suitsInOrder[0], 2); // 2C
    Deck.Card c4 = tdeck.new PlayingCard(Deck.suitsInOrder[0], 3); // 3C

    Deck.Card expected = tdeck.new Joker("black");

    tdeck.addCard(c1);
    tdeck.addCard(c2);
    tdeck.addCard(c3);
    tdeck.addCard(c4);
    tdeck.addCard(expected);

    Deck.Card received = tdeck.locateJoker("black");
    if (expected != received) {
      throw new AssertionError(
          "The reference returned was incorrect."
              + "Expected the card "
              + expected.toString()
              + " with reference "
              + expected.hashCode()
              + " but instead got the card "
              + received
              + " with reference "
              + received.hashCode());
    }
    System.out.println("Test passed.");
  }
}

class LocateJoker_Test3 implements Runnable {
  @Override
  public void run() {
    Deck tdeck = new Deck();
    Deck.Card c1 = tdeck.new PlayingCard(Deck.suitsInOrder[0], 1); // AC
    Deck.Card expected = tdeck.new Joker("red");
    Deck.Card c2 = tdeck.new Joker("black");
    Deck.Card c3 = tdeck.new PlayingCard(Deck.suitsInOrder[0], 2); // 2C
    Deck.Card c4 = tdeck.new PlayingCard(Deck.suitsInOrder[0], 3); // 3C

    tdeck.addCard(c1);
    tdeck.addCard(expected);
    tdeck.addCard(c2);
    tdeck.addCard(c3);
    tdeck.addCard(c4);

    Deck.Card received = tdeck.locateJoker("red");
    if (expected != received) {
      throw new AssertionError(
          "The reference returned was incorrect."
              + "Expected the card "
              + expected.toString()
              + " with reference "
              + expected.hashCode()
              + " but instead got the card "
              + received
              + " with reference "
              + received.hashCode());
    }
    System.out.println("Test passed.");
  }
}

class LookUpCard_Test1 implements Runnable {
  @Override
  public void run() {
    Deck tdeck = new Deck();
    Deck.Card c1 = tdeck.new PlayingCard(Deck.suitsInOrder[0], 1); // AC
    Deck.Card expected = tdeck.new PlayingCard(Deck.suitsInOrder[0], 2); // 2C
    Deck.Card c3 = tdeck.new PlayingCard(Deck.suitsInOrder[0], 3); // 3C

    tdeck.addCard(c1);
    tdeck.addCard(expected);
    tdeck.addCard(c3);

    Deck.Card received = tdeck.lookUpCard();
    if (expected != received) {
      throw new AssertionError(
          "The reference returned was incorrect. The second card should have "
              + "been returned. Expected the card "
              + expected.toString()
              + " with reference "
              + expected.hashCode()
              + " but instead got the card "
              + received
              + " with reference "
              + received.hashCode());
    }
    System.out.println("Test passed.");
  }
}

class LookUpCard_Test2 implements Runnable {
  @Override
  public void run() {
    Deck tdeck = new Deck();
    Deck.Card c1 = tdeck.new PlayingCard(Deck.suitsInOrder[0], 6); // 6C
    Deck.Card c2 = tdeck.new PlayingCard(Deck.suitsInOrder[1], 2); // 2D
    Deck.Card c3 = tdeck.new PlayingCard(Deck.suitsInOrder[2], 3); // 3H
    Deck.Card c4 = tdeck.new PlayingCard(Deck.suitsInOrder[2], 4); // 4H
    Deck.Card c5 = tdeck.new PlayingCard(Deck.suitsInOrder[2], 5); // 5H
    Deck.Card c6 = tdeck.new PlayingCard(Deck.suitsInOrder[2], 6); // 6H
    Deck.Card expected = tdeck.new PlayingCard(Deck.suitsInOrder[2], 7); // 7H
    Deck.Card c7 = tdeck.new PlayingCard(Deck.suitsInOrder[2], 8); // 8H
    Deck.Card c8 = tdeck.new PlayingCard(Deck.suitsInOrder[2], 9); // 9H

    tdeck.addCard(c1);
    tdeck.addCard(c2);
    tdeck.addCard(c3);
    tdeck.addCard(c4);
    tdeck.addCard(c5);
    tdeck.addCard(c6);
    tdeck.addCard(expected);
    tdeck.addCard(c7);
    tdeck.addCard(c8);

    Deck.Card received = tdeck.lookUpCard();
    if (expected != received) {
      throw new AssertionError(
          "The reference returned was incorrect. "
              + "Expected the card "
              + expected.toString()
              + " with reference "
              + expected.hashCode()
              + " but instead got the card "
              + received
              + " with reference "
              + received.hashCode());
    }
    System.out.println("Test passed.");
  }
}

class LookUpCard_Test3 implements Runnable {
  @Override
  public void run() {
    Deck tdeck = new Deck();
    Deck.Card c1 = tdeck.new PlayingCard(Deck.suitsInOrder[0], 8); // 6C
    Deck.Card c2 = tdeck.new PlayingCard(Deck.suitsInOrder[1], 2); // 2D
    Deck.Card c3 = tdeck.new PlayingCard(Deck.suitsInOrder[2], 3); // 3H
    Deck.Card c4 = tdeck.new PlayingCard(Deck.suitsInOrder[2], 4); // 4H
    Deck.Card c5 = tdeck.new PlayingCard(Deck.suitsInOrder[2], 5); // 5H
    Deck.Card c6 = tdeck.new PlayingCard(Deck.suitsInOrder[2], 6); // 6H
    Deck.Card c7 = tdeck.new PlayingCard(Deck.suitsInOrder[2], 7); // 7H
    Deck.Card c8 = tdeck.new PlayingCard(Deck.suitsInOrder[2], 8); // 8H
    Deck.Card c9 = tdeck.new Joker("red"); // JR

    tdeck.addCard(c1);
    tdeck.addCard(c2);
    tdeck.addCard(c3);
    tdeck.addCard(c4);
    tdeck.addCard(c5);
    tdeck.addCard(c6);
    tdeck.addCard(c7);
    tdeck.addCard(c8);
    tdeck.addCard(c9);

    Deck.Card received = tdeck.lookUpCard();
    if (received != null) {
      throw new AssertionError("Null should be returned in case a Joker is found.");
    }
    System.out.println("Test passed.");
  }
}

class MoveCard_CheckNext1 implements Runnable {
  @Override
  public void run() {
    Deck deck = new Deck();
    Deck.Card[] cards =
        new Deck.Card[] {
          deck.new PlayingCard(Deck.suitsInOrder[0], 1),
          deck.new PlayingCard(Deck.suitsInOrder[0], 3),
          deck.new PlayingCard(Deck.suitsInOrder[0], 5),
          deck.new Joker("black"),
          deck.new PlayingCard(Deck.suitsInOrder[1], 2),
          deck.new PlayingCard(Deck.suitsInOrder[2], 4),
          deck.new PlayingCard(Deck.suitsInOrder[3], 6)
        };

    for (Deck.Card c : cards) {
      deck.addCard(c);
    }

    Deck.Card[] expected =
        new Deck.Card[] {cards[0], cards[1], cards[3], cards[4], cards[5], cards[2], cards[6]};

    deck.moveCard(cards[2], 3);

    Deck.Card cur = deck.head;
    for (int i = 0; i < expected.length; i++) {
      // System.out.println(cur);
      if (expected[i] != cur) {
        throw new AssertionError("Expect card: " + expected[i] + " but got: " + cur);
      }
      cur = cur.next;
    }
    System.out.println("Test passed.");
  }
}

class MoveCard_CheckNext2 implements Runnable {
  @Override
  public void run() {
    Deck deck = new Deck();
    Deck.Card[] cards =
        new Deck.Card[] {
          deck.new PlayingCard(Deck.suitsInOrder[0], 1),
          deck.new PlayingCard(Deck.suitsInOrder[0], 3),
          deck.new PlayingCard(Deck.suitsInOrder[0], 5),
          deck.new Joker("black"),
          deck.new PlayingCard(Deck.suitsInOrder[1], 2),
          deck.new PlayingCard(Deck.suitsInOrder[2], 4),
          deck.new PlayingCard(Deck.suitsInOrder[3], 6)
        };

    for (Deck.Card c : cards) {
      deck.addCard(c);
    }

    Deck.Card[] expected =
        new Deck.Card[] {cards[0], cards[3], cards[1], cards[2], cards[4], cards[5], cards[6]};

    deck.moveCard(cards[3], 4);

    Deck.Card cur = deck.head;
    for (int i = 0; i < expected.length; i++) {
      // System.out.println(cur);
      if (expected[i] != cur) {
        throw new AssertionError("Expect card: " + expected[i] + " but got: " + cur);
      }
      cur = cur.next;
    }
    System.out.println("Test passed.");
  }
}

class MoveCard_CheckPrev1 implements Runnable {
  @Override
  public void run() {
    Deck deck = new Deck();
    Deck.Card[] cards =
        new Deck.Card[] {
          deck.new PlayingCard(Deck.suitsInOrder[0], 1),
          deck.new PlayingCard(Deck.suitsInOrder[0], 3),
          deck.new PlayingCard(Deck.suitsInOrder[0], 5),
          deck.new Joker("black"),
          deck.new PlayingCard(Deck.suitsInOrder[1], 2),
          deck.new PlayingCard(Deck.suitsInOrder[2], 4),
          deck.new PlayingCard(Deck.suitsInOrder[3], 6)
        };

    for (Deck.Card c : cards) {
      deck.addCard(c);
    }

    Deck.Card[] expected =
        new Deck.Card[] {cards[0], cards[1], cards[3], cards[4], cards[5], cards[2], cards[6]};

    deck.moveCard(cards[2], 3);

    Deck.Card cur = deck.head;
    for (int j = 0; j < expected.length; j++) {
      int i = Math.floorMod(-j, expected.length); // i goes 0, n-1, n-2, ..., 1
      // System.out.println(cur);
      if (expected[i] != cur) {
        throw new AssertionError("Expect card: " + expected[i] + " but got: " + cur);
      }
      cur = cur.prev;
    }
    System.out.println("Test passed.");
  }
}

class MoveCard_CheckPrev2 implements Runnable {
  @Override
  public void run() {
    Deck deck = new Deck();
    Deck.Card[] cards =
        new Deck.Card[] {
          deck.new PlayingCard(Deck.suitsInOrder[0], 1),
          deck.new PlayingCard(Deck.suitsInOrder[0], 3),
          deck.new PlayingCard(Deck.suitsInOrder[0], 5),
          deck.new Joker("black"),
          deck.new PlayingCard(Deck.suitsInOrder[1], 2),
          deck.new PlayingCard(Deck.suitsInOrder[2], 4),
          deck.new PlayingCard(Deck.suitsInOrder[3], 6)
        };

    for (Deck.Card c : cards) {
      deck.addCard(c);
    }

    Deck.Card[] expected =
        new Deck.Card[] {cards[0], cards[3], cards[1], cards[2], cards[4], cards[5], cards[6]};

    deck.moveCard(cards[3], 4);

    Deck.Card cur = deck.head;
    for (int j = 0; j < expected.length; j++) {
      int i = Math.floorMod(-j, expected.length); // i goes 0, n-1, n-2, ..., 1
      // System.out.println(cur);
      if (expected[i] != cur) {
        throw new AssertionError("Expect card: " + expected[i] + " but got: " + cur);
      }
      cur = cur.prev;
    }
    System.out.println("Test passed.");
  }
}

class Shuffle_Empty implements Runnable {
  @Override
  public void run() {
    Deck deck = new Deck();

    deck.shuffle();

    if (!(deck.head == null)) {
      throw new AssertionError("Deck should be empty.");
    }
    System.out.println("Test passed.");
  }
}

class Shuffle_Example implements Runnable {
  @Override
  public void run() {
    Deck deck = new Deck();
    // example in instruction pdf
    // AC 2C 3C 4C 5C AD 2D 3D 4D 5D RJ BJ
    Deck.Card[] arrDeck = new Deck.Card[12];
    for (int i = 0; i < 10; i++) {
      int suit = i / 5;
      int rank = i % 5 + 1;
      Deck.Card card = deck.new PlayingCard(Deck.suitsInOrder[suit], rank);
      arrDeck[i] = card;
      deck.addCard(card);
    }
    Deck.Card rj = deck.new Joker("red");
    Deck.Card bj = deck.new Joker("black");
    arrDeck[10] = rj;
    arrDeck[11] = bj;
    deck.addCard(rj);
    deck.addCard(bj);

    int seed = 10;
    Deck.gen.setSeed(seed);
    deck.shuffle();

    // expected result
    // 3C 3D AD 5C BJ 2C 2D 4D AC RJ 4C 5D

    int[] shuffledIndex = {2, 7, 5, 4, 11, 1, 6, 8, 0, 10, 3, 9};

    // .next references
    Deck.Card cur = deck.head;
    for (int i = 0; i < 12; i++) {
      Deck.Card expected = arrDeck[shuffledIndex[i]];
      if (cur.getValue() != expected.getValue()) {
        throw new AssertionError(
            "Deck is not correctly shuffled.\n"
                + "Forward references are not correctly set up. "
                + "Expected card at index "
                + i
                + " iterating using .next is "
                + expected
                + " but got "
                + cur);
      }
      cur = cur.next;
    }

    // .prev references
    cur = deck.head.prev;
    for (int i = 11; i >= 0; i--) {
      Deck.Card expected = arrDeck[shuffledIndex[i]];
      if (cur.getValue() != expected.getValue()) {
        throw new AssertionError(
            "Deck is not correctly shuffled.\n"
                + "Backward references are not correctly set up. "
                + "Expected card at index "
                + i
                + " iterating using .prev is "
                + expected
                + " but got "
                + cur);
      }
      cur = cur.prev;
    }
    System.out.println("Test passed.");
  }
}

class Shuffle_FullDeck implements Runnable {
  @Override
  public void run() {
    Deck deck = new Deck();
    // all 54 cards
    Deck.Card[] arrDeck = new Deck.Card[54];
    for (int i = 0; i < 52; i++) {
      int suit = i / 13;
      int rank = i % 13 + 1;
      Deck.Card card = deck.new PlayingCard(Deck.suitsInOrder[suit], rank);
      arrDeck[i] = card;
      deck.addCard(card);
    }
    Deck.Card rj = deck.new Joker("red");
    Deck.Card bj = deck.new Joker("black");
    arrDeck[52] = rj;
    arrDeck[53] = bj;
    deck.addCard(rj);
    deck.addCard(bj);

    int seed = 10;
    Deck.gen.setSeed(seed);
    deck.shuffle();

    // expected result
    // 7S QD 7H JH KH KD 8C 4C 9S JD KC 9C 5C QC 2S 5S 10H 10D
    // 4S 5D 6H 4D 9D 8D 3H 6D 4H 7C RJ 9H 3C 2D JC 6C 8H JS 5H
    // AH BJ 3S 6S 3D QS AS 7D 2C AD KS 10S 8S 10C QH AC 2H
    int[] shuffledIndex = {
      45, 24, 32, 36, 38, 25, 7, 3, 47, 23, 12, 8, 4, 11, 40, 43, 35, 22, 42, 17, 31, 16, 21, 20,
      28, 18, 29, 6, 52, 34, 2, 14, 10, 5, 33, 49, 30, 26, 53, 41, 44, 15, 50, 39, 19, 1, 13, 51,
      48, 46, 9, 37, 0, 27
    };

    // .next references
    Deck.Card cur = deck.head;
    for (int i = 0; i < 54; i++) {
      Deck.Card expected = arrDeck[shuffledIndex[i]];
      if (cur.getValue() != expected.getValue()) {
        throw new AssertionError(
            "Deck is not correctly shuffled.\n"
                + "Forward references are not correctly set up. "
                + "Expected card at index "
                + i
                + " is "
                + expected
                + " but got "
                + cur);
      }
      cur = cur.next;
    }

    // .prev references
    cur = deck.head.prev;
    for (int i = 53; i >= 0; i--) {
      Deck.Card expected = arrDeck[shuffledIndex[i]];
      if (cur.getValue() != expected.getValue()) {
        throw new AssertionError(
            "Deck is not correctly shuffled.\n"
                + "Backward references are not correctly set up. "
                + "Expected card at index "
                + i
                + " iterating using .prev is "
                + expected
                + " but got "
                + cur);
      }
      cur = cur.prev;
    }
    System.out.println("Test passed.");
  }
}

class Shuffle_NewCard implements Runnable {
  @Override
  public void run() {
    Deck deck = new Deck();
    // example in instruction pdf
    // AC 2C 3C 4C 5C AD 2D 3D 4D 5D RJ BJ
    Set<Deck.Card> cardSet = new HashSet<>();
    for (int i = 0; i < 10; i++) {
      int suit = i / 5;
      int rank = i % 5 + 1;
      Deck.Card card = deck.new PlayingCard(Deck.suitsInOrder[suit], rank);
      deck.addCard(card);
      cardSet.add(card);
    }
    Deck.Card rj = deck.new Joker("red");
    Deck.Card bj = deck.new Joker("black");
    deck.addCard(rj);
    deck.addCard(bj);
    cardSet.add(rj);
    cardSet.add(bj);

    int seed = 10;
    Deck.gen.setSeed(seed);
    deck.shuffle();

    Deck.Card cur = deck.head;
    // forward ref
    for (int i = 0; i < 12; i++) {
      if (!cardSet.contains(cur)) {
        throw new AssertionError("Shuffle should not create new cards.");
      }
      cur = cur.next;
    }
    if (cur != deck.head) {
      throw new AssertionError(
          "Deck is not correctly shuffled. "
              + "Tail does not connect to head or new cards were added.");
    }

    // backward ref
    for (int i = 11; i >= 0; i--) {
      cur = cur.prev;
    }
    if (cur != deck.head) {
      throw new AssertionError(
          "Deck is not correctly shuffled. " + "Backward references are not correctly set up.");
    }

    System.out.println("Test passed.");
  }
}

class Shuffle_SingleCard implements Runnable {
  @Override
  public void run() {
    Deck deck = new Deck();
    Deck.Card c = deck.new Joker("red");
    deck.addCard(c);

    deck.shuffle();

    if (!(deck.head.getValue() == c.getValue()
        && c.next.getValue() == c.getValue()
        && c.prev.getValue() == c.getValue())) {
      throw new AssertionError("Deck is not correctly shuffled when it only has one card.");
    }
    System.out.println("Test passed.");
  }
}

class Shuffle_Three implements Runnable {
  @Override
  public void run() {
    Deck deck = new Deck();
    // AC 2C 3C 4C 5C
    Deck.Card[] arrDeck = new Deck.Card[5];
    for (int i = 0; i < 5; i++) {
      Deck.Card card = deck.new PlayingCard(Deck.suitsInOrder[0], i + 1);
      arrDeck[i] = card;
      deck.addCard(card);
    }

    int seed = 250;
    Deck.gen.setSeed(seed);
    deck.shuffle();
    deck.shuffle();
    deck.shuffle();

    // expected first pass
    // AC, 4C, 5C, 3C, 2C

    // expected second pass
    // 5C, AC, 4C, 2C, 3C

    // expected third pass
    // AC, 5C, 3C, 2C, 4C

    int[] shuffledIndex = {0, 4, 2, 1, 3};

    // .next references
    Deck.Card cur = deck.head;
    for (int i = 0; i < 5; i++) {
      Deck.Card expected = arrDeck[shuffledIndex[i]];
      if (cur.getValue() != expected.getValue()) {
        throw new AssertionError(
            "Deck is not correctly shuffled.\n"
                + "Forward references are not correctly set up. "
                + "Expected card at index "
                + i
                + " is "
                + expected
                + " but got "
                + cur);
      }
      cur = cur.next;
    }

    // .prev references
    cur = deck.head.prev;
    for (int i = 4; i >= 0; i--) {
      Deck.Card expected = arrDeck[shuffledIndex[i]];
      if (cur.getValue() != expected.getValue()) {
        throw new AssertionError(
            "Deck is not correctly shuffled.\n"
                + "Backward references are not correctly set up. "
                + "Expected card at index "
                + i
                + " iterating using .prev is "
                + expected
                + " but got "
                + cur);
      }
      cur = cur.prev;
    }
    System.out.println("Test passed.");
  }
}

/* ======== TESTER #2 ======== */

class CreateDeck1 implements Runnable {
  public void run() {
    Deck deck = new Deck(4, 3);

    if (deck.numOfCards != 14) {
      throw new AssertionError("Deck is empty when it should have 14 cards");
    }
    System.out.println("assignment2.Test passed.");
  }
}

class CreateDeck2 implements Runnable {
  public void run() {

    Deck deck1 = new Deck();
    Deck.PlayingCard card1 = deck1.new PlayingCard("clubs", 1); // AC
    Deck.PlayingCard card2 = deck1.new PlayingCard("clubs", 2); // 2C
    Deck.PlayingCard card3 = deck1.new PlayingCard("diamonds", 1); // AD
    Deck.PlayingCard card4 = deck1.new PlayingCard("diamonds", 2); // 2D
    Deck.Joker rJoker = deck1.new Joker("red"); // RJ
    Deck.Joker bJoker = deck1.new Joker("black"); // BJ
    deck1.addCard(card1);
    deck1.addCard(card2);
    deck1.addCard(card3);
    deck1.addCard(card4);
    deck1.addCard(rJoker);
    deck1.addCard(bJoker); // AC 2C AD 2D RJ BJ

    Deck deck2 = new Deck(2, 2); // AC 2C AD 2D RJ BJ

    if (deck1.head.getValue() != deck2.head.getValue()) {
      throw new AssertionError("Head of the deck is incorrect");
    }

    for (int i = 1; i < deck1.numOfCards + 1; i++) {
      if (deck1.head.getValue() != deck2.head.getValue()) {
        throw new AssertionError(
            "The deck is not correctly created."
                + "The card at position "
                + i
                + " (and/or after) is incorrect.");
      }
      deck1.head = deck1.head.next;
      deck2.head = deck2.head.next;
    }
    System.out.println("assignment2.Test passed.");
  }
}

class CreateDeck3 implements Runnable {
  @Override
  public void run() {
    // checking if exception is thrown by the constructor
    try {
      Deck deck1 = new Deck(14, 1);
    } catch (IllegalArgumentException raiseException) {
      System.out.println("assignment2.Test passed.");
    }

    try {
      Deck deck1 = new Deck(2, 6);
    } catch (IllegalArgumentException raiseException) {
      System.out.println("assignment2.Test passed.");
    }
  }
}

class DeepCopyDeck1 implements Runnable {
  public void run() {
    // creating and copying an empty deck
    Deck deck = new Deck();
    Deck copy = new Deck(deck);

    if (!(deck.head == null && copy.head == null)) {
      throw new AssertionError(
          "An empty deck is not being initialized with default values."
              + "The head of the deck and copy deck should be null.");
    }

    if (!(deck.numOfCards == 0 && copy.numOfCards == 0)) {
      throw new AssertionError(
          "An empty deck is not being initialized with default values."
              + "The number of cards in the deck and copy deck should be 0.");
    }
    System.out.println("assignment2.Test passed.");
  }
}

class TripleCut1 implements Runnable {
  @Override
  public void run() {
    // For the edge case of tripleCut() when there are no cards before the 1st card
    // firstCard is the head of the deck

    Deck deck = new Deck();
    Deck.Card c1 = deck.new PlayingCard(Deck.suitsInOrder[0], 1); // AC
    Deck.Card c2 = deck.new PlayingCard(Deck.suitsInOrder[0], 2); // 2C
    Deck.Card c3 = deck.new PlayingCard(Deck.suitsInOrder[2], 1); // AH
    Deck.Card c4 = deck.new PlayingCard(Deck.suitsInOrder[2], 2); // 2H

    deck.addCard(c1);
    deck.addCard(c2);
    deck.addCard(c3);
    deck.addCard(c4); // AC 2C AH 2H

    deck.tripleCut(c1, c3); // Expected outcome: 2H AC 2C AH

    // test whether all the cards are in the right order
    boolean head = deck.head == c4;
    boolean tail = deck.head.prev == c3;
    boolean c4Ref = c4.next == c1 && c4.prev == c3;
    boolean c3Next = c3.next == c4;
    boolean c1Prev = c1.prev == c4;

    if (!(head && tail)) {
      throw new AssertionError("The head/tail is incorrect");
    } else if (!(c4Ref && c3Next && c1Prev)) {
      throw new AssertionError("The pointers of the cards are incorrect");
    }
    System.out.println("assignment2.Test passed.");
  }
}

class TripleCut2 implements Runnable {
  @Override
  public void run() {
    // For the edge case of tripleCut() when there are no cards after the 2nd card
    // secondCard is the tail of the deck

    Deck deck = new Deck();
    Deck.Card c1 = deck.new PlayingCard(Deck.suitsInOrder[0], 1); // AC
    Deck.Card c2 = deck.new PlayingCard(Deck.suitsInOrder[0], 2); // 2C
    Deck.Card c3 = deck.new PlayingCard(Deck.suitsInOrder[2], 1); // AH
    Deck.Card c4 = deck.new PlayingCard(Deck.suitsInOrder[2], 2); // 2H

    deck.addCard(c1);
    deck.addCard(c2);
    deck.addCard(c3);
    deck.addCard(c4); // AC 2C AH 2H

    deck.tripleCut(c2, c4); // Expected Outcome: 2C AH 2H AC

    boolean head = deck.head == c2;
    boolean tail = deck.head.prev == c1;
    boolean c2Prev = c2.prev == c1;
    boolean c1Ref = c1.prev == c4 && c1.next == c2;
    boolean c4Next = c4.next == c1;

    if (!(head && tail)) {
      throw new AssertionError("The head/tail is incorrect");
    } else if (!(c2Prev && c1Ref && c4Next)) {
      throw new AssertionError("The pointers of the cards are incorrect");
    }
    System.out.println("assignment2.Test passed.");
  }
}

class TripleCut3 implements Runnable {
  @Override
  public void run() {
    // regular case of tripleCut()

    Deck deck = new Deck();
    Deck.Card c1 = deck.new PlayingCard(Deck.suitsInOrder[0], 1); // AC
    Deck.Card c2 = deck.new PlayingCard(Deck.suitsInOrder[0], 2); // 2C
    Deck.Card c3 = deck.new PlayingCard(Deck.suitsInOrder[2], 1); // AH
    Deck.Card c4 = deck.new PlayingCard(Deck.suitsInOrder[2], 2); // 2H
    Deck.Card c5 = deck.new PlayingCard(Deck.suitsInOrder[3], 1); // AS
    Deck.Card c6 = deck.new PlayingCard(Deck.suitsInOrder[3], 2); // 2S

    deck.addCard(c1);
    deck.addCard(c2);
    deck.addCard(c3);
    deck.addCard(c4);
    deck.addCard(c5);
    deck.addCard(c6); // Deck: AC 2C AH 2H AS 2S

    deck.tripleCut(c3, c5); // Expected outcome: 2S AH 2H AS AC 2C

    boolean head = deck.head == c6;
    boolean tail = deck.head.prev == c2;
    boolean c1Ref = c1.prev == c5 && c1.next == c2;
    boolean c2Ref = c2.prev == c1 && c2.next == c6;
    boolean c3Red = c3.prev == c6 && c3.next == c4;
    boolean c4ref = c4.prev == c3 && c4.next == c5;
    boolean c5Ref = c5.prev == c4 && c5.next == c1;
    boolean c6Ref = c6.prev == c2 && c6.next == c3;

    if (!(head && tail)) {
      throw new AssertionError("The head/tail is incorrect");
    } else if (!(c1Ref && c2Ref && c3Red && c4ref && c5Ref && c6Ref)) {
      throw new AssertionError("The pointers of the cards are incorrect");
    }
    System.out.println("assignment2.Test passed.");
  }
}

class TripleCut4 implements Runnable {
  @Override
  public void run() {
    // For the edge case of tripleCut() there are three cards and firstCard
    // and secondCard are the same

    Deck deck = new Deck();
    Deck.Card c1 = deck.new PlayingCard(Deck.suitsInOrder[0], 1); // AC
    Deck.Card c2 = deck.new PlayingCard(Deck.suitsInOrder[0], 2); // 2C
    Deck.Card c3 = deck.new PlayingCard(Deck.suitsInOrder[2], 1); // AH

    deck.addCard(c1);
    deck.addCard(c2);
    deck.addCard(c3);

    // Before:
    // head = c1, c2, c3
    // AC, 2C, AH
    deck.tripleCut(c2, c2);
    // Expected deck arrangement:
    // head = c3, c2, c1
    // AH, 2C, AC

    boolean head = deck.head == c3;
    boolean tail = deck.head.prev == c1;
    boolean c1Ref = c1.prev == c2 && c1.next == c3;
    boolean c2Ref = c2.prev == c3 && c2.next == c1;
    boolean c3Ref = c3.prev == c1 && c3.next == c2;

    if (!(head && tail)) {
      throw new AssertionError("The head/tail is incorrect");
    } else if (!(c1Ref && c2Ref && c3Ref)) {
      throw new AssertionError("The pointers of the cards are incorrect");
    }
    System.out.println("assignment2.Test passed.");
  }
}

class CountCut1 implements Runnable {
  @Override
  public void run() {
    // regular case

    Deck deck = new Deck();

    Deck.Card c1 = deck.new PlayingCard(Deck.suitsInOrder[0], 1); // AC
    Deck.Card c2 = deck.new PlayingCard(Deck.suitsInOrder[0], 2); // 2C
    Deck.Card c3 = deck.new PlayingCard(Deck.suitsInOrder[2], 1); // AH
    Deck.Card c4 = deck.new PlayingCard(Deck.suitsInOrder[2], 2); // 2H

    deck.addCard(c1);
    deck.addCard(c3);
    deck.addCard(c4);
    deck.addCard(c2); // Deck : AC AH 2H 2C

    deck.countCut(); // Cut 2 numbers from the top (taking value of 2C)
    // Expected Deck Outcome : 2H AC AH 2C

    boolean head = deck.head == c4;
    boolean tail = deck.head.prev == c2;
    boolean c4Ref = c4.prev == c2 && c4.next == c1;
    boolean c1Ref = c1.prev == c4 && c1.next == c3;
    boolean c3ref = c3.prev == c1 && c3.next == c2;
    boolean c2Ref = c2.prev == c3 && c2.next == c4;

    if (!(head && tail)) {
      throw new AssertionError("The head/tail is incorrect");
    } else if (!(c4Ref && c1Ref && c3ref && c2Ref)) {
      throw new AssertionError("The pointers of the cards are incorrect");
    }
    System.out.println("assignment2.Test passed.");
  }
}

class CountCut2 implements Runnable {
  @Override
  public void run() {
    // when the value of the last card is a multiplier of the number of cards in the deck

    Deck deck = new Deck();
    Deck.PlayingCard c1 = deck.new PlayingCard(Deck.suitsInOrder[0], 1); // AC
    Deck.PlayingCard c2 = deck.new PlayingCard(Deck.suitsInOrder[0], 2); // 2C
    Deck.PlayingCard c3 = deck.new PlayingCard(Deck.suitsInOrder[0], 3); // 3C
    Deck.PlayingCard c4 = deck.new PlayingCard(Deck.suitsInOrder[0], 4); // 4C

    deck.addCard(c1);
    deck.addCard(c2);
    deck.addCard(c3);
    deck.addCard(c4); // Deck : AC 2C 3C 4C

    deck.countCut(); // Do nothing because 4 is a multiplier of 4

    if (!(deck.head == c1) && deck.head.prev == c4) {
      throw new AssertionError("The method countCut() is modifying the deck when it shouldn't");
    }
    System.out.println("assignment2.Test passed.");
  }
}

class CountCut3 implements Runnable {
  @Override
  public void run() {
    // when the number for cut is 1

    Deck deck = new Deck();

    Deck.Card c1 = deck.new PlayingCard(Deck.suitsInOrder[0], 1); // AC
    Deck.Card c2 = deck.new PlayingCard(Deck.suitsInOrder[0], 3); // 3C

    deck.addCard(c1);
    deck.addCard(c2);

    deck.countCut(); // Should put AC above 3C (technically not changing the deck)

    if (!(deck.head == c1) && deck.head.prev == c2) {
      throw new AssertionError("The method countCut() is modifying the deck when it shouldn't");
    }
    System.out.println("assignment2.Test passed.");
  }
}

class CountCut4 implements Runnable {
  @Override
  public void run() {
    // when the deck is empty
    Deck deck = new Deck();

    deck.countCut(); // should do nothing

    if (!(deck.head == null)) {
      throw new AssertionError("The method countCut() is not handling the case of an empty deck");
    }
    System.out.println("assignment2.Test passed.");
  }
}

class LocateJoker1 implements Runnable {
  @Override
  public void run() {
    // edge case where deck is empty, must return null

    Deck deck = new Deck();
    deck.locateJoker("red");

    if (deck.locateJoker("red") != null) {
      throw new AssertionError(
          "The method locateJoker() is not returning null when the deck is empty");
    }
    System.out.println("assignment2.Test passed.");
  }
}

class LocateJoker2 implements Runnable {
  @Override
  public void run() {
    // edge case where there is no joker in the deck, must return null
    Deck deck = new Deck();
    Deck.Card c1 = deck.new PlayingCard(Deck.suitsInOrder[0], 1); // AC
    Deck.Card c2 = deck.new PlayingCard(Deck.suitsInOrder[0], 2); // 2C
    Deck.Card c3 = deck.new PlayingCard(Deck.suitsInOrder[0], 3); // 3C

    deck.addCard(c1);
    deck.addCard(c2);
    deck.addCard(c3);

    deck.locateJoker("red");

    if (deck.locateJoker("red") != null) {
      throw new AssertionError(
          "The method locateJoker() is not returning null when the there is no joker");
    }
    System.out.println("assignment2.Test passed.");
  }
}

class GenerateNextKeystreamValue1 implements Runnable {
  @Override
  public void run() {
    // example case from the last page of pdf

    Deck deck = new Deck(5, 2);
    // AC 2C 3C 4C 5C AD 2D 3D 4D 5D RJ BJ

    int seed = 10;
    Deck.gen.setSeed(seed);
    deck.shuffle(); // 3C 3D AD 5C BJ 2C 2D 4D AC RJ 4C 5D

    int value = deck.generateNextKeystreamValue();

    if (value != 4) {
      throw new AssertionError(
          "The method generateNextKeystreamValue() "
              + "is not returning the correct value. Expected value is 4 but got "
              + value);
    }
    System.out.println("assignment2.Test passed.");
  }
}

class GenerateNextKeystreamValue2 implements Runnable {
  @Override
  public void run() {
    Deck deck = new Deck(1, 1); // AC RJ BJ

    int seed = 31;
    Deck.gen.setSeed(seed);
    deck.shuffle(); // RJ BJ AC

    int value = deck.generateNextKeystreamValue();

    if (value != 1) {
      throw new AssertionError(
          "The method generateNextKeystreamValue() is "
              + "not returning the correct value. Expected value is 1 but got "
              + value);
    } else if (deck.head != deck.locateJoker("red")) {
      throw new AssertionError("Incorrect head after generateNextKeystreamValue()");
    }
    System.out.println("assignment2.Test passed.");
  }
}

class GenerateNextKeystreamValue3 implements Runnable {
  @Override
  public void run() {
    Deck deck = new Deck(2, 2); // AC 2C AD 2D RJ BJ

    int seed = 26;
    Deck.gen.setSeed(seed);
    deck.shuffle(); // BJ 2C 2D AD RJ AC

    int value = deck.generateNextKeystreamValue();

    if (value != 2) {
      throw new AssertionError(
          "The method generateNextKeystreamValue() is "
              + "not returning the correct value. Expected value is 2 but got "
              + value);
    } else if (deck.head.getValue() != 14) {
      throw new AssertionError("Incorrect head after generateNextKeystreamValue()");
    }
    System.out.println("assignment2.Test passed.");
  }
}

class GenerateNextKeystreamValue4 implements Runnable {
  public void run() {
    Deck deck = new Deck();
    Deck.PlayingCard card1 = deck.new PlayingCard("clubs", 1); // AC
    Deck.PlayingCard card2 = deck.new PlayingCard("clubs", 2); // 2C
    Deck.PlayingCard card3 = deck.new PlayingCard("clubs", 3); // 3C
    Deck.Joker rJoker = deck.new Joker("red"); // RJ
    Deck.PlayingCard card4 = deck.new PlayingCard("diamonds", 1); // AD
    Deck.PlayingCard card5 = deck.new PlayingCard("diamonds", 2); // 2D
    Deck.Joker bJoker = deck.new Joker("black"); // BJ
    Deck.PlayingCard card6 = deck.new PlayingCard("diamonds", 3); // 3D

    deck.addCard(card1);
    deck.addCard(card2);
    deck.addCard(card3);
    deck.addCard(rJoker);
    deck.addCard(card4);
    deck.addCard(card5);
    deck.addCard(bJoker);
    deck.addCard(card6); // AC 2C 3C RJ AD 2D BJ 3D

    int value = deck.generateNextKeystreamValue();

    if (value != 16) {
      throw new AssertionError(
          "The method generateNextKeystreamValue() is "
              + "not returning the correct value. Expected value is 16 but got "
              + value);
    } else if (deck.head.getValue() != 16) {
      throw new AssertionError("Incorrect head after generateNextKeystreamValue()");
    }
    System.out.println("assignment2.Test passed.");
  }
}

// =========================== SOLITAIRE CIPHER ================================

class GetKeystream1 implements Runnable {
  @Override
  public void run() {
    // example case from the last page of pdf

    Deck deck = new Deck(5, 2);
    // AC 2C 3C 4C 5C AD 2D 3D 4D 5D RJ BJ

    int seed = 10;
    Deck.gen.setSeed(seed);
    deck.shuffle(); // 3C 3D AD 5C BJ 2C 2D 4D AC RJ 4C 5D

    SolitaireCipher cipher = new SolitaireCipher(deck);
    int[] keystream = cipher.getKeystream(12);

    int[] expected = {4, 4, 15, 3, 3, 2, 1, 14, 16, 17, 17, 14};

    if (!Arrays.equals(keystream, expected)) {
      throw new AssertionError("The method getKeystream() is not returning the correct keystream");
    }

    System.out.println("assignment2.Test passed.");
  }
}

class GetKeystream2 implements Runnable {
  @Override
  public void run() {
    Deck deck1 = new Deck(12, 2);

    int seed = 429;
    Deck.gen.setSeed(seed);
    deck1.shuffle();

    SolitaireCipher cipher1 = new SolitaireCipher(deck1);
    int[] keystream1 = cipher1.getKeystream(5);

    SolitaireCipher cipher2 = new SolitaireCipher(deck1);
    int[] keystream2 = cipher2.getKeystream(5);

    if (!Arrays.equals(keystream1, keystream2)) {
      throw new AssertionError(
          "The keystream values of the 2 ciphers are not the same. "
              + "\nExpected: [8, 10, 21, 16, 21] for both. \nGot "
              + Arrays.toString(keystream1)
              + " for keystream1 and "
              + Arrays.toString(keystream2)
              + " for keystream2");
    }
    System.out.println("assignment2.Test passed.");
  }
}

class EncodingAndDecodingTest1 implements Runnable {
  @Override
  public void run() {
    Deck deck = new Deck(5, 3);
    String message = "Heya! Love", decodedMessage = "HEYALOVE";

    SolitaireCipher cipher = new SolitaireCipher(deck);
    String encodedMessage = (cipher.encode(message));

    cipher = new SolitaireCipher(deck);
    String decodeAttempt = cipher.decode(encodedMessage);

    if (!decodeAttempt.equals(decodedMessage)) {
      throw new AssertionError(
          "Error encoding/ decoding. \n Expected encoded message: LVQCZRNF. I received "
              + encodedMessage
              + "\n"
              + "Expected decode output: HEYALOVE. I received: "
              + decodeAttempt);
    }
    System.out.println("assignment2.Test passed.");
  }
}

class EncodingAndDecodingTest2 implements Runnable {
  @Override
  public void run() {
    Deck deck = new Deck(1, 1);
    String message = "Heya! L%$@!%:ove(!#%$", decodedMessage = "HEYALOVE";

    SolitaireCipher cipher = new SolitaireCipher(deck);
    String encodedMessage = (cipher.encode(message));

    cipher = new SolitaireCipher(deck);
    String decodeAttempt = cipher.decode(encodedMessage);

    if (!decodeAttempt.equals(decodedMessage)) {
      throw new AssertionError(
          "Error encoding/ decoding. \n Expected encoded message: IFZBMPWF. I received "
              + encodedMessage
              + "\n"
              + "Expected decode output: HEYALOVE. I received: "
              + decodeAttempt);
    }
    System.out.println("assignment2.Test passed.");
  }
}

class SolitaireCipher1 implements Runnable {
  @Override
  public void run() {
    Deck deck = new Deck(5, 2);
    // AC 2C 3C 4C 5C AD 2D 3D 4D 5D RJ BJ

    int seed = 123;
    Deck.gen.setSeed(seed);
    deck.shuffle(); // AD RJ 2C 4C 3D AC 5C BJ 4D 2D 5D 3C

    String message = "You are amazing!", message2 = "YOUAREAMAZING";

    SolitaireCipher cipher = new SolitaireCipher(deck);
    int[] keystream = cipher.getKeystream(message2.length());
    int[] expected = {2, 4, 15, 16, 15, 4, 4, 16, 4, 3, 4, 1, 5};

    SolitaireCipher cipher1 = new SolitaireCipher(deck);
    String encodedMessage = cipher1.encode(message);
    // The expected keystream should be the same keystream used by the cipher1 for encoding

    SolitaireCipher cipher2 = new SolitaireCipher(deck);
    String decodedMessage = cipher2.decode(encodedMessage);
    // The expected keystream should be the same keystream used by the cipher2 for decoding

    if (!Arrays.equals(keystream, expected)) {
      throw new AssertionError("The method getKeystream() is not returning the correct keystream");
    }

    if (!encodedMessage.equals("ASJQGIECECMOL")) {
      throw new AssertionError("The encoded message is not correct");
    }
    if (!decodedMessage.equals(message2)) {
      throw new AssertionError("The decoded message is not correct");
    }
    System.out.println("assignment2.Test passed.");
  }
}

class SolitaireCipher2 implements Runnable {
  @Override
  public void run() {
    Deck deck = new Deck(2, 1);

    int seed = 1234;
    Deck.gen.setSeed(seed);
    deck.shuffle();

    String message = "mari[o]  a(n)d><{lu~/ig}i", message2 = "MARIOANDLUIGI";

    SolitaireCipher cipher = new SolitaireCipher(deck);
    int[] keystream = cipher.getKeystream(message2.length());
    int[] expected = {1, 1, 2, 2, 1, 1, 2, 2, 1, 1, 2, 2, 1};

    SolitaireCipher cipher1 = new SolitaireCipher(deck);
    SolitaireCipher cipher2 = new SolitaireCipher(deck);

    String encodedMessage = cipher1.encode(message);
    String decodedMessage = cipher2.decode(encodedMessage);

    if (!Arrays.equals(keystream, expected)) {
      throw new AssertionError("The method getKeystream() is not returning the correct keystream");
    }
    if (!(encodedMessage.equals("NBTKPBPFMVKIJ"))) {
      throw new AssertionError("The encoded message is not correct");
    }
    if (!decodedMessage.equals(message2)) {
      throw new AssertionError("The decoded message is not correct");
    }
    System.out.println("assignment2.Test passed.");
  }
}

public class Tester {
  static String[] tests = {
    "a2.AddCard_AllRef",
    "a2.AddCard_CheckHead",
    "a2.AddCard_Circular",
    "a2.AddCard_NumOfCards",
    "a2.AddCard_SingleCard",
    "a2.DeepCopy_CheckRefs",
    "a2.DeepCopy_CircularNext",
    "a2.DeepCopy_CircularPrev",
    "a2.LocateJoker_Test1",
    "a2.LocateJoker_Test2",
    "a2.LocateJoker_Test3",
    "a2.LookUpCard_Test1",
    "a2.LookUpCard_Test2",
    "a2.LookUpCard_Test3",
    "a2.MoveCard_CheckNext1",
    "a2.MoveCard_CheckNext2",
    "a2.MoveCard_CheckPrev1",
    "a2.MoveCard_CheckPrev2",
    "a2.Shuffle_Empty",
    "a2.Shuffle_Example",
    "a2.Shuffle_FullDeck",
    "a2.Shuffle_NewCard",
    "a2.Shuffle_SingleCard",
    "a2.Shuffle_Three",
    "a2.CreateDeck1",
    "a2.CreateDeck2",
    "a2.CreateDeck3",
    "a2.DeepCopyDeck1",
    "a2.TripleCut1",
    "a2.TripleCut2",
    "a2.TripleCut3",
    "a2.TripleCut4",
    "a2.CountCut1",
    "a2.CountCut2",
    "a2.CountCut3",
    "a2.CountCut4",
    "a2.LocateJoker1",
    "a2.LocateJoker2",
    "a2.GenerateNextKeystreamValue1",
    "a2.GenerateNextKeystreamValue2",
    "a2.GenerateNextKeystreamValue3",
    "a2.GenerateNextKeystreamValue4",
    "a2.GetKeystream1",
    "a2.GetKeystream2",
    "a2.EncodingAndDecodingTest1",
    "a2.EncodingAndDecodingTest2",
    "a2.SolitaireCipher1",
    "a2.SolitaireCipher2",
  };

  public static void run() {
    int passed = 0;

    for (String className : tests) {
      System.out.printf("%n======= %s =======%n", className);
      System.out.flush();
      try {
        Runnable testCase =
            (Runnable) Class.forName(className).getDeclaredConstructor().newInstance();
        testCase.run();
        passed++;
      } catch (AssertionError e) {
        System.out.println(e);
      } catch (Exception e) {
        e.printStackTrace(System.out);
      }
    }

    System.out.printf("%n%n%d of %d tests passed.%n", passed, tests.length);
  }
}
