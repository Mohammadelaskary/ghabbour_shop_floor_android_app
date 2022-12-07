package com.example.gbsbadrsf.Stoppages.Model.ApiResponse;

import com.example.gbsbadrsf.Stoppages.Model.MachineDetail;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponseGetMachineDetails {

    @SerializedName("responseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("machineDetails")
    @Expose
    private MachineDetail machineDetails;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public MachineDetail getMachineDetails() {
        return machineDetails;
    }

    public void setMachineDetails(MachineDetail machineDetails) {
        this.machineDetails = machineDetails;
    }
}
