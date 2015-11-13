package com.gwm.sweethouse.bean;

import java.io.Serializable;
import java.util.Date;

public class Comment implements Serializable{
	private String user_name;
	private String user_image;
	private Date comment_time;
	private float comment_grade;
	private String comment_content;

	@Override
	public String toString() {
		return "Comment{" +
				"user_name='" + user_name + '\'' +
				", user_image='" + user_image + '\'' +
				", comment_time=" + comment_time +
				", comment_grade=" + comment_grade +
				", comment_content='" + comment_content + '\'' +
				'}';
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_image() {
		return user_image;
	}

	public void setUser_image(String user_image) {
		this.user_image = user_image;
	}

	public Date getComment_time() {
		return comment_time;
	}

	public void setComment_time(Date comment_time) {
		this.comment_time = comment_time;
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

	public Comment(String user_image, Date comment_time, float comment_grade, String comment_content, String user_name) {
		this.user_image = user_image;
		this.comment_time = comment_time;
		this.comment_grade = comment_grade;
		this.comment_content = comment_content;
		this.user_name = user_name;
	}

	public Comment() {

	}
}
