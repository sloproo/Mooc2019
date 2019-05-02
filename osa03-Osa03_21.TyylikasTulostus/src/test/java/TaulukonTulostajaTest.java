import fi.helsinki.cs.tmc.edutestutils.MockInOut;
import fi.helsinki.cs.tmc.edutestutils.Points;
import java.util.Arrays;
import java.util.Random;
import org.junit.*;
import static org.junit.Assert.*;

@Points("03-21")
public class TaulukonTulostajaTest {

    @Test
    public void tarkistaTulostus() {
        int[] taulukoidenPituudet = {1, 2, 3, 4, 5, 10, 20, 100};

        Random random = new Random();
        for (int i = 0; i < taulukoidenPituudet.length; i++) {
            int pituus = taulukoidenPituudet[i];
            int[] taulukko = new int[pituus];
            for (int j = 0; j < taulukko.length; j++) {
                taulukko[j] = random.nextInt(150);
            }

            tarkistaTaulukko(taulukko);
        }
    }

    private void tarkistaTaulukko(int[] taulukko) {
        MockInOut mio = new MockInOut("");

        TaulukonTulostaja.tulostaTyylikkaasti(taulukko);

        String output = mio.getOutput().trim();
        if (taulukko.length == 0) {
            if (!output.isEmpty()) {
                fail("Kun metodille tulostaTyylikkaasti() annettiin tyhjä taulukko, metodi tulosti silti jotain: " + output);
            }
            return;
        }

        if (output.isEmpty()) {
            fail("Et tulostanut mitään kun syötteenä oli taulukko " + Arrays.toString(taulukko));
        }

        String[] numbers = output.split(",", -1);
        if (numbers.length == 0) {
            fail("Et tulostanut mitään kun syötteenä oli taulukko " + Arrays.toString(taulukko));
        }
        if (numbers.length != taulukko.length) {
            fail("Tulosteessa pitäisi olla pilkkuja tasan " + (taulukko.length - 1)
                    + " kappaletta, kun niitä oli  " + (numbers.length - 1) + ". Taulukon sisältö oli: " + Arrays.toString(taulukko));
        }

        int loppu = Math.max(0, output.length() - 2);
        assertFalse("tulosteessasi ei saa olla rivinvaihtoa kuin lopussa! syötteellä " + Arrays.toString(taulukko) + " tulostit\n" + output,
                output.substring(0, loppu).contains("\n"));

        for (int i = 0; i < numbers.length; i++) {
            String numberString = numbers[i].trim();
            if (numberString.isEmpty()) {
                fail("Tulosteessa täytyy olla jokaisen pilkun jälkeen numero. Tarkista, että tuloste ei lopu pilkkuun. Tulostit: " + output);
            }

            int number;
            try {
                number = Integer.valueOf(numberString);
            } catch (Exception e) {
                fail("Tulosteessa saa olla vain pilkulla erotettuja numeroita! Tämä ei ole numero: " + numberString + ". Tulostit: " + output);
                return;
            }

            if (number != taulukko[i]) {
                fail("Taulukon indeksi " + i + " oli numero " + taulukko[i] + ", mutta tulosteesta löytyi numero: " + number);
            }
        }
    }
}
