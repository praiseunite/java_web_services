package com.aptech.webservice;

import java.io.IOException;
import java.net.URI;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Main Application Runner.
 * 
 * For Beginners:
 * In enterprise Java, web services used to require installing huge, complex
 * application servers like WildFly, WebLogic, or GlassFish.
 * 
 * Today, we use an "Embedded Server" (Grizzly).
 * Grizzly is a lightweight web server packaged directly inside our code.
 * Running this single Java class starts the web server in under 1 second!
 */
public class Main {

    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://localhost:8080/api/";

    /**
     * Configures Jersey and starts the Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        // Create a resource configuration that scans for JAX-RS resources and providers
        // in the com.aptech.webservice package
        final ResourceConfig rc = new ResourceConfig()
                .packages("com.aptech.webservice.resource")
                // Enable Jackson JSON auto-conversion
                .register(org.glassfish.jersey.jackson.JacksonFeature.class);

        // Create and start a new instance of grizzly http server
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();
        
        System.out.println("==================================================================");
        System.out.println("   APTECH JERSEY RESTful WEB SERVICES SERVER STARTED!            ");
        System.out.println("==================================================================");
        System.out.println("Base API Endpoint: " + BASE_URI + "products");
        System.out.println("Test in your browser right now by clicking or visiting:");
        System.out.println("   -> http://localhost:8080/api/products");
        System.out.println("   -> http://localhost:8080/api/products/1");
        System.out.println("   -> http://localhost:8080/api/products?category=Accessories");
        System.out.println("==================================================================");
        System.out.println("Press Enter to stop the server...");

        // Keep the server running until user presses enter
        System.in.read();
        
        server.shutdownNow();
        System.out.println("Server stopped gracefully. Goodbye!");
    }
}
