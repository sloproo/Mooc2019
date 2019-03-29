
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.junit.Test;
import static org.junit.Assert.*;
import sovellus.Sensori;

public class SensoritJaLampotilaTest<_Sensori> {

    @Test
    @Points("10-12.1")
    public void luokkaVakiosensori() {
        String klassName = "sovellus.Vakiosensori";
        Reflex.ClassRef<Object> classRef;
        classRef = Reflex.reflect(klassName);

        assertTrue("Luokan " + s(klassName) + " pitää olla julkinen, eli se tulee määritellä\n"
                + "public class " + klassName + " {...\n}", classRef.isPublic());
    }

    @Test
    @Points("10-12.1")
    public void eiYlimaaraisiaMuuttujiaVakiosensorilla() {
        String klassName = "sovellus.Vakiosensori";
        saniteettitarkastus(klassName, 1, "aina palautettavan arvon muistavan oliomuuttujan");
    }

    @Test
    @Points("10-12.1")
    public void testaaVakiosensorinKonstruktori() throws Throwable {
        String klassName = "sovellus.Vakiosensori";
        Reflex.ClassRef<Object> classRef = Reflex.reflect(klassName);

        Reflex.MethodRef1<Object, Object, Integer> ctor = classRef.constructor().taking(int.class).withNiceError();
        assertTrue("Määrittele luokalle " + s(klassName) + " julkinen konstruktori: \n"
                + "public " + s(klassName) + "(int arvo)", ctor.isPublic());
        String v = "virheen aiheutti koodi new Vakiosensori(10);\n";
        ctor.withNiceError(v).invoke(10);
    }

    public Sensori newVakiosensori(int ti) throws Throwable {
        String klassName = "sovellus.Vakiosensori";
        Reflex.ClassRef<Object> classRef;
        classRef = Reflex.reflect(klassName);
        Reflex.MethodRef1<Object, Object, Integer> ctor = classRef.constructor().taking(int.class).withNiceError();
        return (Sensori) ctor.invoke(ti);
    }

    @Test
    @Points("10-12.1")
    public void VakiosensoriOnSensori() {
        String klassName = "sovellus.Vakiosensori";
        Class clazz = ReflectionUtils.findClass(klassName);

        boolean toteuttaaRajapinnan = false;
        Class kali = Sensori.class;
        for (Class iface : clazz.getInterfaces()) {
            if (iface.equals(kali)) {
                toteuttaaRajapinnan = true;
            }
        }

        if (!toteuttaaRajapinnan) {
            fail("Toteuttaahan luokka Vakiosensori rajapinnan Sensori?");
        }
    }

    @Test
    @Points("10-12.1")
    public void testaaVakiosensori() throws Throwable {
        String klassName = "sovellus.Vakiosensori";
        Reflex.ClassRef<Object> classRef = Reflex.reflect(klassName);

        Sensori vs10 = newVakiosensori(10);
        Sensori vs55 = newVakiosensori(55);

        String k1 = ""
                + "Vakiosensori v = new Vakiosensori(10);\n"
                + "v.mittaa();\n";

        String k2 = ""
                + "Vakiosensori v = new Vakiosensori(55);\n"
                + "v.mittaa();\n";

        assertEquals(k1, 10, (int) classRef.method(vs10, "mittaa").returning(int.class).takingNoParams().withNiceError(k1).invoke());
        assertEquals(k2, 55, (int) classRef.method(vs55, "mittaa").returning(int.class).takingNoParams().withNiceError(k2).invoke());

        k1 = ""
                + "Vakiosensori v = new Vakiosensori(10);\n"
                + "v.onPaalla();\n";

        k2 = ""
                + "Vakiosensori v = new Vakiosensori(55);\n"
                + "v.onPaalla();\n";

        assertEquals(k1, true, (boolean) classRef.method(vs10, "onPaalla").returning(boolean.class).takingNoParams().withNiceError(k1).invoke());
        assertEquals(k2, true, (boolean) classRef.method(vs55, "onPaalla").returning(boolean.class).takingNoParams().withNiceError(k1).invoke());

        k1 = ""
                + "Vakiosensori v = new Vakiosensori(10);\n"
                + "v.poisPaalta();\n";

        classRef.method(vs10, "poisPaalta").returningVoid().takingNoParams().withNiceError(k1).invoke();

        k1 = ""
                + "Vakiosensori v = new Vakiosensori(10);\n"
                + "v.poisPaalta();\n"
                + "v.onPaalla();\n";

        assertEquals(k1, true, (boolean) classRef.method(vs10, "onPaalla").returning(boolean.class).takingNoParams().withNiceError(k1).invoke());

        k1 = ""
                + "Vakiosensori v = new Vakiosensori(10);\n"
                + "v.paalle();\n";

        classRef.method(vs10, "paalle").returningVoid().takingNoParams().withNiceError(k1).invoke();

        k1 = ""
                + "Vakiosensori v = new Vakiosensori(10);\n"
                + "v.paalle();\n"
                + "v.onPaalla();\n";

        assertEquals(k1, true, (boolean) classRef.method(vs10, "onPaalla").returning(boolean.class).takingNoParams().withNiceError(k1).invoke());

    }

