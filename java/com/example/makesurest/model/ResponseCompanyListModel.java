package com.example.makesurest.model;

import com.google.gson.annotations.SerializedName;

public class ResponseCompanyListModel {


    @SerializedName("company_id")
    private String companyId;
    @SerializedName("company_name")
    private String companyName;

    @SerializedName("gtin_countrycode")
    private String gtinCountryCode;

    @SerializedName("gtin_companycode")
    private String gtinCompanyCode;

    @SerializedName("flag")
    private String flag;

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getGtinCountryCode() {
        return gtinCountryCode;
    }

    public void setGtinCountryCode(String gtinCountryCode) {
        this.gtinCountryCode = gtinCountryCode;
    }

    public String getGtinCompanyCode() {
        return gtinCompanyCode;
    }

    public void setGtinCompanyCode(String gtinCompanyCode) {
        this.gtinCompanyCode = gtinCompanyCode;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
