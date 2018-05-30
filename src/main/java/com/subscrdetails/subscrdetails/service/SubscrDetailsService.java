package com.subscrdetails.subscrdetails.service;

import com.subscrdetails.subscrdetails.dao.SubscrDetailsDao;
import com.subscrdetails.subscrdetails.entity.incomingCalls.IncomingCallsEntity;
import com.subscrdetails.subscrdetails.entity.outsideCallsTab.OutsideCallsTabEntity;
import com.subscrdetails.subscrdetails.entity.reports.BothListEntity;
import com.subscrdetails.subscrdetails.entity.reports.ListTwoEntity;
import com.subscrdetails.subscrdetails.reporting.ReportType;
import com.subscrdetails.subscrdetails.reporting.ReportUtils;
import com.subscrdetails.subscrdetails.reporting.Reporter;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SubscrDetailsService {
    @Autowired
    private SubscrDetailsDao subscrDetailsDao;

    public String getSubscrDetails(String phone){
        ObjectMapper mapper=new ObjectMapper();
        String result="";
        try {
            result=mapper.writeValueAsString(subscrDetailsDao.getSubscrDetails(phone));
        }catch (Exception ex){
            System.err.println(ex.getMessage());
        }
        return result;
    }

    public String getSubscrDetailsTab1Ok1(String phone, String startDate, String endDate){
        ObjectMapper mapper=new ObjectMapper();
        String result="";
        try {
            result=mapper.writeValueAsString(subscrDetailsDao.getSubscrDetailsTab1Ok1(phone, startDate, endDate));
        }catch (Exception ex){
            System.err.println(ex.getMessage());
        }
        return result;
    }

    public String getSubscrDetailsTab1Ok2(String phone, String subscr, String startDate, String endDate){
        ObjectMapper mapper=new ObjectMapper();
        String result="";
        try {
            result=mapper.writeValueAsString(subscrDetailsDao.getSubscrDetailsTab1Ok2(phone, subscr, startDate, endDate));
        }catch (Exception ex){
            System.err.println(ex.getMessage());
        }
        return result;
    }

    public List<BothListEntity> getTwoList(String phone, String subscr, String startDate, String endDate) {
       return subscrDetailsDao.getTwoList(phone, subscr, startDate, endDate);
    }

    public List<BothListEntity> getTwoListCurrent(String phone, String subscr, String startDate, String endDate) {
        return subscrDetailsDao.getTwoListCurrent(phone, subscr, startDate, endDate);
    }
    public List<BothListEntity> getOneNumberPrint(String phone, String subscr, String startDate, String endDate){
        return subscrDetailsDao.getOneNumberPrint(phone, subscr, startDate, endDate);
    }
    public List<BothListEntity> getOneNumberPrint1(String phone, String subscr, String startDate, String endDate){
        return subscrDetailsDao.getOneNumberPrint1(phone, subscr, startDate, endDate);
    }

    public List<ListTwoEntity> getAnatherTwoceReport(String phone, String startDate, String endDate){
        return subscrDetailsDao.getAnatherTwoceReport(phone, startDate, endDate);
    }
    public List<IncomingCallsEntity> getIncomingCallsReport(String phone, String startDate, String endDate){
        return subscrDetailsDao.getIncomingCallsReport(phone, startDate, endDate);
    }

    public String getOutsideTabData( String phone, String startDate, String endDate){
        ObjectMapper mapper=new ObjectMapper();
        String result="";
        try {
            result=mapper.writeValueAsString(subscrDetailsDao.getOutsideTabData(phone, startDate, endDate));
        }catch (Exception ex){
            System.err.println(ex.getMessage());
        }
        return result;
    }

    public String getIncomingCallsData(String phone, String startDate, String endDate){
        ObjectMapper mapper=new ObjectMapper();
        String result="";
        try {
            result=mapper.writeValueAsString(subscrDetailsDao.getIncomingCallsData(phone, startDate, endDate));
        }catch (Exception ex){
            System.err.println(ex.getMessage());
        }
        return result;
    }

    public String getLocalCallsData(String phone, String startDate, String endDate){
        ObjectMapper mapper=new ObjectMapper();
        String result="";
        try {
            result=mapper.writeValueAsString(subscrDetailsDao.getlocalCallsData(phone, startDate, endDate));
        }catch (Exception ex){
            System.err.println(ex.getMessage());
        }
        return result;
    }

    public List<IncomingCallsEntity> getLocalCallsReport(String phone, String startDate, String endDate){
        return subscrDetailsDao.getlocalCallsData(phone, startDate, endDate);
    }
    public List<OutsideCallsTabEntity> getTab2Print(String phone, String startDate, String endDate){
       return subscrDetailsDao.getOutsideTabData(phone, startDate, endDate);
    }
   public String getAccountmentData(String phone, String subscrNum, String startDate, String endDate){
       ObjectMapper mapper=new ObjectMapper();
       String result="";
       try {
           result=mapper.writeValueAsString(subscrDetailsDao.getAccountmentData(phone,subscrNum, startDate, endDate));
       }catch (Exception ex){
           System.err.println(ex.getMessage());
       }
       return result;
   }

   public String getAccountmentCharge(String subscriber){
       ObjectMapper mapper=new ObjectMapper();
       String result="";
       try {
           result=mapper.writeValueAsString(subscrDetailsDao.getAccountmentCharge(subscriber));
       }catch (Exception ex){
           System.err.println(ex.getMessage());
       }
       return result;
   }

   public String getAmountmentGrids(String subscriber){
       ObjectMapper mapper=new ObjectMapper();
       String result="";
       try {
           result=mapper.writeValueAsString(subscrDetailsDao.getAmountmentGrids(subscriber));
       }catch (Exception ex){
           System.err.println(ex.getMessage());
       }
       return result;
   }

    public String getAmountmentGridsYear(String subscriber){
        ObjectMapper mapper=new ObjectMapper();
        String result="";
        try {
            result=mapper.writeValueAsString(subscrDetailsDao.getAmountmentGridsYear(subscriber));
        }catch (Exception ex){
            System.err.println(ex.getMessage());
        }
        return result;
    }


}
