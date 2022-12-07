package com.example.gbsbadrsf.Manfacturing.machinesignoff.MachineStop;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MachineHoldData {

    @SerializedName("UserID")
    @Expose
    private Integer userID;
    @SerializedName("DeviceSerialNo")
    @Expose
    private String deviceSerialNo;
    @SerializedName("MachineCode")
    @Expose
    private String machineCode;
    @SerializedName("SignOutQty")
    @Expose
    private Integer signOutQty;
    @SerializedName("OkBasketLst")
    @Expose
    private List<Basket> okBasketLst = null;
    @SerializedName("IsBulkQty_Ok")
    @Expose
    private Boolean isBulkQtyOk;
    @SerializedName("HoldQty")
    @Expose
    private Integer holdQty;
    @SerializedName("HoldBasketLst")
    @Expose
    private List<Basket> holdBasketLst = null;
    @SerializedName("IsBulkQty_Hold")
    @Expose
    private Boolean isBulkQtyHold;
    @SerializedName("StoppageReasonId")
    @Expose
    private Integer stoppageReasonId;
    @SerializedName("applang")
    @Expose
    private String AppLang;

    public MachineHoldData(Integer userID, String deviceSerialNo, String machineCode, Integer signOutQty, List<Basket> okBasketLst, Boolean isBulkQtyOk, Integer holdQty, List<Basket> holdBasketLst, Boolean isBulkQtyHold, Integer stoppageReasonId, String appLang) {
        this.userID = userID;
        this.deviceSerialNo = deviceSerialNo;
        this.machineCode = machineCode;
        this.signOutQty = signOutQty;
        this.okBasketLst = okBasketLst;
        this.isBulkQtyOk = isBulkQtyOk;
        this.holdQty = holdQty;
        this.holdBasketLst = holdBasketLst;
        this.isBulkQtyHold = isBulkQtyHold;
        this.stoppageReasonId = stoppageReasonId;
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

    public Integer getSignOutQty() {
        return signOutQty;
    }

    public void setSignOutQty(Integer signOutQty) {
        this.signOutQty = signOutQty;
    }

    public List<Basket> getOkBasketLst() {
        return okBasketLst;
    }

    public void setOkBasketLst(List<Basket> okBasketLst) {
        this.okBasketLst = okBasketLst;
    }

    public Boolean getIsBulkQtyOk() {
        return isBulkQtyOk;
    }

    public void setIsBulkQtyOk(Boolean isBulkQtyOk) {
        this.isBulkQtyOk = isBulkQtyOk;
    }

    public Integer getHoldQty() {
        return holdQty;
    }

    public void setHoldQty(Integer holdQty) {
        this.holdQty = holdQty;
    }

    public List<Basket> getHoldBasketLst() {
        return holdBasketLst;
    }

    public void setHoldBasketLst(List<Basket> holdBasketLst) {
        this.holdBasketLst = holdBasketLst;
    }

    public Boolean getIsBulkQtyHold() {
        return isBulkQtyHold;
    }

    public void setIsBulkQtyHold(Boolean isBulkQtyHold) {
        this.isBulkQtyHold = isBulkQtyHold;
    }

    public Integer getStoppageReasonId() {
        return stoppageReasonId;
    }

    public void setStoppageReasonId(Integer stoppageReasonId) {
        this.stoppageReasonId = stoppageReasonId;
    }

}
