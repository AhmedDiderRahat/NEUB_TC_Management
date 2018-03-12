package com.neub.touristclub.mainadmin.members;

import com.neub.touristclub.databaseconnection.DbConnector;
import com.neub.touristclub.bean.ExecutivelMemberBean;
import com.neub.touristclub.mainadmin.HomePage;

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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Team-R&D_v.3
 */

public final class ExecutiveMember extends javax.swing.JFrame implements ActionListener {
        
    ArrayList<information> list;
    public static int exe_member;
    public static int gmIdList[] = new int[5000];
    public static String exe_current_name;
    public static boolean edit_exe_member = false;
    public static int adminIdKey;
    
    public ExecutiveMember(int idForAdmin) throws SQLException {
        adminIdKey = idForAdmin;
        initComponents();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        AddjButton.addActionListener(this);
        UpDatejButton.addActionListener(this);
        DeletejButton.addActionListener(this);
        
        //menu handeling
        generalMemberjMenuItem.addActionListener(this);
        AddjButton.addActionListener(this);
        UpDatejButton.addActionListener(this);
        DeletejButton.addActionListener(this);
        HomejMenuItem.addActionListener(this);
        memberfeejCheckBoxMenuItem.addActionListener(this);
        accountsInfojMenuItem.addActionListener(this);
        eventsjMenuItem.addActionListener(this);
        sendMessagejMenuItem.addActionListener(this);
        previousMessagejMenuItem.addActionListener(this);
        developerjMenuItem.addActionListener(this);
        adminSettingjMenuItem.addActionListener(this);
        exitjMenuItem.addActionListener(this);

        this.showComboBox(AddGeneralMemberjComboBox);
        PrintToTable();
    }

