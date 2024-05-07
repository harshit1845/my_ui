package com.example.makesurest.model;

import com.google.gson.annotations.SerializedName;

public class DispatchNumberResponce {
    @SerializedName("Message")
    private String message;

    @SerializedName("dispatch_id")
    private String dispatchId;

    @SerializedName("dispatch_number")
    private String dispatchNumber;

    @SerializedName("description")
    private String description;

    @SerializedName("date")
    private String date;

    @SerializedName("status")
    private String status;

    @SerializedName("Qty")
    private String qyt ;

    @SerializedName("scn_Qty")
    private String scanQyt;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDispatchId() {
        return dispatchId;
    }

    public void setDispatchId(String dispatchId) {
        this.dispatchId = dispatchId;
    }

    public String getDispatchNumber() {
        return dispatchNumber;
    }

    public void setDispatchNumber(String dispatchNumber) {
        this.dispatchNumber = dispatchNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getQyt() {
        return qyt;
    }

    public void setQyt(String qyt) {
        this.qyt = qyt;
    }

    public String getScanQyt() {
        return scanQyt;
    }

    public void setScanQyt(String scanQyt) {
        this.scanQyt = scanQyt;
    }
}
