
import fi.helsinki.cs.tmc.edutestutils.MockInOut;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import org.junit.*;
import static org.junit.Assert.*;

public class C_MuistavaTuotevarastoTest {

    String klassName = "MuistavaTuotevarasto";
    Reflex.ClassRef<Object> classRef;

    @Before
    public void setup() {
        classRef = Reflex.reflect(klassName);
    }

    @Test
    @Points("08-03.5")
    public void luokkaOlemassa() {
        assertTrue("Luokan " + s(klassName) + " pitää olla julkinen, eli se tulee määritellä\n"
                + "public class " + klassName + " {...\n}", classRef.isPublic());
    }

    @Test
    @Points("08-03.5")
    public void periiLuokanTuotearasto() {
        Class c = ReflectionUtils.findClass(klassName);

        Class sc = c.getSuperclass();
        assertTrue("Luokan MuistavaTuotevarasto tulee periä luokka Tuotevarasto", sc.getName().equals("Tuotevarasto"));
    }

    @Test
    @Points("08-03.5")
    public void onKolmeparametrinenKonstruktori() throws Throwable {
        newMustavaTuotevarasto("vilja", 10, 5);
    }

    @Test
    @Points("08-03.5")
    public void alkuSaldoOnVarastonAlkusaldona() throws Throwable {
        Varasto v = (Varasto) newMustavaTuotevarasto("vilja", 10, 5);
        Assert.assertEquals("Asetathan varaston alkusaldon kun kutsut MuistavaTuotevarasto-konstruktoria?", 5, v.getSaldo(), 0.01);
        Assert.assertEquals("Asetathan varaston tilavuuden kun kutsut MuistavaTuotevarasto-konstruktoria?", 10, v.getTilavuus(), 0.01);
    }

    @Test
    @Points("08-03.5")
    public void eiYlimaaraisiaOliomuuttujia() {
        saniteettitarkastus(klassName, 1, "muita oliomuuttujia kuin Muutoshistoria:n");
    }

    /*
     *
     */
    @Test
    @Points("08-03.5")
    public void onMetodiHistora() throws Throwable {
        String metodi = "historia";
        String virhe = "tee luokalle MuistavaTuotevarasto metodi public String historia()";

        Object o = newMustavaTuotevarasto("olut", 10, 2);

        assertTrue(virhe,
                classRef.method(o, metodi).returning(String.class).takingNoParams().isPublic());

        String v = "MuistavaTuotevarasto mtv = new MuistavaTuotevarasto(\"olut\", 10, 2);\n"
                + "mtv.historia();\n";

        assertEquals(v, "[2.0]",
                classRef.method(o, metodi).returning(String.class).takingNoParams().withNiceError(v).invoke());

        o = newMustavaTuotevarasto("maito", 100, 99);

        v = "MuistavaTuotevarasto mtv = new MuistavaTuotevarasto(\"maito\", 100, 99);\n"
                + "mtv.historia();\n";

        assertEquals(v, "[99.0]",
                classRef.method(o, metodi).returning(String.class).takingNoParams().withNiceError(v).invoke());

    }

    // ************
    @Test
    @Points("08-03.6")
    public void onMetodiLisaaVarastoon() throws Throwable {
        String metodi = "lisaaVarastoon";
        String virhe = "tee luokalle MuistavaTuotevarasto metodi public void lisaaVarastoon(double maara)";

        Object o = newMustavaTuotevarasto("olut", 10, 2);

        assertTrue(virhe,
                classRef.method(o, metodi).returningVoid().taking(double.class).isPublic());

        String v = "MuistavaTuotevarasto mtv = new MuistavaTuotevarasto(\"olut\", 10, 2);\n"
                + "mtv.lisaaVarastoon(3);\n";

        classRef.method(o, metodi).returningVoid().taking(double.class).withNiceError(v).invoke(3.0);
    }

    @Test
    @Points("08-03.6")
    public void onMetodiOtaVarastosta() throws Throwable {
        String metodi = "otaVarastosta";
        String virhe = "tee luokalle MuistavaTuotevarasto metodi public double otaVarastosta(double maara)";

        Object o = newMustavaTuotevarasto("olut", 10, 2);

        assertTrue(virhe,
                classRef.method(o, metodi).returning(double.class).taking(double.class).isPublic());

        String v = "MuistavaTuotevarasto mtv = new MuistavaTuotevarasto(\"olut\", 10, 2);\n"
                + "mtv.otaVarastosta(1);\n";

        assertEquals(v, 1.0, (double) classRef.method(o, metodi).returning(double.class).taking(double.class).withNiceError(v).invoke(1.0), 0.01);
    }

