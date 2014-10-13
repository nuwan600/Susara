/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Class.Job.Job;

/**
 *
 * @author J.M Vikum Chathuranga 
 *         IT13131098
 *
 */

import Class.Job.Status.JobStatus;
import Class.Job.Cover.JobCover;
import Class.Job.Inside.JobInside;
import Class.Job.Select.JobSelect;

import java.util.Vector;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

public class Job {

    //Information adout the job
    private String jobid;
    private String codeno;
    private String itemid;

    private String date;
    private int qty;
    private String waste;
    private String status;

    //cover
    private boolean covervalide = false;
    private JobCover jc;

    //inside
    private boolean insidevalide = false;
    private JobInside ji;

    //global calculation
    private int totalimpression;

    //job steps
    private boolean beforCutting;
    private boolean print;
    private String laminating;
    private boolean dieCutting;
    private boolean afterCutting;
    private boolean pasting;
    private String binding;
    private boolean stitcher;
    private boolean folding;
    private boolean gathering;
    
    private int stepcount = 0;
    
    //This is for store information about job step
    private JobStatus step[] = new JobStatus[10];

    //Vector for get details
    private Vector<Vector<String>> vector;
    
    //Select query in this class
    private JobSelect js;

    //To make new Job object
    public Job(String jobid, String codeno, String itemid, String date, int qty, String waste, int totalimpression, JobCover jc, JobInside ji, ArrayList al) {
        this.jobid = jobid;
        this.codeno = codeno;
        this.itemid = itemid;
        this.date = date;
        this.qty = qty;
        this.waste = waste;
        this.totalimpression = totalimpression;
        this.jc = jc;
        this.ji = ji;
        setJobSteps(al);
        setJobStatusArray(al);
    }
    
    //Make job object from exicting job
    public Job(String jobid) {
        js = new JobSelect();
        covervalide = false;
        insidevalide = false;
        boolean st = false;
        try {
            vector = js.getJob(jobid);
            for (Vector<String> v : vector) {
                setJobid(v.elementAt(0));
                setItemid(v.elementAt(1));
                setCodeno(v.elementAt(2));
                setQty(Integer.parseInt(v.elementAt(3)));
                setWaste(v.elementAt(4));
                setTotalimpression(Integer.parseInt(v.elementAt(5)));
                setStatus(v.elementAt(6));
                setDate(v.elementAt(7));
            }
            st = true;
        } catch (NullPointerException npe) {
            JOptionPane.showMessageDialog(null, "Searched job isn't valide.");
            st = false;
        }
        if (st == true) {
            try {
                jc = null;
                vector = js.getCoverDetail(jobid);
                for (Vector<String> v : vector) {
                    jc = new JobCover(v);
                    System.out.println("There is cover job for : " + jobid);
                    covervalide = true;
                }
                if (jc == null) {
                    System.out.println("No cover job for : " + jobid);
                    covervalide = false;
                }
            } catch (NullPointerException npe) {
                System.out.println("No cover job for : "+jobid);
            }
            try {
                ji = null;
                vector = js.getInsideDetail(jobid);
                for (Vector<String> v : vector) {
                    ji = new JobInside(v);
                    insidevalide = true;
                    System.out.println("There is inside job for : " + jobid);
                }
                if (ji == null) {
                    System.out.println("No inside job for : " + jobid);
                }
            } catch (NullPointerException npe) {
                System.out.println("No inside job for : "+jobid);
            }
            System.out.println(covervalide);
            System.out.println(insidevalide);
            try {
                vector = js.getJobStatus(jobid);
                int count = 0;
                for (Vector<String> v : vector) {
                    String statusName = v.elementAt(2);
                    setJobSteps(statusName);
                    step[count] = new JobStatus(v);
                    count++;
                }
                stepcount = count;
            } catch (NullPointerException npe) {
                System.out.println("Error in job adding. "+jobid);
            }
        }
    }

    //Set boolean variable
    private void setJobSteps(String in) {

        if (in.equals("Before Cutting")) {
            setBeforCutting(true);
        } else if (in.equals("Printing")) {
            setPrint(true);
        } else if (in.equals("Die Cutting")) {
            setDieCutting(true);
        } else if (in.equals("After Cutting")) {
            setAfterCutting(true);
        } else if (in.equals("Pasting")) {
            setPasting(true);
        } else if (in.equals("Sticher")) {
            setStitcher(true);
        } else if (in.equals("Folding")) {
            setFolding(true);
        } else if (in.equals("Gathering")) {
            setGathering(true);
        } else if (in.startsWith("Laminating") || in.startsWith("Varnishing")) {
            setLaminating(in);
        } else if (in.startsWith(itemid)) {
            
        }

    }
    
