
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Random;
import org.junit.*;
import static org.junit.Assert.*;

public class KasvatuslaitosTest {

    Kasvatuslaitos laitos;
    Henkilo pekka;
    Random arpa = new Random();

    Reflex.ClassRef<Object> klass;
    String klassName = "Kasvatuslaitos";

    @Before
    public void haeLuokka() {
        klass = Reflex.reflect(klassName);
    }

    @Before
    public void setUp() {
        laitos = new Kasvatuslaitos();
        pekka = new Henkilo("Pekka", 33, 175, 78);
    }

    @Points("05-09.1")
    @Test
    public void eiYlimaaraisiaMuuttujia1() {
        saniteettitarkastus();
    }

    @Test
    @Points("05-09.1")
    public void osataanPunnita() {
        assertEquals("tarkasta koodi: laitos = Kasvatuslaitos(); "
                + "h = newHenkilo(\"Pekka\", 33, 175, 78); "
                + "System.out.println( laitos.punnitse(h) )", pekka.getPaino(), laitos.punnitse(pekka));

        for (int i = 0; i < 5; i++) {
            int paino = 60 + arpa.nextInt(60);
            Henkilo mikko = new Henkilo("Mikko", 45, 181, paino);

            assertEquals("tarkasta koodi: laitos = Kasvatuslaitos(); "
                    + "h = newHenkilo(\"Mikko\", 45, 181, " + paino + "); "
                    + "System.out.println( laitos.punnitse(h) )", mikko.getPaino(), laitos.punnitse(mikko));
        }
    }

    @Points("05-09.2")
    @Test
    public void eiYlimaaraisiaMuuttujia2() {
        saniteettitarkastus();
    }

    @Points("05-09.2")
    @Test
    public void onkoMetodiSyota() throws Throwable {
        String metodi = "syota";

        Henkilo h = new Henkilo("Pekka", 20, 175, 85);
        Kasvatuslaitos k = new Kasvatuslaitos();

        assertTrue("tee luokalle " + klassName + " metodi public void " + metodi + "(Henkilo h) ",
                klass.method(k, metodi).returningVoid().taking(Henkilo.class).isPublic());

        String v = "\nVirheen aiheuttanut koodi "
                + "k = new Kasvatuslaitos; h = new Henkilo(\"Pekka\", 20, 175, 85); k.punnitse(h);";

        klass.method(k, metodi).returningVoid().taking(Henkilo.class).withNiceError(v).invoke(h);
    }

    @Test
    @Points("05-09.2")
    public void osataanSyottaa() {
        int odotettu = pekka.getPaino() + 1;
        syota(laitos, pekka);

        assertEquals("Syöttämisen pitäisi nostaa henkilön painoa kilolla. Tarkasta koodi: \n"
                + "laitos = Kasvatuslaitos(); "
                + "h = newHenkilo(\"Pekka\", 33, 175, 78); "
                + "laitos.syota(h); "
                + "System.out.println( h.getPaino() )",
                odotettu, pekka.getPaino());

        assertEquals("Syöttämisen pitäisi nostaa henkilön painoa kilolla. Tarkasta koodi: \n"
                + "laitos = Kasvatuslaitos(); "
                + "h = newHenkilo(\"Pekka\", 33, 175, 78); "
                + "laitos.syota(h); "
                + "System.out.println( laitos.punnitse(h) )",
                odotettu, laitos.punnitse(pekka));

        syota(laitos, pekka);
        syota(laitos, pekka);
        syota(laitos, pekka);
        syota(laitos, pekka);

        assertEquals("Syöttämisen pitäisi nostaa henkilön painoa kilolla. Tarkasta koodi: \n"
                + "laitos = Kasvatuslaitos(); "
                + "pekka = newHenkilo(\"Pekka\", 33, 175, 78); "
                + "laitos.syota(pekka); "
                + "laitos.syota(pekka); "
                + "laitos.syota(pekka); "
                + "laitos.syota(pekka); "
                + "laitos.syota(pekka); "
                + "System.out.println( pekka.getPaino() )",
                odotettu + 4, pekka.getPaino());
    }

    @Points("05-09.3")
    @Test
    public void eiYlimaaraisiaMuuttujia3() {
        saniteettitarkastus();
    }

