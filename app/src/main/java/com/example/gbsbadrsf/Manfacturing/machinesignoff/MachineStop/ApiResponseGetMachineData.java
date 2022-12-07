package com.example.gbsbadrsf.Manfacturing.machinesignoff.MachineStop;

import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponseGetMachineData {

    @SerializedName("responseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("machineData")
    @Expose
    private MachineData machineData;
    @SerializedName("relatedMachines")
    @Expose
    private List<String> relatedMachines = null;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public MachineData getMachineData() {
        return machineData;
    }

    public void setMachineData(MachineData machineData) {
        this.machineData = machineData;
    }

    public List<String> getRelatedMachines() {
        return relatedMachines;
    }

    public void setRelatedMachines(List<String> relatedMachines) {
        this.relatedMachines = relatedMachines;
    }
}
