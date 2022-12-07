package com.example.gbsbadrsf.Quality.welding.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UpdateWeldingDefectsData {
    @SerializedName("UserId")
    @Expose
    private Integer userId;
    @SerializedName("DeviceSerialNo")
    @Expose
    private String deviceSerialNo;
    @SerializedName("MainDefectsId")
    @Expose
    private Integer MainDefectsId;
    @SerializedName("DefectGroupId")
    @Expose
    private Integer defectGroupId;
    @SerializedName("QtyDefected")
    @Expose
    private Integer qtyDefected;
    @SerializedName("DefectList")
    @Expose
    private List<Integer> defectList = null;
    @SerializedName("IsBulkGroup")
    @Expose
    private Boolean isBulkGroup;
    @SerializedName("IsRejected")
    @Expose
    private Boolean isRejected;

    @SerializedName("applang")
    @Expose
    private String AppLang;

    public UpdateWeldingDefectsData(Integer userId, String deviceSerialNo, Integer mainDefectsId, Integer defectGroupId, Integer qtyDefected, List<Integer> defectList, Boolean isBulkGroup, Boolean isRejected, String appLang) {
        this.userId = userId;
        this.deviceSerialNo = deviceSerialNo;
        MainDefectsId = mainDefectsId;
        this.defectGroupId = defectGroupId;
        this.qtyDefected = qtyDefected;
        this.defectList = defectList;
        this.isBulkGroup = isBulkGroup;
        this.isRejected = isRejected;
        AppLang = appLang;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getDeviceSerialNo() {
        return deviceSerialNo;
    }

    public void setDeviceSerialNo(String deviceSerialNo) {
        this.deviceSerialNo = deviceSerialNo;
    }

    public Integer getDefectGroupId() {
        return defectGroupId;
    }

    public void setDefectGroupId(Integer defectGroupId) {
        this.defectGroupId = defectGroupId;
    }

    public Integer getQtyDefected() {
        return qtyDefected;
    }

    public void setQtyDefected(Integer qtyDefected) {
        this.qtyDefected = qtyDefected;
    }

    public List<Integer> getDefectList() {
        return defectList;
    }

    public void setDefectList(List<Integer> defectList) {
        this.defectList = defectList;
    }

    public Boolean getIsBulkGroup() {
        return isBulkGroup;
    }

    public void setIsBulkGroup(Boolean isBulkGroup) {
        this.isBulkGroup = isBulkGroup;
    }

    public Boolean getIsRejected() {
        return isRejected;
    }

    public void setIsRejected(Boolean isRejected) {
        this.isRejected = isRejected;
    }

}
