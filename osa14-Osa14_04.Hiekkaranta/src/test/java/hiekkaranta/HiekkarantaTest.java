package hiekkaranta;

import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;


public class HiekkarantaTest {

    @Test
    @Points("14-04.1")
    public void simulaationAlkupalat() {
        Reflex.reflect("hiekkaranta.Simulaatio").requirePublic();
        Reflex.reflect("hiekkaranta.Simulaatio").ctor().taking(int.class, int.class).requirePublic();
        Reflex.reflect("hiekkaranta.Simulaatio").method("lisaa").returningVoid().taking(int.class, int.class, Tyyppi.class).requirePublic();
        Reflex.reflect("hiekkaranta.Simulaatio").method("sisalto").returning(Tyyppi.class).taking(int.class, int.class).requirePublic();
        Reflex.reflect("hiekkaranta.Simulaatio").method("paivita").returningVoid().takingNoParams().requirePublic();

    }

    @Test
    @Points("14-04.1")
    public void ulkopuolellaMetallia() {
        Object simulaatio = null;
        try {
            simulaatio = Reflex.reflect("hiekkaranta.Simulaatio").ctor().taking(int.class, int.class).invoke(200, 100);
        } catch (Throwable ex) {
            fail("Kutsu new Simulaatio(200, 100) aiheutti virheen. Virhe: " + ex.getMessage());
        }

        List<Point> pisteet = new ArrayList<>();
        pisteet.add(new Point(-1, -1));
        pisteet.add(new Point(0, -1));
        pisteet.add(new Point(-1, 0));
        pisteet.add(new Point(200, 0));
        pisteet.add(new Point(0, 100));

        for (Point point : pisteet) {
            int x = (int) point.getX();
            int y = (int) point.getY();

            Tyyppi tyyppi = null;
            try {
                tyyppi = Reflex.reflect("hiekkaranta.Simulaatio").method("sisalto").returning(Tyyppi.class).taking(int.class, int.class).invokeOn(simulaatio, x, y);
            } catch (Throwable ex) {
                fail("Kutsu:\nSimulaatio s = new Simulaatio(200, 100);\ns.sisalto(" + x + ", " + y + ");\nEpäonnistui. Virhe: " + ex.getMessage());
            }

            assertEquals("Kokeile: Simulaatio s = new Simulaatio(200, 100);\ns.sisalto(" + x + ", " + y + ");\n. Pitäisi olla Tyyppi.METALLI.\nNyt arvo oli: " + tyyppi, Tyyppi.METALLI, tyyppi);
        }
    }

