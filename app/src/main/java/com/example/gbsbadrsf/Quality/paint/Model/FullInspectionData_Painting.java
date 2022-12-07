package com.example.gbsbadrsf.Quality.paint.Model;

import com.example.gbsbadrsf.Quality.Data.OkBasketLst;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FullInspectionData_Painting {
    @SerializedName("loadingPaintingSignOutTransactionId")
    @Expose
    private int loadingPaintingSignOutTransactionId;
    @SerializedName("DefectedBasketCode")
    @Expose
    private String defectedBasketCode;
    @SerializedName("RejectedBasketCode")
    @Expose
    private String rejectedBasketCode;
    @SerializedName("PprLoadingId")
    @Expose
    private Integer pprLoadingId;
    @SerializedName("DeviceSerialNo")
    @Expose
    private String deviceSerialNo;
    @SerializedName("UserId")
    @Expose
    private Integer userId;
    @SerializedName("applang")
    @Expose
    private String AppLang;

    public FullInspectionData_Painting(int loadingPaintingSignOutTransactionId, String defectedBasketCode, String rejectedBasketCode, Integer pprLoadingId, String deviceSerialNo, Integer userId, String appLang) {
        this.loadingPaintingSignOutTransactionId = loadingPaintingSignOutTransactionId;
        this.defectedBasketCode = defectedBasketCode;
        this.rejectedBasketCode = rejectedBasketCode;
        this.pprLoadingId = pprLoadingId;
        this.deviceSerialNo = deviceSerialNo;
        this.userId = userId;
        AppLang = appLang;
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

    public Integer getPprLoadingId() {
        return pprLoadingId;
    }

    public void setPprLoadingId(Integer pprLoadingId) {
        this.pprLoadingId = pprLoadingId;
    }

    public String getDeviceSerialNo() {
        return deviceSerialNo;
    }

    public void setDeviceSerialNo(String deviceSerialNo) {
        this.deviceSerialNo = deviceSerialNo;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public int getLoadingPaintingSignOutTransactionId() {
        return loadingPaintingSignOutTransactionId;
    }

    public void setLoadingPaintingSignOutTransactionId(int loadingPaintingSignOutTransactionId) {
        this.loadingPaintingSignOutTransactionId = loadingPaintingSignOutTransactionId;
    }
}
