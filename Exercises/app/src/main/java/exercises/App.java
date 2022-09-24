package exercises;

import java.lang.reflect.*;
import java.util.*;
import java.util.stream.*;

public class App {
  final int A = 97;
  final int Z = 122;

  public static void main(String[] args) {
    try {
      Method[] methods = App.class.getDeclaredMethods();
      for (int i = 0; i < methods.length; ++i)
        System.out.println(String.format("%d) %s", i + 1, methods[i].toString()));
    } catch (Throwable error) {
      System.err.println(error);
    }
  }

  /**
   * Shifts a character right by a given shift value.
   *
   * @param c The character the shift.
   * @param shift The amount of positions to shift the character by.
   * @return The right-shifted character.
   */
  public char charRightShift(char c, int shift) {
    final int range = Z - A + 1;
    if (A <= c && c <= Z) return (char) ((c - A + (shift % (range) + (range))) % (range) + A);
    return c;
  }

  /**
   * Compares a string with `foo`.
   *
   * @param s The string to compare
   * @return Whether or not the given string is equal to `foo`.
   */
  public boolean compareWith(String s) {
    return s.equals("foo");
  }

  /**
   * Returns whether or not a character in a string at a given index is a vowel.
   *
   * @param s The string we're dealing with.
   * @param i The index of the character in `s`.
   * @return Whether or not the character in `s` at `i` is a vowel.
   */
  public boolean isVowelAtIndex(String s, int i) {
    if (i < 0 || i >= s.length()) throw new IllegalArgumentException();

    for (char vowel : new char[] {'a', 'e', 'i', 'o', 'u', 'y'})
      if (s.charAt(i) == vowel) return true;

    return false;
  }

  /**
   * Returns whether or not a given character is uppercase.
   *
   * @param c The character to check.
   * @return Whether or not the character is uppercase.
   */
  public boolean isUpperCase(char c) {
    return 65 <= c && c <= 90;
  }

  /*
   * Counts and returns the total number of uppercase letters in a given string.
   * @param s The string we're dealing with.
   * @return The number of uppercase letters in `s`.
   */
  public int countUpper(String s) {
    return s.chars()
        .mapToObj(i -> (char) i)
        .map(c -> 65 <= c && c <= 90 ? 1 : 0)
        .reduce(0, (a, b) -> a + b);
  }

  /**
   * Finds and returns the maximum value in an array.
   *
   * @param arr The array to work with.
   * @return The maximum value in `arr`.
   */
  public int maxValue(int[] arr) {
    int ret = Integer.MIN_VALUE;

    for (int element : arr) ret = Math.max(ret, element);

    return ret;
  }
}
