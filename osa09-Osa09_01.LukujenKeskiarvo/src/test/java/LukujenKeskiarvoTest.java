

import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;

@Points("09-01")
public class LukujenKeskiarvoTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void testaaSyoteEka() {
        String syote = "2\n4\n6\nloppu\n";
        io.setSysIn(syote);
        LukujenKeskiarvo.main(new String[]{});

        assertTrue("Kun syöte on:\n" + syote + "\nkeskiarvon pitäisi olla: 4.0\nNyt tulostus oli " + io.getSysOut(), io.getSysOut().contains("4.0"));
        assertFalse("Ohjelman tulosteesta löytyi merkkijono 0.6666 kun syöte oli:\n" + syote, io.getSysOut().contains("0.6666"));
    }

    @Test
    public void testaaSyoteToka() {
        String syote = "-1\n1\n2\nloppu\n";
        io.setSysIn(syote);
        LukujenKeskiarvo.main(new String[]{});

        assertTrue("Kun syöte on:\n" + syote + "\nkeskiarvon pitäisi olla: 0.666666\nNyt tulostus oli " + io.getSysOut(), io.getSysOut().contains("0.666666"));
        assertFalse("Ohjelman tulosteesta löytyi merkkijono 4.0 kun syöte oli:\n" + syote, io.getSysOut().contains("4.0"));
    }

    @Test
    public void testaaSyoteSatunnainen() {
        Random rnd = new Random();
        int lukuja = rnd.nextInt(5) + 3;
        List<Integer> luvut = new ArrayList<>();

        String syote = "";
        for (int i = 0; i < lukuja; i++) {
            int luku = rnd.nextInt() % 10000;
            luvut.add(luku);

            syote += luku;
            syote += "\n";
        }

        syote += "loppu\n";

        io.setSysIn(syote);
        LukujenKeskiarvo.main(new String[]{});

        double ka = luvut.stream().mapToInt(i -> i).average().getAsDouble();
        String kaS = "" + ka;
        if (kaS.length() > 6) {
            kaS = kaS.substring(0, 6);
        }

        assertTrue("Kun syöte on:\n" + syote + "\nkeskiarvon pitäisi olla: " + ka + "\nNyt tulostus oli:\n" + io.getSysOut(), io.getSysOut().contains(kaS));
    }

}
