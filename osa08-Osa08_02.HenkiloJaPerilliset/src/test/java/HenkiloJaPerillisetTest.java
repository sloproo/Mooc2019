
import fi.helsinki.cs.tmc.edutestutils.MockInOut;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import java.util.ArrayList;
import org.junit.*;
import static org.junit.Assert.*;

public class HenkiloJaPerillisetTest {

    String henkiloLuokanNimi = "Henkilo";
    String opiskelijaLuokanNimi = "Opiskelija";
    String opettajaLuokanNimi = "Opettaja";

    @Test
    @Points("08-02.1")
    public void luokkaHenkilo() {
        String klassName = henkiloLuokanNimi;
        Reflex.ClassRef<Object> classRef;
        classRef = Reflex.reflect(klassName);

        assertTrue("Luokan " + s(klassName) + " pitää olla julkinen, eli se tulee määritellä\n"
                + "public class " + klassName + " {...\n}", classRef.isPublic());
    }

    @Test
    @Points("08-02.1")
    public void HenkilollaEiYlimaaraisiaMuuttujia() {
        String klassName = henkiloLuokanNimi;
        saniteettitarkastus(klassName, 2, "nimen ja osoitteen muistavat oliomuuttujat");
    }

    @Test
    @Points("08-02.1")
    public void henkilollaKaksiparametrinenKonstruktori() throws Throwable {
        newHenkilo("Pekka", "Mannerheimintie");
    }

    @Test
    @Points("08-02.1")
    public void henkilonToStringYlikirjoitettu() throws Throwable {
        Object h = newHenkilo("Pekka", "Mannerheimintie");

        assertFalse("tee luokalle Henkilo metodi toString", h.toString().contains("@"));
    }

    /*
     *
     */
    @Test
    @Points("08-02.1")
    public void henkilonToStringKunnossa() throws Throwable {
        Object h = newHenkilo("Pekka", "Mannerheimintie");

        String[] aa = h.toString().split("\n");

        assertEquals("tarkista että Henkilo:n toString on tuottaa tehtävänannon mukaisen merkkijonon\n"
                + "onko toStringisi tuottamassa merkkijonossa rivinvaihto eli \\n-merkki? rivejä", 2, h.toString().split("\n").length);

        assertTrue("tarkista että Henkilo:n toString on tuottaa tehtävänannon mukaisen merkkijonon\n"
                + "toisen rivin pitäisi alkaa kahdella välilyönnillä!", h.toString().split("\n")[1].startsWith("  "));
        assertFalse("tarkista että Henkilo:n toString on tuottaa tehtävänannon mukaisen merkkijonon\n"
                + "toisen rivin pitäisi alkaa kahdella välilyönnillä!", h.toString().split("\n")[1].startsWith("   "));

        assertTrue("tarkista että Henkilo:n toString on tuottaa tehtävänannon mukaisen merkkijonon\n"
                + "ala laita rivien perään ylimääräisiä merkkejä", h.toString().split("\n")[0].endsWith("a"));
        assertTrue("tarkista että Henkilo:n toString on tuottaa tehtävänannon mukaisen merkkijonon\n"
                + "ala laita rivien perään ylimääräisiä merkkejä", h.toString().split("\n")[1].endsWith("e"));

        String[] hlot = {"Pekka Mikkola;Mannerheimintie", "Joni Salmi;Korso;",
            "Esko Ukkonen;Westend"};

        for (String hlo : hlot) {
            String[] nimiOs = hlo.split(";");
            Object hh = newHenkilo(nimiOs[0], nimiOs[1]);
            assertTrue("tarkista että Henkilo:n toString on tuottaa tehtävänannon mukaisen merkkijonon", hh.toString().contains(nimiOs[0]));
            assertTrue("tarkista että Henkilo:n toString on tuottaa tehtävänannon mukaisen merkkijonon", hh.toString().contains(nimiOs[1]));
        }

    }

    //********************
    @Test
    @Points("08-02.2")
    public void luokkaOpiskelija() {
        String klassName = opiskelijaLuokanNimi;
        Reflex.ClassRef<Object> classRef;
        classRef = Reflex.reflect(klassName);

        assertTrue("Luokan " + s(klassName) + " pitää olla julkinen, eli se tulee määritellä\n"
                + "public class " + klassName + " {...\n}", classRef.isPublic());
    }

