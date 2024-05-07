package com.example.makesurest.model;

import com.google.gson.annotations.SerializedName;

public class PerantData {
    @SerializedName("serial_no")
    private String serialNo;

    @SerializedName("product_name")
    private String productName;

    @SerializedName("batch_no")
    private String batchNumber;

    @SerializedName("exp_date")
    private String expDate;

    @SerializedName("gtin_no")
    private String gtinNo;

    @SerializedName("packaging_level")
    private String packagingLevel;

    @SerializedName("ChildCount")
    private String childCount;

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public String getGtinNo() {
        return gtinNo;
    }

    public void setGtinNo(String gtinNo) {
        this.gtinNo = gtinNo;
    }

    public String getPackagingLevel() {
        return packagingLevel;
    }

    public void setPackagingLevel(String packagingLevel) {
        this.packagingLevel = packagingLevel;
    }

    public String getChildCount() {
        return childCount;
    }

    public void setChildCount(String childCount) {
        this.childCount = childCount;
    }
}
