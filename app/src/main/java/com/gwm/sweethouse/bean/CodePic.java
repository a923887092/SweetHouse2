package com.gwm.sweethouse.bean;

public class CodePic {
    private int mpic_id;
    private String mpic_house;
    private String mpic_style;
    private String mpic_space;
    private String mpic_local;
    private String mpic_detail;
    private String mpic_pic;
    private String mpic_num;
    public int getMpic_id() {
        return mpic_id;
    }
    public void setMpic_id(int mpic_id) {
        this.mpic_id = mpic_id;
    }
    public String getMpic_house() {
        return mpic_house;
    }
    public void setMpic_house(String mpic_house) {
        this.mpic_house = mpic_house;
    }
    public String getMpic_style() {
        return mpic_style;
    }
    public void setMpic_style(String mpic_style) {
        this.mpic_style = mpic_style;
    }
    public String getMpic_space() {
        return mpic_space;
    }
    public void setMpic_space(String mpic_space) {
        this.mpic_space = mpic_space;
    }
    public String getMpic_local() {
        return mpic_local;
    }
    public void setMpic_local(String mpic_local) {
        this.mpic_local = mpic_local;
    }
    public String getMpic_detail() {
        return mpic_detail;
    }
    public void setMpic_detail(String mpic_detail) {
        this.mpic_detail = mpic_detail;
    }
    public String getMpic_pic() {
        return mpic_pic;
    }
    public void setMpic_pic(String mpic_pic) {
        this.mpic_pic = mpic_pic;
    }
    public String getMpic_num() {
        return mpic_num;
    }
    public void setMpic_num(String mpic_num) {
        this.mpic_num = mpic_num;
    }
    public CodePic(int mpic_id, String mpic_house, String mpic_style,
                   String mpic_space, String mpic_local, String mpic_detail,
                   String mpic_pic, String mpic_num) {
        super();
        this.mpic_id = mpic_id;
        this.mpic_house = mpic_house;
        this.mpic_style = mpic_style;
        this.mpic_space = mpic_space;
        this.mpic_local = mpic_local;
        this.mpic_detail = mpic_detail;
        this.mpic_pic = mpic_pic;
        this.mpic_num = mpic_num;
    }
    public CodePic() {
        super();
    }
    @Override
    public String toString() {
        return "CodePic [mpic_id=" + mpic_id + ", mpic_house=" + mpic_house
                + ", mpic_style=" + mpic_style + ", mpic_space=" + mpic_space
                + ", mpic_local=" + mpic_local + ", mpic_detail=" + mpic_detail
                + ", mpic_pic=" + mpic_pic + ", mpic_num=" + mpic_num + "]";
    }


}

