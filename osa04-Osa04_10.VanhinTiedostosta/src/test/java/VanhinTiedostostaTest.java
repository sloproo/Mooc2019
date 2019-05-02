
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.UUID;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Rule;
import org.junit.Test;

@Points("04-10")
public class VanhinTiedostostaTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void test1() {
        test("tiedosto1.txt", "sauli,32", "tarja,30", "martti,44");
    }

    @Test
    public void test2() {
        test("tiedosto2.txt", "sauli,41", "tarja,9", "martti,13", "anton,42", "lilja,13", "leena,41");
    }

    @Test
    public void testRandom() {
        test("tiedosto" + UUID.randomUUID().toString().substring(0, 4) + ".txt",
                UUID.randomUUID().toString().substring(0, 4) + "," + new Random().nextInt(5),
                UUID.randomUUID().toString().substring(0, 4) + "," + (30 + new Random().nextInt(5)),
                UUID.randomUUID().toString().substring(0, 4) + "," + (20 + new Random().nextInt(5)),
                UUID.randomUUID().toString().substring(0, 4) + "," + (10 + new Random().nextInt(5)));
    }

    private void test(String tiedosto, String... data) {
        poistaJaLuo(tiedosto, data);
        io.setSysIn(tiedosto + "\n");

        String out = io.getSysOut();

        try {
            VanhinTiedostosta.main(new String[]{});
        } catch (Exception e) {
            fail("Ohjelman suorituksessa tapahtui virhe: " + e.getMessage());
        } finally {
            poista(tiedosto);
        }

        out = io.getSysOut().replace(out, "");
        String vanhin = "";
        int vanhimmanIka = -1;
        String syote = "";
        for (String osa : data) {
            syote = syote + osa + "\n";
            String[] palat = osa.split(",");
            try {
                if (Integer.valueOf(palat[1]) > vanhimmanIka) {
                    vanhin = palat[0];
                    vanhimmanIka = Integer.valueOf(palat[1]);
                }
            } catch (Exception e) {

            }
        }

        tarkistaTekstiOn(vanhin, syote, out);
    }

    private void tarkistaTekstiOn(String mika, String sisalto, String tulostus) {
        assertTrue("Kun tiedoston sisältö on:\n" + sisalto + "\nTulee vanhimman olla " + mika + ".\nTulostus oli:\n" + tulostus, tulostus.contains(mika));

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
