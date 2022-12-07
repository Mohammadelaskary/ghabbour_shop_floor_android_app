package com.example.gbsbadrsf.Stoppages.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddStoppageData {

    @SerializedName("UserId")
    @Expose
    private Integer userId;
    @SerializedName("DeviceSerialNo")
    @Expose
    private String deviceSerialNo;
    @SerializedName("FactoryId")
    @Expose
    private Integer factoryId;
    @SerializedName("WorkCenterId")
    @Expose
    private Integer workCenterId;
    @SerializedName("ProductionLineId")
    @Expose
    private Integer productionLineId;
    @SerializedName("ProductionSubLineId")
    @Expose
    private Integer productionSubLineId;
    @SerializedName("IsMachine")
    @Expose
    private Boolean isMachine;
    @SerializedName("MachineFamilyId")
    @Expose
    private Integer machineFamilyId;
    @SerializedName("MachineId")
    @Expose
    private Integer machineId;
    @SerializedName("StationId")
    @Expose
    private Integer stationId;
    @SerializedName("JigId")
    @Expose
    private Integer jigId;
    @SerializedName("DieId")
    @Expose
    private Integer dieId;
    @SerializedName("StoppagesNameId")
    @Expose
    private Integer stoppagesNameId;
    @SerializedName("StoppageStartDate")
    @Expose
    private String stoppageStartDate;
    @SerializedName("StoppageStartTime")
    @Expose
    private String stoppageStartTime;
    @SerializedName("applang")
    @Expose
    private String AppLang;

    public AddStoppageData(Integer userId, String deviceSerialNo, Integer factoryId, Integer workCenterId, Integer productionLineId, Integer productionSubLineId, Boolean isMachine, Integer machineFamilyId, Integer machineId, Integer stationId, Integer jigId, Integer dieId, Integer stoppagesNameId, String stoppageStartDate, String stoppageStartTime, String appLang) {
        this.userId = userId;
        this.deviceSerialNo = deviceSerialNo;
        this.factoryId = factoryId;
        this.workCenterId = workCenterId;
        this.productionLineId = productionLineId;
        this.productionSubLineId = productionSubLineId;
        this.isMachine = isMachine;
        this.machineFamilyId = machineFamilyId;
        this.machineId = machineId;
        this.stationId = stationId;
        this.jigId = jigId;
        this.dieId = dieId;
        this.stoppagesNameId = stoppagesNameId;
        this.stoppageStartDate = stoppageStartDate;
        this.stoppageStartTime = stoppageStartTime;
        AppLang = appLang;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getDeviceSerialNo() {
        return deviceSerialNo;
    }

    public void setDeviceSerialNo(String deviceSerialNo) {
        this.deviceSerialNo = deviceSerialNo;
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

    public Boolean getIsMachine() {
        return isMachine;
    }

    public void setIsMachine(Boolean isMachine) {
        this.isMachine = isMachine;
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

    public Integer getJigId() {
        return jigId;
    }

    public void setJigId(Integer jigId) {
        this.jigId = jigId;
    }

    public Integer getDieId() {
        return dieId;
    }

    public void setDieId(Integer dieId) {
        this.dieId = dieId;
    }

    public Integer getStoppagesNameId() {
        return stoppagesNameId;
    }

    public void setStoppagesNameId(Integer stoppagesNameId) {
        this.stoppagesNameId = stoppagesNameId;
    }

    public Boolean getMachine() {
        return isMachine;
    }

    public void setMachine(Boolean machine) {
        isMachine = machine;
    }

    public String getStoppageStartDate() {
        return stoppageStartDate;
    }

    public void setStoppageStartDate(String stoppageStartDate) {
        this.stoppageStartDate = stoppageStartDate;
    }

    public String getStoppageStartTime() {
        return stoppageStartTime;
    }

    public void setStoppageStartTime(String stoppageStartTime) {
        this.stoppageStartTime = stoppageStartTime;
    }
}
