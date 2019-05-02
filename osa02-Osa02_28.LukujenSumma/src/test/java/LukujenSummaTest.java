
import fi.helsinki.cs.tmc.edutestutils.Points;
import org.junit.*;
import static org.junit.Assert.*;

@Points("02-28")
public class LukujenSummaTest {

    @Test
    public void toimiiNormaalisti() {
        assertEquals("Ei toimi syötteellä 1,2,2,1", 6, LukujenSumma.summa(1, 2, 2, 1));
    }

    @Test
    public void toimiiNegatiivisilla() {
        assertEquals("Ei toimi syötteellä 1,2,-1,1", 3, LukujenSumma.summa(1, 2, -1, 1));
    }

    @Test
    public void testaaMetodi() {
        testaa("Ei toimi syötteellä 1,2,3,4", 10, new int[]{1, 2, 3, 4});
    }
    
    @Test
    public void toimiiSuurillaSyotteilla(){
        testaa("Ei toimi syötteellä 0,0,0,2147483647",Integer.MAX_VALUE,new int[]{0,0,0,Integer.MAX_VALUE});
    }
    

    private void testaa(String viesti, int oletettu, int[] luvut) {
        assertEquals(viesti, oletettu, LukujenSumma.summa(luvut[0], luvut[1], luvut[2], luvut[3]));
    }
}
