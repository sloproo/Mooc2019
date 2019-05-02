import java.util.ArrayList;

public class Paaohjelma {

    public static void main(String[] args) {
        Opiskelija eka = new Opiskelija("jamo");
        Opiskelija toka = new Opiskelija("jamo1");
        System.out.println(eka.compareTo(toka));
        
        ArrayList<Opiskelija> opiskelijat = new ArrayList<>();
        opiskelijat.add(eka);
        opiskelijat.add(toka);
        opiskelijat.add(new Opiskelija ("Kari"));
        opiskelijat.add(new Opiskelija ("Liisa"));
        opiskelijat.add(new Opiskelija ("Lassi"));
        opiskelijat.add(new Opiskelija ("Jorkki"));
        
        opiskelijat.stream().sorted().forEach(o -> System.out.println(o));
       
    }
}
