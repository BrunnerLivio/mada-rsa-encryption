package src.ch.fhnw.mada;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

import org.junit.Test;

public class RSAGeneratorTest {

  @Test
  public void testPhi() {
    BigInteger result = RSAGenerator.phi(BigInteger.valueOf(3), BigInteger.valueOf(11));
    assertEquals(result.intValue(), 20);
  }
}
