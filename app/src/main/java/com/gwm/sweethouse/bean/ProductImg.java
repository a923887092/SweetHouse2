package com.gwm.sweethouse.bean;

import java.io.Serializable;

public class ProductImg implements Serializable{
	private int img_id;
	private int product_id;
	private String img_url;
	/**
	 * @return the img_id
	 */
	public int getImg_id() {
		return img_id;
	}
	/**
	 * @param img_id the img_id to set
	 */
	public void setImg_id(int img_id) {
		this.img_id = img_id;
	}
	/**
	 * @return the product_id
	 */
	public int getProduct_id() {
		return product_id;
	}
	/**
	 * @param product_id the product_id to set
	 */
	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}
	/**
	 * @return the img_url
	 */
	public String getImg_url() {
		return img_url;
	}
	/**
	 * @param img_url the img_url to set
	 */
	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}
	/**
	 * @param img_id
	 * @param product_id
	 * @param img_url
	 */
	public ProductImg(int img_id, int product_id, String img_url) {
		this.img_id = img_id;
		this.product_id = product_id;
		this.img_url = img_url;
	}
	/**
	 * 
	 */
	public ProductImg() {
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ProductImg [img_id=" + img_id + ", product_id=" + product_id
				+ ", img_url=" + img_url + "]";
	}
	
}
