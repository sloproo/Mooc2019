
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.*;
import static org.junit.Assert.*;

@Points("01-37")
public class SyotteidenRajausTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test(timeout = 1000)
    public void test1() {
        testaa(5, 4, -3, 1, 0);
    }

    @Test(timeout = 1000)
    public void test2() {
        testaa(-2, 0);
    }

    @Test(timeout = 1000)
    public void test3() {
        testaa(-2, -3, -1, -4, -5, 0);
    }

    @Test(timeout = 1000)
    public void test4() {
        testaa(1, 6, 4, 5, 0);
    }

    public void testaa(int... luvut) {
        int oldOut = io.getSysOut().length();

        String syote = "";
        for (int i = 0; i < luvut.length; i++) {
            syote += luvut[i] + "\n";
        }

        io.setSysIn(syote);
        callMain(SyotteidenRajaus.class);
        String out = io.getSysOut().substring(oldOut);

        int syotaLkm = out.trim().split("Syötä luku.*").length;
        int syotaOdotettu = syote.split("\n").length;

        assertEquals("Kun syötettiin:\n" + syote + "\n\"Syötä luku\"-tekstejä pitäisi olla yhteensä " + syotaOdotettu + " kertaa. Nyt kertoja oli " + syotaLkm, syotaOdotettu, syotaLkm);

        int epakelpoLkm = out.trim().split("Epäkelpo luku").length - 1;
        int epakelpoOdotettu = (int) Arrays.stream(luvut).filter(luku -> luku < 0).count();

        assertEquals("Kun syötettiin:\n" + syote + "\n\"Epäkelpo luku\"-tekstejä pitäisi olla yhteensä " + epakelpoOdotettu + " kertaa. Nyt kertoja oli " + epakelpoLkm, epakelpoOdotettu, epakelpoLkm);

        List<Integer> olemattomatLuvut = new ArrayList<>(IntStream.range(2, 10).mapToObj(i -> i * i).collect(Collectors.toList()));

        for (int i = 0; i < luvut.length; i++) {
            if (luvut[i] <= 0) {
                continue;
            }

            int krt = luvut[i] * luvut[i];

            olemattomatLuvut.remove(new Integer(krt));

            assertTrue("Kun syötettiin:\n" + syote + "\nTulostuksessa pitäisi esiintyä luku " + krt + ". Nyt ei esiintynyt. Tulostus:\n" + out, out.contains("" + krt));
        }

        for (Integer luku : olemattomatLuvut) {
            assertFalse("Kun syötettiin:\n" + syote + "\nTulostuksessa ei pitäisi esiintyä lukua " + luku + ". Nyt esiintyi. Tulostus:\n" + out, out.contains("" + luku));
        }
    }

    private void callMain(Class kl) {
        try {
            kl = ReflectionUtils.newInstanceOfClass(kl);
            String[] t = null;
            String x[] = new String[0];
            Method m = ReflectionUtils.requireMethod(kl, "main", x.getClass());
            ReflectionUtils.invokeMethod(Void.TYPE, m, null, (Object) x);
        } catch (Throwable e) {
            fail("Jotain kummallista tapahtui. Saattaa olla että " + kl + "-luokan public static void main(String[] args) -metodi on hävinnyt\n"
                    + "tai ohjelmasi kaatui poikkeukseen. Lisätietoja " + e);
        }
    }

}
