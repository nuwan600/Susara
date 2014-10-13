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
import java.util.Date; 

public class Employee {
    public String empId;
    public String empName;
    public String empNic;
    public String empTp;
    public String empAdd;
    public String empPost;
    public Date empJDate;
    public Date empRDate;
    public boolean empBC;
    public boolean empGC;

    public Employee(String empId, String empName, String empNic, String empTp, String empAdd, String empPost, Date empJDate, Date empRDate, boolean empBC, boolean empGC) {
        this.empId = empId;
        this.empName = empName;
        this.empNic = empNic;
        this.empTp = empTp;
        this.empAdd = empAdd;
        this.empPost = empPost;
        this.empJDate = empJDate;
        this.empRDate = empRDate;
        this.empBC = empBC;
        this.empGC = empGC;
    }



    public Employee() {
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpNic() {
        return empNic;
    }

    public void setEmpNic(String empNic) {
        this.empNic = empNic;
    }

    public String getEmpTp() {
        return empTp;
    }

    public void setEmpTp(String empTp) {
        this.empTp = empTp;
    }

    public String getEmpAdd() {
        return empAdd;
    }

    public void setEmpAdd(String empAdd) {
        this.empAdd = empAdd;
    }

    public String getEmpPost() {
        return empPost;
    }

    public void setEmpPost(String empPost) {
        this.empPost = empPost;
    }

    public Date getEmpJDate() {
        return empJDate;
    }

    public void setEmpJDate(Date empJDate) {
        this.empJDate = empJDate;
    }

    public Date getEmpRDate() {
        return empRDate;
    }

    public void setEmpRDate(Date empRDate) {
        this.empRDate = empRDate;
    }

    public boolean isEmpBC() {
        return empBC;
    }

    public void setEmpBC(boolean empBC) {
        this.empBC = empBC;
    }

    public boolean isEmpGC() {
        return empGC;
    }

    public void setEmpGC(boolean empGC) {
        this.empGC = empGC;
    }
    
    
}
