package com.example.gbsbadrsf.AddWorkers;

public class Worker {
    private String workerArName;
    private String workerEnName;
    private int workerId;
    private String workerCode;

    public Worker(String workerArName, String workerEnName, int workerId, String workerCode) {
        this.workerArName = workerArName;
        this.workerEnName = workerEnName;
        this.workerId = workerId;
        this.workerCode = workerCode;
    }

    public String getWorkerEnName() {
        return workerEnName;
    }

    public void setWorkerEnName(String workerEnName) {
        this.workerEnName = workerEnName;
    }

    public String getWorkerArName() {
        return workerArName;
    }

    public void setWorkerArName(String workerArName) {
        this.workerArName = workerArName;
    }

    public int getWorkerId() {
        return workerId;
    }

    public void setWorkerId(int workerId) {
        this.workerId = workerId;
    }

    public String getWorkerCode() {
        return workerCode!=null?workerCode:"";
    }

    public void setWorkerCode(String workerCode) {
        this.workerCode = workerCode;
    }
}
