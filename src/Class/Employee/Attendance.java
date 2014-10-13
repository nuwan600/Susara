/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Class.Employee;

import java.util.Date;

/**
 *
 * @author toshiba
 */

public class Attendance {
   private Date attDate;
   private String atEmpId;
   private String attDutyOnTime;
   private String attDutyOffTime;
   private String attWorkHours;

    public Attendance() {
    }

    public Attendance(Date attDate, String atEmpId, String attDutyOnTime, String attDutyOffTime, String attWorkHours) {
        this.attDate = attDate;
        this.atEmpId = atEmpId;
        this.attDutyOnTime = attDutyOnTime;
        this.attDutyOffTime = attDutyOffTime;
        this.attWorkHours = attWorkHours;
    }

    public Attendance(Date attDate, String attDutyOnTime, String attDutyOffTime, String attWorkHours) {
        this.attDate = attDate;
        this.attDutyOnTime = attDutyOnTime;
        this.attDutyOffTime = attDutyOffTime;
        this.attWorkHours = attWorkHours;
    }

    
    public Date getAttDate() {
        return attDate;
    }

    public void setAttDate(Date attDate) {
        this.attDate = attDate;
    }

    public String getAtEmpId() {
        return atEmpId;
    }

    public void setAtEmpId(String atEmpId) {
        this.atEmpId = atEmpId;
    }

    public String getAttDutyOnTime() {
        return attDutyOnTime;
    }

    public void setAttDutyOnTime(String attDutyOnTime) {
        this.attDutyOnTime = attDutyOnTime;
    }

    public String getAttDutyOffTime() {
        return attDutyOffTime;
    }

    public void setAttDutyOffTime(String attDutyOffTime) {
        this.attDutyOffTime = attDutyOffTime;
    }

    public String getAttWorkHours() {
        return attWorkHours;
    }

    public void setAttWorkHours(String attWorkHours) {
        this.attWorkHours = attWorkHours;
    }
   
   
}
