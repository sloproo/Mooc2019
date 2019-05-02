
import java.util.ArrayList;
import java.util.Scanner;

public class HenkilotietojenTarkastelu {

    public static void main(String[] args) {
        Scanner lukija = new Scanner(System.in);
        ArrayList<String> nimet = new ArrayList<>();
        ArrayList<Integer> iat = new ArrayList<>();
        while (true) {
            String otettu = lukija.nextLine();
            if (otettu.equals("")) {
                break;
            }
            String[] palat = otettu.split(",");
            nimet.add(palat[0]);
            iat.add(Integer.valueOf(palat[1]));
        }
    
        int pituus = 0;
        String pisin = "";
        for (String nimi: nimet) {
            if (nimi.length() > pituus) {
                pisin = nimi;
                pituus = nimi.length();
            }
        }
        int ikiensumma = 0;
        for (Integer ika: iat) {
            ikiensumma = ikiensumma + ika;
        }
        int ikienmaara = iat.size();
        
        System.out.println("Pisin nimi: " + pisin);
        System.out.println("Syntymävuosien keskiarvo: " + (1.0 * ikiensumma / ikienmaara));
        

        


    }
    
}
