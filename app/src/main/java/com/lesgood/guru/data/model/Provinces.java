package com.lesgood.guru.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Agus on 3/9/17.
 */

public class Provinces {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("content")
    @Expose
    private List<Province> content = null;
    @SerializedName("message")
    @Expose
    private MessageResponse message;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Province> getContent() {
        return content;
    }

    public void setContent(List<Province> content) {
        this.content = content;
    }

    public MessageResponse getMessage() {
        return message;
    }

    public void setMessage(MessageResponse message) {
        this.message = message;
    }
}
