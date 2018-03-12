package com.neub.touristclub.frontadmin;

import com.neub.touristclub.mainadmin.members.*;
import com.neub.touristclub.databaseconnection.DbConnector;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import com.neub.touristclub.bean.GeneralMemberBean;
import com.neub.touristclub.mainadmin.HomePage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;

/**
 *
 * @author Team-R&D_v..3
 */
public class GeneralMemberFrontAdmin extends javax.swing.JFrame implements ActionListener {
    
    ArrayList<information> list;
    public int adminIdKey;

    public GeneralMemberFrontAdmin(int idOfAdmin) throws SQLException {

        adminIdKey = idOfAdmin;
        initComponents();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        AddjButton.addActionListener(this);
        UpDatejButton.addActionListener(this);
        home_jButton.addActionListener(this);
        
        PrintToTable();
    }
    
    @Override
    public void actionPerformed(ActionEvent button) {

        if(button.getSource() == home_jButton)
        {
            LandingPage landingPage = new LandingPage(adminIdKey);
            this.dispose();
        }
        
        if (button.getSource() == AddjButton) {
            try {
                GeneralMemberBean bean = new GeneralMemberBean();
                bean.setGm_id(ClubIDjTextField.getText().trim());
                bean.setStudent_id(IDjTextField.getText().trim());
                bean.setName(NamejTextField.getText().trim());
                bean.setMobile(MobileNumberjTextField.getText().trim());

                if (!isValidId(bean.getStudent_id())) {
                    JOptionPane.showMessageDialog(null, "Wrong Id!!!\nPlease Enter a Valid id or Teacher");
                } else {
                    String temporaryNumber = SetMobileNumber(bean.getMobile());
                    if (temporaryNumber.equals("notMatch")) {
                        JOptionPane.showMessageDialog(null, "Wrong Number!!!\nPlease Enter a Valid Phone Number");
                    } else {
                        DbConnector connector = new DbConnector();
                        bean.setMobile(temporaryNumber);
                        String insertStudentQuery = "INSERT INTO general_member "
                                + "VALUES ('" + bean.getGm_id() + "', '"
                                + bean.getStudent_id() + "', '" + bean.getName() + "', '"
                                + bean.getMobile() + "', '" + adminIdKey + "')";

                        connector.statement.executeUpdate(insertStudentQuery);
                        connector.connection.close();
                        JOptionPane.showMessageDialog(null, "Successfully Added...!!!");
                        ClubIDjTextField.setText("");
                        IDjTextField.setText("");
                        NamejTextField.setText("");
                        MobileNumberjTextField.setText("");
                        DefaultTableModel model = (DefaultTableModel) GeneralMemberListjTable.getModel();
                        model.setRowCount(0);
                        PrintToTable();
                    }
                }
            } catch (SQLException ex) {
                HomePage homePage = new HomePage(adminIdKey);
                Logger.getLogger(GeneralMember.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if(button.getSource() == UpDatejButton)
        {
            GeneralMemberBean generalMemberBean = new GeneralMemberBean();            
            generalMemberBean.setGm_id(ClubIDjTextField.getText().trim());
            generalMemberBean.setStudent_id(IDjTextField.getText().trim());
            generalMemberBean.setName(NamejTextField.getText().trim());
            generalMemberBean.setMobile(MobileNumberjTextField.getText().trim());
            
            if(!isValidId(generalMemberBean.getStudent_id() ))
            {
                JOptionPane.showMessageDialog(null, "Wrong Id!!!\nPlease Enter a Valid id or Teacher");         
            }
            else
            {
                String temporaryNumber = SetMobileNumber(generalMemberBean.getMobile());
                if (temporaryNumber.equals("notMatch")) {
                    JOptionPane.showMessageDialog(null, "Wrong Number!!!\nPlease Enter a Valid Phone Number");
                }
                else{   
                    DbConnector dbConnector = new DbConnector();
                    generalMemberBean.setMobile(temporaryNumber);
                    String UpdateQuery = "UPDATE general_member " + " SET " + "student_id = '" + 
                            generalMemberBean.getStudent_id() + "', " + "name = '" + 
                            generalMemberBean.getName() + "', " + "phone = '" + 
                            generalMemberBean.getMobile() + "'" + "WHERE gm_id = " + 
                            "'" + generalMemberBean.getGm_id() + "'";
                    try 
                    {
                        dbConnector.statement.executeUpdate(UpdateQuery);
                        dbConnector.connection.close();
                        JOptionPane.showMessageDialog(null, "Successfullt Updated...!!!");
                        ClubIDjTextField.setText("");
                        IDjTextField.setText("");
                        NamejTextField.setText("");
                        MobileNumberjTextField.setText("");
                        DefaultTableModel model = (DefaultTableModel) GeneralMemberListjTable.getModel();
                        model.setRowCount(0);
                        PrintToTable();
                    } catch (SQLException ex) 
                    {
                        System.out.println(ex);
                        JOptionPane.showMessageDialog(null, "Data Doesn't Update...!!!");
                    }
                }
            }            
        }
    }


    public String SetMobileNumber(String number) {
        String tempNumber, tempSign;
        if (number.length() != 14 && number.length() != 11) {
            return "notMatch";
        }
        if (number.length() == 14) {
            tempSign = number.substring(0, 1);
            tempNumber = number.substring(1, 14);

            if (tempSign.equals("+")) {
                if (tempNumber.matches("[0-9]+") && tempNumber.length() > 2) {
                    return number;
                } else {
                    return "notMatch";
                }
            }
        }
        if (number.length() == 11) {
            if (number.matches("[0-9]+") && number.length() > 2) {
                return "+88" + number;
            } else {
                return "notMatch";
            }
        }
        return "notMatch";
    }

    public boolean isValidId(String id) {
        if ((id.equalsIgnoreCase("Teacher"))) {
            return true;
        }
        if (id.matches("[0-9]+")) {
            System.out.println("Number");
            if (id.length() == 12) {
                return true;
            }
        }
        return false;
    }

    public class information {

        public String clud_id, student_id, name, mobile, add_by, dept, session;
        public information(String clud_id, String student_id, String name, String dept, String session, String mobile, String add_by) {
            this.clud_id = clud_id;
            this.student_id = student_id;
            this.name = name;
            this.dept = dept;
            this.session = session;
            this.mobile = mobile;
            this.add_by = add_by;
        }
    }

    public String getDepartment(String id) {
        if (id.equalsIgnoreCase("teacher")) {
            return "N/A";
        }
        
        String faculty = id.substring(5, 6);
        String dept = id.substring(7, 8);
        int faculty_number = Integer.parseInt(faculty);
        int dept_number = Integer.parseInt(dept);
        String[][] departmentList = {{"N/A", "N/A", "N/A", "N/A", "N/A"},
        {"N/A", "BuA", "N/A", "N/A", "N/A"}, {"N/A", "ENG", "L&J", "N/A",
            "ASS", "N/A"}, {"N/A", "ESC", "CSE", "MAT", "N/A"}};

        return departmentList[faculty_number][dept_number];
    }

    public String getSession(String id) {
        if (id.equalsIgnoreCase("teacher")) {
            return "N/A";
        }
        String session, year, semester;
        year = id.substring(0, 2);
        semester = id.substring(3, 4);
        int semester_number = Integer.parseInt(semester);
        String[] SemesterList = {"N/A", "Spring", "Summer", "Fall"};
        session = SemesterList[semester_number] + "'" + year;
        return session;
    }

    public ArrayList dataRetriveFromDatabase() throws SQLException {

        DbConnector connector = new DbConnector();
        // join query
        String selectStudentQuery = "SELECT general_member.gm_id, general_member"
                + ".student_id, " + "general_member.name, general_member.phone, "
                + "admin.user_name " + "FROM general_member " + "INNER JOIN "
                + "admin ON admin.admin_id = general_member.admin_id order by "
                + "general_member.gm_id";

        connector.resultset = connector.statement.executeQuery(selectStudentQuery);
        list = new ArrayList<information>();
        while (connector.resultset.next()) {
            String clud_id = connector.resultset.getString("general_member.gm_id");
            String student_id = connector.resultset.getString("general_member.student_id");
            String name = connector.resultset.getString("general_member.name");
            String dept = getDepartment(student_id);
            String session = getSession(student_id);
            String mobile = connector.resultset.getString("general_member.phone");
            String add_by = connector.resultset.getString("admin.user_name");
            information info = new information(clud_id, student_id, name, dept, session, mobile, add_by);

            list.add(info);
        }
        connector.connection.close();
        return list;
    }

    public void PrintToTable() throws SQLException {

        DefaultTableModel model = (DefaultTableModel) GeneralMemberListjTable.getModel();
        ArrayList<information> ReciveList = dataRetriveFromDatabase();
        Object rawData[] = new Object[7];

        for (int i = 0; i < ReciveList.size(); i++) {
            rawData[0] = ReciveList.get(i).clud_id;
            rawData[1] = ReciveList.get(i).student_id;
            rawData[2] = ReciveList.get(i).name;
            rawData[3] = ReciveList.get(i).dept;
            rawData[4] = ReciveList.get(i).session;
            rawData[5] = ReciveList.get(i).mobile;
            rawData[6] = ReciveList.get(i).add_by;

            model.addRow(rawData);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        add_edit_delete_jPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        MobileNumberjTextField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        IDjTextField = new javax.swing.JTextField();
        NamejTextField = new javax.swing.JTextField();
        AddjButton = new javax.swing.JButton();
        UpDatejButton = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        ClubIDjTextField = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        GeneralMemberListjTable = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        home_jButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        add_edit_delete_jPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 0, 204), 2, true));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Name : ");

        MobileNumberjTextField.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Mobile : ");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("ID : ");

        IDjTextField.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N

        NamejTextField.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N

        AddjButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        AddjButton.setForeground(new java.awt.Color(0, 153, 0));
        AddjButton.setText("ADD");

        UpDatejButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        UpDatejButton.setForeground(new java.awt.Color(0, 153, 0));
        UpDatejButton.setText("UpDate");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Club ID :");

        ClubIDjTextField.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N

        javax.swing.GroupLayout add_edit_delete_jPanelLayout = new javax.swing.GroupLayout(add_edit_delete_jPanel);
        add_edit_delete_jPanel.setLayout(add_edit_delete_jPanelLayout);
        add_edit_delete_jPanelLayout.setHorizontalGroup(
            add_edit_delete_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(add_edit_delete_jPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(add_edit_delete_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(add_edit_delete_jPanelLayout.createSequentialGroup()
                        .addGroup(add_edit_delete_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(add_edit_delete_jPanelLayout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(MobileNumberjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(add_edit_delete_jPanelLayout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(NamejTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(add_edit_delete_jPanelLayout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(IDjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(add_edit_delete_jPanelLayout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(ClubIDjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(12, Short.MAX_VALUE))
                    .addGroup(add_edit_delete_jPanelLayout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(AddjButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(UpDatejButton, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25))))
        );
        add_edit_delete_jPanelLayout.setVerticalGroup(
            add_edit_delete_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(add_edit_delete_jPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(add_edit_delete_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ClubIDjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(add_edit_delete_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(IDjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(add_edit_delete_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(NamejTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(add_edit_delete_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(MobileNumberjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(add_edit_delete_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(UpDatejButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AddjButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25))
        );

        GeneralMemberListjTable.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        GeneralMemberListjTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Club ID", "ID", "Name", "Dept.", "Session", "Mobile", "ADD_By"
            }
        ));
        GeneralMemberListjTable.setRowHeight(25);
        GeneralMemberListjTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                GeneralMemberListjTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(GeneralMemberListjTable);
        if (GeneralMemberListjTable.getColumnModel().getColumnCount() > 0) {
            GeneralMemberListjTable.getColumnModel().getColumn(0).setMinWidth(55);
            GeneralMemberListjTable.getColumnModel().getColumn(0).setPreferredWidth(55);
            GeneralMemberListjTable.getColumnModel().getColumn(0).setMaxWidth(55);
            GeneralMemberListjTable.getColumnModel().getColumn(1).setMinWidth(100);
            GeneralMemberListjTable.getColumnModel().getColumn(1).setPreferredWidth(100);
            GeneralMemberListjTable.getColumnModel().getColumn(1).setMaxWidth(100);
            GeneralMemberListjTable.getColumnModel().getColumn(3).setMinWidth(50);
            GeneralMemberListjTable.getColumnModel().getColumn(3).setPreferredWidth(50);
            GeneralMemberListjTable.getColumnModel().getColumn(3).setMaxWidth(50);
            GeneralMemberListjTable.getColumnModel().getColumn(4).setMinWidth(85);
            GeneralMemberListjTable.getColumnModel().getColumn(4).setPreferredWidth(85);
            GeneralMemberListjTable.getColumnModel().getColumn(4).setMaxWidth(85);
            GeneralMemberListjTable.getColumnModel().getColumn(5).setMinWidth(120);
            GeneralMemberListjTable.getColumnModel().getColumn(5).setPreferredWidth(120);
            GeneralMemberListjTable.getColumnModel().getColumn(5).setMaxWidth(120);
            GeneralMemberListjTable.getColumnModel().getColumn(6).setMinWidth(80);
            GeneralMemberListjTable.getColumnModel().getColumn(6).setPreferredWidth(80);
            GeneralMemberListjTable.getColumnModel().getColumn(6).setMaxWidth(80);
        }

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 153));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("General Member");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        home_jButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        home_jButton.setForeground(new java.awt.Color(0, 153, 0));
        home_jButton.setText("Home");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(add_edit_delete_jPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 714, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(home_jButton, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(259, 259, 259))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(home_jButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(add_edit_delete_jPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 512, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(58, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void GeneralMemberListjTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_GeneralMemberListjTableMouseClicked
        // TODO add your handling code here:

        int rowNumber = GeneralMemberListjTable.getSelectedRow();

        TableModel model = GeneralMemberListjTable.getModel();

        ClubIDjTextField.setText(model.getValueAt(rowNumber, 0).toString());
        IDjTextField.setText(model.getValueAt(rowNumber, 1).toString());
        NamejTextField.setText(model.getValueAt(rowNumber, 2).toString());
        MobileNumberjTextField.setText(model.getValueAt(rowNumber, 5).toString());
    }//GEN-LAST:event_GeneralMemberListjTableMouseClicked

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
//                try {
//                    //new GeneralMember(1).setVisible(true);
//                } catch (SQLException ex) {
//                    Logger.getLogger(GeneralMember.class.getName()).log(Level.SEVERE, null, ex);
//                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddjButton;
    private javax.swing.JTextField ClubIDjTextField;
    private javax.swing.JTable GeneralMemberListjTable;
    private javax.swing.JTextField IDjTextField;
    private javax.swing.JTextField MobileNumberjTextField;
    private javax.swing.JTextField NamejTextField;
    private javax.swing.JButton UpDatejButton;
    private javax.swing.JPanel add_edit_delete_jPanel;
    private javax.swing.JButton home_jButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
