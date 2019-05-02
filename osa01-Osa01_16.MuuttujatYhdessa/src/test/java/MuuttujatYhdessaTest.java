
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

@Points("01-16")
public class MuuttujatYhdessaTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void tulostusOikeinEkalleEsimerkille() {
        tulostusOikein("heippa", "11", "4.2", "true");
    }

    @Test
    public void tulostusOikeinTokalleEsimerkille() {
        tulostusOikein("oho!", "-4", "3200.1", "false");
    }

    @Test
    public void kokonaislukuMuunnetaanLuvuksi() {
        try {
            tulostusOikein("oho!", "ei kokonaisluku", "3200.1", "false");
            fail("Ohjelman tulee muuntaa syötetty kokonaisluku kokonaislukumuuttujaksi.");
        } catch (NumberFormatException e) {
        }
    }
    
    @Test
    public void liukulukuMuunnetaanLuvuksi() {
        try {
            tulostusOikein("oho!", "7", "ei liukuluku", "false");
            fail("Ohjelman tulee muuntaa syötetty liukuluku liukulukumuuttujaksi.");
        } catch (NumberFormatException e) {
        }
    }

    private void tulostusOikein(String mjono, String kokonaisluku, String liukuluku, String totuusarvo) {
        String syote = mjono + "\n" + kokonaisluku + "\n" + liukuluku + "\n" + totuusarvo + "\n";
        io.setSysIn(syote);
        MuuttujatYhdessa.main(new String[]{});
        String[] lines = new String[]{"Syötä merkkijono!",
            "Syötä kokonaisluku!",
            "Syötä liukuluku!",
            "Syötä totuusarvo!",
            "Syötit merkkijonon " + mjono,
            "Syötit kokonaisluvun " + Integer.valueOf(kokonaisluku),
            "Syötit liukuluvun " + Double.valueOf(liukuluku),
            "Syötit totuusarvon " + Boolean.valueOf(totuusarvo)};

        List<String> rivit = rivit(io.getSysOut().trim());

        assertEquals("Odotettiin, että tulostuksessa olisi " + lines.length + " rivi" + ((lines.length == 1) ? "" : "ä") + ". Nyt rivejä oli " + rivit.size() + ".", lines.length, rivit.size());
        for (int i = 0; i < rivit.size(); i++) {
            assertEquals("Rivin " + (i + 1) + " tulostus väärin kun syöte on:\n" + syote + "\nOdotettiin merkkijonoa:\n" + lines[i] + "\nMutta tulostus oli:\n" + rivit.get(i), lines[i].trim(), rivit.get(i).trim());
        }
    }

    @Test
    public void jarjestysOikein() {
        List<String> koodi = koodi("MuuttujatYhdessa.java");
        int lkm = laskeEsiintymat(koodi, "System.out.println.*String.*System.out.println.*Integer.*System.out.println.*Double.*System.out.println.*Boolean.*System.out.println");
        int lkm2 = laskeEsiintymat(koodi, "System.out.println.*String.*System.out.println.*nextInt.*System.out.println.*nextDouble.*System.out.println.*nextBoolean.*System.out.println");
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
