package com.example.makesurest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CountRequest {
    @SerializedName("product_matrix_id")
    @Expose
    private String productMatrix;

    @SerializedName("batch_no")
    @Expose
    private String batchNo;

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getProductMatrix() {
        return productMatrix;
    }

    public void setProductMatrix(String productMatrix) {
        this.productMatrix = productMatrix;
    }
}
