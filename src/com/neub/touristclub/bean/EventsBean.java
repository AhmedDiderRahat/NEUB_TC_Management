package com.neub.touristclub.bean;

/**
 *
 * @author Team : R_&_D_v.3
 */

public class EventsBean {

    private int ev_id;
    private String title;
    private String start_date;
    private  String end_date;
    private String type;
    private int total_capacity;
    private int fee;
    private String spot_id;
    private int status;
    private int guest;
    
    public EventsBean(int ev_id, int total_capacity, String title, String type,  String spot_id, int guest, int fee) 
    {
        this.ev_id = ev_id;
        this.total_capacity = total_capacity;
        this.title = title;
        this.type = type;
        this.spot_id = spot_id;
        this.guest = guest;
        this.fee = fee;
    }
    
    public EventsBean(int ev_id, String title, String start_date, String end_date, String type, int total_capacity, int fee, String spot_id, int status) 
    {
        this.ev_id = ev_id;
        this.title = title;
        this.start_date = start_date;
        this.end_date = end_date;
        this.type = type;
        this.total_capacity = total_capacity;
        this.fee = fee;
        this.spot_id = spot_id;
        this.status = status;
    }
    
    public EventsBean() 
    {
        this.ev_id = 0;
        this.title = null;
        this.start_date = null;
        this.end_date = null;
        this.type = null;
        this.total_capacity = 0;
        this.fee = 0;
        this.spot_id = null;
        this.status = 0;
    }


    public int getEv_id() {
        return ev_id;
    }

    public void setEv_id(int ev_id) {
        this.ev_id = ev_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTotal_capacity() {
        return total_capacity;
    }

    public void setTotal_capacity(int total_capacity) {
        this.total_capacity = total_capacity;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public String getSpot_id() {
        return spot_id;
    }

    public void setSpot_id(String spot_id) {
        this.spot_id = spot_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }   

    public int getGuest() {
        return guest;
    }

    public void setGuest(int guest) {
        this.guest = guest;
    }
    
}
