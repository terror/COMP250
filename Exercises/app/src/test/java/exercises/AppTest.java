package exercises;

import org.junit.Test;
import static org.junit.Assert.*;

public class AppTest {
  @Test public void charRightShift() {
    App app = new App();
    assertEquals(app.charRightShift('a', 26), 'a');
    assertEquals(app.charRightShift('#', 26), '#');
    assertEquals(app.charRightShift('b', 2), 'd');
    assertEquals(app.charRightShift('z', 1), 'a');
  }

  @Test public void compareWith() {
    App app = new App();
    assertEquals(app.compareWith("foo"), true);
    assertEquals(app.compareWith("bar"), false);
  }

  @Test public void isVowelAtIndex() {
    App app = new App();
    assertEquals(app.isVowelAtIndex("fxo", 1), false);
    assertEquals(app.isVowelAtIndex("bar", 1), true);
  }

  @Test public void isUpperCase() {
    App app = new App();
    assertEquals(app.isUpperCase('c'), false);
    assertEquals(app.isUpperCase('A'), true);
  }

  @Test public void countUpper() {
    App app = new App();
    assertEquals(app.countUpper("fooBaR"), 2);
    assertEquals(app.countUpper("foobar"), 0);
  }

  @Test public void maxValue() {
    App app = new App();
    assertEquals(app.maxValue(new int[] {1, 2, 3}), 3);
    assertEquals(app.maxValue(new int[] {1, 1, 1}), 1);
  }
}
