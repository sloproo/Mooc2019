
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import java.util.NoSuchElementException;
import org.junit.*;
import static org.junit.Assert.*;

@Points("03-06")
public class EnsimmainenJaViimeinenArvoTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void testi() {
        String[][] syotteet = {{"Terho", "Elina", "Aleksi", "Mari", "", "Terho", "Mari"}, {"Elina", "Aleksi", "Mari", "", "Elina", "Mari"}, {"Eka", "Toka", "Kolmas", "Neljäs", "Viides", "Kuudes", "Seitsemas", "", "Eka", "Seitsemas"}};

        for (int i = 0; i < syotteet.length; i++) {
            tarkista(syotteet[i]);
        }
    }

    private void tarkista(String... merkkijonot) {
        int oldOut = io.getSysOut().length();

        String in = "";
        for (int i = 0; i < merkkijonot.length - 2; i++) {
            in += merkkijonot[i] + "\n";
        }

        io.setSysIn(in);
        callMain(EnsimmainenJaViimeinenArvo.class);
        String out = io.getSysOut().substring(oldOut);

        assertTrue("et tulosta mitään!", out.length() > 0);

        String[] vastaus = otaSanatLopusta(out);

        String odotettu1 = merkkijonot[merkkijonot.length - 2];
        String odotettu2 = merkkijonot[merkkijonot.length - 1];

        for (int i = 0; i < merkkijonot.length - 1; i++) {
            String nimi = merkkijonot[i];
            if (nimi.equals(odotettu1) || nimi.equals(odotettu2)) {
                continue;
            }

            if (nimi.equals("")) {
                continue;
            }

            if (out.contains(nimi)) {
                fail("Syöte:\n" + in + "\nEi odotettu tulostusta \"" + nimi + "\", mutta se tulostettiin.\nTulostus oli:\n" + out);
            }
        }

        String virheIlm = "Syöte:\n" + in + "\n\n Odotettiin:\n" + odotettu1 + "\n" + odotettu2 + "\ntulostit: \"" + vastaus + "\"\n";
        assertEquals(virheIlm, odotettu1, vastaus[0]);
        assertEquals(virheIlm, odotettu2, vastaus[1]);
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
        return new String[]{palat[palat.length - 2], palat[palat.length - 1]};
    }
}
