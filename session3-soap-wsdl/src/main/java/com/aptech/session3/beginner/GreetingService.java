package com.aptech.session3.beginner;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;

@WebService(targetNamespace = "urn:aptech:session3:beginner")
public interface GreetingService {
    @WebMethod
    @WebResult(name = "message")
    String sayHello(@WebParam(name = "name") String name);
}
