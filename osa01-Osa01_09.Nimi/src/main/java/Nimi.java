
import java.util.Scanner;

public class Nimi {

    public static void main(String[] args) {
        Scanner lukija = new Scanner(System.in);

        // toteuta ohjelma tänne
        System.out.println("Mikä on nimesi?");
        String nimi = lukija.nextLine();
        System.out.println("Hei " + nimi);

    }
}
