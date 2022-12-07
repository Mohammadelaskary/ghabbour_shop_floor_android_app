package com.example.gbsbadrsf.AddWorkers;

import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponseGetMachineWorkers {

    @SerializedName("responseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("getMachineData")
    @Expose
    private MachineData getMachineData;
    @SerializedName("getWorkerTransactions")
    @Expose
    private List<Worker> getWorkerTransactions = null;
    @SerializedName("jobOrderQty")
    @Expose
    private Integer jobOrderQty;
    @SerializedName("childDescription")
    @Expose
    private String childDescription;

    public MachineData getGetMachineData() {
        return getMachineData;
    }

    public void setGetMachineData(MachineData getMachineData) {
        this.getMachineData = getMachineData;
    }

    public Integer getJobOrderQty() {
        return jobOrderQty;
    }

    public void setJobOrderQty(Integer jobOrderQty) {
        this.jobOrderQty = jobOrderQty;
    }

    public String getChildDescription() {
        return childDescription;
    }

    public void setChildDescription(String childDescription) {
        this.childDescription = childDescription;
    }

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public MachineData getMachineData() {
        return getMachineData;
    }

    public void setMachineData(MachineData getMachineData) {
        this.getMachineData = getMachineData;
    }

    public List<Worker> getGetWorkerTransactions() {
        return getWorkerTransactions;
    }

    public void setGetWorkerTransactions(List<Worker> getWorkerTransactions) {
        this.getWorkerTransactions = getWorkerTransactions;
    }
}
