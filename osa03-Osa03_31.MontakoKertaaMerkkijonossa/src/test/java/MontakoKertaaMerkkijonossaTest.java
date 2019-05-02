
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import org.junit.*;
import static org.junit.Assert.*;

@Points("03-31")
public class MontakoKertaaMerkkijonossaTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void eikaiPoikkeusta() throws Exception {
        io.setSysIn("ohjelmointi\nmoi\n");
        try {
            MontakoKertaaMerkkijonossa.main(new String[0]);
        } catch (Exception e) {
            String v = "\n\npaina nappia show backtrace, virheen syy löytyy hieman alempaa kohdasta "
                    + "\"Caused by\"\n"
                    + "klikkaamalla pääset suoraan virheen aiheuttaneelle koodiriville";

            throw new Exception("syötteellä \"ohjelmointi moi\"" + v, e);
        }
    }

    @Test
    public void test1() {
        String syote = "ohjelmointi\nmoi\n";
        io.setSysIn(syote);

        MontakoKertaaMerkkijonossa.main(new String[0]);

        odotaTuloste(syote, "Merkkijonon moi esiintymiskertoja");
        odotaTuloste(syote, "1");
        for (int i = 2; i < 10; i++) {
            alaOdotaTuloste(syote, "" + i);
        }
    }

    @Test
    public void test2() {
        String syote = "ski-bi dibby dib yo da dub dub\ndib\n";
        io.setSysIn(syote);

        MontakoKertaaMerkkijonossa.main(new String[0]);

        odotaTuloste(syote, "Merkkijonon dib esiintymiskertoja");
        odotaTuloste(syote, "2");
        for (int i = 1; i < 10; i++) {
            if (i == 2) {
                continue;
            }
            alaOdotaTuloste(syote, "" + i);
        }
    }

    private void odotaTuloste(String syote, String mjono) {
        assertTrue("Kun syöte on;\n" + syote + "\nOdotettiin, että tulosteessa esiintyy:\n" + mjono + ".\nNyt tuloste oli:\n" + io.getSysOut(), io.getSysOut().contains(mjono));
    }

    private void alaOdotaTuloste(String syote, String mjono) {
        assertFalse("Kun syöte on;\n" + syote + "\nOdotettiin, että tulosteessa ei esiinny:\n" + mjono + ".\nNyt tuloste oli:\n" + io.getSysOut(), io.getSysOut().contains(mjono));
    }
}
