/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ImagePanel;

import java.awt.Image;
import java.awt.Graphics;
import java.awt.Dimension;

/**
 *
 * @author Vikum
 */
public class ImagePanel extends javax.swing.JPanel{
    
    private Image backGroundImage;
    
    public ImagePanel() {
        
    }
    
    public void setImage(Image image) {
        this.backGroundImage = image;
        Dimension size = new Dimension(this.getWidth(), this.getHeight());
        this.setPreferredSize(size);
        this.setMinimumSize(size);
        this.setMaximumSize(size);
        this.setSize(size);
        this.setOpaque(false);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        if (this.backGroundImage != null) {
            g.drawImage(backGroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
        }
    }
    
    public static void main(String[] args) {
        
    }
    
}
