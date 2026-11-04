package com.aptech.session3;

import jakarta.xml.bind.annotation.XmlType;

@XmlType(name = "CourseFaultInfo", namespace = "urn:aptech:session3")
public class CourseFaultInfo {
    private String reason;

    public CourseFaultInfo() { }
    public CourseFaultInfo(String reason) { this.reason = reason; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
}
