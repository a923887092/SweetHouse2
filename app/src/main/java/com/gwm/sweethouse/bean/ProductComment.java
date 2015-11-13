package com.gwm.sweethouse.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2015/11/3.
 */
public class ProductComment implements Serializable {

    private static final long serialVersionUID = 1L;

    int comment_id;
    int product_id;
    int order_id;
    int user_id;
    float comment_grade;
    String comment_content;
    Date comment_time;
    public int getComment_id() {
        return comment_id;
    }
    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }
    public int getProduct_id() {
        return product_id;
    }
    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }
    public int getOrder_id() {
        return order_id;
    }
    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }
    public int getUser_id() {
        return user_id;
    }
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    public float getComment_grade() {
        return comment_grade;
    }
    public void setComment_grade(float comment_grade) {
        this.comment_grade = comment_grade;
    }
    public String getComment_content() {
        return comment_content;
    }
    public void setComment_content(String comment_content) {
        this.comment_content = comment_content;
    }
    public Date getComment_time() {
        return comment_time;
    }
    public void setComment_time(Date comment_time) {
        this.comment_time = comment_time;
    }
    public ProductComment() {
        super();
        // TODO Auto-generated constructor stub
    }
    public ProductComment(int comment_id, int product_id, int order_id,
                          int user_id, float comment_grade, String comment_content,
                          Date comment_time) {
        super();
        this.comment_id = comment_id;
        this.product_id = product_id;
        this.order_id = order_id;
        this.user_id = user_id;
        this.comment_grade = comment_grade;
        this.comment_content = comment_content;
        this.comment_time = comment_time;
    }
    @Override
    public String toString() {
        return "ProductComment [comment_id=" + comment_id + ", product_id="
                + product_id + ", order_id=" + order_id + ", user_id="
                + user_id + ", comment_grade=" + comment_grade
                + ", comment_content=" + comment_content + ", comment_time="
                + comment_time + "]";
    }
}
