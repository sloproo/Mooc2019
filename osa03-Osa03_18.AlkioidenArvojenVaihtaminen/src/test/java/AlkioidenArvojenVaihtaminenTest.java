

import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import static org.junit.Assert.assertTrue;
import org.junit.Rule;
import org.junit.Test;

@Points("03-18")
public class AlkioidenArvojenVaihtaminenTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void esim1() throws Throwable {
        io.setSysIn("2\n4\n");
        AlkioidenArvojenVaihtaminen.main(new String[]{});
        List<Integer> luvut = lueLuvut();
        assertTrue("Ethän muuttanut lukujen tulostusta. Odotettiin, että tulostuksessa olisi 10 lukua, nyt niitä oli " + luvut.size(), luvut.size() == 10);
        luvut = luvut.subList(5, luvut.size());
        int[] odotettu = {1, 3, 9, 7, 5};

        for (int i = 0; i < odotettu.length; i++) {
            assertTrue("Kun indeksien 2 ja 4 arvot vaihdettiin päittäin, odotettiin että indeksissä " + i + " olisi arvo " + odotettu[i] + ". Nyt arvo oli " + luvut.get(i), odotettu[i] == luvut.get(i));
        }
    }

    @Test
    public void esim2() throws Throwable {
        io.setSysIn("0\n1\n");
        AlkioidenArvojenVaihtaminen.main(new String[]{});
        List<Integer> luvut = lueLuvut();
        assertTrue("Ethän muuttanut lukujen tulostusta. Odotettiin, että tulostuksessa olisi 10 lukua, nyt niitä oli " + luvut.size(), luvut.size() == 10);
        luvut = luvut.subList(5, luvut.size());
        int[] odotettu = {3, 1, 5, 7, 9};

        for (int i = 0; i < odotettu.length; i++) {
            assertTrue("Kun indeksien 0 ja 1 arvot vaihdettiin päittäin, odotettiin että indeksissä " + i + " olisi arvo " + odotettu[i] + ". Nyt arvo oli " + luvut.get(i), odotettu[i] == luvut.get(i));
        }
    }

    @Test
    public void kolmas() throws Throwable {
        io.setSysIn("1\n3\n");
        AlkioidenArvojenVaihtaminen.main(new String[]{});
        List<Integer> luvut = lueLuvut();
        assertTrue("Ethän muuttanut lukujen tulostusta. Odotettiin, että tulostuksessa olisi 10 lukua, nyt niitä oli " + luvut.size(), luvut.size() == 10);
        luvut = luvut.subList(5, luvut.size());
        int[] odotettu = {1, 7, 5, 3, 9};

        for (int i = 0; i < odotettu.length; i++) {
            assertTrue("Kun indeksien 1 ja 3 arvot vaihdettiin päittäin, odotettiin että indeksissä " + i + " olisi arvo " + odotettu[i] + ". Nyt arvo oli " + luvut.get(i), odotettu[i] == luvut.get(i));
        }
    }

    private List<Integer> lueLuvut() {
        return Arrays.stream(io.getSysOut().split("\n"))
                .filter(l -> !l.trim().isEmpty())
                .filter(l -> {
                    try {
                        Integer.valueOf(l.trim());
                        return true;
                    } catch (NumberFormatException t) {
                    }
                    return false;
                }).map(i -> Integer.valueOf(i)).collect(Collectors.toList());
    }

}
