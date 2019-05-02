
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Method;
import org.junit.*;
import static org.junit.Assert.*;

@Points("02-25")
public class KolmellaJaollisetTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test(timeout = 1000)
    public void test1() throws Throwable {
        int oldOut = io.getSysOut().length();
        Reflex.reflect(KolmellaJaolliset.class).staticMethod("kolmellaJaollisetValilta").returningVoid().taking(int.class, int.class).invoke(1, 13);
        String out = io.getSysOut().substring(oldOut);
        
        tarkistaTulostusJarjestyksessa(out, "1, 13", "3\n6\n9\n12\n", "\\s*3\\s*6\\s*9\\s*12\\s", "4", "5", "0", "7", "8", "10", "11", "15");
    }

    @Test(timeout = 1000)
    public void test2() throws Throwable {
        int oldOut = io.getSysOut().length();
        Reflex.reflect(KolmellaJaolliset.class).staticMethod("kolmellaJaollisetValilta").returningVoid().taking(int.class, int.class).invoke(9, 12);
        String out = io.getSysOut().substring(oldOut);
        
        tarkistaTulostusJarjestyksessa(out, "9, 12", "9\n12\n", "\\s*9\\s*12\\s*", "3", "6", "8", "13", "15");
    }

    public void testaa(String syote, String odotettu, String... eiOdotetut) {

        int oldOut = io.getSysOut().length();
        io.setSysIn(syote);
        callMain(KolmellaJaolliset.class);
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
        callMain(KolmellaJaolliset.class);
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
