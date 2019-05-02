
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.Before;
import org.junit.Rule;

@Points("09-09")
public class TiedostonRivitTest {

    @Rule
    public MockStdio io = new MockStdio();

    private Reflex.MethodRef1 metodi;

    @Before
    public void onSetup() {
        metodi = Reflex.reflect(TiedostonRivit.class).staticMethod("lue").returning(List.class).taking(String.class);
    }

    @Test
    public void onMetodiLue() {
        metodi.requirePublic();
    }

    @Test
    public void metodiPalauttaaRivit() throws IOException, Throwable {
        String data = "olipa\nkerran\nelama\n";
        File uusi = luo(data);
        List<String> rivit = (List<String>) metodi.invoke(uusi.getAbsolutePath());
        uusi.delete();

        for (String mjono : data.split("\n")) {
            assertTrue("Kun tiedostossa on rivit " + data + ", tulee metodin palauttamalla listalla olla merkkijono " + mjono, rivit.contains(mjono));
        }
    }

    @Test
    public void metodiPalauttaaRivit2() throws IOException, Throwable {
        String data = "hauki on kala\nkahdella rivilla\n";
        File uusi = luo(data);
        List<String> rivit = (List<String>) metodi.invoke(uusi.getAbsolutePath());
        uusi.delete();

        for (String mjono : data.split("\n")) {
            assertTrue("Kun tiedostossa on rivit " + data + ", tulee metodin palauttamalla listalla olla merkkijono " + mjono, rivit.contains(mjono));
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
