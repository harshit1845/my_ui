package com.example.makesurest.model;

import com.google.gson.annotations.SerializedName;

public class ProductResponse {
    @SerializedName("product_name")
    private String productName;

    @SerializedName("prod_id")
    private String prodId;

    @SerializedName("generic_name")
    private String genricName;

    @SerializedName("gtin_companycode")
    private String gtinCompanyCode;

    @SerializedName("product_code")
    private String productCode ;

    @SerializedName("flag")
    private String flag;


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getGenricName() {
        return genricName;
    }

    public void setGenricName(String genricName) {
        this.genricName = genricName;
    }

    public String getGtinCompanyCode() {
        return gtinCompanyCode;
    }

    public void setGtinCompanyCode(String gtinCompanyCode) {
        this.gtinCompanyCode = gtinCompanyCode;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}

