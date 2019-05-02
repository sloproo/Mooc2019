/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Pyry
 */
import java.util.HashMap;

public class Lyhenteet {
    private HashMap<String, String> lyhenne;
    
    public Lyhenteet() {
        this.lyhenne = new HashMap<>();
    }
    
    public void lisaaLyhenne(String lyhenne, String selite) {
        this.lyhenne.put(lyhenne, selite);
    }
    
    public boolean onkoLyhennetta(String lyhenne) {
        if (this.lyhenne.containsKey(lyhenne)) {
            return true;
        } else return false;
    }
    
    public String haeLyhenne(String lyhenne) {
        if (this.onkoLyhennetta(lyhenne)) {
            return this.lyhenne.get(lyhenne);
        } else return null;
    }
    
    
}
