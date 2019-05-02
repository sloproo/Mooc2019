
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.util.ArrayList;
import org.junit.*;
import static org.junit.Assert.*;

@Points("07-05")
public class ValmiitJarjestamisalgoritmitTest {

    String klassName = "Paaohjelma";
    Reflex.ClassRef<Object> klass;

    @Before
    public void justForKicks() {
        klass = Reflex.reflect(klassName);
    }

    @Test
    public void onLuokka() {
        klass = Reflex.reflect(klassName);
        assertTrue("Luokan " + klassName + " pitää olla julkinen, eli se tulee määritellä\npublic class " + klassName + " {...\n}", klass.isPublic());
    }

    @Test
    public void jarjestaLukutaulukko() throws Throwable {
        int[] t = {2, 1, 3, 0};
        String virhe = "Kokeile:\nint[] t = {2, 1, 3, 0};\njarjesta(t);\nSystem.out.println(Arrays.toString(t));\n";
        klass.staticMethod("jarjesta").returningVoid().taking(int[].class).withNiceError(virhe).invoke(t);
        Assert.assertArrayEquals(virhe, new int[]{0, 1, 2, 3}, t);
    }

    @Test
    public void jarjestaMerkkijonotaulukko() throws Throwable {
        String[] t = {"x", "a", "b", "d"};
        String virhe = "Kokeile:\nString[] t = {\"x\", \"a\", \"b\", \"d\"};\njarjesta(t);\nSystem.out.println(Arrays.toString(t));\n";
        klass.staticMethod("jarjesta").returningVoid().taking(String[].class).withNiceError(virhe).invoke(t);
        Assert.assertArrayEquals(virhe, new String[]{"a", "b", "d", "x"}, t);
    }

    @Test
    public void jarjestaLukulista() throws Throwable {

        ArrayList<Integer> luvut = new ArrayList<>();
        luvut.add(3);
        luvut.add(2);
        luvut.add(0);
        luvut.add(1);

        String virhe = "Kokeile:\nArrayList<Integer> luvut = new ArrayList<>();\n"
                + "luvut.add(3);\n"
                + "luvut.add(2);\n"
                + "luvut.add(0);\n"
                + "luvut.add(1);\n"
                + "jarjestaLuvut(luvut);\n"
                + "System.out.println(luvut);\n";
        klass.staticMethod("jarjestaLuvut").returningVoid().taking(ArrayList.class).withNiceError(virhe).invoke(luvut);

        ArrayList<Integer> luvut2 = new ArrayList<>();
        luvut2.add(0);
        luvut2.add(1);
        luvut2.add(2);
        luvut2.add(3);

        Assert.assertEquals(virhe, luvut2, luvut);
    }

    @Test
    public void jarjestaMerkkijonolista() throws Throwable {

        ArrayList<String> merkkijonot = new ArrayList<>();
        merkkijonot.add("d");
        merkkijonot.add("b");
        merkkijonot.add("a");
        merkkijonot.add("c");

        String virhe = "Kokeile:\n"
                + "ArrayList<String> merkkijonot = new ArrayList<>();\n"
                + "merkkijonot.add(\"d\");\n"
                + "merkkijonot.add(\"b\");\n"
                + "merkkijonot.add(\"a\");\n"
                + "merkkijonot.add(\"c\");\n"
                + "jarjestaMerkkijonot(merkkijonot);\n"
                + "System.out.println(merkkijonot);\n";
        klass.staticMethod("jarjestaMerkkijonot").returningVoid().taking(ArrayList.class).withNiceError(virhe).invoke(merkkijonot);

        ArrayList<String> merkkijonot2 = new ArrayList<>();
        merkkijonot2.add("a");
        merkkijonot2.add("b");
        merkkijonot2.add("c");
        merkkijonot2.add("d");
        
        Assert.assertEquals(virhe, merkkijonot2, merkkijonot);
    }

}
