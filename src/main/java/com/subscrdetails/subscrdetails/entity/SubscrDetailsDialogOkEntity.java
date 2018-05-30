package com.subscrdetails.subscrdetails.entity;

import com.subscrdetails.subscrdetails.utils.FiTranslit1;

public class SubscrDetailsDialogOkEntity {

    private String cgno;
    private double meter;
    private String dateOfCall;
    private String cdno;
    private String location;
    private String countryDescr;
    private double amount;
    private String timeofcall;

    public String getCgno() {
        return cgno;
    }

    public void setCgno(String cgno) {
        this.cgno = cgno;
    }

    public double getMeter() {
        return meter;
    }

    public void setMeter(double meter) {
        this.meter = meter;
    }

    public String getDateOfCall() {
        return dateOfCall;
    }

    public void setDateOfCall(String dateOfCall) {
        this.dateOfCall = dateOfCall;
    }

    public String getCdno() {
        return cdno;
    }

    public void setCdno(String cdno) {
        this.cdno = cdno;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCountryDescr() {
        return FiTranslit1.translit(countryDescr);
    }

    public void setCountryDescr(String countryDescr) {
        this.countryDescr = countryDescr;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTimeofcall() {
        return timeofcall;
    }

    public void setTimeofcall(String timeofcall) {
        this.timeofcall = timeofcall;
    }
}
