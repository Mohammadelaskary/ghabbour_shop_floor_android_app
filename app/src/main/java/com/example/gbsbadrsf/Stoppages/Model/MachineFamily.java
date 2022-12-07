package com.example.gbsbadrsf.Stoppages.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MachineFamily {

    @SerializedName("machineFamilyId")
    @Expose
    private Integer machineFamilyId;
    @SerializedName("machineFamilyName")
    @Expose
    private String machineFamilyName;

    public Integer getMachineFamilyId() {
        return machineFamilyId;
    }

    public void setMachineFamilyId(Integer machineFamilyId) {
        this.machineFamilyId = machineFamilyId;
    }

    public String getMachineFamilyName() {
        return machineFamilyName;
    }

    public void setMachineFamilyName(String machineFamilyName) {
        this.machineFamilyName = machineFamilyName;
    }

    @Override
    public String toString() {
        return machineFamilyName;
    }
}
