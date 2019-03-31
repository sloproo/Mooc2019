
import fi.helsinki.cs.tmc.edutestutils.Points;
import static org.junit.Assert.*;
import org.junit.Test;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Points("11-08")
public class LottoriviTest {

    public ArrayList<Integer> test() {
        Lottorivi rivi;
        ArrayList<Integer> numerot;

        try {
            rivi = new Lottorivi();
            numerot = rivi.numerot();
        } catch (Throwable t) {
            fail("Jokin meni pieleen luotaessa uutta Lottorivi-oliota! Lisätietoja: " + t);
            return new ArrayList<>();
        }

        assertEquals("Palautettuja lottonumeroita pitäisi olla 7!", 7, numerot.size());

        Set<Integer> jaljellaOlevatNumerot = new HashSet<>();
        for (int i = 1; i <= 40; i++) {
            jaljellaOlevatNumerot.add(i);
        }

        Set<Integer> arvotutNumerot = new HashSet<>();
        for (int i : numerot) {
            assertTrue("Lottonumeron pitäisi olla 1-40. Palautit kuitenkin numeron: " + i,
                    (i >= 1 && i <= 40));
            assertTrue("Metodi sisaltaaNumeron() palauttaa arvon false, vaikka numero oli arvottujen numeroiden listassa: " + i,
                    rivi.sisaltaaNumeron(i));
            assertTrue("Lottorivi sisältää saman numero useampaan kertaan: " + i,
                    arvotutNumerot.add(i));
            jaljellaOlevatNumerot.remove(i);
        }

        for (int i : jaljellaOlevatNumerot) {
            assertFalse("Metodi sisaltaaNumeron() palauttaa arvon true, vaikka numeroa ei ollut arvottujen numeroiden listassa: " + i,
                    rivi.sisaltaaNumeron(i));
        }

        return numerot;
    }

    @Test
    public void testaaYksiArvonta() {
        test();
    }

    @Test
    public void testaaArvoNumerotArpooUudetNumerot() {
        String virhe = "Kun kutsuttiin:\n Lottorivi lottorivi = new Lottorivi();\nSystem.out.println(lottorivi.numerot());\nlottorivi.arvoNumerot();\nSystem.out.println(lottorivi.numerot());\n";
        Lottorivi rivi;
        ArrayList<Integer> numerot;
        try {
            rivi = new Lottorivi();
            numerot = rivi.numerot();
        } catch (Throwable t) {
            fail("Jokin meni pieleen luotaessa uutta Lottorivi-objektia! Lisätietoja: " + t);
            return;
        }
        String numerotString = numerot.toString();
        assertEquals("Palautettuja lottonumeroita pitäisi olla 7!", 7, numerot.size());
        rivi.arvoNumerot();
        assertTrue(virhe + "Palautettuja lottonumeroita pitäisi olla 7. Nyt oli " + rivi.numerot().size(), 7 == rivi.numerot().size());
        assertFalse(virhe + "Ohjelmasi arpoi samat numerot uudestaa. Aika epätodennäköistä!", numerotString.equals(rivi.numerot().toString()));
    }

    @Test
    public void testaaMontaArvontaa() {
        int[] arr = new int[41];
        for (int i = 0; i < 200; i++) {
            for (int x : test()) {
                arr[x]++;
            }
        }

        int montako = 0;
        for (int i = 1; i <= 40; i++) {
            if (arr[i] > 0) {
                montako++;
            }
        }

        assertEquals("200 lottoarvontaa tuotti yhteensä vain " + montako
                + " eri lukua! Ei kauhean satunnaista!", 40, montako);
    }
}
