import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Reseptihaku {

    public static void main(String[] args) {
        Scanner lukija = new Scanner(System.in);

        ArrayList<Resepti> reseptit = new ArrayList<>();
        System.out.println("Mistä luetaan?");
        String tiedosto = lukija.nextLine();

        try (Scanner tiedostonLukija = new Scanner(new File(tiedosto))) {

            // luetaan reseptit ja raaka-aineet
            while (tiedostonLukija.hasNextLine()) {
                // luetaan resepti ja luodaan sitä vastaava olio
                String reseptinNimi = tiedostonLukija.nextLine();
                int reseptinAika = Integer.valueOf(tiedostonLukija.nextLine());
                Resepti resepti = new Resepti(reseptinNimi, reseptinAika);

                // lisätään resepti listalle
                reseptit.add(resepti);

                // lisätään reseptiin raaka-aineet
                while (tiedostonLukija.hasNextLine()) {
                    String raakaAine = tiedostonLukija.nextLine();

                    // reseptin raaka-aineet lopetetaan tyhjällä rivillä
                    if (raakaAine.isEmpty()) {
                        // poistutaan tästä while-toistolauseesta
                        // (ulompi jatkaa)
                        break;
                    }

                    resepti.lisaaRaakaAine(raakaAine);
                }

            }
        } catch (Exception e) {
            System.out.println("Virhe: " + e.getMessage());
        }

        System.out.println("Komennot:\n"
                + "listaa - listaa reseptit\n"
                + "lopeta - lopettaa ohjelman\n"
                + "hae nimi - hakee reseptiä nimen perusteella\n"
                + "hae keittoaika - hakee reseptiä keittoajan perusteella\n"
                + "hae aine - hakee reseptiä raaka-aineen perusteella");

        while (true) {
            System.out.print("Syötä komento: ");
            String komento = lukija.nextLine();
            if (komento.equals("lopeta")) {
                break;
            }

            if (komento.equals("listaa")) {
                System.out.println("Reseptit:");
                for (Resepti resepti : reseptit) {
                    System.out.println(resepti);
                }
            }

            if (komento.equals("hae nimi")) {
                System.out.print("Mitä haetaan: ");
                String haettava = lukija.nextLine();
                for (Resepti resepti : reseptit) {
                    if (resepti.nimiSisaltaa(haettava)) {
                        System.out.println(resepti);
                    }
                }
            }

            if (komento.equals("hae keittoaika")) {
                System.out.print("Keittoaika korkeintaan: ");
                int korkeintaan = Integer.valueOf(lukija.nextLine());

                for (Resepti resepti : reseptit) {
                    if (resepti.keittoaikaKorkeintaan(korkeintaan)) {
                        System.out.println(resepti);
                    }
                }
            }

            if (komento.equals("hae aine")) {
                System.out.print("Mitä raaka-ainetta haetaan: ");
                String aine = lukija.nextLine();

                for (Resepti resepti : reseptit) {
                    if (resepti.sisaltaaRaakaAineen(aine)) {
                        System.out.println(resepti);
                    }
                }
            }
        }

    }

}