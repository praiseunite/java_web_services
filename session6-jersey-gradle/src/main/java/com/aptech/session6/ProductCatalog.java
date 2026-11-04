package com.aptech.session6;

import java.util.ArrayList;
import java.util.List;

public class ProductCatalog {
    // Fixed fictional examples held in memory. No database or write operations.
    private static final List<Product> ITEMS = List.of(
            new Product(1, "Notebook", 1500, "Stationery"),
            new Product(2, "USB Mouse", 6500, "Accessories"),
            new Product(3, "Pen Set", 1000, "Stationery"));

    public static List<Product> findAll(String category) {
        if (category == null || category.trim().isEmpty()) {
            return ITEMS;
        }
        List<Product> matches = new ArrayList<>();
        for (Product item : ITEMS) {
            if (item.getCategory().equalsIgnoreCase(category.trim())) {
                matches.add(item);
            }
        }
        return matches;
    }

    public static Product findById(int id) {
        for (Product item : ITEMS) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }
}