    /*
     *
     */
    @Test
    @Points("10-12.2")
    public void luokkaLampomittari() {
        String klassName = "sovellus.Lampomittari";
        Reflex.ClassRef<Object> classRef;
        classRef = Reflex.reflect(klassName);

        assertTrue("Luokan " + s(klassName) + " pitää olla julkinen, eli se tulee määritellä\n"
                + "public class " + klassName + " {...\n}", classRef.isPublic());
    }

    @Test
    @Points("10-12.2")
    public void eiYlimaaraisiaMuuttujiaLampomittarilla() {
        String klassName = "sovellus.Lampomittari";
        saniteettitarkastus(klassName, 2, "muuta kuin Random-olion (joka sekään ei ole pakollinen) sekä päälläolon muistavat oliomuuttujat");
    }

    @Test
    @Points("10-12.2")
    public void testaaLampomittarinKonstruktori() throws Throwable {
        String klassName = "sovellus.Lampomittari";
        Reflex.ClassRef<Object> classRef = Reflex.reflect(klassName);

        Reflex.MethodRef0<Object, Object> ctor = classRef.constructor().takingNoParams().withNiceError();
        assertTrue("Määrittele luokalle " + s(klassName) + " julkinen konstruktori: \n"
                + "public " + s(klassName) + "()", ctor.isPublic());
        String v = "virheen aiheutti koodi new Lampomittari();\n";
        ctor.withNiceError(v).invoke();
    }

    @Test
    @Points("10-12.2")
    public void LampomittariOnSensori() {
        String klassName = "sovellus.Lampomittari";
        Class clazz = ReflectionUtils.findClass(klassName);

        boolean toteuttaaRajapinnan = false;
        Class kali = Sensori.class;
        for (Class iface : clazz.getInterfaces()) {
            if (iface.equals(kali)) {
                toteuttaaRajapinnan = true;
            }
        }

        if (!toteuttaaRajapinnan) {
            fail("Toteuttaahan luokka Lampomittari rajapinnan Sensori?");
        }
    }

    public Sensori newLampomittari() throws Throwable {
        String klassName = "sovellus.Lampomittari";
        Reflex.ClassRef<Object> classRef;
        classRef = Reflex.reflect(klassName);
        Reflex.MethodRef0<Object, Object> ctor = classRef.constructor().takingNoParams().withNiceError();
        return (Sensori) ctor.invoke();
    }