    @Test
    @Points("05-09.3")
    public void onMetodiPunnitukset() throws Throwable {
        String metodi = "punnitukset";

        Kasvatuslaitos k = new Kasvatuslaitos();

        assertTrue("tee luokalle " + klassName + " metodi public int " + metodi + "() ",
                klass.method(k, metodi).returning(int.class).takingNoParams().isPublic());

        String v = "\nVirheen aiheuttanut koodi "
                + "k = new Kasvatuslaitos; k.punnitukset();";

        klass.method(k, metodi).returning(int.class).takingNoParams().withNiceError(v).invoke();
    }

    @Test
    @Points("05-09.3")
    public void tehtyjenPunnitustenMaaraMuistissa() {
        assertEquals("Toimiikohan metodi punnitukset()? Aluksi ei ole ketään punnittu! Testaa koodi"
                + "laitos = Kasvatuslaitos(); "
                + "System.out.println( laitos.punnitukset() )",
                0, punnitukset(laitos));

        laitos.punnitse(pekka);
        assertEquals("Toimiikohan metodi punnitukset()? Metodin pitäisi kertoa kunka monta kertaa metodia punnitse() on kutsuttu "
                + "Testaa koodi\n"
                + "laitos = Kasvatuslaitos(); "
                + "h1 = newHenkilo(\"Pekka\", 33, 175, 78); "
                + "laitos.punnitse(h1);"
                + "System.out.println( laitos.punnitukset() )",
                1, punnitukset(laitos));

        Henkilo mikko = new Henkilo("Mikko", 0, 52, 4);
        laitos.punnitse(mikko);
        assertEquals("Toimiikohan metodi punnitukset()? Metodin pitäisi kertoa kunka monta kertaa metodia punnitse() on kutsuttu "
                + "Testaa koodi\n"
                + "laitos = Kasvatuslaitos(); "
                + "h1 = newHenkilo(\"Pekka\", 33, 175, 78); "
                + "h2 = newHenkilo(\"Mikko\", 0, 52, 4); "
                + "laitos.punnitse(h1);"
                + "laitos.punnitse(h2);"
                + "System.out.println( laitos.punnitukset() )",
                2, punnitukset(laitos));

        laitos.punnitse(mikko);
        laitos.punnitse(pekka);
        laitos.punnitse(pekka);
        assertEquals("Toimiikohan metodi punnitukset()? Metodin pitäisi kertoa kunka monta kertaa metodia punnitse() on kutsuttu "
                + "Testaa koodi\n"
                + "laitos = Kasvatuslaitos(); "
                + "h1 = newHenkilo(\"Pekka\", 33, 175, 78); "
                + "h2 = newHenkilo(\"Mikko\", 0, 52, 4); "
                + "laitos.punnitse(h1);"
                + "laitos.punnitse(h2);"
                + "laitos.punnitse(h2);"
                + "laitos.punnitse(h1);"
                + "laitos.punnitse(h1);"
                + "System.out.println( laitos.punnitukset() )",
                5, punnitukset(laitos));
    }

    String luokanNimi = "Kasvatuslaitos";

    private void syota(Object laitos, Henkilo hlo) {
        try {
            Method syota = ReflectionUtils.requireMethod(Kasvatuslaitos.class, "syota", Henkilo.class);
            ReflectionUtils.invokeMethod(void.class, syota, laitos, hlo);
        } catch (Throwable t) {
        }
    }

    private int punnitukset(Object laitos) {
        try {
            Method punnitukset = ReflectionUtils.requireMethod(Kasvatuslaitos.class, "punnitukset");
            return ReflectionUtils.invokeMethod(int.class, punnitukset, laitos);
        } catch (Throwable t) {
        }
        return -1;
    }

    private void saniteettitarkastus() throws SecurityException {
        String luokanNimi = "Kasvatuslaitos";

        Field[] kentat = ReflectionUtils.findClass(luokanNimi).getDeclaredFields();

        for (Field field : kentat) {
            assertFalse("et tarvitse \"stattisia muuttujia\", poista " + kentta(field.toString()), field.toString().contains("static") && !field.toString().contains("final"));
            assertTrue("luokan kaikkien oliomuuttujien näkyvyyden tulee olla private, muuta " + kentta(field.toString()), field.toString().contains("private"));
        }

        if (kentat.length > 1) {
            int var = 0;
            for (Field field : kentat) {
                if (!field.toString().contains("final")) {
                    var++;
                }
            }
            assertTrue("et tarvitse " + luokanNimi + "-luokalle kuin punnitusten määrän muistavan oliomuuttujan! poista ylimääräiset", var < 2);
        }
    }

    private String kentta(String toString) {
        return toString.replace(luokanNimi + ".", "");
    }
}
