package com.aptech.session3.beginner;

import jakarta.xml.ws.Endpoint;

public class GreetingPublisher {
    public static void main(String[] args) {
        int port = Integer.getInteger("soap.port", 8095);
        String address = "http://127.0.0.1:" + port + "/beginner";
        Endpoint endpoint = Endpoint.publish(address, new GreetingServiceImpl());
        Runtime.getRuntime().addShutdownHook(new Thread(endpoint::stop));
        System.out.println("Beginner SOAP service: " + address);
        System.out.println("WSDL: " + address + "?wsdl");
        System.out.println("Keep terminal A open. Press Ctrl+C to stop.");
    }
}
