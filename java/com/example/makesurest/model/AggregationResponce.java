package com.example.makesurest.model;

import com.google.gson.annotations.SerializedName;

public class AggregationResponce {

    @SerializedName("message")
    private String message;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }



}
