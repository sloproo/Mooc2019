
import java.util.Scanner;

public class NestesailiotOlioilla {

    public static void main(String[] args) {
        Scanner lukija = new Scanner(System.in);
        Sailio eka = new Sailio();
        Sailio toka = new Sailio();


        while (true) {
            System.out.println("Ensimmäinen: " + eka);
            System.out.println("Toinen: " + toka);
            System.out.print("> ");
            
            String luettu = lukija.nextLine();
            String osat[] = luettu.split(" ");

            if (luettu.equals("lopeta")) {
                break;
            }
            
            if (osat[0].equals("lisaa")) {
                int lisattava = Integer.valueOf(osat[1]);
                eka.lisaa(lisattava);
                continue;
            }
            
            if (osat[0].equals("siirra")) {
                int siirrettava = Integer.valueOf(osat[1]);
                if (siirrettava > eka.sisalto()) siirrettava = eka.sisalto();
                eka.poista(siirrettava);
                toka.lisaa(siirrettava);
                continue;
            }
            
            if (osat[0].equals("poista")) {
                int poistettava = Integer.valueOf(osat[1]);
                if (poistettava > toka.sisalto()) poistettava = toka.sisalto();
                toka.poista(poistettava);
                continue;
            }
        }
    }

}
