public class RequestPractice {
    public static String replyTo(String request) {
        if ("GET_GREETING".equals(request)) {
            return "Welcome to Aptech!";
        } else if ("GET_COURSE".equals(request)) {
            return "Creating Services for the Web";
        } else {
            return "Unknown request";
        }
    }

    public static void main(String[] args) {
        String request = "GET_GREETING";
        String response = replyTo(request);
        System.out.println("Request: " + request);
        System.out.println("Response: " + response);
    }
}
