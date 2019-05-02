
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

@Points("01-04")
public class OlipaKerranMaaTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void tulostusOikein() {
        OlipaKerranMaa.main(new String[]{});
        String[] lines = new String[]{"Olipa kerran maa valmistui vuonna 2008. Sarja käsittelee",
            "luontoympäristön suojelemista ja varoittaa maailmanlaajuisesta",
            "ilmastonlämpenemisestä, kasvihuoneilmiöstä, saasteista ja",
            "niin edelleen."};

        List<String> rivit = rivit(io.getSysOut().trim());

        assertEquals("Odotettiin, että tulostuksessa olisi " + lines.length + " riviä. Nyt rivejä oli " + rivit.size() + ".", lines.length, rivit.size());
        for (int i = 0; i < rivit.size(); i++) {
            assertEquals("Rivin " + (i + 1) + " tulostus väärin. Odotettiin merkkijonoa:\n" + lines[i] + "\nMutta tulostus oli:\n" + rivit.get(i), lines[i].trim(), rivit.get(i).trim());
        }
    }

    @Test
    public void systemOutPrintlnKomentojenLukumaara() {
        List<String> koodi = koodi("OlipaKerranMaa.java");
        int esiintymia = laskeEsiintymat(koodi, "System.out.println");
        assertEquals("Odotettiin, että System.out.println-komentoja on 1. Nyt niitä oli " + esiintymia + ".", 1, esiintymia);
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
            while (rivi.contains(haettava)) {
                rivi = rivi.replaceFirst(haettava, "");
                lkm++;
            }
        }

        return lkm;
    }

}
