package com.example.gbsbadrsf.Manfacturing.machineloading;

import com.example.gbsbadrsf.BasketInfo.BasketWipData;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiResponseGetManufacturingBasketWIP {

    @SerializedName("responseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("basketWipData")
    @Expose
    private BasketWipData basketWipData;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public BasketWipData getBasketWipData() {
        return basketWipData;
    }

    public void setBasketWipData(BasketWipData basketWipData) {
        this.basketWipData = basketWipData;
    }

}