    @Override
    public void actionPerformed(ActionEvent button) {

        // Menu item implement
        if(button.getSource() == HomejMenuItem)
        {
            HomePage homePage = new HomePage(adminIdKey);
            this.dispose();
        }
        
        if(button.getSource() == generalMemberjMenuItem)
        {
            try {
                GeneralMember generalMember = new GeneralMember(adminIdKey);
                this.dispose();
            } catch (SQLException ex) {
                Logger.getLogger(GeneralMember.class.getName()).log(Level.SEVERE, null, ex);
            }   
        }
        
        if( (button.getSource() == accountsInfojMenuItem) )
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
        
        if( (button.getSource() == eventsjMenuItem) )
        {
            try {
                EventsInformation ei = new EventsInformation(adminIdKey);
                this.dispose();
            } catch (Exception e) {
            }
        }
        
        if( (button.getSource() == sendMessagejMenuItem) )
        {
            try {
                SendMessage message = new SendMessage(adminIdKey);
                this.dispose();
            } catch (Exception e) {
            }
        }
        
        if(button.getSource() == previousMessagejMenuItem)
        {
            try {
                PreviousMessage pm = new PreviousMessage(adminIdKey);
                this.dispose();
            } catch (Exception e) {
            }
        }
        
        if( (button.getSource() == adminSettingjMenuItem) )
        {
            try {
                Setting setting = new Setting(adminIdKey);
                this.dispose();
            } catch (Exception e) {
            }
        }
        
        if( (button.getSource() == developerjMenuItem)  )
        {
            try {
                Developer developer = new Developer(adminIdKey);
                this.dispose();
            } catch (Exception e) {
            }
        }
        
        if(button.getSource() == exitjMenuItem)
        {
            try {
                this.dispose();
            } catch (Exception e) {
            }
        }
        
        if (button.getSource() == AddjButton) {
            //select name
            ExecutivelMemberBean bean = new ExecutivelMemberBean();
            bean.setName(AddGeneralMemberjComboBox.getSelectedItem().toString());
            DbConnector connector = new DbConnector();
            int gmId = -100;
            try {
                String selectQuery = "SELECT gm_id, phone FROM general_member "
                        + "WHERE name like" + "'" + bean.getName() + "'";
                connector.resultset = connector.statement.executeQuery(selectQuery);
                while (connector.resultset.next()) {
                    bean.setGm_id(connector.resultset.getString("gm_id"));
                    gmId = Integer.parseInt(bean.getGm_id());
                }

                System.out.println(gmId + " ");
                connector.connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(ExecutiveMember.class.getName()).log(Level.SEVERE, null, ex);
            }

            //Insert portion : 
            bean.setDesignation(DesignationjTextField.getText().trim());
            bean.setFromYear(YearFromjComboBox.getSelectedItem().toString());
            bean.setToYear(YearTojComboBox1.getSelectedItem().toString());
            bean.setStatus(StatusjComboBox.getSelectedItem().toString());

            DbConnector connector1 = new DbConnector();

            if ((gmId == -100) || (bean.getDesignation().equals(""))) {
                //System.out.println("sare sobbonash");
                JOptionPane.showMessageDialog(null, "Enter Valid Input\nPlease ");
            } else {
                try {
                    bean.setYear(bean.getFromYear() + "-" + bean.getToYear());

                    String insertQuery1 = "INSERT INTO executive_member "
                            + "(designation, year, status, gm_id) VALUES ('"
                            + bean.getDesignation() + "', '" + bean.getYear()
                            + "', '" + bean.getStatus() + "', '" + gmId + "')";

                    connector1.statement.executeUpdate(insertQuery1);
                    connector1.connection.close();
                    JOptionPane.showMessageDialog(null, "Successfully Added...!!!");

                    DesignationjTextField.setText("");
                    this.showComboBox(AddGeneralMemberjComboBox);

                    DefaultTableModel model = (DefaultTableModel) ExecutivelMemberListjTable.getModel();
                    model.setRowCount(0);
                    PrintToTable();
                } catch (SQLException ex) {
                    Logger.getLogger(ExecutiveMember.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        if (button.getSource() == UpDatejButton) {
            try {
                DbConnector connector = new DbConnector();
                ExecutivelMemberBean bean = new ExecutivelMemberBean();
                bean.setName(AddGeneralMemberjComboBox.getSelectedItem().toString());

                String selectQuery = "SELECT gm_id, phone FROM general_member "
                        + "WHERE name like" + "'" + bean.getName() + "'";
                connector.resultset = connector.statement.executeQuery(selectQuery);
                int gmId = -100;
                while (connector.resultset.next()) {
                    bean.setGm_id(connector.resultset.getString("gm_id"));
                    gmId = Integer.parseInt(bean.getGm_id());
                }

                bean.setDesignation(DesignationjTextField.getText().trim());
                bean.setFromYear(YearFromjComboBox.getSelectedItem().toString());
                bean.setToYear(YearTojComboBox1.getSelectedItem().toString());
                bean.setYear(bean.getFromYear() + "-" + bean.getToYear());
                bean.setStatus(StatusjComboBox.getSelectedItem().toString());
                connector.connection.close();

                if ((gmId == -100) || (bean.getDesignation().equals(""))) {
                    //System.out.println("sare sobbonash");
                    JOptionPane.showMessageDialog(null, "Enter Valid Input\nPlease ");
                } else {
                    
                    //System.out.println("gm_id = " + gmId + " em_id = " + exe_member);
                    DbConnector connector1 = new DbConnector();
                    String UpdateQuery = "UPDATE executive_member " + " SET "
                            + "gm_id = '" + gmId + "', "
                            + "designation = '" + bean.getDesignation() + "' , "
                            + "year = '" + bean.getYear() + "' , "
                            + "status = '" + bean.getStatus() + "'  "
                            + "WHERE em_id = " + exe_member;
                    try {
                        connector1.statement.executeUpdate(UpdateQuery);
                        connector1.connection.close();
                        JOptionPane.showMessageDialog(null, "Successfully Updated...!!!");
                        DesignationjTextField.setText(" ");
                        this.showComboBox(AddGeneralMemberjComboBox);
                        DefaultTableModel model = (DefaultTableModel) ExecutivelMemberListjTable.getModel();
                        model.setRowCount(0);
                        PrintToTable();
                    } catch (SQLException ex) {
                        System.out.println(ex);
                        JOptionPane.showMessageDialog(null, "Data Doesn't Update...!!!");
                    }
                }
            } catch (SQLException ex) {
               System.out.println("test error");
            }
        }

        if (button.getSource() == DeletejButton) 
        {
            try {
                System.out.println(exe_member + " ");             
                DbConnector dbConnector = new DbConnector();
                String deleteQuery = "DELETE FROM executive_member "+ "WHERE em_id = " + "'" + exe_member + "'";
                dbConnector.statement.executeUpdate(deleteQuery);
                dbConnector.connection.close();
                JOptionPane.showMessageDialog(null, "Data Successfully Deleted...!!!");             
                DefaultTableModel model = (DefaultTableModel)ExecutivelMemberListjTable.getModel();
                model.setRowCount(0);
                PrintToTable();
            } catch (SQLException ex) {
                Logger.getLogger(ExecutiveMember.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        
    }

    public class information {

        public String em_id, name, designation, year, gm_id, mobile, status;
        public information(String em_id, String gm_id, String name, String designation,
            String year, String mobile, String status) {
            this.em_id = em_id;
            this.gm_id = gm_id;
            this.name = name;
            this.designation = designation;
            this.year = year;
            this.mobile = mobile;
            this.status = status;
        }
    }

    public ArrayList dataRetriveFromDatabase() throws SQLException {

        DbConnector connector = new DbConnector();
        String selectStudentQuery = "SELECT "
                + "executive_member.em_id, "
                + "general_member.gm_id, "
                + "general_member.name, "
                + "executive_member.designation, "
                + "executive_member.year, "
                + "general_member.phone, "
                + "executive_member.status "
                + "FROM executive_member " + "INNER JOIN "
                + "general_member ON general_member.gm_id = executive_member.gm_id "
                + "order by " + "general_member.gm_id";

        connector.resultset = connector.statement.executeQuery(selectStudentQuery);
        list = new ArrayList<information>();
        while (connector.resultset.next()) {
            String em_id = connector.resultset.getString("executive_member.em_id");
            String gm_id = connector.resultset.getString("general_member.gm_id");
            String name = connector.resultset.getString("general_member.name");
            String designation = connector.resultset.getString("executive_member.designation");
            String year = connector.resultset.getString("executive_member.year");
            String phone = connector.resultset.getString("general_member.phone");
            String status = connector.resultset.getString("executive_member.status");
            information info = new information(em_id, gm_id, name, designation, year, phone, status);

            list.add(info);
        }
        connector.connection.close();
        return list;
    }

    public void PrintToTable() throws SQLException {

        DefaultTableModel model = (DefaultTableModel) ExecutivelMemberListjTable.getModel();
        ArrayList<information> ReciveList = dataRetriveFromDatabase();
        Object rawData[] = new Object[7];

        for (int i = 0; i < ReciveList.size(); i++) {
            rawData[6] = ReciveList.get(i).em_id;
            rawData[0] = ReciveList.get(i).gm_id;
            rawData[1] = ReciveList.get(i).name;
            rawData[2] = ReciveList.get(i).designation;
            rawData[3] = ReciveList.get(i).year;
            rawData[4] = ReciveList.get(i).mobile;
            rawData[5] = ReciveList.get(i).status;

            model.addRow(rawData);
        }
    }

    public void showComboBox(JComboBox object) throws SQLException {
        String[] temp = new String[5000];

        String selectQuery = "SELECT general_member.gm_id," + " general_member.name "
                + "FROM general_member";
        DbConnector connector = new DbConnector();
        connector.resultset = connector.statement.executeQuery(selectQuery);
        int idx = 0;
        while (connector.resultset.next()) {
            temp[idx] = connector.resultset.getString("name").toString();
            idx += 1;
        }

        String[] generalMemberList = new String[idx + 5];
        int i = 0;
        while (i < idx) {
            generalMemberList[i] = temp[i];
            i++;
        }
        object.setModel(new DefaultComboBoxModel(generalMemberList));
        if (edit_exe_member) {
            object.insertItemAt(exe_current_name, 0);
        } else {
            object.insertItemAt("Slecet Member", 0);
        }

        object.setSelectedIndex(0);
        object.toString();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        ExecutivelMemberListjTable = new javax.swing.JTable();
        add_edit_delete_jPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        DesignationjTextField = new javax.swing.JTextField();
        AddjButton = new javax.swing.JButton();
        UpDatejButton = new javax.swing.JButton();
        DeletejButton = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        StatusjComboBox = new javax.swing.JComboBox();
        AddGeneralMemberjComboBox = new javax.swing.JComboBox();
        YearFromjComboBox = new javax.swing.JComboBox();
        YearTojComboBox1 = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        ExecutiveMeberjMenuBar = new javax.swing.JMenuBar();
        HomejMenu = new javax.swing.JMenu();
        HomejMenuItem = new javax.swing.JMenuItem();
        ExecutiveMemberjMenu = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        generalMemberjMenuItem = new javax.swing.JMenuItem();
        accountsInformationjMenu = new javax.swing.JMenu();
        accountsInfojMenuItem = new javax.swing.JMenuItem();
        memberfeejCheckBoxMenuItem = new javax.swing.JMenuItem();
        eventsjMenu = new javax.swing.JMenu();
        eventsjMenuItem = new javax.swing.JMenuItem();
        endMessagejMenu = new javax.swing.JMenu();
        sendMessagejMenuItem = new javax.swing.JMenuItem();
        previousMessagejMenuItem = new javax.swing.JMenuItem();
        SettingjMenu = new javax.swing.JMenu();
        adminSettingjMenuItem = new javax.swing.JMenuItem();
        DeveloperjMenu = new javax.swing.JMenu();
        developerjMenuItem = new javax.swing.JMenuItem();
        exitsjMenu = new javax.swing.JMenu();
        exitjMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        ExecutivelMemberListjTable.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        ExecutivelMemberListjTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Club ID", "Name", "Designation", "Year", "Mobile", "Status", "sl no."
            }
        ));
        ExecutivelMemberListjTable.setRowHeight(25);
        ExecutivelMemberListjTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ExecutivelMemberListjTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(ExecutivelMemberListjTable);
        if (ExecutivelMemberListjTable.getColumnModel().getColumnCount() > 0) {
            ExecutivelMemberListjTable.getColumnModel().getColumn(0).setMinWidth(55);
            ExecutivelMemberListjTable.getColumnModel().getColumn(0).setPreferredWidth(55);
            ExecutivelMemberListjTable.getColumnModel().getColumn(0).setMaxWidth(55);
            ExecutivelMemberListjTable.getColumnModel().getColumn(3).setMinWidth(100);
            ExecutivelMemberListjTable.getColumnModel().getColumn(3).setPreferredWidth(100);
            ExecutivelMemberListjTable.getColumnModel().getColumn(3).setMaxWidth(100);
            ExecutivelMemberListjTable.getColumnModel().getColumn(4).setMinWidth(120);
            ExecutivelMemberListjTable.getColumnModel().getColumn(4).setPreferredWidth(120);
            ExecutivelMemberListjTable.getColumnModel().getColumn(4).setMaxWidth(120);
            ExecutivelMemberListjTable.getColumnModel().getColumn(5).setMinWidth(90);
            ExecutivelMemberListjTable.getColumnModel().getColumn(5).setPreferredWidth(90);
            ExecutivelMemberListjTable.getColumnModel().getColumn(5).setMaxWidth(90);
            ExecutivelMemberListjTable.getColumnModel().getColumn(6).setMinWidth(60);
            ExecutivelMemberListjTable.getColumnModel().getColumn(6).setPreferredWidth(60);
            ExecutivelMemberListjTable.getColumnModel().getColumn(6).setMaxWidth(60);
        }

        add_edit_delete_jPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 0, 204), 2, true));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Name : ");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Designation : ");

        DesignationjTextField.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N

        AddjButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        AddjButton.setForeground(new java.awt.Color(0, 153, 0));
        AddjButton.setText("ADD");

        UpDatejButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        UpDatejButton.setForeground(new java.awt.Color(0, 153, 0));
        UpDatejButton.setText("UpDate");

        DeletejButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        DeletejButton.setForeground(new java.awt.Color(0, 153, 0));
        DeletejButton.setText("Delete");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Year : ");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("Status : ");

        StatusjComboBox.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        StatusjComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Current", "Previous" }));

        AddGeneralMemberjComboBox.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        AddGeneralMemberjComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        AddGeneralMemberjComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddGeneralMemberjComboBoxActionPerformed(evt);
            }
        });

        YearFromjComboBox.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        YearFromjComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30" }));

        YearTojComboBox1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        YearTojComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30" }));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("TO");

        javax.swing.GroupLayout add_edit_delete_jPanelLayout = new javax.swing.GroupLayout(add_edit_delete_jPanel);
        add_edit_delete_jPanel.setLayout(add_edit_delete_jPanelLayout);
        add_edit_delete_jPanelLayout.setHorizontalGroup(
            add_edit_delete_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(add_edit_delete_jPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(add_edit_delete_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(add_edit_delete_jPanelLayout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(AddGeneralMemberjComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(add_edit_delete_jPanelLayout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(DesignationjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(add_edit_delete_jPanelLayout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)
                        .addComponent(YearFromjComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(YearTojComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(add_edit_delete_jPanelLayout.createSequentialGroup()
                        .addGroup(add_edit_delete_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(add_edit_delete_jPanelLayout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(41, 41, 41)
                                .addComponent(StatusjComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(add_edit_delete_jPanelLayout.createSequentialGroup()
                                .addComponent(AddjButton)
                                .addGap(30, 30, 30)
                                .addComponent(DeletejButton)
                                .addGap(18, 18, 18)
                                .addComponent(UpDatejButton, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 3, Short.MAX_VALUE)))
                .addContainerGap())
        );
        add_edit_delete_jPanelLayout.setVerticalGroup(
            add_edit_delete_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(add_edit_delete_jPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(add_edit_delete_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AddGeneralMemberjComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(add_edit_delete_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DesignationjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(add_edit_delete_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(YearFromjComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(YearTojComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(add_edit_delete_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(StatusjComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(add_edit_delete_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AddjButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DeletejButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(UpDatejButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 153));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Executive Member");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        HomejMenu.setText("Home");
        HomejMenu.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N

        HomejMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.CTRL_MASK));
        HomejMenuItem.setText("Home");
        HomejMenu.add(HomejMenuItem);

        ExecutiveMeberjMenuBar.add(HomejMenu);

        ExecutiveMemberjMenu.setForeground(new java.awt.Color(0, 153, 51));
        ExecutiveMemberjMenu.setText(" Member");
        ExecutiveMemberjMenu.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem1.setForeground(new java.awt.Color(0, 153, 51));
        jMenuItem1.setText("Executive Member");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        ExecutiveMemberjMenu.add(jMenuItem1);

        generalMemberjMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.CTRL_MASK));
        generalMemberjMenuItem.setText("General Member");
        generalMemberjMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generalMemberjMenuItemActionPerformed(evt);
            }
        });
        ExecutiveMemberjMenu.add(generalMemberjMenuItem);

        ExecutiveMeberjMenuBar.add(ExecutiveMemberjMenu);

        accountsInformationjMenu.setText("Accounts");
        accountsInformationjMenu.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N

        accountsInfojMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        accountsInfojMenuItem.setText("accounts info.");
        accountsInformationjMenu.add(accountsInfojMenuItem);

        memberfeejCheckBoxMenuItem.setText("member fee");
        accountsInformationjMenu.add(memberfeejCheckBoxMenuItem);

        ExecutiveMeberjMenuBar.add(accountsInformationjMenu);

        eventsjMenu.setText("Events");
        eventsjMenu.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N

        eventsjMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        eventsjMenuItem.setText("events");
        eventsjMenu.add(eventsjMenuItem);

        ExecutiveMeberjMenuBar.add(eventsjMenu);

        endMessagejMenu.setText("Message");
        endMessagejMenu.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N

        sendMessagejMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.CTRL_MASK));
        sendMessagejMenuItem.setText("send message");
        endMessagejMenu.add(sendMessagejMenuItem);

        previousMessagejMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        previousMessagejMenuItem.setText("previous message");
        previousMessagejMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                previousMessagejMenuItemActionPerformed(evt);
            }
        });
        endMessagejMenu.add(previousMessagejMenuItem);

        ExecutiveMeberjMenuBar.add(endMessagejMenu);

        SettingjMenu.setText("Setting");
        SettingjMenu.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N

        adminSettingjMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        adminSettingjMenuItem.setText("admin setting");
        SettingjMenu.add(adminSettingjMenuItem);

        ExecutiveMeberjMenuBar.add(SettingjMenu);

        DeveloperjMenu.setText("Developer");
        DeveloperjMenu.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N

        developerjMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_MASK));
        developerjMenuItem.setText("information");
        DeveloperjMenu.add(developerjMenuItem);

        ExecutiveMeberjMenuBar.add(DeveloperjMenu);

        exitsjMenu.setText("Exit");
        exitsjMenu.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N

        exitjMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        exitjMenuItem.setText("exit");
        exitsjMenu.add(exitjMenuItem);

        ExecutiveMeberjMenuBar.add(exitsjMenu);

        setJMenuBar(ExecutiveMeberjMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(add_edit_delete_jPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 677, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(221, 221, 221))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(add_edit_delete_jPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 540, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void generalMemberjMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generalMemberjMenuItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_generalMemberjMenuItemActionPerformed

    private void previousMessagejMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_previousMessagejMenuItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_previousMessagejMenuItemActionPerformed

    private void AddGeneralMemberjComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddGeneralMemberjComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AddGeneralMemberjComboBoxActionPerformed

    private void ExecutivelMemberListjTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ExecutivelMemberListjTableMouseClicked
        // TODO add your handling code here:

        int rowNumber = ExecutivelMemberListjTable.getSelectedRow();
        edit_exe_member = true;
        TableModel model = ExecutivelMemberListjTable.getModel();

        DesignationjTextField.setText(model.getValueAt(rowNumber, 2).toString());
        exe_member = Integer.parseInt(model.getValueAt(rowNumber, 6).toString());
        exe_current_name = model.getValueAt(rowNumber, 1).toString();
        //System.out.println(exe_member + " ");
        try {
            this.showComboBox(AddGeneralMemberjComboBox);
        } catch (SQLException ex) {
            System.out.println("current error");
            Logger.getLogger(ExecutiveMember.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_ExecutivelMemberListjTableMouseClicked

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
               // /*
                try {
                    new ExecutiveMember(1).setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(ExecutiveMember.class.getName()).log(Level.SEVERE, null, ex);
                }
               // */
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox AddGeneralMemberjComboBox;
    private javax.swing.JButton AddjButton;
    private javax.swing.JButton DeletejButton;
    private javax.swing.JTextField DesignationjTextField;
    private javax.swing.JMenu DeveloperjMenu;
    private javax.swing.JMenuBar ExecutiveMeberjMenuBar;
    private javax.swing.JMenu ExecutiveMemberjMenu;
    private javax.swing.JTable ExecutivelMemberListjTable;
    private javax.swing.JMenu HomejMenu;
    private javax.swing.JMenuItem HomejMenuItem;
    private javax.swing.JMenu SettingjMenu;
    private javax.swing.JComboBox StatusjComboBox;
    private javax.swing.JButton UpDatejButton;
    private javax.swing.JComboBox YearFromjComboBox;
    private javax.swing.JComboBox YearTojComboBox1;
    private javax.swing.JMenuItem accountsInfojMenuItem;
    private javax.swing.JMenu accountsInformationjMenu;
    private javax.swing.JPanel add_edit_delete_jPanel;
    private javax.swing.JMenuItem adminSettingjMenuItem;
    private javax.swing.JMenuItem developerjMenuItem;
    private javax.swing.JMenu endMessagejMenu;
    private javax.swing.JMenu eventsjMenu;
    private javax.swing.JMenuItem eventsjMenuItem;
    private javax.swing.JMenuItem exitjMenuItem;
    private javax.swing.JMenu exitsjMenu;
    private javax.swing.JMenuItem generalMemberjMenuItem;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JMenuItem memberfeejCheckBoxMenuItem;
    private javax.swing.JMenuItem previousMessagejMenuItem;
    private javax.swing.JMenuItem sendMessagejMenuItem;
    // End of variables declaration//GEN-END:variables
}