    @Test
    @Points("08-02.2")
    public void opiskelijallaEiYlimaaraisiaMuuttujia() {
        String klassName = opiskelijaLuokanNimi;
        saniteettitarkastus(klassName, 1, "opintopistemäärän muistavan oliomuuttujan\n"
                + "Nimi ja osoite on peritty yliluokassa. Niitä voi käyttää samaan tyylin kuin "
                + "esimerkissä Moottori perii luokan Osa!");
    }

    @Test
    @Points("08-02.2")
    public void opiskelijaPeriiLuokanHenkilo() {
        Class c = ReflectionUtils.findClass(opiskelijaLuokanNimi);

        Class sc = c.getSuperclass();
        assertTrue("Luokan Opiskelija tulee periä luokka Henkilo", sc.getName().equals(henkiloLuokanNimi));
    }

    @Test
    @Points("08-02.2")
    public void opiskelijallaKaksiparametrinenKonstruktori() throws Throwable {
        newOpiskelija("Olli", "Ida Albergintie 1");
    }

    @Test
    @Points("08-02.2")
    public void luokallaOpiskeliaOnMetodiOpintopisteita() throws Throwable {
        String metodi = "opintopisteita";
        String virhe = "tee luokalle Opiskelija metodi public int opintopisteita()";

        String klassName = opiskelijaLuokanNimi;
        Reflex.ClassRef<Object> classRef;
        classRef = Reflex.reflect(klassName);

        Object o = newOpiskelija("Pekka", "Korso");

        assertTrue(virhe,
                classRef.method(o, metodi).returning(int.class).takingNoParams().isPublic());

        String v = "Opiskelija o = new Opiskelija(\"Pekka\",\"Korso\");\n"
                + "o.opintopisteita();\n";

        assertEquals(0, (int) classRef.method(o, metodi)
                .returning(int.class)
                .takingNoParams().withNiceError("virheen aiheutti koodi\n" + v).invoke());
    }

    @Test
    @Points("08-02.2")
    public void luokallaOpiskeliaOnMetodiOpiskele() throws Throwable {
        String metodi = "opiskele";
        String virhe = "tee luokalle Opiskelija metodi public void opiskele()";

        String klassName = opiskelijaLuokanNimi;
        Reflex.ClassRef<Object> classRef;
        classRef = Reflex.reflect(klassName);

        Object o = newOpiskelija("Pekka", "Korso");

        assertTrue(virhe,
                classRef.method(o, metodi).returningVoid().takingNoParams().isPublic());

        String v = "Opiskelija o = new Opiskelija(\"Pekka\",\"Korso\");\n"
                + "o.opiskele();\n";
        classRef.method(o, metodi).returningVoid().takingNoParams().withNiceError("virheen aiheutti koodi\n" + v).invoke();
    }

    @Test
    @Points("08-02.2")
    public void opintopisteetKasvaa() throws Throwable {
        String k = "Opiskelija op = new Opiskelija(\"Olli\", \"Ida Algergintie 1\"); op.opintopisteita(); ";
        Object op = newOpiskelija("Olli", "Ida Albergintie 1");
        assertEquals("Testaa koodia " + k, 0, opintopisteita(op));

        k = "Opiskelija op = new Opiskelija(\"Olli\", \"Ida Algergintie 1\"); op.opiskele(); op.opintopisteita(); ";
        opiskele(op);
        assertEquals("Testaa koodia " + k, 1, opintopisteita(op));

        k = "Opiskelija op = new Opiskelija(\"Olli\", \"Ida Algergintie 1\"); op.opiskele(); op.opiskele(); op.opiskele(); op.opintopisteita(); ";
        opiskele(op);
        opiskele(op);
        assertEquals("Testaa koodia " + k, 3, opintopisteita(op));
    }

