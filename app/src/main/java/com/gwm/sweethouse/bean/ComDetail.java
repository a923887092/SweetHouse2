package com.gwm.sweethouse.bean;


public class ComDetail {
    private int dt_id;
    private String dt_titlepic;
    private String dt_detail;
    private String dt_addr;
    private String dt_phone;
    public int getDt_id() {
        return dt_id;
    }
    public void setDt_id(int dt_id) {
        this.dt_id = dt_id;
    }
    public String getDt_titlepic() {
        return dt_titlepic;
    }
    public void setDt_titlepic(String dt_titlepic) {
        this.dt_titlepic = dt_titlepic;
    }
    public String getDt_detail() {
        return dt_detail;
    }
    public void setDt_detail(String dt_detail) {
        this.dt_detail = dt_detail;
    }
    public String getDt_addr() {
        return dt_addr;
    }
    public void setDt_addr(String dt_addr) {
        this.dt_addr = dt_addr;
    }
    public String getDt_phone() {
        return dt_phone;
    }
    public void setDt_phone(String dt_phone) {
        this.dt_phone = dt_phone;
    }
    public ComDetail(int dt_id, String dt_titlepic, String dt_detail,
                         String dt_addr, String dt_phone) {
        super();
        this.dt_id = dt_id;
        this.dt_titlepic = dt_titlepic;
        this.dt_detail = dt_detail;
        this.dt_addr = dt_addr;
        this.dt_phone = dt_phone;
    }
    public ComDetail() {
        super();
    }
    @Override
    public String toString() {
        return "CompanyDetail [dt_id=" + dt_id + ", dt_titlepic=" + dt_titlepic
                + ", dt_detail=" + dt_detail + ", dt_addr=" + dt_addr
                + ", dt_phone=" + dt_phone + "]";
    }

}
