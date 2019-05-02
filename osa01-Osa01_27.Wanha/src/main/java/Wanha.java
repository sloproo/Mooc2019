
import java.util.Scanner;

public class Wanha {

    public static void main(String[] args) {
        Scanner lukija = new Scanner(System.in);

        // Toteuta ohjelmasi tähän. 
        System.out.println("Anna vuosiluku:");
        int vuosi = Integer.valueOf(lukija.nextLine());
        if (vuosi < 2015) {
            System.out.println("Wanha!");
        }
    }
}
