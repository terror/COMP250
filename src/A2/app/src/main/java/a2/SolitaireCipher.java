package a2;

public class SolitaireCipher {
  public Deck key;

  public SolitaireCipher(Deck key) {
    this.key = new Deck(key);
  }

  /*
   * Generates a keystream of the given size
   */
  public int[] getKeystream(int size) {
    int[] keystream = new int[size];

    for (int i = 0; i < size; ++i) {
      keystream[i] = key.generateNextKeystreamValue();
    }

    return keystream;
  }

  /*
   * Encodes the input message using the algorithm described in the pdf.
   */
  public String encode(String msg) {
    String filtered = "";

    for (int i = 0; i < msg.length(); ++i) {
      if (Character.isLetter(msg.charAt(i))) filtered += Character.toUpperCase(msg.charAt(i));
    }

    int[] keystream = getKeystream(12);

    String encoded = "";

    for (int i = 0; i < keystream.length; ++i) {
      encoded += (filtered.charAt(i) + keystream[i] - 'A') % 26 + 'A';
    }

    return encoded;
  }

  /*
   * Decodes the input message using the algorithm described in the pdf.
   */
  public String decode(String msg) {
    int[] keystream = getKeystream(12);

    String decoded = "";

    for (int i = 0; i < keystream.length; ++i) {
      decoded += (msg.charAt(i) - keystream[i] - 'A') % 26 + 'A';
    }

    return decoded;
  }
}
