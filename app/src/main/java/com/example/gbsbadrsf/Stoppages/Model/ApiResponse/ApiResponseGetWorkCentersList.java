package com.example.gbsbadrsf.Stoppages.Model.ApiResponse;

import com.example.gbsbadrsf.Stoppages.Model.WorkCenter;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponseGetWorkCentersList {

    @SerializedName("responseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("workCenters")
    @Expose
    private List<WorkCenter> workCenters = null;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<WorkCenter> getWorkCenters() {
        return workCenters;
    }

    public void setWorkCenters(List<WorkCenter> workCenters) {
        this.workCenters = workCenters;
    }
}
