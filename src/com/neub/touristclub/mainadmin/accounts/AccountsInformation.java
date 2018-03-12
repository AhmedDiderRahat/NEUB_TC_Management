package com.neub.touristclub.mainadmin.accounts;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import com.neub.touristclub.mainadmin.HomePage;

import com.neub.touristclub.bean.AccountInfoBean;
import com.neub.touristclub.databaseconnection.DbConnector;
import com.neub.touristclub.developer.Developer;
import com.neub.touristclub.mainadmin.adminsetting.Setting;
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
 * @author Team-R&D_v.3
 */
public class AccountsInformation extends javax.swing.JFrame implements ActionListener{

    public static int adminIdKey, account_id;
    ArrayList<information> listIncome;
    ArrayList<information> listExpense;
    public static String temp="", currentTotalBalance="";
    public AccountsInformation(int key) throws SQLException {
        initComponents();
        
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        
        this.setResizable(false);
        adminIdKey = key;
        AddjButton.addActionListener(this);
        UpDatejButton.addActionListener(this);
        DeletejButton.addActionListener(this);
        PrintToTableIncome();
        PrintToTableExpense();
        this.showComboBox(IncmoeTypejComboBox);
        calculationFund();
        TotalAmountjLabel.setText(currentTotalBalance);
        enterMonthlyFee();
        
        //menu handeling
        homejMenuItem.addActionListener(this);
        generalMemberjMenuItem.addActionListener(this);
        executiveMemberjMenuItem.addActionListener(this);
        memberfeejCheckBoxMenuItem.addActionListener(this);
        //accountsInfojMenuItem.addActionListener(this);
        eventsjMenuItem.addActionListener(this);
        sendMessagejMenuItem.addActionListener(this);
        previousMessagejMenuItem.addActionListener(this);
        adminSettingjMenuItem.addActionListener(this);
        developerJMenuItem.addActionListener(this);
        exitjMenuItem.addActionListener(this);        
    }
    
    public class InformationOfFee{
        String year;
        int amount;
        boolean monthArray[] = new boolean[12];
        
        InformationOfFee(String year, boolean monthArray[], int amount)
        {
            this.year = year;
            this.monthArray = monthArray;
            this.amount = amount;
        }
        
        InformationOfFee(String year, int amount)
        {
            this.year = year;
            this.amount = amount;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public boolean[] getMonthArray() {
            return monthArray;
        }

        public void setMonthArray(boolean[] monthArray) {
            this.monthArray = monthArray;
        } 

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }     
    }
    
