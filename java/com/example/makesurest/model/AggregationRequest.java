package com.example.makesurest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AggregationRequest  {
    @SerializedName("jsonContent")
    @Expose
    private String jsonContent;

    public String getJsonContent() {
        return jsonContent;
    }

    public void setJsonContent(String jsonContent) {
        this.jsonContent = jsonContent;
    }
}
