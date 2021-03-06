package com.sbay.mrz.sbay;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.view.ViewDebug;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "products")
public class softwareDetails {

    @NonNull
    @PrimaryKey
    @SerializedName("_id")
    private String productID;

    @SerializedName("seller_id")
    private String sellerID;

    @SerializedName("pname")
    private String Name;

    @SerializedName("pdescription")
    private String Description;

    @Ignore
    @SerializedName("screenShot")
    private String[] screenShot;

    @SerializedName("exeUrl")
    private String exeURl;

    @SerializedName("demoVideoUrl")
    private String demoVideoURl;

    @SerializedName("hostUrl")
    private String hostURL;

    @SerializedName("cost")
    private String Cost;

    @SerializedName("category")
    private String Category;

    @Ignore
    @SerializedName("screenShotPublicId")
    private String[] screenshotPublicID;

    @SerializedName("delStatus")
    private String delstatus;

    @SerializedName("updateStatus")
    private String updatestatus;

    private String screenshot;

    // This constructor is use for sql lite database
    public softwareDetails(@NonNull String productID, String Name, String Description, String Category, String Cost, String demoVideoURl, String screenshot ) {
        this.productID = productID;
        this.Name = Name;
        this.Description = Description;
        this.Category = Category;
        this.Cost = Cost;
        this.demoVideoURl = demoVideoURl;
        this.screenshot = screenshot;
    }

    // This constructor is use for calling all softwares & software categories
    @Ignore
    public softwareDetails(@NonNull String productID, String Name, String Description, String Category, String Cost, String demoVideoURl, String[] screenShot, String hostURL) {
        this.productID = productID;
        this.Name = Name;
        this.Description = Description;
        this.Category = Category;
        this.Cost = Cost;
        this.demoVideoURl = demoVideoURl;
        this.screenShot = screenShot;
        this.hostURL = hostURL;
    }

    // This constructor is use for post product
    @Ignore
    public softwareDetails(String sellerID, String Name, String Description, String exeURl, String demoVideoURl, String hostURL, String Cost, String Category, String[] screenShot, String[] screenshotPublicID) {
        this.sellerID = sellerID;
        this.Name = Name;
        this.Description = Description;
        this.exeURl = exeURl;
        this.demoVideoURl = demoVideoURl;
        this.hostURL = hostURL;
        this.Cost = Cost;
        this.Category = Category;
        this.screenShot = screenShot;
        this.screenshotPublicID = screenshotPublicID;
    }

    //This constructor is use for sellers my product
    @Ignore
    public softwareDetails(@NonNull String productID, String sellerID, String Name, String Description, String exeURl, String demoVideoURl, String hostURL, String Cost, String Category, String[] screenShot, String[] screenshotPublicID) {
        this.productID = productID;
        this.sellerID = sellerID;
        this.Name = Name;
        this.Description = Description;
        this.exeURl = exeURl;
        this.demoVideoURl = demoVideoURl;
        this.hostURL = hostURL;
        this.Cost = Cost;
        this.Category = Category;
        this.screenShot = screenShot;
        this.screenshotPublicID = screenshotPublicID;
    }

    @NonNull
    public String getProductID() {
        return productID;
    }

    public void setProductID(@NonNull String productID) {
        this.productID = productID;
    }

    public String getSellerID() {
        return sellerID;
    }

    public void setSellerID(String sellerID) {
        this.sellerID = sellerID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getExeURl() {
        return exeURl;
    }

    public void setExeURl(String exeURl) {
        this.exeURl = exeURl;
    }

    public String getDemoVideoURl() {
        return demoVideoURl;
    }

    public void setDemoVideoURl(String demoVideoURl) {
        this.demoVideoURl = demoVideoURl;
    }

    public String getHostURL() {
        return hostURL;
    }

    public void setHostURL(String hostURL) {
        this.hostURL = hostURL;
    }

    public String getCost() {
        return Cost;
    }

    public void setCost(String cost) {
        Cost = cost;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String[] getScreenShot() {
        return screenShot;
    }

    public void setScreenShot(String[] screenShot) {
        this.screenShot = screenShot;
    }

    public String getScreenshot() {
        return screenshot;
    }

    public void setScreenshot(String screenshot) {
        this.screenshot = screenshot;
    }

    public String getDelstatus() {
        return delstatus;
    }

    public void setDelstatus(String delstatus) {
        this.delstatus = delstatus;
    }

    public String getUpdatestatus() {
        return updatestatus;
    }

    public void setUpdatestatus(String updatestatus) {
        this.updatestatus = updatestatus;
    }

    public String[] getScreenshotPublicID() {
        return screenshotPublicID;
    }

    public void setScreenshotPublicID(String[] screenshotPublicID) {
        this.screenshotPublicID = screenshotPublicID;
    }
}