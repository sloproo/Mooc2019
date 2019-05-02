
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import fi.helsinki.cs.tmc.edutestutils.Reflex.ClassRef;
import java.lang.reflect.Field;
import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;

public class KeskiarvotTest {

    @Rule
    public MockStdio io = new MockStdio();
    
    @Test
    @Points("06-13.1")
    public void onMetodiPisteidenKeskiarvo() {
        Reflex.reflect(Arvosanarekisteri.class).method("koepisteidenKeskiarvo").returning(double.class).takingNoParams().requirePublic();
    }

    @Test
    @Points("06-13.1")
    public void koepisteidenKeskiarvo1() throws Throwable {
        Arvosanarekisteri rekisteri = new Arvosanarekisteri();
        rekisteri.lisaaArvosanaPisteidenPerusteella(91);
        rekisteri.lisaaArvosanaPisteidenPerusteella(92);
        rekisteri.lisaaArvosanaPisteidenPerusteella(93);

        String v = "Virheen aiheutti:\n"
                + "Arvosanarekisteri rekisteri = new Arvosanarekisteri();\n"
                + "rekisteri.lisaaArvosanaPisteidenPerusteella(91);\n"
                + "rekisteri.lisaaArvosanaPisteidenPerusteella(92);\n"
                + "rekisteri.lisaaArvosanaPisteidenPerusteella(93);\n"
                + "rekisteri.koepisteidenKeskiarvo();";

        double ka = Reflex.reflect(Arvosanarekisteri.class).method("koepisteidenKeskiarvo").returning(double.class).takingNoParams().withNiceError(v).invokeOn(rekisteri);
        assertEquals("Keskiarvoa ei laskettu oikein. " + v, 92.0, ka, 0.0001);
    }

    @Test
    @Points("06-13.1")
    public void koepisteidenKeskiarvo2() throws Throwable {
        Arvosanarekisteri rekisteri = new Arvosanarekisteri();
        rekisteri.lisaaArvosanaPisteidenPerusteella(3);
        rekisteri.lisaaArvosanaPisteidenPerusteella(3);
        rekisteri.lisaaArvosanaPisteidenPerusteella(4);

        String v = "Virheen aiheutti:\n"
                + "Arvosanarekisteri rekisteri = new Arvosanarekisteri();\n"
                + "rekisteri.lisaaArvosanaPisteidenPerusteella(3);\n"
                + "rekisteri.lisaaArvosanaPisteidenPerusteella(3);\n"
                + "rekisteri.lisaaArvosanaPisteidenPerusteella(4);\n"
                + "rekisteri.koepisteidenKeskiarvo();";

        double ka = Reflex.reflect(Arvosanarekisteri.class).method("koepisteidenKeskiarvo").returning(double.class).takingNoParams().withNiceError(v).invokeOn(rekisteri);
        assertEquals("Keskiarvoa ei laskettu oikein. " + v, 3.333333333, ka, 0.001);

    }

    @Test
    @Points("06-13.1")
    public void koepisteidenKeskiarvo3() throws Throwable {
        Arvosanarekisteri rekisteri = new Arvosanarekisteri();
        rekisteri.lisaaArvosanaPisteidenPerusteella(3);
        rekisteri.lisaaArvosanaPisteidenPerusteella(3);
        rekisteri.lisaaArvosanaPisteidenPerusteella(3);
        rekisteri.lisaaArvosanaPisteidenPerusteella(4);
        rekisteri.lisaaArvosanaPisteidenPerusteella(4);
        rekisteri.lisaaArvosanaPisteidenPerusteella(4);
        rekisteri.lisaaArvosanaPisteidenPerusteella(3);

        String v = "Virheen aiheutti:\n"
                + "Arvosanarekisteri rekisteri = new Arvosanarekisteri();\n"
                + "rekisteri.lisaaArvosanaPisteidenPerusteella(3);\n"
                + "rekisteri.lisaaArvosanaPisteidenPerusteella(3);\n"
                + "rekisteri.lisaaArvosanaPisteidenPerusteella(3);\n"
                + "rekisteri.lisaaArvosanaPisteidenPerusteella(4);\n"
                + "rekisteri.lisaaArvosanaPisteidenPerusteella(4);\n"
                + "rekisteri.lisaaArvosanaPisteidenPerusteella(4);\n"
                + "rekisteri.lisaaArvosanaPisteidenPerusteella(3);\n"
                + "rekisteri.koepisteidenKeskiarvo();";

        double ka = Reflex.reflect(Arvosanarekisteri.class).method("koepisteidenKeskiarvo").returning(double.class).takingNoParams().withNiceError(v).invokeOn(rekisteri);
        assertEquals("Keskiarvoa ei laskettu oikein. " + v, 3.4285714, ka, 0.001);

    }

    @Test
    @Points("06-13.2")
    public void onMetodiArvosanojenKeskiarvo() {
        Reflex.reflect(Arvosanarekisteri.class).method("arvosanojenKeskiarvo").returning(double.class).takingNoParams().requirePublic();
    }

    @Test
    @Points("06-13.2")
    public void arvosanojenKeskiarvo1() throws Throwable {
        Arvosanarekisteri rekisteri = new Arvosanarekisteri();
        rekisteri.lisaaArvosanaPisteidenPerusteella(91);
        rekisteri.lisaaArvosanaPisteidenPerusteella(92);
        rekisteri.lisaaArvosanaPisteidenPerusteella(93);

        String v = "Virheen aiheutti:\n"
                + "Arvosanarekisteri rekisteri = new Arvosanarekisteri();\n"
                + "rekisteri.lisaaArvosanaPisteidenPerusteella(91);\n"
                + "rekisteri.lisaaArvosanaPisteidenPerusteella(92);\n"
                + "rekisteri.lisaaArvosanaPisteidenPerusteella(93);\n"
                + "rekisteri.arvosanojenKeskiarvo();";

        double ka = Reflex.reflect(Arvosanarekisteri.class).method("arvosanojenKeskiarvo").returning(double.class).takingNoParams().withNiceError(v).invokeOn(rekisteri);
        assertEquals("Keskiarvoa ei laskettu oikein. " + v, 5.0, ka, 0.001);
    }

