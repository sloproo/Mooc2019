
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import org.junit.*;
import static org.junit.Assert.*;

@Points("03-11")
public class PienimmanLuvunIndeksiTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void testi() {
        int[][] syotteet = {{72, 2, 8, 8, 11, 9999}, {72, 44, 8, 8, 11, 9999}, {51, 22, -11, -140, -18, 9999}, {1, 7, 9999}, {3, 2, 9999}, {-3, -2, -141, 22, 22, 7, 9999}};

        for (int i = 0; i < syotteet.length; i++) {
            tarkista(syotteet[i]);
        }
    }

    private void tarkista(int... luvut) {
        int oldOut = io.getSysOut().length();

        Set<Integer> indeksit = new HashSet<>();

        String in = "";
        int pienin = luvut[0];
        for (int i = 0; i < luvut.length - 1; i++) {
            in += luvut[i] + "\n";
            if (pienin > luvut[i]) {
                pienin = luvut[i];
                indeksit.clear();
                indeksit.add(i);
            }
        }

        in += "9999\n";

        io.setSysIn(in);
        callMain(PienimmanLuvunIndeksi.class);
        String out = io.getSysOut().substring(oldOut);

        assertTrue("et tulosta mitään!", out.length() > 0);

        assertTrue("Pienin luku tulee ilmoittaa muodossa \"Pienin luku on " + pienin + "\", missä " + pienin + " on pienin luku.", out.contains("luku on " + pienin));

        for (Integer indeksi : indeksit) {
            assertTrue("Kaikki pienimmän luvun indeksit tulee tulostaa. Syötteellä:\n" + in + "\nTulostus oli:\n" + out, out.contains("" + indeksi));
        }
    }

    private void callMain(Class kl) {
        try {
            kl = ReflectionUtils.newInstanceOfClass(kl);
            String[] t = null;
            String x[] = new String[0];
            Method m = ReflectionUtils.requireMethod(kl, "main", x.getClass());
            ReflectionUtils.invokeMethod(Void.TYPE, m, null, (Object) x);
        } catch (NoSuchElementException e) {
            fail("Ohjelmasi koitti lukea liikaa syötettä. Muista lukea nextLine()-metodilla!");
        } catch (Throwable e) {
            fail(kl + "-luokan public static void main(String[] args) -metodi on hävinnyt "
                    + "tai jotain muuta odottamatonta tapahtunut, lisätietoja " + e);
        }
    }
}
