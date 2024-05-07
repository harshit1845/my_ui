package com.example.makesurest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BatchRequest {
    @SerializedName("prod_id")
    @Expose
    private String prodId;
    @SerializedName("company_id")
    @Expose
    private String compnyID;

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getCompnyID() {
        return compnyID;
    }

    public void setCompnyID(String compnyID) {
        this.compnyID = compnyID;
    }
}

