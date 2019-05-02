
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.*;
import static org.junit.Assert.*;

@Points("03-05")
public class ListanViimeinenArvoTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void testi() {
        String[][] syotteet = {{"Terho", "Elina", "Aleksi", "Mari", "", "Mari"}, {"Elina", "Mari", "Aleksi", "", "Aleksi"}, {"Eka", "Toka", "Kolmas", "Neljäs", "Viides", "Kuudes", "Seitsemas", "", "Seitsemas"}};

        for (int i = 0; i < syotteet.length; i++) {
            tarkista(syotteet[i]);
        }
    }

    private void tarkista(String... merkkijonot) {
        int oldOut = io.getSysOut().length();

        String in = "";
        for (int i = 0; i < merkkijonot.length - 1; i++) {
            in += merkkijonot[i] + "\n";
        }

        io.setSysIn(in);
        callMain(ListanViimeinenArvo.class);
        String out = io.getSysOut().substring(oldOut);

        assertTrue("et tulosta mitään!", out.length() > 0);

        String[] vastaus = otaSanatLopusta(out);

        String viimeinenSana = merkkijonot[merkkijonot.length - 1];

        for (int i = 0; i < merkkijonot.length - 1; i++) {
            String nimi = merkkijonot[i];
            if (nimi.equals(viimeinenSana)) {
                continue;
            }

            if (nimi.equals("")) {
                continue;
            }

            if (out.contains(nimi)) {
                fail("Syöte:\n" + in + "\nEi odotettu tulostusta \"" + nimi + "\", mutta se tulostettiin.\nTulostus oli:\n" + out);
            }
        }

        String virheIlm = "Syöte:\n" + in + "\n\n Odotettiin:\n" + viimeinenSana + "\ntulostit: \"" + vastaus + "\"\n";
        assertEquals(virheIlm, viimeinenSana, vastaus[0]);
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

    private static String[] otaSanatLopusta(String inputStr) {
        String[] palat = inputStr.split("\\s+");
        return new String[]{palat[palat.length - 1]};
    }
}
