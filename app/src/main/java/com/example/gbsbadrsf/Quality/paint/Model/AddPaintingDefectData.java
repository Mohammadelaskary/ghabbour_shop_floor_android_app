package com.example.gbsbadrsf.Quality.paint.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddPaintingDefectData {
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
    private int PprLoadingId;
    private int loadingPaintingSignOutTransactionId;
    //    private String NewBasketCode;
    private boolean isRejected;
    private boolean isBulkGroup;
    private boolean isSaved;

    public AddPaintingDefectData() {
    }

    @SerializedName("applang")
    @Expose
    private String AppLang;

    public AddPaintingDefectData(int userId, String deviceSerialNo, int jobOrderId, int parentID, int childId, int operationID, int qtyDefected, String notes, int sampleQty, List<Integer> defectList, int pprLoadingId, int loadingPaintingSignOutTransactionId, boolean isRejected, boolean isBulkGroup, boolean isSaved, String appLang) {
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
        PprLoadingId = pprLoadingId;
        this.loadingPaintingSignOutTransactionId = loadingPaintingSignOutTransactionId;
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

//    public String getNewBasketCode() {
//        return NewBasketCode;
//    }
//
//    public void setNewBasketCode(String newBasketCode) {
//        NewBasketCode = newBasketCode;
//    }


    public int getPprLoadingId() {
        return PprLoadingId;
    }

    public void setPprLoadingId(int pprLoadingId) {
        PprLoadingId = pprLoadingId;
    }

    public int getChildId() {
        return ChildId;
    }

    public void setChildId(int childId) {
        ChildId = childId;
    }

    public boolean isRejected() {
        return isRejected;
    }

    public void setRejected(boolean rejected) {
        isRejected = rejected;
    }

    public boolean isBulkGroup() {
        return isBulkGroup;
    }

    public void setBulkGroup(boolean bulkGroup) {
        isBulkGroup = bulkGroup;
    }

    public boolean isSaved() {
        return isSaved;
    }

    public void setSaved(boolean saved) {
        isSaved = saved;
    }

    public int getLoadingPaintingSignOutTransactionId() {
        return loadingPaintingSignOutTransactionId;
    }

    public void setLoadingPaintingSignOutTransactionId(int loadingPaintingSignOutTransactionId) {
        this.loadingPaintingSignOutTransactionId = loadingPaintingSignOutTransactionId;
    }
}
