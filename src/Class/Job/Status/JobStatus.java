/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Class.Job.Status;

/**
 *
 * @author J.M Vikum Chathuranga 
 *         IT13131098
 *
 */

import Class.Job.Select.JobSelect;

import java.util.Vector;

public class JobStatus {
    
    private int statusId;
    private String jobid;
    private String statusName;
    private String startDate;
    private String endDate;
    private String machine;
    private int sqty;
    
    private JobSelect js;
    
    public JobStatus() {
        
    }
    
    public JobStatus(String jobid, String statusName, String startDate, String endDate, String machine, int sqty) {
        this.jobid = jobid;
        this.statusName = statusName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.machine = machine;
        this.sqty = sqty;
    }

    public JobStatus(int statusId, String jobid, String statusName, String startDate, String endDate, String machine, int sqty) {
        this.statusId = statusId;
        this.jobid = jobid;
        this.statusName = statusName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.machine = machine;
        this.sqty = sqty;
    }
    
    public JobStatus(Vector<String> vector) {
        statusId = Integer.parseInt(vector.elementAt(0));
        jobid = vector.elementAt(1);
        statusName = vector.elementAt(2);
        startDate = vector.elementAt(3);
        endDate = vector.elementAt(4);
        machine = vector.elementAt(5);
        try {
            sqty = Integer.parseInt(vector.elementAt(6));
        } catch (NumberFormatException nfe) {
            sqty = 0;
        }
    }
    
    public Vector getJobStatus() {
        Vector<String> v = new Vector<String>();
        v.add(jobid);
        v.add(statusName);
        v.add(startDate);
        v.add(endDate);
        v.add(machine);
        v.add(Integer.toString(sqty));
        return v;
    }

    public String getJobid() {
        return jobid;
    }

    public void setJobid(String jobid) {
        this.jobid = jobid;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getMachine() {
        return machine;
    }

    public void setMachine(String machine) {
        this.machine = machine;
    }

    public int getSqty() {
        return sqty;
    }

    public void setSqty(int sqty) {
        this.sqty = sqty;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }
    
}
