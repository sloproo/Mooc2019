
import java.util.HashMap;

public class Ohjelma {

    public static void main(String[] args) {
        // Testaa ohjelmasi toimintaa täällä!
        HashMap<String, String> taulu = new HashMap<>();
        taulu.put("esim.", "esimerkiksi");
        taulu.put("jne.", "ja niin edelleen");
        taulu.put("yms.", "ynnä muuta sellaista");

        tulostaAvaimet(taulu);
        System.out.println("---");
        tulostaAvaimetJoissa(taulu, "m");
        System.out.println("---");
        tulostaArvotJosAvaimessa(taulu, "ne");
        }

    
    
        public static void tulostaAvaimet(HashMap<String, String> hajautustaulu) {
            for (String avain : hajautustaulu.keySet() ) {
                System.out.println(avain);
            }
        }
        
        public static void tulostaAvaimetJoissa(HashMap<String, String> 
                hajautustaulu, String merkkijono) {
            for (String haunkohde : hajautustaulu.keySet()) {
                if (haunkohde.contains(merkkijono)) {
                    System.out.println(haunkohde);
                }
            }
        }
        
        public static void tulostaArvotJosAvaimessa(HashMap<String, String> 
                hajautustaulu, String merkkijono) {
            for (String haunkohde : hajautustaulu.keySet()) {
                if (haunkohde.contains(merkkijono)) {
                    System.out.println(hajautustaulu.get(haunkohde));
                }
            }
            
            
            
            
            
            
        }
    }


