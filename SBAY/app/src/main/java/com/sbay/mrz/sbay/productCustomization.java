package com.sbay.mrz.sbay;

import com.google.gson.annotations.SerializedName;

public class productCustomization {

    @SerializedName("cusCuzReqId")
    private String custId;

    @SerializedName("productId")
    private String productId;

    @SerializedName("cuzDescription")
    private String productCustDesc;

    public productCustomization(String custId, String productId, String productCustDesc) {
        this.custId = custId;
        this.productId = productId;
        this.productCustDesc = productCustDesc;
    }

    public String getCustId() {
        return custId;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductCustDesc() {
        return productCustDesc;
    }
}
