package com.example.gbsbadrsf.Quality.Data;

import com.example.gbsbadrsf.Model.ManufacturingDefect;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponseGetRandomQualityInception {

    @SerializedName("responseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("lastMoveManufacturingMachine_Online")
    @Expose
    private List<LastMoveManufacturingMachineOnline> lastMoveManufacturingMachineOnline = null;
    @SerializedName("manufacturingDefects")
    @Expose
    private List<ManufacturingDefect> manufacturingDefects = null;
    @SerializedName("minQtyApproved")
    @Expose
    private Integer minQtyApproved;
    @SerializedName("totalMachineQty")
    @Expose
    private Integer totalMachineQty;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<LastMoveManufacturingMachineOnline> getLastMoveManufacturingMachineOnline() {
        return lastMoveManufacturingMachineOnline;
    }

    public void setLastMoveManufacturingMachineOnline(List<LastMoveManufacturingMachineOnline> lastMoveManufacturingMachineOnline) {
        this.lastMoveManufacturingMachineOnline = lastMoveManufacturingMachineOnline;
    }

    public List<ManufacturingDefect> getManufacturingDefects() {
        return manufacturingDefects;
    }

    public void setManufacturingDefects(List<ManufacturingDefect> manufacturingDefects) {
        this.manufacturingDefects = manufacturingDefects;
    }

    public Integer getMinQtyApproved() {
        return minQtyApproved;
    }

    public void setMinQtyApproved(Integer minQtyApproved) {
        this.minQtyApproved = minQtyApproved;
    }

    public Integer getTotalMachineQty() {
        return totalMachineQty;
    }

    public void setTotalMachineQty(Integer totalMachineQty) {
        this.totalMachineQty = totalMachineQty;
    }
}
