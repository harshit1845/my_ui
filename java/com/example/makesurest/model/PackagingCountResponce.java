package com.example.makesurest.model;

import com.google.gson.annotations.SerializedName;

public class PackagingCountResponce {

    @SerializedName("Packeging_Level_Count")
    private String packgingCount;

    public String getPackgingCount() {
        return packgingCount;
    }

    public void setPackgingCount(String packgingCount) {
        this.packgingCount = packgingCount;
    }
}
