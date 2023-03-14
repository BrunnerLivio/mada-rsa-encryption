package src.ch.fhnw.mada;

import java.math.BigInteger;
import java.util.Random;

public class RSAGenerator {
  private static final int BIT_LENGTH = 1024;

  public static void main(String[] args) {
    // BigInteger q = getRandomPrime(BIT_LENGTH);
    // BigInteger p = getRandomPrime(BIT_LENGTH);
    BigInteger p = BigInteger.valueOf(3);
    BigInteger q = BigInteger.valueOf(11);
    BigInteger n = q.multiply(p);

    BigInteger m = phi(q.intValue(), p.intValue());
    System.out.println(m);
    BigInteger e = getE(m);
    System.out.println(e);
  }

  private static BigInteger getE(BigInteger m) {
    BigInteger e = BigInteger.valueOf(3);
    while (e.gcd(m).intValue() > 1) {
      e = e.add(BigInteger.valueOf(2));
    }
    return e;
  }

  private static BigInteger phi(int prime1, int prime2) {
    return BigInteger.valueOf((prime1 - 1) * (prime2 - 1));
  }

  private static BigInteger getRandomPrime(int bitLength) {
    Random rnd = new Random();
    return BigInteger.probablePrime(bitLength, rnd);

  }
}
