package com.neub.touristclub.mainadmin.events;

import com.neub.touristclub.bean.EventsBean;
import com.neub.touristclub.databaseconnection.DbConnector;
import com.neub.touristclub.developer.Developer;
import com.neub.touristclub.mainadmin.HomePage;
import com.neub.touristclub.mainadmin.accounts.AccountsInformation;
import com.neub.touristclub.mainadmin.accounts.MembersFee;
import com.neub.touristclub.mainadmin.members.ExecutiveMember;
import com.neub.touristclub.mainadmin.members.GeneralMember;
import com.neub.touristclub.message.PreviousMessage;
import com.neub.touristclub.message.SendMessage;
import com.neub.touristclub.mainadmin.adminsetting.Setting;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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

public class EventsInformation extends javax.swing.JFrame implements ActionListener{
    public ArrayList<String>selectedList = new ArrayList<String>();
    public static  int adminIdKey;
    public int[] eventIdList;
    public static int current_event_id;
    public ArrayList<SpotInfo>listOfSpot; 

    public EventsInformation(int key) {
        adminIdKey = key;
        initComponents();
        eventIdList = new int[500];
        
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        
        addjButton.addActionListener(this);
        DeletejButton.addActionListener(this);
        UpDatejButton.addActionListener(this);

        //menu handeling
        homejMenuItem.addActionListener(this);
        generalMemberjMenuItem.addActionListener(this);
        executiveMemberjMenuItem.addActionListener(this);
        memberfeejCheckBoxMenuItem.addActionListener(this);
        accountsInfojMenuItem.addActionListener(this);
        SettingjMenuItem.addActionListener(this);
        sendMessagejMenuItem.addActionListener(this);
        previousMessagejMenuItem.addActionListener(this);
        DeveloperjMenuItem.addActionListener(this);
        exitjMenuItem.addActionListener(this);
        reg_jMenuItem.addActionListener(this);
        SpotjCheckBoxMenuItem.addActionListener(this);
        spotAddjButton.addActionListener(this);
        
        try {
            this.showComboBox(spotTypejComboBox);
        } catch (SQLException ex) {
            Logger.getLogger(EventsInformation.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            PrintToTableEvents();
        } catch (SQLException ex) {
            Logger.getLogger(EventsInformation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String findSpotNameById(String spotIdLists)
    {
        int arr[] = new int[30];
        int indOfArr = 0;
        String tempName="";
        for(int i=0; i<spotIdLists.length(); i++)
        {
            if(spotIdLists.charAt(i) != '+')
            {
                tempName += spotIdLists.charAt(i);
            }
            else
            {
                arr[indOfArr] = checkAmount(tempName.trim());
                indOfArr++;
                //System.out.println(tempName);
                tempName = "";
            }
        }
        arr[indOfArr] = checkAmount(tempName.trim());
        indOfArr++;
        
        String returnAnwer = "";
        
        for(int i=0; i<indOfArr; i++)
        {
            if(arr[i] != -10)
            {
                try {
                    DbConnector connector = new DbConnector();
                    String selectQuery = "SELECT * FROM spot WHERE spot_id = '" + arr[i] +"'";
                    
                    connector.resultset = connector.statement.executeQuery(selectQuery);
                    
                    while (connector.resultset.next()) {
                        String name = connector.resultset.getString("spot_name");
                        //System.out.println(" " + name + " ");
                        returnAnwer += ", " + name;
                    }
                    connector.connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(EventsInformation.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        if(returnAnwer.length()>2)
            returnAnwer = returnAnwer.substring(2, (returnAnwer.length()) );

        return returnAnwer;
    }
    
    public class SpotInfo
    {
        public int spot_id;
        public String spot_name;

        public SpotInfo(int spot_id, String spot_name) {
            this.spot_id = spot_id;
            this.spot_name = spot_name;
        }

        public int getSpot_id() {
            return spot_id;
        }

        public void setSpot_id(int spot_id) {
            this.spot_id = spot_id;
        }

        public String getSpot_name() {
            return spot_name;
        }

        public void setSpot_name(String spot_name) {
            this.spot_name = spot_name;
        }
        
    }
   
    public void showComboBox(JComboBox object) throws SQLException {

        String[] temp = new String[500];
        
        String selectStudentQuery = "SELECT * FROM spot where status = '" + 1 + "'" ; 
        DbConnector connector = new DbConnector();
        connector.resultset = connector.statement.executeQuery(selectStudentQuery);
        listOfSpot = new ArrayList<>();
        int idx = 0;
        while (connector.resultset.next()) {
            String spot = connector.resultset.getString("spot_name").toString();
            temp[idx] = spot;
            int id =  connector.resultset.getInt("spot_id");
            SpotInfo info = new SpotInfo(id, spot);
            listOfSpot.add(info);
            idx++;
        }
        connector.connection.close();
        object.setModel(new DefaultComboBoxModel(temp));
        object.insertItemAt("--Slecet Member--", 0);
        object.setSelectedIndex(0);
        object.toString();
    }
    
    public String dateCalculation(String temp) {

        java.util.Date indate;
        if (temp.equals("start")) {
            indate = startDatejDateChooser.getDate();
        } else {
            indate = endDatejDateChooser.getDate();
        }

        if (indate == null) {
            return "";
        }

        String dateString = null;
        SimpleDateFormat sdfr = new SimpleDateFormat("dd/MMM/yyyy");

        dateString = sdfr.format(indate);

        return dateString;
    }
    
    public String calculateSpotId()
    {
        String spotName = spotListTitlejTextField.getText().trim();
        
        int uniqueSpotId[] = new int[500];
        int idx=0;
        String temp="";
        for(int i=0; i<spotName.length(); i++)
        {
            if((spotName.charAt(i) == ',') ){
                for(int j=0; j<listOfSpot.size(); j++)
                {
                    if(temp.trim().equals(listOfSpot.get(j).spot_name)){                
                        uniqueSpotId[idx] = listOfSpot.get(j).spot_id;
                        idx++;
                    }
                }
                temp="";
            }
            else
                temp += spotName.charAt(i);
        }
        for(int j=0; j<listOfSpot.size(); j++)
        {
            if(temp.trim().equals(listOfSpot.get(j).spot_name)){
                uniqueSpotId[idx] = listOfSpot.get(j).spot_id;
                idx++;
            }
        }
        
        for(int i=0; i<idx; i++)
        {
            if(uniqueSpotId[i] == 0)
                continue;
            for(int j=i+1; j<idx; j++)
            {
                if( uniqueSpotId[i] == uniqueSpotId[j] )
                    uniqueSpotId[j] = 0;
            }
        }
        
        String finalSoptId = "";
        for(int i=0; i<idx; i++)
            if(uniqueSpotId[i] != 0)
                finalSoptId += "+"+uniqueSpotId[i];        
        return finalSoptId;      
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
            
        // Menu item implement
        if (button.getSource() == homejMenuItem) {
            try {
                HomePage homePage = new HomePage(adminIdKey);
                this.dispose();
            } catch (Exception e) {
            }
        }

        if (button.getSource() == SpotjCheckBoxMenuItem) {
            try {
                SpotInformation si = new SpotInformation(adminIdKey);
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

        if (button.getSource() == SettingjMenuItem) {
            try {
                Setting s = new Setting(adminIdKey);
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

        if ((button.getSource() == DeveloperjMenuItem)) {
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

        if (button.getSource() == reg_jMenuItem) {
            try {

                boolean isTour = isTourExist();

                if (isTour) {
                    RegistrationInTour inTour = new RegistrationInTour(adminIdKey);
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Pleasure Ensure one Event \nCan Open at a time!!!");
                }
            } catch (Exception e) {
            }
        }

        if(button.getSource() == addjButton)
        {
            EventsBean bean = new EventsBean();
            bean.setTitle(tourTitlejTextField1.getText());
            bean.setStart_date(dateCalculation("start"));
            bean.setEnd_date(dateCalculation("end"));
            bean.setType(tourTypejComboBox.getSelectedItem().toString());
            bean.setTotal_capacity( checkAmount(membertjTextField.getText().trim()) );
            bean.setFee( checkAmount(feejTextField.getText().trim()) );
            //bean.setFee(Integer.parseInt(feejTextField.getText().trim()));
            bean.setSpot_id(calculateSpotId());
            
            switch (statusjComboBox.getSelectedItem().toString()) {
                case "new":
                    bean.setStatus(1);
                    break;
                case "old":
                    bean.setStatus(0);
                    break;
                default:
                    bean.setStatus(-10);
                    break;
            }
            
            if( (bean.getTitle() == null) || (bean.getStart_date()==null) || 
                    (bean.getEnd_date() == null) || (bean.getType() == null) ||
                    (bean.getTotal_capacity()==-10) || (bean.getFee() == -10) ||
                    (bean.getSpot_id()==null) || (bean.getStatus() == -10) ){
                
                JOptionPane.showMessageDialog(null, "Fill Information Properly...!!!");
            }
            else
            {
                try {
                    DbConnector connector = new DbConnector();
                    
                    String insertQuery = "INSERT INTO events_information ( title, "
                            + "start_date, end_date, type, total_capacity, fee, "
                            + "spot_id, status) VALUES ('" +bean.getTitle() + "', '"
                            + bean.getStart_date() + "', '" + bean.getEnd_date() + "', '"
                            + bean.getType() + "', '" + bean.getTotal_capacity() + "', '"
                            + bean.getFee() + "', '" + bean.getSpot_id() + "', '" +
                            bean.getStatus() + "')";
                    
                    connector.statement.executeUpdate(insertQuery);
                    connector.connection.close();
                    JOptionPane.showMessageDialog(null, "Successfully Added...!!!");
                    EventsInformation ei = new EventsInformation(adminIdKey);
                    this.dispose();
                } catch (SQLException ex) {
                    Logger.getLogger(EventsInformation.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        if(button.getSource() == DeletejButton)
        {
            try {
                    DbConnector dbConnectorDelete = new DbConnector();
                    String deleteQuery = "DELETE FROM events_information " + "WHERE ev_id = " + "'" + current_event_id + "'";
                    dbConnectorDelete.statement.executeUpdate(deleteQuery);
                    dbConnectorDelete.connection.close();
                    JOptionPane.showMessageDialog(null, "Data Successfully Deleted...!!!");
                    
                    DefaultTableModel model = (DefaultTableModel) eventsHistoryjTable.getModel();
                    model.setRowCount(0);
                    PrintToTableEvents();
                    
                } catch (SQLException ex) {
                    Logger.getLogger(MembersFee.class.getName()).log(Level.SEVERE, null, ex);
                }        
        }
        
        if(button.getSource() == UpDatejButton)
        {
            EventsBean bean = new EventsBean();
            bean.setEv_id(current_event_id);
            bean.setTitle(tourTitlejTextField1.getText());
            bean.setStart_date(dateCalculation("start"));
            bean.setEnd_date(dateCalculation("end"));
            bean.setType(tourTypejComboBox.getSelectedItem().toString());
            bean.setTotal_capacity( checkAmount(membertjTextField.getText().trim()) );
            bean.setFee( checkAmount(feejTextField.getText().trim()) );
            bean.setSpot_id(calculateSpotId());
            
            switch (statusjComboBox.getSelectedItem().toString()) {
                case "new":
                    bean.setStatus(1);
                    break;
                case "old":
                    bean.setStatus(0);
                    break;
                default:
                    bean.setStatus(-10);
                    break;
            }
            
            if( (bean.getTitle() == null) || (bean.getStart_date()==null) || 
                    (bean.getEnd_date() == null) || (bean.getType() == null) ||
                    (bean.getTotal_capacity()==-10) || (bean.getFee() == -10) ||
                    (bean.getSpot_id()==null) || (bean.getStatus() == -10) ){
                
                JOptionPane.showMessageDialog(null, "Fill Information Properly...!!!");
            }
            else {
                DbConnector dbConnector = new DbConnector();

                String UpdateQuery = "UPDATE events_information SET title = '"
                        + bean.getTitle() + "', start_date = '"
                        + bean.getStart_date() + "', end_date = '"
                        + bean.getEnd_date() + "' , type = '"
                        + bean.getType() + "', " + "total_capacity = '"
                        + bean.getTotal_capacity() + "', " + "fee = '"
                        + bean.getFee() + "', " + "spot_id = '"
                        + bean.getSpot_id() + "', " + "status = '"
                        + bean.getStatus() + "' WHERE ev_id = " + "'"
                        + bean.getEv_id() + "'";
                try {
                    dbConnector.statement.executeUpdate(UpdateQuery);
                    dbConnector.connection.close();
                                        
                } catch (SQLException ex) {
                    System.out.println(ex);
                    JOptionPane.showMessageDialog(null, "Data Doesn't Update...!!!");
                }

                JOptionPane.showMessageDialog(null, "Successfully Updated...!!!");
                EventsInformation ei = new EventsInformation(adminIdKey);
                this.dispose();
            }            
        }
                
        if(button.getSource() == spotAddjButton)
        {
            if( !(spotTypejComboBox.getSelectedItem()).equals("--Slecet Member--") )
            {
                if( !(spotListTitlejTextField.getText().trim()).equals("") )
                    spotListTitlejTextField.setText(spotListTitlejTextField.getText().trim() 
                            + ", " + spotTypejComboBox.getSelectedItem().toString());
                 else
                    spotListTitlejTextField.setText(spotTypejComboBox.getSelectedItem().toString());
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Fill Information Properly...!!!");
            }
        }     
     }    
    
    public boolean isTourExist() {
        
        int counter = 0;
        String query = "SELECT * FROM events_information WHERE status = '" + 1 + "'";
        DbConnector connector = new DbConnector();
        try {
            connector.resultset = connector.statement.executeQuery(query);
            while (connector.resultset.next()) {
                counter++;
            }
            connector.connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(RegistrationInTour.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return counter == 1;
    }

    public ArrayList dataRetriveFromDatabaseForEvents() throws SQLException {

        DbConnector connectorShow = new DbConnector();
        ArrayList<EventsBean> listEvets;
        String selectFeeQuery = "SELECT * FROM events_information";
        
        connectorShow.resultset = connectorShow.statement.executeQuery(selectFeeQuery);
        
        listEvets = new ArrayList<EventsBean>();
        while (connectorShow.resultset.next()) {
            int ev_id = connectorShow.resultset.getInt("ev_id");
            String title = connectorShow.resultset.getString("title");
            String start_date = connectorShow.resultset.getString("start_date");
            String end_date = connectorShow.resultset.getString("end_date");
            String type = connectorShow.resultset.getString("type");
            int memberCapacity = connectorShow.resultset.getInt("total_capacity");
            int fee = connectorShow.resultset.getInt("fee");
            String spotId = connectorShow.resultset.getString("spot_id");
            int status = connectorShow.resultset.getInt("status");

            EventsBean eventsBean = new EventsBean(ev_id, title, start_date, 
                    end_date, type, memberCapacity, fee, spotId, status);
            
            listEvets.add(eventsBean);
        }
        connectorShow.connection.close();
        return listEvets;
    }

    public void PrintToTableEvents() throws SQLException {

        DefaultTableModel model = (DefaultTableModel) eventsHistoryjTable.getModel();
        ArrayList<EventsBean> listEventReceive = dataRetriveFromDatabaseForEvents();
        Object rawData[] = new Object[9];

        for (int i = 0; i < listEventReceive.size(); i++) {
            rawData[0] = listEventReceive.get(i).getEv_id();
            rawData[1] = listEventReceive.get(i).getTitle();
            rawData[2] = listEventReceive.get(i).getType();
            //useing query
            String finalSpot, spotIdLists = listEventReceive.get(i).getSpot_id();
            
            finalSpot = findSpotNameById(spotIdLists);
            
            rawData[3] = finalSpot;
            
            rawData[4] = listEventReceive.get(i).getStart_date();
            rawData[5] = listEventReceive.get(i).getEnd_date();
            rawData[6] = listEventReceive.get(i).getTotal_capacity();
            rawData[7] = listEventReceive.get(i).getFee();
            //if-else condition
            if(listEventReceive.get(i).getStatus() == 0)
                rawData[8] = "old";
            else
                rawData[8] = "new";
            
            model.addRow(rawData);
        }
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        eventsHistoryjTable = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        tourTypejComboBox = new javax.swing.JComboBox();
        feejTextField = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        tourTitlejTextField1 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        endDatejDateChooser = new com.toedter.calendar.JDateChooser();
        startDatejDateChooser = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        membertjTextField = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        addjButton = new javax.swing.JButton();
        DeletejButton = new javax.swing.JButton();
        UpDatejButton = new javax.swing.JButton();
        statusjComboBox = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        spotTypejComboBox = new javax.swing.JComboBox();
        spotListTitlejTextField = new javax.swing.JTextField();
        spotAddjButton = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        EventsjMenuBar = new javax.swing.JMenuBar();
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
        reg_jMenuItem = new javax.swing.JMenuItem();
        SpotjCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
        endMessagejMenu = new javax.swing.JMenu();
        sendMessagejMenuItem = new javax.swing.JMenuItem();
        previousMessagejMenuItem = new javax.swing.JMenuItem();
        SettingjMenu = new javax.swing.JMenu();
        SettingjMenuItem = new javax.swing.JMenuItem();
        DeveloperjMenu = new javax.swing.JMenu();
        DeveloperjMenuItem = new javax.swing.JMenuItem();
        exitsjMenu = new javax.swing.JMenu();
        exitjMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 0, 204), 3));

        eventsHistoryjTable.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        eventsHistoryjTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "sl", "Tour Title", "Type", "spots ", "Start", "End", "Tourist", "fee", "status"
            }
        ));
        eventsHistoryjTable.setRowHeight(30);
        eventsHistoryjTable.getTableHeader().setReorderingAllowed(false);
        eventsHistoryjTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eventsHistoryjTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(eventsHistoryjTable);
        if (eventsHistoryjTable.getColumnModel().getColumnCount() > 0) {
            eventsHistoryjTable.getColumnModel().getColumn(0).setMinWidth(30);
            eventsHistoryjTable.getColumnModel().getColumn(0).setPreferredWidth(30);
            eventsHistoryjTable.getColumnModel().getColumn(0).setMaxWidth(30);
            eventsHistoryjTable.getColumnModel().getColumn(2).setMinWidth(90);
            eventsHistoryjTable.getColumnModel().getColumn(2).setPreferredWidth(90);
            eventsHistoryjTable.getColumnModel().getColumn(2).setMaxWidth(90);
            eventsHistoryjTable.getColumnModel().getColumn(3).setMinWidth(250);
            eventsHistoryjTable.getColumnModel().getColumn(3).setPreferredWidth(250);
            eventsHistoryjTable.getColumnModel().getColumn(3).setMaxWidth(250);
            eventsHistoryjTable.getColumnModel().getColumn(4).setMinWidth(100);
            eventsHistoryjTable.getColumnModel().getColumn(4).setPreferredWidth(100);
            eventsHistoryjTable.getColumnModel().getColumn(4).setMaxWidth(100);
            eventsHistoryjTable.getColumnModel().getColumn(5).setMinWidth(100);
            eventsHistoryjTable.getColumnModel().getColumn(5).setPreferredWidth(100);
            eventsHistoryjTable.getColumnModel().getColumn(5).setMaxWidth(100);
            eventsHistoryjTable.getColumnModel().getColumn(6).setMinWidth(50);
            eventsHistoryjTable.getColumnModel().getColumn(6).setPreferredWidth(50);
            eventsHistoryjTable.getColumnModel().getColumn(6).setMaxWidth(50);
            eventsHistoryjTable.getColumnModel().getColumn(7).setMinWidth(50);
            eventsHistoryjTable.getColumnModel().getColumn(7).setPreferredWidth(50);
            eventsHistoryjTable.getColumnModel().getColumn(7).setMaxWidth(50);
            eventsHistoryjTable.getColumnModel().getColumn(8).setMinWidth(50);
            eventsHistoryjTable.getColumnModel().getColumn(8).setPreferredWidth(50);
            eventsHistoryjTable.getColumnModel().getColumn(8).setMaxWidth(50);
        }

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 153, 51));
        jLabel1.setText("Events History");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(370, 370, 370)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 0, 204), 2, true));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Type : ");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Fee :");

        tourTypejComboBox.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        tourTypejComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "---Select Item ---", "Local", "National", "International", "Camp", "Other" }));

        feejTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                feejTextFieldActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Title : ");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("End : ");

        endDatejDateChooser.setDateFormatString("d MMM, yyyy");
        endDatejDateChooser.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        endDatejDateChooser.setMinimumSize(new java.awt.Dimension(60, 40));
        endDatejDateChooser.setPreferredSize(new java.awt.Dimension(40, 30));

        startDatejDateChooser.setDateFormatString("d MMM, yyyy");
        startDatejDateChooser.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        startDatejDateChooser.setMinimumSize(new java.awt.Dimension(60, 40));
        startDatejDateChooser.setPreferredSize(new java.awt.Dimension(40, 30));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Start : ");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("Spot : ");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setText("Member :");

        membertjTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                membertjTextFieldActionPerformed(evt);
            }
        });

        addjButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        addjButton.setForeground(new java.awt.Color(0, 153, 31));
        addjButton.setText("ADD");

        DeletejButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        DeletejButton.setForeground(new java.awt.Color(0, 153, 31));
        DeletejButton.setText("Delete");

        UpDatejButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        UpDatejButton.setForeground(new java.awt.Color(0, 153, 31));
        UpDatejButton.setText("UpDate");
        UpDatejButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpDatejButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(UpDatejButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DeletejButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addjButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(addjButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(UpDatejButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addComponent(DeletejButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        statusjComboBox.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        statusjComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "--- Select ---", "new", "old" }));
        statusjComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                statusjComboBoxActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Status : ");

        spotTypejComboBox.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N

        spotAddjButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        spotAddjButton.setForeground(new java.awt.Color(0, 153, 31));
        spotAddjButton.setText("ADD");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(startDatejDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(104, 104, 104)
                                .addComponent(jLabel8)
                                .addGap(10, 10, 10)
                                .addComponent(endDatejDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(tourTitlejTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(108, 108, 108))
                                .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addComponent(membertjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(51, 51, 51)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(feejTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(tourTypejComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spotListTitlejTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(spotTypejComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(spotAddjButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(statusjComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)))
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tourTitlejTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(membertjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(feejTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(endDatejDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(startDatejDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(tourTypejComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(spotListTitlejTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(spotTypejComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(spotAddjButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(statusjComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(12, Short.MAX_VALUE))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 0, 204));
        jLabel10.setText("Create Event");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(419, 419, 419)
                .addComponent(jLabel10)
                .addContainerGap(429, Short.MAX_VALUE))
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
        accountsInfojMenuItem.setText("accounts info.");
        accountsInformationjMenu.add(accountsInfojMenuItem);

        memberfeejCheckBoxMenuItem.setText("member fee");
        accountsInformationjMenu.add(memberfeejCheckBoxMenuItem);

        EventsjMenuBar.add(accountsInformationjMenu);

        eventsjMenu.setForeground(new java.awt.Color(0, 153, 51));
        eventsjMenu.setText("Events");
        eventsjMenu.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N

        eventsjMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        eventsjMenuItem.setForeground(new java.awt.Color(0, 153, 51));
        eventsjMenuItem.setText("events");
        eventsjMenu.add(eventsjMenuItem);

        reg_jMenuItem.setText("registration");
        eventsjMenu.add(reg_jMenuItem);

        SpotjCheckBoxMenuItem.setSelected(true);
        SpotjCheckBoxMenuItem.setText("Spot");
        eventsjMenu.add(SpotjCheckBoxMenuItem);

        EventsjMenuBar.add(eventsjMenu);

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

        exitjMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        exitjMenuItem.setText("exit");
        exitsjMenu.add(exitjMenuItem);

        EventsjMenuBar.add(exitsjMenu);

        setJMenuBar(EventsjMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(14, 14, 14))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void executiveMemberjMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_executiveMemberjMenuItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_executiveMemberjMenuItemActionPerformed
    private void generalMemberjMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generalMemberjMenuItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_generalMemberjMenuItemActionPerformed
    private void UpDatejButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpDatejButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_UpDatejButtonActionPerformed
    private void feejTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_feejTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_feejTextFieldActionPerformed
    private void previousMessagejMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_previousMessagejMenuItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_previousMessagejMenuItemActionPerformed
    private void membertjTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_membertjTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_membertjTextFieldActionPerformed
    private void statusjComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_statusjComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_statusjComboBoxActionPerformed

    
    
    private void eventsHistoryjTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eventsHistoryjTableMouseClicked
        // TODO add your handling code here:
        
        System.out.println("click........!!");
        int rowNumber = eventsHistoryjTable.getSelectedRow();
        TableModel model = eventsHistoryjTable.getModel();
        current_event_id = Integer.parseInt(model.getValueAt(rowNumber, 0).toString());
        tourTitlejTextField1.setText(model.getValueAt(rowNumber, 1).toString());
        spotListTitlejTextField.setText(model.getValueAt(rowNumber, 3).toString());
        feejTextField.setText(model.getValueAt(rowNumber, 7).toString());
        membertjTextField.setText(model.getValueAt(rowNumber, 6).toString());
        
        //System.out.println("id = " + current_event_id);       
    }//GEN-LAST:event_eventsHistoryjTableMouseClicked

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EventsInformation(1).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton DeletejButton;
    private javax.swing.JMenu DeveloperjMenu;
    private javax.swing.JMenuItem DeveloperjMenuItem;
    private javax.swing.JMenuBar EventsjMenuBar;
    private javax.swing.JMenu ExecutiveMemberjMenu;
    private javax.swing.JMenu HomejMenu;
    private javax.swing.JMenu SettingjMenu;
    private javax.swing.JMenuItem SettingjMenuItem;
    private javax.swing.JCheckBoxMenuItem SpotjCheckBoxMenuItem;
    private javax.swing.JButton UpDatejButton;
    private javax.swing.JMenuItem accountsInfojMenuItem;
    private javax.swing.JMenu accountsInformationjMenu;
    private javax.swing.JButton addjButton;
    private com.toedter.calendar.JDateChooser endDatejDateChooser;
    private javax.swing.JMenu endMessagejMenu;
    private javax.swing.JTable eventsHistoryjTable;
    private javax.swing.JMenu eventsjMenu;
    private javax.swing.JMenuItem eventsjMenuItem;
    private javax.swing.JMenuItem executiveMemberjMenuItem;
    private javax.swing.JMenuItem exitjMenuItem;
    private javax.swing.JMenu exitsjMenu;
    private javax.swing.JTextField feejTextField;
    private javax.swing.JMenuItem generalMemberjMenuItem;
    private javax.swing.JMenuItem homejMenuItem;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JMenuItem memberfeejCheckBoxMenuItem;
    private javax.swing.JTextField membertjTextField;
    private javax.swing.JMenuItem previousMessagejMenuItem;
    private javax.swing.JMenuItem reg_jMenuItem;
    private javax.swing.JMenuItem sendMessagejMenuItem;
    private javax.swing.JButton spotAddjButton;
    private javax.swing.JTextField spotListTitlejTextField;
    private javax.swing.JComboBox spotTypejComboBox;
    private com.toedter.calendar.JDateChooser startDatejDateChooser;
    private javax.swing.JComboBox statusjComboBox;
    private javax.swing.JTextField tourTitlejTextField1;
    private javax.swing.JComboBox tourTypejComboBox;
    // End of variables declaration//GEN-END:variables
}
