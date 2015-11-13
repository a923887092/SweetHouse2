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
    int add_state;
    public int getUser_id() {
        return user_id;
    }
    public void setUser_id(int userId) {
        user_id = userId;
    }
    public int getAdd_id() {
        return add_id;
    }
    public void setAdd_id(int addId) {
        add_id = addId;
    }
    public String getAdd_name() {
        return add_name;
    }
    public void setAdd_name(String addName) {
        add_name = addName;
    }
    public String getAdd_address() {
        return add_address;
    }
    public void setAdd_address(String addAddress) {
        add_address = addAddress;
    }
    public String getAdd_phone() {
        return add_phone;
    }
    public void setAdd_phone(String addPhone) {
        add_phone = addPhone;
    }
    public int getAdd_state() {
        return add_state;
    }
    public void setAdd_state(int addState) {
        add_state = addState;
    }
    public Address() {
        super();
        // TODO Auto-generated constructor stub
    }
    public Address(int userId, int addId, String addName,
                   String addAddress, String addPhone, int addState) {
        super();
        user_id = userId;
        add_id = addId;
        add_name = addName;
        add_address = addAddress;
        add_phone = addPhone;
        add_state = addState;
    }
    @Override
    public String toString() {
        return "Address [add_address=" + add_address + ", add_id=" + add_id
                + ", add_name=" + add_name + ", add_phone=" + add_phone
                + ", add_state=" + add_state + ", user_id=" + user_id + "]";
    }
}
