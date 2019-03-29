
import fi.helsinki.cs.tmc.edutestutils.MockInOut;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Scanner;
import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Test;

public class EnsimmaisiaPakkauksiaTest {

    @Test
    @Points("10-08.1")
    public void onKayttoliittymaRajapinta() {
        Class clazz = null;
        try {
            clazz = ReflectionUtils.findClass("mooc.ui.Kayttoliittyma");
        } catch (Throwable e) {
            fail("Luo pakkaus mooc.ui ja sinne rajapinta Kayttoliittyma:\n\n"
                    + "public interface Kayttoliittyma{\n  \\\\...\n}");
        }

        if (clazz == null) {
            fail("Olethan luonut rajapinnan Kayttoliittyma pakkaukseen mooc.ui ja onhan sillä määre public?");
        }

        if (!clazz.isInterface()) {
            fail("Onhan Kayttoliittyma rajapinta?");
        }

        boolean loytyi = false;
        for (Method m : clazz.getMethods()) {
            if (!m.getReturnType().equals(void.class)) {
                continue;
            }

            if (!m.getName().equals("paivita")) {
                continue;
            }

            loytyi = true;
        }

        if (!loytyi) {
            fail("Onhan rajapinnalla Kayttoliittyma metodi void paivita()?");
        }
    }

    /*
     *
     */
    @Test
    @Points("10-08.2")
    public void onTekstikayttoliittyma() {
        Class clazz = ReflectionUtils.findClass("mooc.ui.Tekstikayttoliittyma");

        if (clazz == null) {
            fail("Olethan luonut luokan Tekstikayttoliittyma pakkaukseen mooc.ui ja onhan sillä määre public?");
        }

        Class kali = ReflectionUtils.findClass("mooc.ui.Kayttoliittyma");
        for (Class iface : clazz.getInterfaces()) {
            if (iface.equals(kali)) {
                return;
            }
        }

        fail("Toteuttaahan luokka Tekstikayttoliittyma rajapinnan Kayttoliittyma?");
    }

    @Test
    @Points("10-08.2")
    public void tekstikayttoliittymaTulostaa() {
        Class clazz = ReflectionUtils.findClass("mooc.ui.Tekstikayttoliittyma");
        Object kali = luoTekstikayttoliittyma();

        MockInOut io = new MockInOut("");

        Method m = ReflectionUtils.requireMethod(clazz, "paivita");
        try {
            m.invoke(kali);
        } catch (Throwable t) {
            fail("Koodin suorituksessa tapahtui virhe. Tarkasta mitä tapahtuu kun suoritat koodin\n"
                    + "Kayttoliittyma kali = new Tekstikayttoliittyma();\n"
                    + "kali.paivita()\n");
        }

        Assert.assertTrue("Tulostaahan Tekstikayttoliittymaluokan paivita-metodikutsu jotain? \n"
                + "Nyt tulostui:\n" + io.getOutput(), io.getOutput() != null && io.getOutput().length() > 1);
        Assert.assertTrue("Tekstikayttoliittymaluokan paivita-metodikutsun pitäisi tulostaa rivinvaihto \n"
                + "Nyt tulostui:\n" + io.getOutput(), io.getOutput() != null && io.getOutput().contains("\n"));
        Assert.assertTrue("Tekstikayttoliittymaluokan paivita-metodikutsun pitäisi tulostaa vain yksi rivi. \n"
                + "Nyt tulostui:\n" + io.getOutput(), io.getOutput() != null && io.getOutput().split("\n").length == 1);
    }

