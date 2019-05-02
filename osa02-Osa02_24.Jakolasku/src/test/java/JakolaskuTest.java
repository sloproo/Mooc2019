
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Method;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Rule;
import org.junit.Test;

@Points("02-24")
public class JakolaskuTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test(timeout = 1000)
    public void test1() throws Throwable {
        int oldOut = io.getSysOut().length();
        Reflex.reflect(Jakolasku.class).staticMethod("jakolasku").returningVoid().taking(int.class, int.class).invoke(2, 1);
        String out = io.getSysOut().substring(oldOut);
        tarkistaTulostus(out, "2, 1", "2.0", "0.5", "1.0", "3.5");
    }

    @Test(timeout = 1000)
    public void test2() throws Throwable {
        int oldOut = io.getSysOut().length();
        Reflex.reflect(Jakolasku.class).staticMethod("jakolasku").returningVoid().taking(int.class, int.class).invoke(7, 2);
        String out = io.getSysOut().substring(oldOut);
        tarkistaTulostus(out, "7, 2", "3.5", "3.0", "4.0", "2.0");
    }

    public void testaa(String syote, String odotettu, String... eiOdotetut) {

        int oldOut = io.getSysOut().length();
        io.setSysIn(syote);
        callMain(Jakolasku.class);
        String out = io.getSysOut().substring(oldOut);

        tarkistaTulostus(out, syote, odotettu, eiOdotetut);
    }

    public void tarkistaTulostus(String out, String syote, String odotettu, String... eiOdotetut) {

        assertTrue("Kun syöte oli:\n" + syote + ", odotettiin tulostusta:\n" + odotettu + "\nNyt tulostus oli:\n" + out, out.contains(odotettu));
        for (String eiOdotettu : eiOdotetut) {
            assertFalse("Kun syöte oli:\n" + syote + ", tulostuksessa ei pitäisi olla:\n" + eiOdotettu + "", out.contains(eiOdotettu));
        }
    }

    public void testaaTulostusJarjestyksessa(String syote, String odotettuSelite, String odotettuRegex, String... eiOdotetut) {

        int oldOut = io.getSysOut().length();
        io.setSysIn(syote);
        callMain(Jakolasku.class);
        String out = io.getSysOut().substring(oldOut);

        tarkistaTulostusJarjestyksessa(out, syote, odotettuSelite, odotettuRegex, eiOdotetut);
    }

    public void tarkistaTulostusJarjestyksessa(String tulostus, String syote, String odotettuSelite, String odotettuRegex, String... eiOdotetut) {

        assertTrue("Kun syöte oli:\n" + syote + ", odotettiin tulostusta:\n" + odotettuSelite + "\nNyt tulostus oli:\n" + tulostus, tulostus.matches(odotettuRegex));
        for (String eiOdotettu : eiOdotetut) {
            assertFalse("Kun syöte oli:\n" + syote + ", tulostuksessa ei pitäisi olla:\n" + eiOdotettu + "", tulostus.contains(eiOdotettu));
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
