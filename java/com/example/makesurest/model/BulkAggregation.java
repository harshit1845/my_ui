package com.example.makesurest.model;

import com.google.gson.JsonElement;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BulkAggregation  {
    @SerializedName("company_id")
    @Expose
    private String companyId;
    @SerializedName("prod_id")
    @Expose
    private String prodId;
    @SerializedName("batchid")
    @Expose
    private String batchid;
    @SerializedName("is_sscc")
    @Expose
    private String is_sscc;
    @SerializedName("Loose")
    @Expose
    private String Loose;
    @SerializedName("product_matrix_id")
    @Expose
    private String product_matrix_id;
    @SerializedName("product_matrix_id")
    @Expose
    private String product_matrix_id1;
    @SerializedName("parent")
    @Expose
    private String parent;

    @SerializedName("UID")
    @Expose
    public BulkAggregationChild uid;

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

    public String getBatchid() {
        return batchid;
    }

    public void setBatchid(String batchid) {
        this.batchid = batchid;
    }

    public String getIs_sscc() {
        return is_sscc;
    }

    public void setIs_sscc(String is_sscc) {
        this.is_sscc = is_sscc;
    }

    public String getLoose() {
        return Loose;
    }

    public void setLoose(String loose) {
        Loose = loose;
    }

    public String getProduct_matrix_id() {
        return product_matrix_id;
    }

    public void setProduct_matrix_id(String product_matrix_id) {
        this.product_matrix_id = product_matrix_id;
    }

    public String getProduct_matrix_id1() {
        return product_matrix_id1;
    }

    public void setProduct_matrix_id1(String product_matrix_id1) {
        this.product_matrix_id1 = product_matrix_id1;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public BulkAggregationChild getUid() {
        return uid;
    }

    public void setUid(BulkAggregationChild uid) {
        this.uid = uid;
    }
}
