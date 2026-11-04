package com.aptech.session3;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;

@WebService(name = "GreetingService", targetNamespace = "urn:aptech:session3")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT,
    use = SOAPBinding.Use.LITERAL,
    parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface GreetingService {
    @WebMethod
    @WebResult(name = "message")
    String sayHello(@WebParam(name = "name") String name) throws CourseFault;

    @WebMethod
    @WebResult(name = "totalFees")
    int calculateCourseFees(@WebParam(name = "courseName") String courseName,
        @WebParam(name = "numberOfSemesters") int numberOfSemesters) throws CourseFault;

    @WebMethod
    @WebResult(name = "course")
    CourseInfo getCourseInfo(@WebParam(name = "courseName") String courseName)
        throws CourseFault;
}
