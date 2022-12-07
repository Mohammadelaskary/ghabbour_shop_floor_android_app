package com.example.gbsbadrsf.Quality.paint.PaintRejectionRequest.ApprovalRejectionRequest_Paint;

import com.example.gbsbadrsf.Quality.manfacturing.Model.RejectionRequest;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponsePaintingRejectionRequestGetRejectionRequestList {

    @SerializedName("responseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("rejectionRequest")
    @Expose
    private List<RejectionRequest> rejectionRequestList = null;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<RejectionRequest> getRejectionRequestList() {
        return rejectionRequestList;
    }

    public void setRejectionRequestList(List<RejectionRequest> rejectionRequestList) {
        this.rejectionRequestList = rejectionRequestList;
    }
}
