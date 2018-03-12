package com.neub.touristclub.mainadmin.events;

import com.neub.touristclub.bean.*;
import com.neub.touristclub.databaseconnection.DbConnector;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Team-R&D_v.3
 */

public class ViewInformation extends javax.swing.JFrame implements ActionListener {

    public static  int adminIdKey;
    public static String tmp_title, tmp_start_date, tmp_end_date, tmp_type, tmp_spot_id;
    public static int tmp_ev_id, tmp_total_capacity, tmp_fee;
    
    boolean monthList[] = new boolean[12];

    public ViewInformation(ArrayList<EventsBean>ebs, int key) {
        try {
            //System.out.println("My printer: " + ebs.get(0).getStatus());
            adminIdKey = key;
            initComponents();
            this.setVisible(true);
            this.setLocationRelativeTo(null);
            this.setResizable(false);
            this.setDefaultCloseOperation(HIDE_ON_CLOSE);
            
            tmp_ev_id = ebs.get(0).getEv_id();
            tmp_title = ebs.get(0).getTitle();
            tmp_spot_id = ebs.get(0).getSpot_id();
            tmp_start_date = ebs.get(0).getStart_date();
            tmp_end_date = ebs.get(0).getEnd_date();
            tmp_type = ebs.get(0).getType();
            tmp_total_capacity = ebs.get(0).getTotal_capacity();
            tmp_fee = ebs.get(0).getFee();
            
            
            initializeTheFrame();
            
            PrintToTableSpot();
            
            backjButton.addActionListener(this);

        } catch (SQLException ex) {
            Logger.getLogger(ViewInformation.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public ArrayList dataRetriveFromDatabaseForSpots() throws SQLException {

        DbConnector connectorShow = new DbConnector();
        ArrayList<SpotBean> listSpot;
        String selectFeeQuery = "SELECT * FROM spot WHERE status = '" +1+ "'";
        
        connectorShow.resultset = connectorShow.statement.executeQuery(selectFeeQuery);
        
        listSpot = new ArrayList<SpotBean>();
        while (connectorShow.resultset.next()) {
            int spot_id = connectorShow.resultset.getInt("spot_id");
            String spot_name = connectorShow.resultset.getString("spot_name");
            String start_date = connectorShow.resultset.getString("start_date");
            String end_date = connectorShow.resultset.getString("end_date");
            String transport = connectorShow.resultset.getString("transport");
            
            SpotBean bean = new SpotBean(spot_id, spot_name, start_date, end_date, transport);

            listSpot.add(bean);
        }
        connectorShow.connection.close();
        return listSpot;
    }

    
    public void PrintToTableSpot() throws SQLException {

        DefaultTableModel model = (DefaultTableModel) spot_jTable.getModel();
        ArrayList<SpotBean> listEventReceive = dataRetriveFromDatabaseForSpots();
        Object rawData[] = new Object[5];

        for (int i = 0; i < listEventReceive.size(); i++) {
            
            rawData[0] = listEventReceive.get(i).getSpot_id();
            rawData[1] = listEventReceive.get(i).getSpot_name();
            rawData[2] = listEventReceive.get(i).getStart_date();
            rawData[3] = listEventReceive.get(i).getEnd_date();
            rawData[4] = listEventReceive.get(i).getTransport();
            
            model.addRow(rawData);
        }
    }

    
    public void initializeTheFrame()
    {
        title_jLabel.setText("Tour Title : " + tmp_title);
        start_jLabel.setText("Start Date : " + tmp_start_date);
        end_jLabe.setText("End Date : " + tmp_end_date);
        capacity_jLabel.setText("Total Capacity : " + tmp_total_capacity);
        fee_jLabel.setText("Fee : " + tmp_fee);
        type_jLabel.setText("Type : " + tmp_type);
    }

    @Override
    public void actionPerformed(ActionEvent button) {
        if (button.getSource() == backjButton) {
            this.dispose();
        }
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        backjButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        start_jLabel = new javax.swing.JLabel();
        end_jLabe = new javax.swing.JLabel();
        title_jLabel = new javax.swing.JLabel();
        type_jLabel = new javax.swing.JLabel();
        capacity_jLabel = new javax.swing.JLabel();
        fee_jLabel = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        spot_jTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 153));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Search Result");

        backjButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/neub/touristclub/mainadmin/accounts/closeIcon.jpg"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(backjButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addGap(301, 301, 301))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(backjButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 0, 0), 2));

        start_jLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        start_jLabel.setText("sd");

        end_jLabe.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        end_jLabe.setText("ed");

        title_jLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        type_jLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        type_jLabel.setText("typ");

        capacity_jLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        capacity_jLabel.setText("cp");

        fee_jLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        fee_jLabel.setText("fee");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(start_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(end_jLabe, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(type_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 107, Short.MAX_VALUE)
                        .addComponent(capacity_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(69, 69, 69)
                        .addComponent(fee_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(title_jLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(title_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(start_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(end_jLabe, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fee_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(capacity_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(type_jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 0, 153), 2));

        spot_jTable.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        spot_jTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "sl", "spot name", "start date", "end date", "transport"
            }
        ));
        spot_jTable.setRowHeight(25);
        jScrollPane1.setViewportView(spot_jTable);
        if (spot_jTable.getColumnModel().getColumnCount() > 0) {
            spot_jTable.getColumnModel().getColumn(0).setMinWidth(40);
            spot_jTable.getColumnModel().getColumn(0).setPreferredWidth(40);
            spot_jTable.getColumnModel().getColumn(0).setMaxWidth(40);
            spot_jTable.getColumnModel().getColumn(2).setMinWidth(100);
            spot_jTable.getColumnModel().getColumn(2).setPreferredWidth(100);
            spot_jTable.getColumnModel().getColumn(2).setMaxWidth(100);
            spot_jTable.getColumnModel().getColumn(3).setMinWidth(100);
            spot_jTable.getColumnModel().getColumn(3).setPreferredWidth(100);
            spot_jTable.getColumnModel().getColumn(3).setMaxWidth(100);
            spot_jTable.getColumnModel().getColumn(4).setMinWidth(120);
            spot_jTable.getColumnModel().getColumn(4).setPreferredWidth(120);
            spot_jTable.getColumnModel().getColumn(4).setMaxWidth(120);
        }

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 776, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new SearchResult().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backjButton;
    private javax.swing.JLabel capacity_jLabel;
    private javax.swing.JLabel end_jLabe;
    private javax.swing.JLabel fee_jLabel;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable spot_jTable;
    private javax.swing.JLabel start_jLabel;
    private javax.swing.JLabel title_jLabel;
    private javax.swing.JLabel type_jLabel;
    // End of variables declaration//GEN-END:variables
}
