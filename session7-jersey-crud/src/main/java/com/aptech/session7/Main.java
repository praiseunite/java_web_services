package com.aptech.session7;

import java.net.URI;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import com.sun.net.httpserver.HttpServer;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

public class Main {
    public static final String BASE_URI = "http://127.0.0.1:8099/api/";
    public static void main(String[] args) throws InterruptedException {
        boolean readOnly = Arrays.asList(args).contains("--read-only");
        ResourceConfig config = new ResourceConfig()
                .property(ServerProperties.WADL_FEATURE_DISABLE, true)
                .register(JacksonFeature.class).register(JsonSupport.class);
        if (readOnly) { config.register(ReadOnlyProductsResource.class); }
        else { config.register(ProductResource.class); }
        HttpServer server = JdkHttpServerFactory.createHttpServer(URI.create(BASE_URI), config);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> server.stop(0)));
        System.out.println("Session 7 server: " + BASE_URI + "products");
        System.out.println(readOnly ? "Read-only mode." : "CRUD mode: GET, POST, PUT, DELETE.");
        System.out.println("Leave A running; send requests from B. Ctrl+C stops A.");
        new CountDownLatch(1).await();
    }
}
