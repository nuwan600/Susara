/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI.finishstock;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import static GUI.Home.Home.jPanel5;
import GUI.Homepanel.Homepanel;
import Class.PanelLoader.PanelLoader;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

import net.proteanit.sql.DbUtils;

/**
 *
 * @author sachin
 */

import Class.DBconnect.DBconnect;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class finish_stock extends javax.swing.JPanel {
    
    public Homepanel hp;
    public PanelLoader pl;
    
    //public DBconnect dbmanager ;
    //public Connection 
    
    DBconnect dbmanager = new DBconnect();
    Connection dbconn = null;
    Statement st = null;
    ResultSet rs = null;
    String sql = null;
    
    String status = "Insert";
    
    private float previse;

    public static float Amount;
    public static float Quantity;
    public static float unit_price;
    

    Calendar cal = new GregorianCalendar();
    
    boolean mode = false;

    /**
     * Creates new form Template
     */
    
    
    public String selectedFeild() {
        
        String selected = jComboBox_detail.getSelectedItem().toString();
        
        if(selected.equals("Stock ID")) {
            return "StockID2";
        } else if(selected.equals("Item ID")) {
            return "itemid";
        } else if (selected.equals("Job ID")) {
            return "JobID";
        }
        
        return null;
        
    }
    
    
    public finish_stock() {
        initComponents();
        
        fillTable();
        
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        jXDatePicker_Mindate.setFormats(df);
        jXDatePicker_Maxdate.setFormats(df);
        jXDatePicker_date.setFormats(df);
    }
    private double tot_amount = 0;
    
    
    public void resetadd() {
        
        jTextField_Quantity.setText("");
        jComboBox_itemID.setSelectedItem("");
        jComboBox_Jobid.setSelectedItem("");
        jComboBox_searchstock.setSelectedItem("");
        jXDatePicker_Mindate.setDate(null);
        jXDatePicker_Maxdate.setDate(null);
        
    }
    
    public void resettable() {
        jTextField_Ammout.setText("");
        jXDatePicker_date.setDate(null);
        jComboBox_StockID.setSelectedItem("");
        DefaultTableModel dtm = (DefaultTableModel) jTable_Stock.getModel();
        dtm.setNumRows(0);
    }
    
    public void fillTable() {
        
        try {
            String q = "SELECT * FROM finish_stock_details ORDER BY StockID2;" ;
            
            dbconn = dbmanager.connect();
            Statement s = dbconn.createStatement();
            
            ResultSet rs = s.executeQuery(q);
            
            DefaultTableModel dtm = (DefaultTableModel) jTable_Stock.getModel();
            dtm.setRowCount(0);
            
            while (rs.next()) {
                
                Vector v = new Vector();
                v.add(rs.getString(2));
                v.add(rs.getString(3));
                v.add(rs.getString(4));
                v.add(rs.getString(5));
                v.add(rs.getInt(6));
                v.add(rs.getDouble(7));
                v.add(rs.getDouble(8));
                
                dtm.addRow(v);
                
                
            }
            
            jTable_Stock.setModel(dtm);
            
        } catch (SQLException ex) {
            
        } finally {
            dbmanager.close(dbconn);
        }
        
        
    }
    
    public void feled_search(){
        Connection dbConn = null;
        
        try{
             dbConn = dbmanager.connect();
            Statement stmt = dbConn.createStatement();
            
        
        
        StringBuffer sqlnew = new StringBuffer("select * from finish_stock_details fsd,finish_stock fs where fs.StockID=fsd.StockID2 AND   ");
        if(!jComboBox_searchstock.getSelectedItem().toString().equals("")&&jXDatePicker_Mindate.getEditor().getText().equals("")&&jXDatePicker_Maxdate.getEditor().getText().equals(""))
        {
         sqlnew.append(" fsd."+selectedFeild()+"='"+jComboBox_searchstock.getSelectedItem().toString()+"';");
        }
        
        else if(!jComboBox_searchstock.getSelectedItem().toString().equals("")&&!jXDatePicker_Mindate.getEditor().getText().equals("")&&!jXDatePicker_Maxdate.getEditor().getText().equals(""))
        {
            sqlnew.append(" fsd."+selectedFeild()+"='"+jComboBox_searchstock.getSelectedItem().toString()+"' AND STR_TO_DATE(fsd.date, '%Y-%m-%d') between '"+jXDatePicker_Mindate.getEditor().getText()+"' AND '"+jXDatePicker_Maxdate.getEditor().getText()+"';");
        }
        
        else if(jComboBox_searchstock.getSelectedItem().toString().equals("")&&!jXDatePicker_Mindate.getEditor().getText().equals("")&&!jXDatePicker_Maxdate.getEditor().getText().equals(""))
        {
            sqlnew.append( " STR_TO_DATE(fsd.date, '%Y-%m-%d') between '"+jXDatePicker_Mindate.getEditor().getText()+"' AND '"+jXDatePicker_Maxdate.getEditor().getText()+"';");
        }
        
            //rs.close();
        
        
        
        
            rs = stmt.executeQuery(sqlnew.toString());
   
            
            DefaultTableModel dtm = (DefaultTableModel)jTable_Stock.getModel();
            dtm.setRowCount(0);
            
            while (rs.next()) {
                
                Vector v = new Vector();
                v.add(rs.getString(2));
                v.add(rs.getString(3));
                v.add(rs.getString(4));
                v.add(rs.getString(5));
                v.add(rs.getInt(6));
                v.add(rs.getDouble(7));
                v.add(rs.getDouble(8));
                
                dtm.addRow(v);
                
                
            }
            
            rs.previous();
            jTextField_Ammout.setText(rs.getString(11));
            
            jTable_Stock.setModel(dtm);
        
        }
        catch(Exception e){
            System.out.println(e);
        }
        
    }
    
    
    
    public void delete_stock(){
        Connection dbConn = null;
        try {

            dbConn = dbmanager.connect();
            Statement stmt = dbConn.createStatement();

            String StockID = jComboBox_searchstock.getSelectedItem().toString();

            String query = "DELETE FROM finish_stock_details where StockID2 = '" + StockID + "'";
            String query1 = "DELETE FROM finish_stock where StockID = '" + StockID + "'";

            stmt.executeUpdate(query);
            stmt.executeUpdate(query1);
            JOptionPane.showMessageDialog(null, "Successfully Deteted");
            //jButton_save.setEnabled(true);
            //jComboBox_searchstock.setEnabled(true);
        }
        
        catch (Exception e) {
            
            //if the insertion has problem return false

        } finally {
            //close the DB connection
            dbmanager.close(dbConn);
        }
        resettable();
        
    }
    
    public void delete_row(){
        Connection dbConn = null;
        try{
            dbConn = dbmanager.connect();
            Statement stmt = dbConn.createStatement();
            String ItemID = jComboBox_itemID.getSelectedItem().toString();
            String stockid = jComboBox_StockID.getSelectedItem().toString();
            
            String query = "DELETE FROM finish_stock_details where itemid = '" + ItemID + "' AND StockID2 = '" + stockid + "' ";
            //String query1 = "DELETE FROM finish_stock where itemid = '" + ItemID + "'";
            stmt.executeUpdate(query);
            //stmt.executeUpdate(query1);
            JOptionPane.showMessageDialog(null, "Successfully Deleted");
        }
        catch(Exception e){
            
        }
         finally {
            //close the DB connection
            dbmanager.close(dbConn);
        }
        resettable();
    }
    
    
    public void newSearch(String key) {
        Connection dbConn = null;
        
        try {
        
            String quary = "SELECT sd.date,sd.itemid,sd.jobID,sd.Quantity,sd.Unit_price,sd.Amount,fs.tot_amount FROM finish_stock_details sd,finish_stock fs WHERE fs.StockID=sd.StockID2 AND fs.StockID='"+key+"';";
            
            if (dbconn != null) {
                dbconn.close();
            }
            
            dbconn = dbmanager.connect();
            st = dbconn.createStatement();
            
            rs = st.executeQuery(quary);
            
            if(rs.next()) {
                
                rs.previous();
                Vector<Vector<String>> itemVector = null;
                itemVector = new Vector<Vector<String>>();
                
                DefaultTableModel dtm = (DefaultTableModel) jTable_Stock.getModel();
                dtm.setRowCount(0);
                
                while (rs.next()) {
                    Vector<String> newSearchdetails = new Vector<String>();
                    newSearchdetails.add(rs.getString(1));
                    newSearchdetails.add(key);
                    newSearchdetails.add(rs.getString(2));
                    newSearchdetails.add(rs.getString(3));
                    newSearchdetails.add(rs.getString(4));
                    newSearchdetails.add(rs.getString(5));
                    newSearchdetails.add(rs.getString(6));
                    
                    itemVector.add(newSearchdetails);
                    
                    dtm.addRow(newSearchdetails);
                }
                rs.previous();
                jXDatePicker_date.getEditor().setText(rs.getString(1));
                jComboBox_StockID.setSelectedItem(key);
                jTextField_Ammout.setText(rs.getString(7));
                
                jTable_Stock.setModel(dtm);
                
            } else {
                
                JOptionPane.showMessageDialog(null, "search item is not available");
                
            }

        } catch (SQLException ex) {
            
            JOptionPane.showMessageDialog(null, ex);
            
        }
        finally {
            //close the DB connection
            //dbmanager.con_close(dbConn);
        }
        
        
    }
    

    public void Update_stock(){
        Connection dbConn = null;
        try {

            dbConn = dbmanager.connect();
            Statement stmt = dbConn.createStatement();

            float tot_amount = Float.parseFloat(jTextField_Ammout.getText());
            String StockID = jComboBox_StockID.getSelectedItem().toString();
            String date = jXDatePicker_date.getEditor().getText();
            String itemID = jComboBox_itemID.getSelectedItem().toString();
            String jobID = jComboBox_Jobid.getSelectedItem().toString();
            String StockID2 = jComboBox_StockID.getSelectedItem().toString();
            float qty = Float.parseFloat(jTextField_Quantity.getText());

            String query = "UPDATE finish_Stock set stock_date = '" + date + "', tot_amount = "+ tot_amount + " WHERE StockID= '" + StockID + "';";
            String query1 = "UPDATE finish_stock_details set date = '" + date + "', itemid = '"+ itemID + "',JobID = '"+ jobID + "', Quantity = " +qty+ " WHERE StockID2= '" + StockID2 + "';";

            int val = stmt.executeUpdate(query);
            int val2 = stmt.executeUpdate(query1);
            if (val == 1 && val ==2) {
                Statement stmt1 = dbConn.createStatement();
                //String query2 = "delete from finish_Stock_details where StockID2 = '" + StockID + "'";
                //String query3 = "delete from finish_Stock where StockID = '" + StockID + "'";
                
                //stmt.execute(query2);
                //stmt.execute(query3);
                inset_stock_details();
                
                DefaultTableModel dtm = (DefaultTableModel) jTable_Stock.getModel();//convert to default table model//

            }
            JOptionPane.showMessageDialog(null, "Successfully Updated");
                jButton_save.setEnabled(true);
                jComboBox_StockID.setEnabled(true);
                resetadd();
                resettable();
        } catch (Exception error) {
            //if the insertion has problem return false
            JOptionPane.showMessageDialog(null, error);

        } finally {
            //close the DB connection
            dbmanager.close(dbConn);
        }
    }
    
    public void inset_stock_details(){
       
        Connection dbConn = null;
        try {
            
                         {
                DefaultTableModel dtm = (DefaultTableModel) jTable_Stock.getModel();//convert to default table model//
                int rowcount = dtm.getRowCount();
                
                    String StockID = jComboBox_StockID.getSelectedItem().toString();
                    String date = jXDatePicker_date.getEditor().getText();
                    Savestock(date, StockID, tot_amount);
                    
                sql = "INSERT INTO susara.finish_stock_details(date,StockID2,itemid,JobID,Quantity,unit_price,Amount) VALUES(?,?,?,?,?,?,?); ";

                dbConn = dbmanager.connect();
                
                
                for (int row = 0; row < rowcount; row++) {
                    PreparedStatement ps = dbConn.prepareStatement(sql);
                    
                    String itemid = jTable_Stock.getValueAt(row, 2).toString();
                    String JobID = jTable_Stock.getValueAt(row, 3).toString(); 
                    Double Quantity = Double.parseDouble(jTable_Stock.getValueAt(row, 4).toString());
                    Double unit_price = Double.parseDouble(jTable_Stock.getValueAt(row, 5).toString());
                    Double Amount = Double.parseDouble(jTable_Stock.getValueAt(row, 6).toString());
                    

                    ps.setString(1, date);
                    ps.setString(2, StockID);
                    ps.setString(3, itemid);
                    ps.setString(4, JobID);
                    ps.setDouble(5, Quantity);
                    ps.setDouble(6, unit_price);
                    ps.setDouble(7, Amount);
                    
                    int status = ps.executeUpdate();
                    if(status==1) {
                        System.out.println("ok"); 
                    }
                    
                    //dbmanager.con_close(dbConn);
                     
                     resetadd();
                     
                }
                resettable();
                JOptionPane.showMessageDialog(null, "Successfully Inserted Data");
            }
                         
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "error"+e);
        }
    }
    
    public void Savestock(String date,String StockID, Double tot_amount) {

        Connection dbConn = null;
        try {

            dbConn = dbmanager.connect();
            Statement stmt = dbConn.createStatement();

            String query = "INSERT INTO finish_stock(stock_date, Stockid, tot_amount) VALUES( '" +date+ "','" + StockID + "', "+ tot_amount +");";

            int val = stmt.executeUpdate(query);
            if (val == 1) {
                System.out.println("successfull");
                //updateQuntity(dbConn, itemid, qty);
            }
        } catch (Exception sQLException) {
            System.out.println("unsuccssfull"+sQLException);
        }
    }
    
    //clear cuttent table
    private void clearcurrentTable() {
        DefaultTableModel dtm = (DefaultTableModel) jTable_Stock.getModel() ;
        int rowcount = dtm.getRowCount() ;
        for (int i = rowcount - 1 ; i > -1 ; i-- ) {
            dtm.removeRow(i);
        }
        jTable_Stock.setModel(dtm);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jButton_save = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jButton20 = new javax.swing.JButton();
        jButton_update = new javax.swing.JButton();
        jButton_delete = new javax.swing.JButton();
        jButton_Refresh = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable_Stock = new javax.swing.JTable();
        jPanel13 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jComboBox_detail = new javax.swing.JComboBox();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jXDatePicker_Mindate = new org.jdesktop.swingx.JXDatePicker();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jXDatePicker_Maxdate = new org.jdesktop.swingx.JXDatePicker();
        jComboBox_searchstock = new javax.swing.JComboBox();
        jButton25 = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jXDatePicker_date = new org.jdesktop.swingx.JXDatePicker();
        jLabel16 = new javax.swing.JLabel();
        jComboBox_StockID = new javax.swing.JComboBox();
        jTextField_Ammout = new javax.swing.JTextField();
        jComboBox_itemID = new javax.swing.JComboBox();
        jTextField_Quantity = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jComboBox_Jobid = new javax.swing.JComboBox();
        jLabel17 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jButton_Add = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jButton22 = new javax.swing.JButton();
        jButton23 = new javax.swing.JButton();
        jButton24 = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jPanel17 = new javax.swing.JPanel();
        jButton27 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jComboBox12 = new javax.swing.JComboBox();
        jLabel19 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jComboBox11 = new javax.swing.JComboBox();
        jComboBox20 = new javax.swing.JComboBox();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jXDatePicker7 = new org.jdesktop.swingx.JXDatePicker();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jXDatePicker8 = new org.jdesktop.swingx.JXDatePicker();
        jPanel3 = new javax.swing.JPanel();
        jXDatePicker4 = new org.jdesktop.swingx.JXDatePicker();
        jLabel26 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jComboBox17 = new javax.swing.JComboBox();
        jComboBox8 = new javax.swing.JComboBox();
        jLabel24 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jComboBox18 = new javax.swing.JComboBox();
        jTextField2 = new javax.swing.JTextField();

        setFont(new java.awt.Font("Berlin Sans FB Demi", 0, 18)); // NOI18N
        setMinimumSize(new java.awt.Dimension(1024, 535));
        setPreferredSize(new java.awt.Dimension(1024, 535));
        setLayout(new java.awt.BorderLayout());

        jTabbedPane1.setFocusTraversalPolicyProvider(true);
        jTabbedPane1.setFont(new java.awt.Font("Berlin Sans FB Demi", 0, 18)); // NOI18N
        jTabbedPane1.setMinimumSize(new java.awt.Dimension(1024, 535));

        jPanel5.setLayout(new java.awt.BorderLayout());

        jPanel6.setBackground(new java.awt.Color(153, 153, 153));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel6.setPreferredSize(new java.awt.Dimension(152, 507));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jButton_save.setBackground(new java.awt.Color(51, 51, 51));
        jButton_save.setFont(new java.awt.Font("Bell MT", 1, 11)); // NOI18N
        jButton_save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/finishstock/Save-icon.png"))); // NOI18N
        jButton_save.setText("Save");
        jButton_save.setPreferredSize(new java.awt.Dimension(100, 35));
        jButton_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_saveActionPerformed(evt);
            }
        });

        jButton9.setBackground(new java.awt.Color(51, 51, 51));
        jButton9.setFont(new java.awt.Font("Bell MT", 1, 10)); // NOI18N
        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/finishstock/custom-reports-icon.png"))); // NOI18N
        jButton9.setText("<html>Generate<br>Report</html>");
        jButton9.setPreferredSize(new java.awt.Dimension(100, 35));

        jButton14.setBackground(new java.awt.Color(51, 51, 51));
        jButton14.setFont(new java.awt.Font("Bell MT", 1, 11)); // NOI18N
        jButton14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/finishstock/Actions-edit-clear-locationbar-rtl-icon.png"))); // NOI18N
        jButton14.setText("Clear");
        jButton14.setPreferredSize(new java.awt.Dimension(100, 35));
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        jButton20.setBackground(new java.awt.Color(51, 51, 51));
        jButton20.setFont(new java.awt.Font("Bell MT", 1, 11)); // NOI18N
        jButton20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/finishstock/Windows-Close-Program-icon.png"))); // NOI18N
        jButton20.setText("Close");
        jButton20.setPreferredSize(new java.awt.Dimension(100, 35));
        jButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton20ActionPerformed(evt);
            }
        });

        jButton_update.setBackground(new java.awt.Color(51, 51, 51));
        jButton_update.setFont(new java.awt.Font("Bell MT", 1, 11)); // NOI18N
        jButton_update.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/finishstock/Actions-edit-undo-icon.png"))); // NOI18N
        jButton_update.setText("Update");
        jButton_update.setPreferredSize(new java.awt.Dimension(100, 35));
        jButton_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_updateActionPerformed(evt);
            }
        });

        jButton_delete.setBackground(new java.awt.Color(51, 51, 51));
        jButton_delete.setFont(new java.awt.Font("Bell MT", 1, 11)); // NOI18N
        jButton_delete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/finishstock/Button-Delete-icon.png"))); // NOI18N
        jButton_delete.setText("Delete");
        jButton_delete.setPreferredSize(new java.awt.Dimension(100, 35));
        jButton_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_deleteActionPerformed(evt);
            }
        });

        jButton_Refresh.setBackground(new java.awt.Color(51, 51, 51));
        jButton_Refresh.setFont(new java.awt.Font("Bell MT", 1, 11)); // NOI18N
        jButton_Refresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/finishstock/refresh.png"))); // NOI18N
        jButton_Refresh.setText("Refresh");
        jButton_Refresh.setPreferredSize(new java.awt.Dimension(100, 35));
        jButton_Refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_RefreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_save, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_update, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_delete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_Refresh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jButton_save, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton_update, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton_delete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(75, 75, 75)
                .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton_Refresh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                .addComponent(jButton20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel5.add(jPanel6, java.awt.BorderLayout.EAST);

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jScrollPane4.setBackground(new java.awt.Color(255, 255, 255));

        jTable_Stock.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTable_Stock.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Date", "Stock ID", "Item", "job ID", "Quantity", "Unit price", "Amount"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable_Stock.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable_StockMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(jTable_Stock);

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));

        jPanel15.setBackground(new java.awt.Color(204, 204, 204));
        jPanel15.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel18.setFont(new java.awt.Font("Copperplate Gothic Light", 1, 12)); // NOI18N
        jLabel18.setText("Search detail");

        jComboBox_detail.setEditable(true);
        jComboBox_detail.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jComboBox_detail.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Stock ID", "Item ID", "Job ID" }));

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel31.setText("From");

        jLabel32.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 11)); // NOI18N
        jLabel32.setText("Date1");

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel33.setText("To");

        jLabel34.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 11)); // NOI18N
        jLabel34.setText("Date 2");

        jComboBox_searchstock.setEditable(true);
        jComboBox_searchstock.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jButton25.setBackground(new java.awt.Color(51, 51, 51));
        jButton25.setFont(new java.awt.Font("Bell MT", 1, 11)); // NOI18N
        jButton25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/finishstock/Search-icon.png"))); // NOI18N
        jButton25.setText("Search Stock");
        jButton25.setPreferredSize(new java.awt.Dimension(100, 35));
        jButton25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton25ActionPerformed(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Copperplate Gothic Light", 1, 12)); // NOI18N
        jLabel20.setText("Value");

        jButton1.setText("Demo Add");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Demo Search");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel32)
                        .addGap(7, 7, 7))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addGap(15, 15, 15)))
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jComboBox_detail, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jXDatePicker_Mindate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jComboBox_searchstock, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jXDatePicker_Maxdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jButton25, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox_detail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18)
                    .addComponent(jComboBox_searchstock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addGap(18, 18, 18)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(jXDatePicker_Maxdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jXDatePicker_Mindate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel34)
                    .addComponent(jLabel33))
                .addContainerGap())
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel16.setFont(new java.awt.Font("Copperplate Gothic Light", 1, 13)); // NOI18N
        jLabel16.setText("Total Amount");

        jComboBox_StockID.setEditable(true);
        jComboBox_StockID.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jTextField_Ammout.setEditable(false);
        jTextField_Ammout.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jComboBox_itemID.setEditable(true);
        jComboBox_itemID.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jComboBox_itemID.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "*select ItemID*", "A001", "A002", "B001", " " }));

        jTextField_Quantity.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextField_Quantity.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField_QuantityFocusLost(evt);
            }
        });
        jTextField_Quantity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_QuantityActionPerformed(evt);
            }
        });
        jTextField_Quantity.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField_QuantityKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField_QuantityKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_QuantityKeyTyped(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Copperplate Gothic Light", 1, 11)); // NOI18N
        jLabel2.setText("Date");

        jLabel13.setFont(new java.awt.Font("Copperplate Gothic Light", 1, 11)); // NOI18N
        jLabel13.setText("Quantity");

        jLabel30.setFont(new java.awt.Font("Copperplate Gothic Light", 1, 11)); // NOI18N
        jLabel30.setText("Item ID");

        jComboBox_Jobid.setEditable(true);
        jComboBox_Jobid.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jComboBox_Jobid.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "*Select JobID*", "JB001", "JB002", "JB007" }));
        jComboBox_Jobid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox_JobidActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Copperplate Gothic Light", 1, 11)); // NOI18N
        jLabel17.setText("Job ID");

        jLabel1.setFont(new java.awt.Font("Copperplate Gothic Light", 1, 11)); // NOI18N
        jLabel1.setText("Stock ID");

        jButton_Add.setBackground(new java.awt.Color(51, 51, 51));
        jButton_Add.setFont(new java.awt.Font("Bell MT", 1, 11)); // NOI18N
        jButton_Add.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/finishstock/Button-Add-icon.png"))); // NOI18N
        jButton_Add.setText("Add");
        jButton_Add.setPreferredSize(new java.awt.Dimension(100, 35));
        jButton_Add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_AddActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jXDatePicker_date, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox_StockID, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                        .addComponent(jComboBox_itemID, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox_Jobid, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField_Quantity, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton_Add, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(67, 67, 67)
                        .addComponent(jLabel1)
                        .addGap(71, 71, 71)
                        .addComponent(jLabel30)
                        .addGap(72, 72, 72)
                        .addComponent(jLabel17)
                        .addGap(70, 70, 70)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField_Ammout, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel30)
                            .addComponent(jLabel17)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jXDatePicker_date, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox_StockID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox_itemID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox_Jobid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_Quantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField_Ammout, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton_Add, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane4)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(13, 13, 13))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        jPanel5.add(jPanel8, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("           Finish Stock           ", jPanel5);

        jPanel9.setLayout(new java.awt.BorderLayout());

        jPanel10.setBackground(new java.awt.Color(255, 51, 51));
        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel10.setPreferredSize(new java.awt.Dimension(152, 507));

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jButton10.setBackground(new java.awt.Color(255, 51, 51));
        jButton10.setFont(new java.awt.Font("Bell MT", 1, 11)); // NOI18N
        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/finishstock/Button-Add-icon.png"))); // NOI18N
        jButton10.setText("Add");
        jButton10.setPreferredSize(new java.awt.Dimension(100, 35));

        jButton11.setBackground(new java.awt.Color(255, 51, 51));
        jButton11.setFont(new java.awt.Font("Bell MT", 1, 11)); // NOI18N
        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/finishstock/Save-icon.png"))); // NOI18N
        jButton11.setText("Save");
        jButton11.setPreferredSize(new java.awt.Dimension(100, 35));

        jButton15.setBackground(new java.awt.Color(255, 51, 51));
        jButton15.setFont(new java.awt.Font("Bell MT", 1, 11)); // NOI18N
        jButton15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/finishstock/print-icon.png"))); // NOI18N
        jButton15.setText("Print");
        jButton15.setPreferredSize(new java.awt.Dimension(100, 35));

        jButton16.setBackground(new java.awt.Color(255, 51, 51));
        jButton16.setFont(new java.awt.Font("Bell MT", 1, 11)); // NOI18N
        jButton16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/finishstock/Windows-Close-Program-icon.png"))); // NOI18N
        jButton16.setText("Close");
        jButton16.setPreferredSize(new java.awt.Dimension(100, 35));
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        jButton17.setBackground(new java.awt.Color(255, 51, 51));
        jButton17.setFont(new java.awt.Font("Bell MT", 1, 10)); // NOI18N
        jButton17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/finishstock/custom-reports-icon.png"))); // NOI18N
        jButton17.setText("<html>Generate<br>Report</html>");
        jButton17.setPreferredSize(new java.awt.Dimension(100, 35));
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });

        jButton22.setBackground(new java.awt.Color(255, 51, 51));
        jButton22.setFont(new java.awt.Font("Bell MT", 1, 11)); // NOI18N
        jButton22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/finishstock/Actions-edit-undo-icon.png"))); // NOI18N
        jButton22.setText("Update");
        jButton22.setPreferredSize(new java.awt.Dimension(100, 35));

        jButton23.setBackground(new java.awt.Color(255, 51, 51));
        jButton23.setFont(new java.awt.Font("Bell MT", 1, 11)); // NOI18N
        jButton23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/finishstock/Actions-edit-clear-locationbar-rtl-icon.png"))); // NOI18N
        jButton23.setText("Clear");
        jButton23.setPreferredSize(new java.awt.Dimension(100, 35));
        jButton23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton23ActionPerformed(evt);
            }
        });

        jButton24.setBackground(new java.awt.Color(255, 51, 51));
        jButton24.setFont(new java.awt.Font("Bell MT", 1, 11)); // NOI18N
        jButton24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/finishstock/Button-Delete-icon.png"))); // NOI18N
        jButton24.setText("Delete");
        jButton24.setPreferredSize(new java.awt.Dimension(100, 35));

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addComponent(jButton24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(78, 78, 78))
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 128, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 429, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(55, Short.MAX_VALUE))
        );

        jPanel9.add(jPanel10, java.awt.BorderLayout.EAST);

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel12.setPreferredSize(new java.awt.Dimension(874, 499));

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Date", "Item ID", "JOB ID", "Damage Discription", "Quantity", "Damage Type"
            }
        ));
        jScrollPane3.setViewportView(jTable3);

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));

        jButton27.setBackground(new java.awt.Color(255, 51, 51));
        jButton27.setFont(new java.awt.Font("Bell MT", 1, 11)); // NOI18N
        jButton27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/finishstock/Search-icon.png"))); // NOI18N
        jButton27.setText("Search");
        jButton27.setPreferredSize(new java.awt.Dimension(100, 35));

        jPanel1.setBackground(new java.awt.Color(237, 230, 230));

        jLabel22.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 11)); // NOI18N
        jLabel22.setText("Item name");

        jComboBox12.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel19.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 11)); // NOI18N
        jLabel19.setText("Item ID");

        jLabel7.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 11)); // NOI18N
        jLabel7.setText("Type");

        jComboBox11.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jComboBox20.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel35.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel35.setText("From");

        jLabel36.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 11)); // NOI18N
        jLabel36.setText("Date ");

        jLabel37.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel37.setText("To");

        jLabel38.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 11)); // NOI18N
        jLabel38.setText("Date");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jXDatePicker7, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jXDatePicker8, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jComboBox11, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jComboBox12, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7)
                        .addGap(28, 28, 28)
                        .addComponent(jComboBox20, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(jComboBox11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22)
                    .addComponent(jComboBox12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jComboBox20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel37, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel36)
                        .addComponent(jXDatePicker8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jXDatePicker7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel38)))
                .addGap(7, 7, 7))
        );

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));

        jLabel26.setFont(new java.awt.Font("Copperplate Gothic Light", 1, 11)); // NOI18N
        jLabel26.setText("Item ID");

        jLabel25.setFont(new java.awt.Font("Copperplate Gothic Light", 1, 11)); // NOI18N
        jLabel25.setText("Quantity");

        jLabel29.setFont(new java.awt.Font("Copperplate Gothic Light", 1, 11)); // NOI18N
        jLabel29.setText("Damage Type");

        jComboBox17.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jComboBox8.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel24.setFont(new java.awt.Font("Copperplate Gothic Light", 1, 11)); // NOI18N
        jLabel24.setText("Job ID");

        jLabel23.setFont(new java.awt.Font("Copperplate Gothic Light", 1, 11)); // NOI18N
        jLabel23.setText("Date");

        jComboBox18.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jXDatePicker4, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox17, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox8, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox18, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(jLabel23)
                        .addGap(104, 104, 104)
                        .addComponent(jLabel26)
                        .addGap(97, 97, 97)
                        .addComponent(jLabel24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(67, 67, 67)
                        .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel26)
                            .addComponent(jLabel23)
                            .addComponent(jLabel24))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(jLabel29)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBox18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBox17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jXDatePicker4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBox8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(5, 5, 5))
        );

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(132, 132, 132)
                        .addComponent(jButton27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31))
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 833, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel9.add(jPanel12, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("               Damages             ", jPanel9);

        add(jTabbedPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField_QuantityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_QuantityActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_QuantityActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        resetadd();
        resettable();
        status="Insert";
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton17ActionPerformed

    private void jButton20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton20ActionPerformed
       
    }//GEN-LAST:event_jButton20ActionPerformed

    private void jButton23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton23ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton23ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jComboBox_JobidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_JobidActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox_JobidActionPerformed

    private void jButton_AddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_AddActionPerformed
        String ItemID = jComboBox_itemID.getSelectedItem().toString();
        String Quantity = jTextField_Quantity.getText();
        String Amount = jTextField_Ammout.getText();
        String date = (jXDatePicker_date.getEditor().getText());
        String StockID = jComboBox_StockID.getSelectedItem().toString();
        String JobID = jComboBox_Jobid.getSelectedItem().toString();
        

        String sql = "select Unit_price from items where itemID = '" + ItemID + "'";
        float itemsammount;
        dbconn = dbmanager.connect();
        try {
            st = dbconn.createStatement();
            rs = st.executeQuery(sql);
            if (rs.next()) {
                float unitprice = rs.getFloat(1);
                itemsammount = unitprice * Float.parseFloat(Quantity);

                System.out.println(itemsammount);

                Vector v = new Vector();
                v.add(date);
                v.add(StockID);
                v.add(ItemID);
                v.add(JobID);
                v.add(Quantity);
                v.add(unitprice);
                v.add(itemsammount);
                
                
                DefaultTableModel dtm = (DefaultTableModel) jTable_Stock.getModel();
                
                if(mode == false) {
                    dtm.setRowCount(0);
                    mode = true;
                } 
                
                dtm.addRow(v);
                jTable_Stock.setModel(dtm);

                Amount += itemsammount;
                jTextField_Ammout.setText(Float.toString(itemsammount));
                
                tot_amount +=itemsammount;
                jTextField_Ammout.setText(Double.toString(tot_amount));
          }
        } catch (SQLException e) {

        }
        resetadd(); 
        
        
        
    }//GEN-LAST:event_jButton_AddActionPerformed

    private void jButton_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_saveActionPerformed
        int reply = JOptionPane.showConfirmDialog(null, "Do You Want to save this stock ? ");

            if (reply == JOptionPane.YES_OPTION) {
                 inset_stock_details();
            }
        
    }//GEN-LAST:event_jButton_saveActionPerformed

    private void jTextField_QuantityFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField_QuantityFocusLost
       
    }//GEN-LAST:event_jTextField_QuantityFocusLost

    private void jButton_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_deleteActionPerformed
         int reply = JOptionPane.showConfirmDialog(null, "Do You Want TO Delete this Stock ? ");

        if (reply == JOptionPane.YES_OPTION) {
            delete_row();
            delete_stock();
            
        }
    }//GEN-LAST:event_jButton_deleteActionPerformed

    private void jButton_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_updateActionPerformed
       int reply = JOptionPane.showConfirmDialog(null, "Do you Want to Update?");

        if (reply == JOptionPane.YES_OPTION) {
            Update_stock();
        }
    }//GEN-LAST:event_jButton_updateActionPerformed

    private void jButton25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton25ActionPerformed
       
        feled_search();
        
        
        
        
    }//GEN-LAST:event_jButton25ActionPerformed

    private void jTable_StockMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_StockMouseClicked

        
            int row =jTable_Stock.getSelectedRow();
            String startdate= (jTable_Stock.getModel().getValueAt(row,0).toString());
            jXDatePicker_date.getEditor().setText(startdate);
            String stockid= (jTable_Stock.getModel().getValueAt(row,1).toString());
            jComboBox_StockID.setSelectedItem(stockid);
             String itemid= (jTable_Stock.getModel().getValueAt(row,2).toString());
            jComboBox_itemID.setSelectedItem(itemid);
             String jobid= (jTable_Stock.getModel().getValueAt(row,3).toString());
            jComboBox_Jobid.setSelectedItem(jobid);
            String quantity= (jTable_Stock.getModel().getValueAt(row,4).toString());
            jTextField_Quantity.setText(quantity);
            //String unit_price = (jTable_Stock.getModel().getValueAt(row,5).toString());
            float x;
            float y = (Float.parseFloat(jTable_Stock.getModel().getValueAt(row,6).toString()));
             
            Connection dbConn = null;
        try {

            dbConn = dbmanager.connect();
            Statement stmt = dbConn.createStatement();

            String query = "select tot_amount from finish_stock where StockID = '" + stockid + "'";
            rs = stmt.executeQuery(query);
            if (rs.next()) {
                x = rs.getFloat(1);
                jTextField_Ammout.setText(Float.toString(x));
                previse = x - y;
            }
            
            status = "Update";
            
        } catch (Exception sQLException) {
            System.out.println(sQLException);
        }
            
            
            
           
           
        
    }//GEN-LAST:event_jTable_StockMouseClicked

    private void jButton_RefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_RefreshActionPerformed
        fillTable();
    }//GEN-LAST:event_jButton_RefreshActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        jTextField_Quantity.setText("500");
        jComboBox_itemID.setSelectedItem("A001");
        jComboBox_Jobid.setSelectedItem("J001");
        //jXDatePicker_date.setEditor().settext("2014-08-20");
        jComboBox_StockID.setSelectedItem("st500");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        jComboBox_searchstock.setSelectedItem("st500");
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTextField_QuantityKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_QuantityKeyTyped
        
