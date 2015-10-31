package com.gwm.sweethouse.bean;

public class CompanyPic {
    private int com_picid;
    private int com_id;
    private String com_pic;
    private String com_style;
    private String com_roomnum;
    private String com_despic;
    private String com_desname;
    public int getCom_picid() {
        return com_picid;
    }
    public void setCom_picid(int com_picid) {
        this.com_picid = com_picid;
    }
    public int getCom_id() {
        return com_id;
    }
    public void setCom_id(int com_id) {
        this.com_id = com_id;
    }
    public String getCom_pic() {
        return com_pic;
    }
    public void setCom_pic(String com_pic) {
        this.com_pic = com_pic;
    }
    public String getCom_style() {
        return com_style;
    }
    public void setCom_style(String com_style) {
        this.com_style = com_style;
    }
    public String getCom_roomnum() {
        return com_roomnum;
    }
    public void setCom_roomnum(String com_roomnum) {
        this.com_roomnum = com_roomnum;
    }
    public String getCom_despic() {
        return com_despic;
    }
    public void setCom_despic(String com_despic) {
        this.com_despic = com_despic;
    }
    public String getCom_desname() {
        return com_desname;
    }
    public void setCom_desname(String com_desname) {
        this.com_desname = com_desname;
    }
    public CompanyPic(int com_picid, int com_id, String com_pic,
                       String com_style, String com_roomnum, String com_despic,
                       String com_desname) {
        super();
        this.com_picid = com_picid;
        this.com_id = com_id;
        this.com_pic = com_pic;
        this.com_style = com_style;
        this.com_roomnum = com_roomnum;
        this.com_despic = com_despic;
        this.com_desname = com_desname;
    }
    public CompanyPic() {
        super();
    }
    @Override
    public String toString() {
        return "CompanyaPic [com_picid=" + com_picid + ", com_id=" + com_id
                + ", com_pic=" + com_pic + ", com_style=" + com_style
                + ", com_roomnum=" + com_roomnum + ", com_despic=" + com_despic
                + ", com_desname=" + com_desname + "]";
    }


}
