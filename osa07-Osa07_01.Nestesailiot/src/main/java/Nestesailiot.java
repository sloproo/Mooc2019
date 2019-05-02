
import java.util.Scanner;

public class Nestesailiot {

    public static void main(String[] args) {
        Scanner lukija = new Scanner(System.in);
        int ekansisalto = 0;
        int tokansisalto = 0;


        while (true) {
            System.out.println("Ensimmäinen: " + ekansisalto + "/100");
            System.out.println("Toinen: " + tokansisalto + "/100");
            System.out.print("> ");

            String luettu = lukija.nextLine();
            String osat[] = luettu.split(" ");
            if (luettu.equals("lopeta")) {
                break;
            }
            
            if (osat[0].equals("lisaa")) {
                int lisattava = Integer.valueOf(osat[1]);
                if (lisattava < 0) continue;
                ekansisalto = ekansisalto + lisattava;
                if (ekansisalto > 100) ekansisalto = 100;
                continue;
            }
            
            if (osat[0].equals("siirra")) {
                int siirrettava = Integer.valueOf(osat[1]);
                if (siirrettava < 0) continue;
                if (siirrettava > ekansisalto) {
                    tokansisalto = tokansisalto + ekansisalto;
                    ekansisalto = 0;
                    if (tokansisalto > 100) tokansisalto = 100;
                    continue;
                }else {
                    tokansisalto = tokansisalto + siirrettava;
                    if (tokansisalto > 100) tokansisalto = 100;
                    ekansisalto = ekansisalto - siirrettava;
                    continue;
                }
            }
            
            if (osat[0].equals("poista")) {
                int poistettava = Integer.valueOf(osat[1]);
                if (poistettava < 0) continue;
                if (poistettava > tokansisalto) {
                    tokansisalto = 0;
                    continue;
                } else tokansisalto = tokansisalto - poistettava;
                continue;
            }
            

        }
    }

}
