package com.example.makesurest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponce {
    @SerializedName("Message")
    @Expose
    private String message;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public boolean isUserActive() {
        return message.equals("Login successful.");
    }

}
