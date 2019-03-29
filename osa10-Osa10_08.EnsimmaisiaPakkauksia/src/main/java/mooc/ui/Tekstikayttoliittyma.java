/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mooc.ui;

/**
 *
 * @author Pyry
 */
public class Tekstikayttoliittyma implements Kayttoliittyma {
    
    @Override
    public void paivita() {
        System.out.println("Päivitetään käyttöliittymää");
    }
    
}
