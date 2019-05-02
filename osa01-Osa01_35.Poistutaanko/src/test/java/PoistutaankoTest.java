
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import org.junit.*;
import static org.junit.Assert.*;

@Points("01-35")
public class PoistutaankoTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test(timeout = 1000)
    public void test1() {
        testaa("ei\nei\nnej\nkyllä\n");
    }

    @Test(timeout = 1000)
    public void test2() {
        testaa("kyllä\n");
    }

    @Test(timeout = 1000)
    public void test3() {
        testaa("ej\njej\nej\njej\nkyllä\n");
    }

    public void testaa(String syote) {
        int oldOut = io.getSysOut().length();

        io.setSysIn(syote);
        callMain(Poistutaanko.class);
        String out = io.getSysOut().substring(oldOut);

        int count = out.trim().split("oistu").length - 1;
        int odotettu = syote.split("\n").length;
        assertEquals("Kun syötettiin:\n" + syote + "\nPoistutaanko?-kysymyksiä pitäisi olla yhteensä " + odotettu + " kertaa. Nyt kertoja oli " + count, odotettu, count);
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
