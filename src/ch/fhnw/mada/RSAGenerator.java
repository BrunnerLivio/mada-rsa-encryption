package src.ch.fhnw.mada;

public class RSAGenerator {
  private static final String PATH = "generated/";

  public static void main(String[] args) {
    // Aufgabe 1
    RSA.generateKeys(PATH + "pk.txt", PATH + "sk.txt");


    // Aufgabe 2
    String publicKey = FileHelper.readFile(PATH + "pk.txt");
    String text = FileHelper.readFile("text.txt");

    String encrypted = RSA.encrypt(text, publicKey);
    FileHelper.writeFile(PATH + "chiffre.txt", encrypted);

    // Aufgabe 3
    String textD = RSA.decryptFile(PATH + "chiffre.txt", PATH + "sk.txt");
    FileHelper.writeFile(PATH + "text-d.txt", textD);

    // Vogts Nachricht
    System.out.println("Entschlüsselter text von Herrn Vogt: " + RSA.decryptFile("chiffre.txt", "sk.txt"));
  }
}
