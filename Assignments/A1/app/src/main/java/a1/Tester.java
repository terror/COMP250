package a1;

import java.lang.reflect.*;
import java.util.Arrays;

public class Tester {
  static boolean containsError;

  public static void runTests() {
    busyBeeTest1();
    busyBeeTest2();
    honeyBeeTest1();
    honeyBeeTest2();
    hornetTest1();
    hornetTest2();
    hornetTest3();
    hornetTest4();
    insectTest1();
    insectTest2();
    insectTest3();
    insectTest4();
    insectTest5();
    insectTest6();
    angryBeeTest1();
    angryBeeTest2();
    angryBeeTest3();
    angryBeeTest4();
    swarmOfHornetsTest1();
    swarmOfHornetsTest2();
    swarmOfHornetsTest3();
    swarmOfHornetsTest4();
    swarmOfHornetsTest4();
    swarmOfHornetsTest5();
    swarmOfHornetsTest6();
    tankyBeeTest1();
    tankyBeeTest2();
    tankyBeeTest3();
    tankyBeeTest4();
    tankyBeeTest5();
    tileTest1();
    tileTest2();
    tileTest3();
    tileTest4();
    tileTest5();
    tileTest6();
    tileTest7();
    tileTest8();
    tileTest9();
    tileTest10();
    tileTest11();
    tileTest12();
    tileTest13();
    tileTest14();
    tileTest15();
    tileTest16();
    tileTest17();
    if (!containsError) {
      System.out.println("Passed all exposed test cases!");
    }
  }

  private static void busyBeeTest1() {
    BusyBee bee = new BusyBee(new Tile());
    if (bee.getHealth() != 5) {
      System.out.println(
          "Health is initialized to a wrong value. Expected: 5, actual: " + bee.getHealth());
      containsError = true;
    }
    if (bee.getCost() != 2) {
      System.out.println(
          "Food cost is initialized to a wrong value. Expected: 2, actual: " + bee.getCost());
      containsError = true;
    }
  }

  private static void busyBeeTest2() {
    Tile t = new Tile();
    BusyBee bee = new BusyBee(t);
    if (!bee.takeAction()) {
      System.out.println("takeAction() returns the wrong value. Expected: true, actual: false");
      containsError = true;
    }
    int food = t.collectFood();
    if (food != 2) {
      System.out.println(
          "takeAction() adds wrong amount of food to the tile. Expected: 2, actual: " + food);
      containsError = true;
    }
  }

  private static void honeyBeeTest1() {
    HoneyBee bee = new BusyBee(new Tile());
    if (bee.getCost() != 2) {
      System.out.println(
          "getCost() returns the wrong food cost. Expected: 2, actual: " + bee.getCost());
      containsError = true;
    }
  }

  private static void honeyBeeTest2() {
    Tile s = new Tile();
    Tile t = new Tile();
    HoneyBee bee1 = new BusyBee(s);
    HoneyBee bee2 = new BusyBee(t);
    if (bee1.equals(bee2)) {
      System.out.println("HoneyBee: equals() doesn't compare two honeybees correctly.");
      containsError = true;
    }
  }

  private static void hornetTest1() {
    Tile hive = new Tile();
    hive.buildHive();
    Tile nest = new Tile();
    nest.buildNest();
    Tile t = new Tile();
    t.createPath(hive, nest);
    HoneyBee bee = new BusyBee(t);
    Hornet hornet = new Hornet(t, 10, 2);

    if (!hornet.takeAction()) {
      System.out.println(
          "Hornet: takeAction() returns the wrong value. Expected: true, actual: false");
      containsError = true;
    }
    if (bee.getHealth() != 3) {
      System.out.println(
          "Hornet: takeAction() does the wrong amount of damage to bee. "
              + "Expected: 3, actual: "
              + bee.getHealth());
      containsError = true;
    }
  }

  private static void hornetTest2() {
    Tile t = new Tile();
    t.buildHive();
    Hornet hornet = new Hornet(t, 10, 2);
    if (hornet.takeAction()) {
      System.out.println(
          "Hornet: takeAction() returns the wrong value. Expected: false, actual: true");
      containsError = true;
    }
  }