    @Test
    @Points("14-04.1")
    public void lisaaminenJaSisaltoToimii() {
        Object simulaatio = null;
        try {
            simulaatio = Reflex.reflect("hiekkaranta.Simulaatio").ctor().taking(int.class, int.class).invoke(300, 200);
        } catch (Throwable ex) {
            fail("Kutsu new Simulaatio(300, 200) aiheutti virheen. Virhe: " + ex.getMessage());
        }

        Random rnd = new Random();
        Map<Point, Tyyppi> pisteet = new HashMap<>();

        for (int i = 0; i < 30; i++) {
            int x = rnd.nextInt(300);
            int y = rnd.nextInt(200);

            double d = rnd.nextDouble();
            Tyyppi t = null;
            if (d < 0.33) {
                t = Tyyppi.METALLI;
            } else if (d < 0.66) {
                t = Tyyppi.HIEKKA;
            } else {
                t = Tyyppi.VESI;
            }

            pisteet.put(new Point(x, y), t);
        }

        for (Point piste : pisteet.keySet()) {
            Tyyppi t = pisteet.get(piste);
            int x = (int) piste.getX();
            int y = (int) piste.getY();

            try {
                Reflex.reflect("hiekkaranta.Simulaatio").method("lisaa").returningVoid().taking(int.class, int.class, Tyyppi.class).invokeOn(simulaatio, x, y, t);
            } catch (Throwable ex) {
                fail("Kutsu:\nSimulaatio s = new Simulaatio(300, 200);\ns.lisaa(" + x + ", " + y + ", Tyyppi." + t.name() + ");\nEpäonnistui. Virhe: " + ex.getMessage());
            }
        }

        for (Point piste : pisteet.keySet()) {
            int x = (int) piste.getX();
            int y = (int) piste.getY();
            Tyyppi t = pisteet.get(piste);

            Tyyppi tyyppi = null;
            try {
                tyyppi = Reflex.reflect("hiekkaranta.Simulaatio").method("sisalto").returning(Tyyppi.class).taking(int.class, int.class).invokeOn(simulaatio, x, y);
            } catch (Throwable ex) {
                fail("Kutsu:\nSimulaatio s = new Simulaatio(300, 200);\ns.lisaa(" + x + ", " + y + ", Tyyppi." + t.name() + ");\nTyyppi t = s.sisalto(" + x + ", " + y + ");\nEpäonnistui. Virhe: " + ex.getMessage());
            }

            assertEquals("Kokeile:\nSimulaatio s = new Simulaatio(300, 200);\ns.lisaa(" + x + ", " + y + ", Tyyppi." + t.name() + ");\nTyyppi t = s.sisalto(" + x + ", " + y + ");\n Pitäisi olla Tyyppi." + t.name() + ".\nNyt arvo oli: " + tyyppi, t, tyyppi);
        }
    }

    
    @Test
    @Points("14-04.2")
    public void hiekkaSiirtyySimulaatiossa() throws Throwable {
        Object simulaatio = null;
        try {
            simulaatio = Reflex.reflect("hiekkaranta.Simulaatio").ctor().taking(int.class, int.class).invoke(300, 200);
        } catch (Throwable ex) {
            fail("Kutsu new Simulaatio(300, 200) aiheutti virheen. Virhe: " + ex.getMessage());
        }

        Random rnd = new Random();
        Map<Point, Tyyppi> pisteet = new HashMap<>();

        for (int x = 10; x < 20; x++) {
            Reflex.reflect("hiekkaranta.Simulaatio").method("lisaa").returningVoid().taking(int.class, int.class, Tyyppi.class).invokeOn(simulaatio, x, 100, Tyyppi.HIEKKA);
            Tyyppi tyyppi = Reflex.reflect("hiekkaranta.Simulaatio").method("sisalto").returning(Tyyppi.class).taking(int.class, int.class).invokeOn(simulaatio, x, 100);

            assertEquals("Kun simulaatioon on lisätty kohtaan " + x + ", 100 hiekkaa eikä simulaation paivita-kutsua ole kutsuttu, sisalto-metodin pitäisi löytää hiekka kyseisestä kohdasta.", Tyyppi.HIEKKA, tyyppi);
        }

        Reflex.reflect("hiekkaranta.Simulaatio").method("paivita").returningVoid().takingNoParams().invokeOn(simulaatio);
        Reflex.reflect("hiekkaranta.Simulaatio").method("paivita").returningVoid().takingNoParams().invokeOn(simulaatio);
        Reflex.reflect("hiekkaranta.Simulaatio").method("paivita").returningVoid().takingNoParams().invokeOn(simulaatio);
        Reflex.reflect("hiekkaranta.Simulaatio").method("paivita").returningVoid().takingNoParams().invokeOn(simulaatio);

        int hiekkalaskuri = 0;
        for (int x = 5; x < 25; x++) {
            Tyyppi tyyppi = Reflex.reflect("hiekkaranta.Simulaatio").method("sisalto").returning(Tyyppi.class).taking(int.class, int.class).invokeOn(simulaatio, x, 105);
            if (tyyppi == Tyyppi.HIEKKA) {
                hiekkalaskuri++;
            }

        }

        Reflex.reflect("hiekkaranta.Simulaatio").method("paivita").returningVoid().takingNoParams().invokeOn(simulaatio);

        for (int x = 10; x < 20; x++) {
            Tyyppi tyyppi = Reflex.reflect("hiekkaranta.Simulaatio").method("sisalto").returning(Tyyppi.class).taking(int.class, int.class).invokeOn(simulaatio, x, 100);

            assertNotEquals("Kun simulaatioon on lisätty kohtaan (x=" + x + ", y=100) ja simulaation paivita-metodia on kutsuttu, ei hiekan enää pitäisi olla alkuperäisessä paikassa.", Tyyppi.HIEKKA, tyyppi);
        }

        for (int x = 5; x < 25; x++) {
            Tyyppi tyyppi = Reflex.reflect("hiekkaranta.Simulaatio").method("sisalto").returning(Tyyppi.class).taking(int.class, int.class).invokeOn(simulaatio, x, 105);
            if (tyyppi == Tyyppi.HIEKKA) {
                hiekkalaskuri++;
            }

        }

        Reflex.reflect("hiekkaranta.Simulaatio").method("paivita").returningVoid().takingNoParams().invokeOn(simulaatio);

        for (int x = 5; x < 25; x++) {
            Tyyppi tyyppi = Reflex.reflect("hiekkaranta.Simulaatio").method("sisalto").returning(Tyyppi.class).taking(int.class, int.class).invokeOn(simulaatio, x, 105);
            if (tyyppi == Tyyppi.HIEKKA) {
                hiekkalaskuri++;
            }

        }

        assertTrue("Kun simulaatioon on lisätty hiekkaa y-koordinaattiin 100 ja simulaation paivita-metodia on kutsutaan viisi kertaa, ainakin osan pitäisi löytyä y-koordinaatista 105.", hiekkalaskuri > 0);

    }

