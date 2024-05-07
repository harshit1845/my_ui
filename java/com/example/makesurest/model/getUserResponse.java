package com.example.makesurest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class getUserResponse {

    @SerializedName("Message")
    @Expose
    private String message;


    @SerializedName("security_Question")
    @Expose
    private String securityQuestion;

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }
}
