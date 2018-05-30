package com.subscrdetails.subscrdetails.reporting;

public enum ReportType {
    EXCEL("xls"),
    PDF("pdf"),
    CSV("csv"),
    EXCEL2007("xlsx");

    private String value;
    private ReportType(String value) { this.value = value; }
    public String getValue() { return value; }
    public static ReportType fromString(String str_value) {
        switch (str_value.toLowerCase()) {
            case "xls":
                return EXCEL;
            case "pdf":
                return PDF;
            case "csv":
                return CSV;
            case "xlsx":
                return EXCEL2007;
        }
        return null;
    }
}
