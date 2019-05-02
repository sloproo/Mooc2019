
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import java.util.ArrayList;
import org.junit.*;
import static org.junit.Assert.*;

public class MaksukorttiTest {

    @Rule
    public MockStdio io = new MockStdio();
    Reflex.ClassRef<Object> klass;
    String klassName = "Maksukortti";
    String luokanNimi = "Maksukortti";
    Class korttiLuokka;

    static final double EDULLINEN = 2.6;
    static final double MAUKAS = 4.6;

    @Before
    public void haeLuokka() {
        klass = Reflex.reflect(klassName);
    }

    @Points("04-24.1")
    @Test
    public void luokkaJulkinen() {
        assertTrue("Luokan " + klassName + " pitää olla julkinen, eli se tulee määritellä\npublic class " + klassName + " {...\n}", klass.isPublic());
    }

    @Points("04-24.1")
    @Test
    public void testaaKonstruktori() throws Throwable {
        Reflex.MethodRef1<Object, Object, Double> ctor = klass.constructor().taking(double.class).withNiceError();
        assertTrue("Määrittele luokalle " + klassName + " julkinen konstruktori: public " + klassName + "(double alkusaldo)", ctor.isPublic());
        ctor.invoke(4.0);
    }

    @Points("04-24.1")
    @Test
    public void toStringAlussaOikein() throws Throwable {
        double summa = 10;
        Object kortti = newKortti(summa);
        String vastaus = toString(kortti);

        String odotettu = "Kortilla on rahaa " + summa + " euroa";
        assertFalse("Tee " + luokanNimi + "-luokalle metodi public String toString() tehtävänannon ohjeen mukaan", vastaus.contains("@"));
        assertEquals("luotiin kortti = new " + luokanNimi + "(" + summa + "); metodi toString ei toimi oikein:", odotettu.toLowerCase(), vastaus.toLowerCase());
    }

    @Points("04-24.1")
    @Test
    public void toinenkinAlkusummaToimii() throws Throwable {
        double summa = 25;
        Object kortti = newKortti(summa);
        String vastaus = toString(kortti);
        String odotettu = "Kortilla on rahaa " + summa + " euroa";

        assertEquals("luotiin kortti = new " + luokanNimi + "(" + summa + "); metodi toString ei toimi oikein:", odotettu.toLowerCase(), vastaus.toLowerCase());
    }

    @Points("04-24.1")
    @Test
    public void eiYlimaaraisiaMuuttujia1() {
        saniteettitarkastus();
    }

    /*
     * osa2
     */
    @Points("04-24.2")
    @Test
    public void eiYlimaaraisiaMuuttujia2() {
        saniteettitarkastus();
    }

    @Points("04-24.2")
    @Test
    public void onkoMetodiaSyoEdullisesti() throws Throwable {
        String metodi = "syoEdullisesti";

        //Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);
        Object olio = newKortti(4.0);

        assertTrue("tee luokalle " + klassName + " metodi public void " + metodi + "() ", klass.method(olio, metodi)
                .returningVoid().takingNoParams().isPublic());

        String v = "\nVirheen aiheuttanut koodi " + luokanNimi + " lk = new " + luokanNimi + "(4.0); lk.syoEdullisesti()";

        klass.method(olio, metodi)
                .returningVoid().takingNoParams().withNiceError(v).invoke();

    }

    @Points("04-24.2")
    @Test
    public void syoEdullisestiVahentaaSaldoa() throws Throwable {
        double summa = 6;
        Object kortti = newKortti(summa);
        syo("Edullisesti", kortti);

        String vastaus = toString(kortti);
        String odotettu = "Kortilla on rahaa " + (summa - EDULLINEN) + " euroa";

        assertEquals("metodi syoEdullisesti ei taida vähentää oikein arvoa kortilta. \n"
                + "Tarkasta että seuraava toimii: kortti = new " + luokanNimi + "(6); kortti.syoEdullisesti(); System.out.println(kortti);\n",
                odotettu.toLowerCase(), vastaus.toLowerCase());

        syo("Edullisesti", kortti);

        vastaus = toString(kortti);
        odotettu = "Kortilla on rahaa " + (summa - 2 * EDULLINEN) + " euroa";

        assertEquals("metodi syoEdullisesti ei taida vähentää oikein arvoa kortilta. \n"
                + "Tarkasta että seuraava toimii: kortti = new " + luokanNimi + "(6); kortti.syoEdullisesti(); kortti.syoEdullisesti(); System.out.println(kortti);\n",
                odotettu.toLowerCase(), vastaus.toLowerCase());
    }

