package com.gwm.sweethouse.bean;

import java.util.Date;

/**
 * Created by Administrator on 2015/11/6.
 */
public class Bill {
    private int bill_id;
    private String bill_content;
    private float bill_money;
    private Date bill_time;
    private int bill_state;
    private int user_id;

    public int getBill_id() {
        return bill_id;
    }
    public void setBill_id(int billId) {
        bill_id = billId;
    }
    public String getBill_content() {
        return bill_content;
    }
    public void setBill_content(String billContent) {
        bill_content = billContent;
    }
    public float getBill_money() {
        return bill_money;
    }
    public void setBill_money(float billMoney) {
        bill_money = billMoney;
    }
    public Date getBill_time() {
        return bill_time;
    }
    public void setBill_time(Date billTime) {
        bill_time = billTime;
    }
    public int getBill_state() {
        return bill_state;
    }
    public void setBill_state(int billState) {
        bill_state = billState;
    }
    public int getUser_id() {
        return user_id;
    }
    public void setUser_id(int userId) {
        user_id = userId;
    }
    public Bill() {
        super();
        // TODO Auto-generated constructor stub
    }
    public Bill(int billId, String billContent, float billMoney, Date billTime,
                int billState, int userId) {
        super();
        bill_id = billId;
        bill_content = billContent;
        bill_money = billMoney;
        bill_time = billTime;
        bill_state = billState;
        user_id = userId;
    }
    @Override
    public String toString() {
        return "Bill [bill_content=" + bill_content + ", bill_id=" + bill_id
                + ", bill_money=" + bill_money + ", bill_state=" + bill_state
                + ", bill_time=" + bill_time + ", user_id=" + user_id + "]";
    }

}
