
import fi.helsinki.cs.tmc.edutestutils.MockInOut;
import org.junit.Test;
import static org.junit.Assert.*;
import fi.helsinki.cs.tmc.edutestutils.Points;

import java.lang.reflect.Field;

public class TulosteluaLikeABossTest {

    public String sanitize(String s) {
        return s.replace("\r\n", "\n").replace("\r", "\n");
    }

    @Test
    @Points("02-33.1 02-33.2 02-33.3")
    public void testaaEtteiKenttia() {
        Field[] fs = TulosteluaLikeABoss.class.getDeclaredFields();
        if (fs.length != 0) {
            fail("Luokassa Tulostelua on kenttä nimeltään " + fs[0].getName() + " poista se!");
        }
    }

    @Test
    @Points("02-33.1")
    public void testaaTulostaTahtia() {
        MockInOut mio = new MockInOut("");
        TulosteluaLikeABoss.tulostaTahtia(3);
        String out = sanitize(mio.getOutput());
        assertTrue("Et tulostanut yhtään merkkiä \"*\" kun kutsuttiin tulostaTahtia.", out.contains("*"));
        assertTrue("Et tulostanut yhtään rivinvaihtoa kun kutsuttiin tulostaTahtia.", out.contains("\n"));
        assertEquals("Tulostit väärin kun kutsuttiin tulostaTahtia(3).", "***\n", out);
        mio.close();
    }

    @Test
    @Points("02-33.1")
    public void testaaTulostaTyhjaa1() {
        MockInOut mio = new MockInOut("");
        TulosteluaLikeABoss.tulostaTyhjaa(1);
        String out = sanitize(mio.getOutput());
        assertEquals("Metodikutsun tulostaTyhjaa(1) pitäisi tulostaa 1 merkki, nyt merkkejä tulostui", 1, out.length());
        assertFalse("Metodinkutsun tulostaTyhjaa(1) ei pitäisi tulostaa rivinvaihtoa, nyt kuitenkin niin tapahtuu", out.contains("\n"));
        assertEquals("Tarkasta metodikutsu tulostaTyhjaa(1)", " ", out);
        mio.close();
    }

    @Test
    @Points("02-33.1")
    public void testaaTulostaTyhjaa2() {
        MockInOut mio = new MockInOut("");
        TulosteluaLikeABoss.tulostaTyhjaa(3);
        String out = sanitize(mio.getOutput());
        assertEquals("Metodikutsun tulostaTyhjaa(3) pitäisi tulostaa 3 merkkiä, nyt merkkejä tulostulostui", 3, out.length());
        assertFalse("Metodinkutsun tulostaTyhjaa(3) ei pitäisi tulostaa rivinvaihtoa, nyt kuitenkin niin tapahtuu", out.contains("\n"));
        assertEquals("Tarkasta metodikutsu tulostaTyhjaa(3)", "   ", out);
        mio.close();
    }

    @Test
    @Points("02-33.2")
    public void testaaTulostaPieniKolmio() {
        MockInOut mio = new MockInOut("");
        TulosteluaLikeABoss.tulostaKolmio(1);
        String out = sanitize(mio.getOutput());

        int riveja = out.split("\n").length;

        assertEquals("Kun kutsutiin tulostaKolmio(1), tulostettiin väärä määrä rivejä", 1, riveja);

        assertFalse("Et kai laita rivien alkuun ylimäräistä välilyöntiä?", out.startsWith(" "));
        assertEquals("Tulostit väärin kun kutsuttiin tulostaKolmio(1).", "*\n", out);
        mio.close();
    }

    @Test
    @Points("02-33.2")
    public void testaaTulostaKolmio() {
        MockInOut mio = new MockInOut("");
        TulosteluaLikeABoss.tulostaKolmio(3);
        String out = sanitize(mio.getOutput());
        assertEquals("Tulostit väärin kun kutsuttiin tulostaKolmio(3).", "  *\n **\n***\n", out);
        mio.close();
    }

    @Test
    @Points("02-33.3")
    public void testaaJouluKuusi0() {
        MockInOut mio = new MockInOut("");
        TulosteluaLikeABoss.jouluKuusi(4);
        String out = sanitize(mio.getOutput());
        assertTrue("kutsuttaessa jouluKuusi(4), pitäisi ensimmäisellä rivillä olla alussa 3 tyhjää ja sen jälkeen tähti, tarkasta ettei tyhjää ole liikaa tai liian vähän",
                out.startsWith("   *"));

        mio.close();
    }

    @Test
    @Points("02-33.3")
    public void testaaJouluKuusi1() {
        MockInOut mio = new MockInOut("");
        TulosteluaLikeABoss.jouluKuusi(4);
        String out = sanitize(mio.getOutput());
        assertEquals("Tulostit väärin kun kutsuttiin jouluKuusi(4).",
                "   *\n  ***\n *****\n*******\n  ***\n  ***\n", out);

        mio.close();
    }

    @Test
    @Points("02-33.3")
    public void testaaJouluKuusi2() {
        MockInOut mio = new MockInOut("");
        TulosteluaLikeABoss.jouluKuusi(7);
        String out = sanitize(mio.getOutput());
        assertEquals("Tulostit väärin kun kutsuttiin jouluKuusi(7).",
                "      *\n"
                + "     ***\n"
                + "    *****\n"
                + "   *******\n"
                + "  *********\n"
                + " ***********\n"
                + "*************\n"
                + "     ***\n"
                + "     ***\n",
                out);

        mio.close();
    }
}
