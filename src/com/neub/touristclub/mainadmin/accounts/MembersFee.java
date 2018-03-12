package com.neub.touristclub.mainadmin.accounts;

import com.neub.touristclub.bean.ExecutivelMemberFeeBean;
import com.neub.touristclub.databaseconnection.DbConnector;
import com.neub.touristclub.mainadmin.HomePage;
import com.neub.touristclub.mainadmin.adminsetting.Setting;
import com.neub.touristclub.mainadmin.events.EventsInformation;
import com.neub.touristclub.mainadmin.members.ExecutiveMember;
import com.neub.touristclub.mainadmin.members.GeneralMember;
import com.neub.touristclub.message.PreviousMessage;
import com.neub.touristclub.message.SendMessage;
import com.neub.touristclub.developer.Developer;

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
public class MembersFee extends javax.swing.JFrame implements ActionListener {
    
    public int adminIdKey;
    public boolean edit_exe_member = false;
    public boolean edit_year = false;
    public String exe_current_name = "";
    public String current_year = "";
    public int current_efId = 0;
    ArrayList<ExecutivelMemberFeeBean> listFee;
    ArrayList<ExecutivelMemberFeeBean> listFeeReceive;

    public boolean isMember, isMonth, isYear, isSearch, isSearchYear;

    public MembersFee(int key) throws SQLException {
        key = adminIdKey;
        initComponents();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        isMember = true;
        this.showComboBox(memberjComboBox);
        isMember = false;

        isMonth = true;
        this.showComboBox(monthjComboBox);
        isMonth = false;

        isYear = true;
        this.showComboBox(yearjComboBox);
        isYear = false;

        isSearch = true;
        this.showComboBox(memberjComboBox1);
        isSearch = false;

        isYear = true;
        this.showComboBox(yearjComboBox1);
        isYear = false;

        addFeejButton.addActionListener(this);
        upDatejButton.addActionListener(this);
        deletejButton.addActionListener(this);
        searchjButton.addActionListener(this);
        
        //menu handeling
        homejMenuItem.addActionListener(this);
        generalMemberjMenuItem.addActionListener(this);
        executiveMemberjMenuItem.addActionListener(this);
        accountsInfojMenuItem.addActionListener(this);
        eventsjMenuItem.addActionListener(this);
        sendMessagejMenuItem.addActionListener(this);
        previousMessagejMenuItem.addActionListener(this);
        adminSettingjMenuItem.addActionListener(this);
        developerJMenuItem.addActionListener(this);
        exitjMenuItem.addActionListener(this);       
        
        
        PrintToTableFee();
    }

    public int getMonthNumber(String nameOfMonth) {
        switch (nameOfMonth) {
            case "January":
                return 1; 
            case "February":
                return 2;
            case "March":
                return 3;
            case "April":
                return 4;
            case "May":
                return 5;
            case "June":
                return 6;
            case "July":
                return 7;
            case "August":
                return 8;
            case "September":
                return 9;
            case "Otcobar":
                return 10;
            case "November":
                return 11;
            case "December":
                return 12;
        }
        return 0;
    }

    public String getMonth(String nameOfMonth) {

        String monthString;
        switch (nameOfMonth) {
            case "January":
                return "jan";
            case "February":
                return "feb";
            case "March":
                return "mar";
            case "April":
                return "apr";
            case "May":
                return "may";
            case "June":
                return "jun";
            case "July":
                return "jul";
            case "August":
                return "aug";
            case "September":
                return "sep";
            case "Otcobar":
                return "oct";
            case "November":
                return "nov";
            case "December":
                return "decm";
        }
        return null;
    }

