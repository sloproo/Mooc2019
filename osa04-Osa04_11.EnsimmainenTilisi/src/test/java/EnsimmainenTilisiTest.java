
import fi.helsinki.cs.tmc.edutestutils.Points;
import org.junit.Test;

import static org.junit.Assert.*;

@Points("04-11")
public class EnsimmainenTilisiTest {

    @Test
    public void testaaToString() throws Exception {
        MockInOut mio = new MockInOut("");

        EnsimmainenTilisi.main(new String[0]);

        String out = mio.getOutput();
        assertTrue("ohjelman tulee tulostaa tili, eli kutsua System.out.println(tili); tili on tässä tilimuuttujan nimi", out.contains("120.0"));
        mio.close();

    }
}
