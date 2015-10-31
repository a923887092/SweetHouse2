package com.gwm.sweethouse.bean;

public class Company {
	int zx_comid;
	String zx_comName;
	String zx_comlog;
	String zx_comaprice;
	String zx_comexample;
	String zx_comdesigner;
	String zx_comaddr;
	String zx_comstyle;
	public int getZx_comid() {
		return zx_comid;
	}
	public void setZx_comid(int zx_comid) {
		this.zx_comid = zx_comid;
	}
	public String getZx_comName() {
		return zx_comName;
	}
	public void setZx_comName(String zx_comName) {
		this.zx_comName = zx_comName;
	}
	public String getZx_comlog() {
		return zx_comlog;
	}
	public void setZx_comlog(String zx_comlog) {
		this.zx_comlog = zx_comlog;
	}
	public String getZx_comaprice() {
		return zx_comaprice;
	}
	public void setZx_comaprice(String zx_comaprice) {
		this.zx_comaprice = zx_comaprice;
	}
	public String getZx_comexample() {
		return zx_comexample;
	}
	public void setZx_comexample(String zx_comexample) {
		this.zx_comexample = zx_comexample;
	}
	public String getZx_comdesigner() {
		return zx_comdesigner;
	}
	public void setZx_comdesigner(String zx_comdesigner) {
		this.zx_comdesigner = zx_comdesigner;
	}
	public String getZx_comaddr() {
		return zx_comaddr;
	}
	public void setZx_comaddr(String zx_comaddr) {
		this.zx_comaddr = zx_comaddr;
	}
	public String getZx_comstyle() {
		return zx_comstyle;
	}
	public void setZx_comstyle(String zx_comstyle) {
		this.zx_comstyle = zx_comstyle;
	}
	@Override
	public String toString() {
		return "ZxCompany [zx_comid=" + zx_comid + ", zx_comName=" + zx_comName
				+ ", zx_comlog=" + zx_comlog + ", zx_comaprice=" + zx_comaprice
				+ ", zx_comexample=" + zx_comexample + ", zx_comdesigner="
				+ zx_comdesigner + ", zx_comaddr=" + zx_comaddr
				+ ", zx_comstyle=" + zx_comstyle + "]";
	}

	public Company() {
	}

	public Company(int zx_comid, String zx_comName, String zx_comlog, String zx_comaprice, String zx_comexample, String zx_comdesigner, String zx_comaddr, String zx_comstyle) {
		this.zx_comid = zx_comid;
		this.zx_comName = zx_comName;
		this.zx_comlog = zx_comlog;
		this.zx_comaprice = zx_comaprice;
		this.zx_comexample = zx_comexample;
		this.zx_comdesigner = zx_comdesigner;
		this.zx_comaddr = zx_comaddr;
		this.zx_comstyle = zx_comstyle;
	}
}
