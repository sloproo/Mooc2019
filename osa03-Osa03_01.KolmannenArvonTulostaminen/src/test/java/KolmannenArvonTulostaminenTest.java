
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import java.util.NoSuchElementException;
import org.junit.*;
import static org.junit.Assert.*;

@Points("03-01")
public class KolmannenArvonTulostaminenTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void testi() {
        String[][] syotteet = {{"Terho", "Elina", "Aleksi", "Mari", "", "Aleksi"}, {"Elina", "Aleksi", "Mari", "", "Mari"}};

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
        callMain(KolmannenArvonTulostaminen.class);
        String out = io.getSysOut().substring(oldOut);

        assertTrue("et tulosta mitään!", out.length() > 0);

        String vastaus = otaSanaLopusta(out);
        String odotettuVastaus = merkkijonot[merkkijonot.length - 1];

        for (int i = 0; i < merkkijonot.length - 1; i++) {
            String nimi = merkkijonot[i];
            if (nimi.equals(odotettuVastaus)) {
                continue;
            }

            if (nimi.equals("")) {
                continue;
            }

            if (out.contains(nimi)) {
                fail("Syöte:\n" + in + "\nEi odotettu tulostusta \"" + nimi + "\", mutta se tulostettiin.\nTulostus oli:\n" + out);
            }
        }

        String virheIlm = "Syöte:\n" + in + "\n\n Odotettiin: \"" + odotettuVastaus + "\", tulostit: \"" + vastaus + "\"\n";
        assertEquals(virheIlm, odotettuVastaus, vastaus);
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

    private static String otaSanaLopusta(String inputStr) {
        String[] palat = inputStr.split("\\s+");
        return palat[palat.length - 1];
    }
}
