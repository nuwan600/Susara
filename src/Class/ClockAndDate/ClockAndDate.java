/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Class.ClockAndDate;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author J.M Vikum Chathuranga 
 *         IT13131098
 *
 */
public class ClockAndDate {
    
    private boolean type;
    
    private String AMPM;

    private Calendar cal;

    private Thread systemClock;

    private JLabel jlt;
    private JLabel jld;

    private int second;
    private int minites;
    private int hour;
    private int am_pm;

    private int year ;
    private int mounth ;
    private int day ;
    private int w_day ;
    private String dayname;
    
    public ClockAndDate() {
        runClock();
        type = true;
    }
    
    public ClockAndDate(JLabel jlt, JLabel jld) {
        this.jlt = jlt;
        this.jld = jld;
        type = false ;
        runClock();
    }

    private void runClock() {
        systemClock = new Thread() {

            public void run() {
                while (true) {
                    try {
                        cal = new GregorianCalendar();
                        if (type == false) {
                            getTime(cal);
                            getDate(cal);
                        }
                        sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ClockAndDate.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };
        systemClock.start();
    }
    
    public int getYear() {
        return year;
    }

    public int getMounth() {
        return mounth;
    }

    public int getDay() {
        return day;
    }

    public void getTime(Calendar cal) {
        
        second = cal.get(Calendar.SECOND);
        minites = cal.get(Calendar.MINUTE);
        hour = cal.get(Calendar.HOUR_OF_DAY);
        am_pm = cal.get(Calendar.AM_PM);
        
        if (am_pm == 1) {
            AMPM = "PM";
        } else {
            AMPM = "AM";
        }
        
        jlt.setText(hour + " : " + formateInt(minites) + " : " + formateInt(second) + " " + AMPM);
    }

    public void getDate(Calendar cal) {

        year = cal.get(Calendar.YEAR);
        mounth = cal.get(Calendar.MONTH) + 1;
        day = cal.get(Calendar.DAY_OF_MONTH);
        w_day = cal.get(Calendar.DAY_OF_WEEK);

        dayname = getDayName(w_day);

        jld.setText(year + " / " + formateInt(mounth) + " / " + formateInt(day) + " " + dayname);

    }

    private String getDayName(int week_day) {

        if (week_day == 1) {
            return "Sunday";
        } else if (week_day == 2) {
            return "Monday";
        } else if (week_day == 3) {
            return "Tuesday";
        } else if (week_day == 4) {
            return "Wednesday";
        } else if (week_day == 5) {
            return "Thursday";
        } else if (week_day == 6) {
            return "Friday";
        } else if (week_day == 7) {
            return "Saturday";
        } else {
            JOptionPane.showMessageDialog(null, "System error.");
        }

        return null;

    }
    
    private String formateInt(int i) {
        
        if (i < 10) {
            return "0"+i;
        }
        
        return ""+i;
        
    }
    
    

}
