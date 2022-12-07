package com.example.gbsbadrsf.Quality.paint.PprListQualityOperation;

import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponseGetPaintQualityPprList {

    @SerializedName("responseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("pprList")
    @Expose
    private List<Ppr> pprList = null;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<Ppr> getPprList() {
        return pprList;
    }

    public void setPprList(List<Ppr> pprList) {
        this.pprList = pprList;
    }
}