    @Test
    @Points("06-13.2")
    public void arvosanojenKeskiarvo2() throws Throwable {
        Arvosanarekisteri rekisteri = new Arvosanarekisteri();
        rekisteri.lisaaArvosanaPisteidenPerusteella(91);
        rekisteri.lisaaArvosanaPisteidenPerusteella(92);
        rekisteri.lisaaArvosanaPisteidenPerusteella(93);
        rekisteri.lisaaArvosanaPisteidenPerusteella(88);

        String v = "Virheen aiheutti:\n"
                + "Arvosanarekisteri rekisteri = new Arvosanarekisteri();\n"
                + "rekisteri.lisaaArvosanaPisteidenPerusteella(91);\n"
                + "rekisteri.lisaaArvosanaPisteidenPerusteella(92);\n"
                + "rekisteri.lisaaArvosanaPisteidenPerusteella(93);\n"
                + "rekisteri.lisaaArvosanaPisteidenPerusteella(88);\n"
                + "rekisteri.arvosanojenKeskiarvo();";

        double ka = Reflex.reflect(Arvosanarekisteri.class).method("arvosanojenKeskiarvo").returning(double.class).takingNoParams().withNiceError(v).invokeOn(rekisteri);
        assertEquals("Keskiarvoa ei laskettu oikein. " + v, 4.75, ka, 0.001);
    }

    @Test
    @Points("06-13.2")
    public void arvosanojenKeskiarvo3() throws Throwable {
        Arvosanarekisteri rekisteri = new Arvosanarekisteri();
        rekisteri.lisaaArvosanaPisteidenPerusteella(91);
        rekisteri.lisaaArvosanaPisteidenPerusteella(92);
        rekisteri.lisaaArvosanaPisteidenPerusteella(93);
        rekisteri.lisaaArvosanaPisteidenPerusteella(88);
        rekisteri.lisaaArvosanaPisteidenPerusteella(61);
        rekisteri.lisaaArvosanaPisteidenPerusteella(59);
        rekisteri.lisaaArvosanaPisteidenPerusteella(13);
        rekisteri.lisaaArvosanaPisteidenPerusteella(14);
        
        String v = "Virheen aiheutti:\n"
                + "Arvosanarekisteri rekisteri = new Arvosanarekisteri();\n"
                + "rekisteri.lisaaArvosanaPisteidenPerusteella(91);\n"
                + "rekisteri.lisaaArvosanaPisteidenPerusteella(92);\n"
                + "rekisteri.lisaaArvosanaPisteidenPerusteella(93);\n"
                + "rekisteri.lisaaArvosanaPisteidenPerusteella(88);\n"
                + "rekisteri.lisaaArvosanaPisteidenPerusteella(61);\n"
                + "rekisteri.lisaaArvosanaPisteidenPerusteella(59);\n"
                + "rekisteri.lisaaArvosanaPisteidenPerusteella(13);\n"
                + "rekisteri.lisaaArvosanaPisteidenPerusteella(14);\n"
                + "rekisteri.arvosanojenKeskiarvo();";

        double ka = Reflex.reflect(Arvosanarekisteri.class).method("arvosanojenKeskiarvo").returning(double.class).takingNoParams().withNiceError(v).invokeOn(rekisteri);
        assertEquals("Keskiarvoa ei laskettu oikein. " + v, 2.75, ka, 0.001);
    }
    
    @Test
    @Points("06-13.3")
    public void tulostuksetKayttoliittymassa1() throws Throwable {
        String in = "82\n83\n96\n51\n48\n56\n61\n\n";
        Scanner syote = new Scanner(in);
        
        Arvosanarekisteri rekisteri = new Arvosanarekisteri();
        
        Kayttoliittyma kali = new Kayttoliittyma(rekisteri, syote);
        kali.kaynnista();
        
        assertTrue("Odotettiin, että ohjelman tulostuksessa on koepisteiden keskiarvo. Syotteen:\n" + in + "Pitäisi antaa koepisteiden keskiarvoksi 68.1428..", io.getSysOut().contains("68.1"));
        assertTrue("Odotettiin, että ohjelman tulostuksessa on arvosanojen keskiarvo. Syotteen:\n" + in + "Pitäisi antaa koepisteiden keskiarvoksi 2.4285..",io.getSysOut().contains("2.4"));
    }
    
    @Test
    @Points("06-13.3")
    public void tulostuksetKayttoliittymassa2() throws Throwable {
        String in = "16\n8\n-3\n62\n99\n101\n64\n\n";
        Scanner syote = new Scanner(in);
        
        Arvosanarekisteri rekisteri = new Arvosanarekisteri();
        
        Kayttoliittyma kali = new Kayttoliittyma(rekisteri, syote);
        kali.kaynnista();
        
        assertTrue("Odotettiin, että ohjelman tulostuksessa on koepisteiden keskiarvo. Syotteen:\n" + in + "Pitäisi antaa koepisteiden keskiarvoksi 49.8", io.getSysOut().contains("49."));
        assertTrue("Odotettiin, että ohjelman tulostuksessa on arvosanojen keskiarvo. Syotteen:\n" + in + "Pitäisi antaa koepisteiden keskiarvoksi 1.8",io.getSysOut().contains("1."));
    }
}
