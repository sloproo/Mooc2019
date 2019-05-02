
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import static org.junit.Assert.assertTrue;
import org.junit.Rule;
import org.junit.Test;

@Points("01-25")
public class YlinopeussakkoTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void test() {
        testSakko(119);
    }

    @Test
    public void testToinen() {
        testSakko(120);
    }

    @Test
    public void testKolmas() {
        testSakko(121);
    }

    private void testSakko(int luku) {
        ReflectionUtils.newInstanceOfClass(Ylinopeussakko.class);
        io.setSysIn(luku + "\n");
        Ylinopeussakko.main(new String[0]);

        String out = io.getSysOut();

        assertTrue("Et kysynyt käyttäjältä mitään!", out.trim().length() > 0);

        if (luku > 120) {
            assertTrue("Tulosteessasi pitäisi olla teksti \"Ylinopeussakko!\", kun annettu luku on "
                    + luku + ", nyt ei ollut. Tulosteesi oli: " + out, 
                    out.contains("sakko"));
        } else {
            assertTrue("Tulosteessasi ei pitäisi olla tekstiä \"Ylinopeussakko!\", kun annettu luku on "
                    + luku + ", nyt oli. Tulosteesi oli: " + out, 
                    !out.contains("sakko"));
        }
    }
}
