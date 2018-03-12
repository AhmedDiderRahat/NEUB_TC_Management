package com.neub.touristclub.bean;

/**
 *
 * @author Team : R_&_D_v.3
 */

public class StudentInfoBean {

    private String admin_id;
    private String username;
    private String password;
    private  String search_key;
    private String admin_type;

    public StudentInfoBean(String admin_id, String username, String admmin_type, String password) 
    {
        this.admin_id = admin_id;
        this.username = username;
        this.admin_type = admmin_type;
        this.password = password;
    }

    public StudentInfoBean() {
        this.admin_id = null;
        this.username = null;
        this.admin_type = null;
        this.password = null;
        this.search_key = null;
    }

    public String getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(String admin_id) {
        this.admin_id = admin_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getAdmin_type() {
        return admin_type;
    }
    
    public void setAdmin_type(String admin_type)
    {
        this.admin_type = admin_type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getSearch_key() {
        return search_key;
    }

    public void setSearch_key(String search_key) {
        this.search_key = search_key;
    }   
}
