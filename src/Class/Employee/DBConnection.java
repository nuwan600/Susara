/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Class.Employee;

/**
 *
 * @author toshiba
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author it13128746
 */
public class DBConnection {
   
        String sourceURL;
        
        public DBConnection() {
        try {
// Load JDBC driver
            Class.forName("com.mysql.jdbc.Driver");
// Connection URL.
        sourceURL = new String("jdbc:mysql://localhost:3306/susara");//jdbc:mysql://localhost:3306/susara
        } catch (ClassNotFoundException classNotFoundException) {
        System.out.println(classNotFoundException + "-----------Unable to load database driver classes");
        }
        }


        public Connection connect(){
        Connection dbConn = null;
        try {
        dbConn = (Connection) DriverManager.getConnection(sourceURL, "root","root");//susara
        } catch (SQLException sQLException) {
        System.out.println(sQLException + "-----------DB connection failure");
        }
        return dbConn;
        }

        public void con_close(Connection dbConn) {
        try {
        dbConn.close();
        } catch (SQLException sQLException) {
        System.out.println(sQLException + "-----------DB connection closing failure");
        }
        }
}

   

