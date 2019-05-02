
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.nio.file.Paths;
import java.util.Scanner;
import org.junit.*;
import static org.junit.Assert.*;

public class SaannollisetLausekkeetTest {

    String klassName = "Tarkistin";
    Reflex.ClassRef<Object> klass;

    @Before
    public void setUp() {
        klass = Reflex.reflect(klassName);
    }

    @Points("09-15.1")
    @Test
    public void onMetodiOnViikonpaiva() {
        String metodi = "onViikonpaiva";
        assertTrue("tee luokkaan Tarkistin metodi public boolean onViikonpaiva(String merkkijono)", klass.method(metodi)
                .returning(boolean.class).taking(String.class).isPublic());
    }

    @Points("09-15.1")
    @Test
    public void eiKiellettyjaKomentoja() {
        noForbiddens();
    }

    @Points("09-15.1")
    @Test
    public void onViikonpaivaHyvaksyy() throws Throwable {
        String[] mj = {"ma", "ti", "ke", "to", "pe", "la", "su"};

        for (String pv : mj) {
            String v = "tarkasta pääohjelmassa koodi: "
                    + "new Tarkistin().onViikonpaiva(\"" + pv + "\")\n";
            assertEquals(v, true, onViikonpaiva(pv, v));
        }

    }

    @Points("09-15.1")
    @Test
    public void onViikonpaivaHylkaa() throws Throwable {
        String[] mj = {"m", "maa", "maanantai", "", "titi", "arto", "koe", "ma "};

        for (String pv : mj) {
            String v = "tarkasta koodi: new Tarkistin().onViikonpaiva(\"" + pv + "\")\n";
            assertEquals(v, false, onViikonpaiva(pv, v));
        }
    }

    @Points("09-15.2")
    @Test
    public void onMetodikaikkiVokaaleja() {
        String virhe = "tee luokkaan Tarkistin metodi public boolean kaikkiVokaaleja(String merkkijono)";
        String metodi = "kaikkiVokaaleja";
        assertTrue(virhe, klass.method(metodi)
                .returning(boolean.class).taking(String.class).isPublic());
    }

    @Points("09-15.2")
    @Test
    public void hyvaksyyVokaaleistaKoostuvat() throws Throwable {
        String[] mj = {"a", "aeiouäö", "aaa", "uiuiui", "uaa", "aaai", "ai"};

        for (String pv : mj) {
            String v = "tarkasta koodi: new Tarkistin().kaikkiVokaaleja(\"" + pv + "\")\n";
            assertEquals(v, true, kaikkiVokaaleja(pv, v));
        }

    }

    @Points("09-15.2")
    @Test
    public void hylkaaJosEiVokaaleitaJoukossa() throws Throwable {
        String[] mj = {"fågel", "aaaab", "waeiou", "x", "aaaaaaqaaaaaaaaa", "ala"};

        for (String pv : mj) {
            String v = "tarkasta koodi: new Tarkistin().kaikkiVokaaleja(\"" + pv + "\")\n";
            assertEquals(v, false, kaikkiVokaaleja(pv, v));
        }
    }

    @Points("09-15.2")
    @Test
    public void eiKiellettyjaKomentoja2() {
        noForbiddens();
    }

    @Points("09-15.3")
    @Test
    public void onMetodiKellonaika() {
        String virhe = "tee luokkaan Tarkistin metodi public boolean kellonaika(String merkkijono)";
        String metodi = "kellonaika";
        assertTrue(virhe, klass.method(metodi)
                .returning(boolean.class).taking(String.class).isPublic());
    }

    private boolean kellonaika(String mj, String v) throws Throwable {
        Object t = Reflex.reflect(klassName).constructor().takingNoParams().invoke();
        String metodi = "kellonaika";
        return klass.method(metodi)
                .returning(boolean.class).taking(String.class).withNiceError(v).invokeOn(t, mj);
    }

    private boolean onViikonpaiva(String mj, String v) throws Throwable {
        Object t = Reflex.reflect(klassName).constructor().takingNoParams().invoke();
        String metodi = "onViikonpaiva";
        return klass.method(metodi)
                .returning(boolean.class).taking(String.class).withNiceError(v).invokeOn(t, mj);
    }

    @Points("09-15.3")
    @Test
    public void kellonaikaHyvaksyyOikeat() throws Throwable {
        String[] mj = {"20:00:00", "11:24:00", "04:59:31", "14:41:16", "23:32:23", "20:00:59"};

        for (String pv : mj) {
            String v = "tarkasta koodi: new Tarkistin().kellonaika(\"" + pv + "\")\n";
            assertEquals(v, true, kellonaika(pv, v));
        }

    }

    @Points("09-15.3")
    @Test
    public void kellonaikaHylkaaVaarat() throws Throwable {
        String[] mj = {"a", "aaaaaaa", "3:59:31", "24:41:16", "23:61:23", "20:00:79",
            "13:9:31", "21:41:6", "23,61:23", "20:00;79"};

        for (String pv : mj) {
            String v = "tarkasta koodi: new Tarkistin().kellonaika(\"" + pv + "\")\n";
            assertEquals(v, false, kellonaika(pv, v));
        }

    }

    private boolean kaikkiVokaaleja(String m, String v) throws Throwable {
        String metodi = "kaikkiVokaaleja";

        Object t = Reflex.reflect(klassName).constructor().takingNoParams().invoke();

        return klass.method(metodi)
                .returning(boolean.class).taking(String.class).withNiceError(v).invokeOn(t, m);
    }

    private void noForbiddens() {
        try {
            Scanner lukija = new Scanner(Paths.get("src", "main", "java", "Tarkistin.java").toFile());
            int mainissa = 0;
            while (lukija.hasNext()) {

                String virhe = "Koska harjoittelemme String.match-komennon käyttöä, älä käytä komentoa ";

                String rivi = lukija.nextLine();

                if (rivi.contains("void main(") || rivi.contains("boolean kellonaika(")) {
                    mainissa++;
                } else if (mainissa > 0) {

                    if (rivi.contains("{") && !rivi.contains("}")) {
                        mainissa++;
                    }

                    if (rivi.contains("}") && !rivi.contains("{")) {
                        mainissa--;
                    }
                    continue;
                }

                if (mainissa > 0) {
                    continue;
                }

                String f = "equals";
                if (rivi.contains(f)) {
                    fail(virhe + f + " ongelma rivillä " + rivi);
                }

                f = "charAt";
                if (rivi.contains(f)) {
                    fail(virhe + f + " ongelma rivillä " + rivi);
                }

                f = "indexOf";
                if (rivi.contains(f)) {
                    fail(virhe + f + " ongelma rivillä " + rivi);
                }

                f = "contains";
                if (rivi.contains(f)) {
                    fail(virhe + f + " ongelma rivillä " + rivi);
                }

                f = "substring";
                if (rivi.contains(f)) {
                    fail(virhe + f + " ongelma rivillä " + rivi);
                }

            }
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
