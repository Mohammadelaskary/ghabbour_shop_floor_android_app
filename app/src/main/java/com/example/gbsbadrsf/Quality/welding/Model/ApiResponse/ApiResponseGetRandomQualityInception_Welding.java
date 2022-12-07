package com.example.gbsbadrsf.Quality.welding.Model.ApiResponse;

import com.example.gbsbadrsf.Quality.welding.Model.LastMoveWeldingBasket;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponseGetRandomQualityInception_Welding {

    @SerializedName("responseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("lastMoveWeldingBaskets")
    @Expose
    private List<LastMoveWeldingBasket> lastMoveWeldingBaskets = null;
    @SerializedName("weldingDefects")
    @Expose
    private List<Object> weldingDefects = null;
    @SerializedName("minQtyApproved")
    @Expose
    private Integer minQtyApproved;
    @SerializedName("totalStationQty")
    @Expose
    private Integer totalStationQty;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(
            ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<LastMoveWeldingBasket> getLastMoveWeldingBaskets() {
        return lastMoveWeldingBaskets;
    }

    public void setLastMoveWeldingBaskets(List<LastMoveWeldingBasket> lastMoveWeldingBaskets) {
        this.lastMoveWeldingBaskets = lastMoveWeldingBaskets;
    }

    public List<Object> getWeldingDefects() {
        return weldingDefects;
    }

    public void setWeldingDefects(List<Object> weldingDefects) {
        this.weldingDefects = weldingDefects;
    }

    public Integer getMinQtyApproved() {
        return minQtyApproved;
    }

    public void setMinQtyApproved(Integer minQtyApproved) {
        this.minQtyApproved = minQtyApproved;
    }

    public Integer getTotalStationQty() {
        return totalStationQty;
    }

    public void setTotalStationQty(Integer totalStationQty) {
        this.totalStationQty = totalStationQty;
    }
}
