
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.util.ArrayList;
import org.junit.*;
import static org.junit.Assert.*;

@Points("03-16")
public class SummaTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void testi() throws Throwable {
        ArrayList<Integer> luvut = luvut(3, 1, 7, 9, 2, 6);
        tarkista(luvut);
    }

    @Test
    public void testi2() throws Throwable {
        ArrayList<Integer> luvut = luvut(3, 1, 7, 9, 2, 6);
        tarkista(luvut);
    }

    private void tarkista(ArrayList<Integer> luvut) throws Throwable {
        String oldOut = io.getSysOut();
        int summa = Reflex.reflect(Summa.class).staticMethod("summa").returning(int.class).taking(ArrayList.class).invoke(luvut);

        String out = io.getSysOut().replace(oldOut, "");

        int odotettu = luvut.stream().mapToInt(i -> i).sum();

        assertEquals("Kun metodia summa kutsutaan listalla " + luvut + ", summan pitäisi olla " + odotettu + ". Metodisi palautti arvon " + summa, odotettu, summa);
    }

    private static ArrayList<Integer> luvut(int... lista) {
        ArrayList<Integer> luvut = new ArrayList<>();
        for (int i : lista) {
            luvut.add(i);
        }
        return luvut;
    }
}
