/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Class.Job.Delete;

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

import Class.Job.Insert.JobInsert;
import Class.Job.Update.JobUpdate;

import Class.AutoCloseJOptionPane.AutoCloseJOptionPane;

import java.util.ArrayList;
import javax.swing.JOptionPane;

public class JobDelete {
    
    private DBAccess dba;
    
    private AutoCloseJOptionPane ac;
    
    public JobDelete() {
        dba = new DBAccess();
        ac = new AutoCloseJOptionPane();
    }
    
    private boolean deleteJob(Job j) {
        String deletejobQuary = "DELETE FROM job WHERE jobID='" + j.getJobid() + "';";
        boolean status = dba.operationQuery(deletejobQuary);
        if (status == true) {
            System.out.println("Job " + j.getJobid() + " successfully remove.");
        } else {
            System.out.println("Job " + j.getJobid() + " remove unsuccessful.");
        }
        return status;
    }
    
    //This is 
    public boolean deleteJobCover(JobCover jc) {
        String query = "DELETE FROM jobcover WHERE jobID3='"+ jc.getJobid() +"';";
        boolean status = dba.operationQuery(query);
        if (status == true) {
            System.out.println("Cover of job " + jc.getJobid() + " successfully remove.");
        } else {
            System.out.println("Cover of job " + jc.getJobid() + " remove unsuccessful.");
        }
        return status;
    }
    
    //This is to delete job inside detail from job
    public boolean deleteJobInside(JobInside ji) {
        String query = "DELETE FROM jobinside WHERE jobID4='"+ ji.getJobid() +"';";
        boolean status = dba.operationQuery(query);
        if (status == true) {
            System.out.println("Inside of job " + ji.getJobid() + " successfully remove.");
        } else {
            System.out.println("Inside of job " + ji.getJobid() + " remove unsuccessful.");
        }
        return status;
    }
    
    //This is to delete specific job step
    public boolean deleteJobStep(JobStatus js) {
        String query = "DELETE FROM jobstatus WHERE jobStatusNo="+js.getStatusId()+";";
        boolean status = false;
        System.out.println(js.getJobStatus().toString());
        if (js.getEndDate() == null ) {
            status = dba.operationQuery(query);
            if (status == true) {
                System.out.println(js.getStatusName() + " step removed from " + js.getJobid());
            } else {
                System.out.println(js.getStatusName() + " step couldn't remove from " + js.getJobid());
            }
        } else {
            int reply = JOptionPane.showConfirmDialog(null, "Job is done up to ?.\nDo want to stop job ?", "Job can't remove", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (reply == JOptionPane.YES_OPTION) {
                JobUpdate ju = new JobUpdate();
                boolean rep = ju.updateJobStatus(js.getJobid(), "Stoped");
                if (rep == true) {
                    ac.showThisMessage("Job canseled.", "Job canseled", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
        return status;
    }
    
    //This is to delete all job steps.
    public boolean deleteJobSteps(JobStatus[] js) {
        boolean status = false;
        //This try catch for skip empty indexes
        try {
            for (int i =0 ; i < js.length ; i++) {
                status = deleteJobStep(js[i]);
                if (status == false) {
                    return status;
                }
            }
        } catch (NullPointerException npe) {
            
        }
        return status;
    }
    
    //This is for remove step from process permenetly
    public void removeJob(Job j) {
        boolean coverstatus = false;
        boolean insidestatus = false;
        boolean jobstatus = false;
        boolean stepStatus = deleteJobSteps(j.getStep());
        if (stepStatus == true) {
            if (j.getJc() != null) {
                coverstatus = deleteJobCover(j.getJc());
                System.out.println("Cover "+coverstatus);
            }
            if (j.getJi() != null) {
                insidestatus = deleteJobInside(j.getJi());
                System.out.println("Inside "+insidestatus);
            }
            if ((coverstatus == true) || (insidestatus == true)) {
                jobstatus = deleteJob(j);
                System.out.println("Job "+jobstatus);
            }
        }
        if (jobstatus == false && stepStatus == true) {
            int reply = JOptionPane.showConfirmDialog(null, j.getJobid()+" job removing failed.\nDo"
                    + " you want to restore deleted information.", "Are you sure ?", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null);
            if (reply == JOptionPane.YES_OPTION) {
                JobInsert js = new JobInsert();
                if (coverstatus == true) {
                     js.insertJobCoverDetail(j.getJc());
                }
                if (insidestatus == true) {
                    js.insertJobInsideDetail(j.getJi());
                }
                if (stepStatus == true) {
                    js.insertAllJobStepDetail(j.getStep());
                }
            }
        } else if (jobstatus == true && stepStatus == true && (coverstatus == true || insidestatus == true)) {
            ac.showThisMessage("Successfull", j.getJobid()+" removing successfill.", JOptionPane.INFORMATION_MESSAGE);
        } 
    }
    
//    public static void main(String[] args) {
//        Job j = new Job("j001");
//        JobDelete jd = new JobDelete();
//        jd.removeJob(j);
//        
//    }
    
}
