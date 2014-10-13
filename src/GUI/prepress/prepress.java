/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.prepress;

import Class.DBconnect.DBconnect;
import java.awt.event.KeyAdapter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;

/**
 *
 * @author Vikum
 */
public class prepress extends javax.swing.JPanel {

    DBconnect dbmanager = new DBconnect();
    Connection dbconn = null;
    Statement st = null;
    ResultSet rs = null;
    String sql = null;

    Calendar cal = new GregorianCalendar();

    boolean mode = false;

    /**
     * Creates new form Template
     */
    public prepress() {
        initComponents();

        fillTable();
        fillTable_Book();

        jComboBox4_Bookin_Itemid.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jComboBox4_Bookin_ItemidKeyReleased(evt);
            }
        });
        
        jComboBox_pre_type_itemid.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jComboBox_pre_type_itemidKeyReleased(evt);
            }
        });
    }
    
    public DefaultComboBoxModel getItemIds(String itemsearch) {
        String query = "SELECT itemID FROM items WHERE itemID LIKE '" + itemsearch + "%'";
        dbconn = dbmanager.connect();
        DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
        try {
            Statement st = dbconn.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                dcbm.addElement(rs.getString(1));
            }
        } catch (SQLException ex) {

        }
        return dcbm;
    }

    public void fillTable() {

        try {
            String q = "SELECT * FROM pre_typesetting ORDER BY itemID5;";

            dbconn = dbmanager.connect();
            Statement s = dbconn.createStatement();

            ResultSet rs = s.executeQuery(q);

            DefaultTableModel dtm = (DefaultTableModel) jTablepre_typesett.getModel();
            dtm.setRowCount(0);

            while (rs.next()) {

                Vector v = new Vector();
                v.add(rs.getString(1));
                v.add(rs.getString(2));
                v.add(rs.getString(3));
                v.add(rs.getInt(4));
                v.add(rs.getString(5));
                v.add(rs.getString(6));
                v.add(rs.getString(7));
                v.add(rs.getString(8));

                dtm.addRow(v);

            }

            jTablepre_typesett.setModel(dtm);

        } catch (SQLException ex) {

        } finally {
            dbmanager.close(dbconn);
        }

    }

    public void newSearch(String key) {
        //  Connection dbConn = null;

        try {

            dbconn = dbmanager.connect();
            st = dbconn.createStatement();
            String quary = "SELECT * FROM pre_typesetting WHERE itemID5='" + key + "';";
            rs = st.executeQuery(quary);

            if (rs.next()) {
                DefaultTableModel dtm = (DefaultTableModel) jTablepre_typesett.getModel();
                dtm.setRowCount(0);
                Vector<String> newSearchdetails = new Vector<String>();
                newSearchdetails.add(rs.getString(1));
                newSearchdetails.add(rs.getString(2));
                newSearchdetails.add(rs.getString(3));
                newSearchdetails.add(rs.getString(4));
                newSearchdetails.add(rs.getString(5));
                newSearchdetails.add(rs.getString(6));
                newSearchdetails.add(rs.getString(7));
                newSearchdetails.add(rs.getString(8));

                //     itemVector.add(newSearchdetails);
                dtm.addRow(newSearchdetails);
            //    rs.previous();
                //   jXDatePicker_date.getEditor().setText(rs.getString(1));
                // jComboBox_StockID.setSelectedItem(key);
                //jTextField_Ammout.setText(rs.getString(7));

                //   jTable_Stock.setModel(dtm);
            } else {

                JOptionPane.showMessageDialog(null, "search item is not available");

            }

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(null, ex);

        } finally {
            //close the DB connection
            dbmanager.close(dbconn);
        }

    }

    public void resetadd_cover() {

        jComboBoxpre_cover_itemID.setSelectedItem("");
        jComboBoxpre_cover_size.setSelectedItem("");
        jComboBox_paper_type.setSelectedItem("");
        jComboBox16_maindesiner.setSelectedItem("");
        jComboBox17.setSelectedItem("");
        jComboBox_soft.setSelectedItem("");
        jComboBox19.setSelectedItem("");
        jComboBoxpre_colors.setSelectedItem("");
        jComboBoxpre_colorcat.setSelectedItem("");
        jXDatePickercover_statdate.setDate(null);
        jXDatePickercover_finishdate.setDate(null);

    }

    public void resettable_cover() {

        DefaultTableModel dtm = (DefaultTableModel) jTablepre_cover.getModel();
        dtm.setNumRows(0);
    }

    public void resetadd_typeset() {

        jComboBox_pre_type_itemid.setSelectedItem("");
        jComboBoxpre_type_fontstyle.setSelectedItem("");
        jComboBoxpre_type_fontsize.setSelectedItem("");
        jCombopre_typistid.setSelectedItem("");
        jComboBoxpr_typist_name.setSelectedItem("");
        jTextFieldpre_handoverper.setText("");
        jXDatePickerpre_type_startdate.setDate(null);
        jXDatePickerpre_type_finishdate.setDate(null);
        jXDatePickerpre_copyissudate.setDate(null);

    }

    public void resettable_typesett() {

        DefaultTableModel dtm = (DefaultTableModel) jTablepre_typesett.getModel();
        dtm.setNumRows(0);
    }

    public void insert_typeset() {
        Connection dbConn = null;
        try {

            {
                DefaultTableModel dtm = (DefaultTableModel) jTablepre_typesett.getModel();//convert to default table model//
                int rowcount = dtm.getRowCount();

                //String date1 = jXDatePickercover_statdate.getEditor().getText();
                //String date2 = jXDatePickercover_finishdate.getEditor().getText();
                sql = "INSERT INTO susara.pre_typesetting (itemID5,typist,fontname,fontsize,startdate,enddate,original_copy_date,hand_over_person) VALUES(?,?,?,?,?,?,?,?); ";

                dbConn = dbmanager.connect();

                for (int row = 0; row < rowcount; row++) {
                    PreparedStatement ps = dbConn.prepareStatement(sql);

                    String itemID5 = jTablepre_typesett.getValueAt(row, 0).toString();
                    String typist = jTablepre_typesett.getValueAt(row, 1).toString();
                    String fontname = jTablepre_typesett.getValueAt(row, 2).toString();
                    String font_size = jTablepre_typesett.getValueAt(row, 3).toString();
                    String start_date = jTablepre_typesett.getValueAt(row, 4).toString();
                    String enddate = jTablepre_typesett.getValueAt(row, 5).toString();
                    String originalcd = jTablepre_typesett.getValueAt(row, 6).toString();
                    String handperson = jTablepre_typesett.getValueAt(row, 7).toString();

                    ps.setString(1, itemID5);
                    ps.setString(2, typist);
                    ps.setString(3, fontname);
                    ps.setString(4, font_size);
                    ps.setString(5, start_date);
                    ps.setString(6, enddate);
                    ps.setString(7, originalcd);
                    ps.setString(8, handperson);

                    int status = ps.executeUpdate();
                    if (status == 1) {
                        System.out.println("ok");

                    }

                    //dbmanager.con_close(dbConn);
                    //resetadd();
                }
                JOptionPane.showMessageDialog(null, "Successfully Inserted Data");
                resetadd_typeset();
                resettable_typesett();
                //resettable();

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "error" + e);
        }
    }

    public void fillTable_Book() {

        try {
            String q = "SELECT * FROM pre_book ORDER BY itemID6;";

            dbconn = dbmanager.connect();
            Statement s = dbconn.createStatement();

            ResultSet rs = s.executeQuery(q);

            DefaultTableModel dtm = (DefaultTableModel) jTable3_Bookin.getModel();
            dtm.setRowCount(0);

            while (rs.next()) {

                Vector v = new Vector();
                v.add(rs.getString(1));
                v.add(rs.getString(2));
                v.add(rs.getString(3));
                v.add(rs.getString(4));
                v.add(rs.getString(5));

                dtm.addRow(v);

            }

            jTable3_Bookin.setModel(dtm);

        } catch (SQLException ex) {

        } finally {
            dbmanager.close(dbconn);
        }

//        public void typesett_Search(String key) {
//        Connection dbConn = null;
//        
//        try {
//            dbConn = dbmanager.connect();
//            Statement stmt = dbConn.createStatement();
//            rs = st.executeQuery(quary);
//            
//            String itemID5 = jComboBox_pre_type_itemid.getSelectedItem().toString();
//            
//            String quary = "SELECT * FROM pre_typesetting where itemID5 = '" + itemID5 + "'"; 
//            
//            if (dbconn != null) {
//                dbconn.close();
//            }
//            
//            dbconn = dbmanager.connect();
//            st = dbconn.createStatement();
//            
//            rs = st.executeQuery(quary);
//            
//            if(rs.next()) {
//                
//                rs.previous();
//                Vector<Vector<String>> itemVector = null;
//                itemVector = new Vector<Vector<String>>();
//                
//                
//                DefaultTableModel dtm = (DefaultTableModel) jTablepre_typesett.getModel();
//                dtm.setRowCount(0);
//                
//                while (rs.next()) {
//                    Vector<String> newSearchdetails = new Vector<String>();
//                    newSearchdetails.add(rs.getString(1));
//                    newSearchdetails.add(key);
//                    newSearchdetails.add(rs.getString(2));
//                    newSearchdetails.add(rs.getString(3));
//                    newSearchdetails.add(rs.getString(4));
//                    newSearchdetails.add(rs.getString(5));
//                    newSearchdetails.add(rs.getString(6));
//                    
//                    itemVector.add(newSearchdetails);
//                    
//                    dtm.addRow(newSearchdetails);
//                
//                rs.previous();
//                
//                jTablepre_typesett.setModel(dtm);
//                
//            } 
//                else {
//                
//                JOptionPane.showMessageDialog(null, "search item is not available");
//                
//            }
//            
//     
//
//        } catch (SQLException ex) {
//            
//            JOptionPane.showMessageDialog(null, ex);
//            
//        }
//        finally {
//            //close the DB connection
//            dbmanager.con_close(dbconn);
//        }
//        }
//        }
    }

    public void insertBookinside() {

        Connection dbConn = null;
        try {

            {
                DefaultTableModel dtm = (DefaultTableModel) jTable3_Bookin.getModel();//convert to default table model//
                int rowcount = dtm.getRowCount();

                //String date1 = jXDatePickercover_statdate.getEditor().getText();
                //String date2 = jXDatePickercover_finishdate.getEditor().getText();
                sql = "INSERT INTO susara.pre_book (itemID6,paper_size,paper_meterial,no_of_pages,colors) VALUES(?,?,?,?,?); ";

                dbConn = dbmanager.connect();

                for (int row = 0; row < rowcount; row++) {
                    PreparedStatement ps = dbConn.prepareStatement(sql);

                    String itemID6 = jTable3_Bookin.getValueAt(row, 0).toString();
                    String paper_size = jTable3_Bookin.getValueAt(row, 1).toString();
                    String paper_meterial = jTable3_Bookin.getValueAt(row, 2).toString();
                    String no_of_pages = jTable3_Bookin.getValueAt(row, 3).toString();
                    String colors = jTable3_Bookin.getValueAt(row, 4).toString();

                    ps.setString(1, itemID6);
                    ps.setString(2, paper_size);
                    ps.setString(3, paper_meterial);
                    ps.setString(4, no_of_pages);
                    ps.setString(5, colors);

                    int status = ps.executeUpdate();
                    if (status == 1) {
                        System.out.println("ok");
                    }

                    //dbmanager.con_close(dbConn);
                    //resetadd();
                }
                //resettable();
                JOptionPane.showMessageDialog(null, "Successfully Inserted Data");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "error" + e);
        }

    }

    public void resetadd_bookinside() {

        jComboBox4_Bookin_Itemid.setSelectedItem("");
        jComboBox7_Bookin_Pa1.setSelectedItem("");
        jTextField2_Bookin_Num.setText("");
        jComboBox6_Bookin_colo.setSelectedItem("");
        jComboBox20_Bookin_ColoCa.setSelectedItem("");
        jComboBox5_Bookin_Bsize.setSelectedItem("");

        //jXDatePickerpre_type_startdate.setDate(null);
        //jXDatePickerpre_type_finishdate.setDate(null);
        //jXDatePickerpre_copyissudate.setDate(null);
    }

    public void resettable_bookinside() {

        DefaultTableModel dtm = (DefaultTableModel) jTable3_Bookin.getModel();
        dtm.setNumRows(0);
    }

    public void delete_type_sett() {
        Connection dbConn = null;
        try {

            dbConn = dbmanager.connect();
            Statement stmt = dbConn.createStatement();

            String itemID5 = jComboBox_pre_type_itemid.getSelectedItem().toString();

            String query = "DELETE FROM susara.pre_typesetting where itemID5 = '" + itemID5 + "'";

            stmt.executeUpdate(query);

            JOptionPane.showMessageDialog(null, "Successfully Deteted");
            resetadd_typeset();
            resettable_typesett();
            //jButton_save.setEnabled(true);
            //txtpurchasingid1.setEnabled(true);
        } catch (Exception e) {

            //if the insertion has problem return false
        } finally {
            //close the DB connection
            //dbmanager.con_close(dbConn);
        }
        //resettable();

    }

    public void insertcover() {

        Connection dbConn = null;
        try {

            {
                DefaultTableModel dtm = (DefaultTableModel) jTablepre_cover.getModel();//convert to default table model//
                int rowcount = dtm.getRowCount();

                String date1 = jXDatePickercover_statdate.getEditor().getText();
                String date2 = jXDatePickercover_finishdate.getEditor().getText();

                sql = "INSERT INTO susara.pre_cover (itemID4,cover_size,paper_type,colors,designer,softwares) VALUES(?,?,?,?,?,?); ";

                dbConn = dbmanager.connect();

                for (int row = 0; row < rowcount; row++) {
                    PreparedStatement ps = dbConn.prepareStatement(sql);

                    String itemID4 = jTablepre_cover.getValueAt(row, 0).toString();
                    String cover_size = jTablepre_cover.getValueAt(row, 1).toString();
                    String paper_type = jTablepre_cover.getValueAt(row, 2).toString();
                    String colors = jTablepre_cover.getValueAt(row, 3).toString();
                    String designer = jTablepre_cover.getValueAt(row, 4).toString();
                    String softwares = jTablepre_cover.getValueAt(row, 5).toString();

                    ps.setString(1, itemID4);
                    ps.setString(2, cover_size);
                    ps.setString(3, paper_type);
                    ps.setString(4, colors);
                    ps.setString(5, designer);
                    ps.setString(6, softwares);

                    int status = ps.executeUpdate();
                    if (status == 1) {
                        System.out.println("ok");
                    }

                    //dbmanager.con_close(dbConn);
                    //resetadd();
                }
                //resettable();
                JOptionPane.showMessageDialog(null, "Successfully Inserted Data");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "error" + e);
        }

    }

    private void updateTypesetting() {
        try {

            String q = "UPDATE pre_typesetting SET itemID5='" + jComboBox_pre_type_itemid.getSelectedItem().toString() + "' ,typist='" + jCombopre_typistid.getSelectedItem().toString() + "',fontname='" + jComboBoxpre_type_fontstyle.getSelectedItem().toString() + "' ,fontsize=" + jComboBoxpre_type_fontsize.getSelectedItem().toString() + " ,startdate='" + jXDatePickerpre_type_startdate.getEditor().getText() + "' ,enddate='" + jXDatePickerpre_type_finishdate.getEditor().getText() + "' ,original_copy_date='" + jXDatePickerpre_copyissudate.getEditor().getText() + "',hand_over_person='" + jTextFieldpre_handoverper.getText() + "'  WHERE itemID5='" + jComboBox_pre_type_itemid.getSelectedItem().toString() + "' ;";

            String q2 = "UPDATE pre_typesetting SET itemID5='" + jComboBox_pre_type_itemid.getSelectedItem().toString() + "' ,typist='" + jCombopre_typistid.getSelectedItem().toString() + "',fontname='" + jComboBoxpre_type_fontstyle.getSelectedItem().toString() + "' ,fontsize=" + jComboBoxpre_type_fontsize.getSelectedItem().toString() + " ,startdate='" + jXDatePickerpre_type_startdate.getEditor().getText() + "' ,enddate='" + jXDatePickerpre_type_finishdate.getEditor().getText() + "' ,original_copy_date='" + jXDatePickerpre_copyissudate.getEditor().getText() + "',hand_over_person='" + jTextFieldpre_handoverper.getText() + "'  WHERE itemID5='" + jComboBox_pre_type_itemid.getSelectedItem().toString() + "' ;";
            Connection con = dbmanager.connect();
            Statement s111 = con.createStatement();
            int status = s111.executeUpdate(q2);

            if (status == 1) {
                JOptionPane.showMessageDialog(null, " Successfully Updated");
            } else {

            }

        } catch (SQLException ex) {

            System.out.println("error" + ex);

        }

    }

    private void updateBookInside() {

        try {

            String q = "UPDATE pre_book SET itemID6='" + jComboBox4_Bookin_Itemid.getSelectedItem().toString() + "' ,paper_size='" + jComboBox5_Bookin_Bsize.getSelectedItem().toString() + "',paper_meterial='" + jComboBox7_Bookin_Pa1.getSelectedItem().toString() + "' ,no_of_pages=" + jTextField2_Bookin_Num.getText().toString() + " ,colors='" + jComboBox6_Bookin_colo.getSelectedItem().toString() + "'  WHERE itemID6='" + jComboBox4_Bookin_Itemid.getSelectedItem().toString() + "' ;";

            String q2 = "UPDATE pre_book SET itemID6='" + jComboBox4_Bookin_Itemid.getSelectedItem().toString() + "' ,papersize='" + jComboBox5_Bookin_Bsize.getSelectedItem().toString() + "',papermeterial='" + jComboBox7_Bookin_Pa1.getSelectedItem().toString() + "' ,noofpages=" + jTextField2_Bookin_Num.getText().toString() + " ,colors='" + jComboBox6_Bookin_colo.getSelectedItem().toString() + "' WHERE itemID6='" + jComboBox4_Bookin_Itemid.getSelectedItem().toString() + "' ;";

            Connection con = dbmanager.connect();
            Statement s111 = con.createStatement();
            int status = s111.executeUpdate(q2);

            if (status == 1) {
                JOptionPane.showMessageDialog(null, " Successfully Updated");
            } else {

            }

        } catch (SQLException ex) {

            System.out.println("error" + ex);

        }
    }

    public void delete_Book_Inside() {
        Connection dbConn = null;
        try {

            dbConn = dbmanager.connect();
            Statement stmt = dbConn.createStatement();

            String itemID6 = jComboBox4_Bookin_Itemid.getSelectedItem().toString();

            String query = "DELETE FROM susara.pre_book where itemID6 = '" + itemID6 + "'";

            stmt.executeUpdate(query);

            JOptionPane.showMessageDialog(null, "Successfully Deteted");
            resetadd_typeset();
            resettable_typesett();
            //jButton_save.setEnabled(true);
            //txtpurchasingid1.setEnabled(true);
        } catch (Exception e) {

            //if the insertion has problem return false
        } finally {
            //close the DB connection
            //dbmanager.con_close(dbConn);
        }
        //resettable();

    }

    public void BookSearch(String key) {
        //  Connection dbConn = null;

        try {

            dbconn = dbmanager.connect();
            st = dbconn.createStatement();
            String quary = "SELECT * FROM pre_book WHERE itemID6='" + key + "';";
            rs = st.executeQuery(quary);

            if (rs.next()) {
                DefaultTableModel dtm = (DefaultTableModel) jTable3_Bookin.getModel();
                dtm.setRowCount(0);
                Vector<String> BookSearchdetails = new Vector<String>();
                BookSearchdetails.add(rs.getString(1));
                BookSearchdetails.add(rs.getString(2));
                BookSearchdetails.add(rs.getString(3));
                BookSearchdetails.add(rs.getString(4));
                BookSearchdetails.add(rs.getString(5));

                //     itemVector.add(newSearchdetails);
                dtm.addRow(BookSearchdetails);
            //    rs.previous();
                //   jXDatePicker_date.getEditor().setText(rs.getString(1));
                // jComboBox_StockID.setSelectedItem(key);
                //jTextField_Ammout.setText(rs.getString(7));

                //   jTable_Stock.setModel(dtm);
            } else {

                JOptionPane.showMessageDialog(null, "search item is not available");

            }

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(null, ex);

        } finally {
            //close the DB connection
            dbmanager.close(dbconn);
        }

    }

    public void delete_cover() {
        Connection dbConn = null;
        try {

            dbConn = dbmanager.connect();
            Statement stmt = dbConn.createStatement();

            String itemID4 = jComboBoxpre_cover_itemID.getSelectedItem().toString();

            String query = "DELETE FROM susara.pre_cover where itemID4 = '" + 4 + "'";

            stmt.executeUpdate(query);

            JOptionPane.showMessageDialog(null, "Successfully Deteted");
            resetadd_cover();
            resettable_cover();
            //jButton_save.setEnabled(true);
            //txtpurchasingid1.setEnabled(true);
        } catch (Exception e) {

            //if the insertion has problem return false
        } finally {
            //close the DB connection
            //dbmanager.con_close(dbConn);
        }
        //resettable();

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
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jButton9 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        button_typesett_save = new javax.swing.JButton();
        jButton_update_Bookin = new javax.swing.JButton();
        jButton_typesett_delete = new javax.swing.JButton();
        jButtonpre_clear_typesett = new javax.swing.JButton();
        jButton_Refresh = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jComboBox_pre_type_itemid = new javax.swing.JComboBox();
        jPanel16 = new javax.swing.JPanel();
        jComboBoxpre_type_fontstyle = new javax.swing.JComboBox();
        jTextFieldpre_handoverper = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jComboBoxpre_type_fontsize = new javax.swing.JComboBox();
        jLabel23 = new javax.swing.JLabel();
        jXDatePickerpre_copyissudate = new org.jdesktop.swingx.JXDatePicker();
        jLabel29 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jCombopre_typistid = new javax.swing.JComboBox();
        jComboBoxpr_typist_name = new javax.swing.JComboBox();
        jLabel27 = new javax.swing.JLabel();
        jXDatePickerpre_type_startdate = new org.jdesktop.swingx.JXDatePicker();
        jLabel28 = new javax.swing.JLabel();
        jXDatePickerpre_type_finishdate = new org.jdesktop.swingx.JXDatePicker();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTablepre_typesett = new javax.swing.JTable();
        jButton_typesett_search = new javax.swing.JButton();
        jButton_add_pre_type = new javax.swing.JButton();
        jButton_Demo_preType = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jButton17 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        jButton_save_Bookin = new javax.swing.JButton();
        jButton_Bookin_update = new javax.swing.JButton();
        jButton_Delete_Bookin = new javax.swing.JButton();
        jButton_clear_BookIn = new javax.swing.JButton();
        jButton_Refresh_BookIn = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jComboBox4_Bookin_Itemid = new javax.swing.JComboBox();
        jPanel13 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jComboBox9 = new javax.swing.JComboBox();
        jComboBox8_Bookin_pa2 = new javax.swing.JComboBox();
        jLabel20 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jComboBox7_Bookin_Pa1 = new javax.swing.JComboBox();
        jComboBox5_Bookin_Bsize = new javax.swing.JComboBox();
        jLabel19 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jTextField2_Bookin_Num = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jComboBox6_Bookin_colo = new javax.swing.JComboBox();
        jLabel13 = new javax.swing.JLabel();
        jComboBox20_Bookin_ColoCa = new javax.swing.JComboBox();
        jLabel16 = new javax.swing.JLabel();
        jCheckBox3_Bookin_photo = new javax.swing.JCheckBox();
        jLabel15 = new javax.swing.JLabel();
        jCheckBox4_Bookin_net = new javax.swing.JCheckBox();
        jLabel48 = new javax.swing.JLabel();
        jCheckBox8_Bookin_art = new javax.swing.JCheckBox();
        jButton_Add_BookIn = new javax.swing.JButton();
        jPanel14 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3_Bookin = new javax.swing.JTable();
        jButton_search_Bookin = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jButton_coverSave = new javax.swing.JButton();
        jButton26 = new javax.swing.JButton();
        jButton_delete_cover = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton28 = new javax.swing.JButton();
        jButton29 = new javax.swing.JButton();
        jButtonpre_clear = new javax.swing.JButton();
        jButton_Refresh_BookIn1 = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        jComboBoxpre_cover_itemID = new javax.swing.JComboBox();
        jPanel18 = new javax.swing.JPanel();
        jComboBoxpre_cover_size = new javax.swing.JComboBox();
        jLabel47 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jCheckBox6 = new javax.swing.JCheckBox();
        jComboBox_soft = new javax.swing.JComboBox();
        jCheckBox5 = new javax.swing.JCheckBox();
        jLabel38 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jComboBoxpre_colors = new javax.swing.JComboBox();
        jCheckBox7 = new javax.swing.JCheckBox();
        jComboBox16_maindesiner = new javax.swing.JComboBox();
        jComboBox_paper_type = new javax.swing.JComboBox();
        jLabel32 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jComboBox17 = new javax.swing.JComboBox();
        jComboBox19 = new javax.swing.JComboBox();
        jLabel14 = new javax.swing.JLabel();
        jComboBoxpre_colorcat = new javax.swing.JComboBox();
        jXDatePickercover_finishdate = new org.jdesktop.swingx.JXDatePicker();
        jLabel42 = new javax.swing.JLabel();
        jXDatePickercover_statdate = new org.jdesktop.swingx.JXDatePicker();
        jButton_cover_Add = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTablepre_cover = new javax.swing.JTable();
        jButton_pre_cover = new javax.swing.JButton();

        setMinimumSize(new java.awt.Dimension(1024, 535));
        setPreferredSize(new java.awt.Dimension(1024, 535));
        setLayout(new java.awt.BorderLayout());

        jTabbedPane1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTabbedPane1.setMinimumSize(new java.awt.Dimension(1024, 535));

        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(153, 153, 153));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel2.setPreferredSize(new java.awt.Dimension(152, 507));

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jButton9.setBackground(new java.awt.Color(51, 51, 51));
        jButton9.setFont(new java.awt.Font("Bell MT", 1, 10)); // NOI18N
        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/prepress/custom-reports-icon.png"))); // NOI18N
        jButton9.setText("<html>Generate<br>Report</html>");
        jButton9.setPreferredSize(new java.awt.Dimension(100, 35));

        jButton14.setBackground(new java.awt.Color(51, 51, 51));
        jButton14.setFont(new java.awt.Font("Bell MT", 1, 11)); // NOI18N
        jButton14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/prepress/Windows-Close-Program-icon.png"))); // NOI18N
        jButton14.setText("Close");
        jButton14.setPreferredSize(new java.awt.Dimension(100, 35));
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        button_typesett_save.setBackground(new java.awt.Color(51, 51, 51));
        button_typesett_save.setFont(new java.awt.Font("Bell MT", 1, 11)); // NOI18N
        button_typesett_save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/prepress/Save-icon.png"))); // NOI18N
        button_typesett_save.setText("Save");
        button_typesett_save.setPreferredSize(new java.awt.Dimension(100, 35));
        button_typesett_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_typesett_saveActionPerformed(evt);
            }
        });

        jButton_update_Bookin.setBackground(new java.awt.Color(51, 51, 51));
        jButton_update_Bookin.setFont(new java.awt.Font("Bell MT", 1, 11)); // NOI18N
        jButton_update_Bookin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/prepress/Actions-edit-undo-icon.png"))); // NOI18N
        jButton_update_Bookin.setText("Update");
        jButton_update_Bookin.setPreferredSize(new java.awt.Dimension(100, 35));
        jButton_update_Bookin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_update_BookinActionPerformed(evt);
            }
        });

        jButton_typesett_delete.setBackground(new java.awt.Color(51, 51, 51));
        jButton_typesett_delete.setFont(new java.awt.Font("Bell MT", 1, 11)); // NOI18N
        jButton_typesett_delete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/prepress/Button-Delete-icon.png"))); // NOI18N
        jButton_typesett_delete.setText("Delete");
        jButton_typesett_delete.setPreferredSize(new java.awt.Dimension(100, 35));
        jButton_typesett_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_typesett_deleteActionPerformed(evt);
            }
        });

        jButtonpre_clear_typesett.setBackground(new java.awt.Color(51, 51, 51));
        jButtonpre_clear_typesett.setFont(new java.awt.Font("Bell MT", 1, 11)); // NOI18N
        jButtonpre_clear_typesett.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/finishstock/Actions-edit-clear-locationbar-rtl-icon.png"))); // NOI18N
        jButtonpre_clear_typesett.setText("Clear");
        jButtonpre_clear_typesett.setPreferredSize(new java.awt.Dimension(100, 35));
        jButtonpre_clear_typesett.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonpre_clear_typesettActionPerformed(evt);
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

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(button_typesett_save, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_update_Bookin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_typesett_delete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonpre_clear_typesett, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_Refresh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(button_typesett_save, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonpre_clear_typesett, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addComponent(jButton_update_Bookin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton_typesett_delete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton_Refresh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 105, Short.MAX_VALUE)
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.add(jPanel2, java.awt.BorderLayout.EAST);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel3.setPreferredSize(new java.awt.Dimension(874, 499));

        jLabel6.setFont(new java.awt.Font("Copperplate Gothic Light", 1, 11)); // NOI18N
        jLabel6.setText("Item ID");

        jComboBox_pre_type_itemid.setEditable(true);
        jComboBox_pre_type_itemid.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "*Select ItemID*", "A001", "A002", "B001", " " }));
        jComboBox_pre_type_itemid.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                jComboBox_pre_type_itemidPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        jComboBox_pre_type_itemid.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jComboBox_pre_type_itemidKeyReleased(evt);
            }
        });

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));

        jComboBoxpre_type_fontstyle.setEditable(true);
        jComboBoxpre_type_fontstyle.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "*Select Font Size*", "MS", "Times new Romen", "FB", "OLD" }));

        jLabel22.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 11)); // NOI18N
        jLabel22.setText("Original copy issue date");

        jLabel24.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 11)); // NOI18N
        jLabel24.setText("Font Size");

        jComboBoxpre_type_fontsize.setEditable(true);
        jComboBoxpre_type_fontsize.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "*Select font size*", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18" }));

        jLabel23.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 11)); // NOI18N
        jLabel23.setText("Hand over person");

        jXDatePickerpre_copyissudate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jXDatePickerpre_copyissudateActionPerformed(evt);
            }
        });

        jLabel29.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 11)); // NOI18N
        jLabel29.setText("Font Style");

        jLabel25.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 11)); // NOI18N
        jLabel25.setText("Typist ID");

        jLabel26.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 11)); // NOI18N
        jLabel26.setText("Typist Name");

        jCombopre_typistid.setEditable(true);
        jCombopre_typistid.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "*Select TypistID*", "ET007", "ET003", "ET017" }));

        jComboBoxpr_typist_name.setEditable(true);
        jComboBoxpr_typist_name.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "*Select Name*", "J.C.Wickramanayaka", "L.M Samarathunga", "A.I Wirakkodi" }));

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel23)
                    .addComponent(jLabel22))
                .addGap(18, 18, 18)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jXDatePickerpre_copyissudate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextFieldpre_handoverper))
                .addGap(51, 51, 51)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel29)
                    .addComponent(jLabel24))
                .addGap(27, 27, 27)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBoxpre_type_fontstyle, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxpre_type_fontsize, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(jLabel25)
                        .addGap(18, 18, 18)
                        .addComponent(jCombopre_typistid, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(jLabel26)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBoxpr_typist_name, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(39, 39, 39))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(jXDatePickerpre_copyissudate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxpre_type_fontstyle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCombopre_typistid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextFieldpre_handoverper, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBoxpre_type_fontsize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBoxpr_typist_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel27.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 11)); // NOI18N
        jLabel27.setText("Start Date & Time");

        jLabel28.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 11)); // NOI18N
        jLabel28.setText("Planning  Date & Time");

        jTablepre_typesett.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTablepre_typesett.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Item ID", "Typist", "Font Style", "Font Size", "Start date", "Finishing date", "Original Copy Date", "Hand Over person"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTablepre_typesett.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablepre_typesettMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTablepre_typesett);

        jButton_typesett_search.setBackground(new java.awt.Color(0, 0, 0));
        jButton_typesett_search.setFont(new java.awt.Font("Bell MT", 1, 11)); // NOI18N
        jButton_typesett_search.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/finishstock/Search-icon.png"))); // NOI18N
        jButton_typesett_search.setText("Search");
        jButton_typesett_search.setPreferredSize(new java.awt.Dimension(100, 35));
        jButton_typesett_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_typesett_searchActionPerformed(evt);
            }
        });

        jButton_add_pre_type.setBackground(new java.awt.Color(51, 51, 51));
        jButton_add_pre_type.setFont(new java.awt.Font("Bell MT", 1, 11)); // NOI18N
        jButton_add_pre_type.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/finishstock/Button-Add-icon.png"))); // NOI18N
        jButton_add_pre_type.setText("Add");
        jButton_add_pre_type.setPreferredSize(new java.awt.Dimension(100, 35));
        jButton_add_pre_type.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_add_pre_typeActionPerformed(evt);
            }
        });

        jButton_Demo_preType.setText("Demo");
        jButton_Demo_preType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_Demo_preTypeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGap(41, 41, 41)
                            .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jXDatePickerpre_type_startdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(28, 28, 28)
                            .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jXDatePickerpre_type_finishdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(151, 151, 151)
                            .addComponent(jButton_add_pre_type, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(28, 28, 28))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel6)
                                    .addGap(18, 18, 18)
                                    .addComponent(jComboBox_pre_type_itemid, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(26, 26, 26)
                                    .addComponent(jButton_typesett_search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(428, 428, 428)
                                    .addComponent(jButton_Demo_preType))
                                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 830, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(jComboBox_pre_type_itemid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton_typesett_search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton_Demo_preType))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jXDatePickerpre_type_finishdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jXDatePickerpre_type_startdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_add_pre_type, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 303, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.add(jPanel3, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("           Type Setting        ", jPanel1);

        jPanel5.setLayout(new java.awt.BorderLayout());

        jPanel6.setBackground(new java.awt.Color(153, 153, 153));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel6.setPreferredSize(new java.awt.Dimension(157, 507));

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jButton17.setBackground(new java.awt.Color(0, 0, 0));
        jButton17.setFont(new java.awt.Font("Bell MT", 1, 11)); // NOI18N
        jButton17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/prepress/print-icon.png"))); // NOI18N
        jButton17.setText("Print");
        jButton17.setPreferredSize(new java.awt.Dimension(100, 35));

        jButton11.setBackground(new java.awt.Color(0, 0, 0));
        jButton11.setFont(new java.awt.Font("Bell MT", 1, 10)); // NOI18N
        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/prepress/custom-reports-icon.png"))); // NOI18N
        jButton11.setText("<html>Generate<br>Report</html>");
        jButton11.setPreferredSize(new java.awt.Dimension(100, 35));

        jButton18.setBackground(new java.awt.Color(0, 0, 0));
        jButton18.setFont(new java.awt.Font("Bell MT", 1, 11)); // NOI18N
        jButton18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/prepress/Windows-Close-Program-icon.png"))); // NOI18N
        jButton18.setText("Close");
        jButton18.setPreferredSize(new java.awt.Dimension(100, 35));
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });

        jButton_save_Bookin.setBackground(new java.awt.Color(0, 0, 0));
        jButton_save_Bookin.setFont(new java.awt.Font("Bell MT", 1, 11)); // NOI18N
        jButton_save_Bookin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/prepress/Save-icon.png"))); // NOI18N
        jButton_save_Bookin.setText("Save");
        jButton_save_Bookin.setPreferredSize(new java.awt.Dimension(100, 35));
        jButton_save_Bookin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_save_BookinActionPerformed(evt);
            }
        });

        jButton_Bookin_update.setBackground(new java.awt.Color(0, 0, 0));
        jButton_Bookin_update.setFont(new java.awt.Font("Bell MT", 1, 11)); // NOI18N
        jButton_Bookin_update.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/prepress/Actions-edit-undo-icon.png"))); // NOI18N
        jButton_Bookin_update.setText("Update");
        jButton_Bookin_update.setPreferredSize(new java.awt.Dimension(100, 35));
        jButton_Bookin_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_Bookin_updateActionPerformed(evt);
            }
        });

        jButton_Delete_Bookin.setBackground(new java.awt.Color(0, 0, 0));
        jButton_Delete_Bookin.setFont(new java.awt.Font("Bell MT", 1, 11)); // NOI18N
        jButton_Delete_Bookin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/prepress/Button-Delete-icon.png"))); // NOI18N
        jButton_Delete_Bookin.setText("Delete");
        jButton_Delete_Bookin.setPreferredSize(new java.awt.Dimension(100, 35));
        jButton_Delete_Bookin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_Delete_BookinActionPerformed(evt);
            }
        });

        jButton_clear_BookIn.setBackground(new java.awt.Color(0, 0, 0));
        jButton_clear_BookIn.setFont(new java.awt.Font("Bell MT", 1, 11)); // NOI18N
        jButton_clear_BookIn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/finishstock/Actions-edit-clear-locationbar-rtl-icon.png"))); // NOI18N
        jButton_clear_BookIn.setText("Clear");
        jButton_clear_BookIn.setPreferredSize(new java.awt.Dimension(100, 35));
        jButton_clear_BookIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_clear_BookInActionPerformed(evt);
            }
        });

        jButton_Refresh_BookIn.setBackground(new java.awt.Color(51, 51, 51));
        jButton_Refresh_BookIn.setFont(new java.awt.Font("Bell MT", 1, 11)); // NOI18N
        jButton_Refresh_BookIn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/finishstock/refresh.png"))); // NOI18N
        jButton_Refresh_BookIn.setText("Refresh");
        jButton_Refresh_BookIn.setPreferredSize(new java.awt.Dimension(100, 35));
        jButton_Refresh_BookIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_Refresh_BookInActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton_Bookin_update, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_save_Bookin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_Delete_Bookin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_clear_BookIn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_Refresh_BookIn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jButton_save_Bookin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton_clear_BookIn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton_Bookin_update, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(jButton_Delete_Bookin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton_Refresh_BookIn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
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
        jPanel8.setPreferredSize(new java.awt.Dimension(874, 499));

        jLabel9.setFont(new java.awt.Font("Copperplate Gothic Light", 1, 11)); // NOI18N
        jLabel9.setText("Item ID");

        jComboBox4_Bookin_Itemid.setEditable(true);
        jComboBox4_Bookin_Itemid.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "*Select ItemID*", "A001", "A002", "B001", " " }));
        jComboBox4_Bookin_Itemid.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jComboBox4_Bookin_ItemidKeyReleased(evt);
            }
        });

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));

        jLabel17.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 11)); // NOI18N
        jLabel17.setText("Paper Meterial");

        jComboBox9.setEditable(true);
        jComboBox9.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jComboBox8_Bookin_pa2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel20.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 11)); // NOI18N
        jLabel20.setText("Special Paper");

        jLabel18.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 11)); // NOI18N
        jLabel18.setText("Paper 1");

        jComboBox7_Bookin_Pa1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jComboBox5_Bookin_Bsize.setEditable(true);
        jComboBox5_Bookin_Bsize.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "25*25ft", "20*20ft", "18*18ft", "210*297ft" }));
        jComboBox5_Bookin_Bsize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox5_Bookin_BsizeActionPerformed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 11)); // NOI18N
        jLabel19.setText("Paper 2");

        jLabel10.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 11)); // NOI18N
        jLabel10.setText("Paper Size");

        jLabel11.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 11)); // NOI18N
        jLabel11.setText("Number of pages");

        jTextField2_Bookin_Num.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2_Bookin_NumActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 11)); // NOI18N
        jLabel12.setText("Colours");

        jComboBox6_Bookin_colo.setEditable(true);
        jComboBox6_Bookin_colo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "*Select Color*", "Black", "White", "Red", "Blue" }));
        jComboBox6_Bookin_colo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox6_Bookin_coloActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 11)); // NOI18N
        jLabel13.setText("Colour catogory");

        jComboBox20_Bookin_ColoCa.setEditable(true);
        jComboBox20_Bookin_ColoCa.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "*Select ColorCatagory*", "Catagory 1", "Catagory 2" }));
        jComboBox20_Bookin_ColoCa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox20_Bookin_ColoCaActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 11)); // NOI18N
        jLabel16.setText("Art & Graphic Suplied");

        jCheckBox3_Bookin_photo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox3_Bookin_photoActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 11)); // NOI18N
        jLabel15.setText("PhotoGraph Suplied");

        jCheckBox4_Bookin_net.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox4_Bookin_netActionPerformed(evt);
            }
        });

        jLabel48.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 11)); // NOI18N
        jLabel48.setText("Use internet for graphic");

        jCheckBox8_Bookin_art.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox8_Bookin_artActionPerformed(evt);
            }
        });

        jButton_Add_BookIn.setBackground(new java.awt.Color(0, 0, 0));
        jButton_Add_BookIn.setFont(new java.awt.Font("Bell MT", 1, 11)); // NOI18N
        jButton_Add_BookIn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/finishstock/Button-Add-icon.png"))); // NOI18N
        jButton_Add_BookIn.setText("Add");
        jButton_Add_BookIn.setPreferredSize(new java.awt.Dimension(100, 35));
        jButton_Add_BookIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_Add_BookInActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addGap(57, 57, 57)
                                .addComponent(jLabel20))
                            .addComponent(jLabel11))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField2_Bookin_Num, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox9, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(22, 22, 22)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCheckBox4_Bookin_net)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 168, Short.MAX_VALUE)
                                .addComponent(jButton_Add_BookIn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(35, 35, 35))
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel13Layout.createSequentialGroup()
                                        .addComponent(jLabel13)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jComboBox20_Bookin_ColoCa, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel13Layout.createSequentialGroup()
                                        .addGap(21, 21, 21)
                                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(14, 14, 14)
                                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jCheckBox3_Bookin_photo)
                                            .addComponent(jCheckBox8_Bookin_art)))
                                    .addGroup(jPanel13Layout.createSequentialGroup()
                                        .addGap(44, 44, 44)
                                        .addComponent(jLabel12)
                                        .addGap(18, 18, 18)
                                        .addComponent(jComboBox6_Bookin_colo, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel10))
                            .addComponent(jLabel17))
                        .addGap(7, 7, 7)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jComboBox5_Bookin_Bsize, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(jComboBox7_Bookin_Pa1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jComboBox8_Bookin_pa2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jComboBox5_Bookin_Bsize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17)
                            .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jComboBox7_Bookin_Pa1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel18)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox8_Bookin_pa2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel20)
                            .addComponent(jComboBox9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jTextField2_Bookin_Num, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(jComboBox6_Bookin_colo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(jComboBox20_Bookin_ColoCa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jCheckBox8_Bookin_art)
                            .addComponent(jLabel16))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel15)
                            .addComponent(jCheckBox3_Bookin_photo))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel48)
                                .addComponent(jCheckBox4_Bookin_net))
                            .addComponent(jButton_Add_BookIn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 304, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 136, Short.MAX_VALUE)
        );

        jTable3_Bookin.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Item ID", "Paper size", "Paper Meterial", "No of pages", "Colors"
            }
        ));
        jTable3_Bookin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable3_BookinMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTable3_Bookin);

        jButton_search_Bookin.setBackground(new java.awt.Color(0, 0, 0));
        jButton_search_Bookin.setFont(new java.awt.Font("Bell MT", 1, 11)); // NOI18N
        jButton_search_Bookin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/finishstock/Search-icon.png"))); // NOI18N
        jButton_search_Bookin.setText("Search");
        jButton_search_Bookin.setPreferredSize(new java.awt.Dimension(100, 35));
        jButton_search_Bookin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_search_BookinActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jLabel9)
                        .addGap(39, 39, 39)
                        .addComponent(jComboBox4_Bookin_Itemid, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(jButton_search_Bookin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jComboBox4_Bookin_Itemid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_search_Bookin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.add(jPanel8, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("            Book inside         ", jPanel5);

        jPanel9.setLayout(new java.awt.BorderLayout());

        jPanel10.setBackground(new java.awt.Color(153, 153, 153));
        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel10.setPreferredSize(new java.awt.Dimension(157, 507));

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jButton_coverSave.setBackground(new java.awt.Color(0, 0, 0));
        jButton_coverSave.setFont(new java.awt.Font("Bell MT", 1, 11)); // NOI18N
        jButton_coverSave.setForeground(new java.awt.Color(51, 51, 51));
        jButton_coverSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/prepress/Save-icon.png"))); // NOI18N
        jButton_coverSave.setText("Save");
        jButton_coverSave.setPreferredSize(new java.awt.Dimension(100, 35));
        jButton_coverSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_coverSaveActionPerformed(evt);
            }
        });

        jButton26.setBackground(new java.awt.Color(0, 0, 0));
        jButton26.setFont(new java.awt.Font("Bell MT", 1, 11)); // NOI18N
        jButton26.setForeground(new java.awt.Color(51, 51, 51));
        jButton26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/prepress/Actions-edit-undo-icon.png"))); // NOI18N
        jButton26.setText("Update");
        jButton26.setPreferredSize(new java.awt.Dimension(100, 35));

        jButton_delete_cover.setBackground(new java.awt.Color(0, 0, 0));
        jButton_delete_cover.setFont(new java.awt.Font("Bell MT", 1, 11)); // NOI18N
        jButton_delete_cover.setForeground(new java.awt.Color(51, 51, 51));
        jButton_delete_cover.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/prepress/Button-Delete-icon.png"))); // NOI18N
        jButton_delete_cover.setText("Delete");
        jButton_delete_cover.setPreferredSize(new java.awt.Dimension(100, 35));
        jButton_delete_cover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_delete_coverActionPerformed(evt);
            }
        });

        jButton12.setBackground(new java.awt.Color(0, 0, 0));
        jButton12.setFont(new java.awt.Font("Bell MT", 1, 10)); // NOI18N
        jButton12.setForeground(new java.awt.Color(51, 51, 51));
        jButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/prepress/custom-reports-icon.png"))); // NOI18N
        jButton12.setText("<html>Generate<br>Report</html>");
        jButton12.setPreferredSize(new java.awt.Dimension(100, 35));

        jButton28.setBackground(new java.awt.Color(0, 0, 0));
        jButton28.setFont(new java.awt.Font("Bell MT", 1, 11)); // NOI18N
        jButton28.setForeground(new java.awt.Color(51, 51, 51));
        jButton28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/prepress/print-icon.png"))); // NOI18N
        jButton28.setText("Print");
        jButton28.setPreferredSize(new java.awt.Dimension(100, 35));

        jButton29.setBackground(new java.awt.Color(0, 0, 0));
        jButton29.setFont(new java.awt.Font("Bell MT", 1, 11)); // NOI18N
        jButton29.setForeground(new java.awt.Color(51, 51, 51));
        jButton29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/prepress/Windows-Close-Program-icon.png"))); // NOI18N
        jButton29.setText("Close");
        jButton29.setPreferredSize(new java.awt.Dimension(100, 35));
        jButton29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton29ActionPerformed(evt);
            }
        });

        jButtonpre_clear.setBackground(new java.awt.Color(0, 0, 0));
        jButtonpre_clear.setFont(new java.awt.Font("Bell MT", 1, 11)); // NOI18N
        jButtonpre_clear.setForeground(new java.awt.Color(51, 51, 51));
        jButtonpre_clear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/finishstock/Actions-edit-clear-locationbar-rtl-icon.png"))); // NOI18N
        jButtonpre_clear.setText("Clear");
        jButtonpre_clear.setPreferredSize(new java.awt.Dimension(100, 35));
        jButtonpre_clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonpre_clearActionPerformed(evt);
            }
        });

        jButton_Refresh_BookIn1.setBackground(new java.awt.Color(51, 51, 51));
        jButton_Refresh_BookIn1.setFont(new java.awt.Font("Bell MT", 1, 11)); // NOI18N
        jButton_Refresh_BookIn1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/finishstock/refresh.png"))); // NOI18N
        jButton_Refresh_BookIn1.setText("Refresh");
        jButton_Refresh_BookIn1.setPreferredSize(new java.awt.Dimension(100, 35));
        jButton_Refresh_BookIn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_Refresh_BookIn1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_coverSave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_delete_cover, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonpre_clear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel11Layout.createSequentialGroup()
                    .addGap(14, 14, 14)
                    .addComponent(jButton_Refresh_BookIn1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(15, Short.MAX_VALUE)))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jButton_coverSave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonpre_clear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton_delete_cover, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 122, Short.MAX_VALUE)
                .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel11Layout.createSequentialGroup()
                    .addGap(220, 220, 220)
                    .addComponent(jButton_Refresh_BookIn1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(220, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel9.add(jPanel10, java.awt.BorderLayout.EAST);

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel12.setForeground(new java.awt.Color(51, 51, 51));
        jPanel12.setPreferredSize(new java.awt.Dimension(874, 499));

        jLabel31.setFont(new java.awt.Font("Copperplate Gothic Light", 1, 11)); // NOI18N
        jLabel31.setText("Item ID");

        jComboBoxpre_cover_itemID.setEditable(true);
        jComboBoxpre_cover_itemID.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "A001", "A002", "B001" }));

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));

        jComboBoxpre_cover_size.setEditable(true);
        jComboBoxpre_cover_size.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "25*25ft", "20*20ft", "18*18ft" }));
        jComboBoxpre_cover_size.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxpre_cover_sizeActionPerformed(evt);
            }
        });

        jLabel47.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 11)); // NOI18N
        jLabel47.setText("Using sofware 2");

        jLabel44.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 11)); // NOI18N
        jLabel44.setText("Using sofware 1");

        jLabel33.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 11)); // NOI18N
        jLabel33.setText("Paper Type");

        jLabel34.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 11)); // NOI18N
        jLabel34.setText("Colours");

        jCheckBox6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox6ActionPerformed(evt);
            }
        });

        jComboBox_soft.setEditable(true);
        jComboBox_soft.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Coral Drow", "Photo Shop" }));

        jCheckBox5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox5ActionPerformed(evt);
            }
        });

        jLabel38.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 11)); // NOI18N
        jLabel38.setText("Art & Graphic Suplied");

        jLabel43.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 11)); // NOI18N
        jLabel43.setText("Second Designer");

        jLabel37.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 11)); // NOI18N
        jLabel37.setText("Main Designer");

        jLabel41.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 11)); // NOI18N
        jLabel41.setText("Cover Desin start date");

        jComboBoxpre_colors.setEditable(true);
        jComboBoxpre_colors.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "color1", "color2", "color3" }));
        jComboBoxpre_colors.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxpre_colorsActionPerformed(evt);
            }
        });

        jCheckBox7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox7ActionPerformed(evt);
            }
        });

        jComboBox16_maindesiner.setEditable(true);
        jComboBox16_maindesiner.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "A.B.Jayawardhana", "p.k Senanayaka", "j.b. Kumara" }));

        jComboBox_paper_type.setEditable(true);
        jComboBox_paper_type.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "papertype1", "papertype2", "papertype3", " " }));

        jLabel32.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 11)); // NOI18N
        jLabel32.setText("Cover Size");

        jLabel46.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 11)); // NOI18N
        jLabel46.setText("Use internet for graphic");

        jLabel39.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 11)); // NOI18N
        jLabel39.setText("PhotoGraph Suplied");

        jComboBox17.setEditable(true);
        jComboBox17.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "A.B.Jayawardhana", "p.k Senanayaka", "j.b. Kumara" }));

        jComboBox19.setEditable(true);
        jComboBox19.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Coral Drow", "Photo Shop" }));

        jLabel14.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 11)); // NOI18N
        jLabel14.setText("Colour catogory");

        jComboBoxpre_colorcat.setEditable(true);
        jComboBoxpre_colorcat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "color cat1", "color cat2", "color cat3", " " }));
        jComboBoxpre_colorcat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxpre_colorcatActionPerformed(evt);
            }
        });

        jLabel42.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 11)); // NOI18N
        jLabel42.setText("Cover Desin finish date");

        jButton_cover_Add.setBackground(new java.awt.Color(0, 0, 0));
        jButton_cover_Add.setFont(new java.awt.Font("Bell MT", 1, 11)); // NOI18N
        jButton_cover_Add.setForeground(new java.awt.Color(51, 51, 51));
        jButton_cover_Add.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/finishstock/Button-Add-icon.png"))); // NOI18N
        jButton_cover_Add.setText("Add");
        jButton_cover_Add.setPreferredSize(new java.awt.Dimension(100, 35));
        jButton_cover_Add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_cover_AddActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel18Layout.createSequentialGroup()
                                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel18Layout.createSequentialGroup()
                                        .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jComboBox_paper_type, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel18Layout.createSequentialGroup()
                                        .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jComboBoxpre_cover_size, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(61, 61, 61)
                                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel37)
                                    .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jCheckBox6)
                                        .addGroup(jPanel18Layout.createSequentialGroup()
                                            .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(30, 30, 30)
                                            .addComponent(jCheckBox5))
                                        .addComponent(jCheckBox7)))
                                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel18Layout.createSequentialGroup()
                                        .addGap(57, 57, 57)
                                        .addComponent(jLabel34))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
                                        .addComponent(jLabel14))))))
                    .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel18Layout.createSequentialGroup()
                                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBoxpre_colorcat, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBoxpre_colors, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel42)
                                    .addComponent(jLabel41))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jXDatePickercover_finishdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jXDatePickercover_statdate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel18Layout.createSequentialGroup()
                                        .addComponent(jComboBox17, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel47))
                                    .addGroup(jPanel18Layout.createSequentialGroup()
                                        .addComponent(jComboBox16_maindesiner, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel44)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel18Layout.createSequentialGroup()
                                        .addComponent(jComboBox_soft, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(8, 8, 8))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                                        .addComponent(jComboBox19, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addContainerGap())))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton_cover_Add, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40))))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox16_maindesiner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel32, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel37, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jComboBox_soft, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel44)
                                .addComponent(jComboBoxpre_cover_size, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel18Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel33)
                                    .addComponent(jComboBox_paper_type, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel18Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jComboBox19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel47)))
                            .addGroup(jPanel18Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel43)
                                    .addComponent(jComboBox17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(8, 8, 8)))
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jComboBoxpre_colors, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel18Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel38)
                                    .addComponent(jCheckBox5)))
                            .addGroup(jPanel18Layout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addComponent(jLabel34))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel41)
                                .addComponent(jXDatePickercover_statdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel39)
                            .addComponent(jCheckBox6)
                            .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel14)
                                .addComponent(jComboBoxpre_colorcat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel42)
                                .addComponent(jXDatePickercover_finishdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel46)
                            .addComponent(jCheckBox7)
                            .addComponent(jButton_cover_Add, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
        );

        jTablepre_cover.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Item ID", "Cover Size", "Paper Type", "Colors", "Designer", "Softwares"
            }
        ));
        jScrollPane2.setViewportView(jTablepre_cover);

        jButton_pre_cover.setBackground(new java.awt.Color(0, 0, 0));
        jButton_pre_cover.setFont(new java.awt.Font("Bell MT", 1, 11)); // NOI18N
        jButton_pre_cover.setForeground(new java.awt.Color(51, 51, 51));
        jButton_pre_cover.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/finishstock/Search-icon.png"))); // NOI18N
        jButton_pre_cover.setText("Search");
        jButton_pre_cover.setPreferredSize(new java.awt.Dimension(100, 35));
        jButton_pre_cover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_pre_coverActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane2)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel31)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBoxpre_cover_itemID, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton_pre_cover, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxpre_cover_itemID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31)
                    .addComponent(jButton_pre_cover, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(197, 197, 197))
        );

        jPanel9.add(jPanel12, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("             Cover             ", jPanel9);

        add(jTabbedPane1, java.awt.BorderLayout.PAGE_START);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jComboBox5_Bookin_BsizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox5_Bookin_BsizeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox5_Bookin_BsizeActionPerformed

    private void jTextField2_Bookin_NumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2_Bookin_NumActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2_Bookin_NumActionPerformed

    private void jComboBox6_Bookin_coloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox6_Bookin_coloActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox6_Bookin_coloActionPerformed

    private void jCheckBox4_Bookin_netActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox4_Bookin_netActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox4_Bookin_netActionPerformed

    private void jCheckBox3_Bookin_photoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox3_Bookin_photoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox3_Bookin_photoActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton18ActionPerformed

    private void jButton29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton29ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton29ActionPerformed

    private void jComboBoxpre_cover_sizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxpre_cover_sizeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxpre_cover_sizeActionPerformed

    private void jComboBoxpre_colorsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxpre_colorsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxpre_colorsActionPerformed

    private void jCheckBox5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox5ActionPerformed

    private void jCheckBox6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox6ActionPerformed

    private void jCheckBox7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox7ActionPerformed

    private void jComboBox20_Bookin_ColoCaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox20_Bookin_ColoCaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox20_Bookin_ColoCaActionPerformed

    private void jCheckBox8_Bookin_artActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox8_Bookin_artActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox8_Bookin_artActionPerformed

    private void jComboBoxpre_colorcatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxpre_colorcatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxpre_colorcatActionPerformed

    private void button_typesett_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_typesett_saveActionPerformed
//        try{
//          String sql = "insert in to type setting"  
        int reply = JOptionPane.showConfirmDialog(null, "Do You Want to save this? ");

        if (reply == JOptionPane.YES_OPTION) {
            insert_typeset();
        }


    }//GEN-LAST:event_button_typesett_saveActionPerformed

    private void jButton_cover_AddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_cover_AddActionPerformed

        String ItemID = jComboBoxpre_cover_itemID.getSelectedItem().toString();
        String cover_size = jComboBoxpre_cover_size.getSelectedItem().toString();
        String paper_type = jComboBox_paper_type.getSelectedItem().toString();
        String colors = jComboBoxpre_colors.getSelectedItem().toString();
        String desiner = jComboBox16_maindesiner.getSelectedItem().toString();
        String software = jComboBox_soft.getSelectedItem().toString();

        String sql = "select itemID from items where itemID = '" + ItemID + "'";

        dbconn = dbmanager.connect();
        try {
            st = dbconn.createStatement();
            rs = st.executeQuery(sql);
            if (rs.next()) {

                Vector v = new Vector();
                v.add(ItemID);
                v.add(cover_size);
                v.add(paper_type);
                v.add(colors);
                v.add(desiner);
                v.add(software);

                DefaultTableModel dtm = (DefaultTableModel) jTablepre_cover.getModel();

                if (mode == false) {
                    dtm.setRowCount(0);
                    mode = true;
                }

                dtm.addRow(v);
                jTablepre_cover.setModel(dtm);

            }
        } catch (SQLException e) {

        }
    }//GEN-LAST:event_jButton_cover_AddActionPerformed

    private void jButton_Add_BookInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_Add_BookInActionPerformed
        String ItemID = jComboBox4_Bookin_Itemid.getSelectedItem().toString();
        String Paper_size = jComboBox5_Bookin_Bsize.getSelectedItem().toString();
        String paper_meterial = jComboBox7_Bookin_Pa1.getSelectedItem().toString();
        String no_of_pages = jTextField2_Bookin_Num.getText().toString();
        String colors = jComboBox6_Bookin_colo.getSelectedItem().toString();

        String sql2 = "select itemID from items where itemID ='" + ItemID + "'";

        dbconn = dbmanager.connect();
        try {
            st = dbconn.createStatement();
            rs = st.executeQuery(sql2);
            if (rs.next()) {
                Vector v = new Vector();
                v.add(ItemID);
                v.add(Paper_size);
                v.add(paper_meterial);
                v.add(no_of_pages);
                v.add(colors);

                DefaultTableModel dtm = (DefaultTableModel) jTable3_Bookin.getModel();
                if (mode == false) {
                    dtm.setRowCount(0);
                    mode = true;
                }

                dtm.addRow(v);
                jTable3_Bookin.setModel(dtm);
            }

        } catch (SQLException e) {

        }


    }//GEN-LAST:event_jButton_Add_BookInActionPerformed

    private void jButton_add_pre_typeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_add_pre_typeActionPerformed

        String ItemID = jComboBox_pre_type_itemid.getSelectedItem().toString();
        String typistID = jCombopre_typistid.getSelectedItem().toString();
        String font_style = jComboBoxpre_type_fontstyle.getSelectedItem().toString();
        String font_size = jComboBoxpre_type_fontsize.getSelectedItem().toString();
        String start_date = jXDatePickerpre_type_startdate.getEditor().getText();
        String finish_date = jXDatePickerpre_type_finishdate.getEditor().getText();
        String Originaldate = jXDatePickerpre_copyissudate.getEditor().getText();
        String handper = jTextFieldpre_handoverper.getText().toString();

        String sql1 = "select itemID from items where itemID = '" + ItemID + "'";

        dbconn = dbmanager.connect();
        try {
            st = dbconn.createStatement();
            rs = st.executeQuery(sql1);
            if (rs.next()) {

                Vector v = new Vector();
                v.add(ItemID);
                v.add(typistID);
                v.add(font_style);
                v.add(font_size);
                v.add(start_date);
                v.add(finish_date);
                v.add(Originaldate);
                v.add(handper);

                DefaultTableModel dtm = (DefaultTableModel) jTablepre_typesett.getModel();

                if (mode == false) {
                    dtm.setRowCount(0);
                    mode = true;
                }

                dtm.addRow(v);
                jTablepre_typesett.setModel(dtm);

            }
        } catch (SQLException e) {

        }
    }//GEN-LAST:event_jButton_add_pre_typeActionPerformed

    private void jButton_coverSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_coverSaveActionPerformed
        int reply = JOptionPane.showConfirmDialog(null, "Do You Want to save this? ");

        if (reply == JOptionPane.YES_OPTION) {
            insertcover();
        }
    }//GEN-LAST:event_jButton_coverSaveActionPerformed

    private void jButtonpre_clear_typesettActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonpre_clear_typesettActionPerformed
        resetadd_typeset();
        resettable_typesett();
    }//GEN-LAST:event_jButtonpre_clear_typesettActionPerformed

    private void jButton_clear_BookInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_clear_BookInActionPerformed
        resetadd_bookinside();
        resettable_bookinside();
    }//GEN-LAST:event_jButton_clear_BookInActionPerformed

    private void jButtonpre_clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonpre_clearActionPerformed
        resetadd_cover();
        resettable_cover();
    }//GEN-LAST:event_jButtonpre_clearActionPerformed

    private void jButton_update_BookinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_update_BookinActionPerformed
        updateTypesetting();
    }//GEN-LAST:event_jButton_update_BookinActionPerformed

    private void jButton_typesett_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_typesett_deleteActionPerformed
        int reply = JOptionPane.showConfirmDialog(null, "Do You Want to Delete this? ");

        if (reply == JOptionPane.YES_OPTION) {
            delete_type_sett();
        }

    }//GEN-LAST:event_jButton_typesett_deleteActionPerformed

    private void jButton_typesett_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_typesett_searchActionPerformed
        if (jComboBox_pre_type_itemid.getSelectedItem().toString().equals("")) {
            JOptionPane.showMessageDialog(null, "ghjkl");
        } else {
            newSearch(jComboBox_pre_type_itemid.getSelectedItem().toString());
        }
    }//GEN-LAST:event_jButton_typesett_searchActionPerformed

    private void jTablepre_typesettMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablepre_typesettMouseClicked
        int row = jTablepre_typesett.getSelectedRow();

        String itemid = (jTablepre_typesett.getModel().getValueAt(row, 0).toString());
        jComboBox_pre_type_itemid.setSelectedItem(itemid);
        String typist = (jTablepre_typesett.getModel().getValueAt(row, 1).toString());
        jCombopre_typistid.setSelectedItem(typist);
        String fontst = (jTablepre_typesett.getModel().getValueAt(row, 2).toString());
        jComboBoxpre_type_fontstyle.setSelectedItem(fontst);

        String fontsi = (jTablepre_typesett.getModel().getValueAt(row, 3).toString());
        jComboBoxpre_type_fontsize.setSelectedItem(fontsi);
        String startdate = (jTablepre_typesett.getModel().getValueAt(row, 4).toString());
        jXDatePickerpre_type_startdate.getEditor().setText(startdate);
        String enddate = (jTablepre_typesett.getModel().getValueAt(row, 5).toString());
        jXDatePickerpre_type_finishdate.getEditor().setText(enddate);
        String copydate = (jTablepre_typesett.getModel().getValueAt(row, 6).toString());
        jXDatePickerpre_copyissudate.getEditor().setText(copydate);
        String handpers = (jTablepre_typesett.getModel().getValueAt(row, 7).toString());
        jTextFieldpre_handoverper.setText(handpers);


    }//GEN-LAST:event_jTablepre_typesettMouseClicked

    private void jXDatePickerpre_copyissudateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jXDatePickerpre_copyissudateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jXDatePickerpre_copyissudateActionPerformed

    private void jButton_Demo_preTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_Demo_preTypeActionPerformed
        jComboBox_pre_type_itemid.setSelectedItem("A001");
        jComboBoxpre_type_fontstyle.setSelectedItem("Times new Romen");
        jComboBoxpre_type_fontsize.setSelectedItem("10");
        jCombopre_typistid.setSelectedItem("ET007");
        jComboBoxpr_typist_name.setSelectedItem("L.M Samarathunga");
        jTextFieldpre_handoverper.setText("A.I Gunasena");
    }//GEN-LAST:event_jButton_Demo_preTypeActionPerformed

    private void jButton_RefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_RefreshActionPerformed
        fillTable();
    }//GEN-LAST:event_jButton_RefreshActionPerformed

    private void jButton_Refresh_BookInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_Refresh_BookInActionPerformed
        fillTable_Book();
    }//GEN-LAST:event_jButton_Refresh_BookInActionPerformed

    private void jButton_save_BookinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_save_BookinActionPerformed

        int reply = JOptionPane.showConfirmDialog(null, "Do You Want to save this? ");

        if (reply == JOptionPane.YES_OPTION) {
            insertBookinside();

        }
    }//GEN-LAST:event_jButton_save_BookinActionPerformed

    private void jButton_Bookin_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_Bookin_updateActionPerformed

        updateBookInside();


    }//GEN-LAST:event_jButton_Bookin_updateActionPerformed

    private void jButton_Delete_BookinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_Delete_BookinActionPerformed

        int reply = JOptionPane.showConfirmDialog(null, "Do You Want to Delete this? ");

        if (reply == JOptionPane.YES_OPTION) {
            delete_Book_Inside();
        }
    }//GEN-LAST:event_jButton_Delete_BookinActionPerformed

    private void jButton_search_BookinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_search_BookinActionPerformed
        if (jComboBox4_Bookin_Itemid.getSelectedItem().toString().equals("")) {
            JOptionPane.showMessageDialog(null, "ghjkl");
        } else {
            BookSearch(jComboBox4_Bookin_Itemid.getSelectedItem().toString());
        }
    }//GEN-LAST:event_jButton_search_BookinActionPerformed

    private void jTable3_BookinMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable3_BookinMouseClicked
        int row = jTable3_Bookin.getSelectedRow();

        String itemid = (jTable3_Bookin.getModel().getValueAt(row, 0).toString());
        jComboBox4_Bookin_Itemid.setSelectedItem(itemid);
        String papersize = (jTable3_Bookin.getModel().getValueAt(row, 1).toString());
        jComboBox5_Bookin_Bsize.setSelectedItem(papersize);
        String paperMeterial = (jTable3_Bookin.getModel().getValueAt(row, 2).toString());
        jComboBox7_Bookin_Pa1.setSelectedItem(paperMeterial);

        String noofpages = (jTable3_Bookin.getModel().getValueAt(row, 3).toString());
        jTextField2_Bookin_Num.setText(noofpages);
        String colors = (jTable3_Bookin.getModel().getValueAt(row, 4).toString());
        jComboBox6_Bookin_colo.setSelectedItem(colors);

    }//GEN-LAST:event_jTable3_BookinMouseClicked

    private void jButton_Refresh_BookIn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_Refresh_BookIn1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton_Refresh_BookIn1ActionPerformed

    private void jButton_delete_coverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_delete_coverActionPerformed
        int reply = JOptionPane.showConfirmDialog(null, "Do You Want to Delete this? ");

        if (reply == JOptionPane.YES_OPTION) {
            delete_cover();
        }
    }//GEN-LAST:event_jButton_delete_coverActionPerformed

    private void jButton_pre_coverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_pre_coverActionPerformed

        if (jComboBoxpre_cover_itemID.getSelectedItem().toString().equals("")) {
            JOptionPane.showMessageDialog(null, "ghjkl");
        } else {
            //new cover 
        }
        //search(jComboBoxpre_cover_itemID.getSelectedItem().toString());
    }//GEN-LAST:event_jButton_pre_coverActionPerformed

    private void jComboBox_pre_type_itemidPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_jComboBox_pre_type_itemidPopupMenuWillBecomeInvisible
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox_pre_type_itemidPopupMenuWillBecomeInvisible

    private void jComboBox4_Bookin_ItemidKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboBox4_Bookin_ItemidKeyReleased
        String selected = jComboBox4_Bookin_Itemid.getEditor().getItem().toString();
        int keycode = evt.getKeyCode();
        if (keycode >= 65 && keycode <= 90 || keycode >= 96 && keycode <= 105 || keycode == 8) {
            jComboBox4_Bookin_Itemid.setModel(getItemIds(selected));
            if (jComboBox4_Bookin_Itemid.getItemCount() > 0) {
                jComboBox4_Bookin_Itemid.showPopup();
                if (keycode != 8) {
                    ((JTextComponent) jComboBox4_Bookin_Itemid.getEditor().getEditorComponent()).select(selected.length(), jComboBox4_Bookin_Itemid.getSelectedItem().toString().length());
                } else {
                    jComboBox4_Bookin_Itemid.getEditor().setItem(selected);
                }
            }
        }
    }//GEN-LAST:event_jComboBox4_Bookin_ItemidKeyReleased

    private void jComboBox_pre_type_itemidKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboBox_pre_type_itemidKeyReleased
        String selected = jComboBox_pre_type_itemid.getEditor().getItem().toString();
        int keycode = evt.getKeyCode();
        if (keycode >= 65 && keycode <= 90 || keycode >= 96 && keycode <= 105 || keycode == 8) {
            jComboBox_pre_type_itemid.setModel(getItemIds(selected));
            if (jComboBox_pre_type_itemid.getItemCount() > 0) {
                jComboBox_pre_type_itemid.showPopup();
                if (keycode != 8) {
                    ((JTextComponent) jComboBox_pre_type_itemid.getEditor().getEditorComponent()).select(selected.length(), jComboBox_pre_type_itemid.getSelectedItem().toString().length());
                } else {
                    jComboBox_pre_type_itemid.getEditor().setItem(selected);
                }
            }
        }
    }//GEN-LAST:event_jComboBox_pre_type_itemidKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton button_typesett_save;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton26;
    private javax.swing.JButton jButton28;
    private javax.swing.JButton jButton29;
    private javax.swing.JButton jButton9;
    private javax.swing.JButton jButton_Add_BookIn;
    private javax.swing.JButton jButton_Bookin_update;
    private javax.swing.JButton jButton_Delete_Bookin;
    private javax.swing.JButton jButton_Demo_preType;
    private javax.swing.JButton jButton_Refresh;
    private javax.swing.JButton jButton_Refresh_BookIn;
    private javax.swing.JButton jButton_Refresh_BookIn1;
    private javax.swing.JButton jButton_add_pre_type;
    private javax.swing.JButton jButton_clear_BookIn;
    private javax.swing.JButton jButton_coverSave;
    private javax.swing.JButton jButton_cover_Add;
    private javax.swing.JButton jButton_delete_cover;
    private javax.swing.JButton jButton_pre_cover;
    private javax.swing.JButton jButton_save_Bookin;
    private javax.swing.JButton jButton_search_Bookin;
    private javax.swing.JButton jButton_typesett_delete;
    private javax.swing.JButton jButton_typesett_search;
    private javax.swing.JButton jButton_update_Bookin;
    private javax.swing.JButton jButtonpre_clear;
    private javax.swing.JButton jButtonpre_clear_typesett;
    private javax.swing.JCheckBox jCheckBox3_Bookin_photo;
    private javax.swing.JCheckBox jCheckBox4_Bookin_net;
    private javax.swing.JCheckBox jCheckBox5;
    private javax.swing.JCheckBox jCheckBox6;
    private javax.swing.JCheckBox jCheckBox7;
    private javax.swing.JCheckBox jCheckBox8_Bookin_art;
    private javax.swing.JComboBox jComboBox16_maindesiner;
    private javax.swing.JComboBox jComboBox17;
    private javax.swing.JComboBox jComboBox19;
    private javax.swing.JComboBox jComboBox20_Bookin_ColoCa;
    private javax.swing.JComboBox jComboBox4_Bookin_Itemid;
    private javax.swing.JComboBox jComboBox5_Bookin_Bsize;
    private javax.swing.JComboBox jComboBox6_Bookin_colo;
    private javax.swing.JComboBox jComboBox7_Bookin_Pa1;
    private javax.swing.JComboBox jComboBox8_Bookin_pa2;
    private javax.swing.JComboBox jComboBox9;
    private javax.swing.JComboBox jComboBox_paper_type;
    private javax.swing.JComboBox jComboBox_pre_type_itemid;
    private javax.swing.JComboBox jComboBox_soft;
    private javax.swing.JComboBox jComboBoxpr_typist_name;
    private javax.swing.JComboBox jComboBoxpre_colorcat;
    private javax.swing.JComboBox jComboBoxpre_colors;
    private javax.swing.JComboBox jComboBoxpre_cover_itemID;
    private javax.swing.JComboBox jComboBoxpre_cover_size;
    private javax.swing.JComboBox jComboBoxpre_type_fontsize;
    private javax.swing.JComboBox jComboBoxpre_type_fontstyle;
    private javax.swing.JComboBox jCombopre_typistid;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable3_Bookin;
    private javax.swing.JTable jTablepre_cover;
    private javax.swing.JTable jTablepre_typesett;
    private javax.swing.JTextField jTextField2_Bookin_Num;
    private javax.swing.JTextField jTextFieldpre_handoverper;
    private org.jdesktop.swingx.JXDatePicker jXDatePickercover_finishdate;
    private org.jdesktop.swingx.JXDatePicker jXDatePickercover_statdate;
    private org.jdesktop.swingx.JXDatePicker jXDatePickerpre_copyissudate;
    private org.jdesktop.swingx.JXDatePicker jXDatePickerpre_type_finishdate;
    private org.jdesktop.swingx.JXDatePicker jXDatePickerpre_type_startdate;
    // End of variables declaration//GEN-END:variables
}
