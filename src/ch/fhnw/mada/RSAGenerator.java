package src.ch.fhnw.mada;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.util.Random;

public class RSAGenerator {
  private static final int BIT_LENGTH = 1024;
  private static final String PATH = "generated/";

  public static void main(String[] args) {
    // Aufgabe 1
    generateKeys();

    // Aufgabe 2
    String publicKey = readFile(PATH + "pk.txt");
    String text = readFile("text.txt");

    String encrypted = encrypt(text, publicKey);
    writeFile(PATH + "chiffre.txt", encrypted);

    aufgabe3();
  }

  private static void aufgabe3() {
    // Aufgabe 3
    String chiffreText = readFile(PATH +"chiffre.txt");
    String secretKey = readFile(PATH + "sk.txt");

    String decrypted = decrypt(chiffreText, secretKey);
    System.out.println(decrypted);
  }

  private static String decrypt(String chiffreText, String secretKey) {
    String[] key = parseKey(secretKey);
    BigInteger n = new BigInteger(key[0]);
    BigInteger d = new BigInteger(key[1]);

    String[] chiffreNumbers = chiffreText.split(",");
    StringBuilder result = new StringBuilder();
    for (String chiffreNumber : chiffreNumbers) {
      BigInteger chiffre = new BigInteger(chiffreNumber);
      // BigInteger decrypted = chiffre.modPow(d, n);
      BigInteger decrypted = rapidExponentiation(chiffre, d, n);
      result.append((char) decrypted.intValue());
    }
    return result.toString();
  }

  private static String encrypt(String text, String publicKey) {
    String[] key = parseKey(publicKey);
    BigInteger n = new BigInteger(key[0]);
    BigInteger e = new BigInteger(key[1]);

    StringBuilder result = new StringBuilder();
    for (int i = 0; i < text.length(); i++) {
      BigInteger ascii = BigInteger.valueOf(text.charAt(i));
      BigInteger encrypted = rapidExponentiation(ascii, e, n);
      if (i == text.length() - 1) {
        result.append(encrypted);
      } else {
        result.append(encrypted).append(",");
      }
    }
    return result.toString();
  }

  private static String[] parseKey(String key) {
    return key.substring(1, key.length() - 1).split(", ");
  }

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

  private static void generateKeys() {
    BigInteger q = getRandomPrime(BIT_LENGTH);
    BigInteger p = getRandomPrime(BIT_LENGTH);

    System.out.println("q: " + q);
    System.out.println("p: " + p);
    // BigInteger q = new BigInteger("23");
    // BigInteger p = new BigInteger("11");

    BigInteger n = q.multiply(p);

    BigInteger m = phi(q, p);
    BigInteger e = getE(m);

    BigInteger[] result = extendedEuclideanAlgorithm(m, e);
    BigInteger d = result[2];

    if (d.compareTo(BigInteger.ZERO) < 0) {
      d = d.add(m);
    }

    writePrivateKey(n, d);
    writePublicKey(n, e);
  }

  private static void writePrivateKey(BigInteger n, BigInteger d) {
    writeKey(PATH + "sk.txt", n, d);
  }

  private static void writePublicKey(BigInteger n, BigInteger e) {
    writeKey(PATH + "pk.txt", n, e);
  }

  private static void writeKey(String fileName, BigInteger a, BigInteger b) {
    String key = "(" + a.toString() + ", " + b.toString() + ")";
    writeFile(fileName, key);
  }

  private static void writeFile(String fileName, String content) {
    File keyFile = new File(fileName);
    if (!keyFile.exists()) {
      keyFile.getParentFile().mkdirs();
    }

    try {
      keyFile.createNewFile();
    } catch (IOException e) {
      e.printStackTrace();
    }

    try {
      Files.write(keyFile.toPath(), content.getBytes());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static BigInteger[] extendedEuclideanAlgorithm(BigInteger a, BigInteger b) {
    BigInteger x = BigInteger.ZERO;
    BigInteger y = BigInteger.ONE;
    BigInteger lastX = BigInteger.ONE;
    BigInteger lastY = BigInteger.ZERO;
    BigInteger q;
    BigInteger temp;
    BigInteger r = b;
    while (!r.equals(BigInteger.ZERO)) {
      q = a.divide(b);
      r = a.mod(b);
      a = b;
      b = r;
      temp = x;
      x = lastX.subtract(q.multiply(x));
      lastX = temp;
      temp = y;
      y = lastY.subtract(q.multiply(y));
      lastY = temp;
    }
    return new BigInteger[] { a, lastX, lastY };
  }

  private static BigInteger getE(BigInteger m) {
    BigInteger e = BigInteger.valueOf(3);
    while (e.gcd(m).intValue() > 1) {
      // Use only odd numbers because that is more performant
      e = e.add(BigInteger.valueOf(2));
    }
    return e;
  }

  public static BigInteger phi(BigInteger prime1, BigInteger prime2) {
    BigInteger a = prime1.subtract(BigInteger.ONE);
    BigInteger b = prime2.subtract(BigInteger.ONE);
    return a.multiply(b);
  }

  private static BigInteger getRandomPrime(int bitLength) {
    Random rnd = new Random();
    return BigInteger.probablePrime(bitLength, rnd);
  }

  private static String readFile(String filePath) {
    // read file text.txt
    File file = new File(filePath);
    String content = null;
    try {
      content = new String(Files.readAllBytes(file.toPath()));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return content;
  }
}
