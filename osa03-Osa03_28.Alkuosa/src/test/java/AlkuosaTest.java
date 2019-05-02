
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import org.junit.*;
import static org.junit.Assert.*;

@Points("03-28")
public class AlkuosaTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void eikaiPoikkeusta() throws Exception {
        io.setSysIn("Java\n3\n");
        try {
            Alkuosa.main(new String[0]);
        } catch (Exception e) {
            String v = "\n\npaina nappia show backtrace, virheen syy löytyy hieman alempaa kohdasta "
                    + "\"Caused by\"\n"
                    + "klikkaamalla pääset suoraan virheen aiheuttaneelle koodiriville";

            throw new Exception("syötteellä \"Java 3\"" + v, e);
        }
    }
    
    public String sanitize(String s) {
        return s.replaceAll("\r\n", "\n").replaceAll("\r", "\n").replaceAll("\n", " ").replaceAll("  ", " ").replaceAll(" ", "");
    }

    @Test
    public void testi1() {
        io.setSysIn("Java\n3\n");
        Alkuosa.main(new String[0]);
        String out = io.getSysOut().replaceAll(" ", "");

        assertTrue("ohjelmasi tulee tulostaa lopuksi \"Tulos: \"", out.contains("Tulos:"));

        assertTrue("Syötteellä Java 3 ohjelmasi tulee tulostaa lopuksi \"Tulos: Jav\"", out.contains("Tulos:Jav"));
        assertFalse("Syötteellä Java 3 ohjelmasi tulee tulostaa lopuksi \"Tulos: Jav\"", out.contains("Tulos:Java"));
    }
 
    @Test
    public void testi2() {
        io.setSysIn("Ohjelmointi\n5\n");
        Alkuosa.main(new String[0]);
        String out = io.getSysOut().replaceAll(" ", "");

        assertTrue("ohjelmasi tulee tulostaa lopuksi \"Tulos: \"", out.contains("Tulos:"));

        assertTrue("Syötteellä Ohjelmointi 5 ohjelmasi tulee tulostaa lopuksi \"Tulos: Ohjel\"", out.contains("Tulos:Ohjel"));
        assertFalse("Syötteellä Ohjelmointi 5 ohjelmasi tulee tulostaa lopuksi \"Tulos: Ohjel\"", out.contains("Tulos:Ohjelm"));
    }    
    
    
    @Test
    public void testi3() {
        io.setSysIn("Tietokoneohjelmistosuunnittelija\n17\n");
        Alkuosa.main(new String[0]);
        String out = io.getSysOut().replaceAll(" ", "");

        assertTrue("ohjelmasi tulee tulostaa lopuksi \"Tulos: \"", out.contains("Tulos:"));

        assertTrue("Syötteellä Tietokoneohjelmistosuunnitelija 17 ohjelmasi tulee tulostaa lopuksi \"Tulos: Tietokoneohjelmis\"", out.contains("Tulos:Tietokoneohjelmis"));
        assertFalse("Syötteellä Tietokoneohjelmistosuunnittelija 17 ohjelmasi tulee tulostaa lopuksi \"Tulos: Tietokoneohjelmis\"", out.contains("Tulos:Tietokoneohjelmist"));
    }      
}

