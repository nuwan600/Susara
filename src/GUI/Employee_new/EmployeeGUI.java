
package GUI.Employee_new;

import java.awt.HeadlessException;
import java.util.Date;
import java.util.Vector;
import javax.swing.JOptionPane;
import Class.Employee.DBConnection;
import Class.Employee.Employee_mng;
import Class.Employee.Employee;
import Class.Employee.Attendance_mng;
import Class.Employee.Attendance;
import Class.Employee.Attendance_report;
import java.text.SimpleDateFormat;
import java.util.Calendar;


@SuppressWarnings("serial")
public class EmployeeGUI extends javax.swing.JPanel {

    private Vector<Vector<String>> data;
    private Vector<String> header;
    private DBConnection dbconnManager=null;

    public EmployeeGUI() {
        initComponents();
        header = new Vector<>();
        loadtable_emp();
    }

  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        employee_id = new javax.swing.JTextField();
        employee_name = new javax.swing.JTextField();
        employee_nic = new javax.swing.JTextField();
        employee_tp = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        employee_add = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        emp_search = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        employee_post = new javax.swing.JTextField();
        emp_j_date_new = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        emp_j_date = new org.jdesktop.swingx.JXDatePicker();
        emp_r_date_new = new org.jdesktop.swingx.JXDatePicker();
        jLabel10 = new javax.swing.JLabel();
        emp_b_cert = new javax.swing.JCheckBox();
        jLabel11 = new javax.swing.JLabel();
        emp_g_cert = new javax.swing.JCheckBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        emp = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        att_date = new org.jdesktop.swingx.JXDatePicker();
        jLabel13 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        att_wk_hours = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        att = new javax.swing.JTable();
        jLabel16 = new javax.swing.JLabel();
        att_emp_id = new javax.swing.JTextField();

        setMinimumSize(new java.awt.Dimension(1024, 535));
        setPreferredSize(new java.awt.Dimension(1024, 535));
        setLayout(new java.awt.BorderLayout());

        jTabbedPane1.setFont(new java.awt.Font("Berlin Sans FB Demi", 0, 18)); // NOI18N

        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel2.setPreferredSize(new java.awt.Dimension(200, 507));

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jButton1.setBackground(new java.awt.Color(51, 51, 51));
        jButton1.setFont(new java.awt.Font("Bell MT", 1, 11)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/Employee_new/Button-Add-icon.png"))); // NOI18N
        jButton1.setText("Add");
        jButton1.setPreferredSize(new java.awt.Dimension(100, 35));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(51, 51, 51));
        jButton2.setFont(new java.awt.Font("Bell MT", 1, 11)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/Employee_new/Actions-edit-undo-icon.png"))); // NOI18N
        jButton2.setText("Update");
        jButton2.setPreferredSize(new java.awt.Dimension(100, 35));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(51, 51, 51));
        jButton3.setFont(new java.awt.Font("Bell MT", 1, 11)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/Employee_new/Button-Delete-icon.png"))); // NOI18N
        jButton3.setText("Delete");
        jButton3.setPreferredSize(new java.awt.Dimension(100, 35));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(51, 51, 51));
        jButton4.setFont(new java.awt.Font("Bell MT", 1, 11)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/Employee_new/Actions-edit-clear-locationbar-rtl-icon.png"))); // NOI18N
        jButton4.setText("Clear");
        jButton4.setPreferredSize(new java.awt.Dimension(100, 35));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(79, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(266, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );

        jPanel1.add(jPanel2, java.awt.BorderLayout.EAST);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel1.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 11)); // NOI18N
        jLabel1.setText("Emp. ID                   :");

