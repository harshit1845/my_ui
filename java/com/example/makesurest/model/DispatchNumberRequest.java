package com.example.makesurest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DispatchNumberRequest {

    @SerializedName("company_id")
    @Expose
    private String compnyID;
    @SerializedName("prod_id")
    @Expose
    private String prodId;

    @SerializedName("batch_id")
    @Expose
    private String batchId;

    @SerializedName("divertflag")
    @Expose
    private String flag;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getCompnyID() {
        return compnyID;
    }

    public void setCompnyID(String compnyID) {
        this.compnyID = compnyID;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }
}
