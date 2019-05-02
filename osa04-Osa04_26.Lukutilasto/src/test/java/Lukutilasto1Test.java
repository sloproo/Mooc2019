
import fi.helsinki.cs.tmc.edutestutils.MockInOut;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Random;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class Lukutilasto1Test {
    
    Random seka = new Random();
    String luokanNimi = "Lukutilasto";
    Class laskuriLuokka;
    Reflex.ClassRef<Object> klass;
    String klassName = "Lukutilasto";

    @Before
    public void haeLuokka() {
        klass = Reflex.reflect(klassName);
    }

    @Points("04-26.1")
    @Test
    public void luokkaJulkinen() {
        assertTrue("Luokan " + klassName + " pitää olla julkinen, eli se tulee määritellä\n"
                + "public class " + klassName + " {...\n}", klass.isPublic());
    }

    @Points("04-26.1")
    @Test
    public void testaaKonstruktori() throws Throwable {
        newOlio();
    }

    @Points("04-26.1")
    @Test
    public void onkoMetodiaMetodiLisaaLuku1() throws Throwable {
        String metodi = "lisaaLuku";
        int param = 2;
        hasVoidMethodInt(newOlio(), metodi, param);
    }

    @Points("04-26.1")
    @Test
    public void onkoMetodiaMetodiLisaaLuku() throws Throwable {
        String metodi = "lisaaLuku";
        int param = 2;

        Object olio = newOlio();

        assertTrue("tee luokalle " + klassName + " metodi public void " + metodi + "(int luku) ",
                klass.method(olio, metodi).returningVoid().taking(int.class).isPublic());

        String v = "\nVirheen aiheuttanut koodi "
                + klassName + " t = new " + klassName + "(); t." + metodi + "(" + param + ");";

        klass.method(olio, metodi).returningVoid().taking(int.class).withNiceError(v).invoke(2);
    }

    @Points("04-26.1")
    @Test
    public void onMetodiHaeLukujenMaara() throws Throwable {
        hasMethod0(newOlio(), "haeLukujenMaara", int.class);
    }

    @Points("04-26.1")
    @Test
    public void lukujenLisaysJaMaaraToimii() throws Throwable {
        Object tilasto = newLukutilasto();

        assertEquals("Määrän pitäisi olla alussa 0. Tarkasta koodi \n"
                + "tilasto = new Lukutilasto(); System.out.println( tilasto.haeLukujenMaara()); ", 0, haeLukujenMaara(tilasto));

        lisaaLuku(tilasto, 3);

        assertEquals("Määrän pitäisi kasvaa. Tarkasta koodi \n"
                + "tilasto = new Lukutilasto(); tilasto.lisaaLuku(3); System.out.println( tilasto.haeLukujenMaara()); ", 1, haeLukujenMaara(tilasto));

        lisaaLuku(tilasto, 5);
        lisaaLuku(tilasto, 2);
        lisaaLuku(tilasto, -4);

        assertEquals("Määrän pitäisi kasvaa. Tarkasta koodi \n"
                + "tilasto = new Lukutilasto(); tilasto.lisaaLuku(3); tilasto.lisaaLuku(5); tilasto.lisaaLuku(2); tilasto.lisaaLuku(-4);System.out.println( tilasto.haeLukujenMaara()); ", 4, haeLukujenMaara(tilasto));
    }

     @Points("04-26.1")
    @Test
    public void isoLisaysJaMaaraToimii() throws Throwable {

        for (int i = 0; i < 5; i++) {
            int[] luvut = arvo(10 + seka.nextInt(10));

            Object tilasto = newLukutilasto();

            for (int luku : luvut) {
                lisaaLuku(tilasto, luku);
            }

            assertEquals("Lisättiin luvut " + toString(luvut) + " tilasto.haeLukujenMaara()", luvut.length, haeLukujenMaara(tilasto));
        }
    }

    @Points("04-26.1")
    @Test
    public void eiYlimaaraisiaMuuttujia1() {
        saniteettitarkastus();
    }

    /*
     * osa 2
     */
    @Points("04-26.2")
    @Test
    public void eiYlimaaraisiaMuuttujia2() {
        saniteettitarkastus();
    }

    @Points("04-26.2")
    @Test
    public void onMetodiSumma() throws Throwable {
        hasMethod0(newOlio(), "summa", int.class);
    }

    @Points("04-26.2")
    @Test
    public void summaToimii() throws Throwable {
        Object tilasto = newLukutilasto();

        assertEquals("Summan pitäisi olla alussa 0. Tarkasta koodi \n"
                + "tilasto = new Lukutilasto(); System.out.println( tilasto.summa()); ", 0, summa(tilasto));

        lisaaLuku(tilasto, 3);

        assertEquals("Summan pitäisi kasvaa lisätyllä luvulla. Tarkasta koodi \n"
                + "tilasto = new Lukutilasto(); tilasto.lisaaLuku(3); System.out.println( tilasto.summa()); ", 3, summa(tilasto));

        lisaaLuku(tilasto, 5);
        lisaaLuku(tilasto, 2);

        assertEquals("Summan pitäisi kasvaa lisätyillä. Tarkasta koodi \n"
                + "tilasto = new Lukutilasto(); tilasto.lisaaLuku(3); tilasto.lisaaLuku(5); tilasto.lisaaLuku(2); System.out.println( tilasto.summa()); ", 10, summa(tilasto));


        lisaaLuku(tilasto, -4);

        assertEquals("Summan pitäisi kasvaa lisätyillä. Tarkasta koodi \n"
                + "tilasto = new Lukutilasto(); tilasto.lisaaLuku(3); tilasto.lisaaLuku(5); tilasto.lisaaLuku(2); tilasto.lisaaLuku(-4) System.out.println( tilasto.summa()); ", 6, summa(tilasto));
    }

    @Points("04-26.2")
    @Test
    public void isoSummaToimii() throws Throwable {

        for (int i = 0; i < 5; i++) {
            int[] luvut = arvo(10 + seka.nextInt(10));

            Object tilasto = newLukutilasto();

            int summa = 0;
            for (int luku : luvut) {
                lisaaLuku(tilasto, luku);
                summa += luku;
            }

            assertEquals("Lisättiin luvut " + toString(luvut) + " tilasto.summa()", summa, summa(tilasto));
        }
    }

    @Points("04-26.2")
    @Test
    public void onMetodiKeskiarvo() throws Throwable {
        hasMethod0(newOlio(),"keskiarvo", double.class, 
                "HUOM: jos lukutilastossa ei ole yhtään lukua, palauta keskiarvoksi suoraan 0");
    }

    @Points("04-26.2")
    @Test
    public void keskiarvoToimii() throws Throwable {
        Object tilasto = newLukutilasto();

        try {
            assertEquals("Keskiarvon pitäisi olla alussa 0. Ettet vaan jaa nollalla? Tarkasta koodi \n"
                    + "tilasto = new Lukutilasto(); System.out.println( tilasto.keskiarvo()); ", 0, keskiarvo(tilasto), 0.01);
        } catch (Exception e) {
            fail("Keskiarvon pitäisi olla 0 jos yhtään lukua ei ole lisätty. Ettet vaan jaa nollalla? Tarkasta koodi "
                    + "tilasto = new Lukutilasto(); System.out.println( tilasto.keskiarvo()); ");
        }

        lisaaLuku(tilasto, 3);

        assertEquals("Keskiarvoa ei lasketa oikein. Tarkasta koodi \n"
                + "tilasto = new Lukutilasto(); tilasto.keskiarvo(); tilasto.lisaaLuku(3); System.out.println( tilasto.keskiarvo()); ", 3, keskiarvo(tilasto), 0.01);

        lisaaLuku(tilasto, 5);
        lisaaLuku(tilasto, 2);

        assertEquals("Keskiarvoa ei lasketa oikein. Tarkasta koodi \n"
                + "tilasto = new Lukutilasto(); tilasto.keskiarvo(); tilasto.lisaaLuku(3); tilasto.lisaaLuku(5); tilasto.lisaaLuku(2); System.out.println( tilasto.keskiarvo()); ", 3.333, keskiarvo(tilasto), 0.01);


        lisaaLuku(tilasto, -4);

        assertEquals("Keskiarvoa ei lasketa oikein. Tarkasta koodi \n"
                + "tilasto = new Lukutilasto(); tilasto.keskiarvo(); tilasto.lisaaLuku(3); tilasto.lisaaLuku(5); tilasto.lisaaLuku(2); tilasto.lisaaLuku(-4) System.out.println( tilasto.keskiarvo()); ", 1.5, keskiarvo(tilasto), 0.01);
    }

    @Points("04-26.2")
    @Test
    public void isoKeskiarvoToimii() throws Throwable {

        for (int i = 0; i < 5; i++) {
            int[] luvut = arvo(10 + seka.nextInt(10));

            Object tilasto = newLukutilasto();

            double summa = 0;
            for (int luku : luvut) {
                lisaaLuku(tilasto, luku);
                summa += luku;
            }
            double ka = summa / luvut.length;

            assertEquals("Lisättiin luvut " + toString(luvut) + " tilasto.keskiarvo()", ka, keskiarvo(tilasto), 0.01);
        }
    }
    
    /*
     * osa 3
     */
    @Points("04-26.3")
    @Test
    public void kayttajanSyotteidenSumma() throws Exception {
        MockInOut mio = new MockInOut("2\n-1\n");

        try {
            Paaohjelma.main(new String[0]);
        } catch (Exception e) {
            fail("Ohjelman tulee lopettaa syötteiden lukeminen kun -1 on syötetty");
        }
        String[] rivit = mio.getOutput().split("\n");
        assertTrue("Pääohjelmasi ei tulosta mitään", rivit.length > 0);
        assertTrue("Pääohjelman tulee tulostaa alussa \"Anna lukuja:\"", rivit[0].contains("nna lukuja"));
        String summaRivi = hae(rivit, "umma");
        assertTrue("Ohjelmasi pitää olla tulostusrivi muotoa \"Summa: 10\" missä 10:n tilalla laskettu summa", summaRivi != null);

        assertTrue("syötteellä 2 -1 ohjelman pitäisi tulostaa Summa: 2, tulostui: " + summaRivi, summaRivi.contains("2"));
    }

    @Points("04-26.3")
    @Test
    public void kayttajanSyotteidenSumma2() {
        MockInOut mio = new MockInOut("2\n4\n1\n7\n-1\n");
        try {
            Paaohjelma.main(new String[0]);
        } catch (Exception e) {
            fail("Ohjelman tulee lopettaa syötteiden lukeminen kun -1 on syötetty");
        }
        String[] rivit = mio.getOutput().split("\n");
        String summaRivi = hae(rivit, "umma");

        assertTrue("syötteellä 2 4 1 7 -1 ohjelman pitäisi tulostaa Summa: 14, tulostui: " + summaRivi, summaRivi.contains("14"));
    }

    @Points("04-26.4")
    @Test
    public void parillisetJaParittomat() {
        MockInOut mio = new MockInOut("2\n4\n1\n6\n-1\n");
        try {
            Paaohjelma.main(new String[0]);
        } catch (Exception e) {
            fail("Ohjelman tulee lopettaa syötteiden lukeminen kun -1 on syötetty");
        }
        String[] rivit = mio.getOutput().split("\n");
        String summaRivi = hae(rivit, "umma");

        assertTrue("tarkasta että ohjelmassasi on rivi jossa tulostetaan \"Summa \"", summaRivi != null);
        assertTrue("syötteellä 2 4 1 6 -1 ohjelman pitäisi tulostaa Summa: 13, tulostui: " + summaRivi, summaRivi.contains("13"));

        String parillisetRivi = hae(rivit, "llist");
        assertTrue("tarkasta että ohjelmassasi on rivi jossa tulostetaan \"Parillisten summa \"", parillisetRivi != null);
        assertTrue("Ohjelmasi pitää olla tulostusrivi muotoa \"Parillisten summa: 10\" missä 10: tilalla siis parillisten summa ", parillisetRivi != null);
        assertTrue("syötteellä 2 4 1 6 -1 ohjelman pitäisi tulostaa Parillisten summa: 12, tulostui: " + parillisetRivi, parillisetRivi.contains("12"));

        String parittomatRivi = hae(rivit, "ttomi");
        assertTrue("tarkasta että ohjelmassasi on rivi jossa tulostetaan \"Parittomien summa \"", parittomatRivi != null);
        assertTrue("syötteellä 2 4 1 6 -1 ohjelman pitäisi tulostaa Parittomien summa 1, tulostui: " + parittomatRivi, parittomatRivi.contains("1"));
        assertTrue("Ohjelmasi pitää olla tulostusrivi muotoa \"Parittomien summa: 10\" missä 10: tilalla siis parittomien summa ", parittomatRivi != null);
    }
    
    
    /*
     * 
     */
    
    private Object newLukutilasto() {
        try {
            laskuriLuokka = ReflectionUtils.findClass(luokanNimi);
            return ReflectionUtils.invokeConstructor(laskuriLuokka.getConstructor());
        } catch (Throwable t) {
            fail("tarkista että seuraava onnistuu pääohjelmassa:  Lukutilasto tilasto = new Lukutilasto();");
        }
        return null;
    }

    private void lisaaLukuMock(Object olio, int luku) throws Throwable {
        Method metodi = ReflectionUtils.requireMethod(olio.getClass(), "lisaaLuku", int.class);
        ReflectionUtils.invokeMethod(void.class, metodi, olio, luku);
    }

    private void lisaaLuku(Object olio, int luku) throws Throwable {
        Method metodi = ReflectionUtils.requireMethod(laskuriLuokka, "lisaaLuku", int.class);
        ReflectionUtils.invokeMethod(void.class, metodi, olio, luku);
    }

    private double keskiarvo(Object olio) throws Throwable {
        Method metodi = ReflectionUtils.requireMethod(laskuriLuokka, "keskiarvo");
        return ReflectionUtils.invokeMethod(double.class, metodi, olio);
    }

    private int summa(Object olio) throws Throwable {
        Method metodi = ReflectionUtils.requireMethod(laskuriLuokka, "summa");
        return ReflectionUtils.invokeMethod(int.class, metodi, olio);
    }

    private int summaMock(Object olio) throws Throwable {
        Method metodi = ReflectionUtils.requireMethod(olio.getClass(), "summa");
        return ReflectionUtils.invokeMethod(int.class, metodi, olio);
    }

    private int haeLukujenMaara(Object olio) throws Throwable {
        Method metodi = ReflectionUtils.requireMethod(laskuriLuokka, "haeLukujenMaara");
        return ReflectionUtils.invokeMethod(int.class, metodi, olio);
    }

    private String toString(Object kortti) throws Throwable {
        Method toString = ReflectionUtils.requireMethod(laskuriLuokka, "toString");
        return ReflectionUtils.invokeMethod(String.class, toString, kortti);
    }

    private void saniteettitarkastus() throws SecurityException {
        Field[] kentat = ReflectionUtils.findClass(luokanNimi).getDeclaredFields();

        for (Field field : kentat) {
            assertFalse("et tarvitse \"stattisia muuttujia\", poista " + kentta(field.toString()), field.toString().contains("static") && !field.toString().contains("final"));
            assertTrue("luokan kaikkien oliomuuttujien näkyvyyden tulee olla private, muuta " + kentta(field.toString()), field.toString().contains("private"));
        }

        if (kentat.length > 1) {
            int var = 0;
            for (Field field : kentat) {
                if (!field.toString().contains("final")) {
                    var++;
                }
            }
            assertTrue("et tarvitse " + luokanNimi + "-luokalle kuin lukujen määrän ja summan muistavat oliomuuttujat (keskiarvon voit laskea niiden avulla), poista ylimääräiset", var < 3);
        }
    }

    private String toString(int[] luvut) {
        String mj = "";
        for (int luku : luvut) {
            mj += luku + " ";
        }
        return mj;
    }

    private String enter(int[] luvut) {
        String mj = "";
        for (int luku : luvut) {
            mj += luku + "\n";
        }
        return mj + "-1\n";
    }

    private int[] arvo(int lkm) {
        int[] luvut = new int[lkm];

        for (int i = 0; i < luvut.length; i++) {
            luvut[i] = seka.nextInt(30);
        }

        return luvut;
    }

    private String hae(String[] rivit, String sana) {
        for (String rivi : rivit) {
            if (rivi.contains(sana)) {
                return rivi;
            }
        }

        return null;
    }

    private Object newOlio() throws Throwable {
        Reflex.MethodRef0<Object, Object> ctor = klass.constructor().takingNoParams().withNiceError();
        assertTrue("Määrittele luokalle " + klassName + " julkinen konstruktori: public " + klassName + "()", ctor.isPublic());
        return ctor.invoke();
    }

    private void hasMethod0(Object olio, String name, Class<?> returns) throws Throwable {
        hasMethod0(olio, name, returns, "");
    }

    private void hasMethod0(Object olio, String name, Class<?> returns, String msg) throws Throwable {
        String paluu = returns.toString();
        String muuttuja = ("" + klassName.charAt(0)).toLowerCase();

        assertTrue("tee luokalle " + klassName + " metodi public " + paluu + " " + name + "()",
                klass.method(olio, name).returning(returns).takingNoParams().isPublic());

        String v = "\nVirheen aiheuttanut koodi "
                + klassName + " " + muuttuja + " = new " + klassName + "(); " + muuttuja + "." + name + "();";

        if (!msg.isEmpty()) {
            msg = "\n" + msg;
        }

        klass.method(olio, name).returning(returns).takingNoParams().withNiceError(v + msg).invoke();
    }

    private void hasVoidMethodInt(Object olio, String name, int v1) throws Throwable {
        String muuttuja = ("" + klassName.charAt(0)).toLowerCase();

        assertTrue("tee luokalle " + klassName + " metodi public void " + name + "(int luku) ",
                klass.method(olio, name).returningVoid().taking(int.class).isPublic());

        String v = "\nVirheen aiheuttanut koodi "
                + klassName + " " + muuttuja + " = new " + klassName + "(); " + muuttuja + "." + name + "(" + v1 + ");";

        klass.method(olio, name).returningVoid().taking(int.class).withNiceError(v).invoke(v1);

    }

    private String kentta(String toString) {
        return toString.replace(klassName + ".", "");
    }
}
