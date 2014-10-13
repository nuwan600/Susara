/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Class.Job.Update;

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

public class JobUpdate {
    
    private DBAccess dba;
    
    public JobUpdate() {
        dba = new DBAccess();
    }
    
    public boolean updateJob(Job j) {
        String query = "UPDATE job "
                + "SET itemID3 = '"+j.getItemid()+"', codeNo = '"+j.getCodeno()+"', qty = "+j.getQty()+","
                + "waste = '"+j.getWaste()+"', totalimpression = "+j.getTotalimpression()+", status ='"+j.getStatus()+"', addeddate = '"+j.getDate()+"'"
                + "WHERE jobID ='"+j.getJobid()+"' ;";
        boolean status = dba.operationQuery(query);
        return status;
    }
    
    public boolean updateCover(JobCover jc) {
        String query = "UPDATE jobcover SET width="+jc.getWidth()+",height="+jc.getHeight()+",unite='\\"+jc.getUnite()+"',"
                + "printUP ="+jc.getPrintup()+" WHERE jobID3='"+jc.getJobid()+"';";
        boolean status = dba.operationQuery(query);
        return status;
    }
    
    public boolean updateInside(JobInside ji) {
        String query = "UPDATE jobinside SET width="+ji.getWidth()+", height="+ji.getHeight()+","
                + " unite='\\"+ji.getUnit()+"', platePerBook="+ji.getPlateperbook()+", printUP="+ji.getPrintup()+" "
                + "WHERE jobID4 = '"+ji.getJobid()+"';";
        boolean status = dba.operationQuery(query);
        return status;
    }
    
    public boolean updateStatus(JobStatus js) {
        String query = "UPDATE jobstatus SET endDate='',machine=,qty= "
                + "WHERE jobStatusNo="+js.getStatusId()+" ;";
        return false;
    }
    
    public boolean updateJobStatus(String jobid, String jobstatus) {
        String query = "UPDATE job SET status='"+jobstatus+"' WHERE jobID='"+jobid+"';";
        boolean status = dba.operationQuery(query);
        return status;
    }
    
}
