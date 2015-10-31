package com.gwm.sweethouse.bean;

/**
 * Created by Administrator on 2015/10/27.
 */
public class AdressBean {
    int user_id;
    String add_name;
    String add_address;
    String add_phone;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getadd_name() {
        return add_name;
    }

    public void setadd_name(String add_name) {
        this.add_name = add_name;
    }

    public String getAdd_address() {
        return add_address;
    }

    public void setAdd_address(String add_address) {
        this.add_address = add_address;
    }

    public String getAdd_phone() {
        return add_phone;
    }

    public void setAdd_phone(String add_phone) {
        this.add_phone = add_phone;
    }

    public AdressBean() {
    }

    public AdressBean(String add_phone, String add_address, String add_name) {
        this.add_phone = add_phone;
        this.add_address = add_address;
        this.add_name = add_name;

    }
}
