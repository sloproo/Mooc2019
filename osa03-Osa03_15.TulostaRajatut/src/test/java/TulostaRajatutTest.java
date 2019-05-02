
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.util.ArrayList;
import org.junit.*;
import static org.junit.Assert.*;

@Points("03-15")
public class TulostaRajatutTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void testi() throws Throwable {
        ArrayList<Integer> luvut = luvut(3, 1, 7, 9, 2, 6);
        tarkista(1, 2, luvut, 1, 2);
    }

    @Test
    public void testi2() throws Throwable {
        ArrayList<Integer> luvut = luvut(3, 1, 7, 9, 2, 6);
        tarkista(6, 999, luvut, 6, 7, 9);
    }

    private void tarkista(int alaraja, int ylaraja, ArrayList<Integer> luvut, int... odotetut) throws Throwable {
        String oldOut = io.getSysOut();
        Reflex.reflect(TulostaRajatut.class).staticMethod("tulostaRajatutLuvut").returningVoid().taking(ArrayList.class, int.class, int.class).invoke(luvut, alaraja, ylaraja);

        String out = io.getSysOut().replace(oldOut, "");

        for (int i : odotetut) {
            assertTrue("Kun metodia tulostaRajatutLuvut kutsutaan listalla:\n" + luvut + "\n sekä alarajalla " + alaraja + " ja ylärajalla " + ylaraja + "\nTulostuksessa pitäisi olla luku " + i + "\nTulostus:\n" + out, out.contains("" + i));
        }

        NEXT:
        for (int luku : luvut) {
            for (int odotettu : odotetut) {
                if (luku == odotettu) {
                    continue NEXT;
                }
            }

            assertFalse("Kun metodia tulostaRajatutLuvut kutsutaan listalla:\n" + luvut + "\n sekä alarajalla " + alaraja + " ja ylärajalla " + ylaraja + "\nTulostuksessa ei pitäisi olla lukua " + luku + "\nTulostus:\n" + out, out.contains("" + luku));
        }

    }

    private static ArrayList<Integer> luvut(int... lista) {
        ArrayList<Integer> luvut = new ArrayList<>();
        for (int i : lista) {
            luvut.add(i);
        }
        return luvut;
    }
}
