package src.ch.fhnw.mada;

import java.math.BigInteger;
import java.util.Random;

public class RSA {
  private static final int BIT_LENGTH = 1024;

  public static String decryptFile(String chiffreFilePath, String secretKeyFilePath) {
    String chiffreText = FileHelper.readFile(chiffreFilePath);
    String secretKey = FileHelper.readFile(secretKeyFilePath);

    String decrypted = decrypt(chiffreText, secretKey);
    return decrypted;
  }

  public static String decrypt(String chiffreText, String secretKey) {
    String[] key = parseKey(secretKey);
    BigInteger n = new BigInteger(key[0]);
    BigInteger d = new BigInteger(key[1]);

    String[] chiffreNumbers = chiffreText.split(",");
    StringBuilder result = new StringBuilder();
    for (String chiffreNumber : chiffreNumbers) {
      BigInteger y = new BigInteger(chiffreNumber);

      // Wir entschlüssen den Wert mit dem privaten Schlüssel
      // y^d mod n
      BigInteger decrypted = rapidExponentiation(y, d, n);

      // In diesem Fall können wir die intValue() verwenden, da wir nur ASCII-Zeichen haben.
      // BigInt > Integer > char
      result.append((char) decrypted.intValue());
    }
    return result.toString();
  }

  public static String encrypt(String text, String publicKey) {
    String[] key = parseKey(publicKey);
    BigInteger n = new BigInteger(key[0]);
    BigInteger e = new BigInteger(key[1]);

    StringBuilder result = new StringBuilder();
    for (int i = 0; i < text.length(); i++) {
      // Kriegt den ASCII-Wert des Zeichens
      BigInteger x = BigInteger.valueOf(text.charAt(i));

      // Wir berechnen den verschlüsselten Wert mit dem öffentlichen Schlüssel
      // x^e mod n
      BigInteger encrypted = rapidExponentiation(x, e, n);

      // Wir schauen dass wir keine Komma am Ende haben
      if (i == text.length() - 1) {
        result.append(encrypted);
      } else {
        result.append(encrypted).append(",");
      }
    }
    return result.toString();
  }

 
  public static void generateKeys(String publicKeyPath, String privateKeyPath) {
    BigInteger q = getRandomPrime(BIT_LENGTH);
    BigInteger p = getRandomPrime(BIT_LENGTH);
    BigInteger n = q.multiply(p);

    BigInteger m = phi(q, p);
    BigInteger e = pickNumberFromZStar(m);

    BigInteger[] result = extendedEuclideanAlgorithm(m, e);
    BigInteger d = result[2];

    // Falls d negativ ist, muss es um m erhöht werden.
    // Damit es noch Teil von Z*phi(n) ist.
    if (d.compareTo(BigInteger.ZERO) < 0) {
      d = d.add(m);
    }

    writeKey(privateKeyPath, n, d);
    writeKey(publicKeyPath, n, e);
  }

  public static void writeKey(String fileName, BigInteger a, BigInteger b) {
    String key = "(" + a.toString() + "," + b.toString() + ")";
    FileHelper.writeFile(fileName, key);
  }


  private static String[] parseKey(String key) {
    return key.substring(1, key.length() - 1).split(",");
  }

  private static BigInteger getRandomPrime(int bitLength) {
    Random rnd = new Random();
    return BigInteger.probablePrime(bitLength, rnd);
  }

  // #region Die mathematischen Methoden aus Mada

  private static BigInteger rapidExponentiation(BigInteger a, BigInteger b, BigInteger n) {
    BigInteger result = BigInteger.ONE;
    BigInteger x = a;
    while (b.compareTo(BigInteger.ZERO) > 0) {
      if (b.mod(BigInteger.valueOf(2)).equals(BigInteger.ONE)) {
        result = result.multiply(x).mod(n);
      }
      x = x.multiply(x).mod(n);
      b = b.divide(BigInteger.valueOf(2));
    }
    return result;
  }

  private static BigInteger[] extendedEuclideanAlgorithm(BigInteger a, BigInteger b) {
    // Hardcodiert bei der 1. Iteration
    BigInteger x1 = BigInteger.ZERO;
    BigInteger y1 = BigInteger.ONE;
    BigInteger x0 = BigInteger.ONE;
    BigInteger y0 = BigInteger.ZERO;

    BigInteger q;
    BigInteger temp;
    BigInteger r = b;

    while (!r.equals(BigInteger.ZERO)) {
      q = a.divide(b);
      r = a.mod(b);
      a = b;
      b = r;
      temp = x1;
      x1 = x0.subtract(q.multiply(x1));
      x0 = temp;
      temp = y1;
      y1 = y0.subtract(q.multiply(y1));
      y0 = temp;
    }
    return new BigInteger[] { a, x0, y0 };
  }

  private static BigInteger pickNumberFromZStar(BigInteger m) {
    // Diese Funktion wählt die erste Zahl aus Z*phi(n) aus.
    BigInteger e = BigInteger.valueOf(3);
    while (e.gcd(m).intValue() > 1) {
      // Da e und m teilerfremd sein müssen, fallen alle geraden Zahlen weg.
      e = e.add(BigInteger.valueOf(2));
    }
    return e;
  }

  public static BigInteger phi(BigInteger prime1, BigInteger prime2) {
    // Weil p und q Primzahlen sind, gilt:
    // phi(n) = (p-1)*(q-1)
    BigInteger a = prime1.subtract(BigInteger.ONE);
    BigInteger b = prime2.subtract(BigInteger.ONE);
    return a.multiply(b);
  }

  // #endregion
}