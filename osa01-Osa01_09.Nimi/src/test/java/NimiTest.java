
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@Points("01-09")
public class NimiTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void adaTulostusOikein() {
        tulostusOikein("Ada");
    }

    @Test
    public void liljaTulostusOikein() {
        tulostusOikein("Lilja");
    }

    private void tulostusOikein(String syote) {
        io.setSysIn("" + syote + "\n");
        Nimi.main(new String[]{});
        String[] lines = new String[]{"Mikä on nimesi?", "Hei " + syote};

        List<String> rivit = rivit(io.getSysOut().trim());

        assertEquals("Odotettiin, että tulostuksessa olisi " + lines.length + " rivi" + ((lines.length == 1) ? "" : "ä") + ". Nyt rivejä oli " + rivit.size() + ".", lines.length, rivit.size());
        for (int i = 0; i < rivit.size(); i++) {
            assertEquals("Rivin " + (i + 1) + " tulostus väärin (käyttäjä syöttää merkkijonon " + syote + ").\nOdotettiin merkkijonoa:\n" + lines[i] + "\nMutta tulostus oli:\n" + rivit.get(i), lines[i].trim(), rivit.get(i).trim());
        }
    }

    private List<String> rivit(String out) {
        return Arrays.asList(out.split("\n"));
    }

    private List<String> koodi(String tiedosto) {
        try {
            return Files.lines(Paths.get("src", "main", "java", tiedosto)).collect(Collectors.toList());
        } catch (IOException e) {
            fail("Tiedoston " + tiedosto + " lukeminen epäonnistui. Tehtävässä tulee kirjoittaa koodia tiedostoon " + tiedosto);
        }

        return new ArrayList<>();
    }

    private int laskeEsiintymat(List<String> rivit, String haettava) {
        int lkm = 0;
        for (String rivi : rivit) {
            while (rivi.matches(".*" + haettava + ".*")) {
                rivi = rivi.replaceFirst(haettava, "");
                lkm++;
            }
        }

        return lkm;
    }

}
