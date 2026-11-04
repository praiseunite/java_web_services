package com.aptech.practice;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;

@WebService(name = "LibraryService", targetNamespace = "urn:aptech:session4")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL,
    parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface LibraryService {
    @WebMethod
    @WebResult(name = "hours")
    String getLibraryHours();

    @WebMethod
    @WebResult(name = "title")
    String getBookTitle(@WebParam(name = "bookId") long bookId) throws LibraryFault;

    @WebMethod
    @WebResult(name = "chargeNGN")
    int calculatePrintCharge(@WebParam(name = "pages") int pages) throws LibraryFault;
}
