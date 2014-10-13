/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Class.AutoCloseJOptionPane;

/**
 *
 * @author J.M Vikum Chathuranga 
 *         IT13131098
 *
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class AutoCloseJOptionPane {
    
    public void showThisMessage(String titel, String message, int messagetype) {
        JOptionPane pane = new JOptionPane(message, messagetype);
        JDialog dialog = pane.createDialog(null, titel);
        dialog.setModal(false);
        dialog.setVisible(true);
        Timer t = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.setVisible(false);
            }
        });
        t.start();
    }
    
    public void showThisConfirm(String titel, String message, int messagetype, int optiontype) {
        JOptionPane pane = new JOptionPane(message, messagetype, optiontype);
        JDialog dialog = pane.createDialog(null, titel);
        dialog.setModal(false);
        dialog.setVisible(true);
        Timer t = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.setVisible(false);
            }
        });
        t.start();
    }
 
}
