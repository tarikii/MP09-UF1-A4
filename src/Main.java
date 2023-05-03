import javax.crypto.BadPaddingException;
import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        xifrarDesxifrarCodi111();
        xifrarDesxifrarCodi112();
    }

    public static void xifrarDesxifrarCodi111() throws IOException {
        String rutaArxiu = "src/clausA4.txt";

        byte[] textClar = Files.readAllBytes(Paths.get(rutaArxiu));
        int keySize = 128;


        SecretKey clauSecreta = UtilitatsXifrar.keygenKeyGeneration(keySize);

        byte[] textXifrat = UtilitatsXifrar.encryptData(textClar,clauSecreta);

        byte[] textDesxifrat = UtilitatsXifrar.decryptData(textXifrat,clauSecreta);

        System.out.println("Text xifrat: " + new String(textXifrat));
        System.out.println("Text desxifrat: " + new String(textDesxifrat));
    }

    public static void xifrarDesxifrarCodi112() throws IOException {
    }
}
