package com.example.makesurest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckDuplicateRequest {

    @SerializedName("batchid")
    @Expose
    private String batchId;
    @SerializedName("prod_id")
    @Expose
    private String productId;

    @SerializedName("company_id")
    @Expose
    private String companyId;
    @SerializedName("product_matrix_id")
    @Expose
    private String productMatrixId;

    @SerializedName("gtin_no")
    @Expose
    private String gtinNo;
    @SerializedName("parent")
    @Expose
    private String uidValid;

    @SerializedName("is_sscc")
    @Expose
    private String isscc;
    @SerializedName("exp_date")
    @Expose
    private String expirydate;


    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getProductMatrixId() {
        return productMatrixId;
    }

    public void setProductMatrixId(String productMatrixId) {
        this.productMatrixId = productMatrixId;
    }

    public String getGtinNo() {
        return gtinNo;
    }

    public void setGtinNo(String gtinNo) {
        this.gtinNo = gtinNo;
    }

    public String getUidValid() {
        return uidValid;
    }

    public void setUidValid(String uidValid) {
        this.uidValid = uidValid;
    }

    public String getIsscc() {
        return isscc;
    }

    public void setIsscc(String isscc) {
        this.isscc = isscc;
    }

    public String getExpirydate() {
        return expirydate;
    }

    public void setExpirydate(String expirydate) {
        this.expirydate = expirydate;
    }
}
