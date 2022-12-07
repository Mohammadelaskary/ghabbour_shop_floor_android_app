package com.example.gbsbadrsf.Quality.paint.RandomQualityInception;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FullInspectionQuality_Online_Painting_Data {

    @SerializedName("UserId")
    @Expose
    private Integer userId;
    @SerializedName("DeviceSerialNo")
    @Expose
    private String deviceSerialNo;
    @SerializedName("StationCode")
    @Expose
    private String stationCode;
    @SerializedName("JobOrderId")
    @Expose
    private Integer jobOrderId;
    @SerializedName("PprLoadingId")
    @Expose
    private Integer pprLoadingId;
    @SerializedName("OperationID")
    @Expose
    private Integer operationID;
    @SerializedName("DefectedBasketCode")
    @Expose
    private String defectedBasketCode;
    @SerializedName("RejectedBasketCode")
    @Expose
    private String rejectedBasketCode;

    @SerializedName("applang")
    @Expose
    private String AppLang;

    public FullInspectionQuality_Online_Painting_Data(Integer userId, String deviceSerialNo, String stationCode, Integer jobOrderId, Integer pprLoadingId, Integer operationID, String defectedBasketCode, String rejectedBasketCode, String appLang) {
        this.userId = userId;
        this.deviceSerialNo = deviceSerialNo;
        this.stationCode = stationCode;
        this.jobOrderId = jobOrderId;
        this.pprLoadingId = pprLoadingId;
        this.operationID = operationID;
        this.defectedBasketCode = defectedBasketCode;
        this.rejectedBasketCode = rejectedBasketCode;
        AppLang = appLang;
    }
}
