package com.example.makesurest.model;

import com.google.gson.annotations.SerializedName;

public class DispatchScanningResponce {

    @SerializedName("Message")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
