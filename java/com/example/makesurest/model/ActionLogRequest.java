package com.example.makesurest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ActionLogRequest {
    @SerializedName("action_type")
    @Expose
    private String actiontype;
    @SerializedName("action")
    @Expose
    private String action;

    @SerializedName("user_name")
    @Expose
    private String username;
    @SerializedName("date_time")
    @Expose
    private String datetime;

    @SerializedName("flag")
    @Expose
    private String flag;

    public String getActiontype() {
        return actiontype;
    }

    public void setActiontype(String actiontype) {
        this.actiontype = actiontype;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
