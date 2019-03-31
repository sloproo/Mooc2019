package maatila;

import java.util.ArrayList;
import java.util.List;


public class Main {

    public static void main(String[] args) {
        // Testaa täällä ohjelmaasi
        Maatila maatila = new Maatila("Esko", new Navetta(new Maitosailio()));
Lypsyrobotti robo = new Lypsyrobotti();
maatila.asennaNavettaanLypsyrobotti(robo);

maatila.lisaaLehma(new Lehma());
maatila.lisaaLehma(new Lehma());
maatila.lisaaLehma(new Lehma());

maatila.eleleTunti();
maatila.eleleTunti();

maatila.hoidaLehmat();

System.out.println(maatila);
    }
}
