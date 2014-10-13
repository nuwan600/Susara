/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Class.DBaccess;

import Class.DBconnect.DBconnect;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JOptionPane;

/**
 *
 * @author J.M Vikum Chathuranga
 *         IT13131098
 * 
 */
public class DBaccess {
    
    private DBconnect dbc ;

    private Connection conn = null;
    private Statement s = null;
    private ResultSet rs = null;
    

    public DBaccess() {
        dbc = new DBconnect();
        
    }
    
    public boolean Dbexecute(String query) {
        
        conn = dbc.connect();
        boolean status = false;

        try {

            s = conn.createStatement();
            int respond = s.executeUpdate(query);

            if (respond  > 0) {
                return true;
            }

        } catch (SQLException ex) {
            
            if(ex.toString().endsWith("'PRIMARY'")) {
                
                JOptionPane.showMessageDialog(null, "Already in process.");
                
            } else {
                
                JOptionPane.showMessageDialog(null, "Error in execution.");
                System.out.println(ex);
                
            }
            
        } 
        
        return false;
    }
    
    public ResultSet Dbselect(String query) {
        
        conn = dbc.connect();
        
        try {
            
            s = conn.createStatement();
            rs = s.executeQuery(query);
            
            if(rs.next()) {
                rs.beforeFirst();
                return rs;
            }
            
        } catch (SQLException ex) {
            
            JOptionPane.showMessageDialog(null, "Error in execution.");
            System.out.println(ex);
            
        } 
        
        return null;
    }
    
    public void Dbclose() {
        dbc.close(conn);
    }
    
}
