/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Class.Job.Inside;

/**
 *
 * @author J.M Vikum Chathuranga 
 *         IT13131098
 *
 */

import Class.Job.Select.JobSelect;

import java.util.Vector;

public class JobInside {
    
    private String jobid;
    private int width;
    private int height;
    private char unit;
    private int plateperbook;
    private int printup;

    public JobInside(String jobid, int width, int height, char unit, int plateperbook, int printup) {
        this.jobid = jobid;
        this.width = width;
        this.height = height;
        this.unit = unit;
        this.plateperbook = plateperbook;
        this.printup = printup;
    }
    
    public JobInside(Vector<String> v) {
        fillWithVector(v);
    }
    
    public JobInside(String jobid) {
        JobSelect js = new JobSelect();
        Vector<String> v = js.getInsideDetail(jobid);
        fillWithVector(v);
    }
    
    private void fillWithVector(Vector<String> v) {
        this.setJobid(v.elementAt(1));
        this.setWidth(Integer.parseInt(v.elementAt(2)));
        this.setHeight(Integer.parseInt(v.elementAt(3)));
        this.setUnit(v.elementAt(4).charAt(0));
        this.setPlateperbook(Integer.parseInt(v.elementAt(5)));
        this.setPrintup(Integer.parseInt(v.elementAt(6)));
    }
    
    public Vector getInsideDetail() {

        Vector v = new Vector();

        v.add(getJobid());
        v.add(getWidth());
        v.add(getHeight());
        v.add(getUnit());
        v.add(getPlateperbook());
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
     * @return the unit
     */
    public char getUnit() {
        return unit;
    }

    /**
     * @param unit the unit to set
     */
    public void setUnit(char unit) {
        this.unit = unit;
    }

    /**
     * @return the plateperbook
     */
    public int getPlateperbook() {
        return plateperbook;
    }

    /**
     * @param plateperbook the plateperbook to set
     */
    public void setPlateperbook(int plateperbook) {
        this.plateperbook = plateperbook;
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
