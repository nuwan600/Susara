/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Class.Job.Select;

/**
 *
 * @author J.M Vikum Chathuranga 
 *         IT13131098
 *
 */

import Class.Job.DBAccess.DBAccess;
import java.util.ArrayList;

import java.util.Vector;

public class JobSelect {
    
    private DBAccess dba ;
    private Vector<Vector<String>> vector;
    
    public JobSelect() {
        dba = new DBAccess();
    }
    
    //start - Get job detail according to job id
    
    public Vector getJob(String jobid) {
        String quary = "SELECT * FROM job WHERE jobID='" + jobid + "';";
        vector = dba.selectQuery(quary);
        return vector;
    }
    
    public Vector getCoverDetail(String jobid) {
        String quary = "SELECT * FROM jobcover WHERE jobID3='"+jobid+"';";
        vector = dba.selectQuery(quary);
        return vector;
    }
    
    public Vector getInsideDetail(String jobid) {
        String quary = "SELECT * FROM jobinside WHERE jobID4='"+jobid+"';";
        vector = dba.selectQuery(quary);
        return vector; 
    }
    
    public Vector getJobStatus(String jobid) {
        String quary = "SELECT * FROM jobstatus WHERE jobID2='"+jobid+"';";
        vector = dba.selectQuery(quary);
        return vector;
    }
    
    //end
    
    //start - Get all job informaion
    public Vector getJobs() {
        String quary = "SELECT * FROM job;";
        vector = dba.selectQuery(quary);
        return vector;
    }
    
    //Get information about all cover details
    public Vector getCoverDetails() {
        String query = "SELECT * FROM jobcover;";
        vector = dba.selectQuery(query);
        return vector;
    }
    
    //Get information about all inside details
    public Vector getInsideDetails() {
        String query = "SELECT * FROM jobinside;";
        vector = dba.selectQuery(query);
        return vector;
    }
    
    //Get information about all job status
    public Vector getJobStatus() {
        String query = "SELECT * FROM jobstatus;";
        vector = dba.selectQuery(query);
        return vector;
    }
    
    //Get information about book
    public Vector getItemDetail(String itemid) {
        String query = "SELECT * FROM items WHERE itemid = '"+itemid+"';";
        vector = dba.selectQuery(query);
        return vector;
    }
    
    //Get material size
    public String getMaterialSize(String itemid) {
        String query = "SELECT size FROM inside i,rawmaterial r WHERE i.mID2 = r.itemCode AND i.itemID6 = '"+itemid+"';";
        vector = dba.selectQuery(query);
        String size = vector.elementAt(0).elementAt(0);
        return size;
    }
    
    /*
    *   Start Searching main details about job
    */
    
    //This for create query according to searching prameters
    private String tableQuary(String feild, String searchkey, String start, String end) {
        StringBuilder quaryfirst = new StringBuilder("SELECT j.jobID,j.itemID3,i.book_name,j.status,j.addeddate,j.qty,j.totalimpression,j.waste "
                + "FROM job j,items i "
                + "WHERE j.itemID3=i.itemID ");
        String quarylast = "ORDER BY j.jobID; ";
        if (!searchkey.equals("") && start.equals("") && end.equals("")) {
            quaryfirst.append("AND j."+feild+" LIKE '%"+searchkey+"%' ");
        } else if (!searchkey.equals("") && !start.equals("") && !end.equals("")) {
            quaryfirst.append("AND j."+feild+" LIKE '%"+searchkey+"%' AND STR_TO_DATE(j.addeddate, '%Y-%m-%d') BETWEEN '"+start+"' AND '"+end+"' ");
        } else if (searchkey.equals("") && !start.equals("") && !end.equals("")) {
            quaryfirst.append("AND STR_TO_DATE(j.addeddate, '%Y-%m-%d') BETWEEN '"+start+"' AND '"+end+"' ");
        }
        quaryfirst.append(quarylast);
        return quaryfirst.toString();
    }
    
    //Execute query to get searched information
    public Vector searchJob(String feild, String searchkey, String start, String end) {
        String query = tableQuary(feild, searchkey, start, end);
        vector = dba.selectQuery(query);
        return vector;
    }
       
    /*
    *   End of search code
    */
    
    //This for get list of itemid's
    public ArrayList getItemList(String input) {
        String query = "SELECT itemID FROM items WHERE itemID LIKE '"+input+"%';";
        vector = dba.selectQuery(query);
        ArrayList al = new ArrayList();
        if (vector != null) {
            for (Vector<String> v : vector) {
                al.add(v.elementAt(0));
            }
            return al;
        } else {
            return null;
        }
    }
    
    //
    public String getPreviousJobId() {
        String query = "SELECT jobID FROM job ORDER BY jobID DESC LIMIT 1;";
        vector = dba.selectQuery(query);
        if (vector != null) {
            return vector.elementAt(0).elementAt(0).toString();
        }
        return null;
    }
    
}
