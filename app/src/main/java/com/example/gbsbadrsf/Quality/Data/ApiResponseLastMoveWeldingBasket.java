package com.example.gbsbadrsf.Quality.Data;

import com.example.gbsbadrsf.Model.LastMoveManufacturingBasket;
import com.example.gbsbadrsf.Quality.Basket;
import com.example.gbsbadrsf.Quality.welding.Model.LastMoveWeldingBasket;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponseLastMoveWeldingBasket {
    @SerializedName("responseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("lastMoveWeldingBaskets")
    @Expose
    private List<LastMoveWeldingBasket> lastMoveWeldingBaskets = null;
    @SerializedName("weldingDefects")
    @Expose
    private List<WeldingDefect> weldingDefects = null;
    @SerializedName("minQtyApproved")
    @Expose
    private Integer minQtyApproved;
    @SerializedName("relatedBaskets")
    @Expose
    private List<Basket> relatedBaskets = null;

    public List<Basket> getRelatedBaskets() {
        return relatedBaskets;
    }

    public void setRelatedBaskets(List<Basket> relatedBaskets) {
        this.relatedBaskets = relatedBaskets;
    }
    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<LastMoveWeldingBasket> getLastMoveWeldingBaskets() {
        return lastMoveWeldingBaskets;
    }

    public void setLastMoveWeldingBaskets(List<LastMoveWeldingBasket> lastMoveWeldingBaskets) {
        this.lastMoveWeldingBaskets = lastMoveWeldingBaskets;
    }

    public List<WeldingDefect> getWeldingDefects() {
        return weldingDefects;
    }

    public void setWeldingDefects(List<WeldingDefect> weldingDefects) {
        this.weldingDefects = weldingDefects;
    }

    public Integer getMinQtyApproved() {
        return minQtyApproved;
    }

    public void setMinQtyApproved(Integer minQtyApproved) {
        this.minQtyApproved = minQtyApproved;
    }
}
