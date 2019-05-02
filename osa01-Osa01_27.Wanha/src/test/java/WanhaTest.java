
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import static org.junit.Assert.assertTrue;
import org.junit.Rule;
import org.junit.Test;

@Points("01-27")
public class WanhaTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void test() {
        testWanha(2016);
    }

    @Test
    public void testToinen() {
        testWanha(2015);
    }

    @Test
    public void testKolmas() {
        testWanha(2014);
    }

    private void testWanha(int luku) {
        ReflectionUtils.newInstanceOfClass(Wanha.class);
        io.setSysIn(luku + "\n");
        Wanha.main(new String[0]);

        String out = io.getSysOut();

        assertTrue("Et kysynyt käyttäjältä mitään!", out.trim().length() > 0);

        if (luku < 2015) {
            assertTrue("Tulosteessasi pitäisi olla teksti \"Wanha!\", kun annettu luku on "
                    + luku + ", nyt ei ollut. Tulosteesi oli: " + out, 
                    out.contains("Wanha"));
        } else {
            assertTrue("Tulosteessasi ei pitäisi olla tekstiä \"Wanha\", kun annettu luku on "
                    + luku + ", nyt oli. Tulosteesi oli: " + out, 
                    !out.contains("Wanha"));
        }
    }
}
