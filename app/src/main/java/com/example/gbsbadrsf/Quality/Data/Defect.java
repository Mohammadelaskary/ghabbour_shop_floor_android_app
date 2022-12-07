package com.example.gbsbadrsf.Quality.Data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Defect implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("arName")
    @Expose
    private String arName;

    public Defect() {
    }

    public Defect(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    protected Defect(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        name = in.readString();
        arName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(name);
        dest.writeString(arName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Defect> CREATOR = new Creator<Defect>() {
        @Override
        public Defect createFromParcel(Parcel in) {
            return new Defect(in);
        }

        @Override
        public Defect[] newArray(int size) {
            return new Defect[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArName() {
        return arName;
    }
}
