package com.aptech.session8;

public class ProductRules {
    public static void check(XmlProductInput input) {
        if (input == null) { throw new IllegalArgumentException("Product input is required."); }
        text(input.getName(), "name", 60);
        text(input.getCategory(), "category", 30);
        Integer price = input.getPriceNgn();
        if (price == null || price < 0 || price > 1000000) {
            throw new IllegalArgumentException("priceNgn must be an integer from 0 to 1000000.");
        }
    }

    public static XmlProductInput normalized(XmlProductInput input) {
        check(input);
        XmlProductInput result = new XmlProductInput();
        result.setName(input.getName().trim());
        result.setPriceNgn(input.getPriceNgn());
        result.setCategory(input.getCategory().trim());
        return result;
    }

    private static void text(String value, String label, int maximum) {
        if (value == null || value.trim().isEmpty() || value.trim().length() > maximum) {
            throw new IllegalArgumentException(label + " must contain 1 to " + maximum + " characters after trim.");
        }
    }
}
