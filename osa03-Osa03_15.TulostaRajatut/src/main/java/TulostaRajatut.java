
import java.util.ArrayList;
import java.util.Scanner;

public class TulostaRajatut {

    public static void main(String[] args) {
        // kokeile toteuttamasi metodin toimintaa täällä
        Scanner lukija = new Scanner(System.in);
        System.out.println("Anna lukuja");
        ArrayList<Integer> testilista = new ArrayList<>();
        while (true) {
            int sisaan = Integer.valueOf(lukija.nextLine());
            if (sisaan == -1) {
                break;
            }
        testilista.add(sisaan);
        }
        System.out.println("Luvut syötetty.");
        System.out.println("Pienin lukuarvo jota etsitään listasta");
        int pieni = Integer.valueOf(lukija.nextLine());
        System.out.println("Suurin lukuarvo jota etsitään listasta");
        int iso = Integer.valueOf(lukija.nextLine());
        System.out.println("Hakutulokset");
        tulostaRajatutLuvut(testilista, pieni, iso);
    }
    
    public static void tulostaRajatutLuvut(ArrayList<Integer> luvut, int alaraja,
            int ylaraja) {
        int nouseva = alaraja;
        while (nouseva <= ylaraja) {
            if (luvut.contains(nouseva)) {
                System.out.println(nouseva);
            }
            nouseva++;
        }
    }

}
