package com.example.gbsbadrsf.Quality.paint.RandomQualityInception;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddPaintingDefectData_Online {
    @SerializedName("UserId")
    @Expose
    public Integer userId;
    @SerializedName("DeviceSerialNo")
    @Expose
    public String deviceSerialNo;
    @SerializedName("JobOrderId")
    @Expose
    public Integer jobOrderId;
    @SerializedName("ParentID")
    @Expose
    public Integer parentID;
    @SerializedName("PprLoadingId")
    @Expose
    public Integer pprLoadingId;
    @SerializedName("OperationID")
    @Expose
    public Integer operationID;
    @SerializedName("ProductionSequenceNo")
    @Expose
    public Integer productionSequenceNo;
    @SerializedName("QtyDefected")
    @Expose
    public Integer qtyDefected;
    @SerializedName("StationCode")
    @Expose
    public String stationCode;
    @SerializedName("SampleQty")
    @Expose
    public Integer sampleQty;
    @SerializedName("DefectList")
    @Expose
    public List<Integer> defectList = null;
    @SerializedName("IsBulkGroup")
    @Expose
    public Boolean isBulkGroup;
    @SerializedName("IsRejected")
    @Expose
    public Boolean isRejected;
    @SerializedName("IsSaved")
    @Expose
    public Boolean isSaved;
    @SerializedName("Notes")
    @Expose
    public String notes;


    @SerializedName("applang")
    @Expose
    private String AppLang;

    public AddPaintingDefectData_Online(Integer userId, String deviceSerialNo, Integer jobOrderId, Integer parentID, Integer pprLoadingId, Integer operationID, Integer productionSequenceNo, Integer qtyDefected, String stationCode, Integer sampleQty, List<Integer> defectList, Boolean isBulkGroup, Boolean isRejected, Boolean isSaved, String notes, String appLang) {
        this.userId = userId;
        this.deviceSerialNo = deviceSerialNo;
        this.jobOrderId = jobOrderId;
        this.parentID = parentID;
        this.pprLoadingId = pprLoadingId;
        this.operationID = operationID;
        this.productionSequenceNo = productionSequenceNo;
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
