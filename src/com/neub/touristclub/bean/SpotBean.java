package com.neub.touristclub.bean;

/**
 *
 * @author Team : R_&_D_v.3
 */

public class SpotBean {

    private int spot_id;
    private String spot_name;
    private String start_date;
    private  String end_date;
    private String transport;
    private int status;

    public SpotBean(int spot_id, String spot_name, String start_date, String end_date, String transport, int status) 
    {
        this.spot_id = spot_id;
        this.spot_name = spot_name;
        this.start_date = start_date;
        this.end_date = end_date;
        this.transport = transport;
        this.status = status;
    }

    public SpotBean(int spot_id, String spot_name, String start_date, String end_date, String transport) 
    {
        this.spot_id = spot_id;
        this.spot_name = spot_name;
        this.start_date = start_date;
        this.end_date = end_date;
        this.transport = transport;
    }
    
    public SpotBean() 
    {
        this.spot_id = 0;
        this.spot_name = null;
        this.start_date = null;
        this.end_date = null;
        this.transport = null;
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

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    


    
}