    @Points("04-24.2")
    @Test
    public void onkoMetodiaSyoMaukkaasti() throws Throwable {
        String metodi = "syoMaukkaasti";

        //Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);
        Object olio = newKortti(4.0);

        assertTrue("tee luokalle " + klassName + " metodi public void " + metodi + "() ", klass.method(olio, metodi)
                .returningVoid().takingNoParams().isPublic());

        String v = "\nVirheen aiheuttanut koodi " + luokanNimi + " lk = new " + luokanNimi + "(4.0); lk.syoMaukkaasti()";

        klass.method(olio, metodi)
                .returningVoid().takingNoParams().withNiceError(v).invoke();

    }

    @Points("04-24.2")
    @Test
    public void syoMaukkaastiVahentaaSaldoa() throws Throwable {
        double summa = 10;
        Object kortti = newKortti(summa);
        syo("Maukkaasti", kortti);

        String vastaus = toString(kortti);
        String odotettu = "Kortilla on rahaa " + (summa - MAUKAS) + " euroa";

        assertEquals("metodi syoMaukkaasti ei taida vähentää oikein arvoa kortilta. \n"
                + "Tarkasta että seuraava toimii: kortti = new " + luokanNimi + "(10); kortti.syoMaukkaasti(); System.out.println(kortti);\n", odotettu.toLowerCase(), vastaus.toLowerCase());

        syo("Maukkaasti", kortti);

        vastaus = toString(kortti);
        odotettu = "Kortilla on rahaa " + (summa - 2 * MAUKAS) + " euroa";

        assertEquals("metodi syoMaukkaasti ei taida vähentää oikein arvoa kortilta. \n"
                + "Tarkasta että seuraava toimii: kortti = new " + luokanNimi + "(10); kortti.syoMaukkaasti(); kortti.syoEdullisesti(); System.out.println(kortti);\n", odotettu.toLowerCase(), vastaus.toLowerCase());
    }

    /*
     * osa 3
     */
    @Points("04-24.3")
    @Test
    public void eiYlimaaraisiaMuuttujia3() {
        saniteettitarkastus();
    }

    @Points("04-24.3")
    @Test
    public void syoEdullisestiEiVieNegatiiviselle() throws Throwable {
        double summa = 2;
        Object kortti = newKortti(summa);
        syo("Edullisesti", kortti);

        String vastaus = toString(kortti);
        String odotettu = "Kortilla on rahaa " + (summa) + " euroa";

        assertEquals("Saldo ei saisi mennä negatiiviseksi jos edulliseen lounaaseen ei ole varaa. Tarkasta että seuraava toimii: \n"
                + "kortti = new " + luokanNimi + "(2); kortti.syoEdullisesti(); kortti.syoEdullisesti(); System.out.println(kortti);\n", odotettu.toLowerCase(), vastaus.toLowerCase());
    }

    @Points("04-24.3")
    @Test
    public void syoMaukkaastiEiVieNegatiiviselle() throws Throwable {
        double summa = 7;
        Object kortti = newKortti(summa);

        syo("Maukkaasti", kortti);
        syo("Maukkaasti", kortti);

        String vastaus = toString(kortti);
        String odotettu = "Kortilla on rahaa " + (summa - MAUKAS) + " euroa";

        assertEquals("Saldo ei saisi mennä negatiiviseksi jos maukkaaseen lounaaseen ei ole varaa. \n"
                + "Tarkasta että seuraava toimii: kortti = new " + luokanNimi + "(7); kortti.syoMaukkaasti(); kortti.syoMaukkaasti(); System.out.println(kortti);\n", odotettu.toLowerCase(), vastaus.toLowerCase());
    }

    @Points("04-24.3")
    @Test
    public void voiSyodaKokoRahalla() throws Throwable {
        double summa = MAUKAS;
        Object kortti = newKortti(summa);

        syo("Maukkaasti", kortti);

        String vastaus = toString(kortti);
        String odotettu = "Kortilla on rahaa " + (summa - MAUKAS) + " euroa";

        assertEquals("Jos kortilla on rahaa " + MAUKAS + ", pitäisi pystyä ostamaan syömään maukkaasti. \n"
                + "Tarkasta että seuraava toimii: kortti = new " + luokanNimi + "(" + MAUKAS + "); kortti.syoMaukkasti();  System.out.println(kortti);", odotettu.toLowerCase(), vastaus.toLowerCase());
    }

    @Points("04-24.3")
    @Test
    public void voiSyodaKokoRahalla2() throws Throwable {
        double summa = EDULLINEN;
        Object kortti = newKortti(summa);

        syo("Edullisesti", kortti);

        String vastaus = toString(kortti);
        String odotettu = "Kortilla on rahaa " + (summa - EDULLINEN) + " euroa";

        assertEquals("Jos kortilla on rahaa " + EDULLINEN + ", pitäisi pystyä ostamaan syömään edullisesti. \n"
                + "Tarkasta että seuraava toimii: kortti = new " + luokanNimi + "(" + EDULLINEN + "); kortti.syoEdullisesti();  System.out.println(kortti);", odotettu.toLowerCase(), vastaus.toLowerCase());
    }

