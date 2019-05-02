
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.*;
import static org.junit.Assert.*;

@Points("03-08")
public class LuvutRajatullaAlueellaTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void testi() {
        String[][] syotteet = {{"3", "2", "1", "4", "7", "-1", "0", "1"}, {"3", "9", "2", "8", "-1", "2", "3"}};

        for (int i = 0; i < syotteet.length; i++) {
            tarkista(syotteet[i]);
        }
    }

    private void tarkista(String... syote) {
        int oldOut = io.getSysOut().length();

        int alaraja = Integer.valueOf(syote[syote.length - 2]);
        int ylaraja = Integer.valueOf(syote[syote.length - 1]);

        String in = "";
        List<Integer> luvut = new ArrayList<>();
        for (int i = 0; i < syote.length; i++) {
            in += syote[i] + "\n";

            if (i < syote.length - 2) {
                luvut.add(Integer.valueOf(syote[i]));
            }
        }

        io.setSysIn(in);
        callMain(LuvutRajatullaAlueella.class);
        String out = io.getSysOut().substring(oldOut);

        assertTrue("et tulosta mitään!", out.length() > 0);

        for (int i = alaraja; i <= ylaraja; i++) {
            if (!out.contains("" + luvut.get(i))) {
                fail("Syöte:\n" + in + "\nOdotettiin lukua \"" + luvut.get(i) + "\", mutta sitä ei tulostettu.\nTulostus oli:\n" + out);
            }
        }

        NEXT:
        for (int i = 0; i < 50; i++) {

            for (int indeksi = alaraja; indeksi <= ylaraja; indeksi++) {
                if (luvut.get(indeksi) == i) {
                    continue NEXT;
                }
            }

            if (out.contains("" + i)) {
                fail("Syöte:\n" + in + "\nEi odotettu lukua \"" + i + "\", mutta se tulostettiin.\nTulostus oli:\n" + out);
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

    private static String[] otaSanatLopusta(String inputStr) {
        String[] palat = inputStr.split("\\s+");
        return new String[]{palat[palat.length - 2], palat[palat.length - 1]};
    }
}
