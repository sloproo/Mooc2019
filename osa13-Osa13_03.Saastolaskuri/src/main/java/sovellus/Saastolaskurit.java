/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sovellus;

import javafx.scene.chart.XYChart;

/**
 *
 * @author Pyry
 */
public class Saastolaskurit {
    
    public XYChart.Series tallennusKuvaaja(int tallennus, Number korko) {
        XYChart.Series palautettava = new XYChart.Series();
        for (int i = 0; i <= 30; i++) {
            palautettava.getData().add(new XYChart.Data(i, i * tallennus * 12));
        }
    }
    
}