    @Test
    @Points("14-04.2")
    public void hiekkaEiSiirryMetallinYli() throws Throwable {
        Object simulaatio = null;
        try {
            simulaatio = Reflex.reflect("hiekkaranta.Simulaatio").ctor().taking(int.class, int.class).invoke(300, 200);
        } catch (Throwable ex) {
            fail("Kutsu new Simulaatio(300, 200) aiheutti virheen. Virhe: " + ex.getMessage());
        }

        Random rnd = new Random();
        Map<Point, Tyyppi> pisteet = new HashMap<>();

        for (int x = 10; x < 20; x++) {
            Reflex.reflect("hiekkaranta.Simulaatio").method("lisaa").returningVoid().taking(int.class, int.class, Tyyppi.class).invokeOn(simulaatio, x, 100, Tyyppi.METALLI);
            Reflex.reflect("hiekkaranta.Simulaatio").method("lisaa").returningVoid().taking(int.class, int.class, Tyyppi.class).invokeOn(simulaatio, x, 99, Tyyppi.HIEKKA);
        }

        Reflex.reflect("hiekkaranta.Simulaatio").method("paivita").returningVoid().takingNoParams().invokeOn(simulaatio);
        Reflex.reflect("hiekkaranta.Simulaatio").method("paivita").returningVoid().takingNoParams().invokeOn(simulaatio);
        Reflex.reflect("hiekkaranta.Simulaatio").method("paivita").returningVoid().takingNoParams().invokeOn(simulaatio);
        Reflex.reflect("hiekkaranta.Simulaatio").method("paivita").returningVoid().takingNoParams().invokeOn(simulaatio);
        Reflex.reflect("hiekkaranta.Simulaatio").method("paivita").returningVoid().takingNoParams().invokeOn(simulaatio);

        for (int x = 11; x < 19; x++) {
            Tyyppi tyyppi = Reflex.reflect("hiekkaranta.Simulaatio").method("sisalto").returning(Tyyppi.class).taking(int.class, int.class).invokeOn(simulaatio, x, 99);

            assertEquals("Kun simulaatioon on lisätty ensin metalliviiva, jonka päällä on hiekkaa, ei hiekan tule siirtyä metallin läpi.", Tyyppi.HIEKKA, tyyppi);
        }

    }

