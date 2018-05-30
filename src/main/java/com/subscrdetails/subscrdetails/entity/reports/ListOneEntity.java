package com.subscrdetails.subscrdetails.entity.reports;


import com.subscrdetails.subscrdetails.utils.FiTranslit1;

public class ListOneEntity {
    private String subscriber;
    private String phoneNo;
    private String description;
    private double amount;
    private String operationDate;

    public String getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(String subscriber) {
        this.subscriber = subscriber;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getDescription() {
        return (description!=null)? FiTranslit1.translit(description):"";
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getOperationDate() {
        return operationDate.substring(0,10);
    }

    public void setOperationDate(String operationDate) {
        this.operationDate = operationDate;
    }
}
