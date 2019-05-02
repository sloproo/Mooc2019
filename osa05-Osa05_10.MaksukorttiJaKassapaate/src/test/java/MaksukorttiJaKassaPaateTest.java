
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Random;
import org.junit.*;
import static org.junit.Assert.*;

public class MaksukorttiJaKassaPaateTest {

    Maksukortti kortti;
    Kassapaate kassa;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
        kassa = new Kassapaate();
    }
    Reflex.ClassRef<Object> klassL;
    String klassNameL = "Maksukortti";
    Reflex.ClassRef<Object> klassK;
    String klassNameK = "Kassapaate";

    @Before
    public void haeLuokka() {
        klassL = Reflex.reflect(klassNameL);
        klassK = Reflex.reflect(klassNameK);
    }

    @Points("05-10.1")
    @Test
    public void eiLiikaaOliomuuttujiaKorttiin() {
        saniteettitarkastus("Maksukortti", 1, "Älä lisää Maksukortti-luokalle uusia oliomuuttujia, niitä ei tarvita");
    }

    @Points("05-10.1")
    @Test
    public void kortiltaVoiOttaaRahaaJosSaldoRittaa() {
        assertEquals("Jos kortilla raha riittää, pitäisi otaRahaa-metodin palauttaa true. Tarkasta koodi: \n"
                + "k = new Maksukortti(10); k.otaRahaa(8);", true, kortti.otaRahaa(8));
        assertEquals("Kortin saldon pitäisi vähetä kun rahaa otetaan. Tarkasta koodi: "
                + "k = new Maksukortti(10); k.otaRahaa(8);", 2, kortti.saldo(), 0.01);
    }

    @Points("05-10.1")
    @Test
    public void kortinVoiTyhjentaa() {
        assertEquals("Kortilta pitäisi pystyä ottamaan kaikki rahat. Tarkasta koodi: "
                + "k = new Maksukortti(10); k.otaRahaa(10);", true, kortti.otaRahaa(10));
        assertEquals("Kortilta pitäisi pystyä ottamaan kaikki rahat. Tarkasta koodi: "
                + "k = new Maksukortti(10); k.otaRahaa(10);", 0, kortti.saldo(), 0.01);
    }

    /*
     *
     */
    @Points("05-10.2")
    @Test
    public void eiLiikaaOliomuuttujiaKassaan1() {
        saniteettitarkastus("Kassapaate", 3, "Älä lisää Kassapaate-luokalle uusia oliomuuttujia, niitä ei tarvita");
    }

    @Points("05-10.2")
    @Test
    public void kassaAluksiRahaa1000() {
        assertTrue("Kun kassapääte luodaan pitää rahamäärän muistavan oliomuuttujan arvoksi asettaa 1000. Olion tulostamalla pitäisi näytä että rahaa kassassa on 1000. \n"
                + "Nyt tulostuu: \"" + kassa + "\"", kassa.toString().contains("kassassa rahaa 1000"));
    }

    @Points("05-10.2")
    @Test
    public void alussaRahaa1000JaEiMyytyjaLounaita() {
        String odotettu = "kassassa rahaa 1000.0 edullisia lounaita myyty 0 maukkaita lounaita myyty 0";
        assertEquals("Kun kassapääte luodaan on rahaa 1000 ja myytyjen lounaiden määrä 0, ", odotettu, kassa.toString());
    }

    @Points("05-10.2")
    @Test
    public void onnistunutEdullinen() {
        double vaihto = kassa.syoEdullisesti(4);

        String virhe = "Ostettaessa käteisellä 4:llä eurolla edullinen lounas (eli kutsuttaessa kassa.syoEdullisesti(4)) ";
        assertEquals(virhe + "pitäisi metodin palauttaa vaihtorahan oikea määrä.", 1.5, vaihto, 0.01);

        assertTrue(virhe + " pitäisi kassassa olevan rahamäärän kasvaa 2.5 eurolla eli olla 1002.5. \nNyt kassa on: " + kassa, kassa.toString().contains("kassassa rahaa 1002.5"));

        assertTrue(virhe + " pitäisi myytyjen edullisten lounaiden määrän olla 1. \n"
                + "Nyt kassa on: " + kassa, kassa.toString().contains("edullisia lounaita myyty 1"));
        assertTrue(virhe + " pitäisi myytyjen maukkaiden lounaiden määrän olla edelleen 0. Nyt kassa on: " + kassa, kassa.toString().contains("maukkaita lounaita myyty 0"));
    }

    @Points("05-10.2")
    @Test
    public void onnistunutMaukas() {
        double vaihto = kassa.syoMaukkaasti(5);

        String virhe = "Ostettaessa käteisellä 5:llä eurolla maukas lounas (eli kutsuttaessa kassa.syoMaukkaasti(5)) ";
        assertEquals(virhe + "pitäisi metodin palauttaa vaihtorahan oikea määrä.", 0.7, vaihto, 0.01);

        assertTrue(virhe + " pitäisi kassassa olevan rahamäärän kasvaa 4.3 eurolla eli olla 1004.3. \nNyt kassa on: " + kassa, kassa.toString().contains("kassassa rahaa 1004.3") || kassa.toString().contains("kassassa rahaa 1004.299"));

        assertTrue(virhe + " pitäisi myytyjen maukkaiden lounaiden määrän olla 1. Nyt kassa on: " + kassa, kassa.toString().contains("maukkaita lounaita myyty 1"));
        assertTrue(virhe + " pitäisi myytyjen edullisten lounaiden määrän olla edelleen 0. Nyt kassa on: " + kassa, kassa.toString().contains("edullisia lounaita myyty 0"));
    }

    @Points("05-10.2")
    @Test
    public void onnistunutTasarahaEdullinen() {
        double vaihto = kassa.syoEdullisesti(2.5);

        String virhe = "Ostettaessa käteisellä 2.5:llä eurolla edullinen lounas (eli kutsuttaessa kassa.syoEdullisesti(2.5)) ";
        assertEquals(virhe + "kaikki raha kuluu, eli vaihtorahan pitäisi olla 0.", 0, vaihto, 0.01);

        assertTrue(virhe + " pitäisi kassassa olevan rahamäärän kasvaa 2.5 eurolla eli olla 1002.5. Nyt kassa on: " + kassa, kassa.toString().contains("kassassa rahaa 1002.5"));

        assertTrue(virhe + " pitäisi myytyjen edullisten lounaiden määrän olla 1. Nyt kassa on: " + kassa, kassa.toString().contains("edullisia lounaita myyty 1"));
    }

    @Points("05-10.2")
    @Test
    public void onnistunutTasarahaMaukas() {
        double vaihto = kassa.syoMaukkaasti(4.3);

        String virhe = "Ostettaessa käteisellä 4.3:llä eurolla maukas lounas (eli kutsuttaessa kassa.syoMaukkaasti(4.3)) ";
        assertEquals(virhe + "kaikki raha kuluu, eli vaihtorahan pitäisi olla 0.", 0, vaihto, 0.01);

        assertTrue(virhe + " pitäisi kassassa olevan rahamäärän kasvaa 4.3 eurolla eli olla 1004.3- Nyt kassa on: " + kassa, kassa.toString().contains("kassassa rahaa 1004.3") || kassa.toString().contains("kassassa rahaa 1004.299"));

        assertTrue(virhe + " pitäisi myytyjen maukkaiden lounaiden määrän olla 1. \n"
                + "Nyt kassa on: " + kassa, kassa.toString().contains("maukkaita lounaita myyty 1"));
    }

    @Points("05-10.2")
    @Test
    public void useitaMyyty() {
        kassa.syoMaukkaasti(5);
        kassa.syoEdullisesti(3);
        kassa.syoMaukkaasti(5);
        kassa.syoMaukkaasti(10);
        kassa.syoEdullisesti(4);

        String virhe = "Operaatioiden kassa.syoMaukkasti(5); kassa.syoEdullisesti(3); kassa.syoMaukkasti(5);"
                + "kassa.syoMaukkasti(10);kassa.syoEdullisesi(4);";
        assertTrue(virhe + " jälkeen pitäisi kassassa olevan rahamäärän olla 1017.9 euroa. Nyt kassa on: " + kassa, kassa.toString().contains("kassassa rahaa 1017.899") || kassa.toString().contains("kassassa rahaa 1017.9"));
        assertTrue(virhe + " jälkeen pitäisi myytyjen maukkaiden lounaiden määrän olla 3. Nyt kassa on: " + kassa, kassa.toString().contains("maukkaita lounaita myyty 3"));
        assertTrue(virhe + " jälkeen pitäisi myytyjen edullisten lounaiden määrän olla 2. Nyt kassa on: " + kassa, kassa.toString().contains("edullisia lounaita myyty 2"));

    }

    @Points("05-10.2")
    @Test
    public void josEiRahaaMyyntiEpaonnistuuEiKassaanKosketa() {
        double paluu = kassa.syoEdullisesti(2);

        assertEquals("Kun yritetään ostaa liian pienellä rahamäärällä eli kassa.syoEdullisesti(2), pitäisi koko raha palauttaa takaisin", 2, paluu, 0.01);

        String odotettu = "kassassa rahaa 1000.0 edullisia lounaita myyty 0 maukkaita lounaita myyty 0";
        assertEquals("Kun tyhjästä kassata yritetään ostaa riittämättömällä rahamäärällä edullinen lounas, "
                + "ei kassan sisältö saisi muuttua eli tulostuksen pitäisi olla: ", odotettu, kassa.toString());

        paluu = kassa.syoMaukkaasti(2);
        assertEquals("Kun yritetään ostaa liian pienellä rahamäärällä eli kassa.syoMaukkaasti(2), pitäisi koko raha palauttaa takaisin", 2, paluu, 0.01);

        odotettu = "kassassa rahaa 1000.0 edullisia lounaita myyty 0 maukkaita lounaita myyty 0";
        assertEquals("Kun tyhjästä kassata yritetään ostaa riittämättömällä rahamäärällä maukas lounas, "
                + "ei kassan sisältö saisi muuttua eli tulostuksen pitäisi olla: ", odotettu, kassa.toString());
    }

    /*
     *
     */
    @Points("05-10.3")
    @Test
    public void eiLiikaaOliomuuttujiaKassaan2() {
        saniteettitarkastus("Kassapaate", 3, "Älä lisää Kassapaate-luokalle uusia oliomuuttujia, niitä ei tarvita");
    }

    @Points("05-10.3")
    @Test
    public void onMetodiKortillaEdullisenOstoon() throws Throwable {
        String metodi = "syoEdullisesti";

        Kassapaate k = new Kassapaate();

        assertTrue("tee luokalle " + klassNameK + " metodi public boolean " + metodi + "(Maksukortti kortti) ",
                klassK.method(k, metodi).returning(boolean.class).taking(Maksukortti.class).isPublic());

        String v = "\nVirheen aiheuttanut koodi "
                + "k = new Kassapaate(); lk = new Maksukortti(10); k." + metodi + "(lk);";

        Maksukortti lk = new Maksukortti(10);

        klassK.method(k, metodi).returning(boolean.class).taking(Maksukortti.class).withNiceError(v).invoke(lk);
    }

    @Points("05-10.3")
    @Test
    public void kortillaVoiOstaaEdullisenLounaanJosTarpeeksiRahaa() {
        String sken = "kassa = new Kassapaate(); kortti = new Maksukortti(10); kassa.syoEdullisesti(kortti);";
        Boolean ok = syoEdullisesti(kassa, kortti);

        assertEquals("Kortilla pitäisi voida ostaa edullinen lounas jos raha riittää. Tarkasta seuraava:\n" + sken, true, ok);

        assertEquals("Kortin saldon pitäisi vähetä lounaan hinnan verran. Tarkasta koodi:\n"
                + sken + " kortti.saldo();\n", 7.5, kortti.saldo(), 0.01);

        String odotettu = "kassassa rahaa 1000.0 edullisia lounaita myyty 1 maukkaita lounaita myyty 0";
        assertEquals("Kun tyhjästä kassata ostetaan kortilla edullinen lounas, kassan rahasumma ei saa muuttua ja"
                + " myytyjen edullisten lounaiden pitäisi olla 1.\n", odotettu, kassa.toString());
    }

    @Points("05-10.3")
    @Test
    public void kortillaVoiOstaaEdullisenLounaanJosJuuriJaJuuriTarpeeksiRahaa() {
        String sken = "kassa = new Kassapaate(); kortti = new Maksukortti(2.5); kassa.syoEdullisesti(kortti);";
        kortti = new Maksukortti(2.5);
        Boolean ok = syoEdullisesti(kassa, kortti);

        assertEquals("Kortilla pitäisi voida ostaa edullinen lounas jos rahaa tasan lounaan hinnan verran. Tarkasta seuraava:\n" + sken + "\n", true, ok);

        assertEquals("Kortin saldo putoaa nollaan jos ostetaan edullinen lounas kun rahaa vaan sen verran. Tarkasta koodi:\n"
                + sken + " kortti.saldo();\n", 0, kortti.saldo(), 0.01);

        String odotettu = "kassassa rahaa 1000.0 edullisia lounaita myyty 1 maukkaita lounaita myyty 0";
        assertEquals("Kun tyhjästä kassata ostetaan kortilla edullinen lounas, kassan rahasumma ei saa muuttua ja"
                + " myytyjen edullisten lounaiden pitäisi olla 1.", odotettu, kassa.toString());
    }

    @Points("05-10.3")
    @Test
    public void kortillaEiVoiOstaaEdullistaJosEiTarpeeksiRahaa() {
        String sken = "kassa = new Kassapaate(); kortti = new Maksukortti(2); kassa.syoEdullisesti(kortti);";
        kortti = new Maksukortti(2);
        Boolean ok = syoEdullisesti(kassa, kortti);

        assertEquals("Kortilla ei saa voida ostaa jos rahaa ei riittävästi. Tarkasta seuraava:\n"
                + sken + "\n", false, ok);

        assertEquals("Kortin saldon pitäisi pysyä lmuuttumattomana jos ostokseen ei rahaa. Tarkasta koodi:\n"
                + sken + " kortti.saldo();\n", 2, kortti.saldo(), 0.01);

        String odotettu = "kassassa rahaa 1000.0 edullisia lounaita myyty 0 maukkaita lounaita myyty 0";
        assertEquals("Kun tyhjästä kassata ostetaan kortilla jolla ei rahaa, pysyy kassan tilanne muuttumattomana.",
                odotettu, kassa.toString());
    }

    @Points("05-10.3")
    @Test
    public void onMetodiKortillaMaukkaanOstoon() throws Throwable {
        String metodi = "syoMaukkaasti";

        Kassapaate k = new Kassapaate();

        assertTrue("tee luokalle " + klassNameK + " metodi public boolean " + metodi + "(Maksukortti kortti) ",
                klassK.method(k, metodi).returning(boolean.class).taking(Maksukortti.class).isPublic());

        String v = "\nVirheen aiheuttanut koodi "
                + "k = new Kassapaate(); lk = new Maksukortti(10); k." + metodi + "(lk);";

        Maksukortti lk = new Maksukortti(10);

        klassK.method(k, metodi).returning(boolean.class).taking(Maksukortti.class).withNiceError(v).invoke(lk);
    }

    @Points("05-10.3")
    @Test
    public void kortillaVoiOstaaMaukkaanLounaanJosTarpeeksiRahaa() {
        String sken = "kassa = new Kassapaate(); kortti = new Maksukortti(10); kassa.syoMaukkaasti(kortti);";
        Boolean ok = syoMaukkaasti(kassa, kortti);

        assertEquals("Kortilla pitäisi voida ostaa maukas lounas jos raha riittää. Tarkasta seuraava: " + sken + " "
                + "\n", true, ok);

        assertEquals("Kortin saldon pitäisi vähetä maukkaan lounaan hinnan verran. \n"
                + "Tarkasta koodi "
                + sken + " kortti.saldo();\n", 5.7, kortti.saldo(), 0.01);

        String odotettu = "kassassa rahaa 1000.0 edullisia lounaita myyty 0 maukkaita lounaita myyty 1";
        assertEquals("Kun tyhjästä kassata ostetaan kortilla maukas lounas, kassan rahasumma ei saa muuttua ja"
                + "myytyjen maukkaiden lounaiden pitäisi olla 1.\n", odotettu, kassa.toString());
    }

    @Points("05-10.3")
    @Test
    public void kortillaVoiOstaaMaukkaanLounaanJosJuuriJaJuuriTarpeeksiRahaa() {
        String sken = "kassa = new Kassapaate(); kortti = new Maksukortti(4.3);";
        kortti = new Maksukortti(4.3);
        Boolean ok = syoMaukkaasti(kassa, kortti);

        assertEquals("Kortilla pitäisi voida ostaa maukas lounas jos rahaa tasan lounaan hinnan verran. Tarkasta seuraava: " + sken
                + "\n", true, ok);

        assertEquals("Kortin saldo putoaa nollaan jos ostetaan maukas lounas kun rahaa vaan sen verran. Tarkasta koodi "
                + sken + " kortti.saldo();\n", 0, kortti.saldo(), 0.01);

        String odotettu = "kassassa rahaa 1000.0 edullisia lounaita myyty 0 maukkaita lounaita myyty 1";
        assertEquals("Kun tyhjästä kassata ostetaan kortilla maukas lounas, kassan rahasumma ei saa muuttua ja"
                + "myytyjen maukkaiden lounaiden pitäisi olla 1. ", odotettu, kassa.toString());
    }

    @Points("05-10.3")
    @Test
    public void kortillaEiVoiOstaaMaukastaJosEiTarpeeksiRahaa() {
        String sken = "kassa = new Kassapaate(); kortti = new Maksukortti(4.1);";
        kortti = new Maksukortti(4.1);
        Boolean ok = syoMaukkaasti(kassa, kortti);

        assertEquals("Kortilla ei saa voida ostaa jos rahaa ei riittävästi. Tarkasta seuraava:\n"
                + sken + "\n", false, ok);

        assertEquals("Kortin saldon pitäisi pysyä muuttumattomana jos ostokseen ei rahaa. Tarkasta koodi:\n"
                + sken + " kortti.saldo();\n", 4.1, kortti.saldo(), 0.01);

        String odotettu = "kassassa rahaa 1000.0 edullisia lounaita myyty 0 maukkaita lounaita myyty 0";
        assertEquals("Kun tyhjästä kassata ostetaan kortilla jolla ei rahaa, pysyy kassan tilanne muuttumattomana.",
                odotettu, kassa.toString());
    }

    /*
     *
     */
    @Points("05-10.4")
    @Test
    public void eiLiikaaOliomuuttujiaKassaan3() {
        saniteettitarkastus("Kassapaate", 3, "Älä lisää Kassapaate-luokalle uusia oliomuuttujia, niitä ei tarvita");
    }

    @Points("05-10.4")
    @Test
    public void onMetodiKortinLataamiseen() throws Throwable {
        String metodi = "lataaRahaaKortille";

        Kassapaate k = new Kassapaate();

        assertTrue("tee luokalle " + klassNameK + " metodi public void " + metodi + "(Maksukortti kortti, double summa) ",
                klassK.method(k, metodi).returningVoid().taking(Maksukortti.class, double.class).isPublic());

        String v = "\nVirheen aiheuttanut koodi "
                + "k = new Kassapaate(); lk = new Maksukortti(10); k." + metodi + "(lk, 5);";

        Maksukortti lk = new Maksukortti(10);

        klassK.method(k, metodi).returningVoid().taking(Maksukortti.class, double.class).withNiceError(v).invoke(lk, 5.0);
    }
    
    @Points("05-10.4")
    @Test
    public void lataaminenKasvattaaKortinSaldoa() {
        lataa(kassa, kortti, 10);
        String virhe = "Tomiiko rahan lataaminen? tarkasta koodi:\n"
                + "kassa = new Kassapaate(); kortti = new Maksukortti(10); kassa.lataaRahaaKortille(kortti, 10); kortti.saldo()";
        assertEquals(virhe, 20, kortti.saldo(), 0.01);

        String odotettu = "kassassa rahaa 1010.0 edullisia lounaita myyty 0 maukkaita lounaita myyty 0";
        assertEquals("Kun kortille ladataan rahaa, menee ladattu rahamäärä Kassapäätteeseen. "
                + "Alussa kassassa siis 1000, kun kortille ladataan 10"
                + " pitäisi kassan tulostuksen olla: \n", odotettu, kassa.toString());

    }

    @Points("05-10.4")
    @Test
    public void negatiivinenLatausKortinSaldoa() {
        lataa(kassa, kortti, -10);
        String virhe = "Negatiivisen lataamisen ei pitäisi muuttaa kortin saldoa eikä kassan rahamäärää! tarkasta koodi:\n"
                + "kassa = new Kassapaate(); kortti = new Maksukortti(10); kassa.lataaRahaaKortille(kortti, -10); kortti.saldo()";
        assertEquals(virhe, 10, kortti.saldo(), 0.01);

        String odotettu = "kassassa rahaa 1000.0 edullisia lounaita myyty 0 maukkaita lounaita myyty 0";
        assertEquals("Negatiivisen lataamisen ei pitäisi muuttaa kortin saldoa eikä kassan rahamäärää!"
                + "Alussa kassassa siis 1000, kun kortille ladataan -10"
                + " pitäisi kassan tulostuksen olla: \n", odotettu, kassa.toString());

    }    
    
    @Points("05-10.4")
    @Test
    public void moniLatausToimii() {
        Random seka = new Random();
        int ladatutYhteensa = 0;
        int[] ladatut = new int[5];
        for (int i = 0; i < ladatut.length; i++) {
            int ladattava = 1 + seka.nextInt(20);
            ladatut[i] = ladattava;
            ladatutYhteensa += ladattava;
            lataa(kassa, kortti, ladattava);
        }

        String mj = "";
        for (int l : ladatut) {
            mj += l + " ";
        }

        String virhe = "Tomiiko rahan lataaminen? tarkasta kortin saldo kun lataat sinne peräkkäin summat  " + mj;
        assertEquals(virhe, 10 + ladatutYhteensa, kortti.saldo(), 0.01);

        double exp = 1000 + ladatutYhteensa;
        String odotettu = "kassassa rahaa " + exp + " edullisia lounaita myyty 0 maukkaita lounaita myyty 0";
        assertEquals("Kun kortille ladataan rahaa, menee ladattu rahamäärä Kassapäätteeseen. "
                + "Alussa kassassa siis 1000, kun kortille ladataan perlläin summat " + mj
                + " pitäisi kassan tulostuksen olla: ", odotettu, kassa.toString());

    }
    /*
     *
     */

    private void lataa(Object kassa, Object kortti, double summa) {
        String metodiNimi = "lataaRahaaKortille";
        try {
            Method metodi = ReflectionUtils.requireMethod(Kassapaate.class, metodiNimi, Maksukortti.class, double.class);
            ReflectionUtils.invokeMethod(void.class, metodi, kassa, kortti, summa);
        } catch (Throwable t) {
            t.printStackTrace();
            fail("tee luokalle Kassapaate metodi public boolean lataaRahaaKortille(Maksukortti kortti, double summa)");
        }
    }

    private boolean syoEdullisesti(Object kassa, Object kortti) {
        String metodiNimi = "syoEdullisesti";
        try {
            Method metodi = ReflectionUtils.requireMethod(Kassapaate.class, metodiNimi, Maksukortti.class);
            return ReflectionUtils.invokeMethod(boolean.class, metodi, kassa, kortti);
        } catch (Throwable t) {
            t.printStackTrace();
            fail("tee luokalle Kassapaate metodi public boolean syoEdullisesti(Maksukortti kortti)");
        }
        return false;
    }

    private boolean syoMaukkaasti(Object kassa, Object kortti) {
        String metodiNimi = "syoMaukkaasti";
        try {
            Method metodi = ReflectionUtils.requireMethod(Kassapaate.class, metodiNimi, Maksukortti.class);
            return ReflectionUtils.invokeMethod(boolean.class, metodi, kassa, kortti);
        } catch (Throwable t) {
            t.printStackTrace();
            fail("tee luokalle Kassapaate metodi public boolean syoMaukkaasti(Maksukortti kortti)");
        }
        return false;
    }

    private void saniteettitarkastus(String luokanNimi, int muuttujia, String msg) throws SecurityException {

        Field[] kentat = ReflectionUtils.findClass(luokanNimi).getDeclaredFields();

        String viesti = ", HUOM: jos haluat tallettaa lounaan hinnan olion muuttujaan, tee se seuraavasti: "
                + " private static final double EDULLISEN_HINTA = 2.5; ";

        for (Field field : kentat) {
            assertFalse("et tarvitse \"stattisia muuttujia\", poista " + kentta(field.toString()) + viesti, field.toString().contains("static") && !field.toString().contains("final"));
            assertTrue("luokan kaikkien oliomuuttujien näkyvyyden tulee olla private, muuta " + kentta(field.toString()) + viesti,
                    field.toString().contains("private"));
        }

        if (kentat.length > 1) {
            int var = 0;
            for (Field field : kentat) {
                if (!field.toString().contains("final")) {
                    var++;
                }
            }
            assertTrue(msg + viesti, var <= muuttujia);
        }
    }

    private String kentta(String toString) {
        return toString.replace("Maksukortti" + ".", "");
    }
}
