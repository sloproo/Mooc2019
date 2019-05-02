import java.util.Arrays;
import java.util.Collections;
import java.util.ArrayList;

public class Paaohjelma {

    public static void main(String[] args) {
        // tee testikoodia tänne
        int[] taulukko = {3, 1, 5, 99, 3, 12};
        System.out.println(Arrays.toString(taulukko));
        jarjesta(taulukko);
        System.out.println(Arrays.toString(taulukko));
    }
    
    public static void jarjesta(int[] luvut) {
        Arrays.sort(luvut);
    }
    
    public static void jarjesta(String[] taulukko) {
        Arrays.sort(taulukko);
    }
    
    public static void jarjestaLuvut(ArrayList<Integer> luvut) {
        Collections.sort(luvut);
    }
    
    public static void jarjestaMerkkijonot(ArrayList<String> merkkijonolista) {
        Collections.sort(merkkijonolista);
    }

}
