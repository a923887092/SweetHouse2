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
    private float price;
    private float product_discount;
    int product_id;

    public int getProduct_id() {

        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }



    public float getProduct_discount() {
        return product_discount;
    }

    public void setProduct_discount(float product_discount) {
        this.product_discount = product_discount;
    }

    public int getCart_id() {
        return cart_id;
    }

    public void setCart_id(int cart_id) {
        this.cart_id = cart_id;
    }

    public int getGoods_amount() {
        return goods_amount;
    }

    public void setGoods_amount(int goods_amount) {
        this.goods_amount = goods_amount;
    }

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

    public String getImagesrc() {
        return imagesrc;
    }

    public void setImagesrc(String imagesrc) {
        this.imagesrc = imagesrc;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }


    public CartBean(int goods_amount, String goodsname, String goodsDescribe, String imagesrc, float price,int cart_id,float product_discount,int product_id) {
        this.goods_amount = goods_amount;
        this.goodsname = goodsname;
        this.goodsDescribe = goodsDescribe;
        this.imagesrc = imagesrc;
        this.price = price;
        this.cart_id=cart_id;
        this.product_discount=product_discount;
        this.product_id=product_id;
    }



    public CartBean() {

    }


}
