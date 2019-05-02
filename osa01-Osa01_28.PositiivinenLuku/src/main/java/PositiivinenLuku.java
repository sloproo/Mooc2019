
import java.util.Scanner;

public class PositiivinenLuku {

    public static void main(String[] args) {
        Scanner lukija = new Scanner(System.in);

        // Toteuta ohjelmasi tähän. 
        System.out.println("Anna luku:");
        int luku = Integer.valueOf(lukija.nextLine());
        if (luku > 0) {
            System.out.println("Luku on positiivinen.");
        } else {
            System.out.println("Luku ei ole positiivinen.");
        }
    }
}
