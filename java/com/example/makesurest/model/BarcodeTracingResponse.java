package com.example.makesurest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BarcodeTracingResponse {

    @SerializedName("ParentData")
    @Expose
    private PerantData parentData;

    @SerializedName("ChildData")
    @Expose
    private List<ChildData> Childdata;

    public PerantData getParentData() {
        return parentData;
    }

    public void setParentData(PerantData parentData) {
        this.parentData = parentData;
    }

    public List<ChildData> getChilddata() {
        return Childdata;
    }

    public void setChilddata(List<ChildData> childdata) {
        Childdata = childdata;
    }
}