package com.demo.intuit.model.inventory;

public class Status {
    long statusId;
    String statusName;

    public Status() {

    }

    public Status(long statusId, String statusName) {
        this.statusId = statusId;
        this.statusName = statusName;
    }

    public long getStatusId() {
        return statusId;
    }

    public void setStatusId(long statusId) {
        this.statusId = statusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
