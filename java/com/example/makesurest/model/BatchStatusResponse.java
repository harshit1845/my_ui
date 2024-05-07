package com.example.makesurest.model;

import com.google.gson.annotations.SerializedName;

public class BatchStatusResponse {

    @SerializedName("batchid")
    private String batchId;

    @SerializedName("batch_number")
    private String batchNumber;

    @SerializedName("Date")
    private String date;

    @SerializedName("batch_status")
    private String batchStatus;

    @SerializedName("product_name")
    private String productName;

    @SerializedName("batch_size")
    private String batchSize ;

    @SerializedName("gross_weight")
    private String grossWeight;

    @SerializedName("prod_package_id")
    private String prodPackageId;

    @SerializedName("flag")
    private String flag;

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBatchStatus() {
        return batchStatus;
    }

    public void setBatchStatus(String batchStatus) {
        this.batchStatus = batchStatus;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(String batchSize) {
        this.batchSize = batchSize;
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
