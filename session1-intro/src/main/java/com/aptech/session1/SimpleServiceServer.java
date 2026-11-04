package com.aptech.session1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * SESSION 1: What is a Distributed Service?
 * 
 * For Absolute Beginners:
 * This example exchanges lines using a custom protocol over raw TCP sockets.
 * It illustrates requests and responses, but it does not speak HTTP, SOAP, or REST.
 * 
 * This simple server demonstrates the core principle of ALL web services:
 * 1. A Server listens at a specific Port (e.g., 5000) on a host (e.g., localhost).
 * 2. A Client connects across the network.
 * 3. The Client sends a Request ("GET_QUOTE").
 * 4. The Server processes it and sends back a Response ("Knowledge is Power!").
 * 
 * HOW TO RUN THIS:
 * Step 1: Open PowerShell in the project root.
 * Step 2: Compile both files using the optional commands in the HTML lesson.
 * Step 3: Run: java -cp session1-intro/out com.aptech.session1.SimpleServiceServer
 */
public class SimpleServiceServer {

    public static final int PORT = 5000;

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println(" [SESSION 1] Aptech Distributed Service Server");
        System.out.println("==================================================");
        System.out.println("Server is starting on port " + PORT + "...");

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("✓ Server is listening! Waiting for clients to connect...\n");

            while (true) {
                // Wait for a client to connect (this blocks until a client calls)
                try (Socket clientSocket = serverSocket.accept()) {
                    System.out.println(">> Client connected from: " + clientSocket.getRemoteSocketAddress());

                    // Read the client's request message
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                    String request = in.readLine();
                    System.out.println(">> Received Client Request: " + request);

                    // Formulate the Response
                    String response;
                    if ("GET_QUOTE".equalsIgnoreCase(request)) {
                        response = "{\"service\": \"QuoteService\", \"quote\": \"The beautiful thing about learning is that no one can take it away from you. - B.B. King\"}";
                    } else if ("GET_TIME".equalsIgnoreCase(request)) {
                        response = "{\"service\": \"TimeService\", \"serverTime\": \"" + java.time.LocalDateTime.now() + "\"}";
                    } else {
                        response = "{\"error\": \"Unknown Request. Available commands: GET_QUOTE, GET_TIME\"}";
                    }

                    // Send response back across the network to client
                    out.println(response);
                    System.out.println(">> Sent Response: " + response + "\n");
                }
            }
        } catch (Exception e) {
            System.err.println("Server Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
