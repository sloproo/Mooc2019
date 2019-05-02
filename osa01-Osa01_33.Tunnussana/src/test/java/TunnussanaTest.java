
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.util.Random;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Rule;
import org.junit.Test;

@Points("01-33")
public class TunnussanaTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void testEkaEsim() {
        tarkista("Wattlebird");
    }

    @Test
    public void testTokaEsim() {
        tarkista("Caput Draconis");
    }

    private void tarkista(String sana) {
        ReflectionUtils.newInstanceOfClass(Tunnussana.class);
        String syote = sana + "\n";
        io.setSysIn(syote);
        Tunnussana.main(new String[0]);

        String out = io.getSysOut();

        assertTrue("Et kysynyt käyttäjältä mitään!", out.trim().length() > 0);

        if (sana.equals("Caput Dragonis") || sana.equals("Caput Draconis")) {
            assertTrue("Tulosteessasi pitäisi olla teksti \"Tervetuloa!\", kun syöte on:\n" + syote + "Tulosteesi oli:\n" + out,
                    out.contains("Tervetuloa!"));
            assertFalse("Tulosteessasi ei saa olla tekstiä \"Hus siitä!\", kun syöte on:\n" + syote + "Tulosteesi oli:\n" + out,
                    out.contains("Hus siitä!"));
        } else {
            assertTrue("Tulosteessasi pitäisi olla teksti \"Hus siitä!\", kun syöte on:\n" + syote + "Tulosteesi oli:\n" + out,
                    out.contains("Hus siitä!"));
            assertFalse("Tulosteessasi ei saa olla tekstiä \"Tervetuloa!\", kun syöte on:\n" + syote + "Tulosteesi oli:\n" + out,
                    out.contains("Tervetuloa!"));
        }

    }
}
