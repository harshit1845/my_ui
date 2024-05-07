package com.example.makesurest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SendLoginRequestModel {
    @SerializedName("username")
    @Expose
    private String email;
    @SerializedName("passwords")
    @Expose
    private String password;
    @SerializedName("flag")
    @Expose
    private int flag;

    public String getUserName() {
        return email;
    }

    public void setUsername(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getFlag(int i) {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
