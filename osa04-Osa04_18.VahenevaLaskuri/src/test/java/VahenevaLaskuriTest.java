
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.junit.*;
import static org.junit.Assert.*;

public class VahenevaLaskuriTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Points("04-18.1")
    @Test
    public void arvoVahenee() {
        VahenevaLaskuri laskuri = new VahenevaLaskuri(10);
        laskuri.tulostaArvo();
        String out = io.getSysOut();
        assertTrue("luodun laskurin arvo ei tulostu oikein, etkai muuttanut metodin tulostaArvo() koodia!", out.contains("arvo: 10"));
        int vanhaTuloste = out.length();
        laskuri.vahene();
        laskuri.tulostaArvo();
        out = io.getSysOut().substring(vanhaTuloste);
        assertTrue("kutsuttaessa metodia vahenna() pitäisi laskurin arvon tulostua yhtä pienempänä. \n"
                + "Tarkasta koodi "
                + "laskuri = new VahenevaLaskuri(10); laskuri.vahene(); laskuri.tulostaArvo();", out.contains("arvo: 9"));
        vanhaTuloste = out.length();
        laskuri.vahene();
        laskuri.tulostaArvo();
        out = io.getSysOut().substring(vanhaTuloste);
        assertTrue("kutsuttaessa metodia vahenna() toista kertaa, pitäisi laskurin arvon tulostua kahta pienempänä. \n"
                + "Tarkasta koodi "
                + "laskuri = new VahenevaLaskuri(10); laskuri.vahene(); laskuri.vahene(); laskuri.tulostaArvo();", out.contains("arvo: 8"));
    }

    @Points("04-18.2")
    @Test
    public void arvoEiVaheneAlleNollan() {
        VahenevaLaskuri laskuri = new VahenevaLaskuri(2);
        laskuri.vahene();
        laskuri.vahene();
        laskuri.tulostaArvo();
        String out = io.getSysOut();
        assertTrue("Laskurin koodin pitäisi vähetä yhdellä per metodikutsu. \nTarkasta koodi "
                + "laskuri = new VahenevaLaskuri(2); laskuri.vahene(); laskuri.vahene(); laskuri.tulostaArvo();", out.contains("arvo: 0"));

        int vanhaTuloste = out.length();
        laskuri.vahene();
        laskuri.tulostaArvo();
        out = io.getSysOut().substring(vanhaTuloste);

        assertTrue("Laskurin arvon ei pitäisi vähetä alle nollan. \nTarkasta koodi "
                + "laskuri = new VahenevaLaskuri(2); laskuri.vahene(); laskuri.vahene(); laskuri.vahene(); laskuri.tulostaArvo();", out.contains("arvo: 0"));


        vanhaTuloste = out.length();
        laskuri.vahene();
        laskuri.tulostaArvo();
        out = io.getSysOut().substring(vanhaTuloste);

        assertTrue("Laskurin arvon ei pitäisi vähetä alle nollan. \nTarkasta koodi "
                + "laskuri = new VahenevaLaskuri(2); laskuri.vahene();  laskuri.vahene(); laskuri.vahene(); laskuri.vahene(); laskuri.tulostaArvo();", out.contains("arvo: 0"));
    }

    @Points("04-18.3")
    @Test
    public void onkoMetodiaNollaa() throws Throwable {
        String klassName = "VahenevaLaskuri";

        String metodi = "nollaa";

        Reflex.ClassRef<Object> tuoteClass = Reflex.reflect(klassName);
        Object olio = tuoteClass.constructor().taking(int.class).invoke(4);

        assertTrue("tee luokalle " + klassName + " metodi public void " + metodi + "() ", tuoteClass.method(olio, metodi)
                .returningVoid().takingNoParams().isPublic());

        String v = "\nVirheen aiheuttanut koodi: laskuri = new VahenevaLaskuri(2); laskuri.nollaa();";

        tuoteClass.method(olio, metodi)
                .returningVoid().takingNoParams().withNiceError(v).invoke();
    }
    
    @Points("04-18.3")
    @Test
    public void metodiNollaa() throws Exception {
        String metodinNimi = "nollaa";

        VahenevaLaskuri laskuri = new VahenevaLaskuri(2);
        Method metodi = ReflectionUtils.requireMethod(VahenevaLaskuri.class, metodinNimi);
        metodi.invoke(laskuri);

        laskuri.tulostaArvo();
        String out = io.getSysOut();
        assertTrue("Metodin nollaa() pitäisi nollata laskurin arvo. \nTarkasta koodi "
                + "laskuri = new VahenevaLaskuri(2); laskuri.nollaa(); laskuri.tulostaArvo();", out.contains("arvo: 0"));

        int vanhaTuloste = out.length();
        laskuri.vahene();
        laskuri.tulostaArvo();
        out = io.getSysOut().substring(vanhaTuloste);

        assertTrue("Laskurin arvon ei pitäisi vähetä alle nollan. \nTarkasta koodi "
                + "laskuri = new VahenevaLaskuri(2); laskuri.nollaa(); laskuri.vahenna(); laskuri.tulostaArvo();", out.contains("arvo: 0"));
    }

    @Points("04-18.4")
    @Test
    public void onkoMetodiaPalautaAlkuarvo() throws Throwable {
        String klassName = "VahenevaLaskuri";

        String metodi = "palautaAlkuarvo";

        Reflex.ClassRef<Object> tuoteClass = Reflex.reflect(klassName);
        Object olio = tuoteClass.constructor().taking(int.class).invoke(4);

        assertTrue("tee luokalle " + klassName + " metodi public void " + metodi + "() ", tuoteClass.method(olio, metodi)
                .returningVoid().takingNoParams().isPublic());

        String v = "\nVirheen aiheuttanut koodi: laskuri = new VahenevaLaskuri(4);  laskuri.palautaAlkuarvo();";

        tuoteClass.method(olio, metodi)
                .returningVoid().takingNoParams().withNiceError(v).invoke();
    }    
    

    @Points("04-18.4")
    @Test
    public void metodiPalautaAlkuarvo() throws Exception {
        VahenevaLaskuri laskuri = new VahenevaLaskuri(2);

        String metodinNimi = "nollaa";
        Method metodi = ReflectionUtils.requireMethod(VahenevaLaskuri.class, metodinNimi);
        metodi.invoke(laskuri);

        metodinNimi = "palautaAlkuarvo";
        metodi = ReflectionUtils.requireMethod(VahenevaLaskuri.class, metodinNimi);
        metodi.invoke(laskuri);

        laskuri.tulostaArvo();
        String out = io.getSysOut();
        assertTrue("Metodin palautaAlkuarvo() pitäisi palauttaa laskurille luonnissa annettu arvo.\nTarkasta koodi "
                + "laskuri = new VahenevaLaskuri(2); laskuri.nollaa(); laskuri.palautaAlkuarvo(); laskuri.tulostaArvo();", out.contains("arvo: 2"));

        int vanhaTuloste = out.length();
        laskuri.vahene();

        metodinNimi = "palautaAlkuarvo";
        metodi = ReflectionUtils.requireMethod(VahenevaLaskuri.class, metodinNimi);
        metodi.invoke(laskuri);

        laskuri.tulostaArvo();
        out = io.getSysOut().substring(vanhaTuloste);
        assertTrue("Metodin palautaAlkuarvo() pitäisi palauttaa laskurille luonnissa annettu arvo. \nTarkasta koodi "
                + "laskuri = new VahenevaLaskuri(2); laskuri.nollaa(); laskuri.palautaAlkuarvo(); laskuri.vahenna(); laskuri.palautaAlkuarvo(); laskuri.tulostaArvo();", out.contains("arvo: 2"));

    }

    @Points("04-18.1 04-18.2 04-18.3 04-18.4")
    @Test
    public void eiStaticeja() {
        Class luokka = VahenevaLaskuri.class;
        String luokanNimi = luokka.toString().replace("class ", "");

        for (Field f : luokka.getDeclaredFields()) {
            assertFalse("olet lisännyt luokkalle " + luokanNimi + " luokkamuuttujan (static) " + f.toString().replace(luokanNimi + ".", "") + " et tarvitse sitä, poista se! Luokkamuuttujien käyttö ei ole tässä tehtävässä tarpeen", f.toString().contains("static"));
        }
    }
}
