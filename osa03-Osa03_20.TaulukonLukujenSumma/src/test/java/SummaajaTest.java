import fi.helsinki.cs.tmc.edutestutils.Points;
import java.util.Arrays;
import java.util.Random;
import org.junit.*;
import static org.junit.Assert.*;

@Points("03-20")
public class SummaajaTest {

    @Test
    public void tarkistaSumma() {
        int[] taulukoidenPituudet = {0, 1, 2, 3, 4, 5, 10, 20, 100};

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
        int summa = 0;
        for (int i = 0; i < taulukko.length; i++) {
            summa += taulukko[i];
        }

        int palautettuSumma = Summaaja.laskeTaulukonLukujenSumma(taulukko);

        if (summa != palautettuSumma) {
            fail("Palautettu summa oli " + palautettuSumma + ", kun sen olisi pitänyt olla: " + summa + ". Taulukon sisältö oli: " + Arrays.toString(taulukko));
        }
    }
}
