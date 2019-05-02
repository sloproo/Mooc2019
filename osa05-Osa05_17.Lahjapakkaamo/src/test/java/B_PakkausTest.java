
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class B_PakkausTest<_Lahja, _Pakkaus> {

    private Class lahjaClass;
    private Constructor lahjaConstructor;
    private Class pakkausClass;
    private Constructor pakkausConstructor;
    String klassName = "Pakkaus";
    Reflex.ClassRef<_Pakkaus> _PakkausRef;
    Reflex.ClassRef<_Lahja> _LahjaRef;

    @Before
    public void setup() {
        _PakkausRef = Reflex.reflect("Pakkaus");
        _LahjaRef = Reflex.reflect("Lahja");

        try {
            lahjaClass = ReflectionUtils.findClass("Lahja");
            lahjaConstructor = ReflectionUtils.requireConstructor(lahjaClass, String.class, int.class);

            pakkausClass = ReflectionUtils.findClass("Pakkaus");
            pakkausConstructor = ReflectionUtils.requireConstructor(pakkausClass);

        } catch (Throwable t) {
        }
    }

    @Test
    @Points("05-17.2")
    public void luokkaJulkinen() {
        assertTrue("Luokan " + klassName + " pitää olla julkinen, eli se tulee määritellä\npublic class " + klassName + " {...\n}", _PakkausRef.isPublic());
    }

    @Test
    @Points("05-17.2")
    public void eiYlimaaraisiaMuuttujia() {
        saniteettitarkastus(klassName, 1, "lahjat tallettava lista. Voit laskea pakkauksen lahjojen yhteispainonkäymällä lahjat läpi!");
    }

    @Test
    @Points("05-17.2")
    public void testaaPakkausKonstruktori() throws Throwable {
        Reflex.MethodRef0<_Pakkaus, _Pakkaus> ctor = _PakkausRef.constructor().takingNoParams().withNiceError();
        assertTrue("Määrittele luokalle " + klassName + " julkinen konstruktori: public " + klassName + "()", ctor.isPublic());
        String v = "virheen aiheutti koodi new Pakkaus();";
        ctor.withNiceError(v).invoke();
    }

    public _Pakkaus luoPakkaus() throws Throwable {
        return _PakkausRef.constructor().takingNoParams().withNiceError().invoke();
    }

    public _Lahja luoLahja(String nimi, int paino) throws Throwable {
        return _LahjaRef.constructor().taking(String.class, int.class).withNiceError().invoke(nimi, paino);
    }

    @Test
    @Points("05-17.2")
    public void lisaaLahjaMetodi() throws Throwable {
        _Lahja lahja = luoLahja("kirja", 1);
        _Pakkaus pakkaus = luoPakkaus();

        String v = "\nLahja t = new Lahja(\"kirja\",1); \n"
                + "Pakkaus m = new Pakkaus();\n"
                + "m.lisaaLahja(t);";

        assertTrue("Luokalla Pakkaus tulee olla metodi public void lisaaLahja(Lahja lahja)", _PakkausRef.method(pakkaus, "lisaaLahja").returningVoid().taking(_LahjaRef.cls()).withNiceError(v).isPublic());

        _PakkausRef.method(pakkaus, "lisaaLahja").returningVoid().taking(_LahjaRef.cls()).withNiceError(v).invoke(lahja);
    }

    @Test
    @Points("05-17.2")
    public void tarkistaLisaaLahjanToiminta() {
        try {
            Object pakkaus = ReflectionUtils.invokeConstructor(pakkausConstructor);

            Method lisaaLahjaMeto = ReflectionUtils.requireMethod(pakkausClass, "lisaaLahja", ReflectionUtils.findClass("Lahja"));

            Object tiili = ReflectionUtils.invokeConstructor(lahjaConstructor, "Tiili", 8);
            Object hammas = ReflectionUtils.invokeConstructor(lahjaConstructor, "Hammas", 8);
            ReflectionUtils.invokeMethod(void.class, lisaaLahjaMeto, pakkaus, tiili);
            ReflectionUtils.invokeMethod(void.class, lisaaLahjaMeto, pakkaus, hammas);

            List<Object> lahjat = (List<Object>) oliomuuttujaLista(pakkausClass, pakkaus);
            if (!lahjat.contains(tiili)) {
                fail("Kaikkia lisättyjä lahjoja ei löytynyt pakkauksen sisäisestä lahjalistasta.");
            }

            if (!lahjat.contains(hammas)) {
                fail("Kaikkia lisättyjä lahjoja ei löytynyt pakkauksen sisäisestä lahjalistasta.");
            }

        } catch (Throwable t) {
            fail("Tarkista että Pakkaus-luokan metodi lisaaLahja lisää tavaroita pakkaukseen. Onhan luokalle määritelty myös ArrayList, joka on alustettu?");
        }
    }
    
    @Test
    @Points("05-17.2")
    public void yhteispainoMetodi1() throws Throwable {        
        _Lahja lahja = luoLahja("kirja", 1);
        _Pakkaus pakkaus = luoPakkaus();

        String v = "\nLahja t = new Lahja(\"kirja\",1);\n"
                + "Pakkaus m = new Pakkaus();\n"
                + "m.lisaaLahja(t);\n"
                + "System.out.println(m.yhteispaino());";

        assertTrue("Luokalla Pakkaus tulee olla metodi public int yhteispaino()", _PakkausRef.method(pakkaus, "yhteispaino").returning(int.class).takingNoParams().withNiceError(v).isPublic());

        _PakkausRef.method(pakkaus, "lisaaLahja").returningVoid().taking(_LahjaRef.cls()).withNiceError(v).invoke(lahja);
        
        int paino = _PakkausRef.method(pakkaus, "yhteispaino").returning(int.class).takingNoParams().invoke();
        assertEquals("Koodilla " + v + "\nTulostuksen pitäisi olla 1. Nyt tulostus oli " + paino, 1, paino);
    }
    
    @Test
    @Points("05-17.2")
    public void yhteispainoMetodi2() throws Throwable {        
        _Pakkaus pakkaus = luoPakkaus();

        String v = "\nLahja l1 = new Lahja(\"kirja\",2);\n"
                + "Lahja l2 = new Lahja(\"nalle\",1);\n"
                + "Lahja l3 = new Lahja(\"nauris\",4);\n"
                + "Pakkaus p = new Pakkaus();\n"
                + "p.lisaaLahja(l1);\n"
                + "p.lisaaLahja(l2);\n"
                + "p.lisaaLahja(l3);\n"
                + "System.out.println(p.yhteispaino());";

        assertTrue("Luokalla Pakkaus tulee olla metodi public int yhteispaino()", _PakkausRef.method(pakkaus, "yhteispaino").returning(int.class).takingNoParams().withNiceError(v).isPublic());

        _PakkausRef.method(pakkaus, "lisaaLahja").returningVoid().taking(_LahjaRef.cls()).withNiceError(v).invoke(luoLahja("kirja", 2));
        _PakkausRef.method(pakkaus, "lisaaLahja").returningVoid().taking(_LahjaRef.cls()).withNiceError(v).invoke(luoLahja("nalle", 1));
        _PakkausRef.method(pakkaus, "lisaaLahja").returningVoid().taking(_LahjaRef.cls()).withNiceError(v).invoke(luoLahja("nauris", 4));
        
        int paino = _PakkausRef.method(pakkaus, "yhteispaino").returning(int.class).takingNoParams().invoke();
        assertEquals("Koodilla " + v + "\nTulostuksen pitäisi olla 7. Nyt tulostus oli " + paino, 7, paino);
    }
    
    private boolean sisaltaa(String palautus, String... oletetutArvot) {
        for (String arvo : oletetutArvot) {
            if (!palautus.contains(arvo)) {
                return false;
            }
        }

        return true;
    }

    private Object oliomuuttujaLista(Class clazz, Object container) {
        for (Field f : clazz.getDeclaredFields()) {
            if (f.getType().equals(List.class)) {
                f.setAccessible(true);
                try {
                    return f.get(container);
                } catch (IllegalArgumentException ex) {
                } catch (IllegalAccessException ex) {
                }
            }

            if (f.getType().equals(ArrayList.class)) {
                f.setAccessible(true);
                try {
                    return f.get(container);
                } catch (IllegalArgumentException ex) {
                } catch (IllegalAccessException ex) {
                }
            }

            if (f.getType().equals(LinkedList.class)) {
                f.setAccessible(true);
                try {
                    return f.get(container);
                } catch (IllegalArgumentException ex) {
                } catch (IllegalAccessException ex) {
                }
            }
        }

        return null;
    }

    private void saniteettitarkastus(String klassName, int n, String m) throws SecurityException {
        Field[] kentat = ReflectionUtils.findClass(klassName).getDeclaredFields();

        for (Field field : kentat) {
            assertFalse("et tarvitse \"stattisia muuttujia\", poista luokalta " + klassName + " muuttuja " + kentta(field.toString(), klassName), field.toString().contains("static") && !field.toString().contains("final"));
            assertTrue("luokan kaikkien oliomuuttujien näkyvyyden tulee olla private, muuta luokalta " + klassName + " löytyi: " + kentta(field.toString(), klassName), field.toString().contains("private"));
        }

        if (kentat.length > 1) {
            int var = 0;
            for (Field field : kentat) {
                if (!field.toString().contains("final")) {
                    var++;
                }
            }
            assertTrue("et tarvitse " + klassName + "-luokalle kuin " + m + ", poista ylimääräiset", var <= n);
        }
    }

    private String kentta(String toString, String klassName) {
        return toString.replace(klassName + ".", "").replace("java.lang.", "").replace("java.util.", "");
    }
}
