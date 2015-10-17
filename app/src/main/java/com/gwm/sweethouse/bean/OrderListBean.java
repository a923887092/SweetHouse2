package com.gwm.sweethouse.bean;

import android.util.Log;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/10/16.
 */
public class OrderListBean implements Serializable {
    private String goodsname;
    private String goodsDescribe;
    private int imagesrc;
    private int price;

    public String getGoodsname() {
        return goodsname;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    public String getGoodsDescribe() {
        return goodsDescribe;
    }

    public void setGoodsDescribe(String goodsDescribe) {
        this.goodsDescribe = goodsDescribe;
    }

    public int getImagesrc() {
        return imagesrc;
    }

    public void setImagesrc(int imagesrc) {
        this.imagesrc = imagesrc;
    }

    public int getPrice() {
        return price;
    }

    public OrderListBean() {
        Log.e("3.5", "bean");
    }

    public void setPrice(int price) {

        this.price = price;
    }



    public OrderListBean(String goodsname, String goodsDescribe, int imagesrc, int price) {
        this.goodsname = goodsname;
        this.goodsDescribe = goodsDescribe;
        this.imagesrc = imagesrc;
        this.price = price;
    }



    @Override
    public String toString() {
        return "OrderListBean{" +
                "goodsname='" + goodsname + '\'' +
                ", goodsDescribe='" + goodsDescribe + '\'' +
                ", imagesrc=" + imagesrc +
                ", price=" + price +
                '}';
    }
}
