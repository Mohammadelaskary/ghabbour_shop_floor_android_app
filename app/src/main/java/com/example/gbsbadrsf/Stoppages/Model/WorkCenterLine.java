package com.example.gbsbadrsf.Stoppages.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WorkCenterLine {
    @SerializedName("productionLineId")
    @Expose
    private Integer productionLineId;
    @SerializedName("productionLineName")
    @Expose
    private String productionLineName;

    public Integer getProductionLineId() {
        return productionLineId;
    }

    public void setProductionLineId(Integer productionLineId) {
        this.productionLineId = productionLineId;
    }

    public String getProductionLineName() {
        return productionLineName;
    }

    public void setProductionLineName(String productionLineName) {
        this.productionLineName = productionLineName;
    }

    @Override
    public String toString() {
        return productionLineName;
    }
}
