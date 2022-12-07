package com.example.gbsbadrsf.AddStationWorkers;

import com.example.gbsbadrsf.AddWorkers.Worker;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponseGetStationWorkers {

    @SerializedName("responseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("getStationData")
    @Expose
    private StationData stationParam;
    @SerializedName("getWorkerTransactions")
    @Expose
    private List<Worker> getWorkerTransactions = null;
    @SerializedName("parentDescription")
    @Expose
    private String parentDescription;
    @SerializedName("jobOrderQty")
    @Expose
    private Integer jobOrderQty;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public StationData getStationParam() {
        return stationParam;
    }

    public void setStationParam(StationData stationParam) {
        this.stationParam = stationParam;
    }

    public List<Worker> getGetWorkerTransactions() {
        return getWorkerTransactions;
    }

    public void setGetWorkerTransactions(List<Worker> getWorkerTransactions) {
        this.getWorkerTransactions = getWorkerTransactions;
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
}
