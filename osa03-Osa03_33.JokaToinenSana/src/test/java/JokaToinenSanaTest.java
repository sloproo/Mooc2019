
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import org.junit.*;
import static org.junit.Assert.*;

@Points("03-33")
public class JokaToinenSanaTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void eikaiPoikkeusta() throws Exception {
        io.setSysIn("ohjelmointi on kivaa\nihan totta\n\n");
        try {
            JokaToinenSana.main(new String[0]);
        } catch (Exception e) {
            String v = "\n\npaina nappia show backtrace, virheen syy löytyy hieman alempaa kohdasta "
                    + "\"Caused by\"\n"
                    + "klikkaamalla pääset suoraan virheen aiheuttaneelle koodiriville";

            throw new Exception("syötteellä \"ohjelmointi on kivaa\nihan totta\n\n\"" + v, e);
        }
    }

    @Test
    public void test1() {
        String syote = "ohjelmointi on kivaa\nihan totta\n\n";
        io.setSysIn(syote);
        String oldOut = io.getSysOut();
        JokaToinenSana.main(new String[0]);

        String out = io.getSysOut().replace(oldOut, "");
        odotaSisaltaaPalat(out, syote);
    }

    @Test
    public void test2() {
        String syote = "ski-bi dibby yo da dub\ndib\n\n";
        io.setSysIn(syote);
        String oldOut = io.getSysOut();
        JokaToinenSana.main(new String[0]);

        String out = io.getSysOut().replace(oldOut, "");
        odotaSisaltaaPalat(out, syote);
    }

    @Test
    public void test3() {
        String syote = "olipa kerran pidempi merkkijono joka tuntui jatkuvan lahes loputtomiin\n\n";
        io.setSysIn(syote);
        String oldOut = io.getSysOut();
        JokaToinenSana.main(new String[0]);

        String out = io.getSysOut().replace(oldOut, "");
        odotaSisaltaaPalat(out, syote);
    }

    private static void odotaSisaltaaPalat(String tulostus, String syote) {
        Set<String> odotetut = new HashSet<>();
        Scanner s = new Scanner(syote);
        while (s.hasNextLine()) {
            String rivi = s.nextLine();
            if (rivi.isEmpty()) {
                continue;
            }

            String[] osat = rivi.split(" ");
            for (int i = 1; i < osat.length; i += 2) {
                odotetut.add(osat[i].trim());
            }
        }

        for (String rivi : tulostus.split("\n")) {
            rivi = rivi.trim();
            if (!odotetut.contains(rivi)) {
                fail("Tulostuksessa oli merkkijono " + rivi + " jota ei odotettu.\nTarkista ohjelman toiminta syötteellä:\n" + syote);
            }

            odotetut.remove(rivi);
        }

        if (!odotetut.isEmpty()) {
            ArrayList<String> odotetutLista = new ArrayList(odotetut);
            fail("Tulostuksesta puuttui merkkijono " + odotetutLista.get(0) + "\nTarkista ohjelman toiminta syötteellä:\n" + syote);
        }
    }

}
