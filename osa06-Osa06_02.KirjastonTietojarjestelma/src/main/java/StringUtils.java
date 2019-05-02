/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Pyry
 */
public class StringUtils {
    public static boolean sisaltaa(String sana, String haettava) {
        if (sana.equals("")) return false;
        if (haettava.equals("")) return false;
        if (sana.trim().toUpperCase().contains(haettava.trim().toUpperCase())) {
            return true;
        }
        return false;
        }
    
    
        
    }
    

