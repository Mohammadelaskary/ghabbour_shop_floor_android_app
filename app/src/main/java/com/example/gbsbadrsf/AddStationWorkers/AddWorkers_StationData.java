package com.example.gbsbadrsf.AddStationWorkers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddWorkers_StationData {

    @SerializedName("UserID")
    @Expose
    private Integer userID;
    @SerializedName("DeviceSerialNo")
    @Expose
    private String deviceSerialNo;
    @SerializedName("StationCode")
    @Expose
    private String stationCode;
    @SerializedName("WorkerId")
    @Expose
    private List<Integer> workerId = null;
    @SerializedName("applang")
    @Expose
    private String AppLang;

    public AddWorkers_StationData(Integer userID, String deviceSerialNo, String stationCode, List<Integer> workerId, String appLang) {
        this.userID = userID;
        this.deviceSerialNo = deviceSerialNo;
        this.stationCode = stationCode;
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

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    public List<Integer> getWorkerId() {
        return workerId;
    }

    public void setWorkerId(List<Integer> workerId) {
        this.workerId = workerId;
    }
}