    @Test
    @Points("14-04.2")
    public void tippuvaHiekkaPutoaaPohjalle() throws Throwable {

        Object simulaatio = null;
        try {
            simulaatio = Reflex.reflect("hiekkaranta.Simulaatio").ctor().taking(int.class, int.class).invoke(300, 200);
        } catch (Throwable ex) {
            fail("Kutsu new Simulaatio(300, 200) aiheutti virheen. Virhe: " + ex.getMessage());
        }

        Random rnd = new Random();
        Map<Point, Tyyppi> pisteet = new HashMap<>();

        int hiekkaaLisatty = 0;
        for (int x = 10; x < 20; x++) {
            Reflex.reflect("hiekkaranta.Simulaatio").method("lisaa").returningVoid().taking(int.class, int.class, Tyyppi.class).invokeOn(simulaatio, x, 100, Tyyppi.HIEKKA);
            Reflex.reflect("hiekkaranta.Simulaatio").method("lisaa").returningVoid().taking(int.class, int.class, Tyyppi.class).invokeOn(simulaatio, x, 99, Tyyppi.HIEKKA);
            Reflex.reflect("hiekkaranta.Simulaatio").method("lisaa").returningVoid().taking(int.class, int.class, Tyyppi.class).invokeOn(simulaatio, x, 98, Tyyppi.HIEKKA);
            Reflex.reflect("hiekkaranta.Simulaatio").method("lisaa").returningVoid().taking(int.class, int.class, Tyyppi.class).invokeOn(simulaatio, x, 97, Tyyppi.HIEKKA);
            Reflex.reflect("hiekkaranta.Simulaatio").method("lisaa").returningVoid().taking(int.class, int.class, Tyyppi.class).invokeOn(simulaatio, x, 96, Tyyppi.HIEKKA);

            hiekkaaLisatty += 5;
        }

        for (int i = 0; i < 200; i++) {
            Reflex.reflect("hiekkaranta.Simulaatio").method("paivita").returningVoid().takingNoParams().invokeOn(simulaatio);
        }

        int hiekkaaLoytyi = 0;
        for (int x = 0; x < 300; x++) {
            for (int y = 0; y < 200; y++) {
                Tyyppi tyyppi = Reflex.reflect("hiekkaranta.Simulaatio").method("sisalto").returning(Tyyppi.class).taking(int.class, int.class).invokeOn(simulaatio, x, y);
                if (Tyyppi.HIEKKA == tyyppi) {
                    hiekkaaLoytyi++;
                }
            }
        }

        assertEquals("Kun hiekkaa lisätään " + hiekkaaLisatty + " jyvää, tulee jokaisen jyvän olla mukana simulaatiossa myös 200 päivitä-kutsun jälkeen.", hiekkaaLisatty, hiekkaaLoytyi);

        Tyyppi tyyppi = Reflex.reflect("hiekkaranta.Simulaatio").method("sisalto").returning(Tyyppi.class).taking(int.class, int.class).invokeOn(simulaatio, 8, 199);
        assertEquals("Valuuhan hiekka myös sivuille?", Tyyppi.HIEKKA, tyyppi);
        tyyppi = Reflex.reflect("hiekkaranta.Simulaatio").method("sisalto").returning(Tyyppi.class).taking(int.class, int.class).invokeOn(simulaatio, 22, 199);
        assertEquals("Valuuhan hiekka myös sivuille?", Tyyppi.HIEKKA, tyyppi);

    }
    
    
    @Test
    @Points("14-04.3")
    public void vesiSiirtyySimulaatiossa() throws Throwable {
        Object simulaatio = null;
        try {
            simulaatio = Reflex.reflect("hiekkaranta.Simulaatio").ctor().taking(int.class, int.class).invoke(300, 200);
        } catch (Throwable ex) {
            fail("Kutsu new Simulaatio(300, 200) aiheutti virheen. Virhe: " + ex.getMessage());
        }

        for (int x = 10; x < 20; x++) {
            Reflex.reflect("hiekkaranta.Simulaatio").method("lisaa").returningVoid().taking(int.class, int.class, Tyyppi.class).invokeOn(simulaatio, x, 100, Tyyppi.VESI);

            Tyyppi tyyppi = Reflex.reflect("hiekkaranta.Simulaatio").method("sisalto").returning(Tyyppi.class).taking(int.class, int.class).invokeOn(simulaatio, x, 100);
            assertEquals("Kun simulaatioon on lisätty kohtaan " + x + ", 100 vettä eikä simulaation paivita-kutsua ole kutsuttu, sisalto-metodin pitäisi löytää vettä kyseisestä kohdasta.", Tyyppi.VESI, tyyppi);
        }

        Reflex.reflect("hiekkaranta.Simulaatio").method("paivita").returningVoid().takingNoParams().invokeOn(simulaatio);
        Reflex.reflect("hiekkaranta.Simulaatio").method("paivita").returningVoid().takingNoParams().invokeOn(simulaatio);
        Reflex.reflect("hiekkaranta.Simulaatio").method("paivita").returningVoid().takingNoParams().invokeOn(simulaatio);
        Reflex.reflect("hiekkaranta.Simulaatio").method("paivita").returningVoid().takingNoParams().invokeOn(simulaatio);
        
        int vesilaskuri = 0;
        for (int x = 5; x < 25; x++) {
            Tyyppi tyyppi = Reflex.reflect("hiekkaranta.Simulaatio").method("sisalto").returning(Tyyppi.class).taking(int.class, int.class).invokeOn(simulaatio, x, 105);
            if (tyyppi == Tyyppi.VESI) {
                vesilaskuri++;
            }
        }

        
        Reflex.reflect("hiekkaranta.Simulaatio").method("paivita").returningVoid().takingNoParams().invokeOn(simulaatio);

        for (int x = 10; x < 20; x++) {
            Tyyppi tyyppi = Reflex.reflect("hiekkaranta.Simulaatio").method("sisalto").returning(Tyyppi.class).taking(int.class, int.class).invokeOn(simulaatio, x, 100);

            assertNotEquals("Kun simulaatioon on lisätty kohtaan (x=" + x + ", y=100) vettä ja simulaation paivita-metodia on kutsuttu, ei veden enää pitäisi olla alkuperäisessä paikassa.", Tyyppi.VESI, tyyppi);
        }

        
        for (int x = 5; x < 25; x++) {
            Tyyppi tyyppi = Reflex.reflect("hiekkaranta.Simulaatio").method("sisalto").returning(Tyyppi.class).taking(int.class, int.class).invokeOn(simulaatio, x, 105);
            if (tyyppi == Tyyppi.VESI) {
                vesilaskuri++;
            }
        }
        
        Reflex.reflect("hiekkaranta.Simulaatio").method("paivita").returningVoid().takingNoParams().invokeOn(simulaatio);

        for (int x = 5; x < 25; x++) {
            Tyyppi tyyppi = Reflex.reflect("hiekkaranta.Simulaatio").method("sisalto").returning(Tyyppi.class).taking(int.class, int.class).invokeOn(simulaatio, x, 105);
            if (tyyppi == Tyyppi.VESI) {
                vesilaskuri++;
            }
        }


        assertTrue("Kun simulaatioon on lisätty vetta y-koordinaattiin 100 ja simulaation paivita-metodia on kutsuttu viidesti, ainakin osan vedestä pitäisi löytyä y-koordinaatista 105.", vesilaskuri > 0);

    }

