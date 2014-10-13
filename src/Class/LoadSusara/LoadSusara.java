/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Class.LoadSusara;

import GUI.Home.Home;
import GUI.SplashScreen.SplashScreen;

import javax.swing.UIManager;
import javax.swing.JOptionPane;

/**
 *
 * @author Vikum
 */
public class LoadSusara extends Thread {
    
    private SplashScreen ss;
    private int i = 0;
    private static Home h ;
    
    public LoadSusara() {
        ss = new SplashScreen();
        ss.setVisible(true);
    }
    
    @Override
    public void run() {
        
        while(i<=100) {
            try {
                ss.pbloading.setValue(i);
                i++;
                if(i==50) {
                    h = new Home();
                }
                sleep(20);
            } catch (InterruptedException ex) {
                
            }
        }
        if (i == 101) {
            h.setVisible(true);
            ss.dispose();
        }
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Look And Feel ex");
        }
        LoadSusara ls = new LoadSusara();
        ls.run();
        
    }
    
}
