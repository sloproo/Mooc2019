
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // toteuta tänne toiminnallisuus, jonka avulla käyttäjä voi syöttää
        // kirjoja sekä tarkastella niitä
        Scanner lukija = new Scanner(System.in);
        ArrayList<Kirja> kirjat = new ArrayList<>();
        String nimi = "";
        int sivuja = 0;
        int vuosi = 0;
        String tulostettava = "";
        while (true) {
            System.out.print("Nimi? ");
            nimi = lukija.nextLine();
            if (nimi.equals("")) {
                break;
            }
            System.out.print("Sivuja? ");
            sivuja = Integer.valueOf(lukija.nextLine());
            System.out.print("Kirjoitusvuosi? ");
            vuosi = Integer.valueOf(lukija.nextLine());
            kirjat.add(new Kirja(nimi, sivuja, vuosi));
        }
        System.out.println("");
        System.out.println("Mitä tulostetaan?");
        tulostettava = lukija.nextLine();
        if (tulostettava.equals("kaikki")) {
            for (Kirja kirja: kirjat) {
                System.out.println(kirja.getNimi() + ", " + kirja.getSivumaara()
                        + " sivua, " + kirja.getVuosi());
            }
        } 
        if (tulostettava.equals("nimi")) {
            for (Kirja kirja: kirjat) {
                System.out.println(kirja.getNimi());
            }
        }

    }
}