    @Test
    @Points("14-04.3")
    public void vesiJaHiekkaVaihtaaPaikkaa() throws Throwable {
        Object simulaatio = null;
        try {
            simulaatio = Reflex.reflect("hiekkaranta.Simulaatio").ctor().taking(int.class, int.class).invoke(2, 3);
        } catch (Throwable ex) {
            fail("Kutsu new Simulaatio(2, 3) aiheutti virheen. Virhe: " + ex.getMessage());
        }

        Reflex.reflect("hiekkaranta.Simulaatio").method("lisaa").returningVoid().taking(int.class, int.class, Tyyppi.class).invokeOn(simulaatio, 0, 1, Tyyppi.HIEKKA);
        Reflex.reflect("hiekkaranta.Simulaatio").method("lisaa").returningVoid().taking(int.class, int.class, Tyyppi.class).invokeOn(simulaatio, 1, 1, Tyyppi.HIEKKA);

        Reflex.reflect("hiekkaranta.Simulaatio").method("lisaa").returningVoid().taking(int.class, int.class, Tyyppi.class).invokeOn(simulaatio, 0, 2, Tyyppi.VESI);
        Reflex.reflect("hiekkaranta.Simulaatio").method("lisaa").returningVoid().taking(int.class, int.class, Tyyppi.class).invokeOn(simulaatio, 1, 2, Tyyppi.VESI);

        Reflex.reflect("hiekkaranta.Simulaatio").method("paivita").returningVoid().takingNoParams().invokeOn(simulaatio);
        Reflex.reflect("hiekkaranta.Simulaatio").method("paivita").returningVoid().takingNoParams().invokeOn(simulaatio);
        Reflex.reflect("hiekkaranta.Simulaatio").method("paivita").returningVoid().takingNoParams().invokeOn(simulaatio);

        Tyyppi tyyppi = Reflex.reflect("hiekkaranta.Simulaatio").method("sisalto").returning(Tyyppi.class).taking(int.class, int.class).invokeOn(simulaatio, 0, 2);
        assertEquals("Hiekan tulee olla painavampi kuin veden, eli hiekan tulee vaihtaa veden kanssa paikkaa simulaatiossa niiden ollessa päällekkäin. Kokeile toimintaa 2x3 kokoisella simulaatiolla..", Tyyppi.HIEKKA, tyyppi);
        tyyppi = Reflex.reflect("hiekkaranta.Simulaatio").method("sisalto").returning(Tyyppi.class).taking(int.class, int.class).invokeOn(simulaatio, 1, 2);
        assertEquals("Hiekan tulee olla painavampi kuin veden, eli hiekan tulee vaihtaa veden kanssa paikkaa simulaatiossa niiden ollessa päällekkäin. Kokeile toimintaa 2x3 kokoisella simulaatiolla.", Tyyppi.HIEKKA, tyyppi);
    }

