package com.example.gbsbadrsf.Quality.Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddManufacturingDefectData {
    private int ProductionSequenceNo;
    private int UserId;
    private String DeviceSerialNo;
    private int JobOrderId;
    private int ParentID;
    private int ChildId;
    private int OperationID;
    private int QtyDefected;
    private String Notes;
    private int SampleQty;
//    private boolean IsNewSampleQty;
    private List<Integer> DefectList;
    private String BasketCode;
//    private String NewBasketCode;
    private boolean isRejected;
    private boolean isBulkGroup;
    private boolean isSaved;
    public AddManufacturingDefectData() {
    }

    @SerializedName("applang")
    @Expose
    private String AppLang;

    public AddManufacturingDefectData(int productionSequenceNo, int userId, String deviceSerialNo, int jobOrderId, int parentID, int childId, int operationID, int qtyDefected, String notes, int sampleQty, List<Integer> defectList, String basketCode, boolean isRejected, boolean isBulkGroup, boolean isSaved, String appLang) {
        ProductionSequenceNo = productionSequenceNo;
        UserId = userId;
        DeviceSerialNo = deviceSerialNo;
        JobOrderId = jobOrderId;
        ParentID = parentID;
        ChildId = childId;
        OperationID = operationID;
        QtyDefected = qtyDefected;
        Notes = notes;
        SampleQty = sampleQty;
        DefectList = defectList;
        BasketCode = basketCode;
        this.isRejected = isRejected;
        this.isBulkGroup = isBulkGroup;
        this.isSaved = isSaved;
        AppLang = appLang;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getDeviceSerialNo() {
        return DeviceSerialNo;
    }

    public void setDeviceSerialNo(String deviceSerialNo) {
        DeviceSerialNo = deviceSerialNo;
    }

    public int getJobOrderId() {
        return JobOrderId;
    }

    public void setJobOrderId(int jobOrderId) {
        JobOrderId = jobOrderId;
    }

    public int getParentID() {
        return ParentID;
    }

    public void setParentID(int parentID) {
        ParentID = parentID;
    }

    public int getChildId() {
        return ChildId;
    }

    public void setChildId(int childId) {
        ChildId = childId;
    }

    public int getOperationID() {
        return OperationID;
    }

    public void setOperationID(int operationID) {
        OperationID = operationID;
    }

    public int getQtyDefected() {
        return QtyDefected;
    }

    public void setQtyDefected(int qtyDefected) {
        QtyDefected = qtyDefected;
    }

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String notes) {
        Notes = notes;
    }

    public int getSampleQty() {
        return SampleQty;
    }

    public void setSampleQty(int sampleQty) {
        SampleQty = sampleQty;
    }

    public List<Integer> getDefectList() {
        return DefectList;
    }

    public void setDefectList(List<Integer> defectList) {
        DefectList = defectList;
    }

//    public boolean isNewSampleQty() {
//        return IsNewSampleQty;
//    }
//
//    public void setNewSampleQty(boolean newSampleQty) {
//        IsNewSampleQty = newSampleQty;
//    }

    public String getBasketCode() {
        return BasketCode;
    }

    public void setBasketCode(String basketCode) {
        BasketCode = basketCode;
    }

}
