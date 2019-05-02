
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;

@Points("04-27")
public class TelevisioOhjelmatTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void testaaSyoteEka() {
        String syote = "Miehen puolikkaat\n30\nRemppa vai muutto\n60\n";
        int maksimipituus = 0;

        io.setSysIn(syote + "\n" + maksimipituus + "\n");
        Main.main(new String[]{});

        assertFalse("Kun syöte on\n" + syote + "\n ja maksimipituus on " + maksimipituus + ", ei ohjelmia tule tulostaa.", io.getSysOut().contains("Miehen puolikkaat"));
        assertFalse("Kun syöte on\n" + syote + "\n ja maksimipituus on " + maksimipituus + ", ei ohjelmia tule tulostaa.", io.getSysOut().contains("Remppa vai muutto"));
    }

    @Test
    public void testaaSyoteToka() {
        String syote = "House\n60\nRemppa vai muutto\n60\n";
        int maksimipituus = 60;

        io.setSysIn(syote + "\n" + maksimipituus + "\n");
        Main.main(new String[]{});

        assertTrue("Kun syöte on\n" + syote + "\n ja maksimipituus on " + maksimipituus + ", tulee kaikki ohjelmat tulostaa.", io.getSysOut().contains("House"));
        assertTrue("Kun syöte on\n" + syote + "\n ja maksimipituus on " + maksimipituus + ", tulee kaikki ohjelmat tulostaa.", io.getSysOut().contains("Remppa vai muutto"));
    }

    @Test
    public void testaaSyoteKolmas() {
        String syote = "House\n60\nTeletapit\n30\nRemppa vai muutto\n60\n";
        int maksimipituus = 30;

        io.setSysIn(syote + "\n" + maksimipituus + "\n");
        Main.main(new String[]{});

        assertTrue("Kun syöte on\n" + syote + "\n ja maksimipituus on " + maksimipituus + ", tulee tulostaa vain " + maksimipituus + " minuuttia pitkät tai lyhyemmät ohjelmat.", io.getSysOut().contains("Teletapit"));
        assertFalse("Kun syöte on\n" + syote + "\n ja maksimipituus on " + maksimipituus + ", tulee tulostaa vain " + maksimipituus + " minuuttia pitkät tai lyhyemmät ohjelmat.", io.getSysOut().contains("House"));
        assertFalse("Kun syöte on\n" + syote + "\n ja maksimipituus on " + maksimipituus + ", tulee tulostaa vain " + maksimipituus + " minuuttia pitkät tai lyhyemmät ohjelmat.", io.getSysOut().contains("Remppa vai muutto"));

    }
}
