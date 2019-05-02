
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.*;
import static org.junit.Assert.*;

@Points("03-13")
public class ListanLukujenKeskiarvoTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void testi() {
        int[][] syotteet = {{5, 22, -11, -140, -18}, {1}, {3, 2, 1}, {-3, -2, -141}};

        for (int i = 0; i < syotteet.length; i++) {
            tarkista(syotteet[i]);
        }
    }

    private void tarkista(int... luvut) {
        int oldOut = io.getSysOut().length();

        String in = "";
        int summa = 0;
        for (int i = 0; i < luvut.length; i++) {
            in += luvut[i] + "\n";
            summa += luvut[i];
        }
        
        in += "-1\n";
        

        io.setSysIn(in);
        callMain(ListanLukujenKeskiarvo.class);
        String out = io.getSysOut().substring(oldOut);

        assertTrue("et tulosta mitään!", out.length() > 0);

        double vastaus = otaLukuLopusta(out);
        double odotettuVastaus = (1.0 * summa / luvut.length);
        
        String virheIlm = "Syöte:\n" + in + "\n\n Odotettiin: \"" + odotettuVastaus + "\", tulostit: \"" + vastaus + "\"\n";
        assertEquals(virheIlm, odotettuVastaus, vastaus, 0.001);
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

    private static double otaLukuLopusta(String inputStr) {

        String patternStr = "(?s).*?(-?\\d+\\.\\d+)\\s*$";

        Matcher matcher = Pattern.compile(patternStr).matcher(inputStr);

        assertTrue("Tulostuksen pitäisi olla muotoa \"Keskiarvo: -12.0\". Nyt tulostit: " + inputStr, matcher.find());

        double luku = Double.parseDouble(matcher.group(1));
        return luku;
    }
}
