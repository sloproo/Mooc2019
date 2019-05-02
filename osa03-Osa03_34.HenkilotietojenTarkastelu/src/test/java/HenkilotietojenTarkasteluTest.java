
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import org.junit.*;
import static org.junit.Assert.*;

@Points("03-34")
public class HenkilotietojenTarkasteluTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void test1() {
        String syote = "leevi,2017\n"
                + "anton,2017\n"
                + "lilja,2017\n"
                + "venla,2014\n"
                + "gabriel,2009\n\n";
        io.setSysIn(syote);
        String oldOut = io.getSysOut();
        HenkilotietojenTarkastelu.main(new String[0]);

        String out = io.getSysOut().replace(oldOut, "");
        onkoTiedotOikein(out, syote, "gabriel", "2014.8");
    }

    @Test
    public void test2() {
        String syote = "leevi,2017\n\n";
        io.setSysIn(syote);
        String oldOut = io.getSysOut();
        HenkilotietojenTarkastelu.main(new String[0]);

        String out = io.getSysOut().replace(oldOut, "");
        onkoTiedotOikein(out, syote, "leevi", "2017.0");
    }

    @Test
    public void test3() {
        String syote = "sauli,1948\n"
                + "tarja,1943\n"
                + "martti,1936\n"
                + "mauno,1923\n"
                + "urho,1900\n\n";
        io.setSysIn(syote);
        String oldOut = io.getSysOut();
        HenkilotietojenTarkastelu.main(new String[0]);

        String out = io.getSysOut().replace(oldOut, "");
        onkoTiedotOikein(out, syote, "martti", "1930.0");
    }

    private static void onkoTiedotOikein(String tulostus, String syote, String pisinNimi, String keskiarvo) {
        for (String rivi : syote.split("\n")) {
            String nimi = rivi.split(",")[0];
            if (nimi.equals(pisinNimi)) {
                continue;
            }

            assertFalse("Kun syöte on:\n" + syote + "\nEi tulostuksessa pitäisi olla nimeä:\n" + nimi + "\nTulostus oli:\n" + tulostus, tulostus.contains(nimi));
        }

        assertTrue("Kun syöte on:\n" + syote + "\nTulostuksessa pitäisi olla nimi:\n" + pisinNimi + "\nTulostus oli:\n" + tulostus, tulostus.contains(pisinNimi));
        assertTrue("Kun syöte on:\n" + syote + "\nTulostuksessa pitäisi olla keskiarvo:\n" + keskiarvo + "\nTulostus oli:\n" + tulostus, tulostus.contains(keskiarvo));
    }

}
