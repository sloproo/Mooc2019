
import fi.helsinki.cs.tmc.edutestutils.Points;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class LaskuriTest {

    String name = "Laskuri";

    Class l;

    MethodSignature arvo = new MethodSignature(Integer.TYPE, "arvo");
    MethodSignature lisaa = new MethodSignature(Void.TYPE, "lisaa");
    MethodSignature vahenna = new MethodSignature(Void.TYPE, "vahenna");

    MethodSignature lisaa2 = new MethodSignature(Void.TYPE, "lisaa", Integer.TYPE);
    MethodSignature vahenna2 = new MethodSignature(Void.TYPE, "vahenna", Integer.TYPE);

    ConstructorSignature ci = new ConstructorSignature(Integer.TYPE);
    ConstructorSignature c = new ConstructorSignature();

    @Before
    public void haeLuokka() {
        l = Utils.getClass(name);
    }

    @Points("05-06.1")
    @Test
    public void testaaKonstruointi() {
        ci.invokeIn(l, 10);
        ci.invokeIn(l, 2);
    }

    @Points("05-06.1")
    @Test
    public void testaaArvo() {

        Object o = ci.invokeIn(l, 10);
        Integer i = -9000;
        i = (Integer) arvo.invokeIn(l, o);
        assertEquals("Palautit väärän arvon. Kokeile tätä:\nLaskuri l = new Laskuri(10);\nSystem.out.println(l.arvo());\n",
                10, (int) i);

        o = ci.invokeIn(l, 2);
        i = (Integer) arvo.invokeIn(l, o);
        assertEquals("Palautit väärän arvon. Kokeile tätä:\nLaskuri l = new Laskuri(2);\nSystem.out.println(l.arvo());\n",
                2, (int) i);

    }

    @Points("05-06.1")
    @Test
    public void testaaLisaa() {

        Object o = ci.invokeIn(l, 5);
        lisaa.invokeIn(l, o);
        int i = (Integer) arvo.invokeIn(l, o);
        assertEquals("Palautit väärän arvon. Kokeile tätä:\nLaskuri l = new Laskuri(5);\nl.lisaa();\nSystem.out.println(l.arvo());\n",
                6, (int) i);

        lisaa.invokeIn(l, o);
        i = (Integer) arvo.invokeIn(l, o);
        assertEquals("Palautit väärän arvon. Kokeile tätä:\nLaskuri l = new Laskuri(5);\nl.lisaa();\nl.lisaa();\nSystem.out.println(l.arvo());\n",
                7, (int) i);

    }

    @Points("05-06.1")
    @Test
    public void testaaVahenna() {

        Object o = ci.invokeIn(l, 900);
        vahenna.invokeIn(l, o);
        int i = (Integer) arvo.invokeIn(l, o);
        assertEquals("Palautit väärän arvon. Kokeile tätä:\nLaskuri l = new Laskuri(900);\nl.vahenna();\nSystem.out.println(l.arvo());\n",
                899, (int) i);

        vahenna.invokeIn(l, o);
        i = (Integer) arvo.invokeIn(l, o);
        assertEquals("Palautit väärän arvon. Kokeile tätä:\nLaskuri l = new Laskuri(900);\nl.vahenna();\nl.vahenna();\nSystem.out.println(l.arvo());\n",
                898, (int) i);
    }

    @Points("05-06.1")
    @Test
    public void testaaVahennaEiTarkistusta() {
        Object o = ci.invokeIn(l, 2);
        vahenna.invokeIn(l, o);
        int i = (Integer) arvo.invokeIn(l, o);
        assertEquals("Palautit väärän arvon. Kokeile tätä:\nLaskuri l = new Laskuri(2);\nl.vahenna();\nSystem.out.println(l.arvo());\n",
                1, (int) i);

        vahenna.invokeIn(l, o);
        i = (Integer) arvo.invokeIn(l, o);
        assertEquals("Palautit väärän arvon. Kokeile tätä:\nLaskuri l = new Laskuri(2);\nl.vahenna();\nl.vahenna();\nSystem.out.println(l.arvo());\n",
                0, (int) i);

        vahenna.invokeIn(l, o);
        i = (Integer) arvo.invokeIn(l, o);
        assertEquals("Palautit väärän arvon. Kokeile tätä:\nLaskuri l = new Laskuri(2);\nl.vahenna();\nl.vahenna();\nl.vahenna();\nSystem.out.println(l.arvo());\n",
                -1, (int) i);

    }
    
    @Points("05-06.1")
    @Test
    public void testaaKonstruktorit() {

        Object o = ci.invokeIn(l, 11);
        int i = (Integer) arvo.invokeIn(l, o);
        assertEquals("Palautit väärän arvon. Kokeile tätä:\nLaskuri l = new Laskuri(11);\nSystem.out.println(l.arvo());\n",
                11, i);

        o = c.invokeIn(l);
        i = (Integer) arvo.invokeIn(l, o);
        assertEquals("Palautit väärän arvon. Kokeile tätä:\nLaskuri l = new Laskuri();\nSystem.out.println(l.arvo());\n",
                0, i);

    }

    @Points("05-06.2")
    @Test
    public void testaaParametrillinenLisaa() {

        Object o = ci.invokeIn(l, 5);
        lisaa2.invokeIn(l, o, 2);
        int i = (Integer) arvo.invokeIn(l, o);
        assertEquals("Palautit väärän arvon. "
                + "Kokeile tätä:\nLaskuri l = new Laskuri(5);\nl.lisaa(2);\nSystem.out.println(l.arvo());\n",
                7, (int) i);

        lisaa2.invokeIn(l, o, 4);
        i = (Integer) arvo.invokeIn(l, o);
        assertEquals("Palautit väärän arvon. Kokeile tätä:\n"
                + "Laskuri l = new Laskuri(5);\nl.lisaa(2);\nl.lisaa(4);\nSystem.out.println(l.arvo());\n",
                11, (int) i);
    }

    @Points("05-06.2")
    @Test
    public void testaaParametrillinenLisaaNegatiivisella() {

        Object o = ci.invokeIn(l, 5);
        lisaa2.invokeIn(l, o, -2);
        int i = (Integer) arvo.invokeIn(l, o);
        assertEquals("Negatiivisen lisäyksen ei pitäisi muuttaa laskurin arvoa "
                + "Kokeile tätä:\nLaskuri l = new Laskuri(5);\nl.lisaa(-2);\nSystem.out.println(l.arvo());\n",
                5, (int) i);
    }

    @Points("05-06.2")
    @Test
    public void testaaParametrillinenVahennys() {

        Object o = ci.invokeIn(l, 900);
        vahenna2.invokeIn(l, o, 7);
        int i = (Integer) arvo.invokeIn(l, o);
        assertEquals("Palautit väärän arvon. Kokeile tätä:\nLaskuri l = new Laskuri(900);\nl.vahenna(7);\nSystem.out.println(l.arvo());\n",
                893, (int) i);

        vahenna2.invokeIn(l, o, 3);
        i = (Integer) arvo.invokeIn(l, o);
        assertEquals("Palautit väärän arvon. Kokeile tätä:\nLaskuri l = new Laskuri(900);\nl.vahenna(7);\nl.vahenna(3);\nSystem.out.println(l.arvo());\n",
                890, (int) i);
    }

    @Points("05-06.2")
    @Test
    public void testaaParametrillinenVahennysNegatiivisella() {

        Object o = ci.invokeIn(l, 900);
        vahenna2.invokeIn(l, o, -55);
        int i = (Integer) arvo.invokeIn(l, o);
        assertEquals("Negatiivisen vähennyksen ei pitäisi muuttaa laskurin arvoa "
                + " Kokeile tätä:\nLaskuri l = new Laskuri(900);\nl.vahenna(-55);\nSystem.out.println(l.arvo());\n",
                900, (int) i);

    }

    @Points("05-06.2")
    @Test
    public void testaaParametrillinenVahennaEiTarkistusta() {
        Object o = ci.invokeIn(l, 2);
        vahenna2.invokeIn(l, o, 5);
        int i = (Integer) arvo.invokeIn(l, o);
        assertEquals("Palautit väärän arvon. Kokeile tätä:\nLaskuri l = new Laskuri(2);\nl.vahenna(5);\nSystem.out.println(l.arvo());\n",
                -3, (int) i);
    }
}
