
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.*;
import static org.junit.Assert.*;

@Points("05-08")
public class HenkiloJaLemmikkiTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void testi() {
        Lemmikki hulda = new Lemmikki("Hulda", "sekarotuinen koira");
        Henkilo leevi = new Henkilo("Leevi", hulda);

        String out = io.getSysOut();
        String leevinToStringMetodinPalauttamaArvo = leevi.toString();
        assertTrue("Metodikutsun toString ei tule tulostaa merkkijonoa!", out.equals(io.getSysOut()));

        String virheviesti = "Metodin toString palauttamassa merkkijonossa tulee olla sekä henkilön nimi, että henkilön lemmikin tiedot.\n"
                + "Kokeile:\n"
                + "Lemmikki hulda = new Lemmikki(\"Hulda\", \"sekarotuinen koira\");\n"
                + "Henkilo leevi = new Henkilo(\"Leevi\", hulda);\n"
                + "System.out.println(leevi.toString());";
        assertTrue(virheviesti, leevinToStringMetodinPalauttamaArvo.contains("Leevi"));
        assertTrue(virheviesti, leevinToStringMetodinPalauttamaArvo.contains("Hulda"));
        assertTrue(virheviesti, leevinToStringMetodinPalauttamaArvo.contains("sekarotuinen koira"));
    }

    @Test
    public void testi2() {
        Lemmikki ariel = new Lemmikki("Ariel", "merenneito");
        Henkilo leena = new Henkilo("Leena", ariel);

        String out = io.getSysOut();
        String leenanToString = leena.toString();
        assertTrue("Metodikutsun toString ei tule tulostaa merkkijonoa!", out.equals(io.getSysOut()));

        String virheviesti = "Metodin toString palauttamassa merkkijonossa tulee olla sekä henkilön nimi, että henkilön lemmikin tiedot.\n"
                + "Kokeile:\n"
                + "Lemmikki ariel = new Lemmikki(\"Ariel\", \"merenneito\");\n"
                + "Henkilo leena = new Henkilo(\"Leena\", ariel);\n"
                + "System.out.println(leena.toString());";
        assertTrue(virheviesti, leenanToString.contains("Leena"));
        assertTrue(virheviesti, leenanToString.contains("Ariel"));
        assertTrue(virheviesti, leenanToString.contains("merenneito"));
    }

}
