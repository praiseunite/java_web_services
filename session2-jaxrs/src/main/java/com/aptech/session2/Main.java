package com.aptech.session2;

import com.sun.net.httpserver.HttpServer;
import java.net.URI;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

public class Main {
    public static void main(String[] args) {
        int port = Integer.getInteger("course.port", 8093);
        String baseUrl = "http://127.0.0.1:" + port + "/api/";
        boolean helloOnly = args.length > 0 && "--hello-only".equals(args[0]);

        ResourceConfig config = new ResourceConfig();
        config.property(ServerProperties.WADL_FEATURE_DISABLE, true);
        config.register(HelloResource.class);
        config.register(JacksonFeature.class);
        if (!helloOnly) {
            config.register(CourseResource.class);
        }

        HttpServer server = JdkHttpServerFactory.createHttpServer(
            URI.create(baseUrl), config);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> server.stop(0)));

        System.out.println("Session 2 JAX-RS service is running.");
        System.out.println("Try: " + baseUrl + "greeting");
        if (!helloOnly) {
            System.out.println("Try: " + baseUrl + "courses");
        }
        System.out.println("Press Ctrl+C in this terminal to stop.");
    }
}
