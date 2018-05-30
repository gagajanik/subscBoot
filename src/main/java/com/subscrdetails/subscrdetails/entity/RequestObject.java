package com.subscrdetails.subscrdetails.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RequestObject {
    private String phone;
    private String startDate;
    private String endDate;
    private String subscr;
    private int page;
    private int rows;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public String getSubscr() {
        return subscr;
    }

    public void setSubscr(String subscr) {
        this.subscr = subscr;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStartDate() throws ParseException {
        SimpleDateFormat dt = new SimpleDateFormat("mm/dd/yyyy");
        String date = dt.format(startDate);
        return date;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() throws ParseException {
        SimpleDateFormat dt = new SimpleDateFormat("dd-mm-yyyy");
        String date = dt.format(endDate);

        return date;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