    /*
     *
     */
    @Test
    @Points("10-08.3")
    public void onSovelluslogiikka() {
        Class sovellusLogiikkaClass = ReflectionUtils.findClass("mooc.logiikka.Sovelluslogiikka");

        if (sovellusLogiikkaClass == null) {
            fail("Olethan luonut luokan Sovelluslogiikka pakkaukseen mooc.logiikka ja onhan sillä määre public?");
        }
        Class kaliClass = ReflectionUtils.findClass("mooc.ui.Kayttoliittyma");

        Constructor constructor = ReflectionUtils.requireConstructor(sovellusLogiikkaClass, kaliClass);
        if (constructor == null) {
            fail("Onhan luokalla Sovelluslogiikka konstruktori public Sovelluslogiikka(Kayttoliittyma kayttoliittyma)?");
        }

        Method m = null;

        try {
            m = ReflectionUtils.requireMethod(sovellusLogiikkaClass, "suorita", int.class);
        } catch (Throwable t) {
            fail("Tee luokalle Sovelluslogiikka metodi public void suorita(int montaKertaa)");
        }

        assertTrue("Tee luokalle Sovelluslogiikka metodi public void suorita(int montaKertaa)", m.toString().contains("public"));
    }

    @Test
    @Points("10-08.3")
    public void eiYlimaaraisiaMuuttujia() {
        saniteettitarkastus("mooc.logiikka.Sovelluslogiikka", 1, "Kayttoliittyma-tyyppisen oliomuuttujan");
    }

    @Test
    @Points("10-08.3")
    public void luodaanSovellusLogiikkaOlio() throws IllegalArgumentException, IllegalAccessException {
        Class sovellusLogiikkaClass = ReflectionUtils.findClass("mooc.logiikka.Sovelluslogiikka");
        Object sovelluslogiikka = luoSovellusLogiikkaOlio();
        if (sovelluslogiikka == null) {
            fail("Onhan luokalla Sovelluslogiikka konstruktori public Sovelluslogiikka(Kayttoliittyma kayttoliittyma)");
        }

        Method suoritaMethod = ReflectionUtils.requireMethod(sovellusLogiikkaClass, "suorita", int.class);

        Class clazz = ReflectionUtils.findClass("mooc.ui.Tekstikayttoliittyma");
        Object kali = luoTekstikayttoliittyma();

        MockInOut io = new MockInOut("");

        Method m = ReflectionUtils.requireMethod(clazz, "paivita");
        try {
            m.invoke(kali);
        } catch (Throwable t) {
            fail("Koodin suorituksessa tapahtui virhe. Tarkasta mitä tapahtuu kun suoritat koodin\n"
                    + "Kayttoliittyma kali = new Tekstikayttoliittyma();\n"
                    + "kali.paivita()\n");
        }

        Assert.assertTrue("Tulostaahan Tekstikayttoliittymaluokan paivita-metodikutsu jotain?", io.getOutput() != null && io.getOutput().length() > 5);
        String output = io.getOutput();

        Field[] kentat = ReflectionUtils.findClass("mooc.logiikka.Sovelluslogiikka").getDeclaredFields();
        assertTrue("Onko luokalla Sovelluslogiikka Kayttoliittyma-tyyppinen oliomuuttuja?", kentat.length == 1);
        assertTrue("Onko luokalla Sovelluslogiikka Kayttoliittyma-tyyppinen oliomuuttuja?", kentat[0].toString().contains("Kayttoliittyma"));
        kentat[0].setAccessible(true);
        assertFalse("Sovelluslogiikan oliomuuttuja " + kentat[0].toString().replace("mooc.logiikka.Sovelluslogiikka.", "") + " "
                + "null. \nAseta muuttujalle arvoksi konstruktorissa välitetty käyttöliittymäolio!", kentat[0].get(sovelluslogiikka) == null);

        try {
            suoritaMethod.invoke(sovelluslogiikka, 3);
        } catch (Throwable t) {
            fail("Sovelluslogiikan suorituksessa tapahtui virhe. Tarkasta mitä tapahtuu kun suoritat koodin\n"
                    + "Kayttoliittyma kali = new Tekstikayttoliittyma();\n"
                    + "Sovelluslogiikka sovellus = new Sovelluslogiikka(kali);\n"
                    + "sovellus.suorita(3);");
        }

        String suoritaOutput = io.getOutput().substring(output.length());

        assertTrue("Koodilla\n"
                + "Kayttoliittyma kali = new Tekstikayttoliittyma();\n"
                + "Sovelluslogiikka sovellus = new Sovelluslogiikka(kali);\n"
                + "sovellus.suorita(3);\n"
                + "Pitäisi tulostua 6 riviä\nTulostus oli\n" + suoritaOutput, suoritaOutput.split("\n").length > 5 && suoritaOutput.split("\n").length < 8);

        if (suoritaOutput.length() < output.length() * 3) {
            fail("Kutsutaanhan Kayttoliittyma-rajapinnan metodia paivita() luokan tasan parametrin montaKertaa kertaa Sovelluslogiikka suorita-metodikutsun toistolausekkeessa?");
        }
    }

