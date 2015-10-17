package com.gwm.sweethouse.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/10/15.
 */
public class SubClass implements Serializable{
    private int xl_id;
    private String xl_name;
    private int dl_id;
    private String xl_pic;
    public int getXl_id() {
        return xl_id;
    }
    public void setXl_id(int xl_id) {
        this.xl_id = xl_id;
    }
    public String getXl_name() {
        return xl_name;
    }
    public void setXl_name(String xl_name) {
        this.xl_name = xl_name;
    }
    public int getDl_id() {
        return dl_id;
    }
    public void setDl_id(int dl_id) {
        this.dl_id = dl_id;
    }
    public String getXl_pic() {
        return xl_pic;
    }
    public void setXl_pic(String xl_pic) {
        this.xl_pic = xl_pic;
    }
    @Override
    public String toString() {
        return "ProductXl [xl_id=" + xl_id + ", xl_name=" + xl_name
                + ", dl_id=" + dl_id + ", xl_pic=" + xl_pic + "]";
    }
    public SubClass(int xl_id, String xl_name, int dl_id, String xl_pic) {
        this.xl_id = xl_id;
        this.xl_name = xl_name;
        this.dl_id = dl_id;
        this.xl_pic = xl_pic;
    }
    public SubClass() {
    }

}
