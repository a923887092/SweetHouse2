package com.gwm.sweethouse.bean;

/**
 * Created by Administrator on 2015/10/25.
 */
public class Address {
    int user_id;
    int add_id;
    String add_name;
    String add_address;
    String add_phone;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getAdd_address() {
        return add_address;
    }

    public void setAdd_address(String add_address) {
        this.add_address = add_address;
    }

    public String getAdd_name() {
        return add_name;
    }

    public void setAdd_name(String add_name) {
        this.add_name = add_name;
    }

    public int getAdd_id() {
        return add_id;
    }

    public void setAdd_id(int add_id) {
        this.add_id = add_id;
    }

    public String getAdd_phone() {
        return add_phone;
    }

    public void setAdd_phone(String add_phone) {
        this.add_phone = add_phone;
    }

    public Address() {
    }

    public Address(String add_address, int add_id, String add_name, String add_phone, int user_id) {
        this.add_address = add_address;
        this.add_id = add_id;
        this.add_name = add_name;
        this.add_phone = add_phone;
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "Address{" +
                "add_address='" + add_address + '\'' +
                ", user_id=" + user_id +
                ", add_id=" + add_id +
                ", add_name='" + add_name + '\'' +
                ", add_phone='" + add_phone + '\'' +
                '}';
    }
}
