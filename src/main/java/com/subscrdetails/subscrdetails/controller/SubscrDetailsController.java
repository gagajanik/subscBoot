package com.subscrdetails.subscrdetails.controller;

import com.subscrdetails.subscrdetails.dao.SubscrDetailsDao;
import com.subscrdetails.subscrdetails.entity.RequestObject;
import com.subscrdetails.subscrdetails.entity.reports.ListTwoEntity;
import com.subscrdetails.subscrdetails.service.SubscrDetailsService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.jasperreports.JasperReportsPdfView;
import sun.util.calendar.BaseCalendar;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
//@RequestMapping("/api")
public class SubscrDetailsController {
    @Autowired
    private SubscrDetailsService subscrDetailsService;

    @RequestMapping("subscrDet")
    public String getSubscrDetails(String phone){
        return subscrDetailsService.getSubscrDetails(phone);
    }

    @RequestMapping(value = "subscrDetTab1Ok1", headers = {"Accept=application/json;charset=UTF-8"},
            produces = "application/json;charset=UTF-8")
    public String getSubscrDetailsTab1Ok(RequestObject requestObject) throws ParseException {
        return subscrDetailsService.getSubscrDetailsTab1Ok1(requestObject.getPhone(), requestObject.getStartDate(), requestObject.getEndDate());
    }

    @RequestMapping(value = "subscrDetTab1Ok2", headers = {"Accept=application/json;charset=UTF-8"},
            produces = "application/json;charset=UTF-8")
    public String getSubscrDetailsTab1Ok2(RequestObject requestObject) throws ParseException {
        return subscrDetailsService.getSubscrDetailsTab1Ok2(requestObject.getPhone(), requestObject.getSubscr(), requestObject.getStartDate(), requestObject.getEndDate());
    }

    /*@RequestMapping(value = "twoList", headers = {"Accept=application/x-pdf;charset=UTF-8"},
            produces = "application/x-pdf;charset=UTF-8")
    public  void getTwoList(HttpServletResponse response, String phone, String subscr, String startDate, String endDate ) throws IOException {
        response.setContentType("application/x-pdf");
        response.setHeader("Content-Disposition","inline; filename=test.pdf");
        subscrDetailsService.getTwoList(response, phone, subscr,startDate, endDate);
    }*/

    @RequestMapping(value = "repo")
    public @ResponseBody void getReport(HttpServletResponse response, String phone, String subscr, String startDate, String endDate){
        try {
            Date dt=new Date();
            InputStream jasperStream=this.getClass().getResourceAsStream("/jasper/report1.jrxml");
            JasperDesign design= JRXmlLoader.load(jasperStream);
            JasperReport report= JasperCompileManager.compileReport(design);
            Map<String,Object> parameterMap=new HashMap<>();;
            JRDataSource jrDataSource=new JRBeanCollectionDataSource(subscrDetailsService.getTwoList(phone, subscr, startDate, endDate));
            parameterMap.put("datasource",jrDataSource);
            parameterMap.put("p_startDate", startDate);
            parameterMap.put("p_endDate", endDate);
            parameterMap.put("p_curDate",dt.toString());
            JasperPrint jasperPrint= JasperFillManager.fillReport(report,parameterMap,jrDataSource);
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition","inline;filename=firstTab1Report.pdf");
            final OutputStream outputStream=response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

        }catch (Exception ex){
            System.err.println(ex.getMessage());
        }

    }

    @RequestMapping(value = "repo1")
    public @ResponseBody void getReportCurrent(HttpServletResponse response, String phone, String subscr, String startDate, String endDate){
        try {
            Date dt=new Date();
            InputStream jasperStream=this.getClass().getResourceAsStream("/jasper/report1.jrxml");
            JasperDesign design= JRXmlLoader.load(jasperStream);
            JasperReport report= JasperCompileManager.compileReport(design);
            Map<String,Object> parameterMap=new HashMap<>();
            JRDataSource jrDataSource=new JRBeanCollectionDataSource(subscrDetailsService.getTwoListCurrent(phone, subscr, startDate, endDate));
            parameterMap.put("datasource",jrDataSource);
            parameterMap.put("p_startDate", startDate);
            parameterMap.put("p_endDate", endDate);
            parameterMap.put("p_curDate",dt.toString());
            JasperPrint jasperPrint= JasperFillManager.fillReport(report,parameterMap,jrDataSource);
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition","inline;filename=firstTab1ReportCurrent.pdf");
            final OutputStream outputStream=response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

        }catch (Exception ex){
            System.err.println(ex.getMessage());
        }

    }

