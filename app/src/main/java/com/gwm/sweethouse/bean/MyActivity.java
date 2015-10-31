package com.gwm.sweethouse.bean;

import java.io.Serializable;
import java.sql.Date;

public class MyActivity implements Serializable{
	private int act_id;
	private String act_title;
	private String act_img;
	private String act_addr;
	private String act_detail_addr;
	private int act_people_num;
	private Date act_stop_dateDate;
	public int getAct_id() {
		return act_id;
	}
	public void setAct_id(int act_id) {
		this.act_id = act_id;
	}
	public String getAct_title() {
		return act_title;
	}
	public void setAct_title(String act_title) {
		this.act_title = act_title;
	}
	public String getAct_img() {
		return act_img;
	}
	public void setAct_img(String act_img) {
		this.act_img = act_img;
	}
	public String getAct_addr() {
		return act_addr;
	}
	public void setAct_addr(String act_addr) {
		this.act_addr = act_addr;
	}
	public String getAct_detail_addr() {
		return act_detail_addr;
	}
	public void setAct_detail_addr(String act_detail_addr) {
		this.act_detail_addr = act_detail_addr;
	}
	public int getAct_people_num() {
		return act_people_num;
	}
	public void setAct_people_num(int act_people_num) {
		this.act_people_num = act_people_num;
	}
	public Date getAct_stop_dateDate() {
		return act_stop_dateDate;
	}
	public void setAct_stop_dateDate(Date act_stop_dateDate) {
		this.act_stop_dateDate = act_stop_dateDate;
	}
	public MyActivity(int act_id, String act_title, String act_img,
			String act_addr, String act_detail_addr, int act_people_num,
			Date act_stop_dateDate) {
		this.act_id = act_id;
		this.act_title = act_title;
		this.act_img = act_img;
		this.act_addr = act_addr;
		this.act_detail_addr = act_detail_addr;
		this.act_people_num = act_people_num;
		this.act_stop_dateDate = act_stop_dateDate;
	}
	public MyActivity() {
	}
	@Override
	public String toString() {
		return "Activity [act_id=" + act_id + ", act_title=" + act_title
				+ ", act_img=" + act_img + ", act_addr=" + act_addr
				+ ", act_detail_addr=" + act_detail_addr + ", act_people_num="
				+ act_people_num + ", act_stop_dateDate=" + act_stop_dateDate
				+ "]";
	}
	
}
