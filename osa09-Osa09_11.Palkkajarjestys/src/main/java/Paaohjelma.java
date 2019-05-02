
import java.util.ArrayList;
import java.util.Collections;

public class Paaohjelma {

    public static void main(String[] args) {
        ArrayList<Ihminen> ihmisia = new ArrayList<>();
        ihmisia.add(new Ihminen("Matti", 150000));
        ihmisia.add(new Ihminen("Merja", 500));
        ihmisia.add(new Ihminen("Pertti", 80));

        System.out.println(ihmisia);

        /*
         * Poista alla olevat kommentit, kun olet tehnyt compareTo metodin.
         */
        Collections.sort(ihmisia);
        System.out.println(ihmisia);

    }
}
