package com.gwm.sweethouse.bean;

public class Parameter {
	private int para_id;
	private int product_id;
	private String para_model;
	private String para_func;
	private String para_power;
	private String para_quality;
	private String para_parts;
	public Parameter() {
	}
	public Parameter(int para_id, int product_id, String para_model,
			String para_func, String para_power, String para_quality,
			String para_parts) {
		this.para_id = para_id;
		this.product_id = product_id;
		this.para_model = para_model;
		this.para_func = para_func;
		this.para_power = para_power;
		this.para_quality = para_quality;
		this.para_parts = para_parts;
	}
	@Override
	public String toString() {
		return "Parameter [para_id=" + para_id + ", product_id=" + product_id
				+ ", para_model=" + para_model + ", para_func=" + para_func
				+ ", para_power=" + para_power + ", para_quality="
				+ para_quality + ", para_parts=" + para_parts + "]";
	}
	public int getPara_id() {
		return para_id;
	}
	public void setPara_id(int para_id) {
		this.para_id = para_id;
	}
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
	 * @return the para_model
	 */
	public String getPara_model() {
		return para_model;
	}
	/**
	 * @param para_model the para_model to set
	 */
	public void setPara_model(String para_model) {
		this.para_model = para_model;
	}
	/**
	 * @return the para_func
	 */
	public String getPara_func() {
		return para_func;
	}
	/**
	 * @param para_func the para_func to set
	 */
	public void setPara_func(String para_func) {
		this.para_func = para_func;
	}
	/**
	 * @return the para_power
	 */
	public String getPara_power() {
		return para_power;
	}
	/**
	 * @param para_power the para_power to set
	 */
	public void setPara_power(String para_power) {
		this.para_power = para_power;
	}
	/**
	 * @return the para_quality
	 */
	public String getPara_quality() {
		return para_quality;
	}
	/**
	 * @param para_quality the para_quality to set
	 */
	public void setPara_quality(String para_quality) {
		this.para_quality = para_quality;
	}
	/**
	 * @return the para_parts
	 */
	public String getPara_parts() {
		return para_parts;
	}
	/**
	 * @param para_parts the para_parts to set
	 */
	public void setPara_parts(String para_parts) {
		this.para_parts = para_parts;
	}
}
