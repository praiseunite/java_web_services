package com.aptech.session3;

import jakarta.xml.ws.Endpoint;

public class SoapServicePublisher {
    public static void main(String[] args) {
        int port = Integer.getInteger("soap.port", 8095);
        String address = "http://127.0.0.1:" + port + "/greeting";
        Endpoint endpoint = Endpoint.publish(address, new GreetingServiceImpl());
        Runtime.getRuntime().addShutdownHook(new Thread(endpoint::stop));
        System.out.println("SOAP service: " + address);
        System.out.println("WSDL contract: " + address + "?wsdl");
        System.out.println("Keep terminal A open. Press Ctrl+C to stop.");
    }
}
