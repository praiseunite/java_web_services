package com.aptech.session3;

import jakarta.xml.bind.annotation.XmlType;

@XmlType(name = "CourseInfo", namespace = "urn:aptech:session3",
    propOrder = {"code", "feePerSemester", "currency"})
public class CourseInfo {
    private String code;
    private int feePerSemester;
    private String currency;

    public CourseInfo() { }

    public CourseInfo(String code, int feePerSemester, String currency) {
        this.code = code;
        this.feePerSemester = feePerSemester;
        this.currency = currency;
    }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public int getFeePerSemester() { return feePerSemester; }
    public void setFeePerSemester(int feePerSemester) { this.feePerSemester = feePerSemester; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
}
