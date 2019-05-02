
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

@Points("07-03.1")
public class A_TavaraTest {

    String klassName = "Tavara";
    Reflex.ClassRef<Object> klass;

    @Before
    public void setup() {
        klass = Reflex.reflect(klassName);
    }

    @Test
    public void luokkaJulkinen() {
        assertTrue("Luokan " + klassName + " pitää olla julkinen, eli se tulee määritellä\npublic class " + klassName + " {...\n}", klass.isPublic());
    }

    @Test
    public void eiYlimaaraisiaMuuttujia() {
        saniteettitarkastus(klassName, 2, "nimen ja painon tallettavat oliomuuttujat");
    }

    @Test
    public void testaaKonstruktori() throws Throwable {
        Reflex.MethodRef2<Object, Object, String, Integer> ctor = klass.constructor().taking(String.class, int.class).withNiceError();
        assertTrue("Määrittele luokalle " + klassName + " julkinen konstruktori: public " + klassName + "(String nimi, int paino)", ctor.isPublic());
        String v = "virheen aiheutti koodi new Tavara(\"Puhelin\", 1);";
        ctor.withNiceError(v).invoke("Puhelin", 1);
    }

    public Object luo(String nimi, int paino) throws Throwable {
        Reflex.MethodRef2<Object, Object, String, Integer> ctor = klass.constructor().taking(String.class, int.class).withNiceError();
        return ctor.invoke(nimi, paino);
    }

    @Test
    @Points("07-03.1")
    public void tavaraGetNimiMetodi() throws Throwable {
        String metodi = "getNimi";
        Reflex.ClassRef<Object> tuoteClass = Reflex.reflect(klassName);

        Object olio = luo("Kirja", 2);

        assertTrue("tee luokalle " + klassName + " metodi public String " + metodi + "() ", tuoteClass.method(olio, metodi)
                .returning(String.class).takingNoParams().isPublic());


        String v = "\nVirheen aiheuttanut koodi Tavara t = new Tavara(\"Kirja\", 2); "
                + "t.getNimi();";

        assertEquals("Tarkasta koodi Tavara t = new Tavara(\"Kirja\", 2); "
                + "t.getNimi();", "Kirja", tuoteClass.method(olio, metodi)
                .returning(String.class).takingNoParams().withNiceError(v).invoke());

        olio = luo("Auto", 800);


        v = "\nVirheen aiheuttanut koodi Tavara t = new Tavara(\"Auto\", 800); "
                + "t.getNimi();";

        assertEquals("Tarkasta koodi Tavara t = new Tavara(\"Auto\", 800);  "
                + "t.getNimi();", "Auto", tuoteClass.method(olio, metodi)
                .returning(String.class).takingNoParams().withNiceError(v).invoke());
    }

    @Test
    @Points("07-03.1")
    public void tavaraGetPainoMetodi() throws Throwable {
        String metodi = "getPaino";
        Reflex.ClassRef<Object> tuoteClass = Reflex.reflect(klassName);

        Object olio = luo("Kirja", 2);

        assertTrue("tee luokalle " + klassName + " metodi public int " + metodi + "() ", tuoteClass.method(olio, metodi)
                .returning(int.class).takingNoParams().isPublic());

        String v = "\nVirheen aiheuttanut koodi Tavara t = new Tavara(\"Kirja\", 2); "
                + "t.getPaino();";

        assertEquals("Tarkasta koodi Tavara t = new Tavara(\"Kirja\", 2); "
                + "t.getPaino();", 2, (int)tuoteClass.method(olio, metodi)
                .returning(int.class).takingNoParams().withNiceError(v).invoke());

        olio = luo("Auto", 800);

        v = "\nVirheen aiheuttanut koodi Tavara t = new Tavara(\"Auto\", 800); "
                + "t.getPaino();";

        assertEquals("Tarkasta koodi Tavara t = new Tavara(\"Auto\", 800);  "
                + "t.getPaino();", 800, (int)tuoteClass.method(olio, metodi)
                .returning(int.class).takingNoParams().withNiceError(v).invoke());
    }

    @Test
    @Points("07-03.1")
    public void tavaraToString() throws Throwable {
        Object olio = luo("Kirja", 2);

        assertFalse("Tee luokalle Tavara tehtävämäärittelyn mukainen toString",olio.toString().contains("@"));

        assertTrue("Tarkista että toString-metodi toimii kuten vaadittu. \n"
                + "Tavara t = new Tavara(\"Kirja\", 2); t.toString();  \n"+olio.toString(),sisaltaa(olio.toString(), "Kirja", "2", "kg"));
    }

    private boolean sisaltaa(String palautus, String... oletetutArvot) {
        for (String arvo : oletetutArvot) {
            if (!palautus.contains(arvo)) {
                return false;
            }
        }

        return true;
    }

    private void saniteettitarkastus(String klassName, int n, String m) throws SecurityException {
        Field[] kentat = ReflectionUtils.findClass(klassName).getDeclaredFields();

        for (Field field : kentat) {
            assertFalse("et tarvitse \"stattisia muuttujia\", poista luokalta " + klassName + " muuttuja " + kentta(field.toString(), klassName), field.toString().contains("static") && !field.toString().contains("final"));
            assertTrue("luokan kaikkien oliomuuttujien näkyvyyden tulee olla private, muuta luokalta " + klassName + " löytyi: " + kentta(field.toString(), klassName), field.toString().contains("private"));
        }

        if (kentat.length > 1) {
            int var = 0;
            for (Field field : kentat) {
                if (!field.toString().contains("final")) {
                    var++;
                }
            }
            assertTrue("et tarvitse " + klassName + "-luokalle kuin " + m + ", poista ylimääräiset", var <= n);
        }
    }

    private String kentta(String toString, String klassName) {
        return toString.replace(klassName + ".", "").replace("java.lang.", "").replace("java.util.", "");
    }
}
