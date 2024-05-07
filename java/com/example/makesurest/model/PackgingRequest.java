package com.example.makesurest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PackgingRequest {

    @SerializedName("prod_id")
    @Expose
    private String prodId;
    @SerializedName("prod_package_id")
    @Expose
    private String prodPackagingId;

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getProdPackagingId() {
        return prodPackagingId;
    }

    public void setProdPackagingId(String prodPackagingId) {
        this.prodPackagingId = prodPackagingId;
    }
}
