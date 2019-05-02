


import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import org.junit.*;
import static org.junit.Assert.*;

@Points("09-05")
public class LuettujenArvojenTulostaminenTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void ekaTest() {
        String syote = "yks\nkaks\n\n";
        io.setSysIn(syote);
        LuettujenArvojenTulostaminen.main(new String[0]);

        assertTrue("Syöte oli:\n" + syote + ", odotettiin että tulostus olisi:\n" + syote + "\nNyt tulostus oli:\n" + syote, io.getSysOut().contains("yks"));
        assertTrue("Syöte oli:\n" + syote + ", odotettiin että tulostus olisi:\n" + syote + "\nNyt tulostus oli:\n" + syote, io.getSysOut().contains("kaks"));
    }

    @Test
    public void tokaTest() {
        String syote = "13\n22\n3242\n\n";
        io.setSysIn(syote);
        LuettujenArvojenTulostaminen.main(new String[0]);

        assertTrue("Syöte oli:\n" + syote + ", odotettiin että tulostus olisi:\n" + syote + "\nNyt tulostus oli:\n" + syote, io.getSysOut().contains("13"));
        assertTrue("Syöte oli:\n" + syote + ", odotettiin että tulostus olisi:\n" + syote + "\nNyt tulostus oli:\n" + syote, io.getSysOut().contains("22"));
        assertTrue("Syöte oli:\n" + syote + ", odotettiin että tulostus olisi:\n" + syote + "\nNyt tulostus oli:\n" + syote, io.getSysOut().contains("3242"));
    }

}
