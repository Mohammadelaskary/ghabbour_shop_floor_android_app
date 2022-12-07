package com.example.gbsbadrsf.data.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CountingDataRecivingdata {

    @SerializedName("jobOrderID")
    @Expose
    private Integer jobOrderID;
    @SerializedName("jobOrderName")
    @Expose
    private String jobOrderName;
    @SerializedName("jobOrderDate")
    @Expose
    private String jobOrderDate;
    @SerializedName("parentID")
    @Expose
    private Integer parentID;
    @SerializedName("parentCode")
    @Expose
    private String parentCode;
    @SerializedName("parentDescription")
    @Expose
    private String parentDescription;
    @SerializedName("jobOrderQty")
    @Expose
    private Integer jobOrderQty;
    @SerializedName("totalHandlingSignOutQty")
    @Expose
    private Integer totalHandlingSignOutQty;
    @SerializedName("basketSignOutQty")
    @Expose
    private Integer basketSignOutQty;
    @SerializedName("countingQty")
    @Expose
    private Integer countingQty;
    @SerializedName("receivingQty")
    @Expose
    private Integer receivingQty;
    @SerializedName("totalReceivingQty")
    @Expose
    private Integer totalReceivingQty;
    @SerializedName("availableReceiving")
    @Expose
    private Integer availableReceiving;
    @SerializedName("subInventoryId")
    @Expose
    private Integer subInventoryId;
    @SerializedName("subInventoryCode")
    @Expose
    private String subInventoryCode;
    @SerializedName("subInventoryDesc")
    @Expose
    private String subInventoryDesc;
    @SerializedName("locatorId")
    @Expose
    private Integer locatorId;
    @SerializedName("locatorCode")
    @Expose
    private String locatorCode;
    @SerializedName("locatorDesc")
    @Expose
    private String locatorDesc;

    public Integer getJobOrderID() {
        return jobOrderID;
    }

    public void setJobOrderID(Integer jobOrderID) {
        this.jobOrderID = jobOrderID;
    }

    public String getJobOrderName() {
        return jobOrderName;
    }

    public void setJobOrderName(String jobOrderName) {
        this.jobOrderName = jobOrderName;
    }

    public String getJobOrderDate() {
        return jobOrderDate;
    }

    public void setJobOrderDate(String jobOrderDate) {
        this.jobOrderDate = jobOrderDate;
    }

    public Integer getParentID() {
        return parentID;
    }

    public void setParentID(Integer parentID) {
        this.parentID = parentID;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getParentDescription() {
        return parentDescription;
    }

    public void setParentDescription(String parentDescription) {
        this.parentDescription = parentDescription;
    }

    public Integer getJobOrderQty() {
        return jobOrderQty;
    }

    public void setJobOrderQty(Integer jobOrderQty) {
        this.jobOrderQty = jobOrderQty;
    }

    public Integer getTotalHandlingSignOutQty() {
        return totalHandlingSignOutQty;
    }

    public void setTotalHandlingSignOutQty(Integer totalHandlingSignOutQty) {
        this.totalHandlingSignOutQty = totalHandlingSignOutQty;
    }

    public Integer getBasketSignOutQty() {
        return basketSignOutQty;
    }

    public void setBasketSignOutQty(Integer basketSignOutQty) {
        this.basketSignOutQty = basketSignOutQty;
    }

    public Integer getCountingQty() {
        return countingQty;
    }

    public void setCountingQty(Integer countingQty) {
        this.countingQty = countingQty;
    }

    public Integer getReceivingQty() {
        return receivingQty;
    }

    public void setReceivingQty(Integer receivingQty) {
        this.receivingQty = receivingQty;
    }

    public Integer getTotalReceivingQty() {
        return totalReceivingQty;
    }

    public void setTotalReceivingQty(Integer totalReceivingQty) {
        this.totalReceivingQty = totalReceivingQty;
    }

    public Integer getAvailableReceiving() {
        return availableReceiving;
    }

    public void setAvailableReceiving(Integer availableReceiving) {
        this.availableReceiving = availableReceiving;
    }

    public Integer getSubInventoryId() {
        return subInventoryId;
    }

    public void setSubInventoryId(Integer subInventoryId) {
        this.subInventoryId = subInventoryId;
    }

    public String getSubInventoryCode() {
        return subInventoryCode;
    }

    public void setSubInventoryCode(String subInventoryCode) {
        this.subInventoryCode = subInventoryCode;
    }

    public String getSubInventoryDesc() {
        return subInventoryDesc;
    }

    public void setSubInventoryDesc(String subInventoryDesc) {
        this.subInventoryDesc = subInventoryDesc;
    }

    public Integer getLocatorId() {
        return locatorId;
    }

    public void setLocatorId(Integer locatorId) {
        this.locatorId = locatorId;
    }

    public String getLocatorCode() {
        return locatorCode;
    }

    public void setLocatorCode(String locatorCode) {
        this.locatorCode = locatorCode;
    }

    public String getLocatorDesc() {
        return locatorDesc;
    }

    public void setLocatorDesc(String locatorDesc) {
        this.locatorDesc = locatorDesc;
    }

}
