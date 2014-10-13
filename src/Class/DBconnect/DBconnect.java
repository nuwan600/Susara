/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Class.DBconnect;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.*;

/**
 *
 * @author Sachin
 */
public class DBconnect {

    Connection conn = null;

    public DBconnect() {

        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Database driver error.");
            System.out.println(e);
        }
    }

    public Connection connect() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/susara", "root", "root");
            return conn;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Database access denied.");
            System.out.println(e);
        }
        return conn;
    }
    
    public void close(Connection dbcon) {
        try {
            dbcon.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "DB connection closing error.");
            System.out.println(e);
        }
    }

}
