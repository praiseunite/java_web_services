package com.aptech.jaxws;

import jakarta.jws.WebService;

/**
 * Service Implementation Bean (SIB).
 * 
 * For Beginners:
 * This class is the "Kitchen Cook" that fulfills the promises made in the Menu (CalculatorService).
 * 
 * Notice the annotation:
 * @WebService(endpointInterface = "com.aptech.jaxws.CalculatorService")
 * This connects this implementation class to the interface contract above.
 */
@WebService(endpointInterface = "com.aptech.jaxws.CalculatorService")
public class CalculatorServiceImpl implements CalculatorService {

    @Override
    public int add(int num1, int num2) {
        System.out.println("[SERVER LOG] Received add request for: " + num1 + " + " + num2);
        return num1 + num2;
    }

    @Override
    public int subtract(int num1, int num2) {
        System.out.println("[SERVER LOG] Received subtract request for: " + num1 + " - " + num2);
        return num1 - num2;
    }

    @Override
    public String getGreeting(String studentName) {
        System.out.println("[SERVER LOG] Received greeting request for: " + studentName);
        return "Welcome to Jakarta Web Services at Aptech, " + studentName + "!";
    }
}
