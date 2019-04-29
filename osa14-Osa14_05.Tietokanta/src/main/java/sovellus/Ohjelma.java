package sovellus;

import java.sql.SQLException;
import java.util.Scanner;

public class Ohjelma {

    public static void main(String[] args) throws SQLException {
        String tietokannanPolku = "jdbc:h2:./todo-tietokanta";
        if (args.length > 0) {
            tietokannanPolku = args[0];
        }

        TodoDao tietokanta = new TodoDao(tietokannanPolku);
        Scanner lukija = new Scanner(System.in);

        new Kayttoliittyma(lukija, tietokanta).kaynnista();
    }
}
