
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.util.ArrayList;
import java.util.Random;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

@Points("06-01")
public class MaPuTest {

    @Test
    public void onMetodiMaPuKeskiarvo() {
        Reflex.reflect(MaPu.class).staticMethod("keskiarvo").returning(double.class).taking(ArrayList.class).requirePublic();
    }

    @Test
    public void metodiLaskeeKeskiarvon() throws Throwable {

        ArrayList<Integer> luvut = new ArrayList<>();
        int summa = 0;

        for (int i = 0; i < 10; i++) {
            int luku = new Random().nextInt(100) + 100;
            luvut.add(luku);

            summa = summa + luku;
        }

        double odotettuKeskiarvo = 1.0 * summa / luvut.size();
        double ka = Reflex.reflect(MaPu.class).staticMethod("keskiarvo").returning(double.class).taking(ArrayList.class).invoke(luvut);

        assertEquals("Kun listalla on luvut:\n" + luvut + "\npitäisi keskiarvon olla:\n" + odotettuKeskiarvo + "\nMaPu.keskiarvo väitti keskiarvon olevan:\n" + ka, odotettuKeskiarvo, ka, 0.001);
    }
}
