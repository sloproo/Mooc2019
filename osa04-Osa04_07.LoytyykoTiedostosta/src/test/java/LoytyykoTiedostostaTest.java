
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Rule;
import org.junit.Test;

@Points("04-07")
public class LoytyykoTiedostostaTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void loytyy1() throws Throwable {
        testaaLoytyykoTiedostosta("nimet.txt", "ada", true, false);
    }

    @Test
    public void loytyy2() throws Throwable {
        testaaLoytyykoTiedostosta("nimet.txt", "testi", true, false);
    }

    @Test
    public void tiedostovirhe() throws Throwable {
        testaaLoytyykoTiedostosta("nimet-olematon.txt", "ada", false, true);
    }

    @Test
    public void eiLoydy1() throws Throwable {
        testaaLoytyykoTiedostosta("nimet.txt", "antti", false, false);
    }

    @Test
    public void eiLoydy2() throws Throwable {
        testaaLoytyykoTiedostosta("nimet.txt", "elina", false, false);
    }

    public void testaaLoytyykoTiedostosta(String tiedosto, String merkkijono, boolean loytyy, boolean tiedostovirhe) throws Throwable {
        io.setSysIn(tiedosto + "\n" + merkkijono + "\n");
        LoytyykoTiedostosta.main(new String[]{});

        if (tiedostovirhe) {
            assertTrue("Kun luetaan tiedostoa \"" + tiedosto + "\", pitäisi tulostaa viesti \"Tiedoston lukeminen epäonnistui.\". Nyt tulostus oli:\n" + io.getSysOut(), io.getSysOut().contains("lukeminen ep"));
            return;
        } else {
            assertFalse("Kun luetaan tiedostoa \"" + tiedosto + "\", ei pitäisi tulostaa viestiä \"Tiedoston lukeminen epäonnistui.\". Nyt tulostus oli:\n" + io.getSysOut(), io.getSysOut().contains("lukeminen ep"));
        }

        if (loytyy) {
            assertTrue("Kun tiedostosta \"" + tiedosto + "\" etsitään merkkijonoa \"" + merkkijono + "\", sen pitäisi löytyä.\nOdotettiin, että tulostuksessa olisi merkkijono \"löytyi\".\nNyt tulostus oli:\n" + io.getSysOut(), io.getSysOut().contains("ytyi"));
            assertFalse("Kun tiedostosta \"" + tiedosto + "\" etsitään merkkijonoa \"" + merkkijono + "\", sen pitäisi löytyä.\nOdotettiin, että tulostuksessa ei olisi merkkijonoa \"ei löytynyt\".\nNyt tulostus oli:\n" + io.getSysOut(), io.getSysOut().contains("ytynyt"));
        } else {
            assertFalse("Kun tiedostosta \"" + tiedosto + "\" etsitään merkkijonoa \"" + merkkijono + "\", sen ei pitäisi löytyä.\nOdotettiin, että tulostuksessa ei olisi merkkijonoa \"löytyi\".\nNyt tulostus oli:\n" + io.getSysOut(), io.getSysOut().contains("ytyi"));
            assertTrue("Kun tiedostosta \"" + tiedosto + "\" etsitään merkkijonoa \"" + merkkijono + "\", sen ei pitäisi löytyä.\nOdotettiin, että tulostuksessa olisi merkkijono \"ei löytynyt\".\nNyt tulostus oli:\n" + io.getSysOut(), io.getSysOut().contains("ytynyt"));
        }
    }
}
