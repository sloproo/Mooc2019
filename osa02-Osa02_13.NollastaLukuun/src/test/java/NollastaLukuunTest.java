
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import org.junit.*;
import static org.junit.Assert.*;

@Points("02-13")
public class NollastaLukuunTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test(timeout = 1000)
    public void test1() {
        testaaTulostusJarjestyksessa("1\n", "0\n1\n", "\\s*0\\s*1\\s*", "-1", "2");
    }

    @Test(timeout = 1000)
    public void test2() {
        testaaTulostusJarjestyksessa("5\n", "0\n1\n2\n3\n4\n5\n", "\\s*0\\s*1\\s*2\\s*3\\s*4\\s*5\\s*", "-1", "6");
    }

    public void testaa(String syote, String odotettu, String... eiOdotetut) {

        int oldOut = io.getSysOut().length();
        io.setSysIn(syote);
        callMain(NollastaLukuun.class);
        String out = io.getSysOut().substring(oldOut);

        assertTrue("Kun syöte oli:\n" + syote + ", odotettiin tulostusta:\n" + odotettu + "\nNyt tulostus oli:\n" + out, out.contains(odotettu));
        for (String eiOdotettu : eiOdotetut) {
            assertFalse("Kun syöte oli:\n" + syote + ", tulostuksessa ei pitäisi olla:\n" + eiOdotettu + "", out.contains(eiOdotettu));
        }
    }

    public void testaaTulostusJarjestyksessa(String syote, String odotettuSelite, String odotettuRegex, String... eiOdotetut) {

        int oldOut = io.getSysOut().length();
        io.setSysIn(syote);
        callMain(NollastaLukuun.class);
        String out = io.getSysOut().substring(oldOut);

        assertTrue("Kun syöte oli:\n" + syote + ", odotettiin tulostusta:\n" + odotettuSelite + "\nNyt tulostus oli:\n" + out, out.matches(odotettuRegex));
        for (String eiOdotettu : eiOdotetut) {
            assertFalse("Kun syöte oli:\n" + syote + ", tulostuksessa ei pitäisi olla:\n" + eiOdotettu + "", out.contains(eiOdotettu));
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