    @Test
    @Points("10-12.2")
    public void testaaLampomittari() throws Throwable {
        String klassName = "sovellus.Lampomittari";
        Reflex.ClassRef<Object> classRef = Reflex.reflect(klassName);

        Sensori mittari1 = newLampomittari();

        // alussa ei päällä
        String k1 = ""
                + "Lampomittari v = new Lampomittari();\n"
                + "v.onPaalla();\n";

        assertEquals(k1, false, (boolean) classRef.method(mittari1, "onPaalla").returning(boolean.class).takingNoParams().withNiceError(k1).invoke());

        // päälle
        k1 = ""
                + "Lampomittari v = new Lampomittari();\n"
                + "v.paalle();\n";

        classRef.method(mittari1, "paalle").returningVoid().takingNoParams().withNiceError(k1).invoke();

        k1 = ""
                + "Lampomittari v = new Lampomittari();\n"
                + "v.paalle();\n"
                + "v.onPaalla();\n";

        assertEquals(k1, true, (boolean) classRef.method(mittari1, "onPaalla").returning(boolean.class).takingNoParams().withNiceError(k1).invoke());

        // mittaukset
        k1 = ""
                + "Lampomittari v = new Lampomittari();\n"
                + "v.mittaa();\n";

        Set tulokset = new TreeSet();
        for (int i = 0; i < 1000; i++) {
            int tulos = (int) classRef.method(mittari1, "mittaa").returning(int.class).takingNoParams().withNiceError(k1).invoke();
            assertTrue("Lämpötilan piti olla välillä -30...30, mutta:\n" + k1 + " \n" + tulos, tulos > -31 && tulos < 31);
            tulokset.add(tulos);
        }
        assertTrue("Luotiin Lampomittari v = new Lampomittari(); ja kutsuttiin v.mittaa() tuhat kertaa.\n"
                + "lämpötilat piti arpoa väliltä -30...30. Ei kuitenkaan saatu muita lämpötiloja kuin\n"
                + tulokset.toString(), tulokset.size() > 50);

        // pois päältä
        k1 = ""
                + "Lampomittari v = new Lampomittari();\n"
                + "v.poisPaalta();\n";

        classRef.method(mittari1, "poisPaalta").returningVoid().takingNoParams().withNiceError(k1).invoke();

        k1 = ""
                + "Lampomittari v = new Lampomittari();\n"
                + "v.poisPaalta();\n"
                + "v.onPaalla();\n";

        assertEquals(k1, false, (boolean) classRef.method(mittari1, "onPaalla").returning(boolean.class).takingNoParams().withNiceError(k1).invoke());

        k1 = ""
                + "Lampomittari v = new Lampomittari();\n"
                + "v.poisPaalta();\n"
                + "v.mittaa();\n";
        try {
            classRef.method(mittari1, "mittaa").returning(int.class).takingNoParams().withNiceError(k1).invoke();
            fail("Olisi pitänyt heittää poikkeus IllegalStateException() kun suoritettiin\n"
                    + k1);
        } catch (Throwable e) {
        }
    }

    /*
     *
     */
    @Test
    @Points("10-12.3")
    public void luokkaKeskiarvosensori() {
        String klassName = "sovellus.Keskiarvosensori";
        Reflex.ClassRef<Object> classRef;
        classRef = Reflex.reflect(klassName);

        assertTrue("Luokan " + s(klassName) + " pitää olla julkinen, eli se tulee määritellä\n"
                + "public class " + klassName + " {...\n}", classRef.isPublic());
    }

    @Test
    @Points("10-12.3")
    public void eiYlimaaraisiaMuuttujiaKeskiarvosensorilla() {
        String klassName = "sovellus.Keskiarvosensori";
        saniteettitarkastus(klassName, 2, "hallittavat sensorit sekä mittaukset muistavat oliomuuttujat\n"
                + "Päälläolotietoa ei kannata ylläpitää suoraan keskiarvosensorissa vaan se kannattaa kysellä hallinnassa olevilta"
                + "sensoreilta.");
    }

    @Test
    @Points("10-12.3")
    public void testaaKeskiarvosensorinKonstruktori() throws Throwable {
        String klassName = "sovellus.Keskiarvosensori";
        Reflex.ClassRef<Object> classRef = Reflex.reflect(klassName);

        Reflex.MethodRef0<Object, Object> ctor = classRef.constructor().takingNoParams().withNiceError();
        assertTrue("Määrittele luokalle " + s(klassName) + " julkinen konstruktori: \n"
                + "public " + s(klassName) + "()", ctor.isPublic());
        String v = "virheen aiheutti koodi new Lampomittari();\n";
        ctor.withNiceError(v).invoke();
    }

