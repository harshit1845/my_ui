package com.example.makesurest.model;

import com.google.gson.annotations.SerializedName;

public class CountCaseResponce {
    @SerializedName("countparent")
    private String countParent;

    public String getCountParent() {
        return countParent;
    }

    public void setCountParent(String countParent) {
        this.countParent = countParent;
    }
}
