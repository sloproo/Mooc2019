
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import java.util.NoSuchElementException;
import org.junit.*;
import static org.junit.Assert.*;

@Points("03-24")
public class OnkoTottaTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void sopivatKayvat() {
        String sana = "totta";

        int oldOut = io.getSysOut().length();
        io.setSysIn(sana + "\n");
        callMain(OnkoTotta.class);
        String out = io.getSysOut().substring(oldOut);

        assertTrue("et tulosta mitään!", out.length() > 0);

        assertTrue("syötteellä: \"" + sana + "\" tulostit \"" + out + "\" kun pitäisi tulostaa \"Oikein meni!\". Muista, että merkkijonoja ei voi vertailla ==:lla vaan pitää käyttää equals:ia!", out.toLowerCase().contains("ikein"));;
        assertTrue("syötteellä: \"" + sana + "\" tulostit \"" + out + "\" kun pitäisi tulostaa \"Oikein meni!\". Muista, että merkkijonoja ei voi vertailla ==:lla vaan pitää käyttää equals:ia!", !(out.toLowerCase().contains("appa") && out.toLowerCase().contains("rin")));
    }

    @Test
    public void sopimattomatEivatKay() {
        String[] sanat = {
            "salaisuus",
            "potta",
            "kah-totta",
            "tottapa",
            "ahaa"
        };

        for (String sana : sanat) {
            sopimatonEiKay(sana);
        }
    }

    private void sopimatonEiKay(String sana) {
        int oldOut = io.getSysOut().length();
        io.setSysIn(sana + "\n");
        callMain(OnkoTotta.class);
        String out = io.getSysOut().substring(oldOut);

        assertTrue("et tulosta mitään!", out.length() > 0);

        assertTrue("syötteellä: \"" + sana + "\" tulostit \"" + out + "\" kun pitäisi tulostaa \"Koitappa uudelleen!\". Muista, että merkkijonoja ei voi vertailla ==:lla vaan pitää käyttää equals:ia!", !out.toLowerCase().contains("ikein"));
        assertTrue("syötteellä: \"" + sana + "\" tulostit \"" + out + "\" kun pitäisi tulostaa \"Koitappa uudelleen!\". Muista ,että merkkijonoja ei voi vertailla ==:lla vaan pitää käyttää equals:ia!", (out.toLowerCase().contains("appa") || out.toLowerCase().contains("rin")));
    }

    private void callMain(Class kl) {
        try {
            kl = ReflectionUtils.newInstanceOfClass(kl);
            String[] t = null;
            String x[] = new String[0];
            Method m = ReflectionUtils.requireMethod(kl, "main", x.getClass());
            ReflectionUtils.invokeMethod(Void.TYPE, m, null, (Object) x);
        } catch (NoSuchElementException e) {
            fail("luethan syöteen käyttäjältä lukija.nextLine()-komennolla?");
        } catch (Throwable e) {
            fail("Jotain kummallista tapahtui. Saattaa olla että " + kl + "-luokan public static void main(String[] args) -metodi on hävinnyt\n"
                    + "tai ohjelmasi kaatui poikkeukseen. Lisätietoja " + e);
        }
    }
}
