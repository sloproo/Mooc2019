
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import org.junit.*;
import static org.junit.Assert.assertTrue;

@Points("09-13.1 09-13.2")
public class LukutaitoVertailuTest {

    String odotettu = "Niger (2015), female, 11.01572\n"
            + "Mali (2015), female, 22.19578\n"
            + "Guinea (2015), female, 22.87104";

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void lukutaitoVertailuTest() {
        Lukutaitovertailu.main(new String[]{});
        String[] tulostus = io.getSysOut().split("\n");

        String[] odotettu = this.odotettu.split("\n");

        assertTrue("Tulostuksessa pitäisi olla kaikki tiedoston rivit. Nyt rivejä oli " + tulostus.length, tulostus.length >= odotettu.length);
        for (int i = 0; i < odotettu.length; i++) {
            assertTrue("Odotettiin, että " + (i + 1) + ". rivillä olisi \"" + odotettu[i] + "\".\nNyt rivillä " + (i + 1) + " oli \"" + tulostus[i] + "\"", tulostus[i].trim().equals(odotettu[i].trim()));
        }

    }
}