    @Test
    @Points("14-04.3")
    public void vesiJaMetalliEiVaihdaPaikkaa() throws Throwable {
        Object simulaatio = null;
        try {
            simulaatio = Reflex.reflect("hiekkaranta.Simulaatio").ctor().taking(int.class, int.class).invoke(2, 3);
        } catch (Throwable ex) {
            fail("Kutsu new Simulaatio(2, 3) aiheutti virheen. Virhe: " + ex.getMessage());
        }

        Reflex.reflect("hiekkaranta.Simulaatio").method("lisaa").returningVoid().taking(int.class, int.class, Tyyppi.class).invokeOn(simulaatio, 0, 1, Tyyppi.METALLI);
        Reflex.reflect("hiekkaranta.Simulaatio").method("lisaa").returningVoid().taking(int.class, int.class, Tyyppi.class).invokeOn(simulaatio, 1, 1, Tyyppi.METALLI);

        Reflex.reflect("hiekkaranta.Simulaatio").method("lisaa").returningVoid().taking(int.class, int.class, Tyyppi.class).invokeOn(simulaatio, 0, 2, Tyyppi.VESI);
        Reflex.reflect("hiekkaranta.Simulaatio").method("lisaa").returningVoid().taking(int.class, int.class, Tyyppi.class).invokeOn(simulaatio, 1, 2, Tyyppi.VESI);

        Reflex.reflect("hiekkaranta.Simulaatio").method("paivita").returningVoid().takingNoParams().invokeOn(simulaatio);
        Reflex.reflect("hiekkaranta.Simulaatio").method("paivita").returningVoid().takingNoParams().invokeOn(simulaatio);
        Reflex.reflect("hiekkaranta.Simulaatio").method("paivita").returningVoid().takingNoParams().invokeOn(simulaatio);

        Tyyppi tyyppi = Reflex.reflect("hiekkaranta.Simulaatio").method("sisalto").returning(Tyyppi.class).taking(int.class, int.class).invokeOn(simulaatio, 0, 2);
        assertEquals("Veden ei tule syrjäyttää metallia. Kokeile toimintaa 2x3 kokoisella simulaatiolla.", Tyyppi.VESI, tyyppi);
        tyyppi = Reflex.reflect("hiekkaranta.Simulaatio").method("sisalto").returning(Tyyppi.class).taking(int.class, int.class).invokeOn(simulaatio, 1, 2);
        assertEquals("Veden ei tule syrjäyttää metallia. Kokeile toimintaa 2x3 kokoisella simulaatiolla.", Tyyppi.VESI, tyyppi);
    }

