package com.aptech.jaxws;

import java.net.URI;
import java.net.URL;
import javax.xml.namespace.QName;
import jakarta.xml.ws.Service;

/**
 * Client Support in JAX-WS.
 * 
 * For Beginners:
 * How does a client application (which could be on a completely different computer)
 * talk to the SOAP service?
 * 
 * In JAX-WS, the client uses `jakarta.xml.ws.Service` to create a "Proxy" (Stub).
 * To the programmer, it looks like a normal local Java method call:
 *     calculator.add(25, 15)
 * 
 * But under the hood:
 * 1. JAX-WS serializes the numbers 25 and 15 into a SOAP XML envelope.
 * 2. It sends an HTTP POST request across the network to http://localhost:8080/calculator.
 * 3. The server calculates 40 and responds with a SOAP XML envelope.
 * 4. JAX-WS extracts the number 40 and returns it to your Java variable!
 */
public class SoapClientExample {

    public static void main(String[] args) {
        try {
            // 1. The URL where the WSDL contract is published
            URL wsdlUrl = URI.create("http://localhost:8080/calculator?wsdl").toURL();

            // 2. QName (Qualified Name) uniquely identifying the Service in XML
            // Format: QName(TargetNamespace, ServiceName)
            QName qname = new QName("http://jaxws.aptech.com/", "CalculatorServiceImplService");

            // 3. Create the JAX-WS Service factory
            System.out.println("[CLIENT] Connecting to SOAP Service at: " + wsdlUrl);
            Service service = Service.create(wsdlUrl, qname);

            // 4. Extract the Stub / Proxy (implements our CalculatorService interface)
            CalculatorService calculator = service.getPort(CalculatorService.class);

            System.out.println("[CLIENT] Connected successfully! Invoking remote methods:\n");

            // 5. Invoke the remote methods just like local Java methods
            int sum = calculator.add(45, 15);
            System.out.println("Result of add(45, 15)       = " + sum);

            int diff = calculator.subtract(100, 35);
            System.out.println("Result of subtract(100, 35) = " + diff);

            String greeting = calculator.getGreeting("Alex");
            System.out.println("Server Greeting             = " + greeting);

            System.out.println("\n[CLIENT] All SOAP Web Service calls executed successfully!");

        } catch (Exception e) {
            System.err.println("[CLIENT ERROR] Could not connect to the SOAP server.");
            System.err.println("Make sure ServerPublisher is running first!");
            e.printStackTrace();
        }
    }
}
