/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Class.Job.Insert;

/**
 *
 * @author J.M Vikum Chathuranga 
 *         IT13131098
 *
 */

import Class.Job.DBAccess.DBAccess;

import Class.Job.Job.Job;
import Class.Job.Cover.JobCover;
import Class.Job.Inside.JobInside;
import Class.Job.Status.JobStatus;

import Class.Job.Delete.JobDelete;
import Class.Job.Update.JobUpdate;

import Class.AutoCloseJOptionPane.AutoCloseJOptionPane;

import javax.swing.JDialog;
import javax.swing.Timer;
import javax.swing.JOptionPane;

public class JobInsert {
    
    private DBAccess dba;
    
    private JobDelete jd;
    private JobUpdate ju;
    
    private AutoCloseJOptionPane ac;
    
    public JobInsert() {
        dba = new DBAccess();
        jd = new JobDelete();
        ju = new JobUpdate();
        
        ac = new AutoCloseJOptionPane();
    }
    
    public boolean insertJobDetails(Job j) {
        String query = "INSERT INTO job(jobID,itemID3,codeNo,qty,waste,totalimpression,addeddate) "
                + "VALUES('"+j.getJobid()+"','"+j.getItemid()+"','"+j.getCodeno()+"',"+j.getQty()+","
                + "'"+j.getWaste()+"',"+j.getTotalimpression()+",'"+j.getDate()+"');";
        boolean val = dba.operationQuery(query);
        return val;
    }
    
    public boolean insertJobCoverDetail(JobCover jc) {
        String query = "INSERT INTO jobcover(jobID3,width,height,unite,printUP) "
                + "VALUES('"+jc.getJobid()+"',"+jc.getWidth()+","+jc.getHeight()+",'\\"+jc.getUnite()+"',"+jc.getPrintup()+");";
        boolean val = dba.operationQuery(query);
        System.out.println("Cover job added to "+jc.getJobid());
        return val;
    }
    
    public boolean insertJobInsideDetail(JobInside ji) {
        String query = "INSERT INTO jobinside(jobID4,width,height,unite,platePerBook,printUP) "
                + "VALUES('"+ji.getJobid()+"',"+ji.getWidth()+","+ji.getHeight()+",'\\"+ji.getUnit()+"',"+ji.getPlateperbook()+","+ji.getPrintup()+");";
        boolean val = dba.operationQuery(query);
        System.out.println("Inside job added to "+ji.getJobid());
        return val;
    }
    
    public boolean insertJobStepDetail(JobStatus js) {
        String jobstatusquary;
        boolean status = false;
        jobstatusquary = "INSERT INTO jobstatus(jobID2,statusName) "
                    + "VALUES('" + js.getJobid() + "','" + js.getStatusName() + "');";
        try {
            if (!js.getStartDate().equals("")) {
                jobstatusquary = "INSERT INTO jobstatus(jobID2,statusName,startDate) "
                        + "VALUES('" + js.getJobid() + "','" + js.getStatusName() + "','" + js.getStartDate() + "');";
                boolean firstjob = ju.updateJobStatus(js.getJobid(), js.getStatusName());
                if (firstjob == true) {
                    System.out.println(js.getStatusName()+" is the first job of "+js.getJobid());
                } else {
                    return false;
                }
            } 
        } catch (NullPointerException npe) {
            
        }
        status = dba.operationQuery(jobstatusquary);
        return status;
    }
    
    public boolean insertAllJobStepDetail(JobStatus[] js) {
        boolean status = false;
        try {
            for (int i = 0 ; i < js.length ; i++) {
                status = insertJobStepDetail(js[i]);
                //This if use to if one step adding fail return false 
                if (status == false) {
                    return false;
                }
            }
        } catch (NullPointerException npe) {
            
        }
        return status;
    }
    
    public void insertAllJobDetail(Job j) {
        boolean coverstatus = false;
        boolean insidestatus = false;
        boolean stepstatus = false;
        boolean jobstatus = insertJobDetails(j);
        if (jobstatus) {
            if (j.getJc() != null) {
                coverstatus = insertJobCoverDetail(j.getJc());
            }
            if (j.getJi() != null) {
                insidestatus = insertJobInsideDetail(j.getJi());
            }
            stepstatus = insertAllJobStepDetail(j.getStep());
        }
        if (jobstatus == true && ( coverstatus == false || insidestatus == false ) && stepstatus == false) {
            int reply = JOptionPane.showConfirmDialog(null, "Adding job incomplete.\nDo you want to remove inserted information?", "Remove incomplete insert", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (reply == JOptionPane.OK_OPTION) {
                jd.removeJob(j);
            } else if (reply == JOptionPane.CANCEL_OPTION) {
                ac.showThisMessage("Unwanted data in database", "Incomplete job not removed.\nYou can update or delete it later.", JOptionPane.WARNING_MESSAGE);
            }
        } else if (jobstatus == true && ( coverstatus == true || insidestatus == true ) && stepstatus == true) {
            ac.showThisMessage("Successfull", j.getJobid()+" job adding successfully.", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public boolean restoreJobStepDetail(JobStatus js) {
        String jobstatusquary;
        boolean status = false;
        jobstatusquary = "INSERT INTO jobstatus(jobStatusNo,jobID2,statusName,startDate,endDate,machine,qty) "
                + "VALUES("+js.getStatusId()+",'"+js.getJobid()+"','"+js.getStatusName()+"','"+js.getStartDate()+"',"
                + "'"+js.getEndDate()+"','"+js.getMachine()+"',"+js.getSqty()+");";
        status = dba.operationQuery(jobstatusquary);
        return status;
    }
    
    public boolean restoreAllJobStepDetail(JobStatus[] js) {
        boolean status = false;
        try {
            for (int i = 0 ; i < js.length ; i++) {
                status = restoreJobStepDetail(js[i]);
                if (status == false) {
                    return false;
                }
            }
        } catch (NullPointerException npe) {
            
        }
        return status;
    }
    
}
