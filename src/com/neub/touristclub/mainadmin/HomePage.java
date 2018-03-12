package com.neub.touristclub.mainadmin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.neub.touristclub.mainadmin.members.GeneralMember;
import com.neub.touristclub.mainadmin.members.ExecutiveMember;
import com.neub.touristclub.mainadmin.accounts.AccountsInformation;
import com.neub.touristclub.mainadmin.accounts.MembersFee;
import com.neub.touristclub.mainadmin.events.EventsInformation;
import com.neub.touristclub.message.SendMessage;
import com.neub.touristclub.message.PreviousMessage;
import com.neub.touristclub.developer.Developer;
import com.neub.touristclub.mainadmin.adminsetting.Setting;
import com.neub.touristclub.mainadmin.AdminLogIn;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Team-R&D_v..3
 */
public class HomePage extends javax.swing.JFrame implements ActionListener{
    
    public static int adminIdKey;
    
    public HomePage(int idOfAdmin) 
    {
        adminIdKey = idOfAdmin;
        initComponents();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setResizable(false);    
        
        GeneralMemberjMenuItem.addActionListener(this);
        GeneralMemberjButton.addActionListener(this);
        ExecutiveMemberjButton.addActionListener(this);
        ExecutiveMemberjMenuItem.addActionListener(this);
        memberfeejCheckBoxMenuItem.addActionListener(this);
        AccountsInfojMenuItem.addActionListener(this);
        AccountsjButton.addActionListener(this);
        EventsjButton.addActionListener(this);
        EventsjMenuItem.addActionListener(this);
        SendMessagejMenuItem.addActionListener(this);
        messagejButton.addActionListener(this);
        PreviousMessagejMenuItem.addActionListener(this);
        DeveloperjMenuItem.addActionListener(this);
        developerjbutton.addActionListener(this);
        AdminSettingjMenuItem.addActionListener(this);
        SettingjButton.addActionListener(this);
        ExitjMenuItem.addActionListener(this);
        logOut_jMenuItem.addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent click) 
    {
        if( (click.getSource() == GeneralMemberjMenuItem) || (click.getSource() == GeneralMemberjButton) )
        {
            try {
                GeneralMember generalMember = new GeneralMember(adminIdKey);
            } catch (SQLException ex) {
                Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.dispose();
        } 
        
        if( (click.getSource() == ExecutiveMemberjButton) || (click.getSource() == ExecutiveMemberjMenuItem) )
        {
            try {
                ExecutiveMember executiveMember = new ExecutiveMember(adminIdKey);
            } catch (Exception e) {
            }
            this.dispose();
        }
        
        if( (click.getSource() == AccountsInfojMenuItem) || (click.getSource() == AccountsjButton) )
        {
            try {
                AccountsInformation accountsInformation = new AccountsInformation(adminIdKey);
                this.dispose();
            } catch (Exception e) {
            }
        }
        
        if( click.getSource() == memberfeejCheckBoxMenuItem)
        {
            try {
                MembersFee fee = new MembersFee(adminIdKey);
                this.dispose();
            } catch (Exception e) {
            }
        }
        
        if( (click.getSource() == EventsjButton) || (click.getSource() == EventsjMenuItem) )
        {
            try {
                EventsInformation ei = new EventsInformation(adminIdKey);
                this.dispose();
            } catch (Exception e) {
            }
        }
        
        if( (click.getSource() == SendMessagejMenuItem) || (click.getSource() == messagejButton) )
        {
            try {
                SendMessage message = new SendMessage(adminIdKey);
                this.dispose();
            } catch (Exception e) {
            }
        }
        
        if( (click.getSource() == AdminSettingjMenuItem) || (click.getSource() == SettingjButton) )
        {
            try {
                Setting setting = new Setting(adminIdKey);
                this.dispose();
            } catch (Exception e) {
            }
        }
        
        if( (click.getSource() == DeveloperjMenuItem) || (click.getSource()== developerjbutton) )
        {
            try {
                Developer developer = new Developer(adminIdKey);
                this.dispose();
            } catch (Exception e) {
            }
        }
        if(click.getSource() == PreviousMessagejMenuItem)
        {
            try {
                PreviousMessage pm = new PreviousMessage(adminIdKey);
                this.dispose();
            } catch (Exception e) {
            }
        }
        
        if(click.getSource() == ExitjMenuItem)
        {
            try {
                this.dispose();
            } catch (Exception e) {
            }
        }
        
        if(click.getSource() == logOut_jMenuItem)
        {
            try {
                AdminLogIn adminLogIn = new AdminLogIn();
                this.dispose();
            } catch (Exception e) {
            }
        }
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        imagePanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        GeneralMemberjButton = new javax.swing.JButton();
        ExecutiveMemberjButton = new javax.swing.JButton();
        AccountsjButton = new javax.swing.JButton();
        EventsjButton = new javax.swing.JButton();
        messagejButton = new javax.swing.JButton();
        SettingjButton = new javax.swing.JButton();
        developerjbutton = new javax.swing.JButton();
        HomePagejMenuBar = new javax.swing.JMenuBar();
        HomejMenu = new javax.swing.JMenu();
        HomejMenuItem = new javax.swing.JMenuItem();
        ExecutiveMemberjMenu = new javax.swing.JMenu();
        ExecutiveMemberjMenuItem = new javax.swing.JMenuItem();
        GeneralMemberjMenuItem = new javax.swing.JMenuItem();
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
        logOut_jMenuItem = new javax.swing.JMenuItem();

        jCheckBoxMenuItem1.setSelected(true);
        jCheckBoxMenuItem1.setText("jCheckBoxMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 0, 204));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("Welcome To North East University Bangladesh Tourist Club ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        imagePanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 51), 2));
        imagePanel.setLayout(null);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/neub/touristclub/mainadmin/clubMembers.jpg"))); // NOI18N
        jLabel1.setPreferredSize(new java.awt.Dimension(820, 460));
        imagePanel.add(jLabel1);
        jLabel1.setBounds(10, 10, 790, 430);

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 51), 2));

        GeneralMemberjButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        GeneralMemberjButton.setText("General Members");

        ExecutiveMemberjButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        ExecutiveMemberjButton.setText("Executive Members");

        AccountsjButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AccountsjButton.setText("Accounts");

        EventsjButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        EventsjButton.setText("Events");
        EventsjButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EventsjButtonActionPerformed(evt);
            }
        });

        messagejButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        messagejButton.setText("Send Message");

        SettingjButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SettingjButton.setText("Setting");
        SettingjButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SettingjButtonActionPerformed(evt);
            }
        });

        developerjbutton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        developerjbutton.setText("Developer");
        developerjbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                developerjbuttonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(GeneralMemberjButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ExecutiveMemberjButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(AccountsjButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(EventsjButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SettingjButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addComponent(developerjbutton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(messagejButton)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(GeneralMemberjButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ExecutiveMemberjButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AccountsjButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EventsjButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(messagejButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SettingjButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(developerjbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        HomejMenu.setForeground(new java.awt.Color(0, 153, 51));
        HomejMenu.setText("Home");
        HomejMenu.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N

        HomejMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.CTRL_MASK));
        HomejMenuItem.setText("Home");
        HomejMenu.add(HomejMenuItem);

        HomePagejMenuBar.add(HomejMenu);

        ExecutiveMemberjMenu.setText(" Member");
        ExecutiveMemberjMenu.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N

        ExecutiveMemberjMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.ALT_MASK));
        ExecutiveMemberjMenuItem.setText("Executive Member");
        ExecutiveMemberjMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExecutiveMemberjMenuItemActionPerformed(evt);
            }
        });
        ExecutiveMemberjMenu.add(ExecutiveMemberjMenuItem);

        GeneralMemberjMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.CTRL_MASK));
        GeneralMemberjMenuItem.setText("General Member");
        GeneralMemberjMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GeneralMemberjMenuItemActionPerformed(evt);
            }
        });
        ExecutiveMemberjMenu.add(GeneralMemberjMenuItem);

        HomePagejMenuBar.add(ExecutiveMemberjMenu);

        accountsInformationjMenu.setText("Accounts");
        accountsInformationjMenu.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N

        AccountsInfojMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        AccountsInfojMenuItem.setText("accounts info.");
        accountsInformationjMenu.add(AccountsInfojMenuItem);

        memberfeejCheckBoxMenuItem.setText("member fees");
        accountsInformationjMenu.add(memberfeejCheckBoxMenuItem);

        HomePagejMenuBar.add(accountsInformationjMenu);

        eventsjMenu.setText("Events");
        eventsjMenu.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N

        EventsjMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        EventsjMenuItem.setText("events");
        eventsjMenu.add(EventsjMenuItem);

        HomePagejMenuBar.add(eventsjMenu);

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

        HomePagejMenuBar.add(endMessagejMenu);

        SettingjMenu.setText("Setting");
        SettingjMenu.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N

        AdminSettingjMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        AdminSettingjMenuItem.setText("admin setting");
        SettingjMenu.add(AdminSettingjMenuItem);

        HomePagejMenuBar.add(SettingjMenu);

        DeveloperjMenu.setText("Developer");
        DeveloperjMenu.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N

        DeveloperjMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_MASK));
        DeveloperjMenuItem.setText("information");
        DeveloperjMenu.add(DeveloperjMenuItem);

        HomePagejMenuBar.add(DeveloperjMenu);

        exitsjMenu.setText("Exit");
        exitsjMenu.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N

        ExitjMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        ExitjMenuItem.setText("exit");
        exitsjMenu.add(ExitjMenuItem);

        logOut_jMenuItem.setText("logout");
        exitsjMenu.add(logOut_jMenuItem);

        HomePagejMenuBar.add(exitsjMenu);

        setJMenuBar(HomePagejMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(92, 92, 92)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(imagePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(43, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(imagePanel, javax.swing.GroupLayout.DEFAULT_SIZE, 448, Short.MAX_VALUE)
                .addGap(23, 23, 23)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void GeneralMemberjMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GeneralMemberjMenuItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_GeneralMemberjMenuItemActionPerformed

    private void ExecutiveMemberjMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExecutiveMemberjMenuItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ExecutiveMemberjMenuItemActionPerformed

    private void PreviousMessagejMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PreviousMessagejMenuItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PreviousMessagejMenuItemActionPerformed

    private void SettingjButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SettingjButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SettingjButtonActionPerformed

    private void EventsjButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EventsjButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EventsjButtonActionPerformed

    private void developerjbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_developerjbuttonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_developerjbuttonActionPerformed

    public static void main(String args[]) 
    {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HomePage(1).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem AccountsInfojMenuItem;
    private javax.swing.JButton AccountsjButton;
    private javax.swing.JMenuItem AdminSettingjMenuItem;
    private javax.swing.JMenu DeveloperjMenu;
    private javax.swing.JMenuItem DeveloperjMenuItem;
    private javax.swing.JButton EventsjButton;
    private javax.swing.JMenuItem EventsjMenuItem;
    private javax.swing.JButton ExecutiveMemberjButton;
    private javax.swing.JMenu ExecutiveMemberjMenu;
    private javax.swing.JMenuItem ExecutiveMemberjMenuItem;
    private javax.swing.JMenuItem ExitjMenuItem;
    private javax.swing.JButton GeneralMemberjButton;
    private javax.swing.JMenuItem GeneralMemberjMenuItem;
    private javax.swing.JMenuBar HomePagejMenuBar;
    private javax.swing.JMenu HomejMenu;
    private javax.swing.JMenuItem HomejMenuItem;
    private javax.swing.JMenuItem PreviousMessagejMenuItem;
    private javax.swing.JMenuItem SendMessagejMenuItem;
    private javax.swing.JButton SettingjButton;
    private javax.swing.JMenu SettingjMenu;
    private javax.swing.JMenu accountsInformationjMenu;
    private javax.swing.JButton developerjbutton;
    private javax.swing.JMenu endMessagejMenu;
    private javax.swing.JMenu eventsjMenu;
    private javax.swing.JMenu exitsjMenu;
    private javax.swing.JPanel imagePanel;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JMenuItem logOut_jMenuItem;
    private javax.swing.JMenuItem memberfeejCheckBoxMenuItem;
    private javax.swing.JButton messagejButton;
    // End of variables declaration//GEN-END:variables
}
