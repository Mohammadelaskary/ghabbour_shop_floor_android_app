package com.example.gbsbadrsf.welding.ItemsReceiving;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LstIssuedChildParameter implements Parcelable {


    @SerializedName("chilD_ITEM_ID")
    @Expose
    private String chilDITEMID;
    @SerializedName("chilD_ITEM_NAME")
    @Expose
    private String chilDITEMNAME;
    @SerializedName("chilD_ITEM_DESC")
    @Expose
    private String chilDITEMDESC;
    @SerializedName("chilD_ITEM_UOM")
    @Expose
    private String chilDITEMUOM;
    @SerializedName("requireD_QUANTITY")
    @Expose
    private String requireDQUANTITY;
    @SerializedName("quantitY_ISSUED")
    @Expose
    private String quantitYISSUED;
    @SerializedName("remaininG_QUANTITY")
    @Expose
    private String remaininGQUANTITY;
    @SerializedName("isIssued")
    @Expose
    private Boolean isIssued;

    protected LstIssuedChildParameter(Parcel in) {
        chilDITEMID = in.readString();
        chilDITEMNAME = in.readString();
        chilDITEMDESC = in.readString();
        chilDITEMUOM = in.readString();
        requireDQUANTITY = in.readString();
        quantitYISSUED = in.readString();
        remaininGQUANTITY = in.readString();
        byte tmpIsIssued = in.readByte();
        isIssued = tmpIsIssued == 0 ? null : tmpIsIssued == 1;
    }

    public static final Creator<LstIssuedChildParameter> CREATOR = new Creator<LstIssuedChildParameter>() {
        @Override
        public LstIssuedChildParameter createFromParcel(Parcel in) {
            return new LstIssuedChildParameter(in);
        }

        @Override
        public LstIssuedChildParameter[] newArray(int size) {
            return new LstIssuedChildParameter[size];
        }
    };

    public String getChilDITEMID() {
        return chilDITEMID;
    }

    public void setChilDITEMID(String chilDITEMID) {
        this.chilDITEMID = chilDITEMID;
    }

    public String getChilDITEMNAME() {
        return chilDITEMNAME;
    }

    public void setChilDITEMNAME(String chilDITEMNAME) {
        this.chilDITEMNAME = chilDITEMNAME;
    }

    public String getChilDITEMDESC() {
        return chilDITEMDESC;
    }

    public void setChilDITEMDESC(String chilDITEMDESC) {
        this.chilDITEMDESC = chilDITEMDESC;
    }

    public String getChilDITEMUOM() {
        return chilDITEMUOM;
    }

    public void setChilDITEMUOM(String chilDITEMUOM) {
        this.chilDITEMUOM = chilDITEMUOM;
    }

    public String getRequireDQUANTITY() {
        return requireDQUANTITY;
    }

    public void setRequireDQUANTITY(String requireDQUANTITY) {
        this.requireDQUANTITY = requireDQUANTITY;
    }

    public String getQuantitYISSUED() {
        return quantitYISSUED;
    }

    public void setQuantitYISSUED(String quantitYISSUED) {
        this.quantitYISSUED = quantitYISSUED;
    }

    public String getRemaininGQUANTITY() {
        return remaininGQUANTITY;
    }

    public void setRemaininGQUANTITY(String remaininGQUANTITY) {
        this.remaininGQUANTITY = remaininGQUANTITY;
    }

    public Boolean getIsIssued() {
        return isIssued;
    }

    public void setIsIssued(Boolean isIssued) {
        this.isIssued = isIssued;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(chilDITEMID);
        dest.writeString(chilDITEMNAME);
        dest.writeString(chilDITEMDESC);
        dest.writeString(chilDITEMUOM);
        dest.writeString(requireDQUANTITY);
        dest.writeString(quantitYISSUED);
        dest.writeString(remaininGQUANTITY);
        dest.writeByte((byte) (isIssued == null ? 0 : isIssued ? 1 : 2));
    }
}
