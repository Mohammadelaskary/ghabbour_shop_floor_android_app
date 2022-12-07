package com.example.gbsbadrsf.AddWorkers;

import com.example.gbsbadrsf.data.response.ResponseStatus;

import java.util.List;

public class ApiResponseGetWorkersList {
    private ResponseStatus responseStatus;
    private List<Worker> workers;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public List<Worker> getWorkers() {
        return workers;
    }
}
