package com.example.gbsbadrsf.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.gbsbadrsf.Quality.Data.Defect;

import java.util.List;

public class DefectsPerQty implements Parcelable {
    private int mainDefectId;
    private int groupId;
    private int qty;
    private List<String> defectsNames;
    private List<Integer> defectsIds;
    private boolean isRejected;


    public DefectsPerQty() {
    }

    public DefectsPerQty(int mainDefectId, int groupId, int qty, List<String> defectsNames, List<Integer> defectsIds, boolean isRejected) {
        this.mainDefectId = mainDefectId;
        this.groupId = groupId;
        this.qty = qty;
        this.defectsNames = defectsNames;
        this.defectsIds = defectsIds;
        this.isRejected = isRejected;
    }

    protected DefectsPerQty(Parcel in) {
        mainDefectId = in.readInt();
        groupId = in.readInt();
        qty = in.readInt();
        defectsNames = in.createStringArrayList();
        isRejected = in.readByte() != 0;
    }

    public static final Creator<DefectsPerQty> CREATOR = new Creator<DefectsPerQty>() {
        @Override
        public DefectsPerQty createFromParcel(Parcel in) {
            return new DefectsPerQty(in);
        }

        @Override
        public DefectsPerQty[] newArray(int size) {
            return new DefectsPerQty[size];
        }
    };

    public int getMainDefectId() {
        return mainDefectId;
    }

    public void setMainDefectId(int mainDefectId) {
        this.mainDefectId = mainDefectId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int id) {
        this.groupId = id;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public List<String> getDefectsNames() {
        return defectsNames;
    }

    public void setDefectsNames(List<String> defectsNames) {
        this.defectsNames = defectsNames;
    }

    public List<Integer> getDefectsIds() {
        return defectsIds;
    }

    public void setDefectsIds(List<Integer> defectsIds) {
        this.defectsIds = defectsIds;
    }

    public boolean isRejected() {
        return isRejected;
    }

    public void setRejected(boolean rejected) {
        isRejected = rejected;
    }

    public String getDefectsString (){
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < defectsNames.size(); i++) {
            if (i== defectsIds.size()-1){
                builder.append(defectsNames.get(i));
            } else {
                builder.append(defectsNames.get(i)).append(",").append("\n");
            }
        }
        return builder.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mainDefectId);
        dest.writeInt(groupId);
        dest.writeInt(qty);
        dest.writeStringList(defectsNames);
        dest.writeByte((byte) (isRejected ? 1 : 0));
    }
}
