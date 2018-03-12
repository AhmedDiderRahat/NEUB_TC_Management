package com.neub.touristclub.bean;

/**
 *
 * @author Team : R_&_D_v.3
 */
public class SettingBean {

    private int admin_id;
    private String user_name;
    private String admin_type;
    private  String password;

    public SettingBean(int admin_id, String user_name, String admin_type, String password) 
    {
        this.admin_id = admin_id;
        this.user_name = user_name;
        this.admin_type = admin_type;
        this.password = password;
    }
   
    public SettingBean() 
    {
        this.admin_id = 0;
        this.user_name = null;
        this.admin_type = null;
        this.password = null;
    }

    public int getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(int admin_id) {
        this.admin_id = admin_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getAdmin_type() {
        return admin_type;
    }

    public void setAdmin_type(String admin_type) {
        this.admin_type = admin_type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }   
}
