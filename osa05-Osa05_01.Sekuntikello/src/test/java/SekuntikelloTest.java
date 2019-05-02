
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import org.junit.*;
import static org.junit.Assert.*;

@Points("05-01")
public class SekuntikelloTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void luokkaJaKonstruktori() {
        luoKello();
    }

    @Test
    public void toStringAlussa() {
        Object kello = luoKello();

        String toStringKellolta = kutsuToString(kello);

        assertEquals("Juuri luodun sekuntikellon toString-tulostuksen pitäisi olla \"00:00\". Nyt se oli " + toStringKellolta + "\nKokeile:\n"
                + "Sekuntikello k = new Sekuntikello();\n"
                + "System.out.println(k);", "00:00", toStringKellolta);
    }

    @Test
    public void eteneMetodiOn() {
        Object kello = luoKello();
        Reflex.reflect("Sekuntikello").method("etene").returningVoid().takingNoParams().requirePublic();

        try {
            Reflex.reflect("Sekuntikello").method("etene").returningVoid().takingNoParams().invokeOn(kello);
        } catch (Throwable t) {
            fail("Virhe metodia etene kutsuttaessa. Virhe oli: " + t.getMessage() + "\nKokeile:\n"
                    + "Sekuntikello k = new Sekuntikello();\n"
                    + "k.etene();");
        }

        String toStringKellolta = kutsuToString(kello);

        assertEquals("Kerran edenneen sekuntikellon toString-tulostuksen pitäisi olla \"00:01\". Nyt se oli " + toStringKellolta + "\nKokeile:\n"
                + "Sekuntikello k = new Sekuntikello();\n"
                + "k.etene();\n"
                + "System.out.println(k);", "00:01", toStringKellolta);
    }

    @Test
    public void etenePitkalle() {
        Object kello = luoKello();

        int satunnainenEtenemisMaara = new Random().nextInt(1000) + 1000;
        for (int i = 1; i <= satunnainenEtenemisMaara; i++) {
            try {
                Reflex.reflect("Sekuntikello").method("etene").returningVoid().takingNoParams().invokeOn(kello);
            } catch (Throwable t) {
                fail("Virhe metodia etene monta kertaa kutsuttaessa. Virhe oli: " + t.getMessage() + "\nKokeile kutsua etene-metodia " + (i) + " kertaa.");
            }
        }

        int sekunnit = ((satunnainenEtenemisMaara / 100) % 60);
        int sadasosasekunnit = (satunnainenEtenemisMaara % 100);

        String s = sekunnit < 10 ? "0" + sekunnit : "" + sekunnit;
        String sos = sadasosasekunnit < 10 ? "0" + sadasosasekunnit : "" + sadasosasekunnit;

        String toStringKellolta = kutsuToString(kello);
        String odotettuTulostus = "" + s + ":" + sos;

        assertEquals("Kun kellolle kutsutaan metodia etene " + satunnainenEtenemisMaara + " kertaa, tulostuksen pitäisi olla \"" + odotettuTulostus + "\".\nNyt se oli " + toStringKellolta + "\nKokeile:\n"
                + "Sekuntikello k = new Sekuntikello();\n"
                + "int i = 0;\n"
                + "while (i < " + satunnainenEtenemisMaara + ") {\n"
                + "    k.etene();\n"
                + "}"
                + "System.out.println(k);", odotettuTulostus, toStringKellolta);
    }

    @Test
    public void eteneHyvinPitkalle() {
        Object kello = luoKello();

        int satunnainenEtenemisMaara = new Random().nextInt(10000) + 360000;
        for (int i = 1; i <= satunnainenEtenemisMaara; i++) {
            try {
                Reflex.reflect("Sekuntikello").method("etene").returningVoid().takingNoParams().invokeOn(kello);
            } catch (Throwable t) {
                fail("Virhe metodia etene monta kertaa kutsuttaessa. Virhe oli: " + t.getMessage() + "\nKokeile kutsua etene-metodia " + (i) + " kertaa.");
            }
        }

        int sekunnit = ((satunnainenEtenemisMaara / 100) % 60);
        int sadasosasekunnit = (satunnainenEtenemisMaara % 100);

        String s = sekunnit < 10 ? "0" + sekunnit : "" + sekunnit;
        String sos = sadasosasekunnit < 10 ? "0" + sadasosasekunnit : "" + sadasosasekunnit;

        String toStringKellolta = kutsuToString(kello);
        String odotettuTulostus = "" + s + ":" + sos;

        assertEquals("Kun kellolle kutsutaan metodia etene " + satunnainenEtenemisMaara + " kertaa, tulostuksen pitäisi olla \"" + odotettuTulostus + "\".\nNyt se oli " + toStringKellolta + "\nKokeile:\n"
                + "Sekuntikello k = new Sekuntikello();\n"
                + "int i = 0;\n"
                + "while (i < " + satunnainenEtenemisMaara + ") {\n"
                + "    k.etene();\n"
                + "}"
                + "System.out.println(k);", odotettuTulostus, toStringKellolta);
    }

    private String kutsuToString(Object kello) {
        String out = io.getSysOut();

        String toStringKellolta = null;

        try {
            toStringKellolta = kello.toString();
        } catch (Throwable e) {
            fail("Virhe kellon toString-metodia kutsuttaessa. Kokeile:\n"
                    + "Sekuntikello kello = new Sekuntikello();\n"
                    + "kello.toString();\n"
                    + "Virhe oli: " + e.getMessage());
        }

        assertEquals("Metodikutsun toString ei tule tulostaa mitään. Metodin tulee vain palauttaa merkkijono.", out, io.getSysOut());

        return toStringKellolta;
    }

    private Object luoKello() {
        Reflex.reflect("Sekuntikello").ctor().takingNoParams().requirePublic();
        try {
            return Reflex.reflect("Sekuntikello").ctor().takingNoParams().invoke();
        } catch (Throwable ex) {
            fail("Virhe ohjelmaa testatessa. Kokeile:\nSekuntikello kello = new Sekuntikello();\nVirhe: " + ex.getMessage());
        }
        return null;
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
        callMain(Ohjelma.class);
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
