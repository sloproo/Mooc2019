
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Rule;
import org.junit.Test;

public class NestesailiotOlioillaTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    @Points("07-02.1")
    public void sailio1() {
        Reflex.reflect("Sailio").ctor().takingNoParams().requirePublic();
    }

    @Test
    @Points("07-02.1")
    public void sailio2() {
        Reflex.reflect("Sailio").method("lisaa").returningVoid().taking(int.class).requirePublic();
    }

    @Test
    @Points("07-02.1")
    public void sailio3() {
        Reflex.reflect("Sailio").method("poista").returningVoid().taking(int.class).requirePublic();
    }

    @Test
    @Points("07-02.1")
    public void sailio4() {
        Reflex.reflect("Sailio").method("sisalto").returning(int.class).takingNoParams().requirePublic();
    }

    @Test
    @Points("07-02.1")
    public void sailio5() throws Throwable {
        Object sailio = Reflex.reflect("Sailio").ctor().takingNoParams().invoke();
        String out = sailio.toString();
        assertEquals("Kokeile koodia:\nSailio s = new Sailio();\nSystem.out.println(s);\n", "0/100", out);

        Reflex.reflect("Sailio").method("lisaa").returningVoid().taking(int.class).invokeOn(sailio, 10);
        out = sailio.toString();
        assertEquals("Kokeile koodia:\nSailio s = new Sailio();\ns.lisaa(10);\nSystem.out.println(s);\n", "10/100", out);

        assertEquals("Kokeile koodia:\nSailio s = new Sailio();\ns.lisaa(10);\nSystem.out.println(s.sisalto());\n", Integer.valueOf(10), Reflex.reflect("Sailio").method("sisalto").returning(int.class).takingNoParams().invokeOn(sailio));

        Reflex.reflect("Sailio").method("lisaa").returningVoid().taking(int.class).invokeOn(sailio, 95);
        out = sailio.toString();
        assertEquals("Kokeile koodia:\nSailio s = new Sailio();\ns.lisaa(10);\ns.lisaa(95);\nSystem.out.println(s);\n", "100/100", out);

        Reflex.reflect("Sailio").method("lisaa").returningVoid().taking(int.class).invokeOn(sailio, -5);
        out = sailio.toString();
        assertEquals("Kokeile koodia:\nSailio s = new Sailio();\ns.lisaa(10);\ns.lisaa(95);\ns.lisaa(-5);\nSystem.out.println(s);\n", "100/100", out);
    }

    @Test
    @Points("07-02.1")
    public void sailio6() throws Throwable {
        Object sailio = Reflex.reflect("Sailio").ctor().takingNoParams().invoke();
        String out = sailio.toString();
        assertEquals("Kokeile koodia:\nSailio s = new Sailio();\nSystem.out.println(s);\n", "0/100", out);

        Reflex.reflect("Sailio").method("lisaa").returningVoid().taking(int.class).invokeOn(sailio, 10);
        out = sailio.toString();
        assertEquals("Kokeile koodia:\nSailio s = new Sailio();\ns.lisaa(10);\nSystem.out.println(s);\n", "10/100", out);

        Reflex.reflect("Sailio").method("poista").returningVoid().taking(int.class).invokeOn(sailio, 20);
        out = sailio.toString();
        assertEquals("Kokeile koodia:\nSailio s = new Sailio();\ns.lisaa(10);\ns.poista(20);\nSystem.out.println(s);\n", "0/100", out);
    }

    @Test
    @Points("07-02.2")
    public void tyhjat() {
        String komento = "lopeta\n";
        callMain(NestesailiotOlioilla.class, komento);

        assertTrue("Kun syöte on:\n" + komento + "\nTulostuksessa pitäisi olla rivi:\nEnsimmäinen: 0/100\nNyt tulostus oli:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("ns") && l.replaceAll("\\s+", "").contains("0/100")).count() == 1);
        assertTrue("Kun syöte on:\n" + komento + "\nTulostuksessa pitäisi olla rivi:\nToinen: 0/100\nNyt tulostus oli:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("oinen") && l.replaceAll("\\s+", "").contains("0/100")).count() == 1);
    }

    @Test
    @Points("07-02.2")
    public void lisaa1() {
        String komento = "lisaa 1\nlopeta\n";
        callMain(NestesailiotOlioilla.class, komento);

        assertTrue("Kun syöte on:\n" + komento + "\nTulostuksessa pitäisi olla rivi:\nEnsimmäinen: 0/100\nNyt tulostus oli:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("ns") && l.replaceAll("\\s+", "").contains("0/100")).count() == 1);
        assertTrue("Kun syöte on:\n" + komento + "\nTulostuksessa pitäisi olla rivi:\nEnsimmäinen: 1/100\nNyt tulostus oli:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("ns") && l.replaceAll("\\s+", "").contains("1/100")).count() == 1);
        assertTrue("Kun syöte on:\n" + komento + "\nTulostuksessa pitäisi olla rivi:\nToinen: 0/100\nNyt tulostus oli:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("oinen") && l.replaceAll("\\s+", "").contains("0/100")).count() > 0);
    }

    @Test
    @Points("07-02.2")
    public void lisaa2() {
        String komento = "lisaa 5\nlopeta\n";
        callMain(NestesailiotOlioilla.class, komento);

        assertFalse("Kun syöte on:\n" + komento + "\nTulostuksessa ei pitäisi olla riviä:\nEnsimmäinen: 1/100\nNyt tulostus oli:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("ns") && l.replaceAll("\\s+", "").contains("1/100")).count() == 1);
        assertTrue("Kun syöte on:\n" + komento + "\nTulostuksessa pitäisi olla rivi:\nEnsimmäinen: 5/100\nNyt tulostus oli:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("ns") && l.replaceAll("\\s+", "").contains("5/100")).count() == 1);
        assertTrue("Kun syöte on:\n" + komento + "\nTulostuksessa pitäisi olla rivi:\nToinen: 0/100\nNyt tulostus oli:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("oinen") && l.replaceAll("\\s+", "").contains("0/100")).count() > 0);
    }

    @Test
    @Points("07-02.2")
    public void lisaa3() {
        String komento = "lisaa 25\nlisaa 25\nlopeta\n";
        callMain(NestesailiotOlioilla.class, komento);

        assertFalse("Kun syöte on:\n" + komento + "\nTulostuksessa ei pitäisi olla riviä:\nEnsimmäinen: 1/100\nNyt tulostus oli:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("ns") && l.replaceAll("\\s+", "").contains("1/100")).count() == 1);
        assertTrue("Kun syöte on:\n" + komento + "\nTulostuksessa pitäisi olla rivi:\nEnsimmäinen: 25/100\nNyt tulostus oli:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("ns") && l.replaceAll("\\s+", "").contains("25/100")).count() == 1);
        assertTrue("Kun syöte on:\n" + komento + "\nTulostuksessa pitäisi olla rivi:\nEnsimmäinen: 50/100\nNyt tulostus oli:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("ns") && l.replaceAll("\\s+", "").contains("50/100")).count() == 1);
        assertTrue("Kun syöte on:\n" + komento + "\nTulostuksessa pitäisi olla rivi:\nToinen: 0/100\nNyt tulostus oli:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("oinen") && l.replaceAll("\\s+", "").contains("0/100")).count() > 0);
    }

    @Test
    @Points("07-02.2")
    public void lisaa4() {
        String komento = "lisaa 25\nlisaa -5\nlopeta\n";
        callMain(NestesailiotOlioilla.class, komento);

        assertFalse("Kun syöte on:\n" + komento + "\nTulostuksessa ei pitäisi olla riviä:\nEnsimmäinen: 1/100\nNyt tulostus oli:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("ns") && l.replaceAll("\\s+", "").contains("1/100")).count() == 1);
        assertTrue("Kun syöte on:\n" + komento + "\nTulostuksessa pitäisi olla rivi:\nEnsimmäinen: 25/100\nNyt tulostus oli:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("ns") && l.replaceAll("\\s+", "").contains("25/100")).count() > 0);
        assertFalse("Kun syöte on:\n" + komento + "\nTulostuksessa ei pitäisi olla riviä:\nEnsimmäinen: 20/100\nNyt tulostus oli:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("ns") && l.replaceAll("\\s+", "").contains("20/100")).count() == 1);
        assertTrue("Kun syöte on:\n" + komento + "\nTulostuksessa pitäisi olla rivi:\nToinen: 0/100\nNyt tulostus oli:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("oinen") && l.replaceAll("\\s+", "").contains("0/100")).count() > 0);
    }

    @Test
    @Points("07-02.2")
    public void lisaa5() {
        String komento = "lisaa 110\nlopeta\n";
        callMain(NestesailiotOlioilla.class, komento);

        assertTrue("Kun syöte on:\n" + komento + "\nTulostuksessa pitäisi olla rivi:\nEnsimmäinen: 100/100\nNyt tulostus oli:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("ns") && l.replaceAll("\\s+", "").contains("100/100")).count() == 1);
    }

    @Test
    @Points("07-02.2")
    public void lisaa6() {
        String komento = "lisaa 90\nlisaa 20\nlopeta\n";
        callMain(NestesailiotOlioilla.class, komento);

        assertTrue("Kun syöte on:\n" + komento + "\nTulostuksessa pitäisi olla rivi:\nEnsimmäinen: 90/100\nNyt tulostus oli:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("ns") && l.replaceAll("\\s+", "").contains("90/100")).count() == 1);
        assertTrue("Kun syöte on:\n" + komento + "\nTulostuksessa pitäisi olla rivi:\nEnsimmäinen: 100/100\nNyt tulostus oli:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("ns") && l.replaceAll("\\s+", "").contains("100/100")).count() == 1);
    }

    @Test
    @Points("07-02.2")
    public void siirra1() {
        String komento = "siirra 10\nlopeta\n";
        callMain(NestesailiotOlioilla.class, komento);

        assertTrue("Kun syöte on:\n" + komento + "\nTulostuksessa pitäisi olla rivi:\nEnsimmäinen: 0/100\nNyt tulostus oli:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("ns") && l.replaceAll("\\s+", "").contains("0/100")).count() > 0);
        assertTrue("Kun syöte on:\n" + komento + "\nTulostuksessa pitäisi olla rivi:\nToinen: 0/100\nNyt tulostus oli:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("oinen") && l.replaceAll("\\s+", "").contains("0/100")).count() > 0);
        assertFalse("Kun syöte on:\n" + komento + "\nTulostuksessa ei pitäisi olla riviä:\nToinen: 10/100\nNyt tulostus oli:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("oinen") && l.replaceAll("\\s+", "").contains("10/100")).count() > 0);
    }

    @Test
    @Points("07-02.2")
    public void siirra2() {
        String komento = "lisaa 10\nsiirra 10\nlopeta\n";
        callMain(NestesailiotOlioilla.class, komento);

        assertTrue("Kun syöte on:\n" + komento + "\nTulostuksessa pitäisi olla rivi:\nToinen: 10/100\nNyt tulostus oli:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("oinen") && l.replaceAll("\\s+", "").contains("10/100")).count() > 0);
    }

    @Test
    @Points("07-02.2")
    public void siirra3() {
        String komento = "lisaa 30\nsiirra 10\nlopeta\n";
        callMain(NestesailiotOlioilla.class, komento);

        assertTrue("Kun syöte on:\n" + komento + "\nTulostuksessa pitäisi olla rivi:\nEnsimmäinen: 30/100\nNyt tulostus oli:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("ns") && l.replaceAll("\\s+", "").contains("30/100")).count() > 0);
        assertTrue("Kun syöte on:\n" + komento + "\nTulostuksessa pitäisi olla rivi:\nEnsimmäinen: 20/100\nNyt tulostus oli:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("ns") && l.replaceAll("\\s+", "").contains("20/100")).count() > 0);
        assertTrue("Kun syöte on:\n" + komento + "\nTulostuksessa pitäisi olla rivi:\nToinen: 10/100\nNyt tulostus oli:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("oinen") && l.replaceAll("\\s+", "").contains("10/100")).count() > 0);
    }

    @Test
    @Points("07-02.2")
    public void siirra4() {
        String komento = "lisaa 30\nsiirra 40\nlopeta\n";
        callMain(NestesailiotOlioilla.class, komento);

        assertTrue("Kun syöte on:\n" + komento + "\nTulostuksessa pitäisi olla rivi:\nEnsimmäinen: 30/100\nNyt tulostus oli:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("ns") && l.replaceAll("\\s+", "").contains("30/100")).count() > 0);
        assertTrue("Kun syöte on:\n" + komento + "\nTulostuksessa pitäisi olla rivi:\nToinen: 30/100\nNyt tulostus oli:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("oinen") && l.replaceAll("\\s+", "").contains("30/100")).count() > 0);
        assertFalse("Kun syöte on:\n" + komento + "\nTulostuksessa ei pitäisi olla riviä:\nToinen: 40/100\nNyt tulostus oli:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("oinen") && l.replaceAll("\\s+", "").contains("40/100")).count() > 0);
    }

    @Test
    @Points("07-02.2")
    public void siirra5() {
        String komento = "lisaa 1000\nsiirra 90\nlisaa 100\nsiirra 90\nlopeta\n";
        callMain(NestesailiotOlioilla.class, komento);

        assertFalse("Kun syöte on:\n" + komento + "\nTulostuksessa ei pitäisi olla riviä:\nToinen: 180/100\nNyt tulostus oli:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("oinen") && l.replaceAll("\\s+", "").contains("180/100")).count() > 0);
        assertTrue("Kun syöte on:\n" + komento + "\nTulostuksessa pitäisi olla rivi:\nToinen: 90/100\nNyt tulostus oli:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("oinen") && l.replaceAll("\\s+", "").contains("90/100")).count() > 0);
        assertTrue("Kun syöte on:\n" + komento + "\nTulostuksessa pitäisi olla rivi:\nToinen: 100/100\nNyt tulostus oli:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("oinen") && l.replaceAll("\\s+", "").contains("100/100")).count() > 0);
    }

    @Test
    @Points("07-02.2")
    public void poista1() {
        String komento = "poista 10\nlopeta\n";
        callMain(NestesailiotOlioilla.class, komento);

        assertTrue("Kun syöte on:\n" + komento + "\nTulostuksessa pitäisi olla rivi:\nEnsimmäinen: 0/100\nNyt tulostus oli:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("ns") && l.replaceAll("\\s+", "").contains("0/100")).count() > 0);
        assertTrue("Kun syöte on:\n" + komento + "\nTulostuksessa pitäisi olla rivi:\nToinen: 0/100\nNyt tulostus oli:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("oinen") && l.replaceAll("\\s+", "").contains("0/100")).count() > 0);
        assertFalse("Kun syöte on:\n" + komento + "\nTulostuksessa ei pitäisi olla riviä:\nToinen: -10/100\nNyt tulostus oli:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("oinen") && l.replaceAll("\\s+", "").contains("-10/100")).count() > 0);
    }

    @Test
    @Points("07-02.2")
    public void poista2() {
        String komento = "lisaa 30\nsiirra 20\npoista 15\nlopeta\n";
        callMain(NestesailiotOlioilla.class, komento);

        assertTrue("Kun syöte on:\n" + komento + "\nTulostuksessa pitäisi olla rivi:\nEnsimmäinen: 0/100\nNyt tulostus oli:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("ns") && l.replaceAll("\\s+", "").contains("0/100")).count() > 0);
        assertTrue("Kun syöte on:\n" + komento + "\nTulostuksessa pitäisi olla rivi:\nToinen: 20/100\nNyt tulostus oli:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("oinen") && l.replaceAll("\\s+", "").contains("20/100")).count() > 0);
        assertTrue("Kun syöte on:\n" + komento + "\nTulostuksessa pitäisi olla rivi:\nToinen: 5/100\nNyt tulostus oli:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("oinen") && l.replaceAll("\\s+", "").contains("5/100")).count() > 0);
    }

    @Test
    @Points("07-02.2")
    public void poista3() {
        String komento = "lisaa 30\npoista 15\nlopeta\n";
        callMain(NestesailiotOlioilla.class, komento);

        assertFalse("Kun syöte on:\n" + komento + "\nTulostuksessa ei pitäisi olla riviä:\nEnsimmäinen: 15/100\nNyt tulostus oli:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("ns") && l.replaceAll("\\s+", "").contains("15/100")).count() > 0);
    }

    @Test
    @Points("07-02.2")
    public void poista4() {
        String komento = "lisaa 1000\nsiirra 90\npoista 33\nlisaa 1000\nsiirra 50\npoista 33\nlopeta\n";
        callMain(NestesailiotOlioilla.class, komento);

        assertTrue("Kun syöte on:\n" + komento + "\nTulostuksessa pitäisi olla rivi:\nToinen: 57/100\nNyt tulostus oli:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("oinen") && l.replaceAll("\\s+", "").contains("57/100")).count() > 0);
        assertTrue("Kun syöte on:\n" + komento + "\nTulostuksessa pitäisi olla rivi:\nToinen: 67/100\nNyt tulostus oli:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("oinen") && l.replaceAll("\\s+", "").contains("67/100")).count() > 0);

        for (int i = 10; i < 100; i++) {
            if (i == 57 || i == 67 || i == 90) {
                continue;
            }

            final int luku = i;
            assertFalse("Kun syöte on:\n" + komento + "\nTulostuksessa ei pitäisi olla riviä:\nToinen: " + i + "/100\nNyt tulostus oli:\n" + io.getSysOut(), lines().stream().filter(l -> l.contains("oinen") && l.replaceAll("\\s+", "").contains("" + luku + "/100")).count() > 0);
        }
    }

    private void callMain(Class kl, String syote) {
        io.setSysIn(syote);
        try {
            kl = ReflectionUtils.newInstanceOfClass(kl);
            String[] t = null;
            String x[] = new String[0];
            Method m = ReflectionUtils.requireMethod(kl, "main", x.getClass());
            ReflectionUtils.invokeMethod(Void.TYPE, m, null, (Object) x);
        } catch (NoSuchElementException e) {
            fail("Ohjelmassa yritettiin lukea enemmän syötettä kuin mitä ohjelmalle tarjottiin. Kokeile ohjelmaa syötteellä:\n" + syote);
            return;
        } catch (Throwable e) {
            fail("Jotain kummallista tapahtui. Saattaa olla että " + kl + "-luokan public static void main(String[] args) -metodi on hävinnyt\n"
                    + "tai ohjelmasi kaatui poikkeukseen. Lisätietoja " + e);
        }
    }

    private List<String> lines() {
        return Arrays.asList(io.getSysOut().split("\n"));
    }
}
