public class PrintChargePractice {
    public static int charge(int pages) {
        if (pages < 1 || pages > 20) {
            throw new IllegalArgumentException("Pages must be between 1 and 20.");
        }
        return pages * 10;
    }

    public static void main(String[] args) {
        int[] pageCounts = {1, 3, 20, 0, 21};
        for (int pages : pageCounts) {
            try {
                System.out.println(pages + " pages: " + charge(pages) + " NGN");
            } catch (IllegalArgumentException problem) {
                System.out.println(pages + " pages: " + problem.getMessage());
            }
        }
    }
}