//        String ItemID = jComboBox_itemID.getSelectedItem().toString();
//        String Quantity = jTextField_Quantity.getText();
//        
//        if(Quantity.equals("")) {
//            Quantity = "0";
//        }
//       
//        String sql = "select Unit_price from items where itemID = '" + ItemID + "'";
//        float itemsammount;
//        dbconn = dbmanager.connect();
//        try {
//            st = dbconn.createStatement();
//            rs = st.executeQuery(sql);
//            if (rs.next()) {
//                float unitprice = rs.getFloat(1);
//                System.out.println(unitprice);
//                itemsammount = (float)(unitprice * Float.parseFloat(Quantity));
//
//                System.out.println(itemsammount);
//                
//                jTextField_Ammout.setText(Float.toString(itemsammount));
//            }
//        }
//        catch(SQLException e){
//            
//        }
            
    }//GEN-LAST:event_jTextField_QuantityKeyTyped

    private void jTextField_QuantityKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_QuantityKeyPressed

    }//GEN-LAST:event_jTextField_QuantityKeyPressed

    private void jTextField_QuantityKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_QuantityKeyReleased

        String ItemID = jComboBox_itemID.getSelectedItem().toString();
        String Quantity = jTextField_Quantity.getText();

        if (Quantity.equals("")) {
            Quantity = "0";
        }

        String sql = "select Unit_price from items where itemID = '" + ItemID + "' ";
        float itemsammount = 0;
        dbconn = dbmanager.connect();
        try {
            st = dbconn.createStatement();
            rs = st.executeQuery(sql);
            if (rs.next()) {
                float unitprice = rs.getFloat(1);
                System.out.println(unitprice);
                if (status.equals("Update")) {
                    itemsammount = previse + (float) (unitprice * Float.parseFloat(Quantity));
                } else if (status.equals("Insert")) {
                    itemsammount = (float) (unitprice * Float.parseFloat(Quantity));
                }

                System.out.println(itemsammount);

                jTextField_Ammout.setText(Float.toString(itemsammount));
            }
        } catch (SQLException e) {

        } catch (NumberFormatException n) {
            
        }

    }//GEN-LAST:event_jTextField_QuantityKeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton22;
    private javax.swing.JButton jButton23;
    private javax.swing.JButton jButton24;
    private javax.swing.JButton jButton25;
    private javax.swing.JButton jButton27;
    private javax.swing.JButton jButton9;
    private javax.swing.JButton jButton_Add;
    private javax.swing.JButton jButton_Refresh;
    private javax.swing.JButton jButton_delete;
    private javax.swing.JButton jButton_save;
    private javax.swing.JButton jButton_update;
    private javax.swing.JComboBox jComboBox11;
    private javax.swing.JComboBox jComboBox12;
    private javax.swing.JComboBox jComboBox17;
    private javax.swing.JComboBox jComboBox18;
    private javax.swing.JComboBox jComboBox20;
    private javax.swing.JComboBox jComboBox8;
    private javax.swing.JComboBox jComboBox_Jobid;
    private javax.swing.JComboBox jComboBox_StockID;
    private javax.swing.JComboBox jComboBox_detail;
    private javax.swing.JComboBox jComboBox_itemID;
    private javax.swing.JComboBox jComboBox_searchstock;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable_Stock;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField_Ammout;
    private javax.swing.JTextField jTextField_Quantity;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker4;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker7;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker8;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker_Maxdate;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker_Mindate;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker_date;
    // End of variables declaration//GEN-END:variables
}
