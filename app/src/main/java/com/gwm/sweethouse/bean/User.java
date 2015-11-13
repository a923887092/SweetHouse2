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
    private String user_sex;
    private String user_birth;

    public int getUser_id() {
        return user_id;
    }
    public void setUser_id(int userId) {
        user_id = userId;
    }
    public String getUser_name() {
        return user_name;
    }
    public void setUser_name(String userName) {
        user_name = userName;
    }
    public String getUser_password() {
        return user_password;
    }
    public void setUser_password(String userPassword) {
        user_password = userPassword;
    }
    public String getUser_mobile() {
        return user_mobile;
    }
    public void setUser_mobile(String userMobile) {
        user_mobile = userMobile;
    }
    public String getUser_image() {
        return user_image;
    }
    public void setUser_image(String userImage) {
        user_image = userImage;
    }
    public String getUser_state() {
        return user_state;
    }
    public void setUser_state(String userState) {
        user_state = userState;
    }
    public String getUser_sex() {
        return user_sex;
    }
    public void setUser_sex(String userSex) {
        user_sex = userSex;
    }
    public String getUser_birth() {
        return user_birth;
    }
    public void setUser_birth(String userBirth) {
        user_birth = userBirth;
    }
    public User() {
        super();
        // TODO Auto-generated constructor stub
    }
    public User(int userId, String userName, String userPassword,
                String userMobile, String userImage, String userState,
                String userSex, String userBirth) {
        super();
        user_id = userId;
        user_name = userName;
        user_password = userPassword;
        user_mobile = userMobile;
        user_image = userImage;
        user_state = userState;
        user_sex = userSex;
        user_birth = userBirth;
    }
    @Override
    public String toString() {
        return "User [user_birth=" + user_birth + ", user_id=" + user_id
                + ", user_image=" + user_image + ", user_mobile=" + user_mobile
                + ", user_name=" + user_name + ", user_password="
                + user_password + ", user_sex=" + user_sex + ", user_state="
                + user_state + "]";
    }


}