  private static void hornetTest3() {
    Tile t = new Tile();
    t.buildNest();
    Hornet h1 = new Hornet(t, 10, 1);
    Hornet h2 = new Hornet(t, 10, 1);
    if (!h1.equals(h2)) {
      System.out.println("Hornet: equals() doesn't compare two hornets correctly. ");
      containsError = true;
    }
  }

  private static void hornetTest4() {
    Tile t = new Tile();
    t.buildNest();
    Hornet hornet = new Hornet(t, 10, 2);
    if (hornet.getHealth() != 10) {
      System.out.println(
          "Hornet: Health is initialized to a wrong value. "
              + "Expected: 10, actual: "
              + hornet.getHealth());
      containsError = true;
    }
    try {
      boolean damageField = false;
      for (Field field : Hornet.class.getDeclaredFields()) {
        field.setAccessible(true);
        Object value = field.get(hornet);
        if (value instanceof Integer && (Integer) value == 2) {
          damageField = true;
        }
      }
      if (!damageField) {
        System.out.println("Hornet: Damage is initialized to a wrong value. (expected: 2)");
        containsError = true;
      }
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
  }

  private static void insectTest1() {
    boolean exceptionCaught = false;
    try {
      Tile t = new Tile();
      t.buildHive();
      BusyBee bee1 = new BusyBee(t);
      BusyBee bee2 = new BusyBee(t);
    } catch (IllegalArgumentException e) {
      exceptionCaught = true;
    }
    if (!exceptionCaught) {
      System.out.println(
          "Insect: Constructor incorrectly deals with the case of "
              + "putting two bees on the same tile.");
      containsError = true;
    }
  }

  private static void insectTest2() {
    Insect insect = new BusyBee(new Tile());
    if (insect.getHealth() != 5) {
      System.out.println(
          "Insect: getHealth() returns the wrong health. Expected: 5, actual: "
              + insect.getHealth());
      containsError = true;
    }
  }

  private static void insectTest3() {
    Tile t = new Tile();
    Insect insect = new BusyBee(t);
    if (t != insect.getPosition()) {
      System.out.println("Insect: getPosition() returns the wrong Tile.");
      containsError = true;
    }
  }

  private static void insectTest4() {
    Tile t1 = new Tile();
    Tile t2 = new Tile();
    Insect insect = new BusyBee(t1);
    insect.setPosition(t2);

    if (t2 != insect.getPosition()) {
      System.out.println("Insect: setPosition() incorrectly sets the position Tile.");
      containsError = true;
    }
  }

  private static void insectTest5() {
    Tile t = new Tile();
    t.buildHive();
    Insect bee = new BusyBee(t);
    bee.takeDamage(2);
    if (bee.getHealth() != 4) {
      System.out.println("Insect: takeDamage() does the wrong amount of damage to itself.");
      containsError = true;
    }
  }

  private static void insectTest6() {
    Tile t = new Tile();
    t.buildHive();
    Insect bee1 = new BusyBee(t);
    Insect bee2 = new Hornet(t, 10, 2);
    if (bee1.equals(bee2)) {
      System.out.println("Insect: equals() doesn't compare two insects correctly.");
      containsError = true;
    }
  }

  private static void angryBeeTest1() {
    Tile hive = new Tile();
    hive.buildHive();
    Tile nest = new Tile();
    nest.buildNest();
    Tile t = new Tile();
    t.createPath(hive, nest);
    AngryBee bee = new AngryBee(t, 2);
    Hornet hornet = new Hornet(t, 10, 2);
    if (!bee.takeAction()) {
      System.out.println(
          "AngryBee: takeAction() returns the wrong value. (expected: true, actual: false)");
      containsError = true;
    }
    if (hornet.getHealth() != 8) {
      System.out.println(
          "AngryBee: takeAction() does the wrong amount of damage to hornet. "
              + "Expected: 8, actual: "
              + hornet.getHealth());
      containsError = true;
    }
  }

  private static void angryBeeTest2() {
    Tile t = new Tile();
    AngryBee bee = new AngryBee(t, 2);
    if (bee.takeAction()) {
      System.out.println(
          "AngryBee: takeAction() returns the wrong value. Expected: false, actual: true");
      containsError = true;
    }
  }

  private static void angryBeeTest3() {
    Tile s = new Tile();
    Tile t = new Tile();
    AngryBee bee1 = new AngryBee(s, 1);
    AngryBee bee2 = new AngryBee(t, 1);
    if (bee1.equals(bee2)) {
      System.out.println("AngryBee: equals() doesn't compare two angry bees correctly.");
      containsError = true;
    }
  }

  private static void angryBeeTest4() {
    Tile t = new Tile();
    AngryBee bee = new AngryBee(t, 2);
    if (bee.getHealth() != 10) {
      System.out.println(
          "AngryBee: Health is initialized to a wrong value. "
              + "Expected: 10, actual: "
              + bee.getHealth());
      containsError = true;
    }
    if (bee.getCost() != 1) {
      System.out.println(
          "AngryBee: Food cost is initialized to a wrong value. "
              + "Expected: 1, actual: "
              + bee.getCost());
      containsError = true;
    }
    try {
      boolean damageField = false;
      for (Field field : AngryBee.class.getDeclaredFields()) {
        field.setAccessible(true);
        Object value = field.get(bee);
        if (value instanceof Integer && (Integer) value == 2) {
          damageField = true;
        }
      }
      if (!damageField) {
        System.out.println("AngryBee: Damage is initialized to a wrong value. (expected: 2)");
        containsError = true;
      }
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
  }

  private static void swarmOfHornetsTest1() {
    SwarmOfHornets swarm = new SwarmOfHornets();
    Tile t = new Tile();
    t.buildNest();
    Hornet h1 = new Hornet(t, 1, 1);
    Hornet h2 = new Hornet(t, 1, 1);
    Hornet h3 = new Hornet(t, 1, 1);
    Hornet h4 = new Hornet(t, 1, 1);
    swarm.addHornet(h1);
    swarm.addHornet(h2);
    swarm.addHornet(h3);
    swarm.addHornet(h4);
    int size = swarm.sizeOfSwarm();
    Hornet[] actual = swarm.getHornets();
    Hornet[] expected = {h1, h2, h3, h4};

    if (size != 4 || !Arrays.equals(actual, expected)) {
      System.out.println(
          "addHornet() did not update the size field and/or list" + " of hornets field correctly.");
      containsError = true;
    }
  }

  private static void swarmOfHornetsTest2() {
    SwarmOfHornets swarm = new SwarmOfHornets();
    Tile t = new Tile();
    t.buildNest();
    Hornet h = new Hornet(t, 1, 1);
    swarm.addHornet(h);
    int size = swarm.sizeOfSwarm();
    Hornet[] actual = swarm.getHornets();
    Hornet[] expected = {h};

    if (size != 1 || !Arrays.equals(actual, expected)) {
      System.out.println(
          "addHornet() did not update the size field and/or list of hornets field correctly.");
      containsError = true;
    }
  }

  private static void swarmOfHornetsTest3() {
    SwarmOfHornets swarm = new SwarmOfHornets();
    Tile t = new Tile();
    t.buildNest();
    Hornet h = new Hornet(t, 1, 1);
    swarm.addHornet(h);
    Hornet first = swarm.getFirstHornet();
    if (first != h) {
      System.out.println("getFirstHornet() did not return correct value.");
      containsError = true;
    }
  }

  private static void swarmOfHornetsTest4() {
    SwarmOfHornets swarm = new SwarmOfHornets();
    Hornet first = swarm.getFirstHornet();
    if (first != null) {
      System.out.println(
          "getFirstHornet() did not return null when there were no hornets in the swarm.");
      containsError = true;
    }
  }

  private static void swarmOfHornetsTest5() {
    SwarmOfHornets swarm = new SwarmOfHornets();
    Tile t = new Tile();
    t.buildNest();
    Hornet h = new Hornet(t, 1, 1);
    boolean b = swarm.removeHornet(h);
    if (b) {
      System.out.println(
          "removeHornet() did not return false when the hornet was not in the array.");
      containsError = true;
    }
  }

  private static void swarmOfHornetsTest6() {
    SwarmOfHornets swarm = new SwarmOfHornets();
    Tile t = new Tile();
    t.buildNest();
    Hornet h1 = new Hornet(t, 1, 1);
    Hornet h2 = new Hornet(t, 2, 2);
    swarm.addHornet(h1);
    swarm.addHornet(h2);
    boolean b = swarm.removeHornet(h2);

    Hornet[] expected = {h1};
    // check if class properties were updated appropriately
    if (swarm.sizeOfSwarm() != 1 || !Arrays.equals(swarm.getHornets(), expected)) {
      System.out.println("The fields of the SwarmOfHornets were not modified correctly.");
      containsError = true;
    }
    if (!b) {
      System.out.println("removeHornet() did not return true when the hornet was in the array.");
      containsError = true;
    }
  }

  private static void tankyBeeTest1() {
    Tile t = new Tile();
    t.buildHive();
    TankyBee bee = new TankyBee(t, 2, 10);
    Hornet hornet = new Hornet(t, 10, 2);
    if (!bee.takeAction()) {
      System.out.println("takeAction() returns the wrong value. Expected: true, actual: false");
      containsError = true;
    }
    if (hornet.getHealth() != 8) {
      System.out.println(
          "TankyBee: takeAction() does the wrong amount of damage to hornet. "
              + "Expected: 8, actual: "
              + hornet.getHealth());
      containsError = true;
    }
  }

  private static void tankyBeeTest2() {
    Tile t = new Tile();
    TankyBee bee = new TankyBee(t, 2, 10);
    if (bee.takeAction()) {
      System.out.println(
          "TankyBee: takeAction() returns the wrong value. Expected: false, actual: true");
      containsError = true;
    }
  }

  private static void tankyBeeTest3() {
    Tile hive = new Tile();
    hive.buildHive();
    Tile nest = new Tile();
    nest.buildNest();
    Tile t = new Tile();
    t.createPath(hive, nest);
    TankyBee bee = new TankyBee(t, 2, 10);
    bee.takeDamage(2);
    if (bee.getHealth() != 29) {
      System.out.println(
          "TankyBee: takeDamage() does the wrong amount of damage to itself. "
              + "Expected: 29, actual: "
              + bee.getHealth());
      containsError = true;
    }
  }

  private static void tankyBeeTest4() {
    Tile s = new Tile();
    Tile t = new Tile();
    TankyBee bee1 = new TankyBee(s, 2, 10);
    TankyBee bee2 = new TankyBee(t, 2, 10);
    if (bee1.equals(bee2)) {
      System.out.println("TankyBee: equals() doesn't compare two bees correctly.");
      containsError = true;
    }
  }

  private static void tankyBeeTest5() {
    Tile t = new Tile();
    TankyBee bee = new TankyBee(t, 2, 10);
    if (bee.getHealth() != 30) {
      System.out.println(
          "TankyBee: Health is initialized to a wrong value. "
              + "Expected: 30, actual: "
              + bee.getHealth());
      containsError = true;
    }
    if (bee.getCost() != 3) {
      System.out.println(
          "TankyBee: Food cost is initialized to a wrong value. "
              + "Expected: 3, actual: "
              + bee.getCost());
      containsError = true;
    }
    try {
      boolean damageField = false;
      boolean armorField = false;
      for (Field field : TankyBee.class.getDeclaredFields()) {
        field.setAccessible(true);
        Object value = field.get(bee);
        if (value instanceof Integer && (Integer) value == 2) {
          damageField = true;
        }
        if (value instanceof Integer && (Integer) value == 10) {
          armorField = true;
        }
      }
      if (!damageField) {
        System.out.println("TankyBee: Damage is initialized to a wrong value. (expected: 2)");
        containsError = true;
      }
      if (!armorField) {
        System.out.println("TankyBee: Armor is initialized to a wrong value. (expected: 10)");
        containsError = true;
      }
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
  }

  private static void tileTest1() {
    Tile t0 = new Tile();
    Tile t1 = new Tile();
    HoneyBee bee = new BusyBee(t0);
    SwarmOfHornets s = new SwarmOfHornets();

    Tile tt = new Tile(13, true, false, false, t0, t1, bee, s);
    Hornet hor1 = new Hornet(tt, 20, 20);
    Hornet hor2 = new Hornet(tt, 30, 20);
    boolean result = !t0.addInsect(hor1) && tt.addInsect(hor2) && t1.addInsect(bee);
    if (!result) {
      System.out.println("addInsect() returned incorrect value.");
      containsError = true;
    }
  }

  private static void tileTest2() {
    Tile t = new Tile();
    boolean a = t.isHive();
    t.buildHive();
    boolean b = t.isHive();

    boolean result = !a && b;
    if (!result) {
      System.out.println("buildHive() is incorrect.");
      containsError = true;
    }
  }

  private static void tileTest3() {
    Tile t = new Tile();
    boolean a = t.isNest();
    t.buildNest();
    boolean b = t.isNest();

    boolean result = !a && b;
    if (!result) {
      System.out.println("buildNest() is incorrect.");
      containsError = true;
    }
  }

  private static void tileTest4() {
    Tile t = new Tile();
    int food = t.collectFood();
    if (food != 0) {
      System.out.println("collectFood() returned incorrect value: " + food);
      containsError = true;
    }
  }

  private static void tileTest5() {
    Tile t0 = new Tile();
    Tile t1 = new Tile();
    HoneyBee h = new BusyBee(t0);
    SwarmOfHornets s = new SwarmOfHornets();
    Tile tt = new Tile(98, true, true, false, t0, t1, h, s);
    int food = tt.collectFood();

    if (food != 98) {
      System.out.println("collectFood() returned incorrect value: " + food);
      containsError = true;
    }
  }

  private static void tileTest6() {
    Tile t0 = new Tile();
    Tile t1 = new Tile();
    HoneyBee h = new BusyBee(t0);
    SwarmOfHornets s = new SwarmOfHornets();
    Tile t = new Tile(21, true, true, false, t0, t1, h, s);
    try {
      boolean field1 = false,
          field2 = false,
          field3 = false,
          field4 = false,
          field5 = false,
          field6 = false,
          field7 = false;

      for (Field field : t.getClass().getDeclaredFields()) {
        field.setAccessible(true); // Make private accessible.
        Object value = field.get(t);
        if (value != null) {
          if (value instanceof Integer) {
            if ((Integer) value == 21) field1 = true;
          } else if (value instanceof Boolean) {
            if ((Boolean) value) field2 = true;
            else if (!(Boolean) value) field3 = true;
          } else if (value instanceof Tile) {
            if ((Tile) value == t0) field4 = true;
            if ((Tile) value == t1) field5 = true;
          } else if (value instanceof HoneyBee) {
            if ((HoneyBee) value == h) field6 = true;
          } else if (value instanceof SwarmOfHornets) {
            if ((SwarmOfHornets) value == s) field7 = true;
          }
        }
      }

      boolean result = field1 && field2 && field3 && field4 && field5 && field6 && field7;
      if (!result) {
        System.out.println("Some required fields were initialized incorrectly in constructor");
        containsError = true;
      }
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
  }

  private static void tileTest7() {
    Tile t0 = new Tile();
    Tile t1 = new Tile();
    Tile tt = new Tile();
    tt.createPath(t0, t1);
    boolean field1 = false, field2 = false, field3 = false;

    try {
      for (Field field : tt.getClass().getDeclaredFields()) {
        field.setAccessible(true);
        Object value = field.get(tt);
        if (value != null) {
          if (value instanceof Tile) {
            if ((Tile) value == t0) field1 = true;
            if ((Tile) value == t1) field2 = true;
          } else if (value instanceof Boolean) {
            if ((Boolean) value) field3 = true;
          }
        }
      }
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }

    boolean result = field1 && field2 && field3;
    if (!result) {
      System.out.println("some fields didn't change values according to the assignment.");
      containsError = true;
    }
  }

  private static void tileTest8() {
    Tile t0 = new Tile();
    Tile t1 = new Tile();
    HoneyBee h = new BusyBee(t0);
    SwarmOfHornets s = new SwarmOfHornets();
    Tile tt = new Tile(13, true, true, false, t0, t1, h, s);

    if (tt.getBee() != h) {
      System.out.println("getBee() returned incorrect value.");
      containsError = true;
    }
  }

  private static void tileTest9() {
    Tile t0 = new Tile();
    Tile t1 = new Tile();
    HoneyBee h = new BusyBee(t0);
    SwarmOfHornets s = new SwarmOfHornets();

    Tile tt = new Tile(13, true, false, false, t0, t1, h, s);
    Hornet hor1 = new Hornet(tt, 20, 20);
    Hornet hor2 = new Hornet(tt, 30, 20);
    tt.addInsect(hor1);
    tt.addInsect(hor2);

    if (tt.getHornet() != hor1) {
      System.out.println("getHornet() returned incorrect value.");
      containsError = true;
    }
  }

  private static void tileTest10() {
    Tile t0 = new Tile();
    Tile t1 = new Tile();
    HoneyBee h = new BusyBee(t0);
    SwarmOfHornets s = new SwarmOfHornets();

    Tile tt = new Tile(13, true, false, false, t0, t1, h, s);
    Hornet hor1 = new Hornet(tt, 20, 20);
    Hornet hor2 = new Hornet(tt, 30, 20);
    tt.addInsect(hor1);
    tt.addInsect(hor2);

    if (tt.getNumOfHornets() != 4) {
      System.out.println("getNumOfHornets() returned incorrect value: " + tt.getNumOfHornets());
      containsError = true;
    }
  }

  private static void tileTest11() {
    Tile t = new Tile();
    if (t.isHive()) {
      System.out.println("isHive() should return false for empty tile.");
      containsError = true;
    }
  }

  private static void tileTest12() {
    Tile t = new Tile();
    if (t.isNest()) {
      System.out.println("isNest() should return false for empty tile.");
      containsError = true;
    }
  }

  private static void tileTest13() {
    Tile t = new Tile();
    if (t.isOnThePath()) {
      System.out.println("isOnThePath() should return false.");
      containsError = true;
    }
  }

  private static void tileTest14() {
    Tile t0 = new Tile();
    Tile t1 = new Tile();
    HoneyBee bee = new BusyBee(t0);
    SwarmOfHornets s = new SwarmOfHornets();
    Tile tt = new Tile(13, true, false, false, t0, t1, bee, s);

    if (!tt.removeInsect(bee)) {
      System.out.println("removeInsect() should return true.");
      containsError = true;
    }
  }

  private static void tileTest15() {
    Tile tt = new Tile();
    tt.storeFood(42);
    boolean field1 = false;

    try {
      for (Field field : tt.getClass().getDeclaredFields()) {
        field.setAccessible(true);
        Object value = field.get(tt);
        if (value != null) {
          if (value instanceof Integer) {
            if ((Integer) value == 42) field1 = true;
          }
        }
      }
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }

    if (!field1) {
      System.out.println("Field for food was not updated correctly.");
      containsError = true;
    }
  }

  private static void tileTest16() {
    Tile t0 = new Tile();
    Tile t1 = new Tile();
    HoneyBee h = new BusyBee(t0);
    SwarmOfHornets s = new SwarmOfHornets();
    Tile tt = new Tile(4, false, false, true, t0, t1, h, s);

    if (tt.towardTheHive() != t0) {
      System.out.println("towardTheHive() did not return correct value.");
      containsError = true;
    }
  }

  private static void tileTest17() {
    Tile t0 = new Tile();
    Tile t1 = new Tile();
    HoneyBee h = new BusyBee(t0);
    SwarmOfHornets s = new SwarmOfHornets();

    Tile tt = new Tile(4, false, false, true, t0, t1, h, s);

    if (tt.towardTheNest() != t1) {
      System.out.println("towardTheNest() did not return correct value.");
      containsError = true;
    }
  }
}
