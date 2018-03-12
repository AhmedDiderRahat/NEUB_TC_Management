package com.neub.touristclub.mainadmin.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import com.neub.touristclub.bean.SpotBean;
import com.neub.touristclub.databaseconnection.DbConnector;
import com.neub.touristclub.developer.Developer;
import com.neub.touristclub.mainadmin.HomePage;
import com.neub.touristclub.mainadmin.accounts.AccountsInformation;
import com.neub.touristclub.mainadmin.adminsetting.Setting;
import com.neub.touristclub.mainadmin.members.ExecutiveMember;
import com.neub.touristclub.mainadmin.members.GeneralMember;
import com.neub.touristclub.mainadmin.events.EventsInformation;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Team-R&D_v.3
 */
public final class SpotInformation extends javax.swing.JFrame implements ActionListener {
    public static int currentId;
    ArrayList<SpotBean> list;
    public static  int adminIdKey;
    
    public SpotInformation(int key) throws SQLException {
        initComponents();
        adminIdKey = key;
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        AddjButton.addActionListener(this);
        UpDatejButton.addActionListener(this);
        DeletejButton.addActionListener(this);
        PrintToTable();
        //menu handeling
        homejMenuItem.addActionListener(this);
        generalMemberjMenuItem.addActionListener(this);
        executiveMemberjMenuItem.addActionListener(this);
        accountsInfojMenuItem.addActionListener(this);
        SettingjMenuItem.addActionListener(this);
        DeveloperjMenuItem.addActionListener(this);
        events_jMenuItem.addActionListener(this);
    }

    public String dateCalculation(String type) {

        java.util.Date indate;

        if (type.equals("start")) {
            indate = StartDatejDateChooser.getDate();
        } else {
            indate = EndDatejDateChooser.getDate();
        }

        if (indate == null) {
            return "";
        }

        String dateString = null;
        SimpleDateFormat sdfr = new SimpleDateFormat("dd/MMM/yyyy");

        dateString = sdfr.format(indate);

        return dateString;
    }

