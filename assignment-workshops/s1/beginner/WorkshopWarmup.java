import java.util.LinkedHashMap;
import java.util.Map;

public class WorkshopWarmup {
    static String explainDays(Integer days) {
        if (days == null) { return "REJECTED: days is required"; }
        if (days < 0 || days > 14) { return "REJECTED: days must be 0 to 14"; }
        int chargeNgn = days * 25;
        return "ACCEPTED: " + chargeNgn + " NGN";
    }

    public static void main(String[] args) {
        Integer[] inputs = {null, -1, 0, 1, 14, 15};
        for (Integer days : inputs) {
            System.out.println(days + " -> " + explainDays(days));
        }

        Map<String, String> prediction = new LinkedHashMap<>();
        prediction.put("case", "zero days");
        prediction.put("expected", "accepted with zero charge");
        System.out.println("Prediction only: " + prediction);
        System.out.println("This program sends no HTTP and opens no database.");
    }
}
