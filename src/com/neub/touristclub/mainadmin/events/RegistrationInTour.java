package com.neub.touristclub.mainadmin.events;

import com.neub.touristclub.bean.*;
import com.neub.touristclub.databaseconnection.DbConnector;
import com.neub.touristclub.developer.Developer;
import com.neub.touristclub.mainadmin.HomePage;
import com.neub.touristclub.mainadmin.accounts.AccountsInformation;
import com.neub.touristclub.mainadmin.accounts.MembersFee;
import com.neub.touristclub.mainadmin.adminsetting.Setting;
import com.neub.touristclub.mainadmin.members.ExecutiveMember;
import com.neub.touristclub.mainadmin.members.GeneralMember;
import com.neub.touristclub.message.PreviousMessage;
import com.neub.touristclub.message.SendMessage;

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
 * @author HP-Stream
 */
public class RegistrationInTour extends javax.swing.JFrame implements ActionListener {

    public static int current_reg_id, adminIdKey;
    public boolean isMember, isId, isEdit;
    public String tempName[] = new String[500];
    public String tempId[] = new String[500];
    public String current_name, current_id;
    public boolean isGuest;

    public ArrayList<EventsBean> ebs;

    public RegistrationInTour(int keyId) {
        try {
            adminIdKey = keyId;
            ebs = new ArrayList<>();
            initComponents();
            this.setLocationRelativeTo(null);
            this.setVisible(true);
            this.setResizable(false);

            addFeejButton.addActionListener(this);
            upDatejButton.addActionListener(this);
            deletejButton.addActionListener(this);

            getTourId();
            findRemainingSits();

            view_jButton.addActionListener(this);

            homejMenuItem.addActionListener(this);
            generalMemberjMenuItem.addActionListener(this);
            executiveMemberjMenuItem.addActionListener(this);
            accountsInfojMenuItem.addActionListener(this);
            memberfeejCheckBoxMenuItem.addActionListener(this);
            eventsjMenuItem.addActionListener(this);
            sendMessagejMenuItem.addActionListener(this);
            previousMessagejMenuItem.addActionListener(this);
            adminSettingjMenuItem.addActionListener(this);
            developerJMenuItem.addActionListener(this);
            exitjMenuItem.addActionListener(this);

            isMember = true;
            try {
                this.showComboBox(name_jComboBox);
            } catch (SQLException ex) {
                Logger.getLogger(RegistrationInTour.class.getName()).log(Level.SEVERE, null, ex);
            }
            isMember = false;

            isId = true;
            try {
                this.showComboBox(st_id_jComboBox);
            } catch (SQLException ex) {
                Logger.getLogger(RegistrationInTour.class.getName()).log(Level.SEVERE, null, ex);
            }
            isId = false;

            PrintToTable();
        } catch (SQLException ex) {
            Logger.getLogger(RegistrationInTour.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void showComboBox(JComboBox object) throws SQLException {
        int idx = 0;
        String selectStudentQuery = "SELECT * FROM general_member";
        DbConnector connector = new DbConnector();
        connector.resultset = connector.statement.executeQuery(selectStudentQuery);

        while (connector.resultset.next()) {
            tempName[idx] = connector.resultset.getString("name").toString();
            tempId[idx] = connector.resultset.getString("student_id").toString();
            idx++;
        }
        connector.connection.close();

        if (isMember) {
            object.setModel(new DefaultComboBoxModel(tempName));
            if (isEdit) {
                object.insertItemAt(current_name, 0);
            } else {
                object.insertItemAt("--Slecet Member--", 0);
            }

            object.setSelectedIndex(0);
            object.toString();
        }

        if (isId) {
            object.setModel(new DefaultComboBoxModel(tempId));
            if (isEdit) {
                object.insertItemAt(current_id, 0);
                object.insertItemAt("--Slecet Member--", 1);
            } else {
                object.insertItemAt("--Slecet Member--", 0);
            }
            object.setSelectedIndex(0);
            object.toString();
        }
    }

    public void findRemainingSits() {
        int tempNumber = 0;
        try {
            String query = "SELECT * FROM registration_for_tour";
            DbConnector connector = new DbConnector();
            connector.resultset = connector.statement.executeQuery(query);

            while (connector.resultset.next()) {
                tempNumber++;
            }
            connector.connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(RegistrationInTour.class.getName()).log(Level.SEVERE, null, ex);
        }

        int remainigSit = (ebs.get(0).getTotal_capacity()) - tempNumber;
        numberOfRemainSetsLabel.setText(remainigSit + "");
    }

    public void getTourId() {
        try {
            String query = "SELECT * FROM events_information WHERE status = '" + 1 + "'";
            DbConnector connector = new DbConnector();
            connector.resultset = connector.statement.executeQuery(query);

            while (connector.resultset.next()) {
                int ev_id = connector.resultset.getInt("ev_id");
                String title = connector.resultset.getString("title");
                String start_date = connector.resultset.getString("start_date");
                String end_date = connector.resultset.getString("end_date");
                String type = connector.resultset.getString("type");
                int total_capacity = connector.resultset.getInt("total_capacity");
                int fee = connector.resultset.getInt("fee");
                String spot_id = connector.resultset.getString("spot_id");
                int status = connector.resultset.getInt("status");

                EventsBean bean = new EventsBean(ev_id, title, start_date, end_date, type, total_capacity, fee, spot_id, status);

                ebs.add(bean);
            }
            connector.connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(RegistrationInTour.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getGeneralMemberId() {
        try {
            String temp_name = name_jComboBox.getSelectedItem().toString();
            String temp_id = st_id_jComboBox.getSelectedItem().toString();
            int gm_id1 = 0, gm_id2 = 0;

            String selectStudentQuery = "SELECT * FROM general_member WHERE name = '" + temp_name + "'";
            DbConnector connector = new DbConnector();
            connector.resultset = connector.statement.executeQuery(selectStudentQuery);
            while (connector.resultset.next()) {
                gm_id1 = connector.resultset.getInt("gm_id"); //.toString();
            }
            connector.connection.close();

            String selectStudentQuery1 = "SELECT * FROM general_member WHERE student_id = '" + temp_id + "'";
            connector = new DbConnector();
            connector.resultset = connector.statement.executeQuery(selectStudentQuery1);
            while (connector.resultset.next()) {
                gm_id2 = connector.resultset.getInt("gm_id"); //.toString();
            }
            connector.connection.close();

            isGuest = guest_jRadioButton.isSelected();
            //System.out.println("answer = " + b);

            if ((gm_id1 != 0) && (gm_id2 == 0) && (!isGuest)) {
                return gm_id1;
            }

            if ((gm_id2 != 0) && (gm_id1 == 0) && (!isGuest)) {
                return gm_id2;
            }

            if ((gm_id1 == gm_id2) && (isGuest == false)) {
                return gm_id1;
            }

            if (isGuest) {
                if (gm_id1 != 0) {
                    return gm_id1;
                } else if (gm_id2 != 0) {
                    return gm_id2;
                }
            }

            return 0;
        } catch (SQLException ex) {
            Logger.getLogger(RegistrationInTour.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public ArrayList dataRetriveFromDatabase() throws SQLException {
        ArrayList<EventsBean> listData;
        DbConnector connector = new DbConnector();

        String selectQuery = "SELECT * FROM registration_for_tour rft INNER JOIN "
                + " general_member gm ON gm.gm_id = rft.gm_id order by rft.reg_id";

        connector.resultset = connector.statement.executeQuery(selectQuery);

        listData = new ArrayList<EventsBean>();

        while (connector.resultset.next()) {
            int reg_id = connector.resultset.getInt("rft.reg_id");
            int payment = connector.resultset.getInt("rft.payment");
            String st_id = connector.resultset.getString("gm.student_id");
            String name = connector.resultset.getString("gm.name");
            String phone = connector.resultset.getString("gm.phone");
            int guest = connector.resultset.getInt("guest");
            int adm_id = connector.resultset.getInt("admin_id");
            //System.out.println(reg_id + "\t" + payment + "\t" + st_id + "\t"
            //      + name  + "\t" + phone);            
            EventsBean eb = new EventsBean(reg_id, payment, st_id, name, phone, guest, adm_id);
            listData.add(eb);
        }
        connector.connection.close();
        return listData;
    }

    public String getAdminName(int temp) {
        String admin_name = null;
        try {
            String queryForAdminName = "SELECT * FROM admin WHERE admin_id = '" + temp + "'";

            DbConnector connector = new DbConnector();
            connector.resultset = connector.statement.executeQuery(queryForAdminName);
            while (connector.resultset.next()) {
                admin_name = connector.resultset.getString("user_name").toString();
            }
            connector.connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(RegistrationInTour.class.getName()).log(Level.SEVERE, null, ex);
        }

        return admin_name;
    }

    public void PrintToTable() throws SQLException {

        DefaultTableModel model = (DefaultTableModel) registrationjTable.getModel();
        ArrayList<EventsBean> ReciveList = dataRetriveFromDatabase();
        Object rawData[] = new Object[7];

        for (int i = 0; i < ReciveList.size(); i++) {
            rawData[0] = ReciveList.get(i).getEv_id();
            rawData[1] = ReciveList.get(i).getType();

            if (ReciveList.get(i).getGuest() == 0) {
                rawData[2] = ReciveList.get(i).getTitle();
            } else {
                rawData[2] = "Guest";
            }

            rawData[3] = ReciveList.get(i).getTotal_capacity();

            rawData[4] = ebs.get(0).getFee() - ReciveList.get(i).getTotal_capacity();
            rawData[5] = ReciveList.get(i).getSpot_id();

            rawData[6] = getAdminName(ReciveList.get(i).getFee());

            model.addRow(rawData);
        }
    }

    public int checkAmount(String amount) {
        try {
            Integer.parseInt(amount);
            return Integer.parseInt(amount);
        } catch (NumberFormatException e) {
            return -10;
        }
    }

    @Override
    public void actionPerformed(ActionEvent button) {

        if (button.getSource() == deletejButton) {
            try {
                RegistrationBean bean = new RegistrationBean();
                bean.setReg_id(current_reg_id);

                DbConnector dbConnectorDelete = new DbConnector();
                String deleteQuery = "DELETE FROM registration_for_tour " + "WHERE reg_id = " + "'" + bean.getReg_id() + "'";
                dbConnectorDelete.statement.executeUpdate(deleteQuery);
                dbConnectorDelete.connection.close();

                JOptionPane.showMessageDialog(null, "Successfully Delete...!!!");

                DefaultTableModel model = (DefaultTableModel) registrationjTable.getModel();
                model.setRowCount(0);
                PrintToTable();
                findRemainingSits();

                isMember = true;
                this.showComboBox(name_jComboBox);
                isMember = false;

                isId = true;
                this.showComboBox(st_id_jComboBox);
                isId = false;
                current_reg_id = 0;
                payment_jTextField.setText("");

            } catch (SQLException ex) {
                Logger.getLogger(RegistrationInTour.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        if (button.getSource() == upDatejButton) {
            RegistrationBean bean = new RegistrationBean();
            bean.setReg_id(current_reg_id);
            bean.setGm_id(getGeneralMemberId());
            bean.setEv_id(ebs.get(0).getEv_id());
            bean.setPayment(checkAmount(payment_jTextField.getText().trim()));
            if (isGuest) {
                bean.setGuset(1);
            } else {
                bean.setGuset(0);
            }

            bean.setAdmin_id(adminIdKey);

            if ((bean.getGm_id() == 0) || (bean.getPayment() == -10)) {
                JOptionPane.showMessageDialog(null, "Fill Information Properly...!!!");
            } else {
                try {

                    DbConnector connectorUpDateMain = new DbConnector();
                    String UpdateQuery;
                    UpdateQuery = "UPDATE registration_for_tour " + " SET "
                            + " gm_id = '" + bean.getGm_id()
                            + "', ev_id = '" + bean.getEv_id()
                            + "', payment = '" + bean.getPayment()
                            + "', guest = '" + bean.getGuset()
                            + "', admin_id = '" + bean.getAdmin_id()
                            + "' WHERE reg_id = " + bean.getReg_id();

                    connectorUpDateMain.statement.executeUpdate(UpdateQuery);
                    connectorUpDateMain.connection.close();
                    JOptionPane.showMessageDialog(null, "Successfully UpDate...!!!");

                    DefaultTableModel model = (DefaultTableModel) registrationjTable.getModel();
                    model.setRowCount(0);
                    PrintToTable();
                    findRemainingSits();

                    isMember = true;
                    this.showComboBox(name_jComboBox);
                    isMember = false;

                    isId = true;
                    this.showComboBox(st_id_jComboBox);
                    isId = false;
                    current_reg_id = 0;
                    payment_jTextField.setText("");
                    //payment_jTextField1.setText("");
                } catch (SQLException ex) {
                    Logger.getLogger(RegistrationInTour.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }

        if (button.getSource() == addFeejButton) {
            RegistrationBean bean = new RegistrationBean();

            bean.setGm_id(getGeneralMemberId());
            bean.setEv_id(ebs.get(0).getEv_id());
            bean.setPayment(checkAmount(payment_jTextField.getText().trim()));
            if (isGuest) {
                bean.setGuset(1);
            } else {
                bean.setGuset(0);
            }
            bean.setAdmin_id(adminIdKey);

            if ((bean.getGm_id() == 0) || (bean.getPayment() == -10)) {
                JOptionPane.showMessageDialog(null, "Fill Information Properly...!!!");
            } else {
                try {
                    DbConnector connectorIn = new DbConnector();

                    String insertQuery = "INSERT INTO registration_for_tour "
                            + "(gm_id, ev_id, payment, guest, admin_id) VALUES ('"
                            + bean.getGm_id() + "', '" + bean.getEv_id() + "', '"
                            + bean.getPayment() + "', '" + bean.getGuset() + "', '"
                            + bean.getAdmin_id() + "')";

                    connectorIn.statement.executeUpdate(insertQuery);
                    connectorIn.connection.close();
                    JOptionPane.showMessageDialog(null, "Successfully Added...!!!");

                    DefaultTableModel model = (DefaultTableModel) registrationjTable.getModel();
                    model.setRowCount(0);
                    PrintToTable();
                    findRemainingSits();

                    isMember = true;
                    this.showComboBox(name_jComboBox);
                    isMember = false;

                    isId = true;
                    this.showComboBox(st_id_jComboBox);
                    isId = false;
                    current_reg_id = 0;
                    payment_jTextField.setText("");
                    //payment_jTextField1.setText("");
                } catch (SQLException ex) {
                    Logger.getLogger(RegistrationInTour.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        if (button.getSource() == view_jButton) {
            boolean monthList[] = new boolean[12];
            ViewInformation vi = new ViewInformation(ebs, adminIdKey);
        }

        //menu
        if (button.getSource() == homejMenuItem) {
            try {
                HomePage homePage = new HomePage(adminIdKey);
                this.dispose();
            } catch (Exception e) {
            }
        }

        if (button.getSource() == generalMemberjMenuItem) {
            try {
                GeneralMember generalMember = new GeneralMember(adminIdKey);
                this.dispose();
            } catch (SQLException ex) {
                Logger.getLogger(GeneralMember.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (button.getSource() == executiveMemberjMenuItem) {
            try {
                ExecutiveMember em = new ExecutiveMember(adminIdKey);
                this.dispose();
            } catch (SQLException ex) {
                Logger.getLogger(ExecutiveMember.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if ((button.getSource() == accountsInfojMenuItem)) {
            try {
                AccountsInformation accountsInformation = new AccountsInformation(adminIdKey);
                this.dispose();
            } catch (Exception e) {
            }
        }

        if (button.getSource() == memberfeejCheckBoxMenuItem) {
            try {
                MembersFee fee = new MembersFee(adminIdKey);
                this.dispose();
            } catch (Exception e) {
            }
        }

        if ((button.getSource() == eventsjMenuItem)) {
            try {
                EventsInformation ei = new EventsInformation(adminIdKey);
                this.dispose();
            } catch (Exception e) {
            }
        }

        if ((button.getSource() == sendMessagejMenuItem)) {
            try {
                SendMessage message = new SendMessage(adminIdKey);
                this.dispose();
            } catch (Exception e) {
            }
        }

        if (button.getSource() == previousMessagejMenuItem) {
            try {
                PreviousMessage pm = new PreviousMessage(adminIdKey);
                this.dispose();
            } catch (Exception e) {
            }
        }

        if ((button.getSource() == adminSettingjMenuItem)) {
            try {
                Setting setting = new Setting(adminIdKey);
                this.dispose();
            } catch (Exception e) {
            }
        }

        if (button.getSource() == developerJMenuItem) {
            try {
                Developer d = new Developer(adminIdKey);
                this.dispose();
            } catch (Exception e) {
            }
        }

        if (button.getSource() == exitjMenuItem) {
            try {
                this.dispose();
            } catch (Exception e) {
            }
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        registrationjTable = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        addFeejButton = new javax.swing.JButton();
        upDatejButton = new javax.swing.JButton();
        deletejButton = new javax.swing.JButton();
        st_id_jComboBox = new javax.swing.JComboBox();
        name_jComboBox = new javax.swing.JComboBox();
        payment_jTextField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        guest_jRadioButton = new javax.swing.JRadioButton();
        jPanel3 = new javax.swing.JPanel();
        numberOfRemainSetsLabel = new javax.swing.JLabel();
        view_jButton = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        AccountsjMenuBar = new javax.swing.JMenuBar();
        HomejMenu = new javax.swing.JMenu();
        homejMenuItem = new javax.swing.JMenuItem();
        ExecutiveMemberjMenu = new javax.swing.JMenu();
        executiveMemberjMenuItem = new javax.swing.JMenuItem();
        generalMemberjMenuItem = new javax.swing.JMenuItem();
        accountsInformationjMenu = new javax.swing.JMenu();
        accountsInfojMenuItem = new javax.swing.JMenuItem();
        memberfeejCheckBoxMenuItem = new javax.swing.JMenuItem();
        eventsjMenu = new javax.swing.JMenu();
        eventsjMenuItem = new javax.swing.JMenuItem();
        resitrationjMenuItem = new javax.swing.JMenuItem();
        endMessagejMenu = new javax.swing.JMenu();
        sendMessagejMenuItem = new javax.swing.JMenuItem();
        previousMessagejMenuItem = new javax.swing.JMenuItem();
        SettingjMenu = new javax.swing.JMenu();
        adminSettingjMenuItem = new javax.swing.JMenuItem();
        DeveloperjMenu = new javax.swing.JMenu();
        developerJMenuItem = new javax.swing.JMenuItem();
        exitsjMenu = new javax.swing.JMenu();
        exitjMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 153), 3));

        registrationjTable.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        registrationjTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "sl.", "name", "id", "payment", "due", "mobile", "admin"
            }
        ));
        registrationjTable.setRowHeight(25);
        registrationjTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                registrationjTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(registrationjTable);
        if (registrationjTable.getColumnModel().getColumnCount() > 0) {
            registrationjTable.getColumnModel().getColumn(0).setMinWidth(35);
            registrationjTable.getColumnModel().getColumn(0).setPreferredWidth(35);
            registrationjTable.getColumnModel().getColumn(0).setMaxWidth(35);
            registrationjTable.getColumnModel().getColumn(2).setMinWidth(110);
            registrationjTable.getColumnModel().getColumn(2).setPreferredWidth(110);
            registrationjTable.getColumnModel().getColumn(2).setMaxWidth(110);
            registrationjTable.getColumnModel().getColumn(3).setMinWidth(85);
            registrationjTable.getColumnModel().getColumn(3).setPreferredWidth(85);
            registrationjTable.getColumnModel().getColumn(3).setMaxWidth(85);
            registrationjTable.getColumnModel().getColumn(4).setMinWidth(85);
            registrationjTable.getColumnModel().getColumn(4).setPreferredWidth(85);
            registrationjTable.getColumnModel().getColumn(4).setMaxWidth(85);
            registrationjTable.getColumnModel().getColumn(5).setMinWidth(110);
            registrationjTable.getColumnModel().getColumn(5).setPreferredWidth(110);
            registrationjTable.getColumnModel().getColumn(5).setMaxWidth(110);
            registrationjTable.getColumnModel().getColumn(6).setMinWidth(90);
            registrationjTable.getColumnModel().getColumn(6).setPreferredWidth(90);
            registrationjTable.getColumnModel().getColumn(6).setMaxWidth(90);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 678, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 449, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 153), 3));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Name :");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("   OR");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Id : ");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Payment : ");

        addFeejButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        addFeejButton.setForeground(new java.awt.Color(0, 153, 31));
        addFeejButton.setText("ADD");

        upDatejButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        upDatejButton.setForeground(new java.awt.Color(0, 153, 31));
        upDatejButton.setText("UpDate");
        upDatejButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                upDatejButtonActionPerformed(evt);
            }
        });

        deletejButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        deletejButton.setForeground(new java.awt.Color(0, 153, 31));
        deletejButton.setText("Delete");

        st_id_jComboBox.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N

        name_jComboBox.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N

        payment_jTextField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("   OR");

        guest_jRadioButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        guest_jRadioButton.setText("Guest");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                        .addComponent(payment_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(addFeejButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(upDatejButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(74, 74, 74)
                                .addComponent(deletejButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(name_jComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(st_id_jComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(guest_jRadioButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(st_id_jComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(name_jComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(guest_jRadioButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(payment_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addFeejButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(upDatejButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(deletejButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(75, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 153), 2));

        numberOfRemainSetsLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        view_jButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        view_jButton.setText("view tour");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Number of  Sets Remain :");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(numberOfRemainSetsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(view_jButton, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(view_jButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(numberOfRemainSetsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        HomejMenu.setText("Home");
        HomejMenu.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N

        homejMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.CTRL_MASK));
        homejMenuItem.setText("Home");
        HomejMenu.add(homejMenuItem);

        AccountsjMenuBar.add(HomejMenu);

        ExecutiveMemberjMenu.setText(" Member");
        ExecutiveMemberjMenu.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N

        executiveMemberjMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.ALT_MASK));
        executiveMemberjMenuItem.setText("Executive Member");
        executiveMemberjMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                executiveMemberjMenuItemActionPerformed(evt);
            }
        });
        ExecutiveMemberjMenu.add(executiveMemberjMenuItem);

        generalMemberjMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.CTRL_MASK));
        generalMemberjMenuItem.setText("General Member");
        generalMemberjMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generalMemberjMenuItemActionPerformed(evt);
            }
        });
        ExecutiveMemberjMenu.add(generalMemberjMenuItem);

        AccountsjMenuBar.add(ExecutiveMemberjMenu);

        accountsInformationjMenu.setText("Accounts");
        accountsInformationjMenu.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N

        accountsInfojMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        accountsInfojMenuItem.setText("accounts info.");
        accountsInformationjMenu.add(accountsInfojMenuItem);

        memberfeejCheckBoxMenuItem.setText("member fees");
        accountsInformationjMenu.add(memberfeejCheckBoxMenuItem);

        AccountsjMenuBar.add(accountsInformationjMenu);

        eventsjMenu.setText("Events");
        eventsjMenu.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N

        eventsjMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        eventsjMenuItem.setText("events");
        eventsjMenu.add(eventsjMenuItem);

        resitrationjMenuItem.setForeground(new java.awt.Color(0, 153, 51));
        resitrationjMenuItem.setText("registration");
        eventsjMenu.add(resitrationjMenuItem);

        AccountsjMenuBar.add(eventsjMenu);

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

        AccountsjMenuBar.add(endMessagejMenu);

        SettingjMenu.setText("Setting");
        SettingjMenu.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N

        adminSettingjMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        adminSettingjMenuItem.setText("admin setting");
        SettingjMenu.add(adminSettingjMenuItem);

        AccountsjMenuBar.add(SettingjMenu);

        DeveloperjMenu.setText("Developer");
        DeveloperjMenu.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N

        developerJMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_MASK));
        developerJMenuItem.setText("information");
        DeveloperjMenu.add(developerJMenuItem);

        AccountsjMenuBar.add(DeveloperjMenu);

        exitsjMenu.setText("Exit");
        exitsjMenu.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N

        exitjMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        exitjMenuItem.setText("exit");
        exitsjMenu.add(exitjMenuItem);

        AccountsjMenuBar.add(exitsjMenu);

        setJMenuBar(AccountsjMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(10, 10, 10)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void executiveMemberjMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_executiveMemberjMenuItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_executiveMemberjMenuItemActionPerformed

    private void generalMemberjMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generalMemberjMenuItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_generalMemberjMenuItemActionPerformed

    private void previousMessagejMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_previousMessagejMenuItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_previousMessagejMenuItemActionPerformed

    private void upDatejButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_upDatejButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_upDatejButtonActionPerformed

    private void registrationjTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_registrationjTableMouseClicked
        try {
            // TODO add your handling code here:            
            int rowNumber = registrationjTable.getSelectedRow();
            TableModel model = registrationjTable.getModel();

            current_reg_id = Integer.parseInt(model.getValueAt(rowNumber, 0).toString());
            current_name = model.getValueAt(rowNumber, 1).toString();
            current_id = model.getValueAt(rowNumber, 2).toString();

            isEdit = true; //false korte hobe

            isMember = true;
            this.showComboBox(name_jComboBox);
            isMember = false;

            isId = true;
            this.showComboBox(st_id_jComboBox);
            isId = false;

            isEdit = false;

            payment_jTextField.setText((model.getValueAt(rowNumber, 3)).toString());

        } catch (SQLException ex) {
            Logger.getLogger(RegistrationInTour.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_registrationjTableMouseClicked

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(RegistrationInTour.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegistrationInTour.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegistrationInTour.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegistrationInTour.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RegistrationInTour(1).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar AccountsjMenuBar;
    private javax.swing.JMenu DeveloperjMenu;
    private javax.swing.JMenu ExecutiveMemberjMenu;
    private javax.swing.JMenu HomejMenu;
    private javax.swing.JMenu SettingjMenu;
    private javax.swing.JMenuItem accountsInfojMenuItem;
    private javax.swing.JMenu accountsInformationjMenu;
    private javax.swing.JButton addFeejButton;
    private javax.swing.JMenuItem adminSettingjMenuItem;
    private javax.swing.JButton deletejButton;
    private javax.swing.JMenuItem developerJMenuItem;
    private javax.swing.JMenu endMessagejMenu;
    private javax.swing.JMenu eventsjMenu;
    private javax.swing.JMenuItem eventsjMenuItem;
    private javax.swing.JMenuItem executiveMemberjMenuItem;
    private javax.swing.JMenuItem exitjMenuItem;
    private javax.swing.JMenu exitsjMenu;
    private javax.swing.JMenuItem generalMemberjMenuItem;
    private javax.swing.JRadioButton guest_jRadioButton;
    private javax.swing.JMenuItem homejMenuItem;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JMenuItem memberfeejCheckBoxMenuItem;
    private javax.swing.JComboBox name_jComboBox;
    private javax.swing.JLabel numberOfRemainSetsLabel;
    private javax.swing.JTextField payment_jTextField;
    private javax.swing.JMenuItem previousMessagejMenuItem;
    private javax.swing.JTable registrationjTable;
    private javax.swing.JMenuItem resitrationjMenuItem;
    private javax.swing.JMenuItem sendMessagejMenuItem;
    private javax.swing.JComboBox st_id_jComboBox;
    private javax.swing.JButton upDatejButton;
    private javax.swing.JButton view_jButton;
    // End of variables declaration//GEN-END:variables
}