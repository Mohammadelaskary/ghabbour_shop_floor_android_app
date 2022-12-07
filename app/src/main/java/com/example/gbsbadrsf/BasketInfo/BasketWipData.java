package com.example.gbsbadrsf.BasketInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BasketWipData {

    @SerializedName("basketLocation")
    @Expose
    private String basketLocation;
    @SerializedName("basketCode")
    @Expose
    private String basketCode;
    @SerializedName("isInKanban")
    @Expose
    private Boolean isInKanban;
    @SerializedName("isBasketIssued")
    @Expose
    private Boolean isBasketIssued;
    @SerializedName("salesPlanName")
    @Expose
    private String salesPlanName;
    @SerializedName("jobOrderName")
    @Expose
    private String jobOrderName;
    @SerializedName("parentCode")
    @Expose
    private String parentCode;
    @SerializedName("parentDescription")
    @Expose
    private String parentDescription;
    @SerializedName("childCode")
    @Expose
    private Object childCode;
    @SerializedName("childDescription")
    @Expose
    private String childDescription;
    @SerializedName("jobOrderQty")
    @Expose
    private Integer jobOrderQty;
    @SerializedName("pprLoadingQty")
    @Expose
    private Integer pprLoadingQty;
    @SerializedName("basketQty")
    @Expose
    private Integer basketQty;
    @SerializedName("isBulkQty")
    @Expose
    private Boolean isBulkQty;
    @SerializedName("operationEnName")
    @Expose
    private String operationEnName;
    @SerializedName("machineCode")
    @Expose
    private String machineCode;
    @SerializedName("machineEnName")
    @Expose
    private String machineEnName;
    @SerializedName("dieCode")
    @Expose
    private String dieCode;
    @SerializedName("dieEnName")
    @Expose
    private Object dieEnName;
    @SerializedName("dateSignIn")
    @Expose
    private String dateSignIn;
    @SerializedName("operationTime")
    @Expose
    private Integer operationTime;
    @SerializedName("dateSignOut")
    @Expose
    private String dateSignOut;
    @SerializedName("signOutQty")
    @Expose
    private Integer signOutQty;
    @SerializedName("relatedBaskets")
    @Expose
    private List<String> relatedBaskets = null;

    public Boolean getInKanban() {
        return isInKanban;
    }

    public void setInKanban(Boolean inKanban) {
        isInKanban = inKanban;
    }

    public Boolean getBasketIssued() {
        return isBasketIssued;
    }

    public void setBasketIssued(Boolean basketIssued) {
        isBasketIssued = basketIssued;
    }

    public Boolean getBulkQty() {
        return isBulkQty;
    }

    public void setBulkQty(Boolean bulkQty) {
        isBulkQty = bulkQty;
    }

    public List<String> getRelatedBaskets() {
        return relatedBaskets;
    }

    public void setRelatedBaskets(List<String> relatedBaskets) {
        this.relatedBaskets = relatedBaskets;
    }

    public String getBasketLocation() {
        return basketLocation;
    }

    public void setBasketLocation(String basketLocation) {
        this.basketLocation = basketLocation;
    }

    public String getBasketCode() {
        return basketCode;
    }

    public void setBasketCode(String basketCode) {
        this.basketCode = basketCode;
    }

    public Boolean getIsInKanban() {
        return isInKanban;
    }

    public void setIsInKanban(Boolean isInKanban) {
        this.isInKanban = isInKanban;
    }

    public Boolean getIsBasketIssued() {
        return isBasketIssued;
    }

    public void setIsBasketIssued(Boolean isBasketIssued) {
        this.isBasketIssued = isBasketIssued;
    }

    public String getSalesPlanName() {
        return salesPlanName;
    }

    public void setSalesPlanName(String salesPlanName) {
        this.salesPlanName = salesPlanName;
    }

    public String getJobOrderName() {
        return jobOrderName;
    }

    public void setJobOrderName(String jobOrderName) {
        this.jobOrderName = jobOrderName;
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

    public Object getChildCode() {
        return childCode;
    }

    public void setChildCode(Object childCode) {
        this.childCode = childCode;
    }

    public String getChildDescription() {
        return childDescription;
    }

    public void setChildDescription(String childDescription) {
        this.childDescription = childDescription;
    }

    public Integer getJobOrderQty() {
        return jobOrderQty;
    }

    public void setJobOrderQty(Integer jobOrderQty) {
        this.jobOrderQty = jobOrderQty;
    }

    public Integer getPprLoadingQty() {
        return pprLoadingQty;
    }

    public void setPprLoadingQty(Integer pprLoadingQty) {
        this.pprLoadingQty = pprLoadingQty;
    }

    public Integer getBasketQty() {
        return basketQty;
    }

    public void setBasketQty(Integer basketQty) {
        this.basketQty = basketQty;
    }

    public Boolean getIsBulkQty() {
        return isBulkQty;
    }

    public void setIsBulkQty(Boolean isBulkQty) {
        this.isBulkQty = isBulkQty;
    }

    public String getOperationEnName() {
        return operationEnName;
    }

    public void setOperationEnName(String operationEnName) {
        this.operationEnName = operationEnName;
    }

    public String getMachineCode() {
        return machineCode;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }

    public String getMachineEnName() {
        return machineEnName;
    }

    public void setMachineEnName(String machineEnName) {
        this.machineEnName = machineEnName;
    }

    public String getDieCode() {
        return dieCode;
    }

    public void setDieCode(String dieCode) {
        this.dieCode = dieCode;
    }

    public Object getDieEnName() {
        return dieEnName;
    }

    public void setDieEnName(Object dieEnName) {
        this.dieEnName = dieEnName;
    }

    public String getDateSignIn() {
        return dateSignIn;
    }

    public void setDateSignIn(String dateSignIn) {
        this.dateSignIn = dateSignIn;
    }

    public Integer getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Integer operationTime) {
        this.operationTime = operationTime;
    }

    public String getDateSignOut() {
        return dateSignOut;
    }

    public void setDateSignOut(String dateSignOut) {
        this.dateSignOut = dateSignOut;
    }

    public Integer getSignOutQty() {
        return signOutQty;
    }

    public void setSignOutQty(Integer signOutQty) {
        this.signOutQty = signOutQty;
    }

}
