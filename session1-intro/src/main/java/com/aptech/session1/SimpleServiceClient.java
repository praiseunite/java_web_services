package com.aptech.session1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * SESSION 1: The Service Requester (Client).
 * 
 * For Absolute Beginners:
 * This program plays the role of the "Client" (Service Requester).
 * It dials into the Server at localhost:5000, sends a message, and reads the reply!
 * 
 * HOW TO RUN THIS:
 * Step 1: Make sure SimpleServiceServer is running in another terminal window.
 * Step 2: Open a second terminal window in the project root.
 * Step 3: Run: java -cp session1-intro/out com.aptech.session1.SimpleServiceClient
 */
public class SimpleServiceClient {

    public static void main(String[] args) {
        String host = "localhost";
        int port = 5000;

        System.out.println("==================================================");
        System.out.println(" [SESSION 1] Aptech Distributed Service Client");
        System.out.println("==================================================");
        System.out.println("Connecting to server at " + host + ":" + port + "...");

        try (Socket socket = new Socket(host, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            System.out.println("✓ Connected to server successfully!");

            // 1. Send request command
            String command = "GET_QUOTE";
            System.out.println(">> Sending command to server: " + command);
            out.println(command);

            // 2. Read server's response
            String response = in.readLine();
            System.out.println("<< Server Response Received:");
            System.out.println(response);

            System.out.println("==================================================");
            System.out.println("SUCCESS! You have just executed a client-server distributed call!");

        } catch (Exception e) {
            System.err.println("Client Error: Could not connect to server at " + host + ":" + port);
            System.err.println("Make sure SimpleServiceServer is running first!");
            e.printStackTrace();
        }
    }
}
