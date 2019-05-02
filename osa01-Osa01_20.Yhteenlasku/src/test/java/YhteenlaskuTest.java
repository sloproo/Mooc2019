
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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@Points("01-20")
public class YhteenlaskuTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void tulostusOikeinEkalleEsimerkille() {
        tulostusOikein("5", "4");
    }

    @Test
    public void tulostusOikeinTokalleEsimerkille() {
        tulostusOikein("73457", "12888");
    }

    private void tulostusOikein(String eka, String toka) {
        String syote = eka + "\n" + toka + "\n";
        io.setSysIn(syote);
        Yhteenlasku.main(new String[]{});
        String[] lines = new String[]{"Syötä ensimmäinen luku!",
            "Syötä toinen luku!",
            "" + eka + " + " + toka + " = " + (Integer.valueOf(eka) + Integer.valueOf(toka))
        };

        List<String> rivit = rivit(io.getSysOut().trim());

        assertEquals("Odotettiin, että tulostuksessa olisi " + lines.length + " rivi" + ((lines.length == 1) ? "" : "ä") + ". Nyt rivejä oli " + rivit.size() + ".", lines.length, rivit.size());
        for (int i = 0; i < rivit.size(); i++) {
            assertEquals("Rivin " + (i + 1) + " tulostus väärin kun syöte on:\n" + syote + "\nOdotettiin merkkijonoa:\n" + lines[i] + "\nMutta tulostus oli:\n" + rivit.get(i), lines[i].trim(), rivit.get(i).trim());
        }
    }

    @Test
    public void jarjestysOikein() {
        List<String> koodi = koodi("Yhteenlasku.java");
        int lkm = laskeEsiintymat(koodi, "System.out.println.*Integer.*System.out.println.*Integer.*System.out.println");
        int lkm2 = laskeEsiintymat(koodi, "System.out.println.*Integer.*System.out.println.*Integer.*System.out.println");
        assertTrue("Toteuta ohjelma siten, että tulostus ja kysely tapahtuu vuorotellen.\nEnsin tulostaminen, sitten kysyminen, ja sitten tulostaminen, jne...", lkm == 1 || lkm2 == 1);
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
        return laskeEsiintymat(rivit.stream().reduce("", (a, b) -> a + " " + b), haettava);

    }

    private int laskeEsiintymat(String mjono, String haettava) {
        int lkm = 0;
        while (mjono.matches(".*" + haettava + ".*")) {
            mjono = mjono.replaceFirst(haettava, "");
            lkm++;
        }

        return lkm;
    }

}
