package com.aptech.session3;

import jakarta.xml.ws.WebFault;

@WebFault(name = "CourseFault", targetNamespace = "urn:aptech:session3")
public class CourseFault extends Exception {
    private final CourseFaultInfo faultInfo;

    public CourseFault(String message) {
        this(message, new CourseFaultInfo(message));
    }

    public CourseFault(String message, CourseFaultInfo faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    public CourseFault(String message, CourseFaultInfo faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    public CourseFaultInfo getFaultInfo() { return faultInfo; }
}