    @Test
    @Points("10-12.3")
    public void KeskiarvosensoriOnSensori() {
        String klassName = "sovellus.Keskiarvosensori";
        Class clazz = ReflectionUtils.findClass(klassName);

        boolean toteuttaaRajapinnan = false;
        Class kali = Sensori.class;
        for (Class iface : clazz.getInterfaces()) {
            if (iface.equals(kali)) {
                toteuttaaRajapinnan = true;
            }
        }

        if (!toteuttaaRajapinnan) {
            fail("Toteuttaahan luokka Keskiarvosensori rajapinnan Sensori?");
        }
    }

    public Sensori newKeskiarvosensori() throws Throwable {
        String klassName = "sovellus.Keskiarvosensori";
        Reflex.ClassRef<Object> classRef;
        classRef = Reflex.reflect(klassName);
        Reflex.MethodRef0<Object, Object> ctor = classRef.constructor().takingNoParams().withNiceError();
        return (Sensori) ctor.invoke();
    }

    @Test
    @Points("10-12.3")
    public void onMetodisensorienLisaamiseenKeskiarvosensorille() throws Throwable {
        String klassName = "sovellus.Keskiarvosensori";
        Reflex.ClassRef<Object> classRef = Reflex.reflect(klassName);

        Sensori mittari1 = newLampomittari();

        String k1 = "Virheen aiheutti koodi\n"
                + "Keskiarvosensori ka = new Keskiarvosensori();\n"
                + "ka.lisaaSensori( new Lampomittari() );\n";

        Sensori ka = newKeskiarvosensori();

        assertTrue("Lisää luokalle Keskiarvosensori metodi public void lisaaSensori(Sensori lisattava)", classRef.method(ka, "lisaaSensori").returningVoid().taking(Sensori.class).isPublic());

        classRef.method(ka, "lisaaSensori").returningVoid().taking(Sensori.class).withNiceError(k1).invoke(mittari1);
    }

    @Test
    @Points("10-12.3")
    public void keskiarvonMittausToimii() throws Throwable {
        String klassName = "sovellus.Keskiarvosensori";
        Reflex.ClassRef<Object> classRef = Reflex.reflect(klassName);

        Sensori mittari1 = newVakiosensori(4);

        String koodi = "Virheen aiheutti koodi\n"
                + "Keskiarvosensori ka = new Keskiarvosensori();\n"
                + "ka.lisaaSensori( new Vakiosensori(4) );\n"
                + "ka.mittaa();\n";

        Sensori ka = newKeskiarvosensori();

        classRef.method(ka, "lisaaSensori").returningVoid().taking(Sensori.class).invoke(mittari1);

        classRef.method(ka, "mittaa").returning(int.class).takingNoParams().withNiceError(koodi).invoke();

        assertEquals(koodi, 4, ka.mittaa());

        koodi = "Virheen aiheutti koodi\n"
                + "Keskiarvosensori ka = new Keskiarvosensori();\n"
                + "ka.lisaaSensori( new Vakiosensori(4) );\n"
                + "ka.lisaaSensori( new Vakiosensori(5) );\n"
                + "ka.lisaaSensori( new Vakiosensori(9) );\n"
                + "ka.mittaa();\n";

        classRef.method(ka, "lisaaSensori").returningVoid().taking(Sensori.class).withNiceError(koodi).invoke(newVakiosensori(5));
        classRef.method(ka, "lisaaSensori").returningVoid().taking(Sensori.class).withNiceError(koodi).invoke(newVakiosensori(9));

        classRef.method(ka, "mittaa").returning(int.class).takingNoParams().withNiceError(koodi).invoke();
        assertEquals(koodi, 6, ka.mittaa());

    }

