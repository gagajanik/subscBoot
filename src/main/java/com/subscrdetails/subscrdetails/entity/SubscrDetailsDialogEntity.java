package com.subscrdetails.subscrdetails.entity;

import com.subscrdetails.subscrdetails.utils.FiTranslit1;

public class SubscrDetailsDialogEntity {

    private String abonenti;
    private String subscrNum;
    private String installDate;
    private String endDate;

    public String getAbonenti() {
        return FiTranslit1.translit(abonenti);
    }

    public void setAbonenti(String abonenti) {
        this.abonenti = abonenti;
    }

    public String getSubscrNum() {
        return subscrNum;
    }

    public void setSubscrNum(String subscrNum) {
        this.subscrNum = subscrNum;
    }

    public String getInstallDate() {
        return installDate;
    }

    public void setInstallDate(String installDate) {
        this.installDate = installDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
