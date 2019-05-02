
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.*;
import static org.junit.Assert.*;

@Points("04-02")
public class KuutiotTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void testi() {
        String[][] syotteet = {{"8", "3", "123", "loppu"}, {"9", "loppu"}, {"16", "32", "loppu"}};

        for (int i = 0; i < syotteet.length; i++) {
            tarkista(syotteet[i]);
        }
    }

    private void tarkista(String... syotteet) {
        int oldOut = io.getSysOut().length();

        List<Integer> odotetut = new ArrayList<>();
        String in = "";
        for (int i = 0; i < syotteet.length; i++) {
            try {
                int luku = Integer.valueOf(syotteet[i]);
                odotetut.add(luku * luku * luku);
            } catch (Exception e) {

            }

            in += syotteet[i] + "\n";
        }

        io.setSysIn(in);
        callMain(Kuutiot.class);
        String out = io.getSysOut().substring(oldOut);

        assertTrue("Kun syöte on:\n" + in + "\net tulosta mitään!", out.length() > 0);

        String[] tulostukset = otaTulostukset(out);
        for (String tulostus : tulostukset) {
            int luku = -1;
            try {
                luku = Integer.valueOf(tulostus);
            } catch (NumberFormatException e) {
                continue;
            }

            if (!odotetut.contains(luku)) {
                fail("Syöte:\n" + in + "\nEi odotettu tulostusta \"" + luku + "\", mutta se tulostettiin.\nTulostus oli:\n" + out);
            }

            odotetut.remove(Integer.valueOf(luku));
        }

        if (!odotetut.isEmpty()) {
            for (Integer odotettuLuku : odotetut) {
                fail("Syöte:\n" + in + "\n\n Odotettiin lukua: \"" + odotettuLuku + "\", tulostus oli: \"" + out + "\"\n");
            }

        }
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

    private static String[] otaTulostukset(String str) {
        return str.split("\\s+");
    }
}
