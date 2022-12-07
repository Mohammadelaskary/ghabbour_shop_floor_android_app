package com.example.gbsbadrsf.Stoppages.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MachineDetail {

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
    @SerializedName("machineCode")
    @Expose
    private String machineCode;
    @SerializedName("machineName")
    @Expose
    private String machineName;
    @SerializedName("machineFamilyName")
    @Expose
    private String machineFamilyName;
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

    public String getMachineCode() {
        return machineCode;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public String getMachineFamilyName() {
        return machineFamilyName;
    }

    public void setMachineFamilyName(String machineFamilyName) {
        this.machineFamilyName = machineFamilyName;
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