    public ArrayList enterMonthlyFee()
    {      
        try {            
            ArrayList<InformationOfFee> listOfFee = new ArrayList<InformationOfFee>();            
            String selectIdQuery = "SELECT * FROM executive_member_fee";
            DbConnector connector1 = new DbConnector();            
            connector1.resultset = connector1.statement.executeQuery(selectIdQuery);
            boolean tempArr[] = new boolean[12];
            while (connector1.resultset.next()) {  
                String tempYear = connector1.resultset.getString("year").toString();
                tempArr[0] = connector1.resultset.getBoolean("jan");
                tempArr[1] = connector1.resultset.getBoolean("feb");
                tempArr[2] = connector1.resultset.getBoolean("mar");
                tempArr[3] = connector1.resultset.getBoolean("apr");
                tempArr[4] = connector1.resultset.getBoolean("may");
                tempArr[5] = connector1.resultset.getBoolean("jun");
                tempArr[6] = connector1.resultset.getBoolean("jul");
                tempArr[7] = connector1.resultset.getBoolean("aug");
                tempArr[8]  = connector1.resultset.getBoolean("sep");
                tempArr[9] = connector1.resultset.getBoolean("oct");
                tempArr[10] = connector1.resultset.getBoolean("nov");
                tempArr[11] = connector1.resultset.getBoolean("decm");
                int amount = connector1.resultset.getInt("amount");
                
                InformationOfFee iof = new InformationOfFee(tempYear, tempArr, amount);                
                listOfFee.add(iof);                
            }
            
            int sum[] = new int[50], yn = 0;

            ArrayList<String>years = new ArrayList<>();
            
            for (int i = 0; i < listOfFee.size(); i++) {
                int t;
                for(t=0; t<years.size(); t++)
                {
                    if(years.get(t).equals(listOfFee.get(i).year))
                        break;
                }

                if(t==years.size())
                {
                    years.add(listOfFee.get(i).year);
                    yn = years.size()-1;
                }
                else
                    yn = t;
                
                for (int j = 0; j < 12; j++) {
                    if (listOfFee.get(i).monthArray[j] == true) {
                        sum[yn] += listOfFee.get(i).getAmount();
                    }
                }
            }
            
            ArrayList<InformationOfFee> al = new ArrayList<>();            
            for(int i=0; i<years.size(); i++){
                //System.out.println(years.get(i) + " " + sum[i]);
                InformationOfFee fee = new InformationOfFee(years.get(i), sum[i]);
                al.add(fee);
            }
            
            connector1.connection.close();
            
            return al;
            
        } catch (SQLException ex) {
            Logger.getLogger(AccountsInformation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public String dateCalculation() {
        java.util.Date indate = DatejDateChooser.getDate();
        
        
        if(indate == null)
            return "";

        String dateString = null;
        SimpleDateFormat sdfr = new SimpleDateFormat("dd/MMM/yyyy");

        dateString = sdfr.format(indate);

        return dateString;
    }
    
    public boolean  chechAmount(String amount)
    {
       try
        {
          Double.parseDouble(amount);
          return true;
        }
        catch(NumberFormatException e)
        {
            return false;
        }
       
    }
    
    @Override
    public void actionPerformed(ActionEvent button) {
        
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
                //GeneralMember generalMember = new GeneralMember(adminIdKey);
                this.dispose();
            } catch (SQLException ex) {
                Logger.getLogger(ExecutiveMember.class.getName()).log(Level.SEVERE, null, ex);
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
        
        if(button.getSource() == exitjMenuItem)
        {
            try {
                this.dispose();
            } catch (Exception e) {
            }
        }
        
        if(button.getSource() == developerJMenuItem)
        {
            try {
                Developer d = new Developer(adminIdKey);
                this.dispose();
            } catch (Exception e) {
            }
        }
        
        //Add Portion: 
        if (button.getSource() == AddjButton) {
            try {
                AccountInfoBean bean = new AccountInfoBean();
                bean.setDate(dateCalculation());
                bean.setType(IncmoeTypejComboBox.getSelectedItem().toString());
                bean.setReason(ReasonjTextField.getText().trim());
                
                if (!(chechAmount(AmountjTextField.getText().trim())) || (bean.getDate().equals("")) || (bean.getReason().equals(""))) {
                    JOptionPane.showMessageDialog(null, "Fill up data properly...!!!");
                } else {
                    try {
                        bean.setAmount(Double.parseDouble(AmountjTextField.getText().trim()));
                        //System.out.println(bean.getDate() + " " + bean.getType() + " "
                              //  + bean.getReason() + " " + (bean.getAmount() + 10));
                        
                        DbConnector connector = new DbConnector();
                        String insertQuery = "INSERT INTO club_fund " + "(date, "
                                + "reason, type, amount) VALUES ('" + bean.getDate()
                                + "', '" + bean.getReason() + "', '" + bean.getType()
                                + "', '" + bean.getAmount() + "')";                       
                        
                        connector.statement.executeUpdate(insertQuery);
                        connector.connection.close();                        
                        JOptionPane.showMessageDialog(null, "Successfully Added...!!!");
                                           
                        if(bean.getType().equals("Income")){
                            
                            //monthly Income
                            enterMonthlyFee();
                            
                            DefaultTableModel model = (DefaultTableModel) IncomeInformationjTable.getModel();
                            model.setRowCount(0);
                            PrintToTableIncome();
                        } else{
                            DefaultTableModel model = (DefaultTableModel) ExpenseInformationjTable.getModel();
                            model.setRowCount(0);
                            PrintToTableExpense();
                        }                        
                    } catch (SQLException ex) {
                        Logger.getLogger(AccountsInformation.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                temp = "null";
                this.showComboBox(IncmoeTypejComboBox);
                ReasonjTextField.setText("");
                AmountjTextField.setText("");
                calculationFund();
                TotalAmountjLabel.setText(currentTotalBalance);
            } catch (SQLException ex) 
            { Logger.getLogger(AccountsInformation.class.getName()).log(Level.SEVERE, null, ex); }
        }
        
        //update : 
        if (button.getSource() == UpDatejButton) {
            AccountInfoBean bean = new AccountInfoBean();
            bean.setDate(dateCalculation());
            bean.setType(IncmoeTypejComboBox.getSelectedItem().toString());
            bean.setReason(ReasonjTextField.getText().trim());

            if (!(chechAmount(AmountjTextField.getText().trim()))
                    || (bean.getDate().equals(""))
                    || (bean.getReason().equals(""))) {
                JOptionPane.showMessageDialog(null, "Fill up data properly...!!!");
            } else {
                try {
                    bean.setAmount(Double.parseDouble(AmountjTextField.getText().trim()));
                    //System.out.println(bean.getDate() + " " + bean.getType() + " "
                    //        + bean.getReason() + " " + (bean.getAmount()));

                    DbConnector connector = new DbConnector();
                    String UpdateQuery = "UPDATE club_fund " + " SET "
                            + "date = '" + bean.getDate() + "' , "
                            + "reason = '" + bean.getReason() + "' , "
                            + "type = '" + bean.getType() + "' , "
                            + "amount = '" + bean.getAmount() + "'  "
                            + "WHERE account_id = " + account_id;

                    connector.statement.executeUpdate(UpdateQuery);
                    connector.connection.close();

                    JOptionPane.showMessageDialog(null, "Successfully Added...!!!");

                    DefaultTableModel model = (DefaultTableModel) IncomeInformationjTable.getModel();
                    model.setRowCount(0);
                    PrintToTableIncome();
                    DefaultTableModel model2 = (DefaultTableModel) ExpenseInformationjTable.getModel();
                    model2.setRowCount(0);
                    PrintToTableExpense();
                    temp = "null";
                    this.showComboBox(IncmoeTypejComboBox);

                    ReasonjTextField.setText("");
                    AmountjTextField.setText("");
                    calculationFund();
                    TotalAmountjLabel.setText(currentTotalBalance);
                } catch (SQLException ex) {
                    Logger.getLogger(AccountsInformation.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        //delete option
        if (button.getSource() == DeletejButton) {
            //System.out.println("delet");
            try {
                //System.out.println("Account number : " + account_id);
                DbConnector dbConnector = new DbConnector();
                String deleteQuery = "DELETE FROM club_fund " + "WHERE account_id = " + "'" + account_id + "'";
                dbConnector.statement.executeUpdate(deleteQuery);
                dbConnector.connection.close();
                JOptionPane.showMessageDialog(null, "Data Successfully Deleted...!!!");
                DefaultTableModel model = (DefaultTableModel) IncomeInformationjTable.getModel();
                model.setRowCount(0);
                PrintToTableIncome();
                DefaultTableModel model2 = (DefaultTableModel) ExpenseInformationjTable.getModel();
                model2.setRowCount(0);
                PrintToTableExpense();
                temp = "null";
                this.showComboBox(IncmoeTypejComboBox);

                ReasonjTextField.setText("");
                AmountjTextField.setText("");
                calculationFund();
                TotalAmountjLabel.setText(currentTotalBalance);

            } catch (SQLException ex) 
            { Logger.getLogger(AccountsInformation.class.getName()).log(Level.SEVERE, null, ex); }
        }
    }
    
    //calculation account   
    public double calculationFund() throws SQLException {

        DbConnector connector = new DbConnector();
        String selectQuery = "SELECT type, amount FROM club_fund"; 
        connector.resultset = connector.statement.executeQuery(selectQuery);

        double sumOfAmount = 0.0;
        while (connector.resultset.next()) 
        {
            String Type = connector.resultset.getString("type");
            double amount = connector.resultset.getDouble("amount");
            //System.out.println(Type);

            if (Type.equals("Income")) {
                sumOfAmount += amount;
            } else {
                sumOfAmount -= amount;
            }
        }
        connector.connection.close();
        //System.out.println("sum of amount = " + sumOfAmount);
        
        currentTotalBalance = sumOfAmount+" Taka Only.";
        
        return sumOfAmount;
    }
    
    //IncmoeTypejComboBox
    public void showComboBox(JComboBox object) throws SQLException {

        String[] accountsList = new String[5];
        String temp1 = "Expense";
        String temp2 = "Income";
        String currentStage = "";

        if (temp.equals("Expense")) {
            accountsList[0] = temp2;
            currentStage = temp1;
        } else if (temp.equals("Income")) {
            accountsList[0] = temp1;
            currentStage = temp2;
        } else if (temp.equals("null")) {
            accountsList[0] = temp1;
            accountsList[1] = temp2;
            currentStage = "Select Type";
        } else {
            accountsList[0] = temp1;
            accountsList[1] = temp2;
            currentStage = "Select Type";
        }

        object.setModel(new DefaultComboBoxModel(accountsList));
        object.insertItemAt(currentStage, 0);
        object.setSelectedIndex(0);
        object.toString();
    }

    public class information {

        public String date, reason, type;
        public int account_id;
        public double amount;

        public information(int account_id, String date, String reason, String type, double amount) {
            this.account_id = account_id;
            this.date = date;
            this.reason = reason;
            this.type = type;
            this.amount = amount;
        }
    }
    
    public ArrayList dataRetriveFromDatabaseForIncome() throws SQLException {

        DbConnector connector = new DbConnector();
        String selectQuery = "SELECT * FROM club_fund WHERE " + "TYPE" + " =  'Income'";
        connector.resultset = connector.statement.executeQuery(selectQuery);
        listIncome = new ArrayList<information>();
        
        while (connector.resultset.next()) {
            int account_id = connector.resultset.getInt("account_id");
            String date = connector.resultset.getString("date");
            String reason = connector.resultset.getString("reason");
            String type = connector.resultset.getString("type");
            double amount = connector.resultset.getDouble("amount");
            information info = new information(account_id, date, reason, type, amount);

            listIncome.add(info);
        }
        connector.connection.close();
        return listIncome;
    }
    
    public void PrintToTableIncome() throws SQLException {

        DefaultTableModel model = (DefaultTableModel) IncomeInformationjTable.getModel();
        ArrayList<information> ReciveList = dataRetriveFromDatabaseForIncome();
        Object rawData[] = new Object[4];

        for (int i = 0; i < ReciveList.size(); i++) 
        {
            rawData[0] = ReciveList.get(i).account_id;
            rawData[1] = ReciveList.get(i).date;
            rawData[2] = ReciveList.get(i).reason;
            rawData[3] = ReciveList.get(i).amount;
            model.addRow(rawData);
        }
        
        ArrayList<InformationOfFee> fees = enterMonthlyFee();
        for(int i=0; i<fees.size(); i++)
        {
            rawData[0] = "null";
            rawData[1] = "null";
            rawData[2] = "Members fees of " + fees.get(i).year;
            rawData[3] = fees.get(i).amount;
            model.addRow(rawData);
        }
    }
    
    public ArrayList dataRetriveFromDatabaseForExpense() throws SQLException {

        DbConnector connector = new DbConnector();
        String selectQuery = "SELECT * FROM club_fund WHERE " + "TYPE" +  " =  'Expense'";
        connector.resultset = connector.statement.executeQuery(selectQuery);
        
        listExpense = new ArrayList<information>();
        while (connector.resultset.next()) {
            int account_id = connector.resultset.getInt("account_id");
            String date = connector.resultset.getString("date");
            String reason = connector.resultset.getString("reason");
            String type = connector.resultset.getString("type");
            double amount = connector.resultset.getDouble("amount");
            information info = new information(account_id, date, reason, type, amount);
            listExpense.add(info);
        }
        
        connector.connection.close();
        return listExpense;
    }
    
    public void PrintToTableExpense() throws SQLException {

        DefaultTableModel model = (DefaultTableModel) ExpenseInformationjTable.getModel();
        ArrayList<information> ReciveList1 = dataRetriveFromDatabaseForExpense();
        Object rawData[] = new Object[4];

        for (int i = 0; i < ReciveList1.size(); i++) 
        {
            rawData[0] = ReciveList1.get(i).account_id;
            rawData[1] = ReciveList1.get(i).date;
            rawData[2] = ReciveList1.get(i).reason;
            rawData[3] = ReciveList1.get(i).amount;
            model.addRow(rawData);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        IncomeInformationjTable = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        ExpenseInformationjTable = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        TotalAmountjLabel = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        DatejDateChooser = new com.toedter.calendar.JDateChooser();
        IncmoeTypejComboBox = new javax.swing.JComboBox();
        ReasonjTextField = new javax.swing.JTextField();
        AmountjTextField = new javax.swing.JTextField();
        AddjButton = new javax.swing.JButton();
        UpDatejButton = new javax.swing.JButton();
        DeletejButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        AccountsjMenuBar = new javax.swing.JMenuBar();
        HomejMenu = new javax.swing.JMenu();
        homejMenuItem = new javax.swing.JMenuItem();
        ExecutiveMemberjMenu = new javax.swing.JMenu();
        executiveMemberjMenuItem = new javax.swing.JMenuItem();
        generalMemberjMenuItem = new javax.swing.JMenuItem();
        accountsInformationjMenu = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        memberfeejCheckBoxMenuItem = new javax.swing.JMenuItem();
        eventsjMenu = new javax.swing.JMenu();
        eventsjMenuItem = new javax.swing.JMenuItem();
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

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 0, 204), 3));

        IncomeInformationjTable.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        IncomeInformationjTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "sl#", "Date", "Reason", "Amount"
            }
        ));
        IncomeInformationjTable.setRowHeight(30);
        IncomeInformationjTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                IncomeInformationjTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(IncomeInformationjTable);
        if (IncomeInformationjTable.getColumnModel().getColumnCount() > 0) {
            IncomeInformationjTable.getColumnModel().getColumn(0).setMinWidth(35);
            IncomeInformationjTable.getColumnModel().getColumn(0).setPreferredWidth(35);
            IncomeInformationjTable.getColumnModel().getColumn(0).setMaxWidth(35);
            IncomeInformationjTable.getColumnModel().getColumn(1).setMinWidth(150);
            IncomeInformationjTable.getColumnModel().getColumn(1).setPreferredWidth(150);
            IncomeInformationjTable.getColumnModel().getColumn(1).setMaxWidth(150);
            IncomeInformationjTable.getColumnModel().getColumn(3).setMinWidth(150);
            IncomeInformationjTable.getColumnModel().getColumn(3).setPreferredWidth(150);
            IncomeInformationjTable.getColumnModel().getColumn(3).setMaxWidth(150);
        }

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 153, 51));
        jLabel1.setText("Income History");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(233, 233, 233)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1)
        );

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 153, 51));
        jLabel2.setText("Expense History");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(229, 229, 229)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        ExpenseInformationjTable.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        ExpenseInformationjTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "sl#", "Date", "Reason", "Amount"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        ExpenseInformationjTable.setRowHeight(30);
        ExpenseInformationjTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ExpenseInformationjTableMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(ExpenseInformationjTable);
        if (ExpenseInformationjTable.getColumnModel().getColumnCount() > 0) {
            ExpenseInformationjTable.getColumnModel().getColumn(0).setMinWidth(35);
            ExpenseInformationjTable.getColumnModel().getColumn(0).setPreferredWidth(35);
            ExpenseInformationjTable.getColumnModel().getColumn(0).setMaxWidth(35);
            ExpenseInformationjTable.getColumnModel().getColumn(1).setMinWidth(150);
            ExpenseInformationjTable.getColumnModel().getColumn(1).setPreferredWidth(150);
            ExpenseInformationjTable.getColumnModel().getColumn(1).setMaxWidth(150);
            ExpenseInformationjTable.getColumnModel().getColumn(3).setMinWidth(150);
            ExpenseInformationjTable.getColumnModel().getColumn(3).setPreferredWidth(150);
            ExpenseInformationjTable.getColumnModel().getColumn(3).setMaxWidth(150);
        }

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 0, 204));
        jLabel8.setText("Net Total : ");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(324, 324, 324)
                .addComponent(TotalAmountjLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TotalAmountjLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 625, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 625, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 0, 204), 2, true));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Date : ");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Type : ");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Amount : ");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Reason : ");

        DatejDateChooser.setDateFormatString("d MMM, yyyy");
        DatejDateChooser.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        DatejDateChooser.setMinimumSize(new java.awt.Dimension(60, 40));
        DatejDateChooser.setPreferredSize(new java.awt.Dimension(40, 30));

        IncmoeTypejComboBox.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        IncmoeTypejComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Income", "Expense" }));

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

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(AmountjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(AddjButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(UpDatejButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(DeletejButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(ReasonjTextField)
                                        .addComponent(IncmoeTypejComboBox, 0, 150, Short.MAX_VALUE))
                                    .addComponent(DatejDateChooser, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DatejDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(IncmoeTypejComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ReasonjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AmountjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AddjButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(UpDatejButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DeletejButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(32, Short.MAX_VALUE))
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

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 0, 204));
        jLabel7.setText("Accounts Information");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(232, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addGap(197, 197, 197))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel7)
                .addGap(0, 14, Short.MAX_VALUE))
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

        accountsInformationjMenu.setForeground(new java.awt.Color(0, 153, 51));
        accountsInformationjMenu.setText("Accounts");
        accountsInformationjMenu.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem4.setForeground(new java.awt.Color(0, 153, 51));
        jMenuItem4.setText("accounts info.");
        accountsInformationjMenu.add(jMenuItem4);

        memberfeejCheckBoxMenuItem.setText("member fees");
        accountsInformationjMenu.add(memberfeejCheckBoxMenuItem);

        AccountsjMenuBar.add(accountsInformationjMenu);

        eventsjMenu.setText("Events");
        eventsjMenu.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N

        eventsjMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        eventsjMenuItem.setText("events");
        eventsjMenu.add(eventsjMenuItem);

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
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20))))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(379, 379, 379)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(380, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(35, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(288, 288, 288)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(288, Short.MAX_VALUE)))
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

    private void previousMessagejMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_previousMessagejMenuItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_previousMessagejMenuItemActionPerformed

    private void IncomeInformationjTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_IncomeInformationjTableMouseClicked
        // TODO add your handling code here:

        int rowNumber = IncomeInformationjTable.getSelectedRow();
        TableModel model = IncomeInformationjTable.getModel();

        if (!model.getValueAt(rowNumber, 0).toString().equals("null")) {
            
            account_id = Integer.parseInt(model.getValueAt(rowNumber, 0).toString());
            temp = "Income";
            ReasonjTextField.setText(model.getValueAt(rowNumber, 2).toString());
            AmountjTextField.setText(model.getValueAt(rowNumber, 3).toString());
            
            try {
                this.showComboBox(IncmoeTypejComboBox);
            } catch (SQLException ex) {
                Logger.getLogger(AccountsInformation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_IncomeInformationjTableMouseClicked

    private void ExpenseInformationjTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ExpenseInformationjTableMouseClicked
        // TODO add your handling code here:
        
        int rowNumber = ExpenseInformationjTable.getSelectedRow();
        TableModel model = ExpenseInformationjTable.getModel();
        
        account_id = Integer.parseInt(model.getValueAt(rowNumber, 0).toString()); 
        temp = "Expense";
        ReasonjTextField.setText(model.getValueAt(rowNumber, 2).toString());
        AmountjTextField.setText(model.getValueAt(rowNumber, 3).toString());

        try {
            this.showComboBox(IncmoeTypejComboBox);
        } catch (SQLException ex) {
            Logger.getLogger(AccountsInformation.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_ExpenseInformationjTableMouseClicked

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new AccountsInformation(1).setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(AccountsInformation.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar AccountsjMenuBar;
    private javax.swing.JButton AddjButton;
    private javax.swing.JTextField AmountjTextField;
    private com.toedter.calendar.JDateChooser DatejDateChooser;
    private javax.swing.JButton DeletejButton;
    private javax.swing.JMenu DeveloperjMenu;
    private javax.swing.JMenu ExecutiveMemberjMenu;
    private javax.swing.JTable ExpenseInformationjTable;
    private javax.swing.JMenu HomejMenu;
    private javax.swing.JComboBox IncmoeTypejComboBox;
    private javax.swing.JTable IncomeInformationjTable;
    private javax.swing.JTextField ReasonjTextField;
    private javax.swing.JMenu SettingjMenu;
    private javax.swing.JLabel TotalAmountjLabel;
    private javax.swing.JButton UpDatejButton;
    private javax.swing.JMenu accountsInformationjMenu;
    private javax.swing.JMenuItem adminSettingjMenuItem;
    private javax.swing.JMenuItem developerJMenuItem;
    private javax.swing.JMenu endMessagejMenu;
    private javax.swing.JMenu eventsjMenu;
    private javax.swing.JMenuItem eventsjMenuItem;
    private javax.swing.JMenuItem executiveMemberjMenuItem;
    private javax.swing.JMenuItem exitjMenuItem;
    private javax.swing.JMenu exitsjMenu;
    private javax.swing.JMenuItem generalMemberjMenuItem;
    private javax.swing.JMenuItem homejMenuItem;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JMenuItem memberfeejCheckBoxMenuItem;
    private javax.swing.JMenuItem previousMessagejMenuItem;
    private javax.swing.JMenuItem sendMessagejMenuItem;
    // End of variables declaration//GEN-END:variables
}
