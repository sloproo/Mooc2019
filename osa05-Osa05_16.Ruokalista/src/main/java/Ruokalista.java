
import java.util.ArrayList;

public class Ruokalista {

    private ArrayList<String> ateriat;

    public Ruokalista() {
        this.ateriat = new ArrayList<>();
    }
    

    // toteuta tänne tarvittavat metodit
    public void lisaaAteria(String ateria) {
        if (!(this.ateriat.contains(ateria))) { 
            this.ateriat.add(ateria);
        }
    }
    
    public void tulostaAteriat() {
        for (String ateria: ateriat) {
            System.out.println(ateria);
        }
    }
    
    public void tyhjennaRuokalista() {
        this.ateriat.clear();
    }

}