        jLabel2.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 11)); // NOI18N
        jLabel2.setText("Emp. Name            :");

        jLabel3.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 11)); // NOI18N
        jLabel3.setText("Emp. NIC                :");

        jLabel4.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 11)); // NOI18N
        jLabel4.setText("Telephone            :");

        jLabel5.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 11)); // NOI18N
        jLabel5.setText("Address                :");

        employee_name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                employee_nameActionPerformed(evt);
            }
        });

        employee_nic.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                employee_nicKeyTyped(evt);
            }
        });

        employee_tp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                employee_tpActionPerformed(evt);
            }
        });
        employee_tp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                employee_tpKeyTyped(evt);
            }
        });

        employee_add.setColumns(20);
        employee_add.setRows(5);
        jScrollPane1.setViewportView(employee_add);

        jLabel6.setFont(new java.awt.Font("Copperplate Gothic Light", 1, 12)); // NOI18N
        jLabel6.setText("SEARCH        :");

        emp_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emp_searchActionPerformed(evt);
            }
        });
        emp_search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                emp_searchKeyReleased(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 11)); // NOI18N
        jLabel7.setText("Post                                       :");

        emp_j_date_new.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 11)); // NOI18N
        emp_j_date_new.setText("Joined Date                         :");

        jLabel9.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 11)); // NOI18N
        jLabel9.setText("Resigned Date                    :");

        emp_j_date.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emp_j_dateActionPerformed(evt);
            }
        });

        emp_r_date_new.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emp_r_date_newActionPerformed(evt);
            }
        });
        emp_r_date_new.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                emp_r_date_newKeyTyped(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 11)); // NOI18N
        jLabel10.setText("Birth Certificate                :");

        emp_b_cert.setText("Exist");
        emp_b_cert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emp_b_certActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 11)); // NOI18N
        jLabel11.setText("Gramasewa Certificate  :");

        emp_g_cert.setText("Exist");

        emp.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Emp. ID", "Emp. Name", "Emp. NIC", "Telephone", "Address", "Post", "Joined Date", "Resigned Date", "Birth Certificate", "Gramasewa Certificate"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Boolean.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        emp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                empMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(emp);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(employee_id, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(employee_name, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel4)
                                    .addGap(18, 18, 18)
                                    .addComponent(employee_tp))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel3)
                                    .addGap(18, 18, 18)
                                    .addComponent(employee_nic, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(80, 80, 80)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(emp_search, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(emp_j_date_new, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(employee_post, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(emp_r_date_new, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(emp_b_cert)
                                    .addComponent(emp_g_cert)
                                    .addComponent(emp_j_date, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(67, 67, 67))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(130, 130, 130)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 778, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(emp_search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(employee_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(employee_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(employee_post, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(employee_nic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(emp_j_date, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(emp_j_date_new))))
                .addGap(1, 1, 1)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(employee_tp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel10)
                                    .addComponent(emp_b_cert))
                                .addGap(15, 15, 15)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel11)
                                    .addComponent(emp_g_cert)))
                            .addComponent(jLabel5)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(emp_r_date_new, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel1.add(jPanel3, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Employee", jPanel1);

        jPanel5.setLayout(new java.awt.BorderLayout());

        jPanel6.setBackground(new java.awt.Color(102, 102, 102));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel6.setForeground(new java.awt.Color(255, 0, 0));
        jPanel6.setPreferredSize(new java.awt.Dimension(200, 507));

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jButton7.setBackground(new java.awt.Color(51, 51, 51));
        jButton7.setFont(new java.awt.Font("Bell MT", 1, 11)); // NOI18N
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/Employee_new/Button-Add-icon.png"))); // NOI18N
        jButton7.setText("Add");
        jButton7.setPreferredSize(new java.awt.Dimension(100, 35));
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setBackground(new java.awt.Color(51, 51, 51));
        jButton8.setFont(new java.awt.Font("Bell MT", 1, 11)); // NOI18N
        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/Employee_new/Actions-edit-undo-icon.png"))); // NOI18N
        jButton8.setText("Update");
        jButton8.setPreferredSize(new java.awt.Dimension(100, 35));
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setBackground(new java.awt.Color(51, 51, 51));
        jButton9.setFont(new java.awt.Font("Bell MT", 1, 11)); // NOI18N
        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/Employee_new/custom-reports-icon.png"))); // NOI18N
        jButton9.setText("Report");
        jButton9.setPreferredSize(new java.awt.Dimension(100, 35));
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(101, Short.MAX_VALUE)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(75, 75, 75)
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(68, 68, 68)
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(96, 96, 96))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(266, Short.MAX_VALUE)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );

        jPanel5.add(jPanel6, java.awt.BorderLayout.EAST);

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel12.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 11)); // NOI18N
        jLabel12.setText("DATE                               :");

        att_date.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                att_dateActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 11)); // NOI18N
        jLabel13.setText("Duty On Time                :");

        jButton5.setText("Select");
        jButton5.setPreferredSize(new java.awt.Dimension(100, 35));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 11)); // NOI18N
        jLabel14.setText("Duty Off Time               :");

        jButton6.setText("Select");
        jButton6.setPreferredSize(new java.awt.Dimension(100, 35));
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 11)); // NOI18N
        jLabel15.setText("Working Hours          :");

        att_wk_hours.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                att_wk_hoursActionPerformed(evt);
            }
        });

        att.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Emp. ID", "Duty On Time", "Duty Off Time", "Working Hours"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(att);

        jLabel16.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 11)); // NOI18N
        jLabel16.setText("Emp. ID                            :");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel8Layout.createSequentialGroup()
                                        .addGap(0, 4, Short.MAX_VALUE)
                                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addGap(20, 20, 20)))
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(att_wk_hours, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jButton5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(att_date, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(89, 89, 89)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addGap(18, 18, 18)
                                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addGap(18, 18, 18)
                                .addComponent(att_emp_id, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(135, 135, 135))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(att_date, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(att_emp_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(att_wk_hours, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.add(jPanel8, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Attendance", jPanel5);

        add(jTabbedPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void employee_nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_employee_nameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_employee_nameActionPerformed

    private void emp_j_dateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emp_j_dateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_emp_j_dateActionPerformed

    private void emp_r_date_newActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emp_r_date_newActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_emp_r_date_newActionPerformed

    private void emp_b_certActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emp_b_certActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_emp_b_certActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        delete_employee();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        employee_id.setText(null);
        employee_name.setText(null);
        employee_nic.setText(null);
        employee_tp.setText(null);
        employee_add.setText(null);
        employee_post.setText(null);
        emp_j_date.setDate(null);
        emp_r_date_new.setDate(null);
        emp_b_cert.isSelected();
        emp_g_cert.isSelected();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void att_dateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_att_dateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_att_dateActionPerformed

    private void att_wk_hoursActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_att_wk_hoursActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_att_wk_hoursActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        add_employee();
        clear();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        update_employee();
        clear();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void emp_searchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_emp_searchKeyReleased
        search_employee();
    }//GEN-LAST:event_emp_searchKeyReleased

    private void empMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_empMouseClicked
        emp_set_data();
    }//GEN-LAST:event_empMouseClicked

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        Add_Attendance();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        update_attendance();
    }//GEN-LAST:event_jButton8ActionPerformed

    private void employee_tpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_employee_tpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_employee_tpActionPerformed

    private void employee_tpKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_employee_tpKeyTyped
           if (employee_tp.getText().length() <= 10) {

            if (!(Character.isDigit(evt.getKeyChar()))) {
                evt.consume();

            }

        }
    }//GEN-LAST:event_employee_tpKeyTyped

    private void employee_nicKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_employee_nicKeyTyped
        if (employee_nic.getText().length() <= 8) {
            if (!(Character.isDigit(evt.getKeyChar()))) {
                evt.consume();
            }
        } else if (employee_nic.getText().length() == 9) {
            if (!((("" + evt.getKeyChar()).equals("v")) || (("" + evt.getKeyChar()).equals("V")))) {
                evt.consume();
            }
        } else if (employee_nic.getText().length() > 9) {
            evt.consume();
        }
    }//GEN-LAST:event_employee_nicKeyTyped

    private void emp_r_date_newKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_emp_r_date_newKeyTyped
        Date date1 = emp_j_date.getDate();
        Date date2 = emp_r_date_new.getDate();
        
        if(date1.after(date2)){
        		JOptionPane.showMessageDialog(this, "Incorrect resigning date!");
        	}
 
        if(date1.equals(date2)){
        		JOptionPane.showMessageDialog(this, "Incorrect resigning date!");
        	}
    }//GEN-LAST:event_emp_r_date_newKeyTyped

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        Attendance_report report = new Attendance_report("./src/Class.Employee/day1.jasper");
    }//GEN-LAST:event_jButton9ActionPerformed

    private void emp_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emp_searchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_emp_searchActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        Calendar cal = Calendar.getInstance();
    	cal.getTime();
    	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String OnTime = sdf.format(cal.getTime());
        System.out.println(OnTime);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        Calendar cal = Calendar.getInstance();
    	cal.getTime();
    	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String OnTime = sdf.format(cal.getTime());
        System.out.println(OnTime);
