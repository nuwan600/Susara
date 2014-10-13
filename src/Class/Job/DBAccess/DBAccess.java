/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Class.Job.DBAccess;

/**
 *
 * @author J.M Vikum Chathuranga 
 *         IT13131098
 *
 */

import Class.DBconnect.DBconnect;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSetMetaData;

import java.util.Vector;

public class DBAccess {
    
    private static DBconnect dbc = null;
    private Connection con = null;
    private Statement st = null;
    private ResultSet rs = null;
    private ResultSetMetaData rsmd = null;
    
    public DBAccess() {
        dbc = new DBconnect();
    }
    
    private void makeNull() {
        con = null;
        st = null;
        rs = null;
        rsmd = null;
    }
    
    public Vector selectQuery(String query) {
        Vector<Vector<String>> vector = new Vector<Vector<String>>();
        try {
            con = dbc.connect();
            st = con.createStatement();
            rs = st.executeQuery(query);
            rsmd = rs.getMetaData();
            int colcount = rsmd.getColumnCount();
            while (rs.next()) {
                Vector<String> rowvector = new Vector<String>();
                for ( int i = 1 ; i <= colcount ; i++ ) {
                    rowvector.add(rs.getString(i));
                }
                vector.add(rowvector);
            }
            return vector;
        } catch (SQLException sqle) {
            System.out.println("Select query failed.\n"+query);
            vector = null;
        } catch (NullPointerException npe) {
            
        } finally {
            dbc.close(con);
            makeNull();
        }
        return null;
    }
    
    public boolean operationQuery(String query) {
        boolean result = false;
        try {
            con = dbc.connect();
            st = con.createStatement();
            int val = st.executeUpdate(query);
            if(val > 0) {
                result = true;
            } else {
                result = false;
            }
        } catch (SQLException sqle) {
            if (query.startsWith("INSERT")) {
                System.out.println("Insert query failed.\n"+query);
            } else if (query.startsWith("DELETE")) {
                System.out.println("Delete query failed.\n"+query);
            } else if (query.startsWith("UPDATE")) {
                System.out.println("Update query failed.\n"+query);
            }
            result = false;
        } finally {
            dbc.close(con);
            makeNull();
        }
        return result;
    }
    
}
