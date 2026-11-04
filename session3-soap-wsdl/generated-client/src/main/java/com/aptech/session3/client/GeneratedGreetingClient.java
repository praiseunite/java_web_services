package com.aptech.session3.client;

import java.net.URI;
import com.aptech.session3.generated.AptechGreetingService;
import com.aptech.session3.generated.GreetingService;
import com.aptech.session3.generated.CourseInfo;

public class GeneratedGreetingClient {
    public static void main(String[] args) throws Exception {
        int port = Integer.getInteger("soap.port", 8095);
        String address = "http://127.0.0.1:" + port + "/greeting";
        AptechGreetingService service = new AptechGreetingService(
            URI.create(address + "?wsdl").toURL());
        GreetingService proxy = service.getAptechGreetingPort();
        System.out.println(proxy.sayHello("Amina"));
        System.out.println("ADSE, 2 semesters: " + proxy.calculateCourseFees("ADSE", 2) + " USD");
        CourseInfo course = proxy.getCourseInfo("CPISM");
        System.out.println(course.getCode() + ": " + course.getFeePerSemester()
            + " " + course.getCurrency() + " per semester");
    }
}
