
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;

@Points("04-28.1 04-28.2")
public class KirjatTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void testaaSyoteEka() {
        String syote = "KonMari\n222\n2011\nBeautiful Code\n593\n2007\n";
        String tulostus = "nimi";

        io.setSysIn(syote + "\n" + tulostus + "\n");
        Main.main(new String[]{});

        assertFalse("Kun syöte on\n" + syote + "\n ja tulostustoive on " + tulostus + ", odotetaan tulostusta:\nKonMari\nBeautiful Code\n\nNyt tulostus oli\n" + io.getSysOut(), io.getSysOut().contains("222") || io.getSysOut().contains("2007"));
        assertTrue("Kun syöte on\n" + syote + "\n ja tulostustoive on " + tulostus + ", odotetaan tulostusta:\nKonMari\nBeautiful Code\n\nNyt tulostus oli\n" + io.getSysOut(), io.getSysOut().contains("KonMari") && io.getSysOut().contains("Beautiful Code"));
    }

    @Test
    public void testaaSyoteToka() {
        String syote = "Nalle Puh\n50\n2005\n";
        String tulostus = "kaikki";

        io.setSysIn(syote + "\n" + tulostus + "\n");
        Main.main(new String[]{});

        assertTrue("Kun syöte on\n" + syote + "\n ja tulostustoive on " + tulostus + ", odotetaan tulostusta:\nNalle Puh, 50 sivua, 2005\nNyt tulostus oli\n" + io.getSysOut(), io.getSysOut().contains("Nalle Puh, 50 sivua, 2005"));
        assertFalse("Kun syöte on\n" + syote + "\n ja tulostustoive on " + tulostus + ", odotetaan tulostusta:\nNalle Puh, 50 sivua, 2005\nNyt tulostus oli\n" + io.getSysOut(), io.getSysOut().contains("KonMari"));
    }

    @Test
    public void testaaSyoteKolmas() {
        String syote = "Nalle Puh\n50\n2005\nKonMari\n222\n2011\n";
        String tulostus = "kaikki";

        io.setSysIn(syote + "\n" + tulostus + "\n");
        Main.main(new String[]{});

        assertTrue("Kun syöte on\n" + syote + "\n ja tulostustoive on " + tulostus + ", odotetaan tulostusta:\nNalle Puh, 50 sivua, 2005\nKonMari, 222 sivua, 2011\nNyt tulostus oli\n" + io.getSysOut(), io.getSysOut().contains("Nalle Puh, 50 sivua, 2005") && io.getSysOut().contains("KonMari, 222 sivua, 2011"));
    }

}
