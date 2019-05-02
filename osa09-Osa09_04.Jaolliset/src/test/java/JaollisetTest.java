
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;

@Points("09-04")
public class JaollisetTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void test() {
        ArrayList<Integer> luvut = new ArrayList<>();
        luvut.add(3);
        luvut.add(2);
        luvut.add(-17);
        luvut.add(-5);
        luvut.add(7);

        ArrayList<Integer> jaolliset = Jaolliset.jaolliset(luvut);

        assertTrue("Kun listalle " + luvut + " kutsutaan jaolliset-metodia, palautetulla listalla pitäisi olla 3 arvoa.", jaolliset.size() == 3);
        assertTrue("Kun listalle " + luvut + " kutsutaan jaolliset-metodia, palautetulla listalla pitäisi olla luku 3.", jaolliset.contains(3));
        assertTrue("Kun listalle " + luvut + " kutsutaan jaolliset-metodia, palautetulla listalla pitäisi olla luku 2.", jaolliset.contains(2));
        assertTrue("Kun listalle " + luvut + " kutsutaan jaolliset-metodia, palautetulla listalla pitäisi olla luku -5.", jaolliset.contains(-5));

        assertTrue("Kun listalle " + luvut + " kutsutaan jaolliset-metodia, ei listan tule muuttua.", luvut.size() == 5);
        assertTrue("Kun listalle " + luvut + " kutsutaan jaolliset-metodia, ei listan tule muuttua.", luvut.get(0) == 3);
        assertTrue("Kun listalle " + luvut + " kutsutaan jaolliset-metodia, ei listan tule muuttua.", luvut.get(1) == 2);
        assertTrue("Kun listalle " + luvut + " kutsutaan jaolliset-metodia, ei listan tule muuttua.", luvut.get(2) == -17);
        assertTrue("Kun listalle " + luvut + " kutsutaan jaolliset-metodia, ei listan tule muuttua.", luvut.get(3) == -5);
        assertTrue("Kun listalle " + luvut + " kutsutaan jaolliset-metodia, ei listan tule muuttua.", luvut.get(4) == 7);
    }

}
