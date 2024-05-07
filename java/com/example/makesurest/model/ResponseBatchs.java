package com.example.makesurest.model;

import com.google.gson.annotations.SerializedName;

public class ResponseBatchs {


    @SerializedName("companyid")
    private String companyId;

    @SerializedName("Prod_id")
    private String prodId;

    @SerializedName("Date")
    private String date;

    @SerializedName("batch_status")
    private String batchStatus;

    @SerializedName("product_name")
    private String productName;

    @SerializedName("Status")
    private String status ;

    @SerializedName("batchid")
    private String batchId;

    @SerializedName("batch_number")
    private String batchNumber;

    @SerializedName("expiry_date")
    private String expiryDate;

    @SerializedName("batch_size")
    private String batchSize;

    @SerializedName("mfg_date")
    private String mfdDate;

    @SerializedName("net_weight")
    private String netWeight;

    @SerializedName("gross_weight")
    private String grossWeight;

    @SerializedName("prod_package_id")
    private String proudPackage;

    @SerializedName("flag")
    private String flag;

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
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

    public String getProudPackage() {
        return proudPackage;
    }

    public void setProudPackage(String proudPackage) {
        this.proudPackage = proudPackage;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
