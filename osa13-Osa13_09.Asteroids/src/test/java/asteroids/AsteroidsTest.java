package asteroids;

import fi.helsinki.cs.tmc.edutestutils.Points;
import static junit.framework.Assert.assertTrue;
import org.junit.Test;

public class AsteroidsTest {

    @Test
    @Points("13-09.1")
    public void osa1Tehty() {
        assertTrue(AsteroidsSovellus.osiaToteutettu() >= 1);
    }

    @Test
    @Points("13-09.2")
    public void osa2Tehty() {
        assertTrue(AsteroidsSovellus.osiaToteutettu() >= 2);
    }

    @Test
    @Points("13-09.3")
    public void osa3Tehty() {
        assertTrue(AsteroidsSovellus.osiaToteutettu() >= 3);
    }

    @Test
    @Points("13-09.4")
    public void osa4Tehty() {
        assertTrue(AsteroidsSovellus.osiaToteutettu() >= 4);
    }
}
