package com.example.gbsbadrsf.Quality.welding.RandomQualityInception;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FullInspectionQuality_Online_Welding_Data {

    @SerializedName("UserId")
    @Expose
    public Integer userId;
    @SerializedName("DeviceSerialNo")
    @Expose
    public String deviceSerialNo;
    @SerializedName("StationCode")
    @Expose
    public String stationCode;
    @SerializedName("DefectedBasketCode")
    @Expose
    public String defectedBasketCode;
    @SerializedName("RejectedBasketCode")
    @Expose
    public String rejectedBasketCode;

    @SerializedName("applang")
    @Expose
    private String AppLang;

    public FullInspectionQuality_Online_Welding_Data(Integer userId, String deviceSerialNo, String stationCode, String defectedBasketCode, String rejectedBasketCode, String appLang) {
        this.userId = userId;
        this.deviceSerialNo = deviceSerialNo;
        this.stationCode = stationCode;
        this.defectedBasketCode = defectedBasketCode;
        this.rejectedBasketCode = rejectedBasketCode;
        AppLang = appLang;
    }
}
