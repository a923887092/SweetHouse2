package com.gwm.sweethouse.bean;

public class BeautifulPic {
    private int bea_id;
    private String bea_pic;
    private String bea_detail;
    private String bea_space;
    private String bea_style;
    private String bea_local;
    private String bea_color;
    public int getBea_id() {
        return bea_id;
    }
    public void setBea_id(int bea_id) {
        this.bea_id = bea_id;
    }
    public String getBea_pic() {
        return bea_pic;
    }
    public void setBea_pic(String bea_pic) {
        this.bea_pic = bea_pic;
    }
    public String getBea_detail() {
        return bea_detail;
    }
    public void setBea_detail(String bea_detail) {
        this.bea_detail = bea_detail;
    }
    public String getBea_space() {
        return bea_space;
    }
    public void setBea_space(String bea_space) {
        this.bea_space = bea_space;
    }
    public String getBea_style() {
        return bea_style;
    }
    public void setBea_style(String bea_style) {
        this.bea_style = bea_style;
    }
    public String getBea_local() {
        return bea_local;
    }
    public void setBea_local(String bea_local) {
        this.bea_local = bea_local;
    }
    public String getBea_color() {
        return bea_color;
    }
    public void setBea_color(String bea_color) {
        this.bea_color = bea_color;
    }
    public BeautifulPic(int bea_id, String bea_pic, String bea_detail,
                        String bea_space, String bea_style, String bea_local,
                        String bea_color) {
        super();
        this.bea_id = bea_id;
        this.bea_pic = bea_pic;
        this.bea_detail = bea_detail;
        this.bea_space = bea_space;
        this.bea_style = bea_style;
        this.bea_local = bea_local;
        this.bea_color = bea_color;
    }
    public BeautifulPic() {
        super();
    }
    @Override
    public String toString() {
        return "BeautifulPic [bea_id=" + bea_id + ", bea_pic=" + bea_pic
                + ", bea_detail=" + bea_detail + ", bea_space=" + bea_space
                + ", bea_style=" + bea_style + ", bea_local=" + bea_local
                + ", bea_color=" + bea_color + "]";
    }


}
