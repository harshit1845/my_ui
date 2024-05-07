package com.example.makesurest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BulkAggregationChild {

    @SerializedName("child")
    @Expose
    public ArrayList<String> childData;

    public ArrayList<String> getChildData() {
        return childData;
    }

    public void setChildData(ArrayList<String> childData) {
        this.childData = childData;
    }
}
