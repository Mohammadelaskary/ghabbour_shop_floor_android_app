package com.example.gbsbadrsf.Stoppages.Model.ApiResponse;

import com.example.gbsbadrsf.Stoppages.Model.WorkCenterSubLine;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponseGetWorkCenterSubLinesList {

    @SerializedName("responseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("workCenterSubLines")
    @Expose
    private List<WorkCenterSubLine> workCenterSubLines = null;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<WorkCenterSubLine> getWorkCenterSubLines() {
        return workCenterSubLines;
    }

    public void setWorkCenterSubLines(List<WorkCenterSubLine> workCenterSubLines) {
        this.workCenterSubLines = workCenterSubLines;
    }
}
