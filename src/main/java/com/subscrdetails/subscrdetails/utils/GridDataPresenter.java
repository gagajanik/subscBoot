package com.subscrdetails.subscrdetails.utils;
import java.util.List;

public class GridDataPresenter<T> {
    private List<T> rows;
    private long total;

    public List<T> getRows() { return rows; }
    public void setRows(List<T> value) { rows = value; }

    public long getTotal() { return total; }
    public void setTotal(long value) { total = value; }
}

