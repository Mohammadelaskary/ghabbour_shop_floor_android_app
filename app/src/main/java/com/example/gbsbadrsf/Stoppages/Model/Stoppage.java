package com.example.gbsbadrsf.Stoppages.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Stoppage {

    @SerializedName("stoppageId")
    @Expose
    private Integer stoppageId;
    @SerializedName("stoppagesNameId")
    @Expose
    private Integer stoppagesNameId;
    @SerializedName("isMachine")
    @Expose
    private Boolean isMachine;
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
    @SerializedName("machineFamilyId")
    @Expose
    private Integer machineFamilyId;
    @SerializedName("machineId")
    @Expose
    private Integer machineId;
    @SerializedName("stationId")
    @Expose
    private Integer stationId;
    @SerializedName("dieId")
    @Expose
    private Integer dieId;
    @SerializedName("jigId")
    @Expose
    private Integer jigId;
    @SerializedName("stoppageStartDateTime")
    @Expose
    private String stoppageStartDateTime;
    @SerializedName("transactionDate")
    @Expose
    private String transactionDate;
    @SerializedName("stoppagesNameName")
    @Expose
    private String stoppagesNameName;
    @SerializedName("factoryName")
    @Expose
    private String factoryName;
    @SerializedName("workCenterName")
    @Expose
    private String workCenterName;
    @SerializedName("productionLineName")
    @Expose
    private String productionLineName;
    @SerializedName("productionSubLineName")
    @Expose
    private String productionSubLineName;
    @SerializedName("machineFamilyName")
    @Expose
    private String machineFamilyName;
    @SerializedName("machineName")
    @Expose
    private String machineName;
    @SerializedName("productionStationName")
    @Expose
    private String productionStationName;
    @SerializedName("dieName")
    @Expose
    private String dieName;
    @SerializedName("jigName")
    @Expose
    private String jigName;

    public Integer getStoppageId() {
        return stoppageId;
    }

    public void setStoppageId(Integer stoppageId) {
        this.stoppageId = stoppageId;
    }

    public Integer getStoppagesNameId() {
        return stoppagesNameId;
    }

    public void setStoppagesNameId(Integer stoppagesNameId) {
        this.stoppagesNameId = stoppagesNameId;
    }

    public Boolean getIsMachine() {
        return isMachine;
    }

    public void setIsMachine(Boolean isMachine) {
        this.isMachine = isMachine;
    }

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

    public Integer getMachineFamilyId() {
        return machineFamilyId;
    }

    public void setMachineFamilyId(Integer machineFamilyId) {
        this.machineFamilyId = machineFamilyId;
    }

    public Integer getMachineId() {
        return machineId;
    }

    public void setMachineId(Integer machineId) {
        this.machineId = machineId;
    }

    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    public Integer getDieId() {
        return dieId;
    }

    public void setDieId(Integer dieId) {
        this.dieId = dieId;
    }

    public Integer getJigId() {
        return jigId;
    }

    public void setJigId(Integer jigId) {
        this.jigId = jigId;
    }

    public String getStoppageStartDateTime() {
        return stoppageStartDateTime;
    }

    public void setStoppageStartDateTime(String stoppageStartDateTime) {
        this.stoppageStartDateTime = stoppageStartDateTime;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getStoppagesNameName() {
        return stoppagesNameName==null?" ":stoppagesNameName;
    }

    public void setStoppagesNameName(String stoppagesNameName) {
        this.stoppagesNameName = stoppagesNameName;
    }

    public String getFactoryName() {
        return factoryName==null?" ":factoryName;
    }

    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName;
    }

    public String getWorkCenterName() {
        return workCenterName==null?" ":workCenterName;
    }

    public void setWorkCenterName(String workCenterName) {
        this.workCenterName = workCenterName;
    }

    public String getProductionLineName() {
        return productionLineName==null?" ":productionLineName;
    }

    public void setProductionLineName(String productionLineName) {
        this.productionLineName = productionLineName;
    }

    public String getProductionSubLineName() {
        return productionSubLineName==null?" ":productionSubLineName;
    }

    public void setProductionSubLineName(String productionSubLineName) {
        this.productionSubLineName = productionSubLineName;
    }

    public String getMachineFamilyName() {
        return machineFamilyName==null?" ":machineFamilyName;
    }

    public void setMachineFamilyName(String machineFamilyName) {
        this.machineFamilyName = machineFamilyName;
    }

    public String getMachineName() {
        return machineName==null?" ":machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public String getProductionStationName() {
        return productionStationName==null?" ":productionStationName;
    }

    public void setProductionStationName(String productionStationName) {
        this.productionStationName = productionStationName;
    }

    public String getDieName() {
        return dieName==null?"":dieName;
    }

    public void setDieName(String dieName) {
        this.dieName = dieName;
    }

    public String getJigName() {
        return jigName==null?"":jigName;
    }

    public void setJigName(String jigName) {
        this.jigName = jigName;
    }

}
