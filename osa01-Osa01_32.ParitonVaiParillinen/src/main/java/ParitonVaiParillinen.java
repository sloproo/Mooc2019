
import java.util.Scanner;

public class ParitonVaiParillinen {

    public static void main(String[] args) {
        Scanner lukija = new Scanner(System.in);

        // Toteuta ohjelmasi tähän. 
        System.out.println("Anna luku:");
        int luku = Integer.valueOf(lukija.nextLine());
        if (luku % 2 == 0) {
            System.out.println("Luku " + luku + " on parillinen.");
        } else {
            System.out.println("Luku " + luku + " on pariton.");
        }

    }
}
