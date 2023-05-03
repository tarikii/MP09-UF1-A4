import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class UtilitatsXifrar {
    public static SecretKey keygenKeyGeneration(int keySize){
        SecretKey sKey = null;
        if ((keySize == 128)||(keySize == 192)||(keySize == 256)) {
            try {
                KeyGenerator kgen = KeyGenerator.getInstance("AES");
                kgen.init(keySize);
                sKey = kgen.generateKey();

            } catch (NoSuchAlgorithmException ex) {
                System.err.println("Generador no disponible.");
            }
        }
        return sKey;
    }

    public static SecretKey passwordKeyGeneration(String text, int keySize){
        SecretKey sKey = null;

        if ((keySize == 128)||(keySize == 192)||(keySize == 256)) {
            try {
                byte[] data = text.getBytes("UTF-8");
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                byte[] hash = md.digest(data);
                byte[] key = Arrays.copyOf(hash, keySize/8);
                sKey = new SecretKeySpec(key, "AES");
            } catch (Exception ex) {
                System.err.println("Error generant la clau:" + ex);
            }
        }
        return sKey;
    }

    public static byte[] encryptData(byte[] data, SecretKey key){
        byte[] encryptedData = null;
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            encryptedData =  cipher.doFinal(data);
        } catch (Exception  ex) {
            System.err.println("Error xifrant les dades: " + ex);
        }
        return encryptedData;
    }

    public static byte[] decryptData(byte[] data, SecretKey key){
        byte[] decryptedData = null;
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);
            decryptedData = cipher.doFinal(data);
        } catch (Exception ex) {
            System.err.println("Error desxifrant les dades: " + ex);
        }
        return decryptedData;
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
        Scanner scanner = new Scanner(System.in);
        String rutaArxiu = "src/clausA4.txt";
        System.out.println("Posa la contrasenya que vulguis, es generara una clau a partir d'aquesta contrasenya");
        String paraulaDePas = scanner.nextLine();

        byte[] textClar = Files.readAllBytes(Paths.get(rutaArxiu));
        int keySize = 128;

        SecretKey clauSecreta = UtilitatsXifrar.passwordKeyGeneration(paraulaDePas, keySize);

        byte[] textXifrat = UtilitatsXifrar.encryptData(textClar,clauSecreta);

        byte[] textDesxifrat = UtilitatsXifrar.decryptData(textXifrat,clauSecreta);

        System.out.println("Text xifrat: " + new String(textXifrat));
        System.out.println("Text desxifrat: " + new String(textDesxifrat));
    }


    public static void methodsSecretKey(){
        System.out.println("En aquest apartat es genera una clau secreta, i mostrarem el binari de la clau, el algoritme i el seu format");
        int keySize = 128;
        SecretKey clauSecreta = UtilitatsXifrar.keygenKeyGeneration(keySize);

        byte[] clauCodejada = clauSecreta.getEncoded();
        System.out.println("La clau secreta codejada en binari: " + Arrays.toString(clauCodejada));

        String algoritme = clauSecreta.getAlgorithm();
        System.out.println("L'algoritme utilitzat per a generar aquesta clau: " + algoritme);

        String format = clauSecreta.getFormat();
        System.out.println("El format de la clau generada es " + format);
        System.out.println();
    }

    public static void desxifrarTextCodi112() throws IOException {
        String rutaArxiu = "src/clausA4.txt";

        byte[] textClar = Files.readAllBytes(Paths.get(rutaArxiu));
        int keySize = 128;

        SecretKey clauSecreta = UtilitatsXifrar.passwordKeyGeneration("paraulaCorrecta", keySize);

        byte[] textXifrat = UtilitatsXifrar.encryptData(textClar,clauSecreta);

        SecretKey clauSecretaIncorrecta = UtilitatsXifrar.passwordKeyGeneration("paraulaIncorrecta", keySize);
        try {
            byte[] textDesxifrat = UtilitatsXifrar.decryptData(textXifrat, clauSecretaIncorrecta);
            System.out.println("Text desxifrat: " + new String(textDesxifrat));
        } catch (Exception e) {
            System.out.println("No li estas passant la clau correcta!");
        }
    }

    public static void desxifrarTextAmagat() throws IOException {
        String rutaFitxer = "src/textamagat.crypt";
        byte[] textXifrat = null;
        try {
            textXifrat = Files.readAllBytes(Paths.get(rutaFitxer));
        } catch (IOException e) {
            System.err.println("No s'ha pogut llegir el fitxer de text xifrat: " + e);
            return;
        }

        String rutaFitxerClaus = "src/clausA4.txt";
        List<String> claus = null;
        try {
            claus = Files.readAllLines(Paths.get(rutaFitxerClaus));
        } catch (IOException e) {
            System.err.println("No s'ha pogut llegir el fitxer de claus: " + e);
            return;
        }

        for (String contrasenya : claus) {
            SecretKey clauSecreta = UtilitatsXifrar.passwordKeyGeneration(contrasenya, 128);
            byte[] textDesxifrat = UtilitatsXifrar.decryptData(textXifrat, clauSecreta);
            if (textDesxifrat != null) {
                System.out.println("Contrasenya correcta: " + contrasenya);
                System.out.println("Text desxifrat: " + new String(textDesxifrat));
                return;
            }
        }
        System.out.println("No s'ha trobat cap contrasenya correcta.");
    }
}