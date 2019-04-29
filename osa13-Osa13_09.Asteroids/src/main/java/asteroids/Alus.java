/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroids;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;

/**
 *
 * @author Pyry
 */
public class Alus extends Hahmo{
    
    private Polygon hahmo;
    private Point2D liike;
    
    public Alus(int x, int y) {
        super(new Polygon(-5, -5, 10, 0, -5, 5), x, y);
    }
    
}