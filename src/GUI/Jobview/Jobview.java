/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI.Jobview;

/**
 *
 * @author J.M Vikum Chathuranga 
 *         IT13131098
 *
 */

import Class.DBaccess.DBaccess;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Jobview extends javax.swing.JPanel {

    /**
     * Creates new form Jobview
     */
    
    DBaccess dba;
    
    ResultSet rs;
    
    DefaultTableModel dtm;
    
    public Jobview() {
        initComponents();
        dtm = (DefaultTableModel) tjobstatuse.getModel();
    }
    
    public void fillStatus(String jobid) {
        
        try {

            dba = new DBaccess();
            dtm.setRowCount(0);
            
            String quary = "SELECT statusName,startDate,endDate,machine,qty FROM jobstatus WHERE jobID2='" + jobid + "';";
            
            rs = dba.Dbselect(quary);
            
            while (rs.next()) {
                
                Vector v = new Vector();
                
                v.add(rs.getString(1));
                v.add(rs.getString(2));
                v.add(rs.getString(3));
                v.add(rs.getString(4));
                v.add(rs.getInt(5));
                
                dtm.addRow(v);
                
            }

        } catch (SQLException sqle) {
            
            
            
        } finally {
            
            dba.Dbclose();
            
        }
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tjobstatuse = new javax.swing.JTable();

        setMinimumSize(new java.awt.Dimension(400, 400));
        setPreferredSize(new java.awt.Dimension(400, 400));
        setLayout(new java.awt.BorderLayout());

        jPanel2.setLayout(new java.awt.BorderLayout());

        tjobstatuse.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Status Name", "Start Date", "End Date", "Machine", "Qty"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tjobstatuse);

        jPanel2.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        add(jPanel2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tjobstatuse;
    // End of variables declaration//GEN-END:variables
}
