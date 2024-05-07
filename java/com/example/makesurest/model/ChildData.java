package com.example.makesurest.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class ChildData implements Parcelable {
    @SerializedName("child_serial_no")
    private String childSerialNo;

    @SerializedName("child_product_name")
    private String childProductName;

    @SerializedName("batch_no")
    private String childBatchNumber;

    @SerializedName("exp_date")
    private String childExpDate;

    @SerializedName("gtin_no")
    private String childGtinNo;

    @SerializedName("packaging_level")
    private String childPackagingLevel;

    // Constructor, getters, setters

    // Parcelable implementation
    protected ChildData(Parcel in) {
        childSerialNo = in.readString();
        childProductName = in.readString();
        childBatchNumber = in.readString();
        childExpDate = in.readString();
        childGtinNo = in.readString();
        childPackagingLevel = in.readString();
    }

    public static final Creator<ChildData> CREATOR = new Creator<ChildData>() {
        @Override
        public ChildData createFromParcel(Parcel in) {
            return new ChildData(in);
        }

        @Override
        public ChildData[] newArray(int size) {
            return new ChildData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(childSerialNo);
        dest.writeString(childProductName);
        dest.writeString(childBatchNumber);
        dest.writeString(childExpDate);
        dest.writeString(childGtinNo);
        dest.writeString(childPackagingLevel);
    }

    public String getChildSerialNo() {
        return childSerialNo;
    }

    public void setChildSerialNo(String childSerialNo) {
        this.childSerialNo = childSerialNo;
    }

    public String getChildProductName() {
        return childProductName;
    }

    public void setChildProductName(String childProductName) {
        this.childProductName = childProductName;
    }

    public String getChildBatchNumber() {
        return childBatchNumber;
    }

    public void setChildBatchNumber(String childBatchNumber) {
        this.childBatchNumber = childBatchNumber;
    }

    public String getChildExpDate() {
        return childExpDate;
    }

    public void setChildExpDate(String childExpDate) {
        this.childExpDate = childExpDate;
    }

    public String getChildGtinNo() {
        return childGtinNo;
    }

    public void setChildGtinNo(String childGtinNo) {
        this.childGtinNo = childGtinNo;
    }

    public String getChildPackagingLevel() {
        return childPackagingLevel;
    }

    public void setChildPackagingLevel(String childPackagingLevel) {
        this.childPackagingLevel = childPackagingLevel;
    }
// Getters and setters
}