    @Override
    public void actionPerformed(ActionEvent button) {

        if (button.getSource() == homejMenuItem) {
            try {
                HomePage homePage = new HomePage(adminIdKey);
                this.dispose();
            } catch (Exception e) {
            }
        }
        
        if(button.getSource() == events_jMenuItem){
            try {
                EventsInformation ei = new EventsInformation(adminIdKey);
                this.dispose();
            } catch (Exception e) {
            }
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
       
        if( button.getSource() == SettingjMenuItem )
        {
            try {
                Setting s = new Setting(adminIdKey);
                this.dispose();
            } catch (Exception e) {
            }
        }
        
        if( (button.getSource() == DeveloperjMenuItem) )
        {
            try {
                Developer d = new Developer(adminIdKey);
                this.dispose();
            } catch (Exception e) {
            }
        }
                
        if (button.getSource() == AddjButton) {
            //System.out.println("add");
            SpotBean bean = new SpotBean();

            bean.setSpot_name(SpotNamejTextField.getText().trim());
            bean.setStart_date(dateCalculation("start"));
            bean.setEnd_date(dateCalculation("end_date"));
            
            boolean tempStatus = false;
            bean.setTransport(TransportjComboBox.getSelectedItem().toString());
            if( (statusjComboBox.getSelectedItem().toString()).equals("new") )
                bean.setStatus(1);
            else if( (statusjComboBox.getSelectedItem().toString()).equals("old") )
                bean.setStatus(0);
            else
                tempStatus = true;

            if ((bean.getSpot_name().equals("")) || (bean.getStart_date().equals(""))
                    || (bean.getEnd_date().equals("")) || (bean.getTransport().equals("")) 
                    || (tempStatus)) {
                JOptionPane.showMessageDialog(null, "Fill up data properly...!!!");
            } else {
                try {
                    try {
                        DbConnector connector = new DbConnector();
                        String insertQuery = "INSERT INTO spot ( spot_name, start_date,"
                                + " end_date, transport, status) VALUES ('" + bean.getSpot_name()
                                + "', '" + bean.getStart_date() + "', '" + bean.getEnd_date()
                                + "', '" + bean.getTransport() + "' , '" + bean.getStatus() + " ' )";
                        
                        connector.statement.executeUpdate(insertQuery);
                        connector.connection.close();
                        JOptionPane.showMessageDialog(null, "Successfully Added...!!!");
                    } catch (SQLException ex) {
                        Logger.getLogger(SpotInformation.class.getName()).log(Level.SEVERE, null, ex);
                    }                    
                    DefaultTableModel model = (DefaultTableModel) SpotInformationjTable.getModel();
                    model.setRowCount(0);
                    PrintToTable();
                } catch (SQLException ex) {
                    Logger.getLogger(SpotInformation.class.getName()).log(Level.SEVERE, null, ex);
                }                
            }            
        }
        
        if (button.getSource() == UpDatejButton) {
            System.out.println("upadte  :- " + currentId);
            
            SpotBean bean = new SpotBean();
            bean.setSpot_id(currentId);
            bean.setSpot_name(SpotNamejTextField.getText().trim());
            bean.setStart_date(dateCalculation("start"));
            bean.setEnd_date(dateCalculation("end_date"));
            
            boolean tempStatus = false;
            bean.setTransport(TransportjComboBox.getSelectedItem().toString());
            if( (statusjComboBox.getSelectedItem().toString()).equals("new") )
                bean.setStatus(1);
            else if( (statusjComboBox.getSelectedItem().toString()).equals("old") )
                bean.setStatus(0);
            else
                tempStatus = true;

            
            if ((bean.getSpot_name().equals("")) || (bean.getStart_date().equals(""))
                    || (bean.getEnd_date().equals("")) || (bean.getTransport().equals("")) 
                    || (tempStatus)) {
                JOptionPane.showMessageDialog(null, "Fill up data properly...!!!");
            }
            else{   
                    DbConnector dbConnector = new DbConnector();
                    //generalMemberBean.setMobile(temporaryNumber);
                    String UpdateQuery = "UPDATE spot " + " SET "  + "spot_name = '" + 
                            bean.getSpot_name() + "', " + "start_date = '" + 
                            bean.getStart_date() + "', " + "end_date = '" +
                            bean.getEnd_date() + "' , " + "transport = '" +
                            bean.getTransport() + "', " + "status = '" +
                            bean.getStatus() + "' WHERE spot_id = " + 
                            "'" + bean.getSpot_id() + "'";
                    try 
                    {
                        dbConnector.statement.executeUpdate(UpdateQuery);
                        dbConnector.connection.close();
                        JOptionPane.showMessageDialog(null, "Successfullt Updated...!!!");
                        SpotNamejTextField.setText("");
                        currentId = 0;
                        DefaultTableModel model = (DefaultTableModel) SpotInformationjTable.getModel();
                        model.setRowCount(0);
                        PrintToTable();
                    } catch (SQLException ex) 
                    {
                        System.out.println(ex);
                        JOptionPane.showMessageDialog(null, "Data Doesn't Update...!!!");
                    }
                }
        }
        
        if (button.getSource() == DeletejButton) {
            try {
                System.out.println("delet");
                SpotBean bean =  new SpotBean();
                bean.setSpot_id(currentId);
                
                DbConnector dbConnector = new DbConnector();
                String deleteQuery = "DELETE FROM spot "+ " WHERE spot_id = " + "'" + bean.getSpot_id() + "'";
                
                dbConnector.statement.executeUpdate(deleteQuery);
                dbConnector.connection.close();
                JOptionPane.showMessageDialog(null, "Data Successfully Deleted...!!!");
                currentId = 0;
                SpotNamejTextField.setText("");
                
                DefaultTableModel model = (DefaultTableModel) SpotInformationjTable.getModel();
                model.setRowCount(0);
                PrintToTable();
            }
            catch (SQLException ex) {
                Logger.getLogger(SpotInformation.class.getName()).log(Level.SEVERE, null, ex);
            }
                           
            
        }
    }
    
    public ArrayList dataRetriveFromDatabase() throws SQLException
    {
        ArrayList<SpotBean> listData;  
        DbConnector connector = new DbConnector();
        String selectQuery = "SELECT * FROM spot "; // WHERE " + "TYPE" + " =  'Income'";
        connector.resultset = connector.statement.executeQuery(selectQuery);

        listData = new ArrayList<SpotBean>();
        
        while (connector.resultset.next()) {
            int spot_id = connector.resultset.getInt("spot_id");
            String spot_name  = connector.resultset.getString("spot_name");
            String start_date  = connector.resultset.getString("start_date");
            String end_date  = connector.resultset.getString("end_date");
            String transport  = connector.resultset.getString("transport");
            int status = connector.resultset.getInt("status");
            SpotBean sb = new SpotBean(spot_id, spot_name, start_date, end_date, transport, status);

            listData.add(sb);
        }
        connector.connection.close();
        return listData;
    }
    
    public void PrintToTable() throws SQLException {

        DefaultTableModel model = (DefaultTableModel) SpotInformationjTable.getModel();
        ArrayList<SpotBean> ReciveList = dataRetriveFromDatabase();
        Object rawData[] = new Object[6];

        for (int i = 0; i < ReciveList.size(); i++) 
        {
            rawData[0] = ReciveList.get(i).getSpot_id();
            rawData[1] = ReciveList.get(i).getSpot_name();
            rawData[2] = ReciveList.get(i).getStart_date();
            rawData[3] = ReciveList.get(i).getEnd_date();
            rawData[4] = ReciveList.get(i).getTransport();
            
            if(ReciveList.get(i).getStatus() == 1)
                rawData[5] = "new";
            else
                rawData[5] = "old";
            
            model.addRow(rawData);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        StartDatejDateChooser = new com.toedter.calendar.JDateChooser();
        TransportjComboBox = new javax.swing.JComboBox();
        AddjButton = new javax.swing.JButton();
        UpDatejButton = new javax.swing.JButton();
        DeletejButton = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        SpotNamejTextField = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        EndDatejDateChooser = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        statusjComboBox = new javax.swing.JComboBox();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        SpotInformationjTable = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        EventsjMenuBar = new javax.swing.JMenuBar();
        HomejMenu = new javax.swing.JMenu();
        homejMenuItem = new javax.swing.JMenuItem();
        ExecutiveMemberjMenu = new javax.swing.JMenu();
        executiveMemberjMenuItem = new javax.swing.JMenuItem();
        generalMemberjMenuItem = new javax.swing.JMenuItem();
        accountsInformationjMenu = new javax.swing.JMenu();
        accountsInfojMenuItem = new javax.swing.JMenuItem();
        eventsjMenu = new javax.swing.JMenu();
        events_jMenuItem = new javax.swing.JMenuItem();
        endMessagejMenu = new javax.swing.JMenu();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        SettingjMenu = new javax.swing.JMenu();
        SettingjMenuItem = new javax.swing.JMenuItem();
        DeveloperjMenu = new javax.swing.JMenu();
        DeveloperjMenuItem = new javax.swing.JMenuItem();
        exitsjMenu = new javax.swing.JMenu();
        jMenuItem7 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel5.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 0, 204), 2, true));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Date (start) : ");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Transport : ");

        StartDatejDateChooser.setDateFormatString("d MMM, yyyy");
        StartDatejDateChooser.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        StartDatejDateChooser.setMinimumSize(new java.awt.Dimension(60, 40));
        StartDatejDateChooser.setPreferredSize(new java.awt.Dimension(40, 30));

        TransportjComboBox.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        TransportjComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "--- Select ---", "Bus", "Boat", "Air", "Other" }));

        AddjButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AddjButton.setForeground(new java.awt.Color(0, 153, 31));
        AddjButton.setText("ADD");

        UpDatejButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        UpDatejButton.setForeground(new java.awt.Color(0, 153, 31));
        UpDatejButton.setText("UpDate");
        UpDatejButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpDatejButtonActionPerformed(evt);
            }
        });

        DeletejButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        DeletejButton.setForeground(new java.awt.Color(0, 153, 31));
        DeletejButton.setText("Delete");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Spot Name : ");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("Date (end) : ");

        EndDatejDateChooser.setDateFormatString("d MMM, yyyy");
        EndDatejDateChooser.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        EndDatejDateChooser.setMinimumSize(new java.awt.Dimension(60, 40));
        EndDatejDateChooser.setPreferredSize(new java.awt.Dimension(40, 30));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Status : ");

        statusjComboBox.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        statusjComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "--- Select ---", "new", "old" }));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                                .addComponent(SpotNamejTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(StartDatejDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(TransportjComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(EndDatejDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(AddjButton, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(UpDatejButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(DeletejButton, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(statusjComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SpotNamejTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(StartDatejDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(EndDatejDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TransportjComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(statusjComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AddjButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(UpDatejButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DeletejButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 0, 204), 3));

        SpotInformationjTable.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        SpotInformationjTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "spot Id", "Name", "Start Date", "End Date", "Transport", "status"
            }
        ));
        SpotInformationjTable.setRowHeight(30);
        SpotInformationjTable.getTableHeader().setReorderingAllowed(false);
        SpotInformationjTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SpotInformationjTableMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(SpotInformationjTable);
        if (SpotInformationjTable.getColumnModel().getColumnCount() > 0) {
            SpotInformationjTable.getColumnModel().getColumn(0).setMinWidth(70);
            SpotInformationjTable.getColumnModel().getColumn(0).setPreferredWidth(70);
            SpotInformationjTable.getColumnModel().getColumn(0).setMaxWidth(70);
            SpotInformationjTable.getColumnModel().getColumn(2).setMinWidth(100);
            SpotInformationjTable.getColumnModel().getColumn(2).setPreferredWidth(100);
            SpotInformationjTable.getColumnModel().getColumn(2).setMaxWidth(100);
            SpotInformationjTable.getColumnModel().getColumn(3).setMinWidth(100);
            SpotInformationjTable.getColumnModel().getColumn(3).setPreferredWidth(100);
            SpotInformationjTable.getColumnModel().getColumn(3).setMaxWidth(100);
            SpotInformationjTable.getColumnModel().getColumn(4).setMinWidth(90);
            SpotInformationjTable.getColumnModel().getColumn(4).setPreferredWidth(90);
            SpotInformationjTable.getColumnModel().getColumn(4).setMaxWidth(90);
            SpotInformationjTable.getColumnModel().getColumn(5).setMinWidth(40);
            SpotInformationjTable.getColumnModel().getColumn(5).setPreferredWidth(40);
            SpotInformationjTable.getColumnModel().getColumn(5).setMaxWidth(40);
        }

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 153, 51));
        jLabel8.setText("Spots History");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addGap(249, 249, 249))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 632, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE)
                .addContainerGap())
        );

        HomejMenu.setText("Home");
        HomejMenu.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N

        homejMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.CTRL_MASK));
        homejMenuItem.setText("Home");
        HomejMenu.add(homejMenuItem);

        EventsjMenuBar.add(HomejMenu);

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

        EventsjMenuBar.add(ExecutiveMemberjMenu);

        accountsInformationjMenu.setText("Accounts");
        accountsInformationjMenu.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N

        accountsInfojMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        accountsInfojMenuItem.setForeground(new java.awt.Color(0, 153, 51));
        accountsInfojMenuItem.setText("accounts info.");
        accountsInformationjMenu.add(accountsInfojMenuItem);

        EventsjMenuBar.add(accountsInformationjMenu);

        eventsjMenu.setForeground(new java.awt.Color(0, 153, 51));
        eventsjMenu.setText("Events");
        eventsjMenu.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N

        events_jMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        events_jMenuItem.setForeground(new java.awt.Color(0, 153, 51));
        events_jMenuItem.setText("events");
        eventsjMenu.add(events_jMenuItem);

        EventsjMenuBar.add(eventsjMenu);

        endMessagejMenu.setText("Message");
        endMessagejMenu.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N

        jMenuItem6.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem6.setText("send message");
        endMessagejMenu.add(jMenuItem6);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setText("previous message");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        endMessagejMenu.add(jMenuItem2);

        EventsjMenuBar.add(endMessagejMenu);

        SettingjMenu.setText("Setting");
        SettingjMenu.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N

        SettingjMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        SettingjMenuItem.setText("admin setting");
        SettingjMenu.add(SettingjMenuItem);

        EventsjMenuBar.add(SettingjMenu);

        DeveloperjMenu.setText("Developer");
        DeveloperjMenu.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N

        DeveloperjMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_MASK));
        DeveloperjMenuItem.setText("information");
        DeveloperjMenu.add(DeveloperjMenuItem);

        EventsjMenuBar.add(DeveloperjMenu);

        exitsjMenu.setText("Exit");
        exitsjMenu.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N

        jMenuItem7.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem7.setText("exit");
        exitsjMenu.add(jMenuItem7);

        EventsjMenuBar.add(exitsjMenu);

        setJMenuBar(EventsjMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(36, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void executiveMemberjMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_executiveMemberjMenuItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_executiveMemberjMenuItemActionPerformed

    private void generalMemberjMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generalMemberjMenuItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_generalMemberjMenuItemActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void UpDatejButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpDatejButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_UpDatejButtonActionPerformed

    private void SpotInformationjTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SpotInformationjTableMouseClicked
        // TODO add your handling code here:
        
        //mouse operation
        int rowNumber = SpotInformationjTable.getSelectedRow();
        
        TableModel model = SpotInformationjTable.getModel();
        currentId = Integer.parseInt(model.getValueAt(rowNumber, 0).toString());
        SpotNamejTextField.setText(model.getValueAt(rowNumber, 1).toString());    
    }//GEN-LAST:event_SpotInformationjTableMouseClicked

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new SpotInformation(adminIdKey).setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(SpotInformation.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddjButton;
    private javax.swing.JButton DeletejButton;
    private javax.swing.JMenu DeveloperjMenu;
    private javax.swing.JMenuItem DeveloperjMenuItem;
    private com.toedter.calendar.JDateChooser EndDatejDateChooser;
    private javax.swing.JMenuBar EventsjMenuBar;
    private javax.swing.JMenu ExecutiveMemberjMenu;
    private javax.swing.JMenu HomejMenu;
    private javax.swing.JMenu SettingjMenu;
    private javax.swing.JMenuItem SettingjMenuItem;
    private javax.swing.JTable SpotInformationjTable;
    private javax.swing.JTextField SpotNamejTextField;
    private com.toedter.calendar.JDateChooser StartDatejDateChooser;
    private javax.swing.JComboBox TransportjComboBox;
    private javax.swing.JButton UpDatejButton;
    private javax.swing.JMenuItem accountsInfojMenuItem;
    private javax.swing.JMenu accountsInformationjMenu;
    private javax.swing.JMenu endMessagejMenu;
    private javax.swing.JMenuItem events_jMenuItem;
    private javax.swing.JMenu eventsjMenu;
    private javax.swing.JMenuItem executiveMemberjMenuItem;
    private javax.swing.JMenu exitsjMenu;
    private javax.swing.JMenuItem generalMemberjMenuItem;
    private javax.swing.JMenuItem homejMenuItem;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JComboBox statusjComboBox;
    // End of variables declaration//GEN-END:variables
}
