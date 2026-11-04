package com.aptech.session6;

import java.net.URI;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import com.sun.net.httpserver.HttpServer;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class Main {
    public static final String BASE_URI = "http://127.0.0.1:8098/api/";

    public static void main(String[] args) throws InterruptedException {
        boolean helloOnly = Arrays.asList(args).contains("--hello-only");
        ResourceConfig config = new ResourceConfig().register(GreetingResource.class);
        if (!helloOnly) {
            config.register(ProductResource.class).register(JacksonFeature.class);
        }
        HttpServer server = JdkHttpServerFactory.createHttpServer(URI.create(BASE_URI), config);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> server.stop(0)));
        System.out.println("Session 6 server: " + BASE_URI + "greeting");
        System.out.println(helloOnly ? "Greeting only." : "Products: " + BASE_URI + "products");
        System.out.println("Leave terminal A open. Use terminal B for requests. Ctrl+C stops A.");
        new CountDownLatch(1).await();
    }
}
