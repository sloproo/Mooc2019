
import fi.helsinki.cs.tmc.edutestutils.Points;
import org.junit.*;
import static org.junit.Assert.*;

@Points("02-31")
public class LukujenKeskiarvoTest {

    @Test
    public void onkoAloittanut() {
        double tulos = LukujenKeskiarvo.keskiarvo(1, 1, 1, 1);
        assertFalse("Palautat -1, palautat mallissa olevan vastauksen. luvuilla 1, 1, 1, ja 1  keskiarvo on 1",
                -1 == tulos);
    }

    @Test
    public void onkoKeskiarvoOikein1() {
        assertEquals("Keskiarvo ei ole oikein luvuilla -12, 2, 8 ja 0, Onkohan kyseessä Int-muuttujilla jako?",
                -0.5, LukujenKeskiarvo.keskiarvo(-12, 2, 8, 0), 0.0001);
    }

    @Test
    public void onkoKeskiarvoOikein2() {
        assertEquals("Keskiarvo ei ole oikein luvuilla 1, 2, 3 ja 4, Onkohan kyseessä Int-muuttujilla jako?",
                2.5, LukujenKeskiarvo.keskiarvo(1, 2, 3, 4), 0.0001);
    }
}
