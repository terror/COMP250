package a2;

public class SolitaireCipher {
  private int A = 'A', M = 26, Z = 'Z';

  public Deck key;

  public SolitaireCipher(Deck key) {
    this.key = new Deck(key);
  }

  /*
   * Generates a keystream of the given size
   */
  public int[] getKeystream(int size) {
    int[] keystream = new int[size];

    for (int i = 0; i < size; ++i) keystream[i] = key.generateNextKeystreamValue();

    return keystream;
  }

  /*
   * Encodes the input message using the algorithm described in the pdf.
   */
  public String encode(String msg) {
    String temp = msg.toUpperCase(), result = "";

    for (int i = 0; i < temp.length(); ++i)
      if (temp.charAt(i) >= A && temp.charAt(i) <= Z) result += temp.charAt(i);

    int[] keystream = getKeystream(result.length());

    String encoded = "";

    for (int i = 0; i < keystream.length; ++i)
      encoded += (char) ((result.charAt(i) - A + (keystream[i] % M) + M) % M + A);

    return encoded;
  }

  /*
   * Decodes the input message using the algorithm described in the pdf.
   */
  public String decode(String msg) {
    int[] keystream = getKeystream(msg.length());

    String decoded = "";

    for (int i = 0; i < keystream.length; ++i)
      decoded += (char) ((msg.charAt(i) - A + (-keystream[i] % M) + M) % M + A);

    return decoded;
  }
}
