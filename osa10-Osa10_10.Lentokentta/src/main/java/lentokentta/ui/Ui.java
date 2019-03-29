/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lentokentta.ui;
import java.util.Scanner;
import lentokentta.logiikka.Hallinta;

/**
 *
 * @author Pyry
 */
public class Ui {
    private Scanner lukija;
    private Hallinta hallinta;
    
    public Ui(Hallinta hallinta, Scanner lukija) {
        this.lukija = lukija;
        this.hallinta = hallinta;
    }
    
    public void kaynnista() {
        kaynnistaLentokentanHallinta();
        System.out.println("");
        kaynnistaLentoPalvelu();
        System.out.println("");
    }
    
    private void kaynnistaLentokentanHallinta(){
        System.out.println("Lentokentän hallinta");
        System.out.println("--------------------");
        System.out.println();

        while (true) {
            System.out.println("Valitse toiminto:");
            System.out.println("[1] Lisää lentokone");
            System.out.println("[2] Lisää lento");
            System.out.println("[x] Poistu hallintamoodista");
            
            System.out.println("> ");
            String komento = lukija.nextLine();
            if (komento.equals("1")) {
                System.out.println("Anna lentokoneen tunnus: ");
                String tunnusInput = lukija.nextLine();
                System.out.println("Anna lentokoneen kapasiteetti: ");
                int kapasiteettiInput = Integer.valueOf(lukija.nextLine());
                this.hallinta.lisaaLentokone(tunnusInput, kapasiteettiInput);
            } else if (komento.equals("2")) {
                System.out.println("Anna lentokoneen tunnus: ");
                String koneInput = lukija.nextLine();
                System.out.println("Anna lähtöpaikan tunnus: ");
                String lahtoInput = lukija.nextLine();
                System.out.println("Anna kohdepaikan tunnus: ");
                String kohdeInput = lukija.nextLine();
                this.hallinta.lisaaLento(koneInput, lahtoInput, kohdeInput);
            } else if (komento.equals("x")) break;
                
            }
        
        
        
    }
    
    private void kaynnistaLentoPalvelu() {
        System.out.println("Lentopalvelu");
        System.out.println("------------");
        System.out.println();
        
        while(true) {
            System.out.println("Valitse toiminto:");
            System.out.println("[1] Tulosta lentokoneet");
            System.out.println("[2] Tulosta lennot");
            System.out.println("[3] Tulosta lentokoneen tiedot");
            System.out.println("[x] Lopeta");
            
            System.out.println("> ");
            String komento = lukija.nextLine();
            if (komento.equals("1")) {
                hallinta.tulostaLentokoneet();
            } else if (komento.equals("2")) {
                hallinta.tulostaLennot();
            } else if (komento.equals("3")) {
                System.out.println("Mikä kone: ");
                hallinta.tulostaLentokone(lukija.nextLine());
            } else if (komento.equals("x")) break;
            
        }
        
        
        
    }
    
}
