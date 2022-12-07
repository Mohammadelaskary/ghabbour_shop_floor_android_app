package com.example.gbsbadrsf.Stoppages.Model.ApiResponse;

import com.example.gbsbadrsf.Stoppages.Model.Stoppage;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiResponseAddStoppage {

    @SerializedName("responseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("stoppage")
    @Expose
    private Stoppage stoppage;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public Stoppage getStoppage() {
        return stoppage;
    }

    public void setStoppage(Stoppage stoppage) {
        this.stoppage = stoppage;
    }

}
