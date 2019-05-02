
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import static org.junit.Assert.assertTrue;
import org.junit.Rule;
import org.junit.Test;

@Points("01-29")
public class TaysiIkaisyysTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void test() {
        testTaysiIkaisyys(17);
    }

    @Test
    public void testToinen() {
        testTaysiIkaisyys(18);
    }

    @Test
    public void testKolmas() {
        testTaysiIkaisyys(19);
    }

    private void testTaysiIkaisyys(int luku) {
        ReflectionUtils.newInstanceOfClass(TaysiIkaisyys.class);
        io.setSysIn(luku + "\n");
        TaysiIkaisyys.main(new String[0]);

        String out = io.getSysOut();

        assertTrue("Et kysynyt käyttäjältä mitään!", out.trim().length() > 0);

        String viesti = "Kun ikä on " + luku + ", ";
        if (luku >= 18) {
            assertTrue(viesti + "tulosteessasi pitäisi olla teksti \"Olet täysi-ikäinen\", nyt ei ollut. Tulosteesi oli: " + out,
                    out.contains("let t"));
            assertTrue(viesti + "tulosteessasi ei pitäisi olla tekstiä \"Et ole täysi-ikäinen\", nyt oli. Tulosteesi oli: " + out,
                    !out.contains("t ole"));
        } else {
            assertTrue("Tulosteessasi pitäisi olla teksti \"Et ole täysi-ikäinen\", nyt ei ollut. Tulosteesi oli: " + out,
                    out.contains("t ole"));
            assertTrue(viesti + "tulosteessasi ei pitäisi olla tekstiä \"Olet täysi-ikäinen\", nyt oli. Tulosteesi oli: " + out,
                    !out.contains("let t"));
        }
    }
}
