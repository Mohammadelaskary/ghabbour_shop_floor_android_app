package com.example.gbsbadrsf.Stoppages.Model.ApiResponse;

import com.example.gbsbadrsf.Stoppages.Model.StationDetails;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiResponseGetStationDetails {

    @SerializedName("responseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("machineDetails")
    @Expose
    private StationDetails machineDetails;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public StationDetails getMachineDetails() {
        return machineDetails;
    }

    public void setMachineDetails(StationDetails machineDetails) {
        this.machineDetails = machineDetails;
    }
}
