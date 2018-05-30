package com.subscrdetails.subscrdetails.entity.reports;

public class ValebiEntity {

    private double balance;
    private String balanceTime;

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getBalanceTime() {
        return balanceTime.substring(0,10);
    }

    public void setBalanceTime(String balanceTime) {
        this.balanceTime = balanceTime;
    }
}
