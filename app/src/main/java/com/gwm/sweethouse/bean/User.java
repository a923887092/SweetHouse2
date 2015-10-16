package com.gwm.sweethouse.bean;

/**
 * Created by Administrator on 2015/10/15.
 */
public class User {
    private int user_id;
    private String user_name;
    private String user_password;
    private String user_mobile;
    private String user_image;
    private String user_state;
    /**
     * @return the user_id
     */
    public int getUser_id() {
        return user_id;
    }
    /**
     * @param user_id the user_id to set
     */
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    /**
     * @return the user_name
     */
    public String getUser_name() {
        return user_name;
    }
    /**
     * @param user_name the user_name to set
     */
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
    /**
     * @return the user_password
     */
    public String getUser_password() {
        return user_password;
    }
    /**
     * @param user_password the user_password to set
     */
    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }
    /**
     * @return the user_mobile
     */
    public String getUser_mobile() {
        return user_mobile;
    }
    /**
     * @param user_mobile the user_mobile to set
     */
    public void setUser_mobile(String user_mobile) {
        this.user_mobile = user_mobile;
    }
    /**
     * @return the user_image
     */
    public String getUser_image() {
        return user_image;
    }
    /**
     * @param user_image the user_image to set
     */
    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }
    /**
     * @return the user_state
     */
    public String getUser_state() {
        return user_state;
    }
    /**
     * @param user_state the user_state to set
     */
    public void setUser_state(String user_state) {
        this.user_state = user_state;
    }
    /**
     * @param user_id
     * @param user_name
     * @param user_password
     * @param user_mobile
     * @param user_image
     * @param user_state
     */
    public User(int user_id, String user_name, String user_password,
                String user_mobile, String user_image, String user_state) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_password = user_password;
        this.user_mobile = user_mobile;
        this.user_image = user_image;
        this.user_state = user_state;
    }
    /**
     *
     */
    public User() {
    }
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "User [user_id=" + user_id + ", user_name=" + user_name
                + ", user_password=" + user_password + ", user_mobile="
                + user_mobile + ", user_image=" + user_image + ", user_state="
                + user_state + "]";
    }

}
