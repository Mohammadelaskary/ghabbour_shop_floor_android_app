package com.example.gbsbadrsf.Quality.welding.RandomQualityInception;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UpdateWeldingDefectsData_Online {

    @SerializedName("UserId")
    @Expose
    private Integer userId;
    @SerializedName("DeviceSerialNo")
    @Expose
    private String deviceSerialNo;
    @SerializedName("QtyDefected")
    @Expose
    private Integer qtyDefected;
    @SerializedName("DefectGroupId")
    @Expose
    private Integer defectGroupId;
    @SerializedName("MainDefectsId")
    @Expose
    private Integer mainDefectsId;
    @SerializedName("DefectList")
    @Expose
    private List<Integer> defectList = null;
    @SerializedName("IsBulkGroup")
    @Expose
    private Boolean isBulkGroup;
    @SerializedName("IsRejected")
    @Expose
    private Boolean isRejected;

    public UpdateWeldingDefectsData_Online(Integer userId, String deviceSerialNo, Integer qtyDefected, Integer defectGroupId, Integer mainDefectsId, List<Integer> defectList, Boolean isBulkGroup, Boolean isRejected) {
        this.userId = userId;
        this.deviceSerialNo = deviceSerialNo;
        this.qtyDefected = qtyDefected;
        this.defectGroupId = defectGroupId;
        this.mainDefectsId = mainDefectsId;
        this.defectList = defectList;
        this.isBulkGroup = isBulkGroup;
        this.isRejected = isRejected;
    }

}
