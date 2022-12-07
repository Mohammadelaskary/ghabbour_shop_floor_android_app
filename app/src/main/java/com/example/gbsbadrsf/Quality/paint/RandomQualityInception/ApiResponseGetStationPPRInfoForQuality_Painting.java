package com.example.gbsbadrsf.Quality.paint.RandomQualityInception;

import com.example.gbsbadrsf.Quality.paint.Model.LastMovePainting;
import com.example.gbsbadrsf.Quality.paint.Model.PaintingDefect;
import com.example.gbsbadrsf.Quality.paint.RandomQualityInception.PprListFragment.LastMovePaintingOnline;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponseGetStationPPRInfoForQuality_Painting {

    @SerializedName("responseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("lastMovePaintings")
    @Expose
    private List<LastMovePaintingOnline> lastMovePaintings = null;
    @SerializedName("paintingDefects")
    @Expose
    private List<PaintingDefect> paintingDefects = null;
    @SerializedName("minQtyApproved")
    @Expose
    private Integer minQtyApproved;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<LastMovePaintingOnline> getLastMovePaintings() {
        return lastMovePaintings;
    }

    public void setLastMovePaintings(List<LastMovePaintingOnline> lastMovePaintings) {
        this.lastMovePaintings = lastMovePaintings;
    }

    public List<PaintingDefect> getPaintingDefects() {
        return paintingDefects;
    }

    public void setPaintingDefects(List<PaintingDefect> paintingDefects) {
        this.paintingDefects = paintingDefects;
    }

    public Integer getMinQtyApproved() {
        return minQtyApproved;
    }

    public void setMinQtyApproved(Integer minQtyApproved) {
        this.minQtyApproved = minQtyApproved;
    }

}
