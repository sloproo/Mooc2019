
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import org.junit.*;
import static org.junit.Assert.*;

@Points("03-30")
public class SanaSanassaTest {

    @Rule
    public MockStdio io = new MockStdio();

    public String sanitize(String s) {
        return s.replaceAll("\r\n", "\n").replaceAll("\r", "\n").replaceAll("\n", " ").replaceAll("  ", " ");
    }

    @Test
    public void eikaiPoikkeusta() throws Exception {
        io.setSysIn("ohjelmointi\nmoi\n");
        try {
            SanaSanassa.main(new String[0]);
        } catch (Exception e) {
            String v = "\n\npaina nappia show backtrace, virheen syy löytyy hieman alempaa kohdasta "
                    + "\"Caused by\"\n"
                    + "klikkaamalla pääset suoraan virheen aiheuttaneelle koodiriville";

            throw new Exception("syötteellä \"ohjelmointi moi\"" + v, e);
        }
    }

    @Test
    public void testi1a() {
        String s1 = "ohjelmointi";
        String s2 = "moi";
        io.setSysIn(s1 + "\n" + s2 + "\n");
        SanaSanassa.main(new String[0]);
        
        String out = io.getSysOut().toLowerCase().replaceAll("'", "").replaceAll("sanan", "").replaceAll("  ", " ");

        String odotettu = loytyy(s1, s2);
        String odottamaton = eiLoydy(s1, s2);
        String odotettuM = loytyyM(s1, s2);
        String odottamatonM = eiLoydyM(s1, s2);

        assertTrue("Syötteellä " + s1 + " " + s2 + " ohjelmasi tulee tulostaa " + odotettuM + ", varmista että tulostusasusi on täsmälleen sama kuin tehtävänannossa", out.contains(odotettu));
        assertFalse("Syötteellä " + s1 + " " + s2 + " ohjelmasi tulee tulostaa " + odotettuM + ", varmista että tulostusasusi on täsmälleen sama kuin tehtävänannossa", out.contains(odottamaton));
    }

    @Test
    public void testi1b() {
        String s1 = "ohjelmointi";
        String s2 = "moido";
        io.setSysIn(s1 + "\n" + s2 + "\n");
        SanaSanassa.main(new String[0]);
        String out = io.getSysOut().toLowerCase().replaceAll("'", "").replaceAll("sanan", "").replaceAll("  ", " ");

        String odotettu = eiLoydy(s1, s2);
        String odottamaton = loytyy(s1, s2);
        String odotettuM = eiLoydyM(s1, s2);
        String odottamatonM = loytyyM(s1, s2);

        assertTrue("Syötteellä " + s1 + " " + s2 + " ohjelmasi tulee tulostaa " + odotettuM + ", varmista että tulostusasusi on täsmälleen sama kuin tehtävänannossa", out.contains(odotettu));
        assertFalse("Syötteellä " + s1 + " " + s2 + " ohjelmasi tulee tulostaa " + odotettuM + ", varmista että tulostusasusi on täsmälleen sama kuin tehtävänannossa", out.contains(odottamaton));
    }

    @Test
    public void testi2a() {
        String s1 = "MOOC-verkkokurssi";
        String s2 = "verkko";
        io.setSysIn(s1 + "\n" + s2 + "\n");
        SanaSanassa.main(new String[0]);
        String out = io.getSysOut().toLowerCase().replaceAll("'", "").replaceAll("sanan", "").replaceAll("  ", " ");
        String odotettu = loytyy(s1, s2).toLowerCase();
        String odottamaton = eiLoydy(s1, s2);
        String odotettuM = loytyyM(s1, s2);
        String odottamatonM = eiLoydyM(s1, s2);

        assertTrue("Syötteellä " + s1 + " " + s2 + " ohjelmasi tulee tulostaa " + odotettuM + ", varmista että tulostusasusi on täsmälleen sama kuin tehtävänannossa", out.contains(odotettu));
        assertFalse("Syötteellä " + s1 + " " + s2 + " ohjelmasi tulee tulostaa " + odotettuM + ", varmista että tulostusasusi on täsmälleen sama kuin tehtävänannossa", out.contains(odottamaton));
    }

    @Test
    public void testi2b() {
        String s1 = "MOOC-verkkokurssi";
        String s2 = "jalka";
        io.setSysIn(s1 + "\n" + s2 + "\n");
        SanaSanassa.main(new String[0]);
        String out = io.getSysOut().toLowerCase().replaceAll("'", "").replaceAll("sanan", "").replaceAll("  ", " ");
        String odotettu = eiLoydy(s1, s2).toLowerCase();;
        String odottamaton = loytyy(s1, s2).toLowerCase();;
        String odotettuM = eiLoydyM(s1, s2);
        String odottamatonM = loytyyM(s1, s2);

        assertTrue("Syötteellä " + s1 + " " + s2 + " ohjelmasi tulee tulostaa " + odotettuM + ", varmista että tulostusasusi on täsmälleen sama kuin tehtävänannossa", out.contains(odotettu));
        assertFalse("Syötteellä " + s1 + " " + s2 + " ohjelmasi tulee tulostaa " + odotettuM + ", varmista että tulostusasusi on täsmälleen sama kuin tehtävänannossa", out.contains(odottamaton));
    }

    @Test
    public void testi2c() {
        String s1 = "MOOC-verkkokurssi";
        String s2 = "sitten";
        io.setSysIn(s1 + "\n" + s2 + "\n");
        SanaSanassa.main(new String[0]);
        String out = io.getSysOut().toLowerCase().replaceAll("'", "").replaceAll("sanan", "").replaceAll("  ", " ");
        String odotettu = eiLoydy(s1, s2).toLowerCase();;
        String odottamaton = loytyy(s1, s2).toLowerCase();;
        String odotettuM = eiLoydyM(s1, s2);
        String odottamatonM = loytyyM(s1, s2);

        assertTrue("Syötteellä " + s1 + " " + s2 + " ohjelmasi tulee tulostaa " + odotettuM + ", varmista että tulostusasusi on täsmälleen sama kuin tehtävänannossa", out.contains(odotettu));
        assertFalse("Syötteellä " + s1 + " " + s2 + " ohjelmasi tulee tulostaa " + odotettuM + ", varmista että tulostusasusi on täsmälleen sama kuin tehtävänannossa", out.contains(odottamaton));
    }

    private String loytyyM(String s1, String s2) {
        return "Sana '" + s2 + "' on sanan'" + s1 + "' osana.";
    }

    private String eiLoydyM(String s1, String s2) {
        return "Sana '" + s2 + "' ei ole sanan '" + s1 + "' osana.";
    }

    private String loytyy(String s1, String s2) {
        return s2 + " on " + s1 + " osana";
    }

    private String eiLoydy(String s1, String s2) {
        return s2 + " ei ole " + s1 + " osana";
    }
}
