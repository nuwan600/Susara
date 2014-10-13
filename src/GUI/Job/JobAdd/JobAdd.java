/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Job.JobAdd;

/**
 *
 * @author J.M Vikum Chathuranga 
 *         IT13131098
 *
 */


import Class.DBaccess.DBaccess;
import Class.PanelLoader.PanelLoader;

import GUI.Jobview.Jobview;
import GUI.Job.StatusPanel.StatusPanel;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;

import Class.Job.Job.Job;
import Class.Job.Cover.JobCover;
import Class.Job.Delete.JobDelete;
import Class.Job.Inside.JobInside;

import Class.Job.Select.JobSelect;
import Class.Job.Insert.JobInsert;
import java.awt.event.KeyAdapter;

import java.util.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

import java.awt.event.KeyEvent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.JOptionPane;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

public class JobAdd extends javax.swing.JPanel {

    /**
     * Creates new form JobAdd
     */
    DBaccess dba;

    Date d;

    Connection con;
    ResultSet rs;
    
    StatusPanel sp ;
    PanelLoader pp;
    private Job j;
    Jobview jv;
    
    private JobSelect js;
    private JobInsert ji;
    private JobDelete jd;
    
    //material information
    String mid;
    String size;
    double width;
    double height;
    double cutwidth;
    double cutheight;

    //job class variable
    private int cnopadc = -1;
    private int inopadc = -1;

