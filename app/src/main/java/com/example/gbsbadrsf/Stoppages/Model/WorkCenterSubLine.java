package com.example.gbsbadrsf.Stoppages.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WorkCenterSubLine {
    @SerializedName("productionSubLineId")
    @Expose
    private Integer productionSubLineId;
    @SerializedName("productionSubLineName")
    @Expose
    private String productionSubLineName;

    public Integer getProductionSubLineId() {
        return productionSubLineId;
    }

    public void setProductionSubLineId(Integer productionSubLineId) {
        this.productionSubLineId = productionSubLineId;
    }

    public String getProductionSubLineName() {
        return productionSubLineName;
    }

    public void setProductionSubLineName(String productionSubLineName) {
        this.productionSubLineName = productionSubLineName;
    }

    @Override
    public String toString() {
        return productionSubLineName;
    }
}
