package com.example.makesurest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CompanyResponce {

    @SerializedName("error")
    @Expose
    private String state;
    @SerializedName("msg")
    @Expose
    private int errorCode;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public ResponseCompanyListModel getData() {
        return data;
    }

    public void setData(ResponseCompanyListModel data) {
        this.data = data;
    }

    @SerializedName("data")
    @Expose
    private ResponseCompanyListModel data;


}
