package com.neub.touristclub.mainadmin.members;

import com.neub.touristclub.databaseconnection.DbConnector;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import com.neub.touristclub.bean.GeneralMemberBean;
import com.neub.touristclub.mainadmin.accounts.AccountsInformation;
import com.neub.touristclub.mainadmin.accounts.MembersFee;
import com.neub.touristclub.mainadmin.events.EventsInformation;
import com.neub.touristclub.message.SendMessage;
import com.neub.touristclub.message.PreviousMessage;
import com.neub.touristclub.developer.Developer;
import com.neub.touristclub.mainadmin.adminsetting.Setting;
import com.neub.touristclub.mainadmin.HomePage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;

/**
 *
 * @author Team-R&D_v..3
 */
public class GeneralMember extends javax.swing.JFrame implements ActionListener {
    
    ArrayList<information> list;
    public int adminIdKey;

    public GeneralMember(int idOfAdmin) throws SQLException {

        adminIdKey = idOfAdmin;
        initComponents();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        AddjButton.addActionListener(this);
        UpDatejButton.addActionListener(this);
        DeletejButton.addActionListener(this);
        HomejMenuItem.addActionListener(this);
        executiveMemberJMenuItem.addActionListener(this);
        memberfeejCheckBoxMenuItem.addActionListener(this);
        AccountsInfojMenuItem.addActionListener(this);
        EventsjMenuItem.addActionListener(this);
        SendMessagejMenuItem.addActionListener(this);
        PreviousMessagejMenuItem.addActionListener(this);
        DeveloperjMenuItem.addActionListener(this);
        AdminSettingjMenuItem.addActionListener(this);
        ExitjMenuItem.addActionListener(this);
        
        
        PrintToTable();
    }
    
