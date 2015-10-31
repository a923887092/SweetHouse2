package com.gwm.sweethouse.bean;

/**
 * Created by Administrator on 2015/10/27.
 */
public class Wallet {
    private int user_id;
    private int wallet_id;
    private float wallet_balance;
    public int getUser_id() {
        return user_id;
    }
    @Override
    public String toString() {
        return "Wallet [user_id=" + user_id + ", wallet_balance="
                + wallet_balance + ", wallet_id=" + wallet_id + "]";
    }
    public Wallet() {
        super();
    }
    public Wallet(int userId, int walletId, float walletBalance) {
        super();
        user_id = userId;
        wallet_id = walletId;
        wallet_balance = walletBalance;
    }
    public void setUser_id(int userId) {
        user_id = userId;
    }
    public int getWallet_id() {
        return wallet_id;
    }
    public void setWallet_id(int walletId) {
        wallet_id = walletId;
    }
    public float getWallet_balance() {
        return wallet_balance;
    }
    public void setWallet_balance(float walletBalance) {
        wallet_balance = walletBalance;
    }

}
