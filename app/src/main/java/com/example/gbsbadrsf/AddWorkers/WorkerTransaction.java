package com.example.gbsbadrsf.AddWorkers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WorkerTransaction {

    @SerializedName("workerTransactionId")
    @Expose
    private Integer workerTransactionId;
    @SerializedName("lastMoveId")
    @Expose
    private Integer lastMoveId;
    @SerializedName("childId")
    @Expose
    private Integer childId;
    @SerializedName("childCode")
    @Expose
    private String childCode;
    @SerializedName("jobOrderId")
    @Expose
    private Integer jobOrderId;
    @SerializedName("jobOrderName")
    @Expose
    private String jobOrderName;
    @SerializedName("pprLoadingId")
    @Expose
    private Integer pprLoadingId;
    @SerializedName("operationId")
    @Expose
    private Integer operationId;
    @SerializedName("machineId")
    @Expose
    private Integer machineId;
    @SerializedName("sequenceNo")
    @Expose
    private Integer sequenceNo;
    @SerializedName("workerId")
    @Expose
    private Integer workerId;
    @SerializedName("dateAdd")
    @Expose
    private String dateAdd;
    @SerializedName("userIdAdd")
    @Expose
    private Integer userIdAdd;
    @SerializedName("productionSequenceNo")
    @Expose
    private Integer productionSequenceNo;
    @SerializedName("parentId")
    @Expose
    private Object parentId;
    @SerializedName("parentCode")
    @Expose
    private Object parentCode;
    @SerializedName("stationId")
    @Expose
    private Object stationId;
    @SerializedName("loadingTransactionId")
    @Expose
    private Integer loadingTransactionId;
    private String workerArName;
    private String workerEnName;
    private String workerCode;

    public Integer getWorkerTransactionId() {
        return workerTransactionId;
    }

    public void setWorkerTransactionId(Integer workerTransactionId) {
        this.workerTransactionId = workerTransactionId;
    }

    public Integer getLastMoveId() {
        return lastMoveId;
    }

    public void setLastMoveId(Integer lastMoveId) {
        this.lastMoveId = lastMoveId;
    }

    public Integer getChildId() {
        return childId;
    }

    public void setChildId(Integer childId) {
        this.childId = childId;
    }

    public String getChildCode() {
        return childCode;
    }

    public void setChildCode(String childCode) {
        this.childCode = childCode;
    }

    public Integer getJobOrderId() {
        return jobOrderId;
    }

    public void setJobOrderId(Integer jobOrderId) {
        this.jobOrderId = jobOrderId;
    }

    public String getJobOrderName() {
        return jobOrderName;
    }

    public void setJobOrderName(String jobOrderName) {
        this.jobOrderName = jobOrderName;
    }

    public Integer getPprLoadingId() {
        return pprLoadingId;
    }

    public void setPprLoadingId(Integer pprLoadingId) {
        this.pprLoadingId = pprLoadingId;
    }

    public Integer getOperationId() {
        return operationId;
    }

    public void setOperationId(Integer operationId) {
        this.operationId = operationId;
    }

    public Integer getMachineId() {
        return machineId;
    }

    public void setMachineId(Integer machineId) {
        this.machineId = machineId;
    }

    public Integer getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(Integer sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public Integer getWorkerId() {
        return workerId;
    }

    public void setWorkerId(Integer workerId) {
        this.workerId = workerId;
    }

    public String getDateAdd() {
        return dateAdd;
    }

    public void setDateAdd(String dateAdd) {
        this.dateAdd = dateAdd;
    }

    public Integer getUserIdAdd() {
        return userIdAdd;
    }

    public void setUserIdAdd(Integer userIdAdd) {
        this.userIdAdd = userIdAdd;
    }

    public Integer getProductionSequenceNo() {
        return productionSequenceNo;
    }

    public void setProductionSequenceNo(Integer productionSequenceNo) {
        this.productionSequenceNo = productionSequenceNo;
    }

    public Object getParentId() {
        return parentId;
    }

    public void setParentId(Object parentId) {
        this.parentId = parentId;
    }

    public Object getParentCode() {
        return parentCode;
    }

    public void setParentCode(Object parentCode) {
        this.parentCode = parentCode;
    }

    public Object getStationId() {
        return stationId;
    }

    public void setStationId(Object stationId) {
        this.stationId = stationId;
    }

    public Integer getLoadingTransactionId() {
        return loadingTransactionId;
    }

    public void setLoadingTransactionId(Integer loadingTransactionId) {
        this.loadingTransactionId = loadingTransactionId;
    }

    public String getWorkerArName() {
        return workerArName;
    }

    public void setWorkerArName(String workerArName) {
        this.workerArName = workerArName;
    }

    public String getWorkerEnName() {
        return workerEnName;
    }

    public void setWorkerEnName(String workerEnName) {
        this.workerEnName = workerEnName;
    }

    public String getWorkerCode() {
        return workerCode;
    }

    public void setWorkerCode(String workerCode) {
        this.workerCode = workerCode;
    }
}
