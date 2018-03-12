package com.neub.touristclub.mainadmin.adminsetting;

import com.neub.touristclub.mainadmin.HomePage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.neub.touristclub.bean.SettingBean;
import com.neub.touristclub.databaseconnection.DbConnector;

import com.neub.touristclub.developer.Developer;
import com.neub.touristclub.mainadmin.accounts.AccountsInformation;
import com.neub.touristclub.mainadmin.accounts.MembersFee;
import com.neub.touristclub.mainadmin.events.EventsInformation;
import com.neub.touristclub.mainadmin.members.ExecutiveMember;
import com.neub.touristclub.mainadmin.members.GeneralMember;
import com.neub.touristclub.message.PreviousMessage;
import com.neub.touristclub.message.SendMessage;
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
 * @author Team-R&D_v..3
 */
public class Setting extends javax.swing.JFrame implements ActionListener{

    public static  int adminIdKey;
    
    public static int current_id;
    public String current_item;
    public boolean edit_item;
    
    public Setting(int key) throws SQLException {
        adminIdKey = key;
        initComponents();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        
        PrintToTableAdmin();
        edit_item = false;
        this.showComboBox(adminTypejComboBox);
        
        addjButton.addActionListener(this);
        upDatejButton.addActionListener(this);
        deletejButton.addActionListener(this);
        
        //menu handeling
        homejMenuItem.addActionListener(this);
        generalMemberjMenuItem.addActionListener(this);
        executiveMemberjMenuItem.addActionListener(this);
        memberfeejCheckBoxMenuItem.addActionListener(this);
        accountsInfojMenuItem.addActionListener(this);
        eventsjMenuItem.addActionListener(this);
        sendMessagejMenuItem.addActionListener(this);
        previousMessagejMenuItem.addActionListener(this);
        DeveloperjMenuItem.addActionListener(this);
        exitjMenuItem.addActionListener(this);       
    }
    
