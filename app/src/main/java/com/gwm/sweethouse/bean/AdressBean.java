package com.gwm.sweethouse.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/10/27.
 */
public class AdressBean implements Serializable{
    int user_id;
    String add_name;
    String add_address;
    String add_phone;
    int add_id;
    int add_state;

    public String getAdd_name() {
        return add_name;
    }

    public void setAdd_name(String add_name) {
        this.add_name = add_name;
    }

    public int getAdd_state() {
        return add_state;
    }

    public void setAdd_state(int add_state) {
        this.add_state = add_state;
    }



    public int getAdd_id() {
        return add_id;
    }

    public void setAdd_id(int add_id) {
        this.add_id = add_id;
    }



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


    public AdressBean(String add_phone, String add_address, String add_name,int add_id,int add_state) {
        super();
        this.add_phone = add_phone;
        this.add_address = add_address;
        this.add_name = add_name;
        this.add_id=add_id;
        this.add_state=add_state;
    }

    @Override
    public String toString() {
        return "AdressBean{" +
                ", add_name='" + add_name + '\'' +
                ", add_address='" + add_address + '\'' +
                ", add_phone='" + add_phone + '\'' +
                ", add_id=" + add_id +
                ", add_state=" + add_state +
                '}';
    }

}
