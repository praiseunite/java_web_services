package com.aptech.session3.beginner;

import jakarta.jws.WebService;

@WebService(endpointInterface = "com.aptech.session3.beginner.GreetingService")
public class GreetingServiceImpl implements GreetingService {
    @Override
    public String sayHello(String name) {
        return "Hello, " + name + "!";
    }
}
