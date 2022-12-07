package com.example.gbsbadrsf.Stoppages.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StationDetails {

    @SerializedName("factoryId")
    @Expose
    private Integer factoryId;
    @SerializedName("workCenterId")
    @Expose
    private Integer workCenterId;
    @SerializedName("productionLineId")
    @Expose
    private Integer productionLineId;
    @SerializedName("productionSubLineId")
    @Expose
    private Integer productionSubLineId;
    @SerializedName("productionStationId")
    @Expose
    private Integer productionStationId;
    @SerializedName("productionStationCode")
    @Expose
    private String productionStationCode;
    @SerializedName("productionStationName")
    @Expose
    private String productionStationName;
    @SerializedName("productionSubLineName")
    @Expose
    private String productionSubLineName;
    @SerializedName("productionLineName")
    @Expose
    private String productionLineName;
    @SerializedName("workCenterName")
    @Expose
    private String workCenterName;
    @SerializedName("factoryName")
    @Expose
    private String factoryName;

    public Integer getFactoryId() {
        return factoryId;
    }

    public void setFactoryId(Integer factoryId) {
        this.factoryId = factoryId;
    }

    public Integer getWorkCenterId() {
        return workCenterId;
    }

    public void setWorkCenterId(Integer workCenterId) {
        this.workCenterId = workCenterId;
    }

    public Integer getProductionLineId() {
        return productionLineId;
    }

    public void setProductionLineId(Integer productionLineId) {
        this.productionLineId = productionLineId;
    }

    public Integer getProductionSubLineId() {
        return productionSubLineId;
    }

    public void setProductionSubLineId(Integer productionSubLineId) {
        this.productionSubLineId = productionSubLineId;
    }

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

    public String getProductionSubLineName() {
        return productionSubLineName;
    }

    public void setProductionSubLineName(String productionSubLineName) {
        this.productionSubLineName = productionSubLineName;
    }

    public String getProductionLineName() {
        return productionLineName;
    }

    public void setProductionLineName(String productionLineName) {
        this.productionLineName = productionLineName;
    }

    public String getWorkCenterName() {
        return workCenterName;
    }

    public void setWorkCenterName(String workCenterName) {
        this.workCenterName = workCenterName;
    }

    public String getFactoryName() {
        return factoryName;
    }

    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName;
    }

}