    @RequestMapping(value = "oneNumberRepo")
    public @ResponseBody void getOneNumberPrint(HttpServletResponse response, String phone, String subscr, String startDate, String endDate){
        try {
            Date dt=new Date();
            InputStream jasperStream=this.getClass().getResourceAsStream("/jasper/report1.jrxml");
            JasperDesign design= JRXmlLoader.load(jasperStream);
            JasperReport report= JasperCompileManager.compileReport(design);
            Map<String,Object> parameterMap=new HashMap<>();
            JRDataSource jrDataSource=new JRBeanCollectionDataSource(subscrDetailsService.getOneNumberPrint(phone, subscr, startDate, endDate));
            parameterMap.put("datasource",jrDataSource);
            parameterMap.put("p_startDate", startDate);
            parameterMap.put("p_endDate", endDate);
            parameterMap.put("p_curDate",dt.toString());
            JasperPrint jasperPrint= JasperFillManager.fillReport(report,parameterMap,jrDataSource);
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition","inline;filename=oneNumberPrintRepo.pdf");
            final OutputStream outputStream=response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

        }catch (Exception ex){
            System.err.println(ex.getMessage());
        }

    }

    @RequestMapping(value = "oneNumberRepo1")
    public @ResponseBody void getOneNumberPrint1(HttpServletResponse response, String phone, String subscr, String startDate, String endDate){
        try {
            Date dt=new Date();
            InputStream jasperStream=this.getClass().getResourceAsStream("/jasper/report1.jrxml");
            JasperDesign design= JRXmlLoader.load(jasperStream);
            JasperReport report= JasperCompileManager.compileReport(design);
            Map<String,Object> parameterMap=new HashMap<>();
            JRDataSource jrDataSource=new JRBeanCollectionDataSource(subscrDetailsService.getOneNumberPrint1(phone, subscr, startDate, endDate));
            parameterMap.put("datasource",jrDataSource);
            parameterMap.put("p_startDate", startDate);
            parameterMap.put("p_endDate", endDate);
            parameterMap.put("p_curDate",dt.toString());
            JasperPrint jasperPrint= JasperFillManager.fillReport(report,parameterMap,jrDataSource);
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition","inline;filename=oneNumberPrintRepo1.pdf");
            final OutputStream outputStream=response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

        }catch (Exception ex){
            System.err.println(ex.getMessage());
        }

    }

