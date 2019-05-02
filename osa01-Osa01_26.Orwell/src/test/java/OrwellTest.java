
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import static org.junit.Assert.assertTrue;
import org.junit.Rule;
import org.junit.Test;

@Points("01-26")
public class OrwellTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void test() {
        testOrwell(1983);
    }

    @Test
    public void testToinen() {
        testOrwell(1984);
    }

    @Test
    public void testKolmas() {
        testOrwell(1985);
    }

    private void testOrwell(int luku) {
        ReflectionUtils.newInstanceOfClass(Orwell.class);
        io.setSysIn(luku + "\n");
        Orwell.main(new String[0]);

        String out = io.getSysOut();

        assertTrue("Et kysynyt käyttäjältä mitään!", out.trim().length() > 0);

        if (luku == 1984) {
            assertTrue("Tulosteessasi pitäisi olla teksti \"Orwell\", kun annettu luku on "
                    + luku + ", nyt ei ollut. Tulosteesi oli: " + out, 
                    out.contains("rwell"));
        } else {
            assertTrue("Tulosteessasi ei pitäisi olla tekstiä \"Orwell\", kun annettu luku on "
                    + luku + ", nyt oli. Tulosteesi oli: " + out, 
                    !out.contains("rwell"));
        }
    }
}