    // **************
    @Test
    @Points("08-02.3")
    public void opiskelijanToStringKunnossa() throws Throwable {
        Object h = newOpiskelija("Olli", "Ida Albergintie 1");
        assertEquals("tarkista että Opiskelija:n toString on tuottaa tehtävänannon mukaisen merkkijonon\n"
                + "onko toStringisi tuottamassa merkkijonossa 2 rivinvaihtoa eli \\n-merkkiä? rivejä", 3, h.toString().split("\n").length);

        assertTrue("tarkista että Opiskelija:n toString on tuottaa tehtävänannon mukaisen merkkijonon\n"
                + "toisen rivin pitäisi alkaa kahdella välilyönnillä!", h.toString().split("\n")[1].startsWith("  "));
        assertFalse("tarkista että Opiskelija:n toString on tuottaa tehtävänannon mukaisen merkkijonon\n"
                + "toisen rivin pitäisi alkaa kahdella välilyönnillä!", h.toString().split("\n")[1].startsWith("   "));
        assertTrue("tarkista että Opiskelija:n toString on tuottaa tehtävänannon mukaisen merkkijonon\n"
                + "kolmannen rivin pitäisi alkaa kahdella välilyönnillä!", h.toString().split("\n")[2].startsWith("  "));
        assertFalse("tarkista että Opiskelija:n toString on tuottaa tehtävänannon mukaisen merkkijonon\n"
                + "kolmannen rivin pitäisi alkaa kahdella välilyönnillä!", h.toString().split("\n")[2].startsWith("   "));

        String k = "Opiskelija op = new Opiskelija(\"Olli\",\"Ida Albergintie 1\"); System.out.print(op)";

        String aa = h.toString();

        assertTrue("tarkista että Opiskelija:n toString on tuottaa tehtävänannon mukaisen merkkijonon\n"
                + "tarkista koodi " + k, h.toString().contains("Olli"));
        assertTrue("tarkista että Opiskelija:n toString on tuottaa tehtävänannon mukaisen merkkijonon\n"
                + "tarkista koodi " + k, h.toString().contains("Ida Albergintie 1"));
        assertTrue("tarkista että Opiskelija:n toString on tuottaa tehtävänannon mukaisen merkkijonon\n"
                + "tarkista koodi " + k, h.toString().contains("opintopisteitä 0"));

        k = "Opiskelija op = new Opiskelija(\"Olli\",\"Ida Albergintie 1\"); op.opiskele(); op.opiskele(); System.out.print(op)";

        opiskele(h);
        opiskele(h);

        assertTrue("tarkista että Opiskelija:n toString on tuottaa tehtävänannon mukaisen merkkijonon\n"
                + "tarkista koodi " + k, h.toString().contains("opintopisteitä 2"));
    }

    // **************
    @Test
    @Points("08-02.4")
    public void luokkaOpettaja() {
        String klassName = opettajaLuokanNimi;
        Reflex.ClassRef<Object> classRef;
        classRef = Reflex.reflect(klassName);

        assertTrue("Luokan " + s(klassName) + " pitää olla julkinen, eli se tulee määritellä\n"
                + "public class " + klassName + " {...\n}", classRef.isPublic());
    }

    @Test
    @Points("08-02.4")
    public void opettajallaEiYlimaaraisiaMuuttujia() {
        String klassName = opettajaLuokanNimi;
        saniteettitarkastus(klassName, 1, "palkan muistavan oliomuuttujan\n"
                + "Nimi ja osoite on peritty yliluokasta.");
    }

    @Test
    @Points("08-02.4")
    public void opettajaPeriiLuokanHenkilo() {
        Class c = ReflectionUtils.findClass(opettajaLuokanNimi);

        Class sc = c.getSuperclass();
        assertTrue("Luokan Opettaja tulee periä luokka Henkilo", sc.getName().equals(henkiloLuokanNimi));
    }

    @Test
    @Points("08-02.4")
    public void opettajallaKolmiparametrinenKonstruktori() throws Throwable {
        newOpettaja("Joel Kaasinen", "Haagantie 123", 980);
    }

