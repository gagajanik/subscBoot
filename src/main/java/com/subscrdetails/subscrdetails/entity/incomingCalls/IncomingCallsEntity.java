package com.subscrdetails.subscrdetails.entity.incomingCalls;

public class IncomingCallsEntity {
    private String cgno;
    private String dateOfCall;
    private String timeOfCall;
    private String durationOfCall;

    public String getCgno() {
        return cgno;
    }

    public void setCgno(String cgno) {
        this.cgno = cgno;
    }

    public String getDateOfCall() {
        return dateOfCall;
    }

    public void setDateOfCall(String dateOfCall) {
        this.dateOfCall = dateOfCall;
    }

    public String getTimeOfCall() {
        return timeOfCall;
    }

    public void setTimeOfCall(String timeOfCall) {
        this.timeOfCall = timeOfCall;
    }

    public String getDurationOfCall() {
        return durationOfCall;
    }

    public void setDurationOfCall(String durationOfCall) {
        this.durationOfCall = durationOfCall;
    }
}
