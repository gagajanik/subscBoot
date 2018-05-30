package com.subscrdetails.subscrdetails.entity.reports;

import com.subscrdetails.subscrdetails.utils.FiTranslit1;

public class ListTwoEntity {
    private String cgno;
    private String cdno;
    private String dateOfCall;
    private String timeOfCall;
    private double meter;
    private double amount;
    private String countryDescr;

    public String getCountryDescr() {
        return (countryDescr!=null)? FiTranslit1.translit(countryDescr):"";
    }

    public void setCountryDescr(String countryDescr) {
        this.countryDescr = countryDescr;
    }

    public String getCgno() {
        return cgno;
    }

    public void setCgno(String cgno) {
        this.cgno = cgno;
    }

    public String getCdno() {
        return cdno;
    }

    public void setCdno(String cdno) {
        this.cdno = cdno;
    }

    public String getDateOfCall() {
        return dateOfCall.substring(0,10);
    }

    public void setDateOfCall(String dateOfCall) {
        this.dateOfCall = dateOfCall;
    }

    public String getTimeOfCall() {
        return (timeOfCall!=null)?timeOfCall:"";
    }

    public void setTimeOfCall(String timeOfCall) {
        this.timeOfCall = timeOfCall;
    }

    public double getMeter() {
        return meter;
    }

    public void setMeter(double meter) {
        this.meter = meter;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
