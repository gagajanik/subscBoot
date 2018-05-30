package com.subscrdetails.subscrdetails.reporting;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.Collection;
import java.util.Map;

public class Reporter {

    protected final Logger logger = Logger.getLogger(Reporter.class);

    private JasperReport jasperReport;
    private String outputFile;
    private OutputStream outputStream;
    private HttpServletResponse servletResponse;
    private boolean exportToFile;
    private boolean streamShouldClose = false;

    public Reporter(String jrxml, String fileName) {
        outputFile = fileName;
        exportToFile = true;
        initJasper(jrxml);
    }

    public Reporter(String jrxml, OutputStream outStream) {
        this(jrxml, outStream, false);
    }

    public Reporter(String jrxml, OutputStream outStream, boolean autoClose) {
        outputStream = outStream;
        streamShouldClose = autoClose;
        exportToFile = false;
        initJasper(jrxml);
    }

    public Reporter(String jrxml, HttpServletResponse response) {
        this(jrxml, response, null);
    }

    public Reporter(String jrxml, HttpServletResponse response, String inlineFileName) {
        try {
            outputStream = response.getOutputStream();
        } catch (IOException ioex) { logger.error(ioex.getMessage()); }
        servletResponse = response;
        outputFile = inlineFileName;
        exportToFile = false;
        streamShouldClose = true;
        initJasper(jrxml);
    }

    private void initJasper(String jrxml) {
        try {
            try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(jrxml)) {
                jasperReport = JasperCompileManager.compileReport(inputStream);
            } catch (JRException jrex) {
                logger.error(jrex.getMessage());
            }
        } catch (IOException ioex) {
            logger.warn(ioex.getMessage());
        }
    }

    public boolean export(ReportType reportType, Map<String, Object> params, Connection connection) {
        if (jasperReport == null)
            return false;
        boolean result = true;
        try {
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, connection);
            export(reportType, jasperPrint);
        } catch (JRException jrex) {
            result = false;
            logger.error(jrex.getMessage());
        }
        return result;
    }

    public boolean export(ReportType reportType, Map<String, Object> params, Collection collection) {
        if (jasperReport == null)
            return false;
        boolean result = true;
        try {
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JRBeanCollectionDataSource(collection));
            export(reportType, jasperPrint);
        } catch (JRException jrex) {
            result = false;
            logger.error(jrex.getMessage());
        }
        return result;
    }

    public boolean export(ReportType reportType, Map<String, Object> params, Integer phone) {
        if (jasperReport == null)
            return false;
        boolean result = true;
        try {
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params);
            export(reportType, jasperPrint);
        } catch (JRException jrex) {
            result = false;
            logger.error(jrex.getMessage());
        }
        return result;
    }

    private void export(ReportType reportType, JasperPrint jasperPrint) throws JRException {
        if (!exportToFile && servletResponse != null) {
            servletResponse.setContentType(ReportUtils.getContentTypeByReportType(reportType));
            if (outputFile != null) {
                servletResponse.setHeader("Content-Disposition", "inline;filename=" + outputFile);
            }
        }
        switch (reportType) {
            case EXCEL:
                JRXlsExporter jrXlsExporter = new JRXlsExporter();
                jrXlsExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                if (exportToFile) {
                    jrXlsExporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outputFile);
                } else {
                    jrXlsExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
                }
                jrXlsExporter.exportReport();
                break;
            case PDF:
                if (exportToFile) {
                    JasperExportManager.exportReportToPdfFile(jasperPrint, outputFile);
                } else {
                    JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
                }
                break;
            case CSV:
                JRCsvExporter jrCsvExporter = new JRCsvExporter();
                jrCsvExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                if (exportToFile) {
                    jrCsvExporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outputFile);
                } else {
                    jrCsvExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
                }
                jrCsvExporter.exportReport();
                break;
            case EXCEL2007:
                JRXlsxExporter jrXlsxExporter = new JRXlsxExporter();
                jrXlsxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                if (exportToFile) {
                    jrXlsxExporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outputFile);
                } else {
                    jrXlsxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
                }
                jrXlsxExporter.exportReport();
                break;
        }
        if (!exportToFile && outputStream != null && streamShouldClose)
            try { outputStream.close(); } catch (IOException ioex) {}
    }
}