    /*
     * osa 4
     */

    @Points("04-24.4")
    @Test
    public void eiYlimaaraisiaMuuttujia4() {
        saniteettitarkastus();
    }

    @Points("04-24.4")
    @Test
    public void onkoMetodiaMetodiLataaRahaa() throws Throwable {
        String metodi = "lataaRahaa";

        Object olio = newKortti(4.0);

        assertTrue("tee luokalle " + klassName + " metodi public void " + metodi + "(double ladattavaMaara) ", klass.method(olio, metodi)
                .returningVoid().taking(double.class).isPublic());

        String v = "\nVirheen aiheuttanut koodi " + luokanNimi + " lk = new " + luokanNimi + "(4.0); lk.lataaRahaa(2.0);";

        klass.method(olio, metodi)
                .returningVoid().taking(double.class).withNiceError(v).invoke(2.0);

    }

    @Points("04-24.4")
    @Test
    public void rahaaVoiLadata() throws Throwable {
        double summa = 7;
        Object kortti = newKortti(summa);

        lataaRahaa(kortti, 3);

        String vastaus = toString(kortti);
        String odotettu = "Kortilla on rahaa " + (summa + 3) + " euroa";

        assertEquals("Toimiiko metodi lataaRahaa? Tarkasta että seuraava toimii: \n"
                + "kortti = new " + luokanNimi + "(7); kortti.lataaRahaa(3); System.out.println(kortti);\n"
                + "", odotettu.toLowerCase(), vastaus.toLowerCase());
    }

    @Points("04-24.4")
    @Test
    public void ladatullaRahallaVoiOstaa() throws Throwable {
        Object kortti = newKortti(1);

        lataaRahaa(kortti, 5);
        syo("Maukkaasti", kortti);

        double summa = 6 - MAUKAS;
        String vastaus = toString(kortti);
        String odotettu = "Kortilla on rahaa " + summa + " euroa";

        assertEquals("Toimiiko metodi lataaRahaa? Tarkasta että seuraava toimii: \n"
                + "kortti = new " + luokanNimi + "(1); kortti.lataaRahaa(5); kortti.syoMaukkaasti(); System.out.println(kortti);\n"
                + "", odotettu.toLowerCase(), vastaus.toLowerCase());
    }

    @Points("04-24.4")
    @Test
    public void saldoEiKasvaYliRajan() throws Throwable {
        Object kortti = newKortti(100);

        lataaRahaa(kortti, 100);

        double summa = 150;
        String vastaus = toString(kortti);
        String odotettu = "Kortilla on rahaa " + summa + " euroa";

        assertEquals("Kortin saldo ei saisi nousta suuremmaksi kuin 150. Tarkasta että seuraava toimii: \n"
                + "kortti = new " + luokanNimi + "(100); kortti.lataaRahaa(100); kortti.syoMaukkaasti(); System.out.println(kortti);\n"
                + "", odotettu.toLowerCase(), vastaus.toLowerCase());
    }

    /*
     * osa 5
     */
    @Points("04-24.5")
    @Test
    public void eiYlimaaraisiaMuuttujia5() {
        saniteettitarkastus();
    }

    @Points("04-24.5")
    @Test
    public void negatiivinenLatausEiMuutaSaldoa() throws Throwable {
        double saldo = 10;
        Object kortti = newKortti(10);

        lataaRahaa(kortti, -7);

        String vastaus = toString(kortti);
        String odotettu = "Kortilla on rahaa " + saldo + " euroa";

        assertEquals("Negatiivisen latauksen ei pitäisi vaikuttaa mitään. Tarkasta että seuraava toimii:\n"
                + "kortti = new " + luokanNimi + "(10); kortti.lataaRahaa(-7); kortti.syoMaukkaasti(); System.out.println(kortti);\n", odotettu.toLowerCase(), vastaus.toLowerCase());

        lataaRahaa(kortti, 1);
        lataaRahaa(kortti, -3);

        vastaus = toString(kortti);
        odotettu = "Kortilla on rahaa " + (saldo + 1) + " euroa";

        assertEquals("Negatiivisen latauksen ei pitäisi vaikuttaa mitään. Tarkasta että seuraava toimii:\n"
                + "kortti = new " + luokanNimi + "(10); kortti.lataaRahaa(-7); kortti.lataaRahaa(1); kortti.lataaRahaa(-3);System.out.println(kortti);\n", odotettu.toLowerCase(), vastaus.toLowerCase());
    }

