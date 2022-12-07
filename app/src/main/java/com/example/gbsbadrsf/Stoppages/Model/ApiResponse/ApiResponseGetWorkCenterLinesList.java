package com.example.gbsbadrsf.Stoppages.Model.ApiResponse;

import com.example.gbsbadrsf.Stoppages.Model.WorkCenterLine;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponseGetWorkCenterLinesList {

    @SerializedName("responseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("workCenterLines")
    @Expose
    private List<WorkCenterLine> workCenterLines = null;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<WorkCenterLine> getWorkCenterLines() {
        return workCenterLines;
    }

    public void setWorkCenterLines(List<WorkCenterLine> workCenterLines) {
        this.workCenterLines = workCenterLines;
    }
}
