
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.Before;
import org.junit.Rule;

@Points("09-10")
public class KirjatTiedostostaTest {

    @Rule
    public MockStdio io = new MockStdio();

    private Reflex.MethodRef1 metodi;

    @Before
    public void onSetup() {
        metodi = Reflex.reflect(KirjatTiedostosta.class).staticMethod("lueKirjat").returning(List.class).taking(String.class);
    }

    @Test
    public void onMetodiLueKirjat() {
        metodi.requirePublic();
    }

    @Test
    public void metodiPalauttaaKirjat() throws IOException, Throwable {
        String data = "A,1,2,B";
        File uusi = luo(data);
        List<Kirja> rivit = (List<Kirja>) metodi.invoke(uusi.getAbsolutePath());
        uusi.delete();

        String virhe = "Kun tiedosto on muotoa:\n" + data + "\n, tulee tiedoston luvun palauttaa yksi kirja."
                + "\nnimi: A"
                + "\njulkaisuvuosi: 1"
                + "\nsivuja: 2"
                + "\nkirjoittaja: B";

        assertTrue(virhe, rivit.size() == 1);

        Kirja k = rivit.get(0);
        assertEquals(virhe, "A", k.getNimi());
        assertEquals(virhe, "B", k.getKirjoittaja());
        assertEquals(virhe, 1, k.getJulkaisuvuosi());
        assertEquals(virhe, 2, k.getSivuja());

    }

    @Test
    public void metodiPalauttaaKirjat2() throws IOException, Throwable {
        String data = "A,1,2,B\nC,3,4,D";
        File uusi = luo(data);
        List<Kirja> rivit = (List<Kirja>) metodi.invoke(uusi.getAbsolutePath());
        uusi.delete();

        String virhe = "Kun tiedosto on muotoa:\n" + data + "\n, tulee tiedoston luvun palauttaa kaksi kirjaa. Ensimmäinen:\n"
                + "\nnimi: A"
                + "\njulkaisuvuosi: 1"
                + "\nsivuja: 2"
                + "\nkirjoittaja: B"
                + "\n\nToinen:"
                + "\nnimi: C"
                + "\njulkaisuvuosi: 3"
                + "\nsivuja: 4"
                + "\nkirjoittaja: D";

        assertTrue(virhe, rivit.size() == 2);

        Kirja k = null;

        try {
            k = rivit.get(0);
            assertEquals(virhe, "A", k.getNimi());
            assertEquals(virhe, "B", k.getKirjoittaja());
            assertEquals(virhe, 1, k.getJulkaisuvuosi());
            assertEquals(virhe, 2, k.getSivuja());
        } catch (Throwable t) {
            k = rivit.get(1);
            assertEquals(virhe, "A", k.getNimi());
            assertEquals(virhe, "B", k.getKirjoittaja());
            assertEquals(virhe, 1, k.getJulkaisuvuosi());
            assertEquals(virhe, 2, k.getSivuja());
        }

        try {
            k = rivit.get(1);
            assertEquals(virhe, "C", k.getNimi());
            assertEquals(virhe, "D", k.getKirjoittaja());
            assertEquals(virhe, 3, k.getJulkaisuvuosi());
            assertEquals(virhe, 4, k.getSivuja());
        } catch (Throwable t) {
            k = rivit.get(0);
            assertEquals(virhe, "C", k.getNimi());
            assertEquals(virhe, "D", k.getKirjoittaja());
            assertEquals(virhe, 3, k.getJulkaisuvuosi());
            assertEquals(virhe, 4, k.getSivuja());
        }

    }

    public File luo(String rivit) throws IOException {
        File tmp = File.createTempFile("tmp-", "teht-09-09");
        FileWriter fw = new FileWriter(tmp);
        fw.write(rivit);
        fw.flush();
        fw.close();
        return tmp;
    }
}