    @Test
    @Points("08-03.6")
    public void lisaysJaOttoToimivat() throws Throwable {
        Varasto mtv = (Varasto) (newMustavaTuotevarasto("kahvi", 10, 5));

        String koodi = "tarkasta koodi\nmtv = new MuistavaTuotevarasto(\"kahvi\",10,5);\nmtv.lisaaVarastoon(5);\nmtv.getSaldo() ";

        lisaaVarastoon(mtv, 5, koodi);

        assertEquals("kutsuuko MuistavaTuotevarasto:n metodi lisaaVarastoon ylikirjoitettua metodia? \ntarkasta koodi\n"
                + koodi, 10, mtv.getSaldo(), 0.01);

        otaVarastosta(mtv, 7, koodi);
        koodi = "tarkasta koodi\nmtv = new MuistavaTuotevarasto(\"kahvi\",10,5);\nmtv.lisaaVarastoon(5);\nmtv.otaVarastosta(7);\nmtv.getSaldo() ";

        assertEquals("kutsuuko MuistavaTuotevarasto:n metodi otaVarastosta ylikirjoitettua metodia?\ntarkasta koodi\n"
                + koodi, 3, mtv.getSaldo(), 0.01);
    }

    @Test
    @Points("08-03.6")
    public void lisaysJaOttoVaikuttavatHistoriaan1() throws Throwable {
        Varasto mtv = (Varasto) (newMustavaTuotevarasto("kahvi", 10, 5));

        String koodi = "tarkasta koodi\n"
                + "mtv = new MuistavaTuotevarasto(\"kahvi\",10,5);\nmtv.lisaaVarastoon(5);\nmtv.historia() ";

        lisaaVarastoon(mtv, 5, koodi);

        assertEquals("muista ylläpitää muutoshistoriaa metodin lisaaVarastoon kutsun yhteydessä! \ntarkasta koodi\n"
                + koodi, "[5.0, 10.0]", historia(mtv, koodi));

        otaVarastosta(mtv, 7, koodi);

        koodi = "tarkasta koodi\n"
                + "mtv = new MuistavaTuotevarasto(\"kahvi\",10,5);\nmtv.lisaaVarastoon(5);\nmtv.otaVarastosta(7);\nmtv.historia() ";

        assertEquals("muista ylläpitää muutoshistoriaa metodien lisaaVarastoon ja"
                + "otaVarastosta kutsujen yhteydessä! \ntarkasta koodi\n"
                + koodi, "[5.0, 10.0, 3.0]", historia(mtv, koodi));
    }

    @Test
    @Points("08-03.6")
    public void lisaysJaOttoVaikuttavatHistoriaan2() throws Throwable {
        Varasto mtv = (Varasto) (newMustavaTuotevarasto("kahvi", 10, 5));

        String koodi = "tarkasta koodi\n"
                + "mtv = new MuistavaTuotevarasto(\"kahvi\",10,5);\nmtv.lisaaVarastoon(1);\nmtv.lisaaVarastoon(1);\nmtv.lisaaVarastoon(1);\nmtv.lisaaVarastoon(1);\nmtv.historia() ";

        lisaaVarastoon(mtv, 1, koodi);
        lisaaVarastoon(mtv, 1, koodi);
        lisaaVarastoon(mtv, 1, koodi);
        lisaaVarastoon(mtv, 1, koodi);

        assertEquals("muista ylläpitää muutoshistoriaa  metodien lisaaVarastoon ja"
                + "otaVarastosta kutsujen yhteydessä! \ntarkasta koodi\n"
                + koodi, "[5.0, 6.0, 7.0, 8.0, 9.0]", historia(mtv, koodi));
        otaVarastosta(mtv, 3, koodi);
        otaVarastosta(mtv, 3, koodi);
        otaVarastosta(mtv, 3, koodi);

        koodi = "tarkasta koodi\n"
                + "mtv = new MuistavaTuotevarasto(\"kahvi\",10,5);\nmtv.lisaaVarastoon(1);\nmtv.lisaaVarastoon(1);\nmtv.lisaaVarastoon(1);\nmtv.lisaaVarastoon(1);\nmtv.otaVarastosta(3);\nmtv.otaVarastosta(3);\nmtv.otaVarastosta(3);  mtv.historia() ";

        assertEquals("muista ylläpitää muutoshistoriaa metodien lisaaVarastoon ja"
                + "otaVarastosta kutsujen yhteydessä! \ntarkasta koodi\n"
                + koodi, "[5.0, 6.0, 7.0, 8.0, 9.0, 6.0, 3.0, 0.0]", historia(mtv, koodi));
    }

