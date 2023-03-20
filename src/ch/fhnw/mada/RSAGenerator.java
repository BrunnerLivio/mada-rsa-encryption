package src.ch.fhnw.mada;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.util.Random;

public class RSAGenerator {
  private static final int BIT_LENGTH = 1024;

  public static void main(String[] args) {
    // BigInteger q = getRandomPrime(BIT_LENGTH);
    // BigInteger p = getRandomPrime(BIT_LENGTH);
    BigInteger p = BigInteger.valueOf(3);
    BigInteger q = BigInteger.valueOf(11);
    BigInteger n = q.multiply(p);

    BigInteger m = phi(q, p);
    BigInteger e = getE(m);

    BigInteger[] result = extendedEuclideanAlgorithm(m, e);
    BigInteger d = result[2];

    writePrivateKey(n, d);
    writePublicKey(n, e);
  }

  private static void writePrivateKey(BigInteger n, BigInteger d) {
    writeKey("sk.txt", n, d);
  }

  private static void writePublicKey(BigInteger n, BigInteger e) {
    writeKey("pk.txt", n, e);
  }

  private static void writeKey(String fileName, BigInteger a, BigInteger b) {
    File keyFile = new File("generated/" + fileName);
    if (!keyFile.exists()) {
      keyFile.getParentFile().mkdirs();
    }

    try {
      keyFile.createNewFile();
    } catch (IOException e) {
      e.printStackTrace();
    }

    String key = "(" + a.toString() + ", " + b.toString() + ")";
    try {
      Files.write(keyFile.toPath(), key.getBytes());
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
}
