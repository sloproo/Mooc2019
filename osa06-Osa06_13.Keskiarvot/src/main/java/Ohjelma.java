
import java.util.Scanner;

public class Ohjelma {

    public static void main(String[] args) {
        Scanner lukija = new Scanner(System.in);

Arvosanarekisteri rekisteri = new Arvosanarekisteri();
rekisteri.lisaaArvosanaPisteidenPerusteella(93);
rekisteri.lisaaArvosanaPisteidenPerusteella(91);
rekisteri.lisaaArvosanaPisteidenPerusteella(92);
rekisteri.lisaaArvosanaPisteidenPerusteella(88);

System.out.println(rekisteri.arvosanojenKeskiarvo());
    }
}
