
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;

@Points("09-02")
public class TiettyjenLukujenKeskiarvoTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void testaaSyoteEka() {
        String syote = "-1\n1\n2\nloppu\nn\n";
        io.setSysIn(syote);
        TiettyjenLukujenKeskiarvo.main(new String[]{});

        assertTrue("Kun syöte on:\n" + syote + "\nnegatiivisten lukujen keskiarvon pitäisi olla: -1.0\nNyt tulostus oli " + io.getSysOut(), io.getSysOut().contains("-1.0"));
        assertFalse("Kun syöte on:\n" + syote + "\npositiivisten lukujen keskiarvoa ei tule tulostaa.\nNyt tulostus oli " + io.getSysOut(), io.getSysOut().contains("1.5"));
    }

    @Test
    public void testaaSyoteToka() {
        String syote = "-1\n1\n2\nloppu\np\n";
        io.setSysIn(syote);
        TiettyjenLukujenKeskiarvo.main(new String[]{});

        assertTrue("Kun syöte on:\n" + syote + "\npositiivisten lukujen keskiarvon pitäisi olla: 1.5\nNyt tulostus oli " + io.getSysOut(), io.getSysOut().contains("1.5"));
        assertFalse("Kun syöte on:\n" + syote + "\nnegatiivisten lukujen keskiarvoa ei tule tulostaa.\nNyt tulostus oli " + io.getSysOut(), io.getSysOut().contains("-1.0"));
    }

    @Test
    public void testaaSyoteSatunnainenNeg() {
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

        syote += "loppu\nn\n";

        io.setSysIn(syote);
        TiettyjenLukujenKeskiarvo.main(new String[]{});

        double ka = luvut.stream().filter(l -> l < 0).mapToInt(i -> i).average().getAsDouble();
        String kaS = "" + ka;
        if (kaS.length() > 6) {
            kaS = kaS.substring(0, 6);
        }

        assertTrue("Kun syöte on:\n" + syote + "\nkeskiarvon pitäisi olla: " + ka + "\nNyt tulostus oli:\n" + io.getSysOut(), io.getSysOut().contains(kaS));
    }

    @Test
    public void testaaSyoteSatunnainenPos() {
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

        syote += "loppu\np\n";

        io.setSysIn(syote);
        TiettyjenLukujenKeskiarvo.main(new String[]{});

        double ka = luvut.stream().filter(l -> l > 0).mapToInt(i -> i).average().getAsDouble();
        String kaS = "" + ka;
        if (kaS.length() > 6) {
            kaS = kaS.substring(0, 6);
        }

        assertTrue("Kun syöte on:\n" + syote + "\nkeskiarvon pitäisi olla: " + ka + "\nNyt tulostus oli:\n" + io.getSysOut(), io.getSysOut().contains(kaS));
    }
}
