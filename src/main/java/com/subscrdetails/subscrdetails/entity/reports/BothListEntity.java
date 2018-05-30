package com.subscrdetails.subscrdetails.entity.reports;

import com.subscrdetails.subscrdetails.utils.FiTranslit1;

import java.util.List;

public class BothListEntity {
    private String abonenti;
    private List<ValebiEntity> valebi;
    private List<ListOneEntity> listOne;
    private List<ListTwoEntity> listTwo;

    public String getAbonenti() {
        return FiTranslit1.translit(abonenti);
    }

    public void setAbonenti(String abonenti) {
        this.abonenti = abonenti;
    }

    public List<ValebiEntity> getValebi() {
        return valebi;
    }

    public void setValebi(List<ValebiEntity> valebi) {
        this.valebi = valebi;
    }

    public List<ListOneEntity> getListOne() {
        return listOne;
    }

    public void setListOne(List<ListOneEntity> listOne) {
        this.listOne = listOne;
    }

    public List<ListTwoEntity> getListTwo() {
        return listTwo;
    }

    public void setListTwo(List<ListTwoEntity> listTwo) {
        this.listTwo = listTwo;
    }
}