//    	System.out.println( sdf.format(cal.getTime()) );
    }//GEN-LAST:event_jButton6ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable att;
    private org.jdesktop.swingx.JXDatePicker att_date;
    private javax.swing.JTextField att_emp_id;
    private javax.swing.JTextField att_wk_hours;
    private javax.swing.JTable emp;
    private javax.swing.JCheckBox emp_b_cert;
    private javax.swing.JCheckBox emp_g_cert;
    private org.jdesktop.swingx.JXDatePicker emp_j_date;
    private javax.swing.JLabel emp_j_date_new;
    private org.jdesktop.swingx.JXDatePicker emp_r_date_new;
    private javax.swing.JTextField emp_search;
    private javax.swing.JTextArea employee_add;
    private javax.swing.JTextField employee_id;
    private javax.swing.JTextField employee_name;
    private javax.swing.JTextField employee_nic;
    private javax.swing.JTextField employee_post;
    private javax.swing.JTextField employee_tp;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables

    private void add_employee() {
        try {
            
             if (employee_id.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Employee id is Empty!");
                }
             else{
                String empID = employee_id.getText();
                String empName = employee_name.getText();
                String empNic = employee_nic.getText();
                String empTp = employee_tp.getText();
                String empAdd = employee_add.getText();
                String empPost = employee_post.getText();
                Date empJDate =emp_j_date.getDate();
                Date empRDate =emp_r_date_new.getDate();
                boolean empBC = emp_b_cert.isSelected();
                boolean empGC = emp_g_cert.isSelected();

                Employee_mng emp_mng = new Employee_mng();
                Employee e = new Employee(empID, empName, empNic, empTp, empAdd, empPost, empJDate, empRDate, empBC, empGC);

                boolean val = emp_mng.Add_Employee(e);

                if (val == true) {
                    JOptionPane.showMessageDialog(this, "Insert completed!");
                    loadtable_emp();
                } 
                else {
                JOptionPane.showMessageDialog(this, "Try again!");
                }
            }
        } 
        catch (HeadlessException e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }
    
    private void update_employee() {
        try {
            if (employee_id.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Employee id is Empty!");
                }
             else{
                String empID = employee_id.getText();
                String empName = employee_name.getText();
                String empNic = employee_nic.getText();
                String empTp = employee_tp.getText();
                String empAdd = employee_add.getText();
                String empPost = employee_post.getText();
                Date empJDate =emp_j_date.getDate();
                Date empRDate =emp_r_date_new.getDate();
                boolean empBC = emp_b_cert.isSelected();
                boolean empGC = emp_g_cert.isSelected();

                Employee_mng emp_mng = new Employee_mng();
                Employee e = new Employee(empID, empName, empNic, empTp, empAdd, empPost, empJDate, empRDate, empBC, empGC);

                boolean val = emp_mng.Update_Employee(e);

                if (val == true) {
                    JOptionPane.showMessageDialog(this, "Update completed!");
                    loadtable_emp();
                } 
                else {
                JOptionPane.showMessageDialog(this, "Try again!");
                }
            }
        } 
        catch (HeadlessException e) {
            JOptionPane.showMessageDialog(this, e);
        }

    }
        
    private void delete_employee() {
        try {
                String empID = employee_id.getText();
                String empName = employee_name.getText();
                String empNic = employee_nic.getText();
                String empTp = employee_tp.getText();
                String empAdd = employee_add.getText();
                String empPost = employee_post.getText();
                Date empJDate =emp_j_date.getDate();
                Date empRDate =emp_r_date_new.getDate();
                boolean empBC = emp_b_cert.isSelected();
                boolean empGC = emp_g_cert.isSelected();

                Employee_mng emp_mng = new Employee_mng();
                Employee e = new Employee(empID, empName, empNic, empTp, empAdd, empPost, empJDate, empRDate, empBC, empGC);

                boolean val = emp_mng.Delete_Employee(e);

                if (val == true) {
                    JOptionPane.showMessageDialog(this, "Employee Deleted!");
                    loadtable_emp();
                } 
                else {
                JOptionPane.showMessageDialog(this, "Try again!");
                }
                } 
        catch (HeadlessException e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }

    private void search_employee() {
        try {
                String empID = employee_id.getText();
                String empName = employee_name.getText();
                String empNic = employee_nic.getText();
                String empTp = employee_tp.getText();
                String empAdd = employee_add.getText();
                String empPost = employee_post.getText();
                Date empJDate =emp_j_date.getDate();
                Date empRDate =emp_r_date_new.getDate();
                boolean empBC = emp_b_cert.isSelected();
                boolean empGC = emp_g_cert.isSelected();

                Employee_mng emp_mng = new Employee_mng();
                Employee e = new Employee(empID, empName, empNic, empTp, empAdd, empPost, empJDate, empRDate, empBC, empGC);

                boolean val = emp_mng.Search_Employee(e);

                if (val == true) {
                    JOptionPane.showMessageDialog(this, "Employee Deleted!");
                    loadtable_emp();
                } 
                else {
                JOptionPane.showMessageDialog(this, "Try again!");
                }
                } 
        catch (HeadlessException e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }
        
    private void loadtable_emp() {

        try {
            Employee_mng emp_man = new Employee_mng();
            data = emp_man.Loadtable_emp();
            emp.setModel(new javax.swing.table.DefaultTableModel(
                    data, header));

        } catch (Exception e) {

        }
    
    }
    
    private void emp_set_data() {

        employee_id.setText(emp.getValueAt(emp.getSelectedRow(), 0).toString());
        employee_name.setText(emp.getValueAt(emp.getSelectedRow(), 1).toString());
        employee_nic.setText(emp.getValueAt(emp.getSelectedRow(), 2).toString());
        employee_tp.setText(emp.getValueAt(emp.getSelectedRow(), 3).toString());
        employee_add.setText(emp.getValueAt(emp.getSelectedRow(), 4).toString());
        employee_post.setText(emp.getValueAt(emp.getSelectedRow(), 5).toString());
        emp_j_date.add(emp.getValueAt(emp.getSelectedRow(), 6).toString(), this);
        emp_r_date_new.add(emp.getValueAt(emp.getSelectedRow(), 7).toString(), this);
        emp_b_cert.setText(emp.getValueAt(emp.getSelectedRow(), 8).toString());
        emp_g_cert.setText(emp.getValueAt(emp.getSelectedRow(), 9).toString());
    }
        
    private void Add_Attendance() {
        try {
            
             if (att_emp_id.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Employee ID is Empty!");
                
            } 

             else{
                Date attDate = att_date.getDate();
                String empID = att_emp_id.getText();
                String empDOnTime = jButton5.getText();
                String empDOffTime = jButton6.getText();
                String empWorkHours = att_wk_hours.getText();

                Attendance_mng att_mng = new Attendance_mng();
                Attendance a = new Attendance(attDate, empID, empDOnTime, empDOffTime, empWorkHours);

                boolean val = att_mng.Add_Attendance(a);

                if (val == true) {
                    JOptionPane.showMessageDialog(this, "Attendance Added!");
                    loadtable_att();
                } 
                else {
                JOptionPane.showMessageDialog(this, "Try again!");
                }
            }

        } 
        catch (HeadlessException e) {
            JOptionPane.showMessageDialog(this, e);
        }

    }

    private void update_attendance() {
        try {
            if (employee_id.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Employee id is Empty!");
            }

             else{
                Date attDate = att_date.getDate();
                String attEmpId = att_emp_id.getText();
                String attDOnTime = jButton5.getText();
                String attDOffTime = jButton6.getText();
                String attWorkHours = att_wk_hours.getText();

                Attendance_mng emp_mng = new Attendance_mng();
                Attendance a = new Attendance(attDate,attEmpId,attDOnTime, attDOffTime, attWorkHours);

                boolean val = emp_mng.Update_Attendance(a);

                if (val == true) {
                    JOptionPane.showMessageDialog(this, "Update completed!");
                    loadtable_att();
                } 
                else {
                JOptionPane.showMessageDialog(this, "Try again!");
                }
            }
        } 
        catch (HeadlessException e) {
            JOptionPane.showMessageDialog(this, e);
        }

    }
    
       private void loadtable_att() {

        try {
            Attendance_mng att_man = new Attendance_mng();
            data = att_man.Loadtable_att();
            emp.setModel(new javax.swing.table.DefaultTableModel(
                    data, header));

        } catch (Exception e) {

        }
    
    }
    
    private void clear() {
        employee_id.setText(null);
        employee_name.setText(null);
        employee_nic.setText(null);
        employee_tp.setText(null);
        employee_add.setText(null);
        employee_post.setText(null);
        emp_j_date.setDate(null);
        emp_r_date_new.setDate(null);
        emp_b_cert.setText(null);
        emp_g_cert.setText(null);
    } 

    /**
     * @return the dbconnManager
     */
    public DBConnection getDbconnManager() {
        return dbconnManager;
    }

    /**
     * @param dbconnManager the dbconnManager to set
     */
    public void setDbconnManager(DBConnection dbconnManager) {
        this.dbconnManager = dbconnManager;
    }
}


