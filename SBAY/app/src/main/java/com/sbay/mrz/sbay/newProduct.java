package com.sbay.mrz.sbay;

import com.google.gson.annotations.SerializedName;

public class newProduct {

    @SerializedName("cusNewReqId")
    private String custId;

    @SerializedName("newSoftwareDescription")
    private String softwareDesc;

    @SerializedName("category")
    private String category;

    public newProduct(String custId, String softwareDesc, String category) {
        this.custId = custId;
        this.softwareDesc = softwareDesc;
        this.category = category;
    }

    public String getCustId() {
        return custId;
    }

    public String getSoftwareDesc() {
        return softwareDesc;
    }

    public String getCategory() {
        return category;
    }
}
