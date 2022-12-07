package com.example.gbsbadrsf.Quality.Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FullInspectionQuality_OnlineData {
    @SerializedName("UserId")
    @Expose
    private Integer userId;
    @SerializedName("DeviceSerialNo")
    @Expose
    private String deviceSerialNo;
    @SerializedName("MachineCode")
    @Expose
    private String machineCode;
    @SerializedName("DefectedBasketCode")
    @Expose
    private String defectedBasketCode;
    @SerializedName("RejectedBasketCode")
    @Expose
    private String rejectedBasketCode;

    @SerializedName("applang")
    @Expose
    private String AppLang;

    public FullInspectionQuality_OnlineData(Integer userId, String deviceSerialNo, String machineCode, String defectedBasketCode, String rejectedBasketCode, String appLang) {
        this.userId = userId;
        this.deviceSerialNo = deviceSerialNo;
        this.machineCode = machineCode;
        this.defectedBasketCode = defectedBasketCode;
        this.rejectedBasketCode = rejectedBasketCode;
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

    public String getMachineCode() {
        return machineCode;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }

    public String getDefectedBasketCode() {
        return defectedBasketCode;
    }

    public void setDefectedBasketCode(String defectedBasketCode) {
        this.defectedBasketCode = defectedBasketCode;
    }

    public String getRejectedBasketCode() {
        return rejectedBasketCode;
    }

    public void setRejectedBasketCode(String rejectedBasketCode) {
        this.rejectedBasketCode = rejectedBasketCode;
    }

}
