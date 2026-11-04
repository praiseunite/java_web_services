package com.aptech.practice;

import com.sun.net.httpserver.HttpServer;
import java.net.URI;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import jakarta.persistence.EntityManagerFactory;
import jakarta.xml.ws.Endpoint;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

public class PracticeServer {
    public static void main(String[] args) throws Exception {
        boolean restOnly = args.length == 1 && "--rest-only".equals(args[0]);
        if (args.length > 1 || (args.length == 1 && !restOnly)) {
            throw new IllegalArgumentException("Use no arguments, or --rest-only.");
        }
        EntityManagerFactory factory = LibraryDatabase.open();
        ExecutorService requests = Executors.newFixedThreadPool(4);
        HttpServer rest = null;
        Endpoint soap = null;
        try {
            BookStore store = new BookStore(factory);
            ResourceConfig config = new ResourceConfig();
            config.property(ServerProperties.WADL_FEATURE_DISABLE, true);
            config.register(new BookResource(store));
            config.register(JacksonFeature.class);
            int restPort = Integer.getInteger("practice.rest.port", 8098);
            int soapPort = Integer.getInteger("practice.soap.port", 8099);
            String base = "http://127.0.0.1:" + restPort + "/api/";
            rest = JdkHttpServerFactory.createHttpServer(URI.create(base), config, false);
            rest.setExecutor(requests);
            rest.start();
            if (!restOnly) {
                soap = Endpoint.create(new LibraryServiceImpl(store));
                soap.setExecutor(requests);
                soap.publish("http://127.0.0.1:" + soapPort + "/library");
                System.out.println("SOAP: http://127.0.0.1:" + soapPort + "/library?wsdl");
            }
            System.out.println("REST: " + base + "books");
            System.out.println("Press Enter here to stop safely.");
            System.in.read();
        } finally {
            if (soap != null) soap.stop();
            if (rest != null) rest.stop(0);
            requests.shutdownNow();
            factory.close();
        }
    }
}
