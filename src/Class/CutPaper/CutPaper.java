/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Class.CutPaper;

/**
 *
 * @author J.M Vikum Chathuranga 
 *         IT13131098
 *
 */
public class CutPaper {

    private double height;
    private double width;
    
    private double cutingH;
    private double cutingW;
    
    private double x;
    private double y;
    
    private int count ;

    public void setHeight(double height) {
        this.height = height;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setCutingH(double cutingH) {
        this.cutingH = cutingH;
    }

    public void setCutingW(double cutingW) {
        this.cutingW = cutingW;
    }
    
    public CutPaper() {
        
    }

    public CutPaper(double height, double width, double cutingH, double cutingW) {
        this.height = height;
        this.width = width;
        this.cutingH = cutingH;
        this.cutingW = cutingW;
    }
    
    public void setPaperSize(double height, double width) {
        this.height = height;
        this.width = width;
    }
    
    public void setCuttingSize(double cutingH, double cutingW) {
        this.cutingH = cutingH;
        this.cutingW = cutingW;
    }
    
    public int calcPapers(int ph, int pw, int h, int w) {
        
        setPaperSize(ph, pw);
        setCuttingSize(h, w);
        
        return calcPapers();
        
    }
    
    public int calcPapers() {
        
        x = height / cutingH ;
        y = width / cutingW ;
        
        count = (int) (x * y) ;
        
        return count;
        
    }
    
}
