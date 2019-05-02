
import fi.helsinki.cs.tmc.edutestutils.Points;
import org.junit.Test;
import static org.junit.Assert.*;

@Points("02-30")
public class SuurinTest {

    public SuurinTest() {
    }

    @Test
    public void TestaaSuurinLukuNegatiivisilla() {
        int[] syote = {-5, -8, -4, -4};
        assertEquals(virheilmoitus(syote), vastaus(syote), testaa(syote));
    }

    @Test
    public void TestaaSuurinLukuPositiivisilla1() {
        int[] syote = {42, 5, 9, 42};
        assertEquals(virheilmoitus(syote), vastaus(syote), testaa(syote));
    }

    @Test
    public void TestaaSuurinLukuPositiivisilla2() {
        int[] syote = {5, 42, 9, 42};
        assertEquals(virheilmoitus(syote), vastaus(syote), testaa(syote));
    }

    @Test
    public void TestaaSuurinLukuPositiivisilla3() {
        int[] syote = {5, 9, 42, 42};
        assertEquals(virheilmoitus(syote), vastaus(syote), testaa(syote));
    }

    @Test
    public void TestaaSuurinLukuSamoilla() {
        int[] syote = {9, 9, 9, 9};
        assertEquals(virheilmoitus(syote), vastaus(syote), testaa(syote));
    }

    @Test
    public void TestaaSuurinLukuMallivastauksella() {
        int[] syote = {2, 7, 3, 7};
        assertEquals(virheilmoitus(syote), vastaus(syote), testaa(syote));
    }

    @Test
    public void TestaaSuurinLukuNollilla() {
        int[] syote = {0, 0, 0, 0};
        assertEquals(virheilmoitus(syote), vastaus(syote), testaa(syote));
    }

    private int testaa(int[] syote) {
        return Suurin.suurin(syote[0], syote[1], syote[2]);
    }

    private int vastaus(int[] syote) {
        return syote[syote.length - 1];
    }

    private String virheilmoitus(int[] syote) {
        return "Luvuista " + syote[0] + ", " + syote[1] + ", " + syote[2]
                + " suurin ";
    }
}
