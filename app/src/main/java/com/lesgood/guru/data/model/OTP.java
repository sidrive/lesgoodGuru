package com.lesgood.guru.data.model;

/**
 * Created by Agus on 2/9/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OTP {

    @SerializedName("request_id")
    @Expose
    private String requestId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("error_text")
    @Expose
    private String errorText;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorText() {
        return errorText;
    }

    public void setErrorText(String errorText) {
        this.errorText = errorText;
    }
}