    @Test
    @Points("08-03.6")
    public void otaVarastostaPalauttaaSaadunMaaran() throws Throwable {
        Varasto mtv = (Varasto) (newMustavaTuotevarasto("kahvi", 10, 5));

        String koodi = "Ethän palauta varastosta enempää kuin siellä on? Tarkasta koodi\n"
                + "mtv = new MuistavaTuotevarasto(\"kahvi\",10,5);\nmtv.otaVarastosta(7); ";

        double saatiin = otaVarastosta(mtv, 7, koodi);
        assertEquals(koodi, 5, saatiin, 0.1);
    }

    //*********
    @Test
    @Points("08-03.7")
    public void onMetodiTulostaAnalyysi() throws Throwable {
        String metodi = "tulostaAnalyysi";
        String virhe = "tee luokalle MuistavaTuotevarasto metodi public void tulostaAnalyysi()";

        Object o = newMustavaTuotevarasto("olut", 10, 2);

        assertTrue(virhe,
                classRef.method(o, metodi).returningVoid().takingNoParams().isPublic());

        String v = "MuistavaTuotevarasto mtv = new MuistavaTuotevarasto(\"olut\", 10, 2);\n"
                + "mtv.lisaaVarastoon(5);\n";

        lisaaVarastoon(o, 5, v);

        classRef.method(o, metodi).returningVoid().takingNoParams().withNiceError(v).invoke();

    }

    /*
     *
     */
    @Test
    @Points("08-03.7")
    public void tulostaAnalyysiSisaltaaHalututRivit() throws Throwable {
        MockInOut io = new MockInOut("");
        Object v = newMustavaTuotevarasto("olut", 10, 5);

        String k = "MuistavaTuotevarasto mtv = new MuistavaTuotevarasto(\"olut\", 10, 2);\n"
                + "mtv.tulostaAnalyysi();\n";

        tulostaAnalyysi(v, k);
        String[] rivit = io.getOutput().split("\n");
        String haettava = "Tuote:";
        String rivi = hae(rivit, haettava);
        assertTrue("Metodin tulostaAnalyysi kutsun tulee tulostaa rivi jossa on merkkijono \"" + haettava + "\"", rivi != null);
        haettava = "Historia:";
        rivi = hae(rivit, haettava);
        assertTrue("Metodin tulostaAnalyysi kutsun tulee tulostaa rivi jossa on merkkijono \"" + haettava + "\"", rivi != null);
        haettava = "Suurin tuotemäärä:";
        rivi = hae(rivit, haettava);
        assertTrue("Metodin tulostaAnalyysi kutsun tulee tulostaa rivi jossa on merkkijono \"" + haettava + "\"", rivi != null);
        haettava = "Pienin tuotemäärä:";
        rivi = hae(rivit, haettava);
        assertTrue("Metodin tulostaAnalyysi kutsun tulee tulostaa rivi jossa on merkkijono \"" + haettava + "\"", rivi != null);
        haettava = "Keskiarvo:";
        rivi = hae(rivit, haettava);
        assertTrue("Metodin tulostaAnalyysi kutsun tulee tulostaa rivi jossa on merkkijono \"" + haettava + "\"", rivi != null);
    }

    @Test
    @Points("08-03.7")
    public void tulostaAnalyysiToimii1() throws Throwable {
        MockInOut io = new MockInOut("");
        Object v = newMustavaTuotevarasto("kahvi", 10, 2);

        String k = "Toimiiko tulostaAnalyysi oikein? Koodilla \n"
                + "tv = new MuistavaTuotevarasto(\"kahvi\", 10, 2);\n"
                + "mtv.lisaaVarastoon(4);\nmtv.otaVarastosta(2);\n"
                + "mtv.tulostaRaportti()\n tulostuu rivi \"";

        lisaaVarastoon(v, 4, k);
        otaVarastosta(v, 2, k);

        tulostaAnalyysi(v, k);
        String[] rivit = io.getOutput().split("\n");

        String haettava = "Historia:";
        String rivi = hae(rivit, haettava);
        assertTrue(k + rivi + "\"", rivi.contains("[2.0, 6.0, 4.0]"));

        haettava = "Tuote:";
        rivi = hae(rivit, haettava);
        assertTrue(k + rivi + "\"", rivi.contains("kahvi"));

        haettava = "Suurin tuotemäärä:";
        rivi = hae(rivit, haettava);
        assertTrue(k + rivi + "\"", rivi.contains("6"));

        haettava = "Pienin tuotemäärä:";
        rivi = hae(rivit, haettava);
        assertTrue(k + rivi + "\"", rivi.contains("2"));

        haettava = "Keskiarvo:";
        rivi = hae(rivit, haettava);
        assertTrue(k + rivi + "\"", rivi.contains("4"));

    }

