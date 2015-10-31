package com.gwm.sweethouse.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/10/16.
 */
public class OrderListBean implements Serializable {
    private static final long serialVersionUID = 1L;
    //订单表属性
    int user_id;
    int order_id;
    int buy_count;
    int pay_id;
    float order_price;
    int	order_state;
    int add_id;
    //订单所需pruduct属性
    String product_Photo;
    String product_name;
    String product_desc;
    public int getUser_id() {
        return user_id;
    }
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    public int getOrder_id() {
        return order_id;
    }
    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }
    public int getBuy_count() {
        return buy_count;
    }
    public void setBuy_count(int buy_count) {
        this.buy_count = buy_count;
    }
    public int getPay_id() {
        return pay_id;
    }
    public void setPay_id(int pay_id) {
        this.pay_id = pay_id;
    }
    public float getOrder_price() {
        return order_price;
    }
    public void setOrder_price(float order_price) {
        this.order_price = order_price;
    }
    public int getOrder_state() {
        return order_state;
    }
    public void setOrder_state(int order_state) {
        this.order_state = order_state;
    }
    public int getAdd_id() {
        return add_id;
    }
    public void setAdd_id(int add_id) {
        this.add_id = add_id;
    }
    public String getProduct_Photo() {
        return product_Photo;
    }
    public void setProduct_Photo(String product_Photo) {
        this.product_Photo = product_Photo;
    }
    public String getProduct_name() {
        return product_name;
    }
    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }
    public String getProduct_desc() {
        return product_desc;
    }
    public void setProduct_desc(String product_desc) {
        this.product_desc = product_desc;
    }
    public OrderListBean(int user_id, int order_id, int buy_count, int pay_id,
                 float order_price, int order_state, int add_id,
                 String product_Photo, String product_name, String product_desc) {
        super();
        this.user_id = user_id;
        this.order_id = order_id;
        this.buy_count = buy_count;
        this.pay_id = pay_id;
        this.order_price = order_price;
        this.order_state = order_state;
        this.add_id = add_id;
        this.product_Photo = product_Photo;
        this.product_name = product_name;
        this.product_desc = product_desc;
    }
    public OrderListBean() {
        super();
        // TODO Auto-generated constructor stub
    }
    @Override
    public String toString() {
        return "OrderListBean [user_id=" + user_id + ", order_id=" + order_id
                + ", buy_count=" + buy_count + ", pay_id=" + pay_id
                + ", order_price=" + order_price + ", order_state="
                + order_state + ", add_id=" + add_id + ", product_Photo="
                + product_Photo + ", product_name=" + product_name
                + ", product_desc=" + product_desc + "]";
    }

}
