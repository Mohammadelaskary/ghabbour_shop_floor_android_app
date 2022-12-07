package com.example.gbsbadrsf.data.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MachineLoading  {
    @SerializedName("ProductionSequenceNo")
    @Expose
    private int ProductionSequenceNo;
    @SerializedName("childCode")
    @Expose
    private String childCode;
    @SerializedName("childDescription")
    @Expose
    private String childDescription;
    @SerializedName("jobOrderName")
    @Expose
    private String jobOrderName;
    @SerializedName("loadingQty")
    @Expose
    private Integer loadingQty;
    @SerializedName("jobOrderQty")
    @Expose
    private Integer jobOrderQty;
    @SerializedName("operationEnName")
    @Expose
    private String operationEnName;

    @SerializedName("jobOrderId")
    @Expose
    private String jobOrderId;
    @SerializedName("childId")
    @Expose
    private String childId;
    @SerializedName("operationId")
    @Expose
    private String operationId;
    @SerializedName("accumulatedSignOutQty")
    @Expose
    private Integer signedOffQty;
    private Integer remainingQty;

    public String getChildCode() {
        return childCode;
    }

    public void setChildCode(String childCode) {
        this.childCode = childCode;
    }

    public String getChildDescription() {
        return childDescription;
    }

    public void setChildDescription(String childDescription) {
        this.childDescription = childDescription;
    }

    public String getJobOrderName() {
        return jobOrderName;
    }

    public void setJobOrderName(String jobOrderName) {
        this.jobOrderName = jobOrderName;
    }

    public Integer getLoadingQty() {
        return loadingQty;
    }

    public void setLoadingQty(Integer loadingQty) {
        this.loadingQty = loadingQty;
    }

    public String getOperationEnName() {
        return operationEnName;
    }

    public void setOperationEnName(String operationEnName) {
        this.operationEnName = operationEnName;
    }

    public Integer getJobOrderQty() {
        return jobOrderQty;
    }

    public void setJobOrderQty(Integer jobOrderQty) {
        this.jobOrderQty = jobOrderQty;
    }

    public String getJobOrderId() {
        return jobOrderId;
    }

    public void setJobOrderId(String jobOrderId) {
        this.jobOrderId = jobOrderId;
    }

    public String getChildId() {
        return childId;
    }

    public void setChildId(String childId) {
        this.childId = childId;
    }

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public int getProductionSequenceNo() {
        return ProductionSequenceNo;
    }

    public void setProductionSequenceNo(int productionSequenceNo) {
        ProductionSequenceNo = productionSequenceNo;
    }

    public Integer getSignedOffQty() {
        return signedOffQty;
    }

    public void setSignedOffQty(Integer signedOffQty) {
        this.signedOffQty = signedOffQty;
    }

    public Integer getRemainingQty() {
        return loadingQty-signedOffQty;
    }
}
