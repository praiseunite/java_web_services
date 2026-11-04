import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class FirstWebService {
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(
            new InetSocketAddress("127.0.0.1", 8081), 0);

        server.createContext("/", exchange -> {
            String method = exchange.getRequestMethod();
            String path = exchange.getRequestURI().getPath();
            int status;
            String response;

            if (!"GET".equals(method)) {
                status = 405;
                response = "Use GET for this service.";
                exchange.getResponseHeaders().set("Allow", "GET");
            } else if ("/greeting".equals(path)) {
                status = 200;
                response = "Welcome to Aptech!";
            } else {
                status = 404;
                response = "Resource not found.";
            }

            byte[] body = response.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().set(
                "Content-Type", "text/plain; charset=utf-8");
            exchange.sendResponseHeaders(status, body.length);
            try (var output = exchange.getResponseBody()) {
                output.write(body);
            }
            exchange.close();
        });

        server.start();
        System.out.println("Open http://127.0.0.1:8081/greeting");
        System.out.println("Keep this terminal open. Press Ctrl+C to stop.");
    }
}
