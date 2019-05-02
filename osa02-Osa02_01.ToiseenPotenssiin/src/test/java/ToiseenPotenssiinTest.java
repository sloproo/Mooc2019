
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import org.junit.*;
import static org.junit.Assert.*;

@Points("02-01")
public class ToiseenPotenssiinTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void neljanPotenssi() {
        testaa("4\n", "16", "36");
    }

    @Test
    public void kuudenPotenssi() {
        testaa("6\n", "36", "16");
    }

    public void testaa(String syote, String odotettu, String... eiOdotetut) {

        int oldOut = io.getSysOut().length();
        io.setSysIn(syote);
        callMain(ToiseenPotenssiin.class);
        String out = io.getSysOut().substring(oldOut);

        assertTrue("Kun syöte oli " + syote + ", odotettiin tulostusta:\n" + odotettu + "\nTulostusta ei löytynyt.", out.contains(odotettu));
        for (String eiOdotettu : eiOdotetut) {
            assertFalse("Kun syöte oli " + syote + ", tulostuksessa ei pitäisi olla:\n" + eiOdotettu + "", out.contains(eiOdotettu));
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
