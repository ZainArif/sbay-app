package com.sbay.mrz.sbay;

import com.google.gson.annotations.SerializedName;

public class PasswordResetResponse {

    @SerializedName("resStatus")
    private String responseStatus;

    @SerializedName("email")
    private String email;

    public PasswordResetResponse(String email) {
        this.email = email;
    }

    public String getResponseStatus() {
        return responseStatus;
    }
}
