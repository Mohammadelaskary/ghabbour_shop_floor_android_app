package com.example.gbsbadrsf.data.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiPaintstation<T>{

    @SerializedName("responseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("pprList")
    @Expose
    private List<Pprpaint> pprList = null;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<Pprpaint> getPprList() {
        return pprList;
    }

    public void setPprList(List<Pprpaint> pprList) {
        this.pprList = pprList;
    }
}
