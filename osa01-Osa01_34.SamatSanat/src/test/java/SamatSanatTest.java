
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Rule;
import org.junit.Test;

@Points("01-34")
public class SamatSanatTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void testEkaEsim() {
        onkoSamatSanat("hei", "hei");
    }

    @Test
    public void testTokaEsim() {
        onkoSamatSanat("hei", "maailma");
    }

    private void onkoSamatSanat(String eka, String toka) {
        ReflectionUtils.newInstanceOfClass(SamatSanat.class);
        String syote = eka + "\n" + toka + "\n";
        io.setSysIn(syote);
        SamatSanat.main(new String[0]);

        String out = io.getSysOut();

        assertTrue("Et kysynyt käyttäjältä mitään!", out.trim().length() > 0);

        if (eka.equals(toka)) {
            assertTrue("Tulosteessasi pitäisi olla teksti \"Samat sanat\", kun syöte on:\n" + syote + "Tulosteesi oli:\n" + out,
                    out.contains("Samat sanat"));
            assertFalse("Tulosteessasi ei saa olla tekstiä \"Ei sitten\", kun syöte on:\n" + syote + "Tulosteesi oli:\n" + out,
                    out.contains("Ei sitten"));
        } else {
            assertTrue("Tulosteessasi pitäisi olla teksti \"Ei sitten\", kun syöte on:\n" + syote + "Tulosteesi oli:\n" + out,
                    out.contains("Ei sitten"));
            assertFalse("Tulosteessasi ei saa olla tekstiä \"Samat sanat\", kun syöte on:\n" + syote + "Tulosteesi oli:\n" + out,
                    out.contains("Samat sanat"));
        }

    }
}