    @Test
    @Points("08-03.7")
    public void tulostaAnalyysiToimii2() throws Throwable {
        MockInOut io = new MockInOut("");
        Object v = newMustavaTuotevarasto("kahvi", 10, 2);
        String k = "Toimiiko tulostaAnalyysi oikein? Koodilla \n"
                + "mtv = new MuistavaTuotevarasto(\"kahvi\", 10, 2);\n"
                + "mtv.lisaaVarastoon(4);\nmtv.otaVarastosta(2);\nmtv.tulostaRaportti()\n"
                + "tulostuu rivi \"";

        lisaaVarastoon(v, 4, k);
        otaVarastosta(v, 2, k);
        lisaaVarastoon(v, 6, k);
        otaVarastosta(v, 2, k);

        tulostaAnalyysi(v, k);
        String[] rivit = io.getOutput().split("\n");

        String haettava = "Historia:";
        String rivi = hae(rivit, haettava);
        assertTrue(k + rivi + "\"", rivi.contains("[2.0, 6.0, 4.0, 10.0, 8.0]"));

        haettava = "Tuote:";
        rivi = hae(rivit, haettava);
        assertTrue(k + rivi + "\"", rivi.contains("kahvi"));

        haettava = "Suurin tuotemäärä:";
        rivi = hae(rivit, haettava);
        assertTrue(k + rivi + "\"", rivi.contains("10"));

        haettava = "Pienin tuotemäärä:";
        rivi = hae(rivit, haettava);
        assertTrue(k + rivi + "\"", rivi.contains("2"));

        haettava = "Keskiarvo:";
        rivi = hae(rivit, haettava);
        assertTrue(k + rivi + "\"", rivi.contains("6"));
    }

    /*
     *
     */
    private Object newMustavaTuotevarasto(String tuotenimi, double tilavuus, double alkusaldo) throws Throwable {
        assertTrue("tee luokalle MuistavaTuotevarasto konstruktori MuistavaTuotevarasto(String tuotenimi, double tilavuus, double alkusaldo)", classRef.constructor().taking(String.class, double.class, double.class).isPublic());

        String v = "\nVirheen aiheutti koodi\n new MuistavaTuotevarasto(\"" + tuotenimi + "\"," + tilavuus + "," + alkusaldo + ");";

        Reflex.MethodRef3<Object, Object, String, Double, Double> ctor = classRef.constructor().taking(String.class, double.class, double.class).withNiceError();
        return ctor.withNiceError(v).invoke(tuotenimi, tilavuus, alkusaldo);

    }

    private String historia(Object o, String v) throws Throwable {
        return classRef.method(o, "historia").returning(String.class).takingNoParams().withNiceError(v).invoke();
    }

    private double otaVarastosta(Object o, double maara, String v) throws Throwable {
        return classRef.method(o, "otaVarastosta").returning(double.class)
                .taking(double.class).withNiceError(v).invoke(maara);
    }

    private void tulostaAnalyysi(Object o, String v) throws Throwable {
        classRef.method(o, "tulostaAnalyysi").returningVoid()
                .takingNoParams().withNiceError(v).invoke();
    }

    private void lisaaVarastoon(Object o, double maara, String v) throws Throwable {
        classRef.method(o, "lisaaVarastoon").returningVoid()
                .taking(double.class).withNiceError(v).invoke(maara);
    }

    private String hae(String[] rivit, String haettava) {
        for (String rivi : rivit) {
            if (rivi.contains(haettava)) {
                return rivi;
            }
        }

        return null;
    }

    /*
     *
     */
    private void saniteettitarkastus(String klassName, int n, String m) throws SecurityException {
        Field[] kentat = ReflectionUtils.findClass(klassName).getDeclaredFields();

        for (Field field : kentat) {
            assertFalse("et tarvitse \"stattisia muuttujia\", poista luokalta " + s(klassName) + " muuttuja " + kentta(field.toString(), s(klassName)), field.toString().contains("static") && !field.toString().contains("final"));
            assertTrue("luokan kaikkien oliomuuttujien näkyvyyden tulee olla private, muuta luokalta " + s(klassName) + " löytyi: " + kentta(field.toString(), klassName), field.toString().contains("private"));
        }

        if (kentat.length > 1) {
            int var = 0;
            for (Field field : kentat) {
                if (!field.toString().contains("final")) {
                    var++;
                }
            }
            assertTrue("et tarvitse " + s(klassName) + "-luokalle " + m + ", poista ylimääräiset", var <= n);
        }
    }

    private String kentta(String toString, String klassName) {
        return toString.replace(klassName + ".", "").replace("java.lang.", "").replace("java.util.", "");
    }

    private String s(String klassName) {
        if (!klassName.contains(".")) {
            return klassName;
        }
        return klassName.substring(klassName.lastIndexOf(".") + 1);
    }
}
