package com.subscrdetails.subscrdetails.reporting;

public class ReportUtils {
    public static String getContentTypeByReportType(ReportType reportType) {
        switch (reportType) {
            case EXCEL:
                return "application/vnd.ms-excel";
            case PDF:
                return "application/pdf";
            case CSV:
                return "text/csv";
            case EXCEL2007:
                return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        }
        return null;
    }

    public static String getFilenameByReportType(String fileNameRaw, ReportType reportType) {
        switch (reportType) {
            case EXCEL:
                return fileNameRaw + ".xls";
            case PDF:
                return fileNameRaw + ".pdf";
            case CSV:
                return fileNameRaw + ".csv";
            case EXCEL2007:
                return fileNameRaw + ".xlsx";
        }
        return fileNameRaw;
    }
}
