package com.example.gbsbadrsf.Model;

import com.example.gbsbadrsf.Quality.Basket;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponseLastMoveManufacturingBasket {

    @SerializedName("responseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("lastMoveManufacturingBaskets")
    @Expose
    private List<LastMoveManufacturingBasket> lastMoveManufacturingBaskets = null;
    @SerializedName("manufacturingDefects")
    @Expose
    private List<ManufacturingDefect> manufacturingDefects = null;
    @SerializedName("minQtyApproved")
    @Expose
    private Integer minQtyApproved;
    @SerializedName("relatedBaskets")
    @Expose
    private List<Basket> relatedBaskets = null;


    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<LastMoveManufacturingBasket> getLastMoveManufacturingBaskets() {
        return lastMoveManufacturingBaskets;
    }

    public void setLastMoveManufacturingBaskets(List<LastMoveManufacturingBasket> lastMoveManufacturingBaskets) {
        this.lastMoveManufacturingBaskets = lastMoveManufacturingBaskets;
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

    public List<Basket> getRelatedBaskets() {
        return relatedBaskets;
    }

    public void setRelatedBaskets(List<Basket> relatedBaskets) {
        this.relatedBaskets = relatedBaskets;
    }

}
