package com.example.gbsbadrsf.Stoppages.Model.ApiResponse;

import com.example.gbsbadrsf.Stoppages.Model.MachineFamily;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponseGetMachineFamilyList {
    @SerializedName("responseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("machineFamily")
    @Expose
    private List<MachineFamily> machineFamily = null;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<MachineFamily> getMachineFamily() {
        return machineFamily;
    }

    public void setMachineFamily(List<MachineFamily> machineFamily) {
        this.machineFamily = machineFamily;
    }
}
