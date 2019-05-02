
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Rule;

@Points("09-03")
public class PositiivisetLuvutTest {

    @Rule
    public MockStdio io = new MockStdio();

    private Reflex.MethodRef1 metodi;

    @Before
    public void onSetup() {
        metodi = Reflex.reflect(PositiivisetLuvut.class).staticMethod("positiiviset").returning(List.class).taking(List.class);
    }

    @Test
    public void onMetodiPositiiviset() {
        metodi.requirePublic();
    }

    @Test
    public void metodiEiMuokkaaParametrinaAnnettavaaListaa() throws Throwable {
        List<Integer> luvut = luvut(-3, -7, 0, 7, 3);
        List<Integer> kopio = new ArrayList<>(luvut);

        metodi.invoke(luvut);

        assertEquals("Metodin positiiviset ei tule muokata alkuperäistä listaa. Kokeile:\nList<Integer> luvut = new ArrayList<>();\n"
                + "luvut.add(-3);\n"
                + "luvut.add(-7);\n"
                + "luvut.add(0);\n"
                + "luvut.add(7);\n"
                + "luvut.add(3);\n"
                + "System.out.println(luvut);\n"
                + "positiiviset(luvut);\n"
                + "System.out.println(luvut);", kopio, luvut);
    }

    @Test
    public void metodiPalauttaaPositiivisetLuvut() throws Throwable {
        List<Integer> luvut = luvut(-8, -11, -3, 1, 8, 1);
        List<Integer> positiiviset = luvut.stream().filter(l -> l > 0).collect(Collectors.toList());

        List<Integer> palautus = (List<Integer>) metodi.invoke(luvut);

        assertEquals("Metodin positiiviset tulee palauttaa lista, joka sisältää parametrina saadun listan positiiviset luvut.\n"
                + "Kokeile:\nList<Integer> luvut = new ArrayList<>();\n"
                + "luvut.add(-8);\n"
                + "luvut.add(-11);\n"
                + "luvut.add(-3);\n"
                + "luvut.add(1);\n"
                + "luvut.add(8);\n"
                + "luvut.add(1);\n"
                + "System.out.println(luvut);\n"
                + "positiiviset(luvut);\n"
                + "System.out.println(luvut);", positiiviset, palautus);
    }

    @Test
    public void metodiPalauttaaPositiivisetLuvut2() throws Throwable {
        List<Integer> luvut = luvut(2, -8, -11, -3, 1, 8, 1);
        List<Integer> positiiviset = luvut.stream().filter(l -> l > 0).collect(Collectors.toList());

        List<Integer> palautus = (List<Integer>) metodi.invoke(luvut);

        assertEquals("Metodin positiiviset tulee palauttaa lista, joka sisältää parametrina saadun listan positiiviset luvut.\n"
                + "Kokeile:\nList<Integer> luvut = new ArrayList<>();\n"
                + "luvut.add(2);\n"
                + "luvut.add(-8);\n"
                + "luvut.add(-11);\n"
                + "luvut.add(-3);\n"
                + "luvut.add(1);\n"
                + "luvut.add(8);\n"
                + "luvut.add(1);\n"
                + "System.out.println(luvut);\n"
                + "positiiviset(luvut);\n"
                + "System.out.println(luvut);", positiiviset, palautus);
    }

    public List<Integer> luvut(int... l) {
        List<Integer> luvut = new ArrayList<>();

        for (int i : l) {
            luvut.add(i);
        }

        return luvut;
    }

}
