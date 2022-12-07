package com.example.gbsbadrsf.welding.ItemsReceiving;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PutChildsToBasketData {
    @SerializedName("UserID")
    @Expose
    private Integer userID;
    @SerializedName("DeviceSerialNo")
    @Expose
    private String deviceSerialNo;
    @SerializedName("EntitiyId")
    @Expose
    private int entitiyId;
    @SerializedName("PprparentId")
    @Expose
    private int pprparentId;
    @SerializedName("CHILD_ITEM_ID")
    @Expose
    private String childItemId;
    @SerializedName("BasketLst")
    @Expose
    private List<String> basketLst = null;
    @SerializedName("applang")
    @Expose
    private String AppLang;

    public PutChildsToBasketData(Integer userID, String deviceSerialNo, int entitiyId, int pprparentId, String childItemId, List<String> basketLst, String appLang) {
        this.userID = userID;
        this.deviceSerialNo = deviceSerialNo;
        this.entitiyId = entitiyId;
        this.pprparentId = pprparentId;
        this.childItemId = childItemId;
        this.basketLst = basketLst;
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

    public int getEntitiyId() {
        return entitiyId;
    }

    public void setEntitiyId(int entitiyId) {
        this.entitiyId = entitiyId;
    }

    public int getPprparentId() {
        return pprparentId;
    }

    public void setPprparentId(int pprparentId) {
        this.pprparentId = pprparentId;
    }

    public String getChildItemId() {
        return childItemId;
    }

    public void setChildItemId(String childItemId) {
        this.childItemId = childItemId;
    }

    public List<String> getBasketLst() {
        return basketLst;
    }

    public void setBasketLst(List<String> basketLst) {
        this.basketLst = basketLst;
    }

}
