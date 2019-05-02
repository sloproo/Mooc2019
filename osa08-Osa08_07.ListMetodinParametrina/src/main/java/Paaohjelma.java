import java.util.ArrayList;
import java.util.List;

public class Paaohjelma {

    public static void main(String[] args) {
        // tee tänne testikoodia
        List<String> nimet = new ArrayList<>();
nimet.add("eka");
nimet.add("toka");
nimet.add("kolmas");

System.out.println(palautaKoko(nimet));

    }
    
    

    // toteuta tänne  metodi palautaKoko, joka palauttaa parametrina 
    // saamansa lista-olion alkioiden lukumäärän
    
    public static int palautaKoko(List lista) {
        return lista.size();
    }
}
