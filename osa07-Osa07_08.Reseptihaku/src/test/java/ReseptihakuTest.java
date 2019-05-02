
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import static org.junit.Assert.assertFalse;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Rule;

public class ReseptihakuTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    @Points("07-08.1")
    public void lukeminenJaListaus1() throws Throwable {
        testaa(Arrays.asList("Lettutaikina", "15", "maito", "", "Lihapullat", "10", "jauheliha"), Arrays.asList("listaa"), Arrays.asList("Lettutaikina, keittoaika: 15", "Lihapullat, keittoaika: 10"), Arrays.asList(""));
    }

    @Test
    @Points("07-08.1")
    public void lukeminenJaListaus2() throws Throwable {
        testaa(Arrays.asList("Makkarakeitto", "20", "makkara", "peruna", "vesi", "", "Lihapullat", "10", "jauheliha"), Arrays.asList("listaa"), Arrays.asList("Makkarakeitto, keittoaika: 20", "Lihapullat, keittoaika: 10"), Arrays.asList("Lettutaikina, keittoaika: 15"));
    }

    @Test
    @Points("07-08.1")
    public void lukeminenJaEiListausta() throws Throwable {
        testaa(Arrays.asList("Lettutaikina", "15", "maito", "", "Lihapullat", "10", "jauheliha"), Arrays.asList(""), Arrays.asList(""), Arrays.asList("Lettutaikina, keittoaika: 15", "Lihapullat, keittoaika: 10"));
    }

    @Test
    @Points("07-08.2")
    public void hakuNimenPerusteella1() throws Throwable {
        testaa(Arrays.asList("Lettutaikina", "15", "maito", "", "Lihapullat", "10", "jauheliha"), Arrays.asList("hae nimi", "a"), Arrays.asList("Lettutaikina, keittoaika: 15", "Lihapullat, keittoaika: 10"), Arrays.asList(""));
    }

    @Test
    @Points("07-08.2")
    public void hakuNimenPerusteella2() throws Throwable {
        testaa(Arrays.asList("Makkarakeitto", "20", "makkara", "peruna", "vesi", "", "Lihapullat", "10", "jauheliha"), Arrays.asList("hae nimi", "keitto"), Arrays.asList("Makkarakeitto, keittoaika: 20"), Arrays.asList("Lettutaikina, keittoaika: 15", "Lihapullat, keittoaika: 10"));
    }

    @Test
    @Points("07-08.3")
    public void hakuKeittoajanPerusteella1() throws Throwable {
        testaa(Arrays.asList("Lettutaikina", "15", "maito", "", "Lihapullat", "10", "jauheliha"), Arrays.asList("hae keittoaika", "15"), Arrays.asList("Lettutaikina, keittoaika: 15", "Lihapullat, keittoaika: 10"), Arrays.asList(""));
    }

    @Test
    @Points("07-08.3")
    public void hakuKeittoajanPerusteella2() throws Throwable {
        testaa(Arrays.asList("Makkarakeitto", "20", "makkara", "peruna", "vesi", "", "Lihapullat", "10", "jauheliha"), Arrays.asList("hae keittoaika", "10"), Arrays.asList("Lihapullat, keittoaika: 10"), Arrays.asList("Makkarakeitto, keittoaika: 20"));
    }

    @Test
    @Points("07-08.3")
    public void hakuKeittoajanPerusteella3() throws Throwable {
        testaa(Arrays.asList("Makkarakeitto", "20", "makkara", "peruna", "vesi", "", "Lihapullat", "10", "jauheliha"), Arrays.asList("hae keittoaika", "5"), Arrays.asList(""), Arrays.asList("Makkarakeitto, keittoaika: 20", "Lihapullat, keittoaika: 10"));
    }

    @Test
    @Points("07-08.4")
    public void hakuRaakaaineenPerusteella1() throws Throwable {
        testaa(Arrays.asList("Lettutaikina", "15", "maito", "", "Lihapullat", "10", "jauheliha"), Arrays.asList("hae aine", "aito"), Arrays.asList(""), Arrays.asList("Lettutaikina, keittoaika: 15", "Lihapullat, keittoaika: 10"));
    }

    @Test
    @Points("07-08.4")
    public void hakuRaakaaineenPerusteella2() throws Throwable {
        testaa(Arrays.asList("Lettutaikina", "15", "maito", "", "Lihapullat", "10", "jauheliha"), Arrays.asList("hae aine", "maito"), Arrays.asList("Lettutaikina, keittoaika: 15"), Arrays.asList("Lihapullat, keittoaika: 10"));
    }

    @Test
    @Points("07-08.4")
    public void hakuRaakaaineenPerusteella3() throws Throwable {
        testaa(Arrays.asList("Makkarakeitto", "20", "makkara", "peruna", "vesi", "", "Tofurullat", "75", "tofu", "sipuli", "kurkku", "avokado"), Arrays.asList("hae aine", "kurkku"), Arrays.asList("Tofurullat, keittoaika: 75"), Arrays.asList("Makkarakeitto, keittoaika: 20"));
    }

    public void testaa(List<String> tiedostonSisalto, List<String> komentolista, List<String> odotetutTulosteet, List<String> eiOdotetutTulosteet) {
        String tiedosto = luoTestitiedosto(tiedostonSisalto);

        String komennot = tiedosto + "\n";
        for (String komento : komentolista) {
            komennot += komento + "\n";
        }
        komennot += "lopeta\n";

        io.setSysIn(komennot);
        Reseptihaku.main(new String[]{});

        String tulostus = io.getSysOut();
        for (String odotettu : odotetutTulosteet) {
            if (odotettu.trim().isEmpty()) {
                continue;
            }

            assertTrue("Odotettiin, että tulostuksessa olisi merkkijono " + odotettu + ".\nKun tiedoston sisältö on:\n" + riveittain(tiedostonSisalto) + "\nKokeile ohjelmaa komennoilla:\n" + komennot + ".", tulostus.contains(odotettu));
        }

        for (String eiOdotettu : eiOdotetutTulosteet) {
            if (eiOdotettu.trim().isEmpty()) {
                continue;
            }

            assertFalse("Odotettiin, että tulostuksessa ei olisi merkkijonoa " + eiOdotettu + ".\nKun tiedoston sisältö on:\n" + riveittain(tiedostonSisalto) + "\nKokeile ohjelmaa komennoilla:\n" + komennot + ".", tulostus.contains(eiOdotettu));
        }

        try {
            new File(tiedosto).delete();
        } catch (Throwable t) {
            System.out.println("Testitiedoston " + tiedosto + " poistaminen epäonnistui.");
        }
    }

    private String riveittain(List<String> rivit) {
        String s = "";
        for (String rivi : rivit) {
            s += rivi + "\n";
        }

        return s;
    }

    public String luoTestitiedosto(List<String> sisalto) {
        String tiedostonNimi = "test-" + UUID.randomUUID().toString().substring(0, 6);

        try (PrintWriter pw = new PrintWriter(tiedostonNimi)) {
            for (String rivi : sisalto) {
                pw.println(rivi);
            }
            pw.flush();
        } catch (Exception e) {
            fail("Reseptejä sisältävän testitiedoston luominen epäonnistui.\nKokeile testien ajamista TMC-palvelimella.");

        }

        return tiedostonNimi;
    }
}
