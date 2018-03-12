package com.neub.touristclub.bean;

/**
 *
 * @author Team : R_&_D_v.3
 */

public class AccountInfoBean {

    private int account_id;
    private String date;
    private String reason;
    private  String type;
    private double amount;

    public AccountInfoBean(int account_id, String date, String reason, String type, double amount) 
    {
        this.account_id = account_id;
        this.date = date;
        this.reason = reason;
        this.type = type;
        this.amount = amount;
    }
   
    public AccountInfoBean() 
    {
        account_id = 0;
        date = null;
        reason = null;
        type = null;
        amount = 0.0;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    
}
