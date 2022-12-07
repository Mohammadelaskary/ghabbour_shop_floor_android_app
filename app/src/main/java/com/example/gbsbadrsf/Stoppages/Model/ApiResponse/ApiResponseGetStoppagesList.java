package com.example.gbsbadrsf.Stoppages.Model.ApiResponse;

import com.example.gbsbadrsf.Stoppages.Model.Stoppage;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponseGetStoppagesList {

    @SerializedName("responseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("stoppages")
    @Expose
    private List<Stoppage> stoppages = null;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<Stoppage> getStoppages() {
        return stoppages;
    }

    public void setStoppages(List<Stoppage> stoppages) {
        this.stoppages = stoppages;
    }
}
