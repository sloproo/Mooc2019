
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.*;
import static org.junit.Assert.*;

@Points("03-07")
public class ListanLuvutTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void testi() {
        String[][] syotteet = {{"3", "2", "1", "4", "7", "-1"}, {"3", "9", "2", "8", "-1"}};

        for (int i = 0; i < syotteet.length; i++) {
            tarkista(syotteet[i]);
        }
    }

    private void tarkista(String... syote) {
        int oldOut = io.getSysOut().length();

        String in = "";
        List<Integer> luvut = new ArrayList<>();
        for (int i = 0; i < syote.length; i++) {
            in += syote[i] + "\n";
            luvut.add(Integer.valueOf(syote[i]));
        }

        io.setSysIn(in);
        callMain(ListanLuvut.class);
        String out = io.getSysOut().substring(oldOut);

        assertTrue("et tulosta mitään!", out.length() > 0);

        for (int i = 0; i < 50; i++) {
            if (luvut.contains(i) && !out.contains("" + i)) {
                fail("Syöte:\n" + in + "\nEi odotettu lukua \"" + i + "\", mutta se tulostettiin.\nTulostus oli:\n" + out);
            }

            if (!luvut.contains(i) && out.contains("" + i)) {
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
