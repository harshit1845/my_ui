package com.example.makesurest.model;

import com.google.gson.annotations.SerializedName;

public class BatchResponse {

    @SerializedName("batch_number")
    private String batchNumber;

    @SerializedName("batchid")
    private String batchId;

    @SerializedName("batch_status")
    private String BatchStatus;

    @SerializedName("date")
    private String date;

    @SerializedName("batch_size")
    private String batchSize;

    @SerializedName("mfg_date")
    private String mfdDate ;

    @SerializedName("net_weight")
    private String netWeight;

    @SerializedName("gross_weight")
    private String grossWeight;

    @SerializedName("prod_package_id")
    private String prodPackageId;

    @SerializedName("flag")
    private String flag;

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getBatchStatus() {
        return BatchStatus;
    }

    public void setBatchStatus(String batchStatus) {
        BatchStatus = batchStatus;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(String batchSize) {
        this.batchSize = batchSize;
    }

    public String getMfdDate() {
        return mfdDate;
    }

    public void setMfdDate(String mfdDate) {
        this.mfdDate = mfdDate;
    }

    public String getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(String netWeight) {
        this.netWeight = netWeight;
    }

    public String getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(String grossWeight) {
        this.grossWeight = grossWeight;
    }

    public String getProdPackageId() {
        return prodPackageId;
    }

    public void setProdPackageId(String prodPackageId) {
        this.prodPackageId = prodPackageId;
    }



    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
