package com.gwm.sweethouse.bean;

public class CodePicDetail {
    private int cod_id;
    private int cod_code;
    private String cod_pic;
    private String cod_detail;
    public int getCod_id() {
        return cod_id;
    }
    public void setCod_id(int cod_id) {
        this.cod_id = cod_id;
    }
    public int getCod_code() {
        return cod_code;
    }
    public void setCod_code(int cod_code) {
        this.cod_code = cod_code;
    }
    public String getCod_pic() {
        return cod_pic;
    }
    public void setCod_pic(String cod_pic) {
        this.cod_pic = cod_pic;
    }
    public String getCod_detail() {
        return cod_detail;
    }
    public void setCod_detail(String cod_detail) {
        this.cod_detail = cod_detail;
    }
    public CodePicDetail(int cod_id, int cod_code, String cod_pic,
                         String cod_detail) {
        super();
        this.cod_id = cod_id;
        this.cod_code = cod_code;
        this.cod_pic = cod_pic;
        this.cod_detail = cod_detail;
    }
    public CodePicDetail() {
        super();
    }
    @Override
    public String toString() {
        return "CodePicDetail [cod_id=" + cod_id + ", cod_code=" + cod_code
                + ", cod_pic=" + cod_pic + ", cod_detail=" + cod_detail + "]";
    }


}