    /*
     * osa 6
     */
    @Points("04-24.6")
    @Test
    public void montaKorttia() {
        Paaohjelma.main(new String[0]);
        String[] rivit = io.getSysOut().split("\n");
        assertTrue("Et tulosta mitään", rivit.length > 0);
        for (String rivi : rivit) {
            assertTrue("Tulosta kortin tietojen kanssa saman rivin alussa kortin omistaja! Muista poistaa yliääräinen koodi pääohjelmasta kun teet tehtävää 04-24.6. ", rivi.toLowerCase().contains("pek") || rivi.toLowerCase().contains("mat"));
            assertFalse("Tulosta yhden kortin tiedot per rivi, nyt tulostuu rivi " + rivi, rivi.toLowerCase().contains("pek") && rivi.toLowerCase().contains("mat"));
        }
        ArrayList<String> pekka = new ArrayList<String>();
        ArrayList<String> matti = new ArrayList<String>();
        for (String rivi : rivit) {
            if (rivi.toLowerCase().contains("mat")) {
                matti.add(rivi);
            } else if (rivi.toLowerCase().contains("pek")) {
                pekka.add(rivi);
            }
        }

        tarkastaMatinRivit(matti);
        tarkastaPekanRivit(pekka);
    }

    private void tarkastaMatinRivit(ArrayList<String> rivi) {
        String virhe = "tarkasta että ohjelmasi tulostus on samanlainen kun tehtävänannon esimerkissä. tulostuu ";

        assertEquals("Matin kortin tiedot pitäisi tulostaa 3 kertaa: ", 3, rivi.size());

        assertTrue(virhe + rivi.get(0), rivi.get(0).contains("27.4"));
        assertTrue(virhe + rivi.get(1), rivi.get(1).contains("22.7"));
        assertTrue(virhe + rivi.get(2), rivi.get(2).contains("72.8"));
    }

    private void tarkastaPekanRivit(ArrayList<String> rivi) {
        assertEquals("Pekan kortin tiedot pitäisi tulostaa 3 kertaa: ", 3, rivi.size());

        String virhe = "tarkasta että ohjelmasi tulostus on samanlainen kun tehtävänannon esimerkissä. tulostuu ";
        assertTrue(virhe + rivi.get(0), rivi.get(0).contains("15.4"));
        assertTrue(virhe + rivi.get(1), rivi.get(1).contains("35.4"));
        assertTrue(virhe + rivi.get(2), rivi.get(2).contains("30."));
    }

    @Points("04-24.6")
    @Test
    public void eiYlimaaraisiaMuuttujia6() {
        saniteettitarkastus();
    }

    private void saniteettitarkastus() throws SecurityException {
        Field[] kentat = ReflectionUtils.findClass(luokanNimi).getDeclaredFields();

        for (Field field : kentat) {
            assertFalse("et tarvitse \"stattisia muuttujia\", poista " + kentta(field.toString()), field.toString().contains("static") && !field.toString().contains("final"));
            assertTrue("luokan kaikkien oliomuuttujien näkyvyyden tulee olla private, muuta koodissa on: " + kentta(field.toString()), field.toString().contains("private"));
        }

        if (kentat.length > 1) {
            int var = 0;
            for (Field field : kentat) {
                if (!field.toString().contains("final")) {
                    var++;
                }
            }
            assertTrue("et tarvitse " + luokanNimi + "-luokalle kuin arvon muistavan oliomuuttujan, poista ylimääräiset", var < 2);
        }
    }

    /*
     * helpers
     */
    private void syo(String miten, Object kortti) throws Throwable {
        String metodi = "syo" + miten;

        String v = "\nVirheen aiheuttanut koodi lk = new " + luokanNimi + "(4); lk." + metodi + "()";

        klass.method(kortti, metodi)
                .returningVoid().takingNoParams().withNiceError(v).invoke();
    }

    private void lataaRahaa(Object kortti, double maara) throws Throwable {

        String metodi = "lataaRahaa";

        String v = "\nVirheen aiheuttanut koodi lk = new " + luokanNimi + "(4); lk." + metodi + "(" + maara + ")";

        klass.method(kortti, metodi)
                .returningVoid().taking(double.class).withNiceError(v).invoke(maara);
    }

    private String toString(Object olio) throws Throwable {
        //Reflex.ClassRef<Object> klassi = Reflex.reflect(klassName);
        String metodi = "toString";

        String v = "\nVirheen aiheuttanut koodi lk = new " + luokanNimi + "(4); lk.toString()";

        return klass.method(olio, metodi)
                .returning(String.class).takingNoParams().withNiceError(v).invoke();
    }

    private Object newKortti(double arvo) throws Throwable {
        Reflex.MethodRef1<Object, Object, Double> ctor = klass.constructor().taking(double.class).withNiceError();
        assertTrue("Määrittele luokalle " + klassName + " julkinen konstruktori: public " + klassName + "(double alkusaldo)", ctor.isPublic());
        return ctor.invoke(arvo);
    }

    private String kentta(String toString) {
        return toString.replace(luokanNimi + ".", "");
    }
}
