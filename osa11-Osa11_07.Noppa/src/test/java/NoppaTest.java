
import fi.helsinki.cs.tmc.edutestutils.Points;
import java.util.Random;
import static org.junit.Assert.*;
import org.junit.Test;

@Points("11-07")
public class NoppaTest {

    @Test
    public void noppaTarjoaaKaikkiLuvut() {
        int sarmat = new Random().nextInt(15) + 2;
        Noppa n = new Noppa(sarmat);

        int luku = n.heita();
        assertTrue("Kun luodaan Noppa n = new Noppa(" + sarmat + "); ja kutsutaan n.heita(), palautus oli " + luku + " vaikka tuloksen pitäisi olla valillä 1..." + sarmat, luku > 0 && luku <= sarmat);
        int i = 0;
        while (true) {
            int uusi = n.heita();
            if (uusi != luku) {
                break;
            }
            assertTrue("Kun luodaan Noppa n = new Noppa(" + sarmat + "); ja kutsutaan n.heita(), palautus oli " + luku + " vaikka tuloksen pitäisi olla valillä 1..." + sarmat, luku > 0 && luku <= sarmat);

            i++;
            if (i == 20) {
                fail("Noppasi tuottaa jokaisella heitolla saman luvun! Testaa että pääohjelma runko toimii järkevästi");
            }
        }
        int[] luvut = new int[sarmat + 1];
        for (int j = 0; j < 1000; j++) {
            luku = n.heita();
            assertTrue("kun luodaan Noppa n = new Noppa(" + sarmat + "); ja kutsutaan n.heita(), palautus oli " + luku + " vaikka tuloksen pitäisi olla valillä 1..." + sarmat, luku > 0 && luku <= sarmat);
            luvut[luku]++;
        }

        for (int j = 1; j < luvut.length; j++) {
            assertTrue("Toimiiko noppasi oikein? Kun luotiin Noppa n = new Noppa(" + sarmat + "); tuhannella heitolla lukua " + j + " oli vain " + luvut[j] + " kappaletta.", luvut[j] > 10);
        }
    }
}