    public JobAdd() {

        initComponents();

        laminatingGroup();

        dba = new DBaccess();
        
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        jxdpjobdate.setFormats(df);
        
        d = new Date();
        jxdpjobdate.setDate(d);
        
        /*
        ** OOP
        */
        
        ji = new JobInsert();
        jd = new JobDelete();
        js = new JobSelect();
        
        txtjobid.setText(getAutoGenaratedJobId());
        
        /*
        **End OOP
        */
        
        //test
        sp = new StatusPanel();
        pp = new PanelLoader();
        jv = new Jobview();
        //pp.swichPanel(jPanel11, sp);
        sp.setVisible(true);
        
        cbitemid.getEditor().getEditorComponent().addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cbitemidKeyReleased(evt);
            }
        });
        
    }
    
    public void setJob(Job j) {
        this.j = j;
    }

    public void clearAll() {
        txtjobid.setText("");
        txtcodeno.setText("");
        txtcovercuttingheight.setText("");
        txtcovercuttingwidth.setText("");
        txtcouerup.setText("");
        txtflack.setText("");
        txtvalue.setText("");
        txtinsidecuttingheight.setText("");
        txtinsidecuttingwidth.setText("");
        txtinsideup.setText("");
        txtplateperbook.setText("");
        txtqty.setText("");
        cbitemid.setSelectedItem("i001");
    }
    
    public void resizeController(boolean status) {
        
        if( status == true ) {
            
            jpShowHide.setVisible(true);

//            pp.swichPanel(jpShowHide, sp);
//            sp.setVisible(true);
            
            pp.swichPanel(jpShowHide, jv);
            jv.setVisible(true);
            
            jv.fillStatus(txtjobid.getText());
            
        } else if( status == false) {
            
            jpShowHide.setVisible(false);
            
        }
        
    }
    
    public int calcPaper(String table, String itemid, String cutheight, String cutwidth) {
        
        StringTokenizer st;
        
        try {
            
            this.cutheight = Double.parseDouble(cutheight);
            this.cutwidth = Double.parseDouble(cutwidth);
            
            String getMSize = null;
            
            if (table.equals("cover")) {
                getMSize = "SELECT r.size FROM cover i,rawmaterial r WHERE i.mID3 = r.itemCode and i.itemID7='"+itemid+"';";
            } if (table.equals("inside")) {
                getMSize = "SELECT r.size FROM inside i,rawmaterial r WHERE i.mID2 = r.itemCode and i.itemID6='"+itemid+"';";
            }
            
            rs = dba.Dbselect(getMSize);
            
            if(rs.next()) {
                
                st = new StringTokenizer(rs.getString(1),"x");
                
                width = Double.parseDouble(st.nextToken());
                height = Double.parseDouble(st.nextToken());
                
                int i = (int)((width/this.cutwidth)*(height/this.cutheight));
                
                return i;
                
            }
            
        } catch (NullPointerException e) {
            
            JOptionPane.showMessageDialog(null, "Job can't proceed still in pre-press stage or invalide item.");
            
        } catch (SQLException ex) {
            
            JOptionPane.showMessageDialog(null, "Error calculating no pecies.");
            
        } catch (NumberFormatException e) {
            
            JOptionPane.showMessageDialog(null, "Invalid data in rewmaterial.");
            
        } 
        
        return 0;
        
    }
    
    public void setNoPecies() {
        
        try {
        
            String itemid = cbitemid.getSelectedItem().toString();

            
            if (!txtcovercuttingwidth.getText().equals("") && !txtcovercuttingheight.getText().equals("")) {

                cnopadc = calcPaper("cover", itemid, txtcovercuttingheight.getText(), txtcovercuttingwidth.getText());

                System.out.println("Number of cover p : " + cnopadc);
            }

            if (!txtinsidecuttingwidth.getText().equals("") && !txtinsidecuttingheight.getText().equals("")) {

                inopadc = calcPaper("inside", itemid, txtinsidecuttingheight.getText(), txtinsidecuttingwidth.getText());

                System.out.println("Number of inside p : " + inopadc);
            }
            
        } catch (NullPointerException npe) {
            
        }
        
    }

    private void laminatingGroup() {
        int selected = cbltype.getSelectedIndex();

        if (selected == 0) {
            cblside.setEnabled(false);
            cblmethod.setEnabled(false);
        } else {
            cblside.setEnabled(true);
            cblmethod.setEnabled(true);
        }
    }
    
    public void deleteJob(String jobid) {

        String deletejobQuary = "DELETE FROM job WHERE jobID ='"+ jobid+"';";

        boolean status = dba.Dbexecute(deletejobQuary);

        if (status == true) {

            System.out.println("Job "+jobid+" successfully remove.");

        } else {
            
            System.out.println("Job "+jobid+" remove unsuccessful.");
            
        }

    }
    
    public boolean updateJob(String jobid) {
        
        String quary = "UPDATE job SET itemID3='"+cbitemid.getSelectedItem().toString()+"',codeNo='"+txtcodeno.getText()+"',addeddate='"+jxdpjobdate.getEditor().getText()+"',"
                + "qty="+txtqty.getText()+",waste='"+txtflack.getText()+"/"+ txtvalue.getText()+"',totalimpression="+txttotalimpression.getText()+" "
                + "WHERE jobID='"+jobid+"'";
        
        boolean status = dba.Dbexecute(quary);
        
        if (status == true) {
            JOptionPane.showMessageDialog(null, jobid+" is successfully updated.");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, jobid+" is unsuccessfully updated.");
            return false;
        }
        
    }
    
    public void deletePartInformation(String table, String jobid) {
        
        if (table.equals("jobcover") ) {
            
            String deleteQuary = "DELETE FROM "+table+" WHERE jobID3 = '"+jobid+"';";
            
            boolean status = dba.Dbexecute(deleteQuary);
            
            if (status == true) {
                
                System.out.println("Cover info of cover deleted. Where "+jobid);
                
            }
            
        } else if (table.equals("jobinside")) {
            
            String deleteQuary = "DELETE FROM "+table+" WHERE jobID4 = '"+jobid+"';";
            
            boolean status = dba.Dbexecute(deleteQuary);
            
            if (status == true) {
                
                System.out.println("Inside info of cover deleted. Where "+jobid);
                
            }
            
        } 
        
    }
    
    public void updatePartInformation(String table, String jobid) {
        
        if (table.equals("Cover")) {
            
            String quary = "UPDATE jobcover "
                    + "SET width="+txtcovercuttingwidth.getText()+",height="+txtcovercuttingheight.getText()+",unite='\\"+cbcovera.getSelectedItem().toString()+"',printUP="+txtcouerup.getText()+" "
                    + "WHERE jobID3='"+jobid+"';";
            boolean status = dba.Dbexecute(quary);
            if (status == true) {
                System.out.println("Cover information of job "+jobid+" is been updated.");
            } else {
                System.out.println("Cover information of job "+jobid+" unable to update.");
            }
            
        } else if (table.equals("Inside")) {
            
            String quary = "UPDATE jobinside "
                    + "SET width="+txtinsidecuttingwidth.getText()+" ,height="+txtinsidecuttingheight.getText()+",unite='\\"+cbinsidea.getSelectedItem().toString()+"',platePerBook="+txtplateperbook.getText()+",printUP="+txtcouerup.getText()+" "
                    + "WHERE jobID4='"+jobid+"';";
            boolean status = dba.Dbexecute(quary);
            if(status == true) {
                System.out.println("Inside information of job "+jobid+" is been updated.");
            } else {
                System.out.println("Inside information of job "+jobid+" unable to update.");
            }
            
        }
        
    }
    
    public void addJobStatues(String jobid, String stat) {
        
        String quary = "UPDATE job SET status='"+stat+"' WHERE jobID ='"+jobid+"';";
        
        boolean status = dba.Dbexecute(quary);
        
        if(status == true) {
            
            System.out.println("Frist job is "+stat+".");
            
        } else {
            
            System.out.println("Error in adding frist job.");
            
        }
        
    }
    
    public void deleteAllJobStatus(String jobid) {
        
        String quary = "DELETE FROM jobstatus WHERE jobID2='"+jobid+"';";
        boolean status = dba.Dbexecute(quary);
        
        if (status == true) {
            System.out.println(jobid+" process removed from job.");
        } else {
            System.out.println(jobid+" process couldn't remove from job.");
        }
        
    }
    
    public void deleteJobStatuse(String jobid, String stat) {
        
        String quary = "DELETE FROM jobstatus WHERE jobID2='"+jobid+"' AND statusName='"+stat+"';";
        boolean status = dba.Dbexecute(quary);
        
        if (status == true) {
            System.out.println(jobid+" - "+stat+" process removed from job.");
        } else {
            System.out.println(jobid+" - "+stat+" process couldn't remove from job.");
        }
        
    }
    
    public void updateJobStatuse (String jobid, String stat) {
        
        String quary = "UPDATE jobstatus "
                + "SET jobID2='"+txtjobid.getText()+"' "
                + "WHERE jobID2='' AND status='';"; 
        boolean status = dba.Dbexecute(quary);
        
        if (status == true) {
            
        }
        
    } 
        
    /*
    ** Begin of OOP coding
    */
    
    public void fillForUpdate(Job j) {
        
        txtjobid.setText(j.getJobid());
        txtcodeno.setText(j.getCodeno());
        jxdpjobdate.getEditor().setText(j.getDate());
        cbitemid.setSelectedItem(j.getItemid());
        txtqty.setText(Integer.toString(j.getQty()));
        txttotalimpression.setText(Integer.toString(j.getTotalimpression()));
        
        StringTokenizer st = new StringTokenizer(j.getWaste(),"/");
        txtflack.setText(st.nextToken());
        txtvalue.setText(st.nextToken());
        
        if (j.isCovervalide() == true) {
            txtcovercuttingwidth.setText(Integer.toString(j.getJc().getWidth()));
            txtcovercuttingheight.setText(Integer.toString(j.getJc().getHeight()));
            cbcovera.setSelectedItem(j.getJc().getUnite());
            txtcouerup.setText(Integer.toString(j.getJc().getPrintup()));
        } else {
            txtcovercuttingwidth.setText("");
            txtcovercuttingheight.setText("");
            cbcovera.setSelectedItem("'");
            txtcouerup.setText("");
        }
        
        if (j.isInsidevalide() == true) {
            txtinsidecuttingwidth.setText(Integer.toString(j.getJi().getWidth()));
            txtinsidecuttingheight.setText(Integer.toString(j.getJi().getHeight()));
            cbinsidea.setSelectedItem(j.getJi().getUnit());
            txtinsideup.setText(Integer.toString(j.getJi().getPrintup()));
            txtplateperbook.setText(Integer.toString(j.getJi().getPlateperbook()));
        } else {
            txtinsidecuttingwidth.setText("");
            txtinsidecuttingheight.setText("");
            cbinsidea.setSelectedItem("'");
            txtinsideup.setText("");
            txtplateperbook.setText("");
        }
        
        if(j.isAfterCutting()) {
            cbaftercuttting.setSelected(true);
        } else {
            cbaftercuttting.setSelected(false);
        }
        
        if(j.isDieCutting()) {
            cbdiecutting.setSelected(true);
        } else {
            cbdiecutting.setSelected(false);
        }
        
        if(j.isPasting()) {
            cbpasting.setSelected(true);
        } else {
            cbpasting.setSelected(false);
        }
        
        if(j.isFolding()) {
            cbfolding.setSelected(true);
        } else {
            cbfolding.setSelected(false);
        }
        
        if(j.isGathering()) {
            cbgathering.setSelected(true);
        } else {
            cbgathering.setSelected(false);
        }
        
        if(j.isStitcher()) {
            cbsticher.setSelected(true);
        } else {
            cbsticher.setSelected(false);
        }
        
    }
    
    public void updateController() {
        
        if(checkJobDetailChanged()) {
            updateJob(j.getJobid());
        }
    }
    
    private boolean checkJobDetailChanged() {
        
        if (!j.getCodeno().equals(txtcodeno.getText())) {
            return true;
        } else if(!j.getJobid().equals(txtjobid.getText())) {
            return true;
        } else if(!j.getItemid().equals(cbitemid.getSelectedItem().toString())) {
            return true;
        } else if(j.getQty() != Integer.parseInt(txtqty.getText())) {
            return true;
        } else if(!j.getDate().equals(jxdpjobdate.getEditor().getText())) {
            return true;
        } else if(!j.getWaste().equals(txtflack.getText()+"/"+ txtvalue.getText())) {
            return true;
        } else if(j.getTotalimpression() != Integer.parseInt(txttotalimpression.getText())) {
            return true;
        }
        return false;
    }
    
