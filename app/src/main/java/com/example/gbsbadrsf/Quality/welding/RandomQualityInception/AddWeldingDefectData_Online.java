package com.example.gbsbadrsf.Quality.welding.RandomQualityInception;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddWeldingDefectData_Online {

    @SerializedName("UserId")
    @Expose
    private Integer userId;
    @SerializedName("DeviceSerialNo")
    @Expose
    private String deviceSerialNo;
    @SerializedName("JobOrderId")
    @Expose
    private Integer jobOrderId;
    @SerializedName("ParentID")
    @Expose
    private Integer parentID;
    @SerializedName("OperationID")
    @Expose
    private Integer operationID;
    @SerializedName("QtyDefected")
    @Expose
    private Integer qtyDefected;
    @SerializedName("StationCode")
    @Expose
    private String stationCode;
    @SerializedName("SampleQty")
    @Expose
    private Integer sampleQty;
    @SerializedName("DefectList")
    @Expose
    private List<Integer> defectList = null;
    @SerializedName("IsBulkGroup")
    @Expose
    private Boolean isBulkGroup;
    @SerializedName("IsRejected")
    @Expose
    private Boolean isRejected;
    @SerializedName("IsSaved")
    @Expose
    private Boolean isSaved;
    @SerializedName("Notes")
    @Expose
    private String notes;
    @SerializedName("applang")
    @Expose
    private String AppLang;


    public AddWeldingDefectData_Online(Integer userId, String deviceSerialNo, Integer jobOrderId, Integer parentID, Integer operationID, Integer qtyDefected, String stationCode, Integer sampleQty, List<Integer> defectList, Boolean isBulkGroup, Boolean isRejected, Boolean isSaved, String notes, String appLang) {
        this.userId = userId;
        this.deviceSerialNo = deviceSerialNo;
        this.jobOrderId = jobOrderId;
        this.parentID = parentID;
        this.operationID = operationID;
        this.qtyDefected = qtyDefected;
        this.stationCode = stationCode;
        this.sampleQty = sampleQty;
        this.defectList = defectList;
        this.isBulkGroup = isBulkGroup;
        this.isRejected = isRejected;
        this.isSaved = isSaved;
        this.notes = notes;
        AppLang = appLang;
    }
}
