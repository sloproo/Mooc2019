
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Rule;
import org.junit.Test;

@Points("11-06")
public class LukujaTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void test1() {
        io.setSysIn("5\n");
        try {
            Ohjelma.main(new String[]{});
        } catch (Throwable t) {

        }

        List<Integer> luvut = luvut();
        assertEquals("Kun syöte on 5 tulee tulostuksessa olla 5 lukua. Nyt lukuja oli " + luvut.size() + "\nLuvut olivat: " + luvut, 5, luvut.size());
    }

    @Test
    public void test2() {
        io.setSysIn("100000\n");
        try {
            Ohjelma.main(new String[]{});
        } catch (Throwable t) {

        }

        List<Integer> luvut = luvut();
        for (int i = 0; i <= 10; i++) {
            assertTrue("Ohjelman luomissa satunnaisissa luvuissa pitäisi esiintyä luku " + i, luvut.contains(i));
        }

        assertFalse("Ohjelman luomissa satunnaisissa luvuissa ei pitäisi esiintyä lukua -1.", luvut.contains(-1));
        assertFalse("Ohjelman luomissa satunnaisissa luvuissa ei pitäisi esiintyä lukua 11.", luvut.contains(11));
    }

    private List<Integer> luvut() {
        return Arrays.stream(io.getSysOut().split("\n"))
                .filter(l -> l.replaceAll("[^\\d]", "").equals(l))
                .map(l -> Integer.parseInt(l))
                .collect(Collectors.toList());
    }
}