     @Override
    public void actionPerformed(ActionEvent button) {
    
        
        // Menu item implement
        if(button.getSource() == homejMenuItem)
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
        
        if(button.getSource() == executiveMemberjMenuItem)
        {
            try {
                ExecutiveMember em = new ExecutiveMember(adminIdKey);
                this.dispose();
            } catch (SQLException ex) {
                Logger.getLogger(ExecutiveMember.class.getName()).log(Level.SEVERE, null, ex);
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
        
        if( (button.getSource() == DeveloperjMenuItem) )
        {
            try {
                Developer d = new Developer(adminIdKey);
                //Deve setting = new Setting(adminIdKey);
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
        
        
        if(button.getSource() == addjButton)
        {
            SettingBean bean = new SettingBean();
            
            bean.setUser_name(userNamejTextField.getText().trim());          

            if(adminTypejComboBox.getSelectedItem().toString().equals("Main Admin"))
                bean.setAdmin_type("main_admin");
            else if(adminTypejComboBox.getSelectedItem().toString().equals("Frontend Admin"))
                bean.setAdmin_type("front_admin");
            else
                bean.setAdmin_type("null");

            bean.setPassword(passwordjTextField.getText().trim());
            
            if( (bean.getUser_name().equals("")) || (bean.getAdmin_type().equals("null")) 
                    || (bean.getPassword().equals("")) ){
                System.out.println("error");
                JOptionPane.showMessageDialog(null, "Fill Information Properly...!!!");
            }
            else{
                try {
                    DbConnector connector = new DbConnector();
                    String insertQuery  = "INSERT INTO admin " + "(user_name, "
                            + " admin_type, password) VALUES ('" + bean.getUser_name()
                            + "', '" + bean.getAdmin_type() + "', '" +bean.getPassword() + "')";

                    //System.out.println("talia");
                    
                    current_id = 0;
                    edit_item = false;
                    this.showComboBox(adminTypejComboBox);
                    userNamejTextField.setText("");
                    passwordjTextField.setText("");
                    
                    connector.statement.executeUpdate(insertQuery);
                    connector.connection.close();
                    DefaultTableModel model = (DefaultTableModel) adminPenalrListjTable.getModel();
                    model.setRowCount(0);
                    PrintToTableAdmin();
                    JOptionPane.showMessageDialog(null, "Successfully Added...!!!");
                } catch (SQLException ex) {
                    Logger.getLogger(Setting.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        if(button.getSource() == upDatejButton)
        {
            SettingBean bean = new SettingBean();
            
            bean.setAdmin_id(current_id);
            bean.setUser_name(userNamejTextField.getText().trim());
            if((adminTypejComboBox.getSelectedItem().toString()).equals("Main Admin"))                
                bean.setAdmin_type("main_admin");
            else
                bean.setAdmin_type("front_admin");
            
            bean.setPassword(passwordjTextField.getText().trim());
            
            /*
            System.out.println(bean.getAdmin_id() +  ".  " +
                    bean.getUser_name() + ".  " + bean.getAdmin_type()+ ".  " +
                    bean.getPassword());
            */
            if( (bean.getUser_name().equals("")) || (bean.getAdmin_type().equals("null")) 
                    || (bean.getPassword().equals("")) ){
                System.out.println("error");
                JOptionPane.showMessageDialog(null, "Fill Information Properly...!!!");
            }
            else{   
                DbConnector dbConnector = new DbConnector();
                String UpdateQuery = "UPDATE admin " + " SET "  + "user_name = '" + 
                        bean.getUser_name()+ "', " + "admin_type = '" + 
                        bean.getAdmin_type()+ "', " + "password = '" +
                        bean.getPassword()+ "' WHERE admin_id = " + 
                        "'" + bean.getAdmin_id() + "'";
                try 
                {
                    dbConnector.statement.executeUpdate(UpdateQuery);
                    dbConnector.connection.close();
                    JOptionPane.showMessageDialog(null, "Successfullt Updated...!!!");
                    current_id = 0;
                    edit_item = false;
                    this.showComboBox(adminTypejComboBox);
                    userNamejTextField.setText("");
                    passwordjTextField.setText("");
                    DefaultTableModel model = (DefaultTableModel) adminPenalrListjTable.getModel();
                    model.setRowCount(0);
                    PrintToTableAdmin();
                } catch (SQLException ex) 
                {
                    System.out.println(ex);
                    JOptionPane.showMessageDialog(null, "Data Doesn't Update...!!!");
                }
            }
        }
        
        if(button.getSource() == deletejButton)
        {
            try {
                SettingBean bean = new SettingBean();
                bean.setAdmin_id(current_id);
                DbConnector dbConnector = new DbConnector();
                String deleteQuery = "DELETE FROM admin "+ " WHERE admin_id = " + "'" + bean.getAdmin_id() + "'";
                dbConnector.statement.executeUpdate(deleteQuery);
                dbConnector.connection.close();
                JOptionPane.showMessageDialog(null, "Data Successfully Deleted...!!!");
                
                current_id = 0;
                edit_item = false;
                this.showComboBox(adminTypejComboBox);
                userNamejTextField.setText("");
                passwordjTextField.setText("");
                DefaultTableModel model = (DefaultTableModel) adminPenalrListjTable.getModel();
                model.setRowCount(0);
                PrintToTableAdmin();
                
            } catch (SQLException ex) {
                Logger.getLogger(Setting.class.getName()).log(Level.SEVERE, null, ex);
            }
                
        }
    }   
    
    public ArrayList dataRetriveFromDatabaseForFee() throws SQLException {

        DbConnector connectorShow = new DbConnector();
        String selectFeeQuery = "SELECT * FROM admin";
        
        connectorShow.resultset = connectorShow.statement.executeQuery(selectFeeQuery);
        
        ArrayList<SettingBean> listFee = new ArrayList<SettingBean>();
        
        while (connectorShow.resultset.next()) {
            int id = connectorShow.resultset.getInt("admin_id");
            String name = connectorShow.resultset.getString("user_name");
            String type = connectorShow.resultset.getString("admin_type");
            String password = connectorShow.resultset.getString("password");
            
            SettingBean sb = new SettingBean(id, name, type, password);
                  
            listFee.add(sb);
        }
        connectorShow.connection.close();
        return listFee;
    }

    
    public void PrintToTableAdmin() throws SQLException {
        DefaultTableModel model = (DefaultTableModel) adminPenalrListjTable.getModel();
        ArrayList<SettingBean> adminListReceive = dataRetriveFromDatabaseForFee();
        Object rawData[] = new Object[4];

        for (int i = 0; i < adminListReceive.size(); i++) {
            rawData[0] = adminListReceive.get(i).getAdmin_id();
            rawData[1] = adminListReceive.get(i).getUser_name();
            if( (adminListReceive.get(i).getAdmin_type()).equals("main_admin") )                
                rawData[2] = "Main Admin";
            else
                rawData[2] = "Frontend Admin";
            
            rawData[3] = adminListReceive.get(i).getPassword();
            model.addRow(rawData);
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        adminPenalrListjTable = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        userNamejTextField = new javax.swing.JTextField();
        passwordjTextField = new javax.swing.JTextField();
        adminTypejComboBox = new javax.swing.JComboBox();
        addjButton = new javax.swing.JButton();
        deletejButton = new javax.swing.JButton();
        upDatejButton = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        SettingjMenuBar = new javax.swing.JMenuBar();
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
        endMessagejMenu = new javax.swing.JMenu();
        sendMessagejMenuItem = new javax.swing.JMenuItem();
        previousMessagejMenuItem = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem8 = new javax.swing.JMenuItem();
        DeveloperjMenu = new javax.swing.JMenu();
        DeveloperjMenuItem = new javax.swing.JMenuItem();
        exitsjMenu = new javax.swing.JMenu();
        exitjMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 153), 2, true));

        adminPenalrListjTable.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        adminPenalrListjTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "sl", "User Name", "Type", "Password"
            }
        ));
        adminPenalrListjTable.setRowHeight(25);
        adminPenalrListjTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                adminPenalrListjTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(adminPenalrListjTable);
        if (adminPenalrListjTable.getColumnModel().getColumnCount() > 0) {
            adminPenalrListjTable.getColumnModel().getColumn(0).setMinWidth(40);
            adminPenalrListjTable.getColumnModel().getColumn(0).setPreferredWidth(40);
            adminPenalrListjTable.getColumnModel().getColumn(0).setMaxWidth(40);
            adminPenalrListjTable.getColumnModel().getColumn(2).setMinWidth(150);
            adminPenalrListjTable.getColumnModel().getColumn(2).setPreferredWidth(150);
            adminPenalrListjTable.getColumnModel().getColumn(2).setMaxWidth(150);
            adminPenalrListjTable.getColumnModel().getColumn(3).setMinWidth(200);
            adminPenalrListjTable.getColumnModel().getColumn(3).setPreferredWidth(200);
            adminPenalrListjTable.getColumnModel().getColumn(3).setMaxWidth(200);
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 613, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 504, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 204), 2, true));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("User Name : ");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Type : ");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Password : ");

        userNamejTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userNamejTextFieldActionPerformed(evt);
            }
        });

        adminTypejComboBox.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        adminTypejComboBox.setMaximumRowCount(3);

        addjButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        addjButton.setForeground(new java.awt.Color(0, 153, 51));
        addjButton.setText("ADD");

        deletejButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        deletejButton.setForeground(new java.awt.Color(0, 153, 51));
        deletejButton.setText("Delete");

        upDatejButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        upDatejButton.setForeground(new java.awt.Color(0, 153, 51));
        upDatejButton.setText("UpDate");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(addjButton, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(upDatejButton, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deletejButton, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(userNamejTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(adminTypejComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(passwordjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(userNamejTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(adminTypejComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(passwordjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addjButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(upDatejButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deletejButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(36, Short.MAX_VALUE))
        );

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 0, 204));
        jLabel10.setText("Setting");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(202, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addGap(200, 200, 200))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        HomejMenu.setText("Home");
        HomejMenu.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N

        homejMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.CTRL_MASK));
        homejMenuItem.setText("Home");
        HomejMenu.add(homejMenuItem);

        SettingjMenuBar.add(HomejMenu);

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

        SettingjMenuBar.add(ExecutiveMemberjMenu);

        accountsInformationjMenu.setText("Accounts");
        accountsInformationjMenu.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N

        accountsInfojMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        accountsInfojMenuItem.setText("accounts info.");
        accountsInformationjMenu.add(accountsInfojMenuItem);

        memberfeejCheckBoxMenuItem.setText("member fee");
        accountsInformationjMenu.add(memberfeejCheckBoxMenuItem);

        SettingjMenuBar.add(accountsInformationjMenu);

        eventsjMenu.setText("Events");
        eventsjMenu.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N

        eventsjMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        eventsjMenuItem.setText("events");
        eventsjMenu.add(eventsjMenuItem);

        SettingjMenuBar.add(eventsjMenu);

        endMessagejMenu.setText("Message");
        endMessagejMenu.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N

        sendMessagejMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.CTRL_MASK));
        sendMessagejMenuItem.setText("send message");
        sendMessagejMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendMessagejMenuItemActionPerformed(evt);
            }
        });
        endMessagejMenu.add(sendMessagejMenuItem);

        previousMessagejMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        previousMessagejMenuItem.setText("previous message");
        previousMessagejMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                previousMessagejMenuItemActionPerformed(evt);
            }
        });
        endMessagejMenu.add(previousMessagejMenuItem);

        SettingjMenuBar.add(endMessagejMenu);

        jMenu1.setForeground(new java.awt.Color(0, 153, 51));
        jMenu1.setText("Setting");
        jMenu1.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N

        jMenuItem8.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem8.setForeground(new java.awt.Color(0, 153, 51));
        jMenuItem8.setText("admin setting");
        jMenu1.add(jMenuItem8);

        SettingjMenuBar.add(jMenu1);

        DeveloperjMenu.setText("Developer");
        DeveloperjMenu.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N

        DeveloperjMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_MASK));
        DeveloperjMenuItem.setText("information");
        DeveloperjMenu.add(DeveloperjMenuItem);

        SettingjMenuBar.add(DeveloperjMenu);

        exitsjMenu.setText("Exit");
        exitsjMenu.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N

        exitjMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        exitjMenuItem.setText("exit");
        exitsjMenu.add(exitjMenuItem);

        SettingjMenuBar.add(exitsjMenu);

        setJMenuBar(SettingjMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(91, 91, 91))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void executiveMemberjMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_executiveMemberjMenuItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_executiveMemberjMenuItemActionPerformed

    private void generalMemberjMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generalMemberjMenuItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_generalMemberjMenuItemActionPerformed

    private void sendMessagejMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendMessagejMenuItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sendMessagejMenuItemActionPerformed

    private void previousMessagejMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_previousMessagejMenuItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_previousMessagejMenuItemActionPerformed

    private void userNamejTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userNamejTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_userNamejTextFieldActionPerformed

    
    public void showComboBox(JComboBox object) throws SQLException {

        String tempAdmin;
        String[] typeList = new String[5];
        if(edit_item)
        {        
            if(current_item.equals("Main Admin")){
                typeList[0] = "Main Admin";
                typeList[1] = "Frontend Admin"; 
                tempAdmin = "main_admin";
            }
            else
            {
                typeList[0] = "Frontend Admin"; 
                typeList[1] = "Main Admin"; 
                tempAdmin = "front_admin"; 
            }
            object.setModel(new DefaultComboBoxModel(typeList));
           
            object.setSelectedIndex(0);
            object.toString();
        }
        
        else
        {
            typeList[0] = "Main Admin";
            typeList[1] = "Frontend Admin"; 

            object.setModel(new DefaultComboBoxModel(typeList)); 
            object.insertItemAt("--Slecet Member--", 0);
            object.setSelectedIndex(0);
            object.toString();
        }    
    }
    
    
    private void adminPenalrListjTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_adminPenalrListjTableMouseClicked
        // TODO add your handling code here:
        
        int rowNumber = adminPenalrListjTable.getSelectedRow();
        TableModel model = adminPenalrListjTable.getModel();
        
        current_id = Integer.parseInt(model.getValueAt(rowNumber, 0).toString());
        current_item = model.getValueAt(rowNumber, 2).toString();
        edit_item = true;
        try {
            this.showComboBox(adminTypejComboBox);
        } catch (SQLException ex) {
            Logger.getLogger(Setting.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        userNamejTextField.setText(model.getValueAt(rowNumber, 1).toString());
        passwordjTextField.setText(model.getValueAt(rowNumber, 3).toString());

    }//GEN-LAST:event_adminPenalrListjTableMouseClicked

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(Setting.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Setting.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Setting.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Setting.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Setting(1).setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(Setting.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu DeveloperjMenu;
    private javax.swing.JMenuItem DeveloperjMenuItem;
    private javax.swing.JMenu ExecutiveMemberjMenu;
    private javax.swing.JMenu HomejMenu;
    private javax.swing.JMenuBar SettingjMenuBar;
    private javax.swing.JMenuItem accountsInfojMenuItem;
    private javax.swing.JMenu accountsInformationjMenu;
    private javax.swing.JButton addjButton;
    private javax.swing.JTable adminPenalrListjTable;
    private javax.swing.JComboBox adminTypejComboBox;
    private javax.swing.JButton deletejButton;
    private javax.swing.JMenu endMessagejMenu;
    private javax.swing.JMenu eventsjMenu;
    private javax.swing.JMenuItem eventsjMenuItem;
    private javax.swing.JMenuItem executiveMemberjMenuItem;
    private javax.swing.JMenuItem exitjMenuItem;
    private javax.swing.JMenu exitsjMenu;
    private javax.swing.JMenuItem generalMemberjMenuItem;
    private javax.swing.JMenuItem homejMenuItem;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JMenuItem memberfeejCheckBoxMenuItem;
    private javax.swing.JTextField passwordjTextField;
    private javax.swing.JMenuItem previousMessagejMenuItem;
    private javax.swing.JMenuItem sendMessagejMenuItem;
    private javax.swing.JButton upDatejButton;
    private javax.swing.JTextField userNamejTextField;
    // End of variables declaration//GEN-END:variables
}
