/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroids;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
/**
 *
 * @author Pyry
 */
public abstract class Hahmo {
    
    private Polygon hahmo;
    private Point2D liike;
    private boolean elossa;
    
    public Hahmo(Polygon monikulmio, int x, int y) {
        this.hahmo = monikulmio;
        this.hahmo.setTranslateX(x);
        this.hahmo.setTranslateY(y);
        
        this.liike = new Point2D(0, 0);
        this.elossa = true;
    }
    
    public Polygon getHahmo() {
        return hahmo;
    }
    
    public void kaannaVasemmalle() {
        this.hahmo.setRotate(this.hahmo.getRotate() - 5);
    }
    
    public void kaannaOikealle() {
        this.hahmo.setRotate(this.hahmo.getRotate() + 5);
    }
    
    public void liiku() {
        this.hahmo.setTranslateX(this.hahmo.getTranslateX() + this.liike.getX());
        this.hahmo.setTranslateY(this.hahmo.getTranslateY() + this.liike.getY());
    
    if (this.hahmo.getTranslateX() < 0 ) {
     this.hahmo.setTranslateX(this.hahmo.getTranslateX() + AsteroidsSovellus.LEVEYS);
    }
    
    if (this.hahmo.getTranslateX() > AsteroidsSovellus.LEVEYS) {
        this.hahmo.setTranslateX(this.hahmo.getTranslateX() % AsteroidsSovellus.LEVEYS);
    }

    if (this.hahmo.getTranslateY() < 0) {
        this.hahmo.setTranslateY(this.hahmo.getTranslateY() + AsteroidsSovellus.KORKEUS);
    }

    if (this.hahmo.getTranslateY() > AsteroidsSovellus.KORKEUS) {
        this.hahmo.setTranslateY(this.hahmo.getTranslateY() % AsteroidsSovellus.KORKEUS);
    }
    
    }

    public Point2D getLiike() {
        return liike;
    }

    public void setLiike(Point2D liike) {
        this.liike = liike;
    }
    
    public void kiihdyta() {
        double muutosX = Math.cos(Math.toRadians(this.hahmo.getRotate()));
        double muutosY = Math.sin(Math.toRadians(this.hahmo.getRotate()));
        
        muutosX *= 0.05;
        muutosY *= 0.05;
        
        this.liike = this.liike.add(muutosX, muutosY);
    }
    
    public boolean tormaa(Hahmo toinen) {
        Shape tormaysalue = Shape.intersect(this.hahmo, toinen.getHahmo());
        return tormaysalue.getBoundsInLocal().getWidth() != -1;
    }

    public boolean isElossa() {
        return elossa;
    }

    public void setElossa(boolean elossa) {
        this.elossa = elossa;
    }
    
    
}
