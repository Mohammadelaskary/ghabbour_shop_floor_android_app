package com.example.gbsbadrsf.Quality.Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UpdateManufacturingDefectsData_Online {

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
    private Boolean isBulkGroup = true;
    @SerializedName("IsRejected")
    @Expose
    private Boolean isRejected;

    @SerializedName("applang")
    @Expose
    private String AppLang;

    public UpdateManufacturingDefectsData_Online(Integer userId, String deviceSerialNo, Integer qtyDefected, Integer defectGroupId, Integer mainDefectsId, List<Integer> defectList, Boolean isRejected, String appLang) {
        this.userId = userId;
        this.deviceSerialNo = deviceSerialNo;
        this.qtyDefected = qtyDefected;
        this.defectGroupId = defectGroupId;
        this.mainDefectsId = mainDefectsId;
        this.defectList = defectList;
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

    public Integer getQtyDefected() {
        return qtyDefected;
    }

    public void setQtyDefected(Integer qtyDefected) {
        this.qtyDefected = qtyDefected;
    }

    public Integer getDefectGroupId() {
        return defectGroupId;
    }

    public void setDefectGroupId(Integer defectGroupId) {
        this.defectGroupId = defectGroupId;
    }

    public Integer getMainDefectsId() {
        return mainDefectsId;
    }

    public void setMainDefectsId(Integer mainDefectsId) {
        this.mainDefectsId = mainDefectsId;
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
