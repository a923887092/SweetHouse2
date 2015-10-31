package com.gwm.sweethouse.bean;

public class DesOrder1 {
    private int ord_id;
    private String ord_tel;
    private String ord_addr;
    private String ord_userName;
    private int com_id;
    private String ord_orderTime;
    public int getOrd_id() {
        return ord_id;
    }
    public void setOrd_id(int ord_id) {
        this.ord_id = ord_id;
    }
    public String getOrd_tel() {
        return ord_tel;
    }
    public void setOrd_tel(String ord_tel) {
        this.ord_tel = ord_tel;
    }
    public String getOrd_addr() {
        return ord_addr;
    }
    public void setOrd_addr(String ord_addr) {
        this.ord_addr = ord_addr;
    }
    public String getOrd_userName() {
        return ord_userName;
    }
    public void setOrd_userName(String ord_userName) {
        this.ord_userName = ord_userName;
    }
    public int getCom_id() {
        return com_id;
    }
    public void setCom_id(int comId) {
        this.com_id = comId;
    }
    public String getOrd_orderTime() {
        return ord_orderTime;
    }
    public void setOrd_orderTime(String ord_orderTime) {
        this.ord_orderTime = ord_orderTime;
    }
    public DesOrder1(int ord_id, String ord_tel, String ord_addr,
                    String ord_userName, int com_id, String ord_orderTime) {
        super();
        this.ord_id = ord_id;
        this.ord_tel = ord_tel;
        this.ord_addr = ord_addr;
        this.ord_userName = ord_userName;
        this.com_id = com_id;
        this.ord_orderTime = ord_orderTime;
    }
    public DesOrder1() {
        super();
    }
    @Override
    public String toString() {
        return "DesOrder [ord_id=" + ord_id + ", ord_tel=" + ord_tel
                + ", ord_addr=" + ord_addr + ", ord_userName=" + ord_userName
                + ", com_id=" + com_id + ", ord_orderTime=" + ord_orderTime
                + "]";
    }

}
