
import fi.helsinki.cs.tmc.edutestutils.MockInOut;
import fi.helsinki.cs.tmc.edutestutils.Points;
import java.util.Arrays;
import java.util.Random;
import org.junit.*;
import static org.junit.Assert.*;

@Points("03-22")
public class TulostinTest {

    @Test
    public void tarkistaTahdet() {
        int[] taulukoidenPituudet = {0, 1, 2, 3, 4, 5, 10, 20, 100};

        Random random = new Random();
        for (int i = 0; i < taulukoidenPituudet.length; i++) {
            int pituus = taulukoidenPituudet[i];
            int[] taulukko = new int[pituus];
            for (int j = 0; j < taulukko.length; j++) {
                taulukko[j] = 1 + random.nextInt(10) + pituus;
            }
            tarkistaTaulukko(taulukko);
        }
    }

    private void tarkistaTaulukko(int[] taulukko) {
        MockInOut mio = new MockInOut("");
        Tulostin.tulostaTaulukkoTahtina(taulukko);

        String output = mio.getOutput().trim();
        if (taulukko.length == 0) {
            if (!output.isEmpty()) {
                fail("Kun metodille tulostaTaulukkoTahtina annettiin tyhjä taulukko, metodi tulosti silti jotain: " + output);
            }
            return;
        }

        if (output.isEmpty()) {
            fail("Et tulostanut mitään parametrilla " + Arrays.toString(taulukko) + " Tarkista, että koodisi on metodissa tulostaTaulukkoTahtina(int taulukko).");
        }

        String[] tahdet = output.split("\\n");
        if (tahdet.length == 0) {
            fail("Et tulostanut mitään parametrilla" + Arrays.toString(taulukko) + " Tarkista, että koodisi on metodissa tulostaTaulukkoTahtina().");
        }

        if (tahdet.length < taulukko.length) {
            fail("Kutsuttaessa metodia parametrilla " + Arrays.toString(taulukko) + ", tulosteessa oli vain " + tahdet.length + " riviä, kun taulukon pituuden perusteella niitä olisi pitänyt olla: " + taulukko.length);
        }

        for (int i = 0; i < tahdet.length; i++) {
            String rivi = tahdet[i].trim();
            int maara = taulukko[i];
            if (!rivi.matches("[\\*]+")) {
                fail("Kutsuttaessa metodia parametrilla " + Arrays.toString(taulukko) + ", tulostetuilla riveillä pitäisi olla vain tähtiä, mutta riviltä löytyi: " + rivi);
            }
            if (rivi.length() != maara) {
                fail("Kutsuttaessa metodia parametrilla " + Arrays.toString(taulukko) + ", tulostetulla rivillä oli " + rivi.length() + " tähteä, kun sillä olisi pitänyt olla: " + maara);
            }
        }
    }
}
