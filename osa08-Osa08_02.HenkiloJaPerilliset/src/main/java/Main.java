import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        // tee tänne testikoodia
        ArrayList<Henkilo> henkilot = new ArrayList<>();
    henkilot.add(new Opettaja("Ada Lovelace", "Korsontie 1 03100 Vantaa", 1200));
    henkilot.add(new Opiskelija("Olli", "Ida Albergintie 1 00400 Helsinki"));

    tulostaHenkilot(henkilot);
    }
    
    public static void tulostaHenkilot(ArrayList<Henkilo> henkilot) {
        for (int i = 0; i < henkilot.size(); i++) {
            System.out.println(henkilot.get(i));
        }
    }

}
