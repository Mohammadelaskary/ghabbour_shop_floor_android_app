package com.example.gbsbadrsf.Quality.welding.RejectionRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
public class SaveRejectionRequestBody_Welding {
    @SerializedName("UserId")
    @Expose
    private Integer userId;
    @SerializedName("DeviceSerialNo")
    @Expose
    private String deviceSerialNo;
    @SerializedName("OldBasketCode")
    @Expose
    private String oldBasketCode;
    @SerializedName("NewBasketCode")
    @Expose
    private String newBasketCode;

    @SerializedName("RejectionQty")
    @Expose
    private Integer rejectionQty;
    @SerializedName("DepartmentID")
    @Expose
    private Integer departmentID;
    @SerializedName("RejectionReasonID")
    @Expose
    private Integer rejectionReasonID;
    @SerializedName("DefectList")
    @Expose
    private List<Integer> defectList = null;

    @SerializedName("applang")
    @Expose
    private String AppLang;

    public SaveRejectionRequestBody_Welding(Integer userId, String deviceSerialNo, String oldBasketCode, String newBasketCode, Integer rejectionQty, Integer departmentID, Integer rejectionReasonID, List<Integer> defectList, String appLang) {
        this.userId = userId;
        this.deviceSerialNo = deviceSerialNo;
        this.oldBasketCode = oldBasketCode;
        this.newBasketCode = newBasketCode;
        this.rejectionQty = rejectionQty;
        this.departmentID = departmentID;
        this.rejectionReasonID = rejectionReasonID;
        this.defectList = defectList;
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

    public String getOldBasketCode() {
        return oldBasketCode;
    }

    public void setOldBasketCode(String oldBasketCode) {
        this.oldBasketCode = oldBasketCode;
    }

    public Integer getRejectionQty() {
        return rejectionQty;
    }

    public void setRejectionQty(Integer rejectionQty) {
        this.rejectionQty = rejectionQty;
    }

    public Integer getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(Integer departmentID) {
        this.departmentID = departmentID;
    }

    public Integer getRejectionReasonID() {
        return rejectionReasonID;
    }

    public void setRejectionReasonID(Integer rejectionReasonID) {
        this.rejectionReasonID = rejectionReasonID;
    }

    public List<Integer> getDefectList() {
        return defectList;
    }

    public void setDefectList(List<Integer> defectList) {
        this.defectList = defectList;
    }

    public String getNewBasketCode() {
        return newBasketCode;
    }

    public void setNewBasketCode(String newBasketCode) {
        this.newBasketCode = newBasketCode;
    }
}
