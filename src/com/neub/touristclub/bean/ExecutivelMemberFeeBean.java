package com.neub.touristclub.bean;

/**
 *
 * @author Team-R&D_v.3
 */

public class ExecutivelMemberFeeBean 
{
    private String gm_id, name, month, year;
    int ef_id, amount;
    boolean jan, feb, mar, apr, may, jun, jul, aug, sep, oct, nov, dec;

    public ExecutivelMemberFeeBean() 
    {
        ef_id = 0;
        gm_id = null;
        name = null;
        month = null;
        year = null;
        amount = 0;
        jan = false;
        feb = false;
        mar = false;
        apr = false;
        may = false;
        jun = false;
        jul = false;
        aug = false;
        sep = false;
        oct = false;
        nov = false;
        dec = false;
    }
    
    public ExecutivelMemberFeeBean(int ef_id, String name, String year, 
            boolean jan, boolean feb, boolean mar, boolean apr, boolean may, 
            boolean jun, boolean jul, boolean aug, boolean sep, boolean oct, 
            boolean nov, boolean dec, int amount) {
        
            this.ef_id = ef_id;
            this.name = name;
            this.jan = jan;
            this.feb = feb;
            this.mar = mar;
            this.apr = apr;
            this.may = may;
            this.jun = jun;
            this.jul = jul;
            this.aug = aug;
            this.sep = sep;
            this.oct = oct;
            this.nov = nov;
            this.dec = dec;
            this.year = year;
            this.amount = amount;
    }

    public int getEf_id() {
        return ef_id;
    }

    public void setEf_id(int ef_id) {
        this.ef_id = ef_id;
    }

    public String getGm_id() {
        return gm_id;
    }

    public void setGm_id(String gm_id) {
        this.gm_id = gm_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean isJan() {
        return jan;
    }

    public void setJan(boolean jan) {
        this.jan = jan;
    }

    public boolean isFeb() {
        return feb;
    }

    public void setFeb(boolean feb) {
        this.feb = feb;
    }

    public boolean isMar() {
        return mar;
    }

    public void setMar(boolean mar) {
        this.mar = mar;
    }

    public boolean isApr() {
        return apr;
    }

    public void setApr(boolean apr) {
        this.apr = apr;
    }

    public boolean isMay() {
        return may;
    }

    public void setMay(boolean may) {
        this.may = may;
    }

    public boolean isJun() {
        return jun;
    }

    public void setJun(boolean jun) {
        this.jun = jun;
    }

    public boolean isJul() {
        return jul;
    }

    public void setJul(boolean jul) {
        this.jul = jul;
    }

    public boolean isAug() {
        return aug;
    }

    public void setAug(boolean aug) {
        this.aug = aug;
    }

    public boolean isSep() {
        return sep;
    }

    public void setSep(boolean sep) {
        this.sep = sep;
    }

    public boolean isOct() {
        return oct;
    }

    public void setOct(boolean oct) {
        this.oct = oct;
    }

    public boolean isNov() {
        return nov;
    }

    public void setNov(boolean nov) {
        this.nov = nov;
    }

    public boolean isDec() {
        return dec;
    }

    public void setDec(boolean dec) {
        this.dec = dec;
    }
    
    
    
    
}
