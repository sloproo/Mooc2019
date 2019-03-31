import java.util.HashMap;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // tee tänne testikoodia
        Scanner lukija = new Scanner(System.in);
        ArrayList<String> lista = new ArrayList<>();
        int pituus = 0;
        System.out.println("Montako kohdetta listataan?");
        String syote = "10\n" + "fasfaa\n" + "fkajsfhakjsh\n" + "asfasfasf\n" + 
                "asfasfa\n" + "alkfsjal\n" + "lsgkjölk\n" + "söfhklhlöksj\n" + 
                "gaölksgnja\n" + "algkj\n" + "aggj\n";
        lukija = new Scanner(syote);
                
        while (pituus < 10) {
                pituus = Integer.valueOf(lukija.nextLine());
                if (pituus < 10) System.out.println("Vähän nyt yritystä!");
        }
        
        for (int i = 0; i < pituus; i++) {
            System.out.println("Kirjoita jotain:");
            String rivi = lukija.nextLine();
            if (rivi.equals("")) {
                System.out.println("Älä laiskottele");
                i -= 3;
            } else lista.add(rivi);
        }
        
        System.out.println("Pisin rivi:");
        System.out.println(pisinRivi(lista));
        System.out.println("Ensimmäinen rivi:");
        System.out.println(ensimmainenRivi(lista));
        System.out.println("Yli viiden merkin pituisia rivejä:");
        System.out.println(yliViidenPituisia(lista));
    }
    
    public static String pisinRivi(ArrayList<String> lista) {
        String pisin = lista.get(0);
        for (String s: lista) {
            if (s.length() > pisin.length()) {
                pisin = s;
            }
        }
        return pisin;
    }
        
        
        public static String ensimmainenRivi(ArrayList<String> lista) {
            return lista.get(0);
        }
        
        public static int yliViidenPituisia(ArrayList<String> lista) {
            int yliViiden = 0;
            int i = 0;
            while (i < lista.size()) {
                if (lista.get(i).length() > 5) {
                    yliViiden += 1;
                }
                i++;
            }
            return yliViiden;
            
        }
      
}

