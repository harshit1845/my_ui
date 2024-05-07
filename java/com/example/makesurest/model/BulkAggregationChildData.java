package com.example.makesurest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BulkAggregationChildData {

    @SerializedName("")
    @Expose
    public ArrayList<String> childDataA;

    public BulkAggregationChildData(ArrayList scannedArray) {
        this.childDataA = scannedArray;
    }

    public ArrayList<String> getChildData() {
        return childDataA;
    }

    public void setChildData(ArrayList<String> childData) {
        this.childDataA = childData;
    }
}
