/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Class.PanelLoader;

/**
 *
 * @author Vikum
 */
import java.awt.BorderLayout;

import javax.swing.JPanel;

public class PanelLoader {

    private JPanel jpf = null;
    private JPanel jpb = null;

    public void swichPanel(JPanel jpb, JPanel jpf) {
        this.jpf = jpf;
        this.jpb = jpb;
        swich();
    }

    private void swich() {
        jpb.removeAll();
        jpb.add(jpf, BorderLayout.CENTER);
        jpf.setVisible(true);
        System.out.println("Swich successfully.");
    }

}
