package com.gwm.sweethouse.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/10/19.
 */
public class CartBean implements Serializable{


    private int cart_id;
    private int goods_amount;
    private String goodsname;
    private String goodsDescribe;
    private String imagesrc;
    private int price;

    public int getGoods_amount() {
        return goods_amount;
    }

    public void setGoods_amount(int goods_amount) {
        this.goods_amount = goods_amount;
    }

    public String getGoodsname() {
        return goodsname;
    }

    public int getCart_id() {
        return cart_id;
    }

    public void setCart_id(int cart_id) {
        this.cart_id = cart_id;
    }

    @Override
    public String toString() {
        return "CartBean{" +
                "cart_id=" + cart_id +
                ", goods_amount=" + goods_amount +
                ", goodsname='" + goodsname + '\'' +
                ", goodsDescribe='" + goodsDescribe + '\'' +
                ", imagesrc='" + imagesrc + '\'' +
                ", price=" + price +
                '}';
    }

    public CartBean(int goods_amount, String goodsname, String goodsDescribe, String imagesrc, int price,int cart_id) {
        this.goods_amount = goods_amount;
        this.goodsname = goodsname;
        this.goodsDescribe = goodsDescribe;
        this.imagesrc = imagesrc;
        this.price = price;
        this.cart_id=cart_id;
    }

    public CartBean(int cart_id, int goods_amount, String goodsname, String goodsDescribe, String imagesrc, int price) {
        this.cart_id = cart_id;
        this.goods_amount = goods_amount;
        this.goodsname = goodsname;
        this.goodsDescribe = goodsDescribe;
        this.imagesrc = imagesrc;
        this.price = price;
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

    public String getImagesrc() {
        return imagesrc;
    }

    public void setImagesrc(String imagesrc) {
        this.imagesrc = imagesrc;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public CartBean() {

    }


}
