package com.example.gbsbadrsf.BasketInfo;

import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponseBasketsWIP {

    @SerializedName("responseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("basketWipData")
    @Expose
    private BasketWipData basketWipData;
    @SerializedName("relatedBaskets")
    @Expose
    private List<String> relatedBaskets = null;

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

    public List<String> getRelatedBaskets() {
        return relatedBaskets;
    }

    public void setRelatedBaskets(List<String> relatedBaskets) {
        this.relatedBaskets = relatedBaskets;
    }


}
