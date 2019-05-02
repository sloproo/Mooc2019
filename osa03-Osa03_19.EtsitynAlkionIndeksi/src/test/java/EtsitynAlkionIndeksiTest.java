
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import static org.junit.Assert.assertTrue;
import org.junit.Rule;
import org.junit.Test;

@Points("03-19")
public class EtsitynAlkionIndeksiTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void esim1() throws Throwable {
        io.setSysIn("3\n");
        EtsitynAlkionIndeksi.main(new String[]{});
        assertTrue("Kun käyttäjä syöttää luvun 3, tulostuksen pitäisi olla \"Luku 3 löytyy indeksistä 4.\".\nNyt tulostus oli:\n" + io.getSysOut(), io.getSysOut().contains("Luku 3 l") && io.getSysOut().contains(" 4."));
    }

    @Test
    public void esim2() throws Throwable {
        io.setSysIn("7\n");
        EtsitynAlkionIndeksi.main(new String[]{});
        assertTrue("Kun käyttäjä syöttää luvun 7, tulostuksen pitäisi olla \"Luku 7 löytyy indeksistä 7.\".\nNyt tulostus oli:\n" + io.getSysOut(), io.getSysOut().contains("Luku 7 l") && io.getSysOut().contains(" 7."));
    }

    @Test
    public void esim3() throws Throwable {
        io.setSysIn("22\n");
        EtsitynAlkionIndeksi.main(new String[]{});
        assertTrue("Kun käyttäjä syöttää luvun 22, tulostuksen pitäisi olla \"Lukua 22 ei löydy.\".\nNyt tulostus oli:\n" + io.getSysOut(), io.getSysOut().contains("Lukua 22 ei l"));
    }

    @Test
    public void ylim1() throws Throwable {
        io.setSysIn("0\n");
        EtsitynAlkionIndeksi.main(new String[]{});
        assertTrue("Kun käyttäjä syöttää luvun 0, tulostuksen pitäisi olla \"Luku 0 löytyy indeksistä 5.\".\nNyt tulostus oli:\n" + io.getSysOut(), io.getSysOut().contains("Luku 0 l") && io.getSysOut().contains(" 5."));
    }

    @Test
    public void ylim2() throws Throwable {
        io.setSysIn("5\n");
        EtsitynAlkionIndeksi.main(new String[]{});
        assertTrue("Kun käyttäjä syöttää luvun 5, tulostuksen pitäisi olla \"Lukua 5 ei löydy.\".\nNyt tulostus oli:\n" + io.getSysOut(), io.getSysOut().contains("Lukua 5 ei l"));
    }
}
