
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.UUID;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Rule;
import org.junit.Test;

@Points("04-05")
public class KysytynTiedostonTulostaminenTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void test1() {
        test("tiedosto1.txt", "olipa", "kerran", "ihminen");
    }

    @Test
    public void test2() {
        test("tiedosto2.txt", "never", "gonna", "give", "you", "up");
    }

    @Test
    public void testRandom() {
        test("tiedosto" + UUID.randomUUID().toString().substring(0, 4) + ".txt",
                UUID.randomUUID().toString().substring(0, 4),
                UUID.randomUUID().toString().substring(0, 4),
                UUID.randomUUID().toString().substring(0, 4));
    }

    private void test(String tiedosto, String... sanat) {
        poistaJaLuo(tiedosto, sanat);
        io.setSysIn(tiedosto + "\n");

        String out = io.getSysOut();

        try {
            KysytynTiedostonTulostaminen.main(new String[]{});
        } catch (Exception e) {
            fail("Ohjelman suorituksessa tapahtui virhe: " + e.getMessage());
        } finally {
            poista(tiedosto);
        }

        out = io.getSysOut().replace(out, "");
        String syote = "";
        for (String sana : sanat) {
            syote = syote + sana + "\n";
        }

        for (String sana : sanat) {
            tarkistaTekstiOn(sana, syote, out);
        }
    }

    private void tarkistaTekstiOn(String mika, String sisalto, String tulostus) {
        assertTrue("Kun tiedoston sisältö on:\n" + sisalto + "\nTulee koko sisällön olla tulostuksessa.\nNyt tulostuksesta puuttui merkkijono " + mika + ".\nTulostus oli:\n" + tulostus, tulostus.contains(mika));

    }

    private void poistaJaLuo(String tiedosto, String... rivit) {
        poista(tiedosto);

        try {
            kirjoitaTiedostoon(tiedosto, rivit);
        } catch (Exception e) {
            fail("Tiedoston " + tiedosto + " luominen epäonnistui testejä ajettaessa.\nMikäli ohjelma toimii mielestäsi oikein, palauta se palvelimelle.");
        }

    }

    private void poista(String tiedosto) {
        if (new File(tiedosto).exists()) {
            try {
                new File(tiedosto).delete();
            } catch (Exception e) {
                fail("Tiedoston " + tiedosto + " poistaminen epäonnistui testejä ajettaessa.\nMikäli ohjelma toimii mielestäsi oikein, palauta se palvelimelle.");
            }
        }
    }

    private void kirjoitaTiedostoon(String tiedosto, String[] rivit) throws FileNotFoundException {
        try (PrintWriter pw = new PrintWriter(new File(tiedosto))) {
            for (String rivi : rivit) {
                pw.println(rivi);
            }
            pw.flush();
        }
    }
}
