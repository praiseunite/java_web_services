package com.aptech.practice;

import jakarta.xml.bind.annotation.XmlType;

@XmlType(name = "LibraryFaultInfo", namespace = "urn:aptech:session4")
public class LibraryFaultInfo {
    private String reason;

    public LibraryFaultInfo() {
    }

    public LibraryFaultInfo(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
