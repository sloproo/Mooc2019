
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import static org.junit.Assert.assertTrue;
import org.junit.Rule;
import org.junit.Test;

@Points("04-06")
public class VieraslistaTiedostostaTest {
    
    @Rule
    public MockStdio io = new MockStdio();
    
    @Test
    public void testaaNimet() throws Throwable {
        vieraslistaTest("nimet.txt", 4, 0, "ada", "arto", "leena", "testi");
    }
    
    @Test
    public void testaaNimet2() throws Throwable {
        vieraslistaTest("toiset-nimet.txt", 3, 2, "leo", "jarmo", "alicia", "ada", "testi");
    }
    
    public void vieraslistaTest(String tiedosto, int onListallaLkm, int eiOleListallaLkm, String... haettavat) throws Throwable {
        String in = tiedosto + "\n";
        for (String nimi : haettavat) {
            in += nimi + "\n";
        }
        
        io.setSysIn(in + "\n");
        
        VieraslistaTiedostosta.main(new String[]{});
        
        String out = io.getSysOut();
        
        assertTrue("Kun syöte on:\n" + in + "Odotettiin, että " + onListallaLkm + " oli listalla ja " + eiOleListallaLkm + " ei ollut listalla.\nTulostus oli:\n" + out, out.split("on listalla").length == onListallaLkm + 1);
        assertTrue("Kun syöte on:\n" + in + "Odotettiin, että " + onListallaLkm + " oli listalla ja " + eiOleListallaLkm + " ei ollut listalla.\nTulostus oli:\n" + out, out.split("ei ole listalla").length == eiOleListallaLkm + 1);
    }
}
