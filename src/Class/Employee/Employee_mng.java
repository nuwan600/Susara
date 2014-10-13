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

public class Employee_mng {
    
    private DBConnection dbConn = null;

    public Employee_mng() {
        dbConn = new DBConnection();

    }

    public boolean Add_Employee(Employee e) {
       
        boolean result = false;
        Connection dbconn = null;
        
        try {

            dbconn = dbConn.connect();
            Statement state = dbconn.createStatement();
            
            String query = "INSERT INTO employee (emp_id,emp_name,emp_nic,TP,address,post,j_date,r_date,b_cert,g_cert)"
                    + "values('" + e.getEmpId() + "','" + e.getEmpName() + "','" + e.getEmpNic() + "','" + e.getEmpTp() + "','" + e.getEmpAdd() + "','" + e.getEmpPost() + "','" + e.getEmpJDate() + "','" + e.getEmpRDate() + "','" + e.isEmpBC()+ "','" + e.isEmpGC() + "')";

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
    
    public boolean Update_Employee(Employee e) {
       
        boolean result = false;
        Connection dbconn = null;
        
        try {

            dbconn = dbConn.connect();
            Statement state = dbconn.createStatement();
            
            String query = "' where emp_id='" + e.getEmpId() + "update employee set emp_id='" + e.getEmpId() + "',emp_name='" + e.getEmpName() + "',emp_nic='" + e.getEmpNic() + "',tp='" + e.getEmpTp() + "',address='" + e.getEmpAdd() + "'"
                    + ",post='" + e.getEmpPost() + "',j_date='" + e.getEmpJDate() + "',r_date='" + e.getEmpRDate() + "',b_cert='" + e.isEmpBC() + "', g_cert='" + e.isEmpGC() + "'"+ "')";

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
    
    public boolean Delete_Employee(Employee e) {
      
        boolean result = false;
        Connection dbconn = null;
        
        try {

            dbconn = dbConn.connect();
            Statement state = dbconn.createStatement();
            
            String query = "delete * from employee where emp_id='" + e.getEmpId() + "'";

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
    
    public boolean Search_Employee(Employee e){
        
        boolean result = false;
        Connection dbconn = null;
        
        try {

            dbconn = dbConn.connect();
            Statement state = dbconn.createStatement();
            
            String query = "select * from employee where emp_id='" + e.getEmpId() + "'";

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
    
    public Vector<Vector<String>> Loadtable_emp(){
         
         Vector<Vector<String>> employeeVector = null;
	Connection dbConnection = null;

        try {
            dbConnection = dbConn.connect();
            Statement stmt = dbConnection.createStatement();

            String query = "select * from employee ";

            ResultSet rs = stmt.executeQuery(query);
            employeeVector = new Vector<>();

            while (rs.next()) {
                Vector<String> employee = new Vector<>();
                employee.add(rs.getString(1)); 
                employee.add(rs.getString(2)); 
                employee.add(rs.getString(3)); 
                employee.add(rs.getString(4)); 
                employee.add(rs.getString(5)); 
                employee.add(rs.getString(6)); 
                employee.add(rs.getString(7));
                employee.add(rs.getString(8));
                employee.add(rs.getString(9));
                employeeVector.add(employee);
            }

        } catch (SQLException sQLException) {
            System.out.println(sQLException + "-----------Select query failed");
        } finally {
            dbConn.con_close(dbConnection);
        }
        return employeeVector;
     }
    
}

