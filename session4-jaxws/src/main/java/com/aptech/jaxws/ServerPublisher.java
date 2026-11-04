package com.aptech.jaxws;

import jakarta.xml.ws.Endpoint;

/**
 * Server Publisher.
 * 
 * For Beginners:
 * How does our SOAP service actually get put onto the network?
 * JAX-WS provides a built-in lightweight HTTP server via `Endpoint.publish()`.
 * 
 * When this main method runs:
 * 1. It binds our CalculatorServiceImpl to http://localhost:8080/calculator
 * 2. It automatically generates the WSDL file (the contract) at:
 *    http://localhost:8080/calculator?wsdl
 * 3. It listens for incoming SOAP XML requests.
 */
public class ServerPublisher {

    public static void main(String[] args) {
        String url = "http://localhost:8080/calculator";
        System.out.println("==================================================");
        System.out.println(" Starting Aptech JAX-WS SOAP Service...");
        System.out.println(" Published URL: " + url);
        System.out.println(" WSDL Contract URL: " + url + "?wsdl");
        System.out.println("==================================================");
        System.out.println("Server is running. Open your browser and visit the WSDL URL!");
        System.out.println("Press Ctrl+C to stop the server anytime.");

        // This single line starts the server and publishes the service!
        Endpoint.publish(url, new CalculatorServiceImpl());
    }
}
