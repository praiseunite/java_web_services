package com.aptech.persistence;

import com.sun.net.httpserver.HttpServer;
import java.net.URI;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import jakarta.persistence.EntityManagerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

public class PersistentServer {
    public static void main(String[] args) throws Exception {
        EntityManagerFactory factory = Database.open();
        HttpServer server = null;
        ExecutorService requests = Executors.newFixedThreadPool(4);
        try {
            StudentStore store = new StudentStore(factory);
            ResourceConfig config = new ResourceConfig();
            config.property(ServerProperties.WADL_FEATURE_DISABLE, true);
            config.register(new StudentResource(store));
            config.register(JacksonFeature.class);
            int port = Integer.getInteger("lab.port", 8096);
            String base = "http://127.0.0.1:" + port + "/api/";
            server = JdkHttpServerFactory.createHttpServer(URI.create(base), config, false);
            server.setExecutor(requests);
            server.start();
            System.out.println("Persistent service: " + base + "students");
            System.out.println("Leave this terminal open. Press Enter here to stop safely.");
            System.in.read();
        } finally {
            if (server != null) server.stop(0);
            requests.shutdownNow();
            factory.close();
        }
    }
}