    @Test
    @Points("10-12.3")
    public void keskiarvosensoriPaalleJaPois() throws Throwable {
        String klassName = "sovellus.Keskiarvosensori";
        Reflex.ClassRef<Object> classRef = Reflex.reflect(klassName);

        Sensori mittari1 = newLampomittari();

        String koodi = ""
                + "Keskiarvosensori ka = new Keskiarvosensori();\n"
                + "ka.lisaaSensori( new Lampomittari() );\n"
                + "ka.onPaalla();\n";

        Sensori ka = newKeskiarvosensori();

        classRef.method(ka, "lisaaSensori").returningVoid().taking(Sensori.class).invoke(mittari1);

        assertEquals("Koska lämpömittari on aluksi pois päältä, ei keskiarvosensorinkaan pitäisi olla päällä\n"
                + "" + koodi, false, classRef.method(ka, "onPaalla").returning(boolean.class).takingNoParams().withNiceError(koodi).invoke());

        koodi = "Lampomittari mittari = new Lampomittari();\n"
                + "mittari.paalle();\n"
                + "Keskiarvosensori ka = new Keskiarvosensori();\n"
                + "ka.lisaaSensori( mittari);\n"
                + "ka.onPaalla();\n";

        mittari1 = newLampomittari();
        mittari1.paalle();
        ka = newKeskiarvosensori();
        classRef.method(ka, "lisaaSensori").returningVoid().taking(Sensori.class).invoke(mittari1);

        assertEquals("Jos kesiarvosensorin ainoa hallitsema sensori on päällä,"
                + " pitäisi keskiarvosensorin olla päällä\n"
                + "" + koodi, true, classRef.method(ka, "onPaalla").returning(boolean.class).takingNoParams().withNiceError(koodi).invoke());

        koodi = "Lampomittari mittari = new Lampomittari();\n"
                + "mittari.paalle();\n"
                + "Keskiarvosensori ka = new Keskiarvosensori();\n"
                + "ka.lisaaSensori( mittari);\n"
                + "ka.poisPaalta();\n"
                + "ka.paalla();\n";

        mittari1 = newLampomittari();
        mittari1.paalle();
        ka = newKeskiarvosensori();
        classRef.method(ka, "lisaaSensori").returningVoid().taking(Sensori.class).invoke(mittari1);
        classRef.method(ka, "poisPaalta").returningVoid().takingNoParams().withNiceError(koodi).invoke();

        assertEquals(koodi, false, classRef.method(ka, "onPaalla").returning(boolean.class).takingNoParams().withNiceError(koodi).invoke());

        koodi
                = "Lampomittari mittari = new Lampomittari();\n"
                + "mittari.paalle();\n"
                + "Keskiarvosensori ka = new Keskiarvosensori();\n"
                + "ka.lisaaSensori( mittari );\n"
                + "ka.poisPaalta();\n"
                + "mittari.paalla();\n";

        assertEquals(koodi, false, mittari1.onPaalla());
    }

