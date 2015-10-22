package com.gwm.sweethouse.bean;

import java.io.Serializable;

public class Product implements Serializable{
	private int product_id;
	private String product_name;
	private int xl_id;
	private float product_price;
	private int comment_counts;
	private float product_discount;
	private String product_photo;
	private int product_sum;
	private String product_desc;
	private int saled_num;
	
	public int getSaled_num() {
		return saled_num;
	}
	public void setSaled_num(int saled_num) {
		this.saled_num = saled_num;
	}

	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int productId) {
		product_id = productId;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String productName) {
		product_name = productName;
	}

	public int getXl_id() {
		return xl_id;
	}

	public void setXl_id(int xlId) {
		xl_id = xlId;
	}

	public float getProduct_price() {
		return product_price;
	}

	public void setProduct_price(float productPrice) {
		product_price = productPrice;
	}

	public int getComment_counts() {
		return comment_counts;
	}

	public void setComment_counts(int commentCounts) {
		comment_counts = commentCounts;
	}

	public float getProduct_discount() {
		return product_discount;
	}

	public void setProduct_discount(float productDiscount) {
		product_discount = productDiscount;
	}

	public String getProduct_photo() {
		return product_photo;
	}

	public void setProduct_photo(String productPhoto) {
		product_photo = productPhoto;
	}

	public int getProduct_sum() {
		return product_sum;
	}

	public void setProduct_sum(int productSum) {
		product_sum = productSum;
	}

	public String getProduct_desc() {
		return product_desc;
	}

	public void setProduct_desc(String productDesc) {
		product_desc = productDesc;
	}
	@Override
	public String toString() {
		return "Product [product_id=" + product_id + ", product_name="
				+ product_name + ", xl_id=" + xl_id + ", product_price="
				+ product_price + ", comment_counts=" + comment_counts
				+ ", product_discount=" + product_discount + ", product_photo="
				+ product_photo + ", product_sum=" + product_sum
				+ ", product_desc=" + product_desc + ", saled_num=" + saled_num
				+ "]";
	}
	public Product(int product_id, String product_name, int xl_id,
			float product_price, int comment_counts, float product_discount,
			String product_photo, int product_sum, String product_desc,
			int saled_num) {
		this.product_id = product_id;
		this.product_name = product_name;
		this.xl_id = xl_id;
		this.product_price = product_price;
		this.comment_counts = comment_counts;
		this.product_discount = product_discount;
		this.product_photo = product_photo;
		this.product_sum = product_sum;
		this.product_desc = product_desc;
		this.saled_num = saled_num;
	}
	public Product() {
		
	}

}