    @Test
    @Points("08-02.4")
    public void opettajanToStringKunnossa() throws Throwable {
        Object h = newOpettaja("Joel Kaasinen", "Haagantie 123", 980);
        assertEquals("tarkista että Opettaja:n toString on tuottaa tehtävänannon mukaisen merkkijonon\n"
                + "onko toStringisi tuottamassa merkkijonossa 2 rivinvaihtoa eli \\n-merkkiä? rivejä", 3, h.toString().split("\n").length);

        assertTrue("tarkista että Opettaja:n toString on tuottaa tehtävänannon mukaisen merkkijonon\n"
                + "toisen rivin pitäisi alkaa kahdella välilyönnillä!", h.toString().split("\n")[1].startsWith("  "));
        assertFalse("tarkista että Opettaja:n toString on tuottaa tehtävänannon mukaisen merkkijonon\n"
                + "toisen rivin pitäisi alkaa kahdella välilyönnillä!", h.toString().split("\n")[1].startsWith("   "));
        assertTrue("tarkista että Opettaja:n toString on tuottaa tehtävänannon mukaisen merkkijonon\n"
                + "kolmannen rivin pitäisi alkaa kahdella välilyönnillä!", h.toString().split("\n")[2].startsWith("  "));
        assertFalse("tarkista että Opettaja:n toString on tuottaa tehtävänannon mukaisen merkkijonon\n"
                + "kolmannen rivin pitäisi alkaa kahdella välilyönnillä!", h.toString().split("\n")[2].startsWith("   "));

        String k = "Opettaja op = new Opettaja(\"Joel Kaasinen\",\"Haagantie 123\",980); System.out.print(op)";

        String aa = h.toString();

        assertTrue("tarkista että Opettaja:n toString on tuottaa tehtävänannon mukaisen merkkijonon\n"
                + "tarkista koodi " + k, h.toString().contains("Joel Kaasinen"));
        assertTrue("tarkista että Opettaja:n toString on tuottaa tehtävänannon mukaisen merkkijonon\n"
                + "tarkista koodi " + k, h.toString().contains("Haagantie 123"));
        assertTrue("tarkista että Opettaja:n toString on tuottaa tehtävänannon mukaisen merkkijonon\n"
                + "koodilla " + k + " pitäisi tulostua rivi jossa lukee   palkka 980 euroa", h.toString().contains("palkka 980 euroa"));
    }
    // **************

    @Test
    @Points("08-02.5")
    public void tulostaHenkilotMetodiluokallaMain() throws Throwable {
        String metodi = "tulostaHenkilot";

        String virhe = "tee pääohjelmaluokkaan luokkametodi public static void " + metodi + "(ArrayList<Henkilo> henkilot)";

        assertTrue(virhe, Reflex.reflect(Main.class).staticMethod(metodi).returningVoid().taking(ArrayList.class).isPublic());

        String v = "ArrayList<Henkilo> lista = new ArrayList<Henkilo>();\n"
                + "lista.add( new Opiskelija(\"Pekka\",\"Korso\") );\n"
                + "tulostaHenkilot(lista)\n";

        ArrayList lista = new ArrayList();
        lista.add(newOpiskelija("Pekka", "Korso"));

        Reflex.reflect(Main.class).staticMethod(metodi).returningVoid().taking(ArrayList.class).withNiceError().invoke(lista);

    }

    public void tulosta(ArrayList lista) throws Throwable {
        String metodi = "tulostaHenkilot";

        Reflex.reflect(Main.class).staticMethod(metodi).returningVoid().taking(ArrayList.class).withNiceError().invoke(lista);
    }

    @Test
    @Points("08-02.5")
    public void tulostaHenkilotToimii1() throws Exception, Throwable {
        MockInOut io = new MockInOut("");

        ArrayList a = new ArrayList();
        a.add(newOpiskelija("Olli", "Ida Albergintie 1"));
        a.add(newOpettaja("Mikael Nousiainen", "Leppavaara", 3850));
        tulosta(a);
        String output = io.getOutput();
        String v = "Toimiiko metodi tulostaHenkilot oikein? \n"
                + "lisättin listalle new Opiskelija((\"Olli\", \"Ida Albergintie 1\") ja new Opettaja(\"Mikael Nousiainen\", \"Leppavaara\", 3850) \n"
                + "ja kutsuttiin metodia, tulostui: " + output;

        assertTrue(v, output.contains("Olli"));
        assertTrue(v, output.contains("Ida Albergintie 1"));
        assertTrue(v, output.contains("Leppavaara"));
        assertTrue(v, output.contains("Mikael Nousiainen"));
        assertFalse(v, output.contains("Oskari"));
        assertFalse(v, output.contains("Domus Academica"));
        assertFalse(v, output.contains("Korso"));
        assertFalse(v, output.contains("Pekka Mikkola"));
    }

