package com.example.gbsbadrsf.Manfacturing.machinesignoff.MachineStop;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StopReason implements Parcelable {
    @SerializedName("stoppageReasonId")
    @Expose
    private Integer reasonId;
    @SerializedName("stoppageReasonName")
    @Expose
    private String reasonName;

    protected StopReason(Parcel in) {
        if (in.readByte() == 0) {
            reasonId = null;
        } else {
            reasonId = in.readInt();
        }
        reasonName = in.readString();
    }

    public static final Creator<StopReason> CREATOR = new Creator<StopReason>() {
        @Override
        public StopReason createFromParcel(Parcel in) {
            return new StopReason(in);
        }

        @Override
        public StopReason[] newArray(int size) {
            return new StopReason[size];
        }
    };

    public Integer getReasonId() {
        return reasonId;
    }

    public void setReasonId(Integer reasonId) {
        this.reasonId = reasonId;
    }

    public String getReasonName() {
        return reasonName;
    }

    public void setReasonName(String reasonName) {
        this.reasonName = reasonName;
    }

    @Override
    public String toString() {
        return  reasonName ;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (reasonId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(reasonId);
        }
        dest.writeString(reasonName);
    }
}
