package com.entity;

public class Call {
    private long id;
    private CallStatus status;
    private int durationInSeconds;

    public Call(long id, CallStatus status, int durationInSeconds) {
        this.id = id;
        this.status = status;
        this.durationInSeconds = durationInSeconds;
    }

    public synchronized long getId() {
        return id;
    }

    public synchronized void setId(long id) {
        this.id = id;
    }

    public synchronized CallStatus getStatus() {
        return status;
    }

    public synchronized void setStatus(CallStatus status) {
        this.status = status;
    }

    public synchronized int getDurationInSeconds() {
        return durationInSeconds;
    }

    public synchronized void setDurationInSeconds(int durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
    }

    @Override
    public String toString() {
        return "Call{" +
                "id=" + id +
                ", status=" + status +
                ", durationInSeconds=" + durationInSeconds +
                '}';
    }
}
