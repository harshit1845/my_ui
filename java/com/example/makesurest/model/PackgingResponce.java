package com.example.makesurest.model;

import com.google.gson.annotations.SerializedName;

public class PackgingResponce {

    @SerializedName("product_matrix_id")
    private String productMatrixId;

    @SerializedName("product_name")
    private String productName;

    @SerializedName("gtin_no")
    private String gtinNo;

    @SerializedName("matrix")
    private String matrix;

    @SerializedName("group_name")
    private String groupName;

    @SerializedName("level_name")
    private String levelName ;

    @SerializedName("packaging")
    private String packaging;

    @SerializedName("provider")
    private String provider;

    public String getProductMatrixId() {
        return productMatrixId;
    }

    public void setProductMatrixId(String productMatrixId) {
        this.productMatrixId = productMatrixId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getGtinNo() {
        return gtinNo;
    }

    public void setGtinNo(String gtinNo) {
        this.gtinNo = gtinNo;
    }

    public String getMatrix() {
        return matrix;
    }

    public void setMatrix(String matrix) {
        this.matrix = matrix;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getPackaging() {
        return packaging;
    }

    public void setPackaging(String packaging) {
        this.packaging = packaging;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }
}