    @Test
    @Points("10-12.3")
    public void keskiarvosensoriPaalleJaPois2() throws Throwable {
        String klassName = "sovellus.Keskiarvosensori";
        Reflex.ClassRef<Object> classRef = Reflex.reflect(klassName);

        Sensori mittari1 = newLampomittari();
        Sensori mittari2 = newLampomittari();

        String koodi = ""
                + "Keskiarvosensori ka = new Keskiarvosensori();\n"
                + "Lampomittari mittari1 = new Lampomittari();\n"
                + "Lampomittari mittari2 = new Lampomittari();\n"
                + "ka.lisaaSensori(mittari1);\n"
                + "ka.lisaaSensori(mittari2);\n"
                + "ka.paalla();\n";

        Sensori ka = newKeskiarvosensori();

        classRef.method(ka, "lisaaSensori").returningVoid().taking(Sensori.class).invoke(mittari1);
        classRef.method(ka, "lisaaSensori").returningVoid().taking(Sensori.class).invoke(mittari2);

        assertEquals(koodi, false, classRef.method(ka, "onPaalla").returning(boolean.class).takingNoParams().withNiceError(koodi).invoke());

        ka.paalle();

        koodi = ""
                + "Keskiarvosensori ka = new Keskiarvosensori();\n"
                + "Lampomittari mittari1 = new Lampomittari();\n"
                + "Lampomittari mittari2 = new Lampomittari();\n"
                + "ka.lisaaSensori(mittari1);\n"
                + "ka.lisaaSensori(mittari2);\n"
                + "ka.paalle();\n"
                + "ka.paalla();\n";

        assertEquals(koodi, true, ka.onPaalla());

        koodi = ""
                + "Keskiarvosensori ka = new Keskiarvosensori();\n"
                + "Lampomittari mittari1 = new Lampomittari();\n"
                + "Lampomittari mittari2 = new Lampomittari();\n"
                + "ka.lisaaSensori(mittari1);\n"
                + "ka.lisaaSensori(mittari2);\n"
                + "ka.paalle();\n"
                + "mittari1.paalla();\n";

        assertEquals(koodi, true, mittari1.onPaalla());

        koodi = ""
                + "Keskiarvosensori ka = new Keskiarvosensori();\n"
                + "Lampomittari mittari1 = new Lampomittari();\n"
                + "Lampomittari mittari2 = new Lampomittari();\n"
                + "ka.lisaaSensori(mittari1);\n"
                + "ka.lisaaSensori(mittari2);\n"
                + "ka.paalle();\n"
                + "mittari2.paalla();\n";

        assertEquals(koodi, true, mittari2.onPaalla());
    }

    @Test
    @Points("10-12.3")
    public void poikkeusJosKeskiarvomittarillaMitataanJaEiPaalla() throws Throwable {
        String klassName = "sovellus.Keskiarvosensori";
        Reflex.ClassRef<Object> classRef = Reflex.reflect(klassName);

        Sensori mittari1 = newLampomittari();
        Sensori mittari2 = newLampomittari();

        String koodi = ""
                + "Keskiarvosensori ka = new Keskiarvosensori();\n"
                + "Lampomittari mittari1 = new Lampomittari();\n"
                + "Lampomittari mittari2 = new Lampomittari();\n"
                + "ka.lisaaSensori( mittari1);\n"
                + "ka.lisaaSensori( mittari2);\n"
                + "ka.mittaa();\n";

        Sensori ka = newKeskiarvosensori();

        classRef.method(ka, "lisaaSensori").returningVoid().taking(Sensori.class).invoke(mittari1);
        classRef.method(ka, "lisaaSensori").returningVoid().taking(Sensori.class).invoke(mittari2);

        try {
            classRef.method(ka, "mittaa").returning(int.class).takingNoParams().withNiceError(koodi).invoke();
            fail("Olisi pitänyt heittää poikkeus IllegalStateException() kun suoritettiin\n"
                    + koodi);
        } catch (Throwable e) {
        }
    }

    /*
     *
     */
    @Test
    @Points("10-12.4")
    public void keskiarvosensorilaMetodiMittaukset() throws Throwable {
        String klassName = "sovellus.Keskiarvosensori";
        Reflex.ClassRef<Object> classRef = Reflex.reflect(klassName);

        String k1 = "Virheen aiheutti koodi\n"
                + "Keskiarvosensori ka = new Keskiarvosensori();\n"
                + "ka.mittaukset();\n";

        Sensori ka = newKeskiarvosensori();

        assertTrue("Lisää luokalle Keskiarvosensori metodi public List<Integer> mittaukset()\n",
                classRef.method(ka, "mittaukset").returning(List.class).takingNoParams().isPublic());

        classRef.method(ka, "mittaukset").returning(List.class).takingNoParams().withNiceError(k1).invoke();
    }

