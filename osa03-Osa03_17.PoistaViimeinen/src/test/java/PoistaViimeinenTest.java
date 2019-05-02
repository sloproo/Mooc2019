
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.util.ArrayList;
import org.junit.*;
import static org.junit.Assert.*;

@Points("03-17")
public class PoistaViimeinenTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void testi() throws Throwable {
        ArrayList<String> mjonot = merkkijonot("a", "b", "c");
        tarkista(mjonot);
    }

    @Test
    public void testi2() throws Throwable {
        ArrayList<String> mjonot = merkkijonot("c", "b", "a");
        tarkista(mjonot);
    }

    @Test
    public void testi3() throws Throwable {
        ArrayList<String> mjonot = merkkijonot();
        tarkista(mjonot);
    }

    private void tarkista(ArrayList<String> mjonot) throws Throwable {
        String oldOut = io.getSysOut();
        ArrayList<String> alkup = new ArrayList<>(mjonot);
        try {
            Reflex.reflect(PoistaViimeinen.class).staticMethod("poistaViimeinen").returningVoid().taking(ArrayList.class).invoke(mjonot);
        } catch (Throwable t) {
            fail("Metodin poistaViimeinen ei tule aiheuttaa poikkeusta. Varmista mm. että tyhjän listan tapauksessa metodi ei tee mitään.\nTarkista metodin toiminta myös listalla " + alkup.toString());
        }

        if (alkup.size() == 0) {
            return;
        }

        assertFalse("Metodin poistaViimeinen tulee poistaa listan viimeisin arvo.", mjonot.contains(alkup.get(alkup.size() - 1)));
        alkup.remove(alkup.size() - 1);
        assertEquals("Listalta tulee poistaa viimeinen arvo. Älä muokkaa listan sisältöä millään muulla tavalla.", mjonot, alkup);
    }

    private static ArrayList<String> merkkijonot(String... lista) {
        ArrayList<String> mjonot = new ArrayList<>();
        for (String s : lista) {
            mjonot.add(s);
        }
        return mjonot;
    }
}