    //Set job step boolean variable when adding new job
    private void setJobSteps(ArrayList al) {
        Iterator i = al.iterator();
        while (i.hasNext()) {
            setJobSteps(i.next().toString());
        }
    }
    
    //Set job step array when filling job for inser
    private void setJobStatusArray(ArrayList al) {
        setStep(new JobStatus[10]);
        Iterator i = al.iterator();
        int count = 0;
        while (i.hasNext()) {
            JobStatus js = new JobStatus();
            js.setJobid(getJobid());
            js.setStatusName(i.next().toString());
            if (count == 0) {
                js.setStartDate(getDate());
            }
            getStep()[count] = js;
            count++;
        }
        setStepcount(count);
    }

    //Return job detail as vector
    public Vector getJobDetail() {
        Vector v = new Vector();
        v.add(getJobid());
        v.add(getCodeno());
        v.add(getItemid());
        v.add(getQty());
        v.add(getDate());
        v.add(getWaste());
        v.add(getStatus());
        return v;
    }

    /*
     *  Start of getters and setters
     */

    public JobCover getJc() {
        return jc;
    }

    public void setJc(JobCover jc) {
        this.jc = jc;
    }

    public JobInside getJi() {
        return ji;
    }

    public void setJi(JobInside ji) {
        this.ji = ji;
    }
    
    public String getJobid() {
        return jobid;
    }

    public void setJobid(String jobid) {
        this.jobid = jobid;
    }

    public String getCodeno() {
        return codeno;
    }

    public void setCodeno(String codeno) {
        this.codeno = codeno;
    }

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getWaste() {
        return waste;
    }

    public void setWaste(String waste) {
        this.waste = waste;
    }

    public int getTotalimpression() {
        return totalimpression;
    }

    public void setTotalimpression(int totalimpression) {
        this.totalimpression = totalimpression;
    }

    public boolean isBeforCutting() {
        return beforCutting;
    }

    public void setBeforCutting(boolean beforCutting) {
        this.beforCutting = beforCutting;
    }

    public boolean isPrint() {
        return print;
    }

    public void setPrint(boolean print) {
        this.print = print;
    }

    public String getLaminating() {
        return laminating;
    }

    public void setLaminating(String laminating) {
        this.laminating = laminating;
    }

    public boolean isDieCutting() {
        return dieCutting;
    }

    public void setDieCutting(boolean dieCutting) {
        this.dieCutting = dieCutting;
    }

    public boolean isAfterCutting() {
        return afterCutting;
    }

    public void setAfterCutting(boolean afterCutting) {
        this.afterCutting = afterCutting;
    }

    public boolean isPasting() {
        return pasting;
    }

    public void setPasting(boolean pasting) {
        this.pasting = pasting;
    }

    public String getBinding() {
        return binding;
    }

    public void setBinding(String binding) {
        this.binding = binding;
    }

    public boolean isStitcher() {
        return stitcher;
    }

    public void setStitcher(boolean stitcher) {
        this.stitcher = stitcher;
    }

    public boolean isFolding() {
        return folding;
    }

    public void setFolding(boolean folding) {
        this.folding = folding;
    }

    public boolean isGathering() {
        return gathering;
    }

    public void setGathering(boolean gathering) {
        this.gathering = gathering;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isCovervalide() {
        return covervalide;
    }

    public void setCovervalide(boolean covervalide) {
        this.covervalide = covervalide;
    }

    public boolean isInsidevalide() {
        return insidevalide;
    }

    public void setInsidevalide(boolean insidevalide) {
        this.insidevalide = insidevalide;
    }

    public JobStatus[] getStep() {
        return step;
    }

    public void setStep(JobStatus[] step) {
        this.step = step;
    }
    
    public int getStepcount() {
        return stepcount;
    }

    public void setStepcount(int stepcount) {
        this.stepcount = stepcount;
    }

    /*
     * End of getters and setters
     */
    
    public void get() {
        for (int i = 0 ; i <10 ; i++) {
            try {
                System.out.println(getStep()[i].getJobStatus().toString());
            } catch (NullPointerException npe) {
                
            }
        }
    }
    
}
