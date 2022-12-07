package com.example.gbsbadrsf.Quality.paint.RandomQualityInception.PprListFragment;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LastMovePaintingOnline implements Parcelable {

    @SerializedName("productionSequenceNo")
    @Expose
    private Integer productionSequenceNo;
    @SerializedName("basketMoveId")
    @Expose
    private Integer basketMoveId;
    @SerializedName("jobOrderId")
    @Expose
    private Integer jobOrderId;
    @SerializedName("jobOrderName")
    @Expose
    private String jobOrderName;
    @SerializedName("jobOrderDate")
    @Expose
    private String jobOrderDate;
    @SerializedName("jobOrderQty")
    @Expose
    private Integer jobOrderQty;
    @SerializedName("pprLoadingId")
    @Expose
    private Integer pprLoadingId;
    @SerializedName("parentId")
    @Expose
    private Integer parentId;
    @SerializedName("parentCode")
    @Expose
    private String parentCode;
    @SerializedName("childId")
    @Expose
    private Integer childId;
    @SerializedName("childCode")
    @Expose
    private Object childCode;
    @SerializedName("parentDescription")
    @Expose
    private String parentDescription;
    @SerializedName("operationId")
    @Expose
    private Integer operationId;
    @SerializedName("operationEnName")
    @Expose
    private String operationEnName;
    @SerializedName("signOffQty")
    @Expose
    private Integer signOffQty;
    @SerializedName("basketCode")
    @Expose
    private Object basketCode;
    @SerializedName("isBulkQty")
    @Expose
    private Boolean isBulkQty;
    @SerializedName("sampleQty")
    @Expose
    private String sampleQty;
    @SerializedName("totalQtyDefected")
    @Expose
    private String totalQtyDefected;
    @SerializedName("totalQtyRejected")
    @Expose
    private String totalQtyRejected;
    @SerializedName("totalQtyOk")
    @Expose
    private String totalQtyOk;
    @SerializedName("isFullInspection")
    @Expose
    private Boolean isFullInspection;
    @SerializedName("isSaved")
    @Expose
    private Boolean isSaved;
    @SerializedName("isRejectedQty")
    @Expose
    private Boolean isRejectedQty;
    @SerializedName("basketType")
    @Expose
    private Object basketType;

    protected LastMovePaintingOnline(Parcel in) {
        if (in.readByte() == 0) {
            productionSequenceNo = null;
        } else {
            productionSequenceNo = in.readInt();
        }
        if (in.readByte() == 0) {
            basketMoveId = null;
        } else {
            basketMoveId = in.readInt();
        }
        if (in.readByte() == 0) {
            jobOrderId = null;
        } else {
            jobOrderId = in.readInt();
        }
        jobOrderName = in.readString();
        jobOrderDate = in.readString();
        if (in.readByte() == 0) {
            jobOrderQty = null;
        } else {
            jobOrderQty = in.readInt();
        }
        if (in.readByte() == 0) {
            pprLoadingId = null;
        } else {
            pprLoadingId = in.readInt();
        }
        if (in.readByte() == 0) {
            parentId = null;
        } else {
            parentId = in.readInt();
        }
        parentCode = in.readString();
        if (in.readByte() == 0) {
            childId = null;
        } else {
            childId = in.readInt();
        }
        parentDescription = in.readString();
        if (in.readByte() == 0) {
            operationId = null;
        } else {
            operationId = in.readInt();
        }
        operationEnName = in.readString();
        if (in.readByte() == 0) {
            signOffQty = null;
        } else {
            signOffQty = in.readInt();
        }
        byte tmpIsBulkQty = in.readByte();
        isBulkQty = tmpIsBulkQty == 0 ? null : tmpIsBulkQty == 1;
        sampleQty = in.readString();
        totalQtyDefected = in.readString();
        totalQtyRejected = in.readString();
        totalQtyOk = in.readString();
        byte tmpIsFullInspection = in.readByte();
        isFullInspection = tmpIsFullInspection == 0 ? null : tmpIsFullInspection == 1;
        byte tmpIsSaved = in.readByte();
        isSaved = tmpIsSaved == 0 ? null : tmpIsSaved == 1;
        byte tmpIsRejectedQty = in.readByte();
        isRejectedQty = tmpIsRejectedQty == 0 ? null : tmpIsRejectedQty == 1;
    }

    public static final Creator<LastMovePaintingOnline> CREATOR = new Creator<LastMovePaintingOnline>() {
        @Override
        public LastMovePaintingOnline createFromParcel(Parcel in) {
            return new LastMovePaintingOnline(in);
        }

        @Override
        public LastMovePaintingOnline[] newArray(int size) {
            return new LastMovePaintingOnline[size];
        }
    };

    public Integer getProductionSequenceNo() {
        return productionSequenceNo;
    }

    public void setProductionSequenceNo(Integer productionSequenceNo) {
        this.productionSequenceNo = productionSequenceNo;
    }

    public Integer getBasketMoveId() {
        return basketMoveId;
    }

    public void setBasketMoveId(Integer basketMoveId) {
        this.basketMoveId = basketMoveId;
    }

    public Integer getJobOrderId() {
        return jobOrderId;
    }

    public void setJobOrderId(Integer jobOrderId) {
        this.jobOrderId = jobOrderId;
    }

    public String getJobOrderName() {
        return jobOrderName;
    }

    public void setJobOrderName(String jobOrderName) {
        this.jobOrderName = jobOrderName;
    }

    public String getJobOrderDate() {
        return jobOrderDate;
    }

    public void setJobOrderDate(String jobOrderDate) {
        this.jobOrderDate = jobOrderDate;
    }

    public Integer getJobOrderQty() {
        return jobOrderQty;
    }

    public void setJobOrderQty(Integer jobOrderQty) {
        this.jobOrderQty = jobOrderQty;
    }

    public Integer getPprLoadingId() {
        return pprLoadingId;
    }

    public void setPprLoadingId(Integer pprLoadingId) {
        this.pprLoadingId = pprLoadingId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public Integer getChildId() {
        return childId;
    }

    public void setChildId(Integer childId) {
        this.childId = childId;
    }

    public Object getChildCode() {
        return childCode;
    }

    public void setChildCode(Object childCode) {
        this.childCode = childCode;
    }

    public String getParentDescription() {
        return parentDescription;
    }

    public void setParentDescription(String parentDescription) {
        this.parentDescription = parentDescription;
    }

    public Integer getOperationId() {
        return operationId;
    }

    public void setOperationId(Integer operationId) {
        this.operationId = operationId;
    }

    public String getOperationEnName() {
        return operationEnName;
    }

    public void setOperationEnName(String operationEnName) {
        this.operationEnName = operationEnName;
    }

    public Integer getSignOffQty() {
        return signOffQty;
    }

    public void setSignOffQty(Integer signOffQty) {
        this.signOffQty = signOffQty;
    }

    public Object getBasketCode() {
        return basketCode;
    }

    public void setBasketCode(Object basketCode) {
        this.basketCode = basketCode;
    }

    public Boolean getIsBulkQty() {
        return isBulkQty;
    }

    public void setIsBulkQty(Boolean isBulkQty) {
        this.isBulkQty = isBulkQty;
    }

    public String getSampleQty() {
        return sampleQty;
    }

    public void setSampleQty(String sampleQty) {
        this.sampleQty = sampleQty;
    }

    public String getTotalQtyDefected() {
        return totalQtyDefected;
    }

    public void setTotalQtyDefected(String totalQtyDefected) {
        this.totalQtyDefected = totalQtyDefected;
    }

    public String getTotalQtyRejected() {
        return totalQtyRejected;
    }

    public void setTotalQtyRejected(String totalQtyRejected) {
        this.totalQtyRejected = totalQtyRejected;
    }

    public String getTotalQtyOk() {
        return totalQtyOk;
    }

    public void setTotalQtyOk(String totalQtyOk) {
        this.totalQtyOk = totalQtyOk;
    }

    public Boolean getIsFullInspection() {
        return isFullInspection;
    }

    public void setIsFullInspection(Boolean isFullInspection) {
        this.isFullInspection = isFullInspection;
    }

    public Boolean getIsSaved() {
        return isSaved;
    }

    public void setIsSaved(Boolean isSaved) {
        this.isSaved = isSaved;
    }

    public Boolean getIsRejectedQty() {
        return isRejectedQty;
    }

    public void setIsRejectedQty(Boolean isRejectedQty) {
        this.isRejectedQty = isRejectedQty;
    }

    public Object getBasketType() {
        return basketType;
    }

    public void setBasketType(Object basketType) {
        this.basketType = basketType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (productionSequenceNo == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(productionSequenceNo);
        }
        if (basketMoveId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(basketMoveId);
        }
        if (jobOrderId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(jobOrderId);
        }
        dest.writeString(jobOrderName);
        dest.writeString(jobOrderDate);
        if (jobOrderQty == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(jobOrderQty);
        }
        if (pprLoadingId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(pprLoadingId);
        }
        if (parentId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(parentId);
        }
        dest.writeString(parentCode);
        if (childId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(childId);
        }
        dest.writeString(parentDescription);
        if (operationId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(operationId);
        }
        dest.writeString(operationEnName);
        if (signOffQty == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(signOffQty);
        }
        dest.writeByte((byte) (isBulkQty == null ? 0 : isBulkQty ? 1 : 2));
        dest.writeString(sampleQty);
        dest.writeString(totalQtyDefected);
        dest.writeString(totalQtyRejected);
        dest.writeString(totalQtyOk);
        dest.writeByte((byte) (isFullInspection == null ? 0 : isFullInspection ? 1 : 2));
        dest.writeByte((byte) (isSaved == null ? 0 : isSaved ? 1 : 2));
        dest.writeByte((byte) (isRejectedQty == null ? 0 : isRejectedQty ? 1 : 2));
    }
}
