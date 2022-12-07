package com.example.gbsbadrsf.Quality.paint.Model;

import com.example.gbsbadrsf.Model.BasketData;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiResponseFullInspection_Painting {
    @SerializedName("responseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("getPPRData")
    @Expose
    private GetPPRData getPPRData;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public GetPPRData getGetPPRData() {
        return getPPRData;
    }

    public void setGetPPRData(GetPPRData getPPRData) {
        this.getPPRData = getPPRData;
    }
}
