import javax.crypto.BadPaddingException;
import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        boolean sortir = false;

        while (!sortir){
            System.out.println("Que vols fer: ");
            System.out.println("1. Xifrar i Desxifrar text pla amb clau generada.");
            System.out.println("2. Xifrar i Desxifrar text pla amb clau generada a partir d'un password.");
            System.out.println("3. Executar alguns metodes que proporciona la classe SecretKey.");
            System.out.println("4. Desxifrar el text de la opció 2 un altre vegada, pero amb una contrasenya incorrecta.");
            System.out.println("5. Desxifrar la contrasenya correcta i el missatge del arxiu textamagat.crypt.");
            System.out.println("0. Sortir.");
            int opcio = scanner.nextInt();
            if(opcio == 1){
                UtilitatsXifrar.xifrarDesxifrarCodi111();
            }

            else if(opcio == 2){
                UtilitatsXifrar.xifrarDesxifrarCodi112();
            }

            else if(opcio == 3){
                UtilitatsXifrar.methodsSecretKey();
            }

            else if(opcio == 4){
                UtilitatsXifrar.desxifrarTextCodi112();
            }

            else if(opcio == 5){
                UtilitatsXifrar.desxifrarTextAmagat();
            }

            else if(opcio == 0){
                sortir = true;
            }
        }

    }
}
