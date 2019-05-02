
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Rule;
import org.junit.Test;

@Points("03-23")
public class TulostusKolmestiTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void testTulostusKolmesti() {
        toimii("testi");
    }
    
    @Test
    public void testHahaha() {
        toimii("ha");
    }
    
    @Test
    public void testKukka() {
        toimii("kukka");
    }
    
    private void toimii(String merkkijono) {
        ReflectionUtils.newInstanceOfClass(TulostusKolmesti.class);
        io.setSysIn(merkkijono + "\n");
        try {
            TulostusKolmesti.main(new String[0]);
        } catch (NumberFormatException e) {
            fail("Kun luet käyttäjältä merkkijonoa, älä yritä muuttaa sitä numeroksi. Virhe: " + e.getMessage());
        }
        

        String out = io.getSysOut();

        assertTrue("Et kysynyt käyttäjältä mitään!", out.trim().length() > 0);
        
        assertTrue("Kun syöte on \"" + merkkijono + "\" pitäisi tulostuksessa olla teksti \"" + merkkijono + merkkijono + merkkijono + "\", nyt ei ollut. Tulosteesi oli: "+out,
                   out.contains(merkkijono + merkkijono + merkkijono));
        
        assertTrue("Kun syöte on \"" + merkkijono + "\" pitäisi tulostuksessa olla teksti \"" + merkkijono + merkkijono + merkkijono + "\", nyt ei ollut. Tulosteesi oli: "+out,
                   !out.contains(merkkijono + merkkijono + merkkijono + merkkijono));
    }
}
