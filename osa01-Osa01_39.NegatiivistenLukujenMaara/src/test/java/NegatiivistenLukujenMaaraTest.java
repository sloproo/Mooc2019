
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import java.util.Arrays;
import org.junit.*;
import static org.junit.Assert.*;

@Points("01-39")
public class NegatiivistenLukujenMaaraTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test(timeout = 1000)
    public void test1() {
        testaa(-5, 4, -3, 1, 0);
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
        callMain(NegatiivistenLukujenMaara.class);
        String out = io.getSysOut().substring(oldOut);

        int syotaLkm = out.trim().split("Syötä luku").length - 1;
        int syotaOdotettu = syote.split("\n").length;

        assertEquals("Kun syötettiin:\n" + syote + "\n\"Syötä luku\"-tekstejä pitäisi olla yhteensä " + syotaOdotettu + " kertaa. Nyt kertoja oli " + syotaLkm, syotaOdotettu, syotaLkm);

        int negat = (int) Arrays.stream(luvut).filter(luku -> luku < 0).count();
        
        String odotettu = "Negatiivisia lukuja yhteensä " + negat;
        assertTrue("Kun syötettiin:\n" + syote + "\nOhjelmassa pitäisi olla tulostus \"" + odotettu + "\". Nyt tulostus oli " + out, out.contains(odotettu));

        for (int i = 0; i < 10; i++) {
            if (i == negat) {
                continue;
            }

            String eiOdotettu = "Negatiivisia lukuja yhteensä " + i;
            assertFalse("Kun syötettiin:\n" + syote + "\nOhjelmassa ei pitäisi olla tulostusta \"" + eiOdotettu + "\". Nyt tulostus oli " + out, out.contains(eiOdotettu));
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