    @Override
    public void actionPerformed(ActionEvent button) {

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
        
        if(button.getSource() == DeletejButton)
        {
            GeneralMemberBean bean = new GeneralMemberBean();            
            bean.setGm_id(ClubIDjTextField.getText().trim());
            DbConnector dbConnector = new DbConnector();
            String deleteQuery = "DELETE FROM general_member "+ " WHERE gm_id = " + "'" + Integer.parseInt(bean.getGm_id()) + "'";
 //           String deleteQuery = "DELETE FROM executive_member "+ "WHERE em_id = " + "'" + exe_member + "'";
            System.out.println(bean.getGm_id() + " ");
            try 
            {
                dbConnector.statement.executeUpdate(deleteQuery);                
                dbConnector.connection.close();
                JOptionPane.showMessageDialog(null, "Data Successfully Deleted...!!!");
                ClubIDjTextField.setText("");
                IDjTextField.setText("");
                NamejTextField.setText("");
                MobileNumberjTextField.setText("");
                DefaultTableModel model = (DefaultTableModel)GeneralMemberListjTable.getModel();
                model.setRowCount(0);
                PrintToTable();
            } catch (SQLException ex) 
            {
                try {
                    JOptionPane.showMessageDialog(null, "Some Problem Occur...!!!");
                    GeneralMember gm = new GeneralMember(adminIdKey);
                    this.dispose();
                } catch (SQLException ex1) {
                    Logger.getLogger(GeneralMember.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        }
        
        // Menu item implement
        if(button.getSource() == HomejMenuItem)
        {
            HomePage homePage = new HomePage(adminIdKey);
            this.dispose();
        }
        if(button.getSource() == executiveMemberJMenuItem)
        {
            try {
                ExecutiveMember executiveMember = new ExecutiveMember(adminIdKey);
                this.dispose();
            } catch (SQLException ex) {
                Logger.getLogger(GeneralMember.class.getName()).log(Level.SEVERE, null, ex);
            }   
        }
        
        if( (button.getSource() == AccountsInfojMenuItem) )
        {
            try {
                AccountsInformation accountsInformation = new AccountsInformation(adminIdKey);
                this.dispose();
            } catch (Exception e) {
            }
        }
        
        if( button.getSource() == memberfeejCheckBoxMenuItem)
        {
            try {
                MembersFee fee = new MembersFee(adminIdKey);
                this.dispose();
            } catch (Exception e) {
            }
        }
        
        if( (button.getSource() == EventsjMenuItem) )
        {
            try {
                EventsInformation ei = new EventsInformation(adminIdKey);
                this.dispose();
            } catch (Exception e) {
            }
        }
        
        if( (button.getSource() == SendMessagejMenuItem) )
        {
            try {
                SendMessage message = new SendMessage(adminIdKey);
                this.dispose();
            } catch (Exception e) {
            }
        }
        
        if( (button.getSource() == AdminSettingjMenuItem) )
        {
            try {
                Setting setting = new Setting(adminIdKey);
                this.dispose();
            } catch (Exception e) {
            }
        }
        
        if( (button.getSource() == DeveloperjMenuItem)  )
        {
            try {
                Developer developer = new Developer(adminIdKey);
                this.dispose();
            } catch (Exception e) {
            }
        }
        if(button.getSource() == PreviousMessagejMenuItem)
        {
            try {
                PreviousMessage pm = new PreviousMessage(adminIdKey);
                this.dispose();
            } catch (Exception e) {
            }
        }
        
        if(button.getSource() == ExitjMenuItem)
        {
            try {
                this.dispose();
            } catch (Exception e) {
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
        DeletejButton = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        ClubIDjTextField = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        GeneralMemberListjTable = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        GeneralMeberjMenuBar = new javax.swing.JMenuBar();
        HomejMenu = new javax.swing.JMenu();
        HomejMenuItem = new javax.swing.JMenuItem();
        ExecutiveMemberjMenu = new javax.swing.JMenu();
        executiveMemberJMenuItem = new javax.swing.JMenuItem();
        GeneralMemberjMenu = new javax.swing.JMenuItem();
        accountsInformationjMenu = new javax.swing.JMenu();
        AccountsInfojMenuItem = new javax.swing.JMenuItem();
        memberfeejCheckBoxMenuItem = new javax.swing.JMenuItem();
        eventsjMenu = new javax.swing.JMenu();
        EventsjMenuItem = new javax.swing.JMenuItem();
        endMessagejMenu = new javax.swing.JMenu();
        SendMessagejMenuItem = new javax.swing.JMenuItem();
        PreviousMessagejMenuItem = new javax.swing.JMenuItem();
        SettingjMenu = new javax.swing.JMenu();
        AdminSettingjMenuItem = new javax.swing.JMenuItem();
        DeveloperjMenu = new javax.swing.JMenu();
        DeveloperjMenuItem = new javax.swing.JMenuItem();
        exitsjMenu = new javax.swing.JMenu();
        ExitjMenuItem = new javax.swing.JMenuItem();

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

        DeletejButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        DeletejButton.setForeground(new java.awt.Color(0, 153, 0));
        DeletejButton.setText("Delete");

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
                        .addComponent(AddjButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(UpDatejButton, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(DeletejButton))
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
                        .addGap(0, 2, Short.MAX_VALUE)))
                .addContainerGap())
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
                .addGap(19, 19, 19)
                .addGroup(add_edit_delete_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AddjButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DeletejButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(UpDatejButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24))
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

        HomejMenu.setText("Home");
        HomejMenu.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N

        HomejMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.CTRL_MASK));
        HomejMenuItem.setText("Home");
        HomejMenu.add(HomejMenuItem);

        GeneralMeberjMenuBar.add(HomejMenu);

        ExecutiveMemberjMenu.setForeground(new java.awt.Color(0, 153, 51));
        ExecutiveMemberjMenu.setText(" Member");
        ExecutiveMemberjMenu.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N

        executiveMemberJMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.ALT_MASK));
        executiveMemberJMenuItem.setText("Executive Member");
        executiveMemberJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                executiveMemberJMenuItemActionPerformed(evt);
            }
        });
        ExecutiveMemberjMenu.add(executiveMemberJMenuItem);

        GeneralMemberjMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.CTRL_MASK));
        GeneralMemberjMenu.setForeground(new java.awt.Color(0, 153, 51));
        GeneralMemberjMenu.setText("General Member");
        GeneralMemberjMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GeneralMemberjMenuActionPerformed(evt);
            }
        });
        ExecutiveMemberjMenu.add(GeneralMemberjMenu);

        GeneralMeberjMenuBar.add(ExecutiveMemberjMenu);

        accountsInformationjMenu.setText("Accounts");
        accountsInformationjMenu.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N

        AccountsInfojMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        AccountsInfojMenuItem.setText("accounts info.");
        accountsInformationjMenu.add(AccountsInfojMenuItem);

        memberfeejCheckBoxMenuItem.setText("member fees");
        accountsInformationjMenu.add(memberfeejCheckBoxMenuItem);

        GeneralMeberjMenuBar.add(accountsInformationjMenu);

        eventsjMenu.setText("Events");
        eventsjMenu.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N

        EventsjMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        EventsjMenuItem.setText("events");
        eventsjMenu.add(EventsjMenuItem);

        GeneralMeberjMenuBar.add(eventsjMenu);

        endMessagejMenu.setText("Message");
        endMessagejMenu.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N

        SendMessagejMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.CTRL_MASK));
        SendMessagejMenuItem.setText("send message");
        endMessagejMenu.add(SendMessagejMenuItem);

        PreviousMessagejMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        PreviousMessagejMenuItem.setText("previous message");
        PreviousMessagejMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PreviousMessagejMenuItemActionPerformed(evt);
            }
        });
        endMessagejMenu.add(PreviousMessagejMenuItem);

        GeneralMeberjMenuBar.add(endMessagejMenu);

        SettingjMenu.setText("Setting");
        SettingjMenu.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N

        AdminSettingjMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        AdminSettingjMenuItem.setText("admin setting");
        SettingjMenu.add(AdminSettingjMenuItem);

        GeneralMeberjMenuBar.add(SettingjMenu);

        DeveloperjMenu.setText("Developer");
        DeveloperjMenu.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N

        DeveloperjMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_MASK));
        DeveloperjMenuItem.setText("information");
        DeveloperjMenu.add(DeveloperjMenuItem);

        GeneralMeberjMenuBar.add(DeveloperjMenu);

        exitsjMenu.setText("Exit");
        exitsjMenu.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N

        ExitjMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        ExitjMenuItem.setText("exit");
        exitsjMenu.add(ExitjMenuItem);

        GeneralMeberjMenuBar.add(exitsjMenu);

        setJMenuBar(GeneralMeberjMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(add_edit_delete_jPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 714, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(259, 259, 259))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(add_edit_delete_jPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 512, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(35, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void executiveMemberJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_executiveMemberJMenuItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_executiveMemberJMenuItemActionPerformed

    private void GeneralMemberjMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GeneralMemberjMenuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_GeneralMemberjMenuActionPerformed

    private void PreviousMessagejMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PreviousMessagejMenuItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PreviousMessagejMenuItemActionPerformed

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
                try {
                    new GeneralMember(1).setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(GeneralMember.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem AccountsInfojMenuItem;
    private javax.swing.JButton AddjButton;
    private javax.swing.JMenuItem AdminSettingjMenuItem;
    private javax.swing.JTextField ClubIDjTextField;
    private javax.swing.JButton DeletejButton;
    private javax.swing.JMenu DeveloperjMenu;
    private javax.swing.JMenuItem DeveloperjMenuItem;
    private javax.swing.JMenuItem EventsjMenuItem;
    private javax.swing.JMenu ExecutiveMemberjMenu;
    private javax.swing.JMenuItem ExitjMenuItem;
    private javax.swing.JMenuBar GeneralMeberjMenuBar;
    private javax.swing.JTable GeneralMemberListjTable;
    private javax.swing.JMenuItem GeneralMemberjMenu;
    private javax.swing.JMenu HomejMenu;
    private javax.swing.JMenuItem HomejMenuItem;
    private javax.swing.JTextField IDjTextField;
    private javax.swing.JTextField MobileNumberjTextField;
    private javax.swing.JTextField NamejTextField;
    private javax.swing.JMenuItem PreviousMessagejMenuItem;
    private javax.swing.JMenuItem SendMessagejMenuItem;
    private javax.swing.JMenu SettingjMenu;
    private javax.swing.JButton UpDatejButton;
    private javax.swing.JMenu accountsInformationjMenu;
    private javax.swing.JPanel add_edit_delete_jPanel;
    private javax.swing.JMenu endMessagejMenu;
    private javax.swing.JMenu eventsjMenu;
    private javax.swing.JMenuItem executiveMemberJMenuItem;
    private javax.swing.JMenu exitsjMenu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JMenuItem memberfeejCheckBoxMenuItem;
    // End of variables declaration//GEN-END:variables
}
