
import fi.helsinki.cs.tmc.edutestutils.Points;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import org.junit.Test;
import static org.junit.Assert.*;

@Points("04-03")
public class UudenTiedostonLuominenTest {

    @Test
    public void tiedostoOlemassa() {
        assertTrue(new File("tiedosto.txt").exists());
    }

    @Test
    public void onTekstiHeiMaailma() throws FileNotFoundException {
        tiedostoOlemassa();

        try (Scanner s = new Scanner(new File("tiedosto.txt"))) {
            String rivi = s.nextLine();
            assertTrue("Tiedoston \"tiedosto.txt\" ensimmäisellä rivillä tulee olla teksti Hei maailma.\nNyt teksti oli:\n" + rivi, rivi.startsWith("Hei maailma"));
        }

    }

}
