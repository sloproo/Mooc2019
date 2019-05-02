
import java.util.ArrayList;

public class MaPu {

    // toteuta apumetodit tänne
    public static double keskiarvo(ArrayList<Integer> luvut) {
        int i = 0;
        int summa = 0;
        while (i < luvut.size()) {
            summa = summa + luvut.get(i);
            i++;
        }
        return 1.0 * summa / luvut.size();
    }
}
