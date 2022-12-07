package com.example.gbsbadrsf.Quality.paint.RandomQualityInception.PprListFragment;

import com.example.gbsbadrsf.Quality.paint.Model.LastMovePainting;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponseGetStationInfoForQuality_Painting {

    @SerializedName("responseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("lastMovePaintings")
    @Expose
    private List<LastMovePaintingOnline> lastMovePaintings = null;

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
}
