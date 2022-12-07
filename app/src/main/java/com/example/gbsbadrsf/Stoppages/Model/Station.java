package com.example.gbsbadrsf.Stoppages.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Station {

    @SerializedName("productionStationId")
    @Expose
    private Integer productionStationId;
    @SerializedName("productionStationCode")
    @Expose
    private String productionStationCode;
    @SerializedName("productionStationName")
    @Expose
    private String productionStationName;

    public Integer getProductionStationId() {
        return productionStationId;
    }

    public void setProductionStationId(Integer productionStationId) {
        this.productionStationId = productionStationId;
    }

    public String getProductionStationCode() {
        return productionStationCode;
    }

    public void setProductionStationCode(String productionStationCode) {
        this.productionStationCode = productionStationCode;
    }

    public String getProductionStationName() {
        return productionStationName;
    }

    public void setProductionStationName(String productionStationName) {
        this.productionStationName = productionStationName;
    }

    @Override
    public String toString() {
        return  productionStationName ;
    }
}
