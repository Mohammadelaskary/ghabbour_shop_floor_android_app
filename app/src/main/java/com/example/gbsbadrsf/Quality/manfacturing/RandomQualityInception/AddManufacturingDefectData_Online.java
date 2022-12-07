package com.example.gbsbadrsf.Quality.manfacturing.RandomQualityInception;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddManufacturingDefectData_Online {

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
    @SerializedName("ChildId")
    @Expose
    private Integer childId;
    @SerializedName("OperationID")
    @Expose
    private Integer operationID;
    @SerializedName("QtyDefected")
    @Expose
    private Integer qtyDefected;
    @SerializedName("MachineCode")
    @Expose
    private String machineCode;
    @SerializedName("SampleQty")
    @Expose
    private Integer sampleQty;
    @SerializedName("DefectList")
    @Expose
    private List<Integer> defectList = null;
    @SerializedName("IsBulkGroup")
    @Expose
    private Boolean isBulkGroup = true;
    @SerializedName("IsRejected")
    @Expose
    private Boolean isRejected;
    @SerializedName("IsSaved")
    @Expose
    private Boolean isSaved;
    @SerializedName("Notes")
    @Expose
    private String notes;
    @SerializedName("productionSequenceNo")
    @Expose
    private int productionSequenceNo;
    @SerializedName("applang")
    @Expose
    private String AppLang;

    public AddManufacturingDefectData_Online(Integer userId, String deviceSerialNo, Integer jobOrderId, Integer parentID, Integer childId, Integer operationID, Integer qtyDefected, String machineCode, Integer sampleQty, List<Integer> defectList, Boolean isRejected, Boolean isSaved, String notes, int productionSequenceNo, String appLang) {
        this.userId = userId;
        this.deviceSerialNo = deviceSerialNo;
        this.jobOrderId = jobOrderId;
        this.parentID = parentID;
        this.childId = childId;
        this.operationID = operationID;
        this.qtyDefected = qtyDefected;
        this.machineCode = machineCode;
        this.sampleQty = sampleQty;
        this.defectList = defectList;
        this.isRejected = isRejected;
        this.isSaved = isSaved;
        this.notes = notes;
        this.productionSequenceNo = productionSequenceNo;
        AppLang = appLang;
    }
}
