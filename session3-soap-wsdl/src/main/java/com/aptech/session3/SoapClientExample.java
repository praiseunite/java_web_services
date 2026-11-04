package com.aptech.session3;

import java.net.URI;
import javax.xml.namespace.QName;
import jakarta.xml.ws.Service;

public class SoapClientExample {
    public static void main(String[] args) throws Exception {
        int port = Integer.getInteger("soap.port", 8095);
        String address = "http://127.0.0.1:" + port + "/greeting";
        QName serviceName = new QName("urn:aptech:session3", "AptechGreetingService");
        QName portName = new QName("urn:aptech:session3", "AptechGreetingPort");
        Service service = Service.create(URI.create(address + "?wsdl").toURL(), serviceName);
        GreetingService proxy = service.getPort(portName, GreetingService.class);
        System.out.println(proxy.sayHello("Amina"));
        System.out.println("ADSE, 2 semesters: " + proxy.calculateCourseFees("ADSE", 2) + " USD");
        CourseInfo course = proxy.getCourseInfo("CPISM");
        System.out.println(course.getCode() + ": " + course.getFeePerSemester()
            + " " + course.getCurrency() + " per semester");
        try {
            proxy.calculateCourseFees("ADSE", 0);
        } catch (CourseFault fault) {
            System.out.println("Rejected input: " + fault.getFaultInfo().getReason());
        }
    }
}
