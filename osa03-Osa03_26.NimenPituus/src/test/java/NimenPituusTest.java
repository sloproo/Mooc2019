
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import org.junit.*;
import static org.junit.Assert.*;

@Points("03-26")
public class NimenPituusTest {

    @Rule
    public MockStdio io = new MockStdio();
    Method metodi;
    String metodinNimi = "laskeKirjaimet";

    @Test
    public void metodiOlemassa() {
        String virhe = "ohjelmassasi tulee olla metodi public static int " + metodinNimi + "(String merkkijono)";

        try {
            metodi = ReflectionUtils.requireMethod(NimenPituus.class, metodinNimi, String.class);
        } catch (Throwable e) {
            fail(virhe);
        }

        assertTrue(virhe, metodi.toString().contains("static"));
        assertTrue(virhe, int.class == metodi.getReturnType());
        assertTrue(virhe, metodi.toString().contains("public"));
        //assertTrue(virhe, metodi.isAccessible());2
    }

    @Test
    public void testi1() throws Throwable {
        String syote = "koe";
        int tulos = kutsuWrap(syote, "komento nimenPituus(\"" + syote + "\");\n\n"
                + "paina nappia show backtrace, virheen syy löytyy hieman alempaa kohdasta "
                + "\"Caused by\"\n"
                + "klikkaamalla pääset suoraan virheen aiheuttaneelle koodiriville");
        assertEquals(metodinNimi + " parametrilla " + syote, syote.length(), tulos);
    }

    @Test
    public void testi2() throws Throwable {
        String syote = "ohjelmointi";
        int tulos = kutsuWrap(syote, "komento nimenPituus(\"" + syote + "\");\n\n"
                + "paina nappia show backtrace, virheen syy löytyy hieman alempaa kohdasta "
                + "\"Caused by\"\n"
                + "klikkaamalla pääset suoraan virheen aiheuttaneelle koodiriville");
        assertEquals(metodinNimi + " parametrilla " + syote, syote.length(), tulos);
    }

    @Test
    public void testi3() throws Throwable {
        String syote = "mooc-verkkokurssi";
        int tulos = kutsuWrap(syote, "komento nimenPituus(\"" + syote + "\");\n\n"
                + "paina nappia show backtrace, virheen syy löytyy hieman alempaa kohdasta "
                + "\"Caused by\"\n"
                + "klikkaamalla pääset suoraan virheen aiheuttaneelle koodiriville");
        assertEquals(metodinNimi + " parametrilla " + syote, syote.length(), tulos);
    }

    @Test
    public void mainToimii() {
        io.setSysIn("Pekka\n");
        NimenPituus.main(new String[0]);
        assertTrue("Käyttäjän syötteellä \"Pekka\" ohjelmasi pitäisi tulostaa 5", io.getSysOut().contains("5"));
    }

    private int kutsuWrap(String syote, String viesti) throws Throwable {
        try {
            metodi = ReflectionUtils.requireMethod(NimenPituus.class, metodinNimi, String.class);
            return ReflectionUtils.invokeMethod(int.class, metodi, null, syote);
        } catch (Throwable t) {
            throw new Exception(viesti, t);
        }

    }

}
