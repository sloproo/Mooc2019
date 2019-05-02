
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import org.junit.*;
import static org.junit.Assert.*;

@Points("01-36")
public class UudestaanTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test(timeout = 1000)
    public void test1() {
        testaa("1\n5\n4\n");
    }

    @Test(timeout = 1000)
    public void test2() {
        testaa("4\n");
    }

    @Test(timeout = 1000)
    public void test3() {
        testaa("-2\n-7\n99\n123\n4\n");
    }

    public void testaa(String syote) {
        int oldOut = io.getSysOut().length();

        io.setSysIn(syote);
        callMain(Uudestaan.class);
        String out = io.getSysOut().substring(oldOut);

        int count = out.trim().split("luk").length - 1;
        int odotettu = syote.split("\n").length;
        assertEquals("Kun syötettiin luvut:\n" + syote + "\nlukuja pitäisi kysyä yhteensä " + odotettu + " kertaa. Nyt kertoja oli " + count, odotettu, count);
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
