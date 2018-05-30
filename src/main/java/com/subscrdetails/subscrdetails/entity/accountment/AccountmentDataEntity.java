package com.subscrdetails.subscrdetails.entity.accountment;

import com.subscrdetails.subscrdetails.utils.FiTranslit1;

public class AccountmentDataEntity {
    private String phoneNo;
    private String description;
    private double amount;
    private String operationDate;

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getDescription() {
        return FiTranslit1.translit(description);
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
        return operationDate;
    }

    public void setOperationDate(String operationDate) {
        this.operationDate = operationDate;
    }
}