    public boolean checkAmount(String amount) {
        try {
            Integer.parseInt(amount);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public int checkAmount2(String amount) {
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
        if(button.getSource() == homejMenuItem)
        {
            try {
                HomePage homePage = new HomePage(adminIdKey);
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
        
        if(button.getSource() == developerJMenuItem)
        {
            try {
                Developer d = new Developer(adminIdKey);
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
        
        
        
        if (button.getSource() == addFeejButton) {
            try {
                DbConnector connector1 = new DbConnector();
                ExecutivelMemberFeeBean emfb = new ExecutivelMemberFeeBean();
                emfb.setName(memberjComboBox.getSelectedItem().toString());
                emfb.setMonth(monthjComboBox.getSelectedItem().toString());
                emfb.setYear(yearjComboBox.getSelectedItem().toString());

                String selectIdQuery = "SELECT general_member.gm_id FROM general_member "
                        + " WHERE general_member.name = '" + emfb.getName() + "'";
                connector1.resultset = connector1.statement.executeQuery(selectIdQuery);
                while (connector1.resultset.next()) {
                    String tempId = connector1.resultset.getString("gm_id").toString();
                    emfb.setGm_id(tempId);
                }
                connector1.connection.close();

                if ((!checkAmount(amountJTextField.getText().trim()))
                        || (emfb.getGm_id() == null) || (emfb.getMonth() == null)
                        || (emfb.getYear() == null)) {

                    System.out.println("error");
                    JOptionPane.showMessageDialog(null, "Fill Information Properly...!!!");
                } else {

                    emfb.setAmount(Integer.parseInt(amountJTextField.getText().trim()));
                    DbConnector connector2 = new DbConnector();

                    String selectEfIdQuery = "SELECT * FROM executive_member_fee emf "
                            + " WHERE emf.gm_id = '" + emfb.getGm_id() + "' AND emf.year = '"
                            + emfb.getYear() + "'";

                    connector2.resultset = connector2.statement.executeQuery(selectEfIdQuery);
                    boolean isUpdate = false;
                    int tempId2 = 0;
                    boolean arrayMonth[] = new boolean[12];

                    while (connector2.resultset.next()) {
                        isUpdate = true;
                        tempId2 = connector2.resultset.getInt("emf.ef_id");
                        arrayMonth[0] = connector2.resultset.getBoolean("emf.jan");
                        arrayMonth[1] = connector2.resultset.getBoolean("emf.feb");
                        arrayMonth[2] = connector2.resultset.getBoolean("emf.mar");
                        arrayMonth[3] = connector2.resultset.getBoolean("emf.apr");
                        arrayMonth[4] = connector2.resultset.getBoolean("emf.may");
                        arrayMonth[5] = connector2.resultset.getBoolean("emf.jun");
                        arrayMonth[6] = connector2.resultset.getBoolean("emf.jul");
                        arrayMonth[7] = connector2.resultset.getBoolean("emf.aug");
                        arrayMonth[8] = connector2.resultset.getBoolean("emf.sep");
                        arrayMonth[9] = connector2.resultset.getBoolean("emf.oct");
                        arrayMonth[10] = connector2.resultset.getBoolean("emf.nov");
                        arrayMonth[11] = connector2.resultset.getBoolean("emf.decm");
                    }
                    connector2.connection.close();

                    if (isUpdate) {
                        int monthNo = getMonthNumber(emfb.getMonth());

                        String monthName = getMonth(emfb.getMonth());

                        if (arrayMonth[monthNo - 1] == true) {
                            JOptionPane.showMessageDialog(null, "Information Already Exist...!!!");
                        } else {

                            DbConnector connectorUpDate = new DbConnector();
                            String UpdateQuery = "UPDATE executive_member_fee " + " SET "
                                    + monthName + " = '" + 1 + "' , "
                                    + " amount = '" + emfb.getAmount() + "' "
                                    + "WHERE ef_id = " + tempId2;

                            connectorUpDate.statement.executeUpdate(UpdateQuery);
                            connectorUpDate.connection.close();
                            JOptionPane.showMessageDialog(null, "Successfully Added...!!!");
                        }
                    } else {
                        System.out.println("Add");

                        DbConnector connectorIn = new DbConnector();
                        String monthName = getMonth(emfb.getMonth());
                        String insertQuery = "INSERT INTO executive_member_fee " + "(gm_id, "
                                + " " + monthName + " , " + " year, amount) VALUES ('" + emfb.getGm_id()
                                + "', '" + 1 + "', '" + emfb.getYear()
                                + "', '" + emfb.getAmount() + "')";

                        connectorIn.statement.executeUpdate(insertQuery);
                        connectorIn.connection.close();
                        JOptionPane.showMessageDialog(null, "Successfully Added...!!!");
                    }

                    //table upDate.......          
                    DefaultTableModel model = (DefaultTableModel) memberFeejTable.getModel();
                    model.setRowCount(0);
                    PrintToTableFee();

                    amountJTextField.setText("");
                    isMember = true;
                    this.showComboBox(memberjComboBox);
                    isMember = false;

                    isMonth = true;
                    this.showComboBox(monthjComboBox);
                    isMonth = false;

                    isYear = true;
                    this.showComboBox(yearjComboBox);
                    isYear = false;
                }

            } catch (SQLException ex) {
                Logger.getLogger(MembersFee.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (button.getSource() == upDatejButton) {
            try {
                ExecutivelMemberFeeBean emfb = new ExecutivelMemberFeeBean();
                emfb.setEf_id(current_efId);
                emfb.setName(memberjComboBox.getSelectedItem().toString());
                emfb.setMonth(monthjComboBox.getSelectedItem().toString());
                emfb.setYear(yearjComboBox.getSelectedItem().toString());
                emfb.setAmount(checkAmount2(amountJTextField.getText().trim()));

                DbConnector connectorSelect = new DbConnector();

                String selectIdQuery = "SELECT general_member.gm_id FROM general_member "
                        + " WHERE general_member.name = '" + emfb.getName() + "'";

                connectorSelect.resultset = connectorSelect.statement.executeQuery(selectIdQuery);
                while (connectorSelect.resultset.next()) {
                    String tempId = connectorSelect.resultset.getString("gm_id").toString();
                    emfb.setGm_id(tempId);
                }
                connectorSelect.connection.close();

                String monthName = getMonth(emfb.getMonth());

                if ((!checkAmount(amountJTextField.getText().trim()))
                        || (emfb.getGm_id() == null) || (emfb.getMonth() == null)
                        || (emfb.getYear() == null)) {

                    //System.out.println("error");
                    JOptionPane.showMessageDialog(null, "Fill Information Properly...!!!");
                } else {
                    try {
                        DbConnector connectorUpDateMain = new DbConnector();
                        String UpdateQuery;
                        if (emfb.getAmount() == 0) {
                            System.out.println("rahat");
                            UpdateQuery = "UPDATE executive_member_fee " + " SET "
                                    + monthName + " = '" + 0 + "' , gm_id = " + emfb.getGm_id()
                                    + " WHERE ef_id = " + current_efId;
                        } else {
                            UpdateQuery = "UPDATE executive_member_fee " + " SET "
                                    + monthName + " = '" + 1 + "' , gm_id = " + emfb.getGm_id()
                                    + " , amount = '" + emfb.getAmount() + "' "
                                    + "WHERE ef_id = " + current_efId;
                        }
                        connectorUpDateMain.statement.executeUpdate(UpdateQuery);
                        connectorUpDateMain.connection.close();
                        JOptionPane.showMessageDialog(null, "Successfully UpDate...!!!");

                        //table upDate.......
                        DefaultTableModel model = (DefaultTableModel) memberFeejTable.getModel();
                        model.setRowCount(0);
                        PrintToTableFee();

                        amountJTextField.setText("");
                        isMember = true;
                        this.showComboBox(memberjComboBox);
                        isMember = false;

                        isMonth = true;
                        this.showComboBox(monthjComboBox);
                        isMonth = false;

                        isYear = true;
                        this.showComboBox(yearjComboBox);
                        isYear = false;
                    } catch (SQLException ex) {
                        Logger.getLogger(MembersFee.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(MembersFee.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (button.getSource() == deletejButton) {
            try {
                try {
                    //System.out.println("delet : " + current_efId);
                    DbConnector dbConnectorDelete = new DbConnector();
                    String deleteQuery = "DELETE FROM executive_member_fee " + "WHERE ef_id = " + "'" + current_efId + "'";
                    dbConnectorDelete.statement.executeUpdate(deleteQuery);
                    dbConnectorDelete.connection.close();
                    JOptionPane.showMessageDialog(null, "Data Successfully Deleted...!!!");
                } catch (SQLException ex) {
                    Logger.getLogger(MembersFee.class.getName()).log(Level.SEVERE, null, ex);
                }

                //table upDate.......
                DefaultTableModel model = (DefaultTableModel) memberFeejTable.getModel();
                model.setRowCount(0);
                PrintToTableFee();

                amountJTextField.setText("");
                isMember = true;
                edit_exe_member = false;
                this.showComboBox(memberjComboBox);
                isMember = false;

                isMonth = true;
                this.showComboBox(monthjComboBox);
                isMonth = false;

                isYear = true;
                edit_year = false;
                this.showComboBox(yearjComboBox);
                isYear = false;
            } catch (SQLException ex) {
                Logger.getLogger(MembersFee.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (button.getSource() == searchjButton) {
            try {
                String year = yearjComboBox1.getSelectedItem().toString();
                String name = memberjComboBox1.getSelectedItem().toString();
                String subQuery = "SELECT * FROM executive_member_fee WHERE year = '" + year
                        + "' AND gm_id = " + "( SELECT gm_id FROM general_member WHERE name = '"
                        + name + "' ) ";

                DbConnector connectorSearch = new DbConnector();
                connectorSearch.resultset = connectorSearch.statement.executeQuery(subQuery);
                boolean arrayOfmonth[] = new boolean[12];
                while (connectorSearch.resultset.next()) {
                    arrayOfmonth[0] = connectorSearch.resultset.getBoolean("jan");
                    arrayOfmonth[1] = connectorSearch.resultset.getBoolean("feb");
                    arrayOfmonth[2] = connectorSearch.resultset.getBoolean("mar");
                    arrayOfmonth[3] = connectorSearch.resultset.getBoolean("apr");
                    arrayOfmonth[4] = connectorSearch.resultset.getBoolean("may");
                    arrayOfmonth[5] = connectorSearch.resultset.getBoolean("jun");
                    arrayOfmonth[6] = connectorSearch.resultset.getBoolean("jul");
                    arrayOfmonth[7] = connectorSearch.resultset.getBoolean("aug");
                    arrayOfmonth[8] = connectorSearch.resultset.getBoolean("sep");
                    arrayOfmonth[9] = connectorSearch.resultset.getBoolean("oct");
                    arrayOfmonth[10] = connectorSearch.resultset.getBoolean("nov");
                    arrayOfmonth[11] = connectorSearch.resultset.getBoolean("decm");
                }
                SearchResult sr = new SearchResult(arrayOfmonth);
                connectorSearch.connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(MembersFee.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public ArrayList dataRetriveFromDatabaseForFee() throws SQLException {

        DbConnector connectorShow = new DbConnector();
        String selectFeeQuery = "SELECT emf.ef_id, gmt.name, emf.year, emf.jan, emf.feb,"
                + " emf.mar, emf.apr, emf.may, emf.jun, emf.jul, emf.aug, emf.sep, "
                + " emf.oct, emf.nov, emf.decm,  emf.amount"
                + " FROM executive_member_fee emf INNER JOIN general_member gmt "
                + "ON gmt.gm_id = emf.gm_id order by gmt.gm_id";

        connectorShow.resultset = connectorShow.statement.executeQuery(selectFeeQuery);
        listFee = new ArrayList<ExecutivelMemberFeeBean>();
        while (connectorShow.resultset.next()) {
            int ef_id = connectorShow.resultset.getInt("emf.ef_id");
            String gm_id = connectorShow.resultset.getString("gmt.name");
            String year = connectorShow.resultset.getString("emf.year");
            boolean jan = connectorShow.resultset.getBoolean("emf.jan");
            boolean feb = connectorShow.resultset.getBoolean("emf.feb");
            boolean mar = connectorShow.resultset.getBoolean("emf.mar");
            boolean apr = connectorShow.resultset.getBoolean("emf.apr");
            boolean may = connectorShow.resultset.getBoolean("emf.may");
            boolean jun = connectorShow.resultset.getBoolean("emf.jun");
            boolean jul = connectorShow.resultset.getBoolean("emf.jul");
            boolean aug = connectorShow.resultset.getBoolean("emf.aug");
            boolean sep = connectorShow.resultset.getBoolean("emf.sep");
            boolean oct = connectorShow.resultset.getBoolean("emf.oct");
            boolean nov = connectorShow.resultset.getBoolean("emf.nov");
            boolean dec = connectorShow.resultset.getBoolean("emf.decm");
            int amount = connectorShow.resultset.getInt("emf.amount");

            ExecutivelMemberFeeBean feeBean = new ExecutivelMemberFeeBean(ef_id, gm_id, year,
                    jan, feb, mar, apr, may, jun, jul, aug, sep, oct, nov, dec, amount);
            listFee.add(feeBean);
        }
        connectorShow.connection.close();
        return listFee;
    }

    public void PrintToTableFee() throws SQLException {

        DefaultTableModel model = (DefaultTableModel) memberFeejTable.getModel();
        ArrayList<ExecutivelMemberFeeBean> listFeeReceive = dataRetriveFromDatabaseForFee();
        Object rawData[] = new Object[15];

        for (int i = 0; i < listFeeReceive.size(); i++) {
            rawData[0] = listFeeReceive.get(i).getEf_id();
            rawData[1] = listFeeReceive.get(i).getName();
            rawData[2] = listFeeReceive.get(i).getYear();
            if (listFeeReceive.get(i).isJan()) {
                rawData[3] = listFeeReceive.get(i).getAmount();
            } else {
                rawData[3] = 0;
            }

            if (listFeeReceive.get(i).isFeb()) {
                rawData[4] = listFeeReceive.get(i).getAmount();
            } else {
                rawData[4] = 0;
            }

            if (listFeeReceive.get(i).isMar()) {
                rawData[5] = listFeeReceive.get(i).getAmount();
            } else {
                rawData[5] = 0;
            }

            if (listFeeReceive.get(i).isApr()) {
                rawData[6] = listFeeReceive.get(i).getAmount();
            } else {
                rawData[6] = 0;
            }

            if (listFeeReceive.get(i).isMay()) {
                rawData[7] = listFeeReceive.get(i).getAmount();
            } else {
                rawData[7] = 0;
            }

            if (listFeeReceive.get(i).isJun()) {
                rawData[8] = listFeeReceive.get(i).getAmount();
            } else {
                rawData[8] = 0;
            }

            if (listFeeReceive.get(i).isJul()) {
                rawData[9] = listFeeReceive.get(i).getAmount();
            } else {
                rawData[9] = 0;
            }

            if (listFeeReceive.get(i).isAug()) {
                rawData[10] = listFeeReceive.get(i).getAmount();
            } else {
                rawData[10] = 0;
            }

            if (listFeeReceive.get(i).isSep()) {
                rawData[11] = listFeeReceive.get(i).getAmount();
            } else {
                rawData[11] = 0;
            }

            if (listFeeReceive.get(i).isOct()) {
                rawData[12] = listFeeReceive.get(i).getAmount();
            } else {
                rawData[12] = 0;
            }

            if (listFeeReceive.get(i).isNov()) {
                rawData[13] = listFeeReceive.get(i).getAmount();
            } else {
                rawData[13] = 0;
            }

            if (listFeeReceive.get(i).isDec()) {
                rawData[14] = listFeeReceive.get(i).getAmount();
            } else {
                rawData[14] = 0;
            }

            model.addRow(rawData);
        }
    }

    public void showComboBox(JComboBox object) throws SQLException {

        if (isMember) {
            String[] temp = new String[100];
            String selectStudentQuery = "SELECT "
                    + "general_member.name "
                    + "FROM executive_member " + "INNER JOIN "
                    + "general_member ON general_member.gm_id = executive_member.gm_id "
                    + "order by " + "general_member.gm_id";

            DbConnector connector = new DbConnector();
            connector.resultset = connector.statement.executeQuery(selectStudentQuery);

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
                //System.out.println("name = " + generalMemberList[i]);
            }

            connector.connection.close();

            object.setModel(new DefaultComboBoxModel(generalMemberList));
            if (edit_exe_member) {
                object.insertItemAt(exe_current_name, 0);
            } else {
                object.insertItemAt("--Slecet Member--", 0);
            }

            object.setSelectedIndex(0);
            object.toString();
        }

        if (isSearch) {
            String[] temp = new String[100];
            String selectStudentQuery = "SELECT "
                    + "general_member.name "
                    + "FROM executive_member " + "INNER JOIN "
                    + "general_member ON general_member.gm_id = executive_member.gm_id "
                    + "order by " + "general_member.gm_id";

            DbConnector connector = new DbConnector();
            connector.resultset = connector.statement.executeQuery(selectStudentQuery);

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
            connector.connection.close();

            object.setModel(new DefaultComboBoxModel(generalMemberList));
            if (edit_exe_member) {
                object.insertItemAt(exe_current_name, 0);
            } else {
                object.insertItemAt("--Slecet Member--", 0);
            }
            object.setSelectedIndex(0);
            object.toString();
        }

        if (isMonth) {
            String[] temp = new String[12];
            temp[0] = "January";
            temp[1] = "February";
            temp[2] = "March";
            temp[3] = "April";
            temp[4] = "May";
            temp[5] = "June";
            temp[6] = "July";
            temp[7] = "August";
            temp[8] = "September";
            temp[9] = "Otcobar";
            temp[10] = "November";
            temp[11] = "December";

            object.setModel(new DefaultComboBoxModel(temp));
            object.insertItemAt("--Slecet Month--", 0);
            object.setSelectedIndex(0);
            object.toString();
        }

        if (isYear) {
            String[] temp = new String[13];
            temp[0] = "2013";
            temp[1] = "2014";
            temp[2] = "2015";
            temp[3] = "2016";
            temp[4] = "2017";
            temp[5] = "2018";
            temp[6] = "2019";
            temp[7] = "2020";
            temp[8] = "2021";
            temp[9] = "2022";
            temp[10] = "2023";
            temp[11] = "2024";
            temp[12] = "2025";

            object.setModel(new DefaultComboBoxModel(temp));
            if (edit_year) {
                object.insertItemAt(current_year, 0);
            } else {
                object.insertItemAt("--Slecet Year--", 0);
            }
            object.setSelectedIndex(0);
            object.toString();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        memberFeejTable = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        search = new javax.swing.JLabel();
        memberjComboBox1 = new javax.swing.JComboBox();
        jLabel28 = new javax.swing.JLabel();
        yearjComboBox1 = new javax.swing.JComboBox();
        jLabel29 = new javax.swing.JLabel();
        searchjButton = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        monthjComboBox = new javax.swing.JComboBox();
        amountJTextField = new javax.swing.JTextField();
        addFeejButton = new javax.swing.JButton();
        upDatejButton = new javax.swing.JButton();
        deletejButton = new javax.swing.JButton();
        yearjComboBox = new javax.swing.JComboBox();
        memberjComboBox = new javax.swing.JComboBox();
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

        memberFeejTable.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        memberFeejTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "sl#", "Name", "year", "Jan", "Feb", "Mar", "April", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
            }
        ));
        memberFeejTable.setRowHeight(30);
        memberFeejTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                memberFeejTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(memberFeejTable);
        if (memberFeejTable.getColumnModel().getColumnCount() > 0) {
            memberFeejTable.getColumnModel().getColumn(0).setMinWidth(55);
            memberFeejTable.getColumnModel().getColumn(0).setPreferredWidth(55);
            memberFeejTable.getColumnModel().getColumn(0).setMaxWidth(55);
            memberFeejTable.getColumnModel().getColumn(2).setMinWidth(55);
            memberFeejTable.getColumnModel().getColumn(2).setPreferredWidth(55);
            memberFeejTable.getColumnModel().getColumn(2).setMaxWidth(55);
            memberFeejTable.getColumnModel().getColumn(3).setMinWidth(52);
            memberFeejTable.getColumnModel().getColumn(3).setPreferredWidth(52);
            memberFeejTable.getColumnModel().getColumn(3).setMaxWidth(52);
            memberFeejTable.getColumnModel().getColumn(4).setMinWidth(52);
            memberFeejTable.getColumnModel().getColumn(4).setPreferredWidth(52);
            memberFeejTable.getColumnModel().getColumn(4).setMaxWidth(52);
            memberFeejTable.getColumnModel().getColumn(5).setMinWidth(52);
            memberFeejTable.getColumnModel().getColumn(5).setPreferredWidth(52);
            memberFeejTable.getColumnModel().getColumn(5).setMaxWidth(52);
            memberFeejTable.getColumnModel().getColumn(6).setMinWidth(52);
            memberFeejTable.getColumnModel().getColumn(6).setPreferredWidth(52);
            memberFeejTable.getColumnModel().getColumn(6).setMaxWidth(52);
            memberFeejTable.getColumnModel().getColumn(7).setMinWidth(52);
            memberFeejTable.getColumnModel().getColumn(7).setPreferredWidth(52);
            memberFeejTable.getColumnModel().getColumn(7).setMaxWidth(52);
            memberFeejTable.getColumnModel().getColumn(8).setMinWidth(52);
            memberFeejTable.getColumnModel().getColumn(8).setPreferredWidth(52);
            memberFeejTable.getColumnModel().getColumn(8).setMaxWidth(52);
            memberFeejTable.getColumnModel().getColumn(9).setMinWidth(52);
            memberFeejTable.getColumnModel().getColumn(9).setPreferredWidth(52);
            memberFeejTable.getColumnModel().getColumn(9).setMaxWidth(52);
            memberFeejTable.getColumnModel().getColumn(10).setMinWidth(52);
            memberFeejTable.getColumnModel().getColumn(10).setPreferredWidth(52);
            memberFeejTable.getColumnModel().getColumn(10).setMaxWidth(52);
            memberFeejTable.getColumnModel().getColumn(11).setMinWidth(52);
            memberFeejTable.getColumnModel().getColumn(11).setPreferredWidth(52);
            memberFeejTable.getColumnModel().getColumn(11).setMaxWidth(52);
            memberFeejTable.getColumnModel().getColumn(12).setMinWidth(52);
            memberFeejTable.getColumnModel().getColumn(12).setPreferredWidth(52);
            memberFeejTable.getColumnModel().getColumn(12).setMaxWidth(52);
            memberFeejTable.getColumnModel().getColumn(13).setMinWidth(52);
            memberFeejTable.getColumnModel().getColumn(13).setPreferredWidth(52);
            memberFeejTable.getColumnModel().getColumn(13).setMaxWidth(52);
            memberFeejTable.getColumnModel().getColumn(14).setMinWidth(52);
            memberFeejTable.getColumnModel().getColumn(14).setPreferredWidth(52);
            memberFeejTable.getColumnModel().getColumn(14).setMaxWidth(52);
        }

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 153, 51));
        jLabel1.setText("Member's Fee");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(340, 340, 340)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        search.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        search.setForeground(new java.awt.Color(0, 153, 51));
        search.setText("search : ");

        memberjComboBox1.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        memberjComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        memberjComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                memberjComboBox1ActionPerformed(evt);
            }
        });

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel28.setText("Name : ");

        yearjComboBox1.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        yearjComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yearjComboBox1ActionPerformed(evt);
            }
        });

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel29.setText("Year : ");

        searchjButton.setText("SEARCH");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(search)
                .addGap(38, 38, 38)
                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(memberjComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel29)
                .addGap(18, 18, 18)
                .addComponent(yearjComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(81, 81, 81)
                .addComponent(searchjButton, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(memberjComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(yearjComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchjButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );

        jPanel11.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 0, 204), 2, true));

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel24.setText("Name : ");

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel25.setText("Month : ");

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel26.setText("Amount : ");

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel27.setText("Year : ");

        monthjComboBox.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        monthjComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                monthjComboBoxActionPerformed(evt);
            }
        });

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

        yearjComboBox.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        yearjComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yearjComboBoxActionPerformed(evt);
            }
        });

        memberjComboBox.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        memberjComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        memberjComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                memberjComboBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(memberjComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                        .addComponent(jLabel25)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(monthjComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel27)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(yearjComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(jLabel26)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(amountJTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(addFeejButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(upDatejButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(deletejButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(325, 325, 325))))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(memberjComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(monthjComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(yearjComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(amountJTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addFeejButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deletejButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(upDatejButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        accountsInfojMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        accountsInfojMenuItem.setText("accounts info.");
        accountsInformationjMenu.add(accountsInfojMenuItem);

        memberfeejCheckBoxMenuItem.setForeground(new java.awt.Color(0, 153, 51));
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
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

    private void memberFeejTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_memberFeejTableMouseClicked
        // TODO add your handling code here:
        int rowNumber = memberFeejTable.getSelectedRow();
        TableModel model = memberFeejTable.getModel();
        edit_exe_member = true;
        exe_current_name = model.getValueAt(rowNumber, 1).toString();

        isMember = true;
        try {
            this.showComboBox(memberjComboBox);
        } catch (SQLException ex) {
            Logger.getLogger(MembersFee.class.getName()).log(Level.SEVERE, null, ex);
        }
        isMember = false;

        edit_year = true;
        current_year = model.getValueAt(rowNumber, 2).toString();
        isYear = true;
        try {
            this.showComboBox(yearjComboBox);
        } catch (SQLException ex) {
            Logger.getLogger(MembersFee.class.getName()).log(Level.SEVERE, null, ex);
        }
        isYear = false;

        amountJTextField.setText("");
        current_efId = Integer.parseInt(model.getValueAt(rowNumber, 0).toString());
    }//GEN-LAST:event_memberFeejTableMouseClicked

    private void upDatejButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_upDatejButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_upDatejButtonActionPerformed

    private void monthjComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_monthjComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_monthjComboBoxActionPerformed

    private void yearjComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yearjComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_yearjComboBoxActionPerformed

    private void memberjComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_memberjComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_memberjComboBoxActionPerformed

    private void memberjComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_memberjComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_memberjComboBox1ActionPerformed

    private void yearjComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yearjComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_yearjComboBox1ActionPerformed

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
            java.util.logging.Logger.getLogger(MembersFee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MembersFee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MembersFee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MembersFee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new MembersFee(1).setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(MembersFee.class.getName()).log(Level.SEVERE, null, ex);
                }
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
    private javax.swing.JTextField amountJTextField;
    private javax.swing.JButton deletejButton;
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
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable memberFeejTable;
    private javax.swing.JMenuItem memberfeejCheckBoxMenuItem;
    private javax.swing.JComboBox memberjComboBox;
    private javax.swing.JComboBox memberjComboBox1;
    private javax.swing.JComboBox monthjComboBox;
    private javax.swing.JMenuItem previousMessagejMenuItem;
    private javax.swing.JLabel search;
    private javax.swing.JButton searchjButton;
    private javax.swing.JMenuItem sendMessagejMenuItem;
    private javax.swing.JButton upDatejButton;
    private javax.swing.JComboBox yearjComboBox;
    private javax.swing.JComboBox yearjComboBox1;
    // End of variables declaration//GEN-END:variables
}
