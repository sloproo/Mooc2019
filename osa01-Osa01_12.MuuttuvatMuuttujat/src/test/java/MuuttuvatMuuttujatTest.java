
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import java.util.regex.Pattern;
import org.junit.*;
import static org.junit.Assert.assertTrue;

@Points("01-12")
public class MuuttuvatMuuttujatTest {

    String out;

    @Rule
    public MockStdio io = new MockStdio();

    @Before
    public void sieppaaTulostus() {
        MuuttuvatMuuttujat.main(new String[0]);
        out = io.getSysOut();
    }

    String ekaRegex(String a, String b) {
        return "(?s).*" + a + "\\s*" + b + "\\s.*";
    }

    String tokaRegex(String a, String b) {
        return "(?s).*tiiviste.*\\s*" + b + "\\s.*";
    }

    void testaa(String a, String b) {
        assertTrue("Tarkasta että " + a + " -tulostus on oikein!",
                Pattern.matches(ekaRegex(a, b), out));
        assertTrue("Tarkasta että " + a + " -tulostus on oikein myös tiivistelmässä!",
                Pattern.matches(tokaRegex(a, b), out));
    }

    @Test
    public void testaaKanat() {
        testaa("Kanoja:", "9000");
    }

    @Test
    public void testaaPekoni() {
        testaa("Pekonia \\(kg\\):", "0\\.1");
    }

    @Test
    public void testaaTraktori() {
        testaa("Traktori:", "Zetor");
    }

}
