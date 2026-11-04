package com.aptech.practice;

import jakarta.xml.ws.WebFault;

@WebFault(name = "LibraryFault", targetNamespace = "urn:aptech:session4",
    faultBean = "com.aptech.practice.LibraryFaultInfo")
public class LibraryFault extends Exception {
    private final LibraryFaultInfo faultInfo;

    public LibraryFault(String message) {
        this(message, new LibraryFaultInfo(message));
    }

    public LibraryFault(String message, LibraryFaultInfo faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    public LibraryFault(String message, LibraryFaultInfo faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    public LibraryFaultInfo getFaultInfo() {
        return faultInfo;
    }
}