    @Test
    @Points("10-08.3")
    public void toinenSovellus() throws IllegalArgumentException, IllegalAccessException {
        Class sovellusLogiikkaClass = ReflectionUtils.findClass("mooc.logiikka.Sovelluslogiikka");
        Object sovelluslogiikka = luoSovellusLogiikkaOlio();
        if (sovelluslogiikka == null) {
            fail("Onhan luokalla Sovelluslogiikka konstruktori public Sovelluslogiikka(Kayttoliittyma kayttoliittyma)");
        }

        Method suoritaMethod = ReflectionUtils.requireMethod(sovellusLogiikkaClass, "suorita", int.class);

        Class clazz = ReflectionUtils.findClass("mooc.ui.Tekstikayttoliittyma");
        Object kali = luoTekstikayttoliittyma();

        MockInOut io = new MockInOut("");

        Method m = ReflectionUtils.requireMethod(clazz, "paivita");
        try {
            m.invoke(kali);
        } catch (Throwable t) {
            fail("Koodin suorituksessa tapahtui virhe. Tarkasta mitä tapahtuu kun suoritat koodin\n"
                    + "Kayttoliittyma kali = new Tekstikayttoliittyma();\n"
                    + "kali.paivita()\n");
        }

        Assert.assertTrue("Tulostaahan Tekstikayttoliittymaluokan paivita-metodikutsu jotain?", io.getOutput() != null && io.getOutput().length() > 5);
        String output = io.getOutput();

        Field[] kentat = ReflectionUtils.findClass("mooc.logiikka.Sovelluslogiikka").getDeclaredFields();
        assertTrue("Onko luokalla Sovelluslogiikka Kayttoliittyma-tyyppinen oliomuuttuja?", kentat.length == 1);
        assertTrue("Onko luokalla Sovelluslogiikka Kayttoliittyma-tyyppinen oliomuuttuja?", kentat[0].toString().contains("Kayttoliittyma"));
        kentat[0].setAccessible(true);
        assertFalse("Sovelluslogiikan oliomuuttuja " + kentat[0].toString().replace("mooc.logiikka.Sovelluslogiikka.", "") + " "
                + "null. \nAseta muuttujalle arvoksi konstruktorissa välitetty käyttöliittymäolio!", kentat[0].get(sovelluslogiikka) == null);

        try {
            suoritaMethod.invoke(sovelluslogiikka, 5);
        } catch (Throwable t) {
            fail("Sovelluslogiikan suorituksessa tapahtui virhe. Tarkasta mitä tapahtuu kun suoritat koodin\n"
                    + "Kayttoliittyma kali = new Tekstikayttoliittyma();\n"
                    + "Sovelluslogiikka sovellus = new Sovelluslogiikka(kali);\n"
                    + "sovellus.suorita(3);");
        }

        String suoritaOutput = io.getOutput().substring(output.length());

        assertTrue("Koodilla\n"
                + "Kayttoliittyma kali = new Tekstikayttoliittyma();\n"
                + "Sovelluslogiikka sovellus = new Sovelluslogiikka(kali);\n"
                + "sovellus.suorita(5);\n"
                + "Pitäisi tulostua 10 riviä\nTulostus oli\n" + suoritaOutput, suoritaOutput.split("\n").length > 9 && suoritaOutput.split("\n").length < 12);
    }

