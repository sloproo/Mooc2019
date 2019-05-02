
import java.util.Scanner;

public class Kokonaisluku {

    public static void main(String[] args) {
        Scanner lukija = new Scanner(System.in);

        // toteuta ohjelma tänne
        System.out.println("Syötä luku!");
        int luku = Integer.valueOf(lukija.nextLine());
        System.out.println("Syötit luvun " + luku);

    }
}
