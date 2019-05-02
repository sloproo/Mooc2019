/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Pyry
 */
import java.util.Comparator;

public class SamatMaatVierekkainArvojarjestykseen implements Comparator<Kortti>{
    public int compare(Kortti k1, Kortti k2) {
        if (k1.getMaa().ordinal() != k2.getMaa().ordinal()) {
            return k1.getMaa().ordinal() - k2.getMaa().ordinal();
        } else return k1.getArvo() - k2.getArvo();
    }
    
}
