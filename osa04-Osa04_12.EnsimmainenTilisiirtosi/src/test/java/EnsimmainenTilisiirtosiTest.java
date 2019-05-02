
import fi.helsinki.cs.tmc.edutestutils.Points;
import org.junit.Test;
import static org.junit.Assert.*;

@Points("04-12")
public class EnsimmainenTilisiirtosiTest {

    @Test
    public void testaaToString() throws Exception {
        MockInOut mio = new MockInOut("");

        EnsimmainenTilisiirtosi.main(new String[0]);

        String out = mio.getOutput();
        assertTrue("ohjelman tulee tulostaa siirron jälkeen Matin tilin tiedot", out.contains("900.0"));
        assertTrue("ohjelman tulee tulostaa siirron jälkeen oman tilin tiedot", out.contains("100.0"));
        mio.close();

    }
}