//    private boolean checkJobCoverDetailChanged() {
//        if (j.getJi().getWidth() != ) {
//            
//        }
//    }
    
    public void insertNewJob() {
        String waste = txtflack.getText()+"/"+txtvalue.getText();
        //Untill finish totalimpression
        int tot;
        try {
            tot = Integer.parseInt(txttotalimpression.getText());
        } catch (NumberFormatException nfe) {
            tot = 0;
        }
        Job j = new Job(txtjobid.getText(), txtcodeno.getText(), cbitemid.getSelectedItem().toString(), jxdpjobdate.getEditor().getText(),Integer.parseInt(txtqty.getText()), waste, tot, getJobCover(), getJobInside(), getStepArray());
        ji.insertAllJobDetail(j);
    }
    
    private JobCover getJobCover() {
        try {
            JobCover jc = new JobCover(txtjobid.getText(), Integer.parseInt(txtcovercuttingwidth.getText()), Integer.parseInt(txtcovercuttingheight.getText()), cbcovera.getSelectedItem().toString().charAt(0), Integer.parseInt(txtcouerup.getText()));
            return jc;
        } catch (NumberFormatException nfe) {
            
        }
        return null;
    }
    
    private JobInside getJobInside() {
        try {
            JobInside ji = new JobInside(txtjobid.getText(), Integer.parseInt(txtinsidecuttingwidth.getText()), Integer.parseInt(txtinsidecuttingheight.getText()), cbinsidea.getSelectedItem().toString().charAt(0), Integer.parseInt(txtplateperbook.getText()), Integer.parseInt(txtinsideup.getText()));
            return ji;
        } catch (NumberFormatException nfe) {
            
        }
        return null;
    }
    
    private ArrayList getStepArray() {
        
        ArrayList al = new ArrayList();
        
        //Add before cut if it needed
        if (cnopadc > 1 || inopadc > 1) {
            al.add("Before Cutting");
        }
        
        //Printing must add to job
        al.add("Printing");
        
        //Add laminating job
        if ( !cbltype.getSelectedItem().toString().equals("Not selected") ) {
            String side = cblside.getSelectedItem().toString();
            String method = cblmethod.getSelectedItem().toString();
            String type = cbltype.getSelectedItem().toString();
            al.add(type+"-"+side+"-"+method);
        }
        
        //Add die cutting
         if ( cbdiecutting.isSelected() == true ) {
             al.add("Die Cutting");
        }
        
        //Add after cutting
        if ( cbaftercuttting.isSelected() == true ) {
            al.add("After Cutting");
        }
        
        //Add pasting
        if ( cbpasting.isSelected() == true ) {
            al.add("Pasting");
        }
        
        //Add binding
        if (!jrbno.isSelected()) {
            if(jrbperfect.isSelected()) {
                al.add("Binding-PERFECT");
            } else if (jrbside.isSelected()) {
                al.add("Binding-SIDE");
            } else if (jrbcenter.isSelected()) {
                al.add("Binding-CENTER");
            }
        }
        
        //Add sticher
        if ( cbsticher.isSelected() == true ) {
            al.add("Sticher");
        }
        
        //Add folding
        if ( cbfolding.isSelected() == true ) {
            al.add("Folding");
        }
        
        //Add gathering
        if ( cbgathering.isSelected() == true ) { 
            al.add("Gathering");
        }
        
        return al;
    }
    
    //Show warning message when going to delete job
    public void removeJob(String jobid) {
        int reply = JOptionPane.showConfirmDialog(null, "Are you sure, you want to remove this job ("+jobid+") ?", "Confirm remove", JOptionPane.WARNING_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
        if (reply == JOptionPane.OK_OPTION) {
            Job j = new Job(jobid);
            jd.removeJob(j);
        }
    }
    
    //This is for only accept numbers
    private void onlyNumbers(KeyEvent evt) {
        char key = evt.getKeyChar();
        if (!(Character.isDigit(key)) || (key==KeyEvent.VK_BACK_SPACE) || (key==KeyEvent.VK_DELETE)) {
            getToolkit().beep();
            evt.consume();
        } else if (key == KeyEvent.VK_ENTER) {
            
        }
    }
    
    //This for get ComboBoxModel of item
    private DefaultComboBoxModel getItemComboModel(String itemid) {
        DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
        ArrayList al = js.getItemList(itemid);
        Iterator i = al.iterator();
        while (i.hasNext()) {
            dcbm.addElement(i.next());
        }
        return dcbm;
    }
    
    //This is for auto complete
    private void autoComplete(KeyEvent evt) {
        String selected = cbitemid.getEditor().getItem().toString();
        int keycode = evt.getKeyCode();
        if (keycode >= 65 && keycode <= 90 || keycode >= 96 && keycode <= 105 || keycode == 8) {
            cbitemid.setModel(getItemComboModel(selected));
            if (cbitemid.getItemCount() > 0) {
                cbitemid.showPopup();
                if (keycode != 8) {
                    ((JTextComponent)cbitemid.getEditor().getEditorComponent()).select(selected.length(), cbitemid.getSelectedItem().toString().length());
                } else {
                    cbitemid.getEditor().setItem(selected);
                }
            }
        }
    }
    
    //
    public String getAutoGenaratedJobId() {
        String auto = js.getPreviousJobId();
        int number = Integer.parseInt(auto.substring(1));
        number++;
        if(auto == null) {
            return "j001";
        } else {
            return "j"+number;
        }
    }
    
    /*
    ** End of OOP coding
    */

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgbinding = new javax.swing.ButtonGroup();
        jPanel10 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        txtcodeno = new javax.swing.JTextField();
        lbljobid10 = new javax.swing.JLabel();
        lbljobid9 = new javax.swing.JLabel();
        lbljobid7 = new javax.swing.JLabel();
        lbljobid6 = new javax.swing.JLabel();
        txtjobid = new javax.swing.JTextField();
        txtqty = new javax.swing.JTextField();
        cbitemid = new javax.swing.JComboBox();
        lbljobid18 = new javax.swing.JLabel();
        jxdpjobdate = new org.jdesktop.swingx.JXDatePicker();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        lbljobid31 = new javax.swing.JLabel();
        cbdiecutting = new javax.swing.JCheckBox();
        lbljobid32 = new javax.swing.JLabel();
        cbaftercuttting = new javax.swing.JCheckBox();
        lbljobid34 = new javax.swing.JLabel();
        lbljobid37 = new javax.swing.JLabel();
        lbljobid38 = new javax.swing.JLabel();
        lbljobid39 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jrbperfect = new javax.swing.JRadioButton();
        jrbside = new javax.swing.JRadioButton();
        jrbcenter = new javax.swing.JRadioButton();
        jrbno = new javax.swing.JRadioButton();
        jPanel12 = new javax.swing.JPanel();
        lbljobid33 = new javax.swing.JLabel();
        lbljobid35 = new javax.swing.JLabel();
        cbltype = new javax.swing.JComboBox();
        cblside = new javax.swing.JComboBox();
        lbljobid36 = new javax.swing.JLabel();
        cblmethod = new javax.swing.JComboBox();
        cbpasting = new javax.swing.JCheckBox();
        cbsticher = new javax.swing.JCheckBox();
        cbfolding = new javax.swing.JCheckBox();
        cbgathering = new javax.swing.JCheckBox();
        jPanel5 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        lbljobid8 = new javax.swing.JLabel();
        lbljobid11 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        txtcouerup = new javax.swing.JTextField();
        lbljobid16 = new javax.swing.JLabel();
        cbcovera = new javax.swing.JComboBox();
        txtcovercuttingwidth = new javax.swing.JTextField();
        txtcovercuttingheight = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        txtinsideup = new javax.swing.JTextField();
        lbljobid17 = new javax.swing.JLabel();
        cbinsidea = new javax.swing.JComboBox();
        txtinsidecuttingwidth = new javax.swing.JTextField();
        txtinsidecuttingheight = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        lbljobid12 = new javax.swing.JLabel();
        txtflack = new javax.swing.JTextField();
        txtvalue = new javax.swing.JTextField();
        lbljobid13 = new javax.swing.JLabel();
        lbljobid14 = new javax.swing.JLabel();
        txtplateperbook = new javax.swing.JTextField();
        lbljobid15 = new javax.swing.JLabel();
        txttotalimpression = new javax.swing.JTextField();
        jpShowHide = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(800, 450));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(800, 450));
        setLayout(new java.awt.BorderLayout());

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel10.setMinimumSize(new java.awt.Dimension(820, 500));
        jPanel10.setOpaque(false);
        jPanel10.setPreferredSize(new java.awt.Dimension(820, 500));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        lbljobid10.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 12)); // NOI18N
        lbljobid10.setText("Quantity :");

        lbljobid9.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 12)); // NOI18N
        lbljobid9.setText("Code no :");

        lbljobid7.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 12)); // NOI18N
        lbljobid7.setText("item id :");

        lbljobid6.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 12)); // NOI18N
        lbljobid6.setText("Job ID :");

        txtqty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtqtyActionPerformed(evt);
            }
        });
        txtqty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtqtyKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtqtyKeyTyped(evt);
            }
        });

        cbitemid.setEditable(true);
        cbitemid.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbitemidItemStateChanged(evt);
            }
        });
        cbitemid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbitemidActionPerformed(evt);
            }
        });
        cbitemid.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cbitemidKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                cbitemidKeyTyped(evt);
            }
        });

        lbljobid18.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 12)); // NOI18N
        lbljobid18.setText("date :");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(lbljobid7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbljobid6, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtjobid)
                    .addComponent(cbitemid, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbljobid9, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbljobid10))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtqty, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtcodeno, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lbljobid18)
                        .addGap(18, 18, 18)
                        .addComponent(jxdpjobdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbljobid6, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtjobid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtcodeno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbljobid9, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbljobid18, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jxdpjobdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbljobid7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtqty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbljobid10, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbitemid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "cutting", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Copperplate Gothic Light", 0, 11))); // NOI18N

        lbljobid31.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 12)); // NOI18N
        lbljobid31.setText("Die cutting :");

        cbdiecutting.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbdiecuttingActionPerformed(evt);
            }
        });

        lbljobid32.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 12)); // NOI18N
        lbljobid32.setText("After printing :");

        cbaftercuttting.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbaftercutttingActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbljobid32, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
                    .addComponent(lbljobid31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbdiecutting, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cbaftercuttting, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbljobid31, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbdiecutting))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbljobid32, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbaftercuttting))
                .addGap(8, 8, 8))
        );

        lbljobid34.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 12)); // NOI18N
        lbljobid34.setText("Pasting :");

        lbljobid37.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 12)); // NOI18N
        lbljobid37.setText("Stitcher :");

        lbljobid38.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 12)); // NOI18N
        lbljobid38.setText("Folding :");

        lbljobid39.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 12)); // NOI18N
        lbljobid39.setText("Gathering :");

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "BINDING", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Copperplate Gothic Light", 0, 10))); // NOI18N

        bgbinding.add(jrbperfect);
        jrbperfect.setText("PERFECT");

        bgbinding.add(jrbside);
        jrbside.setText("SIDE");

        bgbinding.add(jrbcenter);
        jrbcenter.setText("CENTER");

        bgbinding.add(jrbno);
        jrbno.setSelected(true);
        jrbno.setText("NO");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jrbperfect)
                    .addComponent(jrbside)
                    .addComponent(jrbcenter)
                    .addComponent(jrbno))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jrbperfect)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jrbside, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jrbcenter)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jrbno)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "LAMINATING", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Copperplate Gothic Light", 0, 10))); // NOI18N

        lbljobid33.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 12)); // NOI18N
        lbljobid33.setText("type :");

        lbljobid35.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 12)); // NOI18N
        lbljobid35.setText("side :");

        cbltype.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Not selected", "Laminating", "Varnishing" }));
        cbltype.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbltypeItemStateChanged(evt);
            }
        });
        cbltype.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbltypeActionPerformed(evt);
            }
        });

        cblside.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "one side", "both side" }));

        lbljobid36.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 12)); // NOI18N
        lbljobid36.setText("method :");

        cblmethod.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "MAT", "GLOSS" }));

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbljobid36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbljobid35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbljobid33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cblside, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cblmethod, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbltype, 0, 94, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbljobid33, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbltype, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbljobid35, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cblside, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbljobid36, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cblmethod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbljobid37, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbljobid38, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbljobid39, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbljobid34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbpasting)
                    .addComponent(cbsticher)
                    .addComponent(cbfolding)
                    .addComponent(cbgathering))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lbljobid34, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbpasting))
                                .addGap(5, 5, 5)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lbljobid37, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(cbsticher))
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addGap(27, 27, 27)
                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(lbljobid38, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cbfolding))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lbljobid39, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbgathering)))
                            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        lbljobid8.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 12)); // NOI18N
        lbljobid8.setText("Cutting size :");

        lbljobid11.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 12)); // NOI18N
        lbljobid11.setText("printting up :");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbljobid8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbljobid11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(lbljobid8, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbljobid11, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("COVER"));

        txtcouerup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcouerupActionPerformed(evt);
            }
        });
        txtcouerup.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtcouerupKeyTyped(evt);
            }
        });

        lbljobid16.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 12)); // NOI18N
        lbljobid16.setText(" X ");

        cbcovera.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "'", "\"" }));

        txtcovercuttingwidth.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtcovercuttingwidthFocusLost(evt);
            }
        });
        txtcovercuttingwidth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcovercuttingwidthActionPerformed(evt);
            }
        });
        txtcovercuttingwidth.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtcovercuttingwidthPropertyChange(evt);
            }
        });
        txtcovercuttingwidth.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtcovercuttingwidthKeyTyped(evt);
            }
        });

        txtcovercuttingheight.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtcovercuttingheightFocusLost(evt);
            }
        });
        txtcovercuttingheight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcovercuttingheightActionPerformed(evt);
            }
        });
        txtcovercuttingheight.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtcovercuttingheightPropertyChange(evt);
            }
        });
        txtcovercuttingheight.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtcovercuttingheightKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtcouerup)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(txtcovercuttingwidth, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbljobid16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtcovercuttingheight, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)
                        .addComponent(cbcovera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 2, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbljobid16, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbcovera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtcovercuttingwidth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtcovercuttingheight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(txtcouerup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("INSIDE"));

        txtinsideup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtinsideupActionPerformed(evt);
            }
        });
        txtinsideup.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtinsideupKeyTyped(evt);
            }
        });

        lbljobid17.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 12)); // NOI18N
        lbljobid17.setText(" X ");

        cbinsidea.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "'", "\"" }));

        txtinsidecuttingwidth.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtinsidecuttingwidthFocusLost(evt);
            }
        });
        txtinsidecuttingwidth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtinsidecuttingwidthActionPerformed(evt);
            }
        });
        txtinsidecuttingwidth.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtinsidecuttingwidthKeyTyped(evt);
            }
        });

        txtinsidecuttingheight.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtinsidecuttingheightFocusLost(evt);
            }
        });
        txtinsidecuttingheight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtinsidecuttingheightActionPerformed(evt);
            }
        });
        txtinsidecuttingheight.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtinsidecuttingheightKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtinsideup)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(txtinsidecuttingwidth, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(lbljobid17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtinsidecuttingheight, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)
                        .addComponent(cbinsidea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbljobid17, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbinsidea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtinsidecuttingwidth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtinsidecuttingheight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(txtinsideup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        lbljobid12.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 12)); // NOI18N
        lbljobid12.setText("Waste :");

        txtflack.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtflackKeyTyped(evt);
            }
        });

        txtvalue.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtvalueKeyTyped(evt);
            }
        });

        lbljobid13.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 12)); // NOI18N
        lbljobid13.setText(" : ");

        lbljobid14.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 12)); // NOI18N
        lbljobid14.setText("plate per book :");

        txtplateperbook.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtplateperbookKeyTyped(evt);
            }
        });

        lbljobid15.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 12)); // NOI18N
        lbljobid15.setText("total Impression :");

        txttotalimpression.setEditable(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbljobid14)
                .addGap(30, 30, 30)
                .addComponent(txtplateperbook, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbljobid12)
                .addGap(18, 18, 18)
                .addComponent(txtflack, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbljobid13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtvalue, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbljobid15)
                .addGap(18, 18, 18)
                .addComponent(txttotalimpression, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lbljobid15, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txttotalimpression, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lbljobid12, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtflack, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtvalue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lbljobid13, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lbljobid14, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtplateperbook, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jpShowHide.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jpShowHide.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jpShowHide, javax.swing.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jpShowHide, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(80, Short.MAX_VALUE))
        );

        add(jPanel10, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void txtqtyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtqtyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtqtyActionPerformed

    private void cbdiecuttingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbdiecuttingActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbdiecuttingActionPerformed

    private void cbaftercutttingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbaftercutttingActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbaftercutttingActionPerformed

    private void txtcouerupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcouerupActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcouerupActionPerformed

    private void txtinsideupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtinsideupActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtinsideupActionPerformed

    private void cbltypeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbltypeItemStateChanged
        laminatingGroup();
    }//GEN-LAST:event_cbltypeItemStateChanged

    private void cbltypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbltypeActionPerformed

    }//GEN-LAST:event_cbltypeActionPerformed

    private void cbitemidItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbitemidItemStateChanged
        
    }//GEN-LAST:event_cbitemidItemStateChanged

    private void txtcovercuttingwidthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcovercuttingwidthActionPerformed
        
    }//GEN-LAST:event_txtcovercuttingwidthActionPerformed

    private void txtcovercuttingheightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcovercuttingheightActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcovercuttingheightActionPerformed

    private void txtinsidecuttingwidthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtinsidecuttingwidthActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtinsidecuttingwidthActionPerformed

    private void txtinsidecuttingheightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtinsidecuttingheightActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtinsidecuttingheightActionPerformed

    private void cbitemidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbitemidActionPerformed
        //calcPaper();
    }//GEN-LAST:event_cbitemidActionPerformed

    private void txtcovercuttingwidthPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtcovercuttingwidthPropertyChange
        
    }//GEN-LAST:event_txtcovercuttingwidthPropertyChange

    private void txtcovercuttingheightPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtcovercuttingheightPropertyChange
        
    }//GEN-LAST:event_txtcovercuttingheightPropertyChange

    private void txtcovercuttingwidthFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtcovercuttingwidthFocusLost
        setNoPecies();
    }//GEN-LAST:event_txtcovercuttingwidthFocusLost

    private void txtcovercuttingheightFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtcovercuttingheightFocusLost
        setNoPecies();
    }//GEN-LAST:event_txtcovercuttingheightFocusLost

    private void txtinsidecuttingwidthFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtinsidecuttingwidthFocusLost
        setNoPecies();
    }//GEN-LAST:event_txtinsidecuttingwidthFocusLost

    private void txtinsidecuttingheightFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtinsidecuttingheightFocusLost
        setNoPecies();
    }//GEN-LAST:event_txtinsidecuttingheightFocusLost

    private void txtqtyKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtqtyKeyTyped
        onlyNumbers(evt);
    }//GEN-LAST:event_txtqtyKeyTyped

    private void txtcovercuttingwidthKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcovercuttingwidthKeyTyped
        onlyNumbers(evt);
    }//GEN-LAST:event_txtcovercuttingwidthKeyTyped

    private void txtcovercuttingheightKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcovercuttingheightKeyTyped
        onlyNumbers(evt);
    }//GEN-LAST:event_txtcovercuttingheightKeyTyped

    private void txtinsidecuttingwidthKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtinsidecuttingwidthKeyTyped
        onlyNumbers(evt);
    }//GEN-LAST:event_txtinsidecuttingwidthKeyTyped

    private void txtinsidecuttingheightKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtinsidecuttingheightKeyTyped
        onlyNumbers(evt);
    }//GEN-LAST:event_txtinsidecuttingheightKeyTyped

    private void txtinsideupKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtinsideupKeyTyped
        onlyNumbers(evt);
    }//GEN-LAST:event_txtinsideupKeyTyped

    private void txtcouerupKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcouerupKeyTyped
        onlyNumbers(evt);
    }//GEN-LAST:event_txtcouerupKeyTyped

    private void txtplateperbookKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtplateperbookKeyTyped
        onlyNumbers(evt);
    }//GEN-LAST:event_txtplateperbookKeyTyped

    private void txtflackKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtflackKeyTyped
        onlyNumbers(evt);
    }//GEN-LAST:event_txtflackKeyTyped

    private void txtvalueKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtvalueKeyTyped
        onlyNumbers(evt);
    }//GEN-LAST:event_txtvalueKeyTyped

    private void cbitemidKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbitemidKeyReleased
        autoComplete(evt);
    }//GEN-LAST:event_cbitemidKeyReleased

    private void cbitemidKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbitemidKeyTyped
        
    }//GEN-LAST:event_cbitemidKeyTyped

    private void txtqtyKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtqtyKeyReleased
        
    }//GEN-LAST:event_txtqtyKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgbinding;
    private javax.swing.JCheckBox cbaftercuttting;
    public static javax.swing.JComboBox cbcovera;
    private javax.swing.JCheckBox cbdiecutting;
    private javax.swing.JCheckBox cbfolding;
    private javax.swing.JCheckBox cbgathering;
    public static javax.swing.JComboBox cbinsidea;
    public static javax.swing.JComboBox cbitemid;
    private javax.swing.JComboBox cblmethod;
    private javax.swing.JComboBox cblside;
    private javax.swing.JComboBox cbltype;
    private javax.swing.JCheckBox cbpasting;
    private javax.swing.JCheckBox cbsticher;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jpShowHide;
    private javax.swing.JRadioButton jrbcenter;
    private javax.swing.JRadioButton jrbno;
    private javax.swing.JRadioButton jrbperfect;
    private javax.swing.JRadioButton jrbside;
    public static org.jdesktop.swingx.JXDatePicker jxdpjobdate;
    private javax.swing.JLabel lbljobid10;
    private javax.swing.JLabel lbljobid11;
    private javax.swing.JLabel lbljobid12;
    private javax.swing.JLabel lbljobid13;
    private javax.swing.JLabel lbljobid14;
    private javax.swing.JLabel lbljobid15;
    private javax.swing.JLabel lbljobid16;
    private javax.swing.JLabel lbljobid17;
    private javax.swing.JLabel lbljobid18;
    private javax.swing.JLabel lbljobid31;
    private javax.swing.JLabel lbljobid32;
    private javax.swing.JLabel lbljobid33;
    private javax.swing.JLabel lbljobid34;
    private javax.swing.JLabel lbljobid35;
    private javax.swing.JLabel lbljobid36;
    private javax.swing.JLabel lbljobid37;
    private javax.swing.JLabel lbljobid38;
    private javax.swing.JLabel lbljobid39;
    private javax.swing.JLabel lbljobid6;
    private javax.swing.JLabel lbljobid7;
    private javax.swing.JLabel lbljobid8;
    private javax.swing.JLabel lbljobid9;
    public static javax.swing.JTextField txtcodeno;
    public static javax.swing.JTextField txtcouerup;
    public static javax.swing.JTextField txtcovercuttingheight;
    public static javax.swing.JTextField txtcovercuttingwidth;
    public static javax.swing.JTextField txtflack;
    public static javax.swing.JTextField txtinsidecuttingheight;
    public static javax.swing.JTextField txtinsidecuttingwidth;
    public static javax.swing.JTextField txtinsideup;
    public static javax.swing.JTextField txtjobid;
    public static javax.swing.JTextField txtplateperbook;
    public static javax.swing.JTextField txtqty;
    public static javax.swing.JTextField txttotalimpression;
    public static javax.swing.JTextField txtvalue;
    // End of variables declaration//GEN-END:variables
}
