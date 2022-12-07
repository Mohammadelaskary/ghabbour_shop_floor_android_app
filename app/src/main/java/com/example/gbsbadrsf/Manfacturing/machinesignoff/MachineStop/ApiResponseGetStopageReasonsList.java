package com.example.gbsbadrsf.Manfacturing.machinesignoff.MachineStop;

import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponseGetStopageReasonsList {
    @SerializedName("responseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("stoppagesReasonsList")
    @Expose
    private List<StopReason> stoppagesReasonsList = null;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<StopReason> getStoppagesReasonsList() {
        return stoppagesReasonsList;
    }

    public void setStoppagesReasonsList(List<StopReason> stoppagesReasonsList) {
        this.stoppagesReasonsList = stoppagesReasonsList;
    }
}
