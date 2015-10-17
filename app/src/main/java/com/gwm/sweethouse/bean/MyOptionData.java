package com.gwm.sweethouse.bean;

/**
 * Created by Administrator on 2015/10/16.
 */
public class MyOptionData {

    int picId;
    String text;

    public int getPicId() {
        return picId;
    }
    public void setPicId(int picId) {
        this.picId = picId;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }


    public MyOptionData(int picId, String text) {
        super();
        this.picId = picId;
        this.text = text;
    }


}
