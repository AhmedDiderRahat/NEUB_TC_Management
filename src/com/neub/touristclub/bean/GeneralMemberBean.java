package com.neub.touristclub.bean;

/**
 *
 * @author Team-R&D_v..3
 */

public class GeneralMemberBean 
{
    private String gm_id, student_id, name, dept, session, mobile, add_by;

    public GeneralMemberBean() 
    {
        gm_id = null;
        student_id = null;
        name = null;
        dept = null;
        session = null;
        mobile = null;
        add_by = null;    
    }

    public String getGm_id() {
        return gm_id;
    }

    public void setGm_id(String gm_id) {
        this.gm_id = gm_id;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAdd_by() {
        return add_by;
    }

    public void setAdd_by(String add_by) {
        this.add_by = add_by;
    }
       
}
