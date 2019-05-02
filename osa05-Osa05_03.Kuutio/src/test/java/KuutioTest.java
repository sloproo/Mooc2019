
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import org.junit.*;
import static org.junit.Assert.*;

@Points("05-03")
public class KuutioTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void luokkaJaKonstruktori() {
        luoKuutio(5);
    }

    @Test
    public void tilavuusMetodi() {
        Object k = luoKuutio(5);

        assertEquals("Kun kuution särmän pituus on 5, sen tilavuuden pitäisi olla 125. Kokeile:\n"
                + "Kuutio k = new Kuutio(5);\n"
                + "System.out.println(k.tilavuus());", 125, kutsuTilavuus(k, 5));
    }

    @Test
    public void tilavuusMetodi2() {
        Object k = luoKuutio(7);

        assertEquals("Kun kuution särmän pituus on 7, sen tilavuuden pitäisi olla 343. Kokeile:\n"
                + "Kuutio k = new Kuutio(7);\n"
                + "System.out.println(k.tilavuus());", 343, kutsuTilavuus(k, 7));
    }

    @Test
    public void toStringMetodi() {
        Object k = luoKuutio(7);
        String out = io.getSysOut();
        String toStringPalautus = k.toString();
        assertTrue("Metodin toString ei tule tulostaa mitään. Kokeile:\n"
                + "Kuutio k = new Kuutio(7);\n"
                + "k.toString();\n"
                + "Ohjelman ei pitäisi tulostaa mitään.", out.equals(io.getSysOut()));

        assertTrue("Kokeile:\n"
                + "Kuutio k = new Kuutio(7);\n"
                + "System.out.println(k.toString());\n"
                + "Ohjelman tulostuksen pitäisi olla:\n"
                + "\"Kuution särmän pituus on 7, tilavuus on 343\"", toStringPalautus.contains("uutio") && toStringPalautus.contains("7") && toStringPalautus.contains("343"));
    }

    @Test
    public void toStringMetodi2() {
        Object k = luoKuutio(3);
        String out = io.getSysOut();
        String toStringPalautus = k.toString();
        assertTrue("Metodin toString ei tule tulostaa mitään. Kokeile:\n"
                + "Kuutio k = new Kuutio(3);\n"
                + "k.toString();\n"
                + "Ohjelman ei pitäisi tulostaa mitään.", out.equals(io.getSysOut()));

        assertTrue("Kokeile:\n"
                + "Kuutio k = new Kuutio(3);\n"
                + "System.out.println(k.toString());\n"
                + "Ohjelman tulostuksen pitäisi olla:\n"
                + "\"Kuution särmän pituus on 3, tilavuus on 27\"", toStringPalautus.contains("uutio") && toStringPalautus.contains("3") && toStringPalautus.contains("27"));
        
        assertFalse("Kokeile:\n"
                + "Kuutio k = new Kuutio(3);\n"
                + "System.out.println(k.toString());\n"
                + "Ohjelman tulostuksen pitäisi olla:\n"
                + "\"Kuution särmän pituus on 3, tilavuus on 27\"", toStringPalautus.contains("34") || toStringPalautus.contains("43"));
    }

    private Object luoKuutio(int sarmanPituus) {
        Reflex.reflect("Kuutio").ctor().taking(int.class).requirePublic();
        try {
            return Reflex.reflect("Kuutio").ctor().taking(int.class).invoke(sarmanPituus);
        } catch (Throwable ex) {
            fail("Virhe Kuution luomisessa. Kokeile:\nKuutio kuutio = new Kuutio(" + sarmanPituus + ");");
        }

        return null;
    }

    private int kutsuTilavuus(Object kuutio, int sarmanPituus) {
        Reflex.reflect("Kuutio").method("tilavuus").returning(int.class).takingNoParams().requirePublic();
        try {
            return Reflex.reflect("Kuutio").method("tilavuus").returning(int.class).takingNoParams().invokeOn(kuutio);
        } catch (Throwable ex) {
            fail("Virhe Kuution tilavuus-metodin kutsumisessa. Kokeile:\nKuutio kuutio = new Kuutio(" + sarmanPituus + ");\nSystem.out.println(kuutio.tilavuus());");
        }

        return -1;
    }
}
