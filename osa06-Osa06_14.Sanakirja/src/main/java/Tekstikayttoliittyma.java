/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Pyry
 */
import java.util.Scanner;
public class Tekstikayttoliittyma {
    
    private Scanner lukija;
    private Sanakirja sanakirja;
    
    public Tekstikayttoliittyma(Scanner lukija, Sanakirja sanakirja) {
        this.lukija = lukija;
        this.sanakirja = sanakirja;
    }
    
    public void kaynnista() {
        System.out.println("Komennot:");
        System.out.println("lopeta - poistuu käyttöliittymästä");
        while (true) {
            System.out.print("Komento: ");
            String komento = lukija.nextLine();
            if (komento.equals("lopeta")) {
                System.out.println("Hei hei!");
                break;
            } 
            if (komento.equals("lisaa")) {
                System.out.print("Suomeksi:");
                String suomex = lukija.nextLine();
                System.out.print("Englanniksi");
                String englannix = lukija.nextLine();
                this.sanakirja.lisaa(suomex, englannix);
                continue;
            }
            if (komento.equals("kaanna")) {
                System.out.print("Anna sana: ");
                String kaannettava = lukija.nextLine();
                System.out.println("Käännös: " + sanakirja.kaanna(kaannettava));
                continue;
            }
            else {
                System.out.println("Tuntematon komento.");
            }
            
            
            
        }
        
    }
    
}