    @Test
    @Points("08-02.5")
    public void tulostaHenkilotToimi2() throws Exception, Throwable {
        MockInOut io = new MockInOut("");
        ArrayList a = new ArrayList();
        a.add(newOpiskelija("Oskari", "Domus Academica"));
        a.add(newOpettaja("Pekka Mikkola", "Korso", 1105));
        tulosta(a);
        String output = io.getOutput();
        String v = "Toimiiko metodi tulostaHenkilot oikein?\n"
                + " lisättin listalle new Opiskelija(\"Oskari\", \"Domus Academica\") ja "
                + "new Opettaja(\"Pekka Mikkola\", \"Korso\", 1105) \nja kutsuttiin metodia, tulostui: " + output;

        assertTrue(v, output.contains("Oskari"));
        assertTrue(v, output.contains("Domus Academica"));
        assertTrue(v, output.contains("Korso"));
        assertTrue(v, output.contains("Pekka Mikkola"));
        assertFalse(v, output.contains("Olli"));
        assertFalse(v, output.contains("Ida Albergintie 1"));
        assertFalse(v, output.contains("Leppavaara"));
        assertFalse(v, output.contains("Mikael Nousiainen"));
    }

    /*
     *
     */
    private Object newHenkilo(String nimi, String osoite) throws Throwable {
        String klassName = henkiloLuokanNimi;
        Reflex.ClassRef<Object> classRef;
        classRef = Reflex.reflect(klassName);

        assertTrue("tee luokalle Henkilo konstruktori public Henkilo(String nimi, String osoite)", classRef.constructor().taking(String.class, String.class).isPublic());

        Reflex.MethodRef2<Object, Object, String, String> ctor = classRef.constructor().taking(String.class, String.class).withNiceError();
        return ctor.invoke(nimi, osoite);
    }

    private Object newOpiskelija(String nimi, String osoite) throws Throwable {
        String klassName = opiskelijaLuokanNimi;
        Reflex.ClassRef<Object> classRef;
        classRef = Reflex.reflect(klassName);

        assertTrue("tee luokalle Opiskelija konstruktori Opiskelija(String nimi, String osoite)", classRef.constructor().taking(String.class, String.class).isPublic());

        Reflex.MethodRef2<Object, Object, String, String> ctor = classRef.constructor().taking(String.class, String.class).withNiceError();
        return ctor.invoke(nimi, osoite);

    }

    private Object newOpettaja(String nimi, String osoite, int palkka) throws Throwable {
        String klassName = opettajaLuokanNimi;
        Reflex.ClassRef<Object> classRef;
        classRef = Reflex.reflect(klassName);

        assertTrue("tee luokalle Opettaja konstruktori public Opettaja(String nimi, String osoite, int palkka)",
                classRef.constructor().taking(String.class, String.class, int.class).isPublic());

        Reflex.MethodRef3<Object, Object, String, String, Integer> ctor = classRef
                .constructor().taking(String.class, String.class, int.class).withNiceError();
        return ctor.invoke(nimi, osoite, palkka);
    }

    private void opiskele(Object olio) throws Throwable {
        String klassName = opiskelijaLuokanNimi;
        Reflex.ClassRef<Object> classRef;
        classRef = Reflex.reflect(klassName);
        classRef.method(olio, "opiskele").returningVoid().takingNoParams().invoke();
    }

    private int opintopisteita(Object olio) throws Throwable {
        String klassName = opiskelijaLuokanNimi;
        Reflex.ClassRef<Object> classRef;
        classRef = Reflex.reflect(klassName);

        return classRef.method(olio, "opintopisteita").returning(int.class).takingNoParams().invoke();
    }

    private String toS(String[] nimiOs) {
        return nimiOs[0] + "\n  " + nimiOs[1];
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
            assertTrue("et tarvitse " + s(klassName) + "-luokalle kuin " + m + ", poista ylimääräiset", var <= n);
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
