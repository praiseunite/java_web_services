package com.aptech.session6;

// Ordinary Java: no Jersey annotation and no HTTP connection here.
public class GreetingLogic {
    public static String message(String name) {
        return "Welcome, " + name + "! You are learning Jersey with Gradle.";
    }
}
