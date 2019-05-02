
import org.junit.Test;
import static org.junit.Assert.*;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.MockInOut;

import java.lang.reflect.Field;

public class TulosteluaTest {

    public String sanitize(String s) {
        return s.replace("\r\n", "\n").replace("\r", "\n");
    }

    @Points("02-32.1 02-32.2 02-32.3 02-32.4")
    @Test
    public void testaaEtteiKenttia() {
        Field[] fs = Tulostelua.class.getDeclaredFields();
        if (fs.length != 0) {
            fail("Luokassa Tulostelua on kenttä nimeltään " + fs[0].getName() + " poista se!");
        }
    }

    @Test
    @Points("02-32.1")
    public void testaaTulostaTahtia1() {
        MockInOut mio = new MockInOut("");
        Tulostelua.tulostaTahtia(3);
        String out = sanitize(mio.getOutput());
        assertTrue("Et tulostanut yhtään merkkiä \"*\" kun kutsuttiin tulostaTahtia.", out.contains("*"));
        assertTrue("Et tulostanut yhtään rivinvaihtoa kun kutsuttiin tulostaTahtia.", out.contains("\n"));
        assertEquals("Tulostit väärin kun kutsuttiin tulostaTahtia(3).", "***\n", out);
        mio.close();
    }

    @Test
    @Points("02-32.1")
    public void testaaTulostaTahtia2() {
        MockInOut mio = new MockInOut("");
        Tulostelua.tulostaTahtia(7);
        String out = sanitize(mio.getOutput());
        assertEquals("Tulostit väärin kun kutsuttiin tulostaTahtia(7).", "*******", out.trim());
    }

    @Test
    @Points("02-32.2")
    public void testaaTulostaNelio1() {
        MockInOut mio = new MockInOut("");
        Tulostelua.tulostaNelio(3);
        String out = sanitize(mio.getOutput());
        assertEquals("Tulostit väärin kun kutsuttiin tulostaNelio(3).", "***\n***\n***", out.trim());
    }

    @Test
    @Points("02-32.3")
    public void testaaTulostaSuorakulmio1() {
        MockInOut mio = new MockInOut("");
        Tulostelua.tulostaSuorakulmio(4, 2);
        String out = sanitize(mio.getOutput());
        assertEquals("Tulostit väärin kun kutsuttiin tulostaSuorakulmio(4,2).", "****\n****", out.trim());
    }

    @Test
    @Points("02-32.4")
    public void testaaTulostaKolmio1() {
        MockInOut mio = new MockInOut("");
        Tulostelua.tulostaKolmio(3);
        String out = sanitize(mio.getOutput());
        assertEquals("Tulostit väärin kun kutsuttiin tulostaKolmio(3).", "*\n**\n***", out.trim());
    }
}
