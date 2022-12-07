package com.example.gbsbadrsf.Stoppages.Model.ApiResponse;

import com.example.gbsbadrsf.Stoppages.Model.StoppageName;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponseGetNameOfStoppagesList {
    @SerializedName("responseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("stoppagesNames")
    @Expose
    private List<StoppageName> stoppagesNames = null;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<StoppageName> getStoppagesNames() {
        return stoppagesNames;
    }

    public void setStoppagesNames(List<StoppageName> stoppagesNames) {
        this.stoppagesNames = stoppagesNames;
    }

}
