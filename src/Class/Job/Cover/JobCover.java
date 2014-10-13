/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Class.Job.Cover;

/**
 *
 * @author J.M Vikum Chathuranga 
 *         IT13131098
 *
 */

import Class.Job.Select.JobSelect;

import java.util.Vector;

public class JobCover {
    
    private String jobid;
    private int width;
    private int height;
    private char unite;
    private int printup;
    private int coverpaperQty;

    public JobCover(String jobid, int width, int height, char unite, int printup) {
        this.jobid = jobid;
        this.width = width;
        this.height = height;
        this.unite = unite;
        this.printup = printup;
    }
    
    public JobCover(Vector<String> v) {
        fillWithVector(v);
    }
    
    public JobCover(String jobid) {
        JobSelect js = new JobSelect();
        Vector<String> v = js.getCoverDetail(jobid);
        fillWithVector(v);
    }
    
    private void fillWithVector(Vector<String> v) {
        this.setJobid(v.elementAt(1));
        this.setWidth(Integer.parseInt(v.elementAt(2)));
        this.setHeight(Integer.parseInt(v.elementAt(3)));
        this.setUnite(v.elementAt(4).charAt(0));
        this.setPrintup(Integer.parseInt(v.elementAt(5)));
    }
    
    public Vector getJobCoverDetail() {
        Vector v = new Vector();
        v.add(getJobid());
        v.add(getWidth());
        v.add(getHeight());
        v.add(getUnite());
        v.add(getPrintup());
        return v;
    }

    /**
     * @return the jobid
     */
    public String getJobid() {
        return jobid;
    }

    /**
     * @param jobid the jobid to set
     */
    public void setJobid(String jobid) {
        this.jobid = jobid;
    }

    /**
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * @param height the height to set
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * @return the unite
     */
    public char getUnite() {
        return unite;
    }

    /**
     * @param unite the unite to set
     */
    public void setUnite(char unite) {
        this.unite = unite;
    }

    /**
     * @return the printup
     */
    public int getPrintup() {
        return printup;
    }

    /**
     * @param printup the printup to set
     */
    public void setPrintup(int printup) {
        this.printup = printup;
    }
    
    
    
}
