
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import java.util.NoSuchElementException;
import org.junit.*;
import static org.junit.Assert.*;

@Points("03-14")
public class LoytyykoListaltaTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void testi() {
        String[][] syotteet = {{"Terho", "Elina", "Aleksi", "Mari", ""}, {"Elina", "Aleksi", "Mari", ""}, {"Eka", "Toka", "Kolmas", "Neljäs", "Viides", "Kuudes", "Seitsemäs", ""}};

        for (int i = 0; i < syotteet.length; i++) {
            tarkista("Olematon", syotteet[i]);
            tarkista("Elina", syotteet[i]);
            tarkista("Mari", syotteet[i]);
        }
    }

    private void tarkista(String haettava, String... merkkijonot) {
        int oldOut = io.getSysOut().length();

        String in = "";
        boolean loytyi = false;
        for (int i = 0; i < merkkijonot.length; i++) {
            in += merkkijonot[i] + "\n";
            if (merkkijonot[i].equals(haettava)) {
                loytyi = true;
            }
        }
        
        in += haettava + "\n";

        io.setSysIn(in);
        callMain(LoytyykoListalta.class);
        String out = io.getSysOut().substring(oldOut);

        assertTrue("et tulosta mitään!", out.length() > 0);

        if (loytyi) {
            assertTrue("Kun etsittävä löytyy, tulostuksessa pitäisi olla haettavan nimi.\nEsim. \"Terho löytyi!\".\nNyt tulostus oli:\n" + out, out.contains(haettava));
            assertTrue("Kun etsittävä löytyy, tulostuksessa pitäisi olla teksti löytyi.\nEsim. \"Terho löytyi!\".\nNyt tulostus oli:\n" + out, out.contains("ytyi"));
            assertFalse("Kun etsittävä löytyy, tulostuksessa ei pitäisi olla tekstiä \"ei löytynyt\".\nEsim. \"Arto ei löytynyt!\".\nNyt tulostus oli:\n" + out, out.contains("ei"));

        } else {
            assertTrue("Kun etsittävää ei löydy, tulostuksessa pitäisi olla haettavan nimi.\nEsim. \"Terho ei löytynyt!\".\nNyt tulostus oli:\n" + out, out.contains(haettava));
            assertFalse("Kun etsittävää ei löydy, tulostuksessa ei pitäisi olla tekstiä \"löytyi\".\nNyt tulostus oli:\n" + out, out.contains("ytyi"));
            assertTrue("Kun etsittävää ei löydy, tulostuksessa pitäisi olla teksti \"ei löytynyt\".\nEsim. \"Arto ei löytynyt!\".\nNyt tulostus oli:\n" + out, out.contains("ei"));
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
}
