package com.neub.touristclub.bean;

/**
 *
 * @author Team : R_&_D_v.3
 */

public class RegistrationBean {

    private int reg_id;
    private int gm_id;
    private int ev_id;
    private int payment;
    private int admin_id;
    private int guset;
    
    public RegistrationBean(int reg_id, int gm_id, int ev_id, int payment, int guset, int admin_id) 
    {
        this.reg_id = reg_id;
        this.gm_id = gm_id;
        this.ev_id = ev_id;
        this.payment = payment;
        this.guset = guset;
        this.admin_id = admin_id;
    }
    
    public RegistrationBean() 
    {
        this.reg_id = 0;
        this.gm_id = 0;
        this.ev_id = 0;
        this.payment = 0;
        this.admin_id = 0;
    }

    public int getReg_id() {
        return reg_id;
    }

    public void setReg_id(int reg_id) {
        this.reg_id = reg_id;
    }

    public int getGm_id() {
        return gm_id;
    }

    public void setGm_id(int gm_id) {
        this.gm_id = gm_id;
    }

    public int getEv_id() {
        return ev_id;
    }

    public void setEv_id(int ev_id) {
        this.ev_id = ev_id;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public int getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(int admin_id) {
        this.admin_id = admin_id;
    }

    public int getGuset() {
        return guset;
    }

    public void setGuset(int guset) {
        this.guset = guset;
    }
    
}