    @RequestMapping(value = "anatherTwiceRepo")
    public @ResponseBody void getAnatherTwoceReport(HttpServletResponse response, String phone, String startDate, String endDate){
        try {
            Date dt=new Date();

            InputStream jasperStream=this.getClass().getResourceAsStream("/jasper/anatherTwiceReport.jrxml");
            JasperDesign design= JRXmlLoader.load(jasperStream);
            JasperReport report= JasperCompileManager.compileReport(design);
            Map<String,Object> parameterMap=new HashMap<>();
            JRDataSource jrDataSource=new JRBeanCollectionDataSource(subscrDetailsService.getAnatherTwoceReport(phone, startDate, endDate));
            parameterMap.put("datasource",jrDataSource);
            parameterMap.put("p_phone", phone);
            parameterMap.put("p_curDate",dt.toString());
            JasperPrint jasperPrint= JasperFillManager.fillReport(report,parameterMap,jrDataSource);
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition","inline;filename=anatherTwiceRepo.pdf");
            final OutputStream outputStream=response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

        }catch (Exception ex){
            System.err.println(ex.getMessage());
        }
    }
    @RequestMapping(value = "outsideTabData", headers = {"Accept=application/json;charset=UTF-8"},
            produces = "application/json;charset=UTF-8")
    public String getOutsideTabData(RequestObject requestObject) throws ParseException {
        return subscrDetailsService.getOutsideTabData(requestObject.getPhone(), requestObject.getStartDate(), requestObject.getEndDate());
    }
    @RequestMapping(value = "incomingCallsData", headers = {"Accept=application/json;charset=UTF-8"},
            produces = "application/json;charset=UTF-8")
    public String getIncomingCallsData(RequestObject requestObject) throws ParseException {
        return subscrDetailsService.getIncomingCallsData(requestObject.getPhone(), requestObject.getStartDate(), requestObject.getEndDate());
    }
    @RequestMapping(value = "localCallsData", headers = {"Accept=application/json;charset=UTF-8"},
            produces = "application/json;charset=UTF-8")
    public String getLocalCallsData(RequestObject requestObject) throws ParseException {
        return subscrDetailsService.getLocalCallsData(requestObject.getPhone(), requestObject.getStartDate(), requestObject.getEndDate());
    }
    @RequestMapping(value = "accountmentData", headers = {"Accept=application/json;charset=UTF-8"},
            produces = "application/json;charset=UTF-8")
    public String getAccountmentData(RequestObject requestObject) throws ParseException {
        return subscrDetailsService.getAccountmentData(requestObject.getPhone(), requestObject.getSubscr(),
                                                   requestObject.getStartDate(), requestObject.getEndDate());
    }
    @RequestMapping(value = "incommingCallsReport")
    public @ResponseBody void getIncomingCallsReport(HttpServletResponse response, String phone, String startDate, String endDate){
        try {
            Date dt=new Date();

            InputStream jasperStream=this.getClass().getResourceAsStream("/jasper/incomingCallsReport.jrxml");
            JasperDesign design= JRXmlLoader.load(jasperStream);
            JasperReport report= JasperCompileManager.compileReport(design);
            Map<String,Object> parameterMap=new HashMap<>();
            JRDataSource jrDataSource=new JRBeanCollectionDataSource(subscrDetailsService.getIncomingCallsReport(phone, startDate, endDate));
            parameterMap.put("datasource",jrDataSource);
            parameterMap.put("p_phone", phone);
            parameterMap.put("p_curDate",dt.toString());
            JasperPrint jasperPrint= JasperFillManager.fillReport(report,parameterMap,jrDataSource);
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition","inline;filename=incomingCallsReport.pdf");
            final OutputStream outputStream=response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

        }catch (Exception ex){
            System.err.println(ex.getMessage());
        }
    }
    @RequestMapping(value = "localCallsReport")
    public @ResponseBody void getLocalCallsReport(HttpServletResponse response, String phone, String startDate, String endDate){
        try {
            Date dt=new Date();

            InputStream jasperStream=this.getClass().getResourceAsStream("/jasper/localCallsReport.jrxml");
            JasperDesign design= JRXmlLoader.load(jasperStream);
            JasperReport report= JasperCompileManager.compileReport(design);
            Map<String,Object> parameterMap=new HashMap<>();
            JRDataSource jrDataSource=new JRBeanCollectionDataSource(subscrDetailsService.getLocalCallsReport(phone, startDate, endDate));
            parameterMap.put("datasource",jrDataSource);
            parameterMap.put("p_phone", phone);
            parameterMap.put("p_curDate",dt.toString());
            JasperPrint jasperPrint= JasperFillManager.fillReport(report,parameterMap,jrDataSource);
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition","inline;filename=localCallsReport.pdf");
            final OutputStream outputStream=response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

        }catch (Exception ex){
            System.err.println(ex.getMessage());
        }
    }
    @RequestMapping(value = "tab2Print")
    public @ResponseBody void getTab2Print(HttpServletResponse response, String phone, String startDate, String endDate){
        try {
            Date dt=new Date();

            InputStream jasperStream=this.getClass().getResourceAsStream("/jasper/tab2PrintReport.jrxml");
            JasperDesign design= JRXmlLoader.load(jasperStream);
            JasperReport report= JasperCompileManager.compileReport(design);
            Map<String,Object> parameterMap=new HashMap<>();
            JRDataSource jrDataSource=new JRBeanCollectionDataSource(subscrDetailsService.getTab2Print(phone, startDate, endDate));
            parameterMap.put("datasource",jrDataSource);
            parameterMap.put("p_phone", phone);
            parameterMap.put("p_curDate",dt.toString());
            JasperPrint jasperPrint= JasperFillManager.fillReport(report,parameterMap,jrDataSource);
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition","inline;filename=tab2PrintReport.pdf");
            final OutputStream outputStream=response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

        }catch (Exception ex){
            System.err.println(ex.getMessage());
        }
    }

    @RequestMapping(value = "accountmentCharge")
    public @ResponseBody String getAccountmentCharge(String subscriber){
        return subscrDetailsService.getAccountmentCharge(subscriber);
    }
    @RequestMapping(value = "accountmentGrids", headers = {"Accept=application/json;charset=UTF-8"},
            produces = "application/json;charset=UTF-8")
    public @ResponseBody String getAmountmentGrids(String subscriber){
        return subscrDetailsService.getAmountmentGrids(subscriber);
    }

    @RequestMapping(value = "accountmentGridsYear", headers = {"Accept=application/json;charset=UTF-8"},
            produces = "application/json;charset=UTF-8")
    public @ResponseBody String getAmountmentGridsYear(String subscriber){
        return subscrDetailsService.getAmountmentGridsYear(subscriber);
    }



}
