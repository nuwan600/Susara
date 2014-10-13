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
import java.awt.Container;
import javax.swing.JFrame;
import java.sql.Connection;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;
import net.sf.jasperreports.engine.JRException;
import Class.Employee.DBConnection;
import javax.swing.JOptionPane;

public class Attendance_report {
    public Attendance_report(String filename){
       try{
        Connection con = DBConnection.DBConnection();
        JasperPrint print = JasperFillManager.fillReport(filename,null,con);
        JRViewer jr = new JRViewer(print);
        Container contain = getContentPane();
        contain.add(jr);
//        setVisible(true);
//        setBounds(10,10,800,660);
        }catch(JRException e){
            JOptionPane.showMessageDialog(null, "JRE Error!");
        }
    }
}
