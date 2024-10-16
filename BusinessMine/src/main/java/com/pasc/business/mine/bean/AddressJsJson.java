package com.pasc.business.mine.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AddressJsJson implements Serializable {
    @SerializedName("success")
    private String success = "";

    @SerializedName("type")
    private String type = ""; //0代表收件人，1代表寄件人

    @SerializedName("requestId")
    private String requestId = "";

    @SerializedName("sendId")
    private String sendId = "";

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getSendId() {
        return sendId;
    }

    public void setSendId(String sendId) {
        this.sendId = sendId;
    }
}
