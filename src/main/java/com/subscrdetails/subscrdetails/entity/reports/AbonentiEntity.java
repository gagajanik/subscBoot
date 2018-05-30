package com.subscrdetails.subscrdetails.entity.reports;


import com.subscrdetails.subscrdetails.utils.FiTranslit1;

public class AbonentiEntity {

    private String abonenti;

    public String getAbonenti() {
        return FiTranslit1.translit(abonenti);
    }

    public void setAbonenti(String abonenti) {
        this.abonenti = abonenti;
    }
}
