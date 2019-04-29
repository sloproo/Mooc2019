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
        }

        System.out.println("Kiitos!");
    }

}
