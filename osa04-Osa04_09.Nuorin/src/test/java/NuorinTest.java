
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Rule;
import org.junit.Test;

@Points("04-09")
public class NuorinTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void testi() {
        String[][] syotteet = {{"virpi,19", "jenna,21", "ada,20", "loppu"}, {"virpi,19", "jenna,21", "ada,20", "anton,3", "loppu"}, {"lilja,2", "virpi,19", "jenna,21", "ada,20", "anton,3", "loppu"}};

        for (int i = 0; i < syotteet.length; i++) {
            tarkista(syotteet[i]);
        }
    }

    private void tarkista(String... syotteet) {
        int oldOut = io.getSysOut().length();

        List<String> nimet = new ArrayList<>();

        String in = "";
        String nuorin = "";
        int nuorinIka = -1;
        for (int i = 0; i < syotteet.length; i++) {
            try {
                String[] osat = syotteet[i].split(",");

                String nimi = osat[0];
                int ika = Integer.valueOf(osat[1]);

                if (nuorinIka == -1 || ika < nuorinIka) {
                    nuorinIka = ika;
                    nuorin = nimi;
                }

                nimet.add(nimi);
            } catch (Exception e) {

            }

            in += syotteet[i] + "\n";
        }

        io.setSysIn(in);
        callMain(Nuorin.class);
        String out = io.getSysOut().substring(oldOut);

        assertTrue("Kun syöte on:\n" + in + "\net tulosta mitään!", out.length() > 0);

        String ehdotettu = otaLopusta(out);
        assertTrue("Kun syöte on:\n" + in + "\n\nOdotettiin että nuorin on " + nuorin + "\ntulostus oli: \"" + out + "\"\n", ehdotettu.contains(nuorin));

        for (String nimi : nimet) {
            if (nimi.equals(nuorin)) {
                continue;
            }

            assertFalse("Kun syöte on:\n" + in + "\n\nOdotettiin että nuorin on " + nuorin + "\nTulostuksessa ei tule olla muita nimiä.\nTulostus oli: \"" + out + "\"\n", ehdotettu.contains(nimi));
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

    private static String otaLopusta(String inputStr) {
        String[] palat = inputStr.split("\\s+");
        return palat[palat.length - 1];
    }
}
