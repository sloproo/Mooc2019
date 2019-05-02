
import java.util.ArrayList;
import java.util.Scanner;

public class UniikitSukunimet {

    public static void main(String[] args) {
        ArrayList<Henkilo> henkilot = new ArrayList<>();
        Scanner lukija = new Scanner(System.in);

        while (true) {
            System.out.println("Syötetäänkö henkilöiden tietoja, \"loppu\" lopettaa:");
            String jatketaan = lukija.nextLine();

            if (jatketaan.equals("loppu")) {
                break;
            }

            System.out.print("Syötä etunimi: ");
            String etunimi = lukija.nextLine();
            System.out.print("Syötä sukunimi: ");
            String sukunimi = lukija.nextLine();
            System.out.print("Syötä syntymävuosi: ");
            int syntymavuosi = Integer.valueOf(lukija.nextLine());

            henkilot.add(new Henkilo(etunimi, sukunimi, syntymavuosi));
            System.out.println("");
        }

        // toteuta uniikkien sukunimien tulostaminen aakkosjärjestyksessä tänne
        henkilot.stream()
                .map(s -> s.getSukunimi())
                .distinct()
                .sorted()
                .forEach(sukunimi -> System.out.println(sukunimi));
                
    }
}