    @Test
    @Points("14-04.3")
    public void vesiTayttaaKuopan() throws Throwable {
        Object simulaatio = null;
        try {
            simulaatio = Reflex.reflect("hiekkaranta.Simulaatio").ctor().taking(int.class, int.class).invoke(50, 50);
        } catch (Throwable ex) {
            fail("Kutsu new Simulaatio(50, 50) aiheutti virheen. Virhe: " + ex.getMessage());
        }

        // muodostetaan kuoppa
        for (int y = 10; y < 20; y++) {
            Reflex.reflect("hiekkaranta.Simulaatio").method("lisaa").returningVoid().taking(int.class, int.class, Tyyppi.class).invokeOn(simulaatio, 20, y, Tyyppi.METALLI);
            Reflex.reflect("hiekkaranta.Simulaatio").method("lisaa").returningVoid().taking(int.class, int.class, Tyyppi.class).invokeOn(simulaatio, 30, y, Tyyppi.METALLI);
        }

        for (int x = 20; x <= 30; x++) {
            Reflex.reflect("hiekkaranta.Simulaatio").method("lisaa").returningVoid().taking(int.class, int.class, Tyyppi.class).invokeOn(simulaatio, x, 20, Tyyppi.METALLI);
        }

        // lisataan vetta ja päivitetään vuorotellen
        for (int i = 0; i < 200; i++) {
            Reflex.reflect("hiekkaranta.Simulaatio").method("lisaa").returningVoid().taking(int.class, int.class, Tyyppi.class).invokeOn(simulaatio, 25, 5, Tyyppi.VESI);
            Reflex.reflect("hiekkaranta.Simulaatio").method("paivita").returningVoid().takingNoParams().invokeOn(simulaatio);
        }

        for (int i = 0; i < 200; i++) {
            Reflex.reflect("hiekkaranta.Simulaatio").method("paivita").returningVoid().takingNoParams().invokeOn(simulaatio);
        }

        for (int x = 21; x <= 29; x++) {
            for (int y = 10; y < 20; y++) {
                Tyyppi tyyppi = Reflex.reflect("hiekkaranta.Simulaatio").method("sisalto").returning(Tyyppi.class).taking(int.class, int.class).invokeOn(simulaatio, x, y);
                assertEquals("Kun metallista muodostetaan kuppi, ja vettä kaadetaan kuppiin kupin yläpuolelta, tulee veden päätyä kuppiin.\nYlimenevän veden tulee valua yli.", Tyyppi.VESI, tyyppi);
            }
        }

        for (int x = 21; x <= 29; x++) {
            Tyyppi tyyppi = Reflex.reflect("hiekkaranta.Simulaatio").method("sisalto").returning(Tyyppi.class).taking(int.class, int.class).invokeOn(simulaatio, x, 9);
            assertEquals("Kun metallista muodostetaan kuppi, ja vettä kaadetaan kuppiin kupin yläpuolelta, tulee veden päätyä kuppiin.\nYlimenevän veden tulee valua yli.", Tyyppi.TYHJA, tyyppi);
        }
    }
}