    @Test
    @Points("10-12.4")
    public void keskiarvosensorilaMetodiMittauksetToimii() throws Throwable {
        String klassName = "sovellus.Keskiarvosensori";
        Reflex.ClassRef<Object> classRef = Reflex.reflect(klassName);

        Sensori mittari1 = newLampomittari();

        String koodi = ""
                + "Keskiarvosensori ka = new Keskiarvosensori();\n"
                + "ka.lisaaSensori( new Vakiosensori(3) );\n"
                + "ka.lisaaSensori( new Vakiosensori(7) );\n"
                + "ka.mittaukset();\n";

        Sensori ka = newKeskiarvosensori();

        classRef.method(ka, "lisaaSensori").returningVoid().taking(Sensori.class).invoke(newVakiosensori(3));
        classRef.method(ka, "lisaaSensori").returningVoid().taking(Sensori.class).invoke(newVakiosensori(7));

        assertTrue("Jos mitauksia ei ole, palauta tyhjä lista.\n"
                + "Nyt palautettiin null koodilla\n" + koodi, classRef.method(ka, "mittaukset").returning(List.class).takingNoParams().withNiceError(koodi).invoke() != null);
        assertTrue("Palautetun listan olisi pitänyt olla tyhjä koodilla\n" + koodi, classRef.method(ka, "mittaukset").returning(List.class).takingNoParams().withNiceError(koodi).invoke().isEmpty());

        classRef.method(ka, "mittaa").returning(int.class).takingNoParams().withNiceError(koodi).invoke();

        koodi = ""
                + "Keskiarvosensori ka = new Keskiarvosensori();\n"
                + "ka.lisaaSensori( new Vakiosensori(3) );\n"
                + "ka.lisaaSensori( new Vakiosensori(7) );\n"
                + "ka.mittaa();\n"
                + "ka.mittaukset();\n";
        assertTrue("Palautettiin null koodilla\n" + koodi, classRef.method(ka, "mittaukset").returning(List.class).takingNoParams().withNiceError(koodi).invoke() != null);
        List l = classRef.method(ka, "mittaukset").returning(List.class).takingNoParams().withNiceError(koodi).invoke();
        assertTrue("Palautetun listan olisi pitänyt olla pituudeltaan 1 koodilla\n" + koodi
                + "\npalautit: " + l, l.size() == 1);

        assertTrue("Palautetun listan olisi pitänyt sisältää ainoastaan luku 5 koodilla" + koodi + ""
                + "\npalautit: " + l, classRef.method(ka, "mittaukset").returning(List.class).takingNoParams().withNiceError(koodi).invoke().get(0).equals(Integer.valueOf(5)));

        koodi = ""
                + "Keskiarvosensori ka = new Keskiarvosensori();\n"
                + "ka.lisaaSensori( new Vakiosensori(3) );\n"
                + "ka.lisaaSensori( new Vakiosensori(7) );\n"
                + "ka.mittaa();\n"
                + "ka.mittaa();\n"
                + "ka.mittaa();\n"
                + "ka.mittaukset();\n";

        classRef.method(ka, "mittaa").returning(int.class).takingNoParams().withNiceError(koodi).invoke();
        classRef.method(ka, "mittaa").returning(int.class).takingNoParams().withNiceError(koodi).invoke();

        assertTrue("Palautettiin null koodilla\n" + koodi, classRef.method(ka, "mittaukset").returning(List.class).takingNoParams().withNiceError(koodi).invoke() != null);
        assertTrue("Palautetun listan olisi pitänyt olla pituudeltaan 3 koodilla\n" + koodi + ""
                + "\npalautit: " + l, classRef.method(ka, "mittaukset").returning(List.class).takingNoParams().withNiceError(koodi).invoke().size() == 3);
        l = classRef.method(ka, "mittaukset").returning(List.class).takingNoParams().withNiceError(koodi).invoke();
        assertTrue("Palautetun listan olisi pitänyt sisältää ainoastaan kolme kertaa luku 5 koodilla" + koodi + ""
                + "\npalautit: " + l, l.get(0).equals(Integer.valueOf(5)) && l.get(1).equals(Integer.valueOf(5)) && l.get(2).equals(Integer.valueOf(5)));
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
        return klassName.substring(klassName.lastIndexOf(".") + 1);
    }
}
