package com.aptech.session3;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;

public class SoapRawRequestSender {
    public static void main(String[] args) throws Exception {
        String filename = args.length == 0 ? "say-hello.xml" : args[0];
        String xml = Files.readString(Path.of(filename), StandardCharsets.UTF_8);
        int port = Integer.getInteger("soap.port", 8095);
        URI address = URI.create("http://127.0.0.1:" + port + "/greeting");
        HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(5)).build();
        HttpRequest request = HttpRequest.newBuilder(address)
            .timeout(Duration.ofSeconds(10))
            .header("Content-Type", "text/xml; charset=utf-8")
            .header("SOAPAction", "\"\"")
            .POST(HttpRequest.BodyPublishers.ofString(xml, StandardCharsets.UTF_8))
            .build();
        System.out.println("Request body from " + filename + ":\n" + xml);
        HttpResponse<String> response = client.send(request,
            HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
        System.out.println("HTTP status: " + response.statusCode());
        System.out.println("Response body:\n" + response.body());
    }
}
