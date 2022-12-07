package com.example.gbsbadrsf.Manfacturing.machinesignoff.MachineStop;

import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiResponseTransferMachineLoading {
    @SerializedName("responseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("getMachineLoadingData")
    @Expose
    private GetMachineLoadingData getMachineLoadingData;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public GetMachineLoadingData getGetMachineLoadingData() {
        return getMachineLoadingData;
    }

    public void setGetMachineLoadingData(GetMachineLoadingData getMachineLoadingData) {
        this.getMachineLoadingData = getMachineLoadingData;
    }
}
