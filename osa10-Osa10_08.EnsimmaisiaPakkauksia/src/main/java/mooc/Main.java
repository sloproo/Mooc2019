import mooc.logiikka.Sovelluslogiikka;
import mooc.ui.Kayttoliittyma;
import mooc.ui.Tekstikayttoliittyma;

public class Main {

    public static void main(String[] args) {
        Kayttoliittyma kayttoliittyma = new Tekstikayttoliittyma();
        new Sovelluslogiikka(kayttoliittyma).suorita(3);
    }
}