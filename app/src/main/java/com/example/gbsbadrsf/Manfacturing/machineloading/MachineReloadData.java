package com.example.gbsbadrsf.Manfacturing.machineloading;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MachineReloadData {

    @SerializedName("UserID")
    @Expose
    private Integer userID;
    @SerializedName("DeviceSerialNo")
    @Expose
    private String deviceSerialNo;
    @SerializedName("MachineCode")
    @Expose
    private String machineCode;
    @SerializedName("DieCode")
    @Expose
    private String dieCode;
    @SerializedName("LoadingQtyMobile")
    @Expose
    private Integer loadingQtyMobile;
    @SerializedName("BasketLst")
    @Expose
    private List<String> basketLst = null;
    @SerializedName("applang")
    @Expose
    private String AppLang;

    public MachineReloadData(Integer userID, String deviceSerialNo, String machineCode, String dieCode, Integer loadingQtyMobile, List<String> basketLst, String appLang) {
        this.userID = userID;
        this.deviceSerialNo = deviceSerialNo;
        this.machineCode = machineCode;
        this.dieCode = dieCode;
        this.loadingQtyMobile = loadingQtyMobile;
        this.basketLst = basketLst;
        AppLang = appLang;
    }

    public MachineReloadData(Integer userID, String deviceSerialNo, String machineCode, Integer loadingQtyMobile, List<String> basketLst) {
        this.userID = userID;
        this.deviceSerialNo = deviceSerialNo;
        this.machineCode = machineCode;
        this.loadingQtyMobile = loadingQtyMobile;
        this.basketLst = basketLst;
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

    public String getDieCode() {
        return dieCode;
    }

    public void setDieCode(String dieCode) {
        this.dieCode = dieCode;
    }

    public Integer getLoadingQtyMobile() {
        return loadingQtyMobile;
    }

    public void setLoadingQtyMobile(Integer loadingQtyMobile) {
        this.loadingQtyMobile = loadingQtyMobile;
    }

    public List<String> getBasketLst() {
        return basketLst;
    }

    public void setBasketLst(List<String> basketLst) {
        this.basketLst = basketLst;
    }
}
