package com.aptech.session7;

public class ProductRules {
    // A null return means valid; a String explains the first invalid rule.
    public static String validate(ProductInput input) {
        if (input == null) { return "A JSON product object is required."; }
        if (input.getName() == null || input.getName().trim().isEmpty()
                || input.getName().trim().length() > 60) {
            return "name must contain 1 to 60 characters after trimming.";
        }
        if (input.getPriceNgn() == null || input.getPriceNgn() < 0
                || input.getPriceNgn() > 1_000_000) {
            return "priceNgn must be a whole number from 0 to 1000000.";
        }
        if (input.getCategory() == null || input.getCategory().trim().isEmpty()
                || input.getCategory().trim().length() > 30) {
            return "category must contain 1 to 30 characters after trimming.";
        }
        return null;
    }
}
