/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Class.LogIn;

import Class.DBaccess.DBaccess;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

/**
 *
 * @author Vikum
 */
public class LogIn {
    
    private String username;
    private String password;
    
    DBaccess dba ;
    
    public LogIn() {
        dba = new DBaccess();
    }
    
    public boolean AdminLog(String username, String password) {
        
        if (username.equals("Admin") && password.equals("Admin123")) {
            return true;
        }
        return false;
        
    }
    
    public boolean EmployeeLog(String username, String password) {
        try {
            String quary = "SELECT * FROM employee WHERE username='"+username+"' AND password='"+password+"';";
            
            ResultSet rs = dba.Dbselect(quary) ;
            
            if (rs.next()) {
                
                return true;
                
            }
        } catch (SQLException ex) {
            
            
            
        }
        
        return false;
        
    }
    
    
    
}
