/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Pyry
 * @param <T>
 */
public class Piilo <T>{
    private T piilo;
    
    public Piilo() {
    }
    
    public void laitaPiiloon(T piilotettava) {
        this.piilo = piilotettava;
    }
    
    public T otaPiilosta() {
        if (piilo == null) return null;
        else {
            Object palautettava = piilo;
            this.piilo = null;
            return (T) palautettava;
        }
    }
    
    public boolean onkoPiilossa() {
        return (!(piilo == null));
    }
}
