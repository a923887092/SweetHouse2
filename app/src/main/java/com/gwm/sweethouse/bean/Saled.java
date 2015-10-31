package com.gwm.sweethouse.bean;

import java.io.Serializable;

public class Saled implements Serializable{
	private int rec_id;
	private int product_id;
	private String product_name;
	private String product_photo;
	private String product_desc;
	private float product_price;
	private float product_discount;
	@Override
	public String toString() {
		return "saled [rec_id=" + rec_id + ", product_id=" + product_id
				+ ", product_name=" + product_name + ", product_photo="
				+ product_photo + ", product_desc=" + product_desc
				+ ", product_price=" + product_price + ", product_discount="
				+ product_discount + "]";
	}
	public Saled(int rec_id, int product_id, String product_name,
			String product_photo, String product_desc, float product_price,
			float product_discount) {
		this.rec_id = rec_id;
		this.product_id = product_id;
		this.product_name = product_name;
		this.product_photo = product_photo;
		this.product_desc = product_desc;
		this.product_price = product_price;
		this.product_discount = product_discount;
	}
	public Saled() {
	}
	public int getRec_id() {
		return rec_id;
	}
	public void setRec_id(int rec_id) {
		this.rec_id = rec_id;
	}
	public int getProduct_id() {
		return product_id;
	}
	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getProduct_photo() {
		return product_photo;
	}
	public void setProduct_photo(String product_photo) {
		this.product_photo = product_photo;
	}
	public String getProduct_desc() {
		return product_desc;
	}
	public void setProduct_desc(String product_desc) {
		this.product_desc = product_desc;
	}
	public float getProduct_price() {
		return product_price;
	}
	public void setProduct_price(float product_price) {
		this.product_price = product_price;
	}
	public float getProduct_discount() {
		return product_discount;
	}
	public void setProduct_discount(float product_discount) {
		this.product_discount = product_discount;
	}
	
}
