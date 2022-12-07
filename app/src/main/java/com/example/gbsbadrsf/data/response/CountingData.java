package com.example.gbsbadrsf.data.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CountingData {

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

}