    @Test
    @Points("10-08.3")
    public void sovelluslogiikkaKutsuuKayttoliittymaa() {
        Field[] kentat = ReflectionUtils.findClass("mooc.logiikka.Sovelluslogiikka").getDeclaredFields();
        String muuttuja = kentat[0].toString();
        muuttuja = muuttuja.substring(muuttuja.lastIndexOf(".") + 1);
        assertTrue("Luokan Sovelluslogiikka metodin suorita on kutsuttava käyttöliittymän metodia paivita!", sisaltaaKutsun(muuttuja + ".paivita()"));
    }

    private boolean sisaltaaKutsun(String kutsu) {

        try {
            Scanner lukija = new Scanner(new File("src/main/java/mooc/logiikka/Sovelluslogiikka.java"));
            int metodissa = 0;

            while (lukija.hasNextLine()) {

                String rivi = lukija.nextLine();

                if (rivi.indexOf("//") > -1) {
                    continue;
                }

                if (metodissa > 0 && rivi.contains(kutsu)) {
                    return true;
                }

                if (rivi.contains("void") && rivi.contains("suorita")) {
                    metodissa++;
                } else if (metodissa > 0) {
                    if (rivi.contains("{") && !rivi.contains("}")) {
                        metodissa++;
                    }

                    if (rivi.contains("}") && !rivi.contains("{")) {
                        metodissa--;
                    }
                }

            }

        } catch (Exception e) {
            fail(e.getMessage());
        }

        return false;
    }

    private Object luoTekstikayttoliittyma() {
        Class clazz = ReflectionUtils.findClass("mooc.ui.Tekstikayttoliittyma");
        Object kali = null;
        try {
            kali = ReflectionUtils.invokeConstructor(ReflectionUtils.requireConstructor(clazz));
        } catch (Throwable ex) {
        }

        if (kali == null) {
            fail("Onhan luokalla Tekstikayttoliittyma parametriton konstruktori?");
        }

        return kali;
    }

    private Object luoSovellusLogiikkaOlio() {
        Class sovellusLogiikkaClass = ReflectionUtils.findClass("mooc.logiikka.Sovelluslogiikka");

        if (sovellusLogiikkaClass == null) {
            fail("Olethan luonut luokan Sovelluslogiikka pakkaukseen mooc.logiikka ja onhan sillä määre public?");
        }
        Class kaliClass = ReflectionUtils.findClass("mooc.ui.Kayttoliittyma");

        Constructor constructor = ReflectionUtils.requireConstructor(sovellusLogiikkaClass, kaliClass);

        try {
            return ReflectionUtils.invokeConstructor(constructor, luoTekstikayttoliittyma());
        } catch (Throwable ex) {
        }

        return null;
    }

    private void saniteettitarkastus(String klassName, int n, String m) throws SecurityException {
        Field[] kentat = ReflectionUtils.findClass(klassName).getDeclaredFields();

        for (Field field : kentat) {
            assertFalse("et tarvitse \"stattisia muuttujia\", poista luokalta " + klassName + " muuttuja " + kentta(field.toString(), klassName), field.toString().contains("static") && !field.toString().contains("final"));
            assertTrue("luokan kaikkien oliomuuttujien näkyvyyden tulee olla private, muuta luokalta " + klassName + " löytyi: " + kentta(field.toString(), klassName), field.toString().contains("private"));
        }

        if (kentat.length > 1) {
            int var = 0;
            for (Field field : kentat) {
                if (!field.toString().contains("final")) {
                    var++;
                }
            }
            assertTrue("et tarvitse " + klassName + "-luokalle kuin " + m + ", poista ylimääräiset", var <= n);
        }
    }

    private String kentta(String toString, String klassName) {
        return toString.replace(klassName + ".", "").replace("java.lang.", "").replace("java.util.", "");
    }
}
