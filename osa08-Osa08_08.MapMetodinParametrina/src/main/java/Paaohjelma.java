import java.util.Map;
import java.util.HashMap;

public class Paaohjelma {

    public static void main(String[] args) {
        // tee tänne testikoodia
        Map<String, String> nimet = new HashMap<>();
nimet.put("eka", "first");
nimet.put("toka", "second");

System.out.println(palautaKoko(nimet));

    }

    // toteuta tänne metodi palautaKoko, joka palauttaa parametrina 
    // saamansa map-olion alkioiden lukumäärän
    public static int palautaKoko(Map kartta) {
        return kartta.size();
    }
}
