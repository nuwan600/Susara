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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class Attendance_mng {
    private DBConnection dbConn = null;

    public Attendance_mng() {
        dbConn = new DBConnection();
    }
    
    public boolean Add_Attendance(Attendance a) {
       
        boolean result = false;
        Connection dbconn = null;
        
        try {

            dbconn = dbConn.connect();
            Statement state = dbconn.createStatement();
            
            String query = "INSERT INTO attendance (date,emp_id,d_on,d_off,wk_hour)"
                    + "values('" + a.getAttDate() + "','" + a.getAtEmpId() + "','" + a.getAttDutyOnTime() + "','" + a.getAttDutyOffTime() + "','" + a.getAttWorkHours() + "')";

            int val = state.executeUpdate(query);
            if (val == 1) {
                result = true;
            } 
            else {
                result = false;
            }

        } catch (SQLException er) {
            System.out.println(er);
        } 
        
        finally {
            dbConn.con_close(dbconn);
        }

        return result;
    }
    
    public boolean Update_Attendance(Attendance a) {
       
        boolean result = false;
        Connection dbconn = null;
        
        try {

            dbconn = dbConn.connect();
            Statement state = dbconn.createStatement();
            
            String query = "' where emp_id='" + a.getAtEmpId() + "update attendance set date='" + a.getAttDate() + "',d_on='" + a.getAttDutyOnTime() + "',d_off='" + a.getAttDutyOffTime() + "',wk_hour='" + a.getAttWorkHours() + "'"+ "')";

            int val = state.executeUpdate(query);
            if (val == 1) {
                result = true;
            } 
            else {
                result = false;
            }

        } catch (SQLException er) {
            System.out.println(er);
        } 
        finally {
            dbConn.con_close(dbconn);
        }

        return result;
    }
    
    public Vector<Vector<String>> Loadtable_att(){
         
         Vector<Vector<String>> attendanceVector = null;
	Connection dbConnection = null;

        try {
            dbConnection = dbConn.connect();
            Statement stmt = dbConnection.createStatement();

            String query = "select * from attendance ";

            ResultSet rs = stmt.executeQuery(query);
            attendanceVector = new Vector<>();

            while (rs.next()) {
                Vector<String> att = new Vector<>();
                att.add(rs.getString(1)); 
                att.add(rs.getString(2)); 
                att.add(rs.getString(3)); 
                att.add(rs.getString(4)); 
                att.add(rs.getString(5)); 
                att.add(rs.getString(6)); 
                att.add(rs.getString(7));
                att.add(rs.getString(8));
                att.add(rs.getString(9));
                attendanceVector.add(att);
            }

        } catch (SQLException sQLException) {
            System.out.println(sQLException + "-----------Select query failed");
        } finally {
            dbConn.con_close(dbConnection);
        }
        return attendanceVector;
     }
}
