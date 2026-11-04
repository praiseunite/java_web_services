package com.aptech.jaxws;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.jws.soap.SOAPBinding.Style;

/**
 * Service Endpoint Interface (SEI).
 * 
 * For Beginners:
 * Think of this Interface as a "Contract" or "Menu".
 * It declares what services the server promises to provide to clients,
 * without revealing the internal behind-the-scenes code.
 */
@WebService
@SOAPBinding(style = Style.DOCUMENT)
public interface CalculatorService {

    /**
     * @WebMethod explicitly tells JAX-WS to expose this Java method
     * as an operation over SOAP XML.
     */
    @WebMethod
    int add(int num1, int num2);

    @WebMethod
    int subtract(int num1, int num2);

    @WebMethod
    String getGreeting(String studentName);
}
