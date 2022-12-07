package com.example.gbsbadrsf.AddWorkers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddWorkers_MachineData {

    @SerializedName("UserID")
    @Expose
    private Integer userID;
    @SerializedName("DeviceSerialNo")
    @Expose
    private String deviceSerialNo;
    @SerializedName("MachineCode")
    @Expose
    private String machineCode;
    @SerializedName("WorkerId")
    @Expose
    private List<Integer> workerId = null;
    @SerializedName("applang")
    @Expose
    private String AppLang;

    public AddWorkers_MachineData(Integer userID, String deviceSerialNo, String machineCode, List<Integer> workerId, String appLang) {
        this.userID = userID;
        this.deviceSerialNo = deviceSerialNo;
        this.machineCode = machineCode;
        this.workerId = workerId;
        AppLang = appLang;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getDeviceSerialNo() {
        return deviceSerialNo;
    }

    public void setDeviceSerialNo(String deviceSerialNo) {
        this.deviceSerialNo = deviceSerialNo;
    }

    public String getMachineCode() {
        return machineCode;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }

    public List<Integer> getWorkerId() {
        return workerId;
    }

    public void setWorkerId(List<Integer> workerId) {
        this.workerId = workerId;
    }
}
