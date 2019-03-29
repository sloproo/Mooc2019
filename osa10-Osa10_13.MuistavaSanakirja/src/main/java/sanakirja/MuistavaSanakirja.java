/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sanakirja;

/**
 *
 * @author Pyry
 */
import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;
import java.io.PrintWriter;

public class MuistavaSanakirja {
    private ArrayList<String> sanat;
    private String kaytettavaTiedosto;
    
    public MuistavaSanakirja() {
        this.sanat = new ArrayList<>();
    }
    
    public MuistavaSanakirja(String tiedosto) {
        this();
        this.kaytettavaTiedosto = tiedosto;
    }
    
    public boolean loytyyko(String haettava) {
        boolean loytyiko = false;
        for (String s: sanat) {
            String[] splitattu = s.split(":");
            if (splitattu[0].equals(haettava) || splitattu[1].equals(haettava)) {
                loytyiko = true;
            }
        }
        return loytyiko;
    }
    
    public void lisaa(String sana, String kaannos) {
        if (loytyyko(sana) == false && loytyyko(kaannos) == false) {
            sanat.add(sana + ":" + kaannos);
        }
    }
        
     public String kaanna (String sana) {
        if (loytyyko(sana) == false) return null;
        for (String r: sanat) {
            if (!(r.contains(sana))) continue;
            if (r.contains(sana)) {
                String[] splitattu = r.split(":");
                if (splitattu[0].equalsIgnoreCase(sana)) return splitattu[1];
                if (splitattu[1].equalsIgnoreCase(sana)) return splitattu[0];
            }
        }
    return null;

    }
     
    public void poista(String sana)  {
        if (loytyyko(sana) == true) {
            for (int i = 0; i < sanat.size(); i++) {
                if (!(sanat.get(i).contains(sana))) continue;
                if (sanat.get(i).contains(sana)) {
                    String[] splitattu = sanat.get(i).split(":");
                    if (splitattu[0].equalsIgnoreCase(sana) || 
                        splitattu[1].equalsIgnoreCase(sana)){
                        sanat.remove(i);
                        break;
                    }
                }
            }
        }

    }
    
    public boolean lataa() {
        try (Scanner tiedostonLukija = new Scanner(new File(kaytettavaTiedosto))) {
            while (tiedostonLukija.hasNextLine()) {
                String rivi = tiedostonLukija.nextLine();
                String[] osat = rivi.split(":");
                this.lisaa(osat[0], osat[1]);
            }
            return true;
        } catch (Exception e) {
                    return false;
        }
    }
    
    public boolean tallenna() {
       try (PrintWriter kirjoittaja = new PrintWriter(kaytettavaTiedosto)) {
           for (int i = 0; i< sanat.size(); i++) {
               kirjoittaja.println(sanat.get(i));
           }
       } catch (Exception e) {
           return false;
       }
       return true;
    }

}
