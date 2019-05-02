
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.Scanner;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import org.junit.Test;

public class D_YleinenTest extends TestUtils {

    @Test
    public void eiToistolausettaTavarassa() {
        eiToistolausettaTiedostossa("Tavara.java");
    }

    @Test
    public void eiToistolausettaMatkalaukussa() {
        eiToistolausettaTiedostossa("Matkalaukku.java");
    }

    @Test
    public void eiToistolausettaLastiruumassa() {
        eiToistolausettaTiedostossa("Lastiruuma.java");
    }

    private void eiToistolausettaTiedostossa(String tiedosto) {
        File f = Paths.get("src", "main", "java", tiedosto).toFile();
        StringBuilder sb = new StringBuilder();
        try (Scanner s = new Scanner(f)) {
            while (s.hasNextLine()) {
                sb.append(s.nextLine());
            }
        } catch (FileNotFoundException ex) {
            fail("Tiedostoa " + tiedosto + " ei löydy.");
        }

        String content = sb.toString().replaceAll("\\s+", "");
        assertFalse("Tiedostossa " + tiedosto + " ei pitäisi olla toistolausetta. Nyt löytyi while-toistolause.", content.contains("while("));
        assertFalse("Tiedostossa " + tiedosto + " ei pitäisi olla toistolausetta. Nyt löytyi for-toistolause.", content.contains("for("));

    }

}
