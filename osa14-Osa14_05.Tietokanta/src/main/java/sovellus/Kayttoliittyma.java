package sovellus;

import java.sql.SQLException;
import java.util.Scanner;

public class Kayttoliittyma {

    private Scanner lukija;
    private TodoDao tietokanta;

    public Kayttoliittyma(Scanner lukija, TodoDao tietokanta) {
        this.lukija = lukija;
        this.tietokanta = tietokanta;
    }

    public void kaynnista() throws SQLException {
        while (true) {
            System.out.println("");
            System.out.println("Syötä komento:");
            System.out.println("1) listaa");
            System.out.println("2) lisää");
            System.out.println("3) aseta tehdyksi");
            System.out.println("4) poista");
            System.out.println("x) lopeta");

            System.out.print("> ");
            String komento = this.lukija.nextLine();
            if (komento.equals("x")) {
                break;
            }

            // toteuta toiminnallisuus tänne
            if (komento.equals("1")) {
                listaa();
            }
            else if (komento.equals("2")) {
                lisaa();
            }
            else if (komento.equals("3")) {
                asetaTehdyksi();
            }
            else if (komento.equals("4")) {
                poista();
            }
        }

        System.out.println("Kiitos!");
    }

    private void listaa() throws SQLException {
        System.out.println("Listataan tietokannan tiedot");
        for (Todo todo : tietokanta.listaa()) {
            System.out.println(todo);
        }
        System.out.println("");
    }

    private void lisaa() throws SQLException {
        System.out.println("Lisätään tehtävää");
        System.out.println("Syötä nimi");
        String syotettavanNimi = lukija.nextLine();
        System.out.println("Syötä kuvaus");
        String syotettavanKuvaus = lukija.nextLine();
        Todo lisattava = new Todo(syotettavanNimi, syotettavanKuvaus, false);
        tietokanta.lisaa(lisattava);
        System.out.println("");
    }

    private void asetaTehdyksi() throws SQLException {
        System.out.println("");
        System.out.println("Mikä asetetaan tehdyksi (syötä id)?");
        int tehdyksiAsetettava = Integer.valueOf(lukija.nextLine());
        tietokanta.asetaTehdyksi(tehdyksiAsetettava);
        System.out.println("");
    }

    private void poista() throws SQLException {
        System.out.println("");
        System.out.println("Mikä poistetaan (syötä id)?");
        int poistettavanId = Integer.valueOf(lukija.nextLine());
        tietokanta.poista(poistettavanId);
        System.out.println("");
    }

}
