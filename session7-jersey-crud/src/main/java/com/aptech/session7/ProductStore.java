package com.aptech.session7;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ProductStore {
    private static final Map<Integer, Product> ITEMS = new LinkedHashMap<>();
    private static int nextId = 4;
    static {
        ITEMS.put(1, new Product(1, "Notebook", 1500, "Stationery"));
        ITEMS.put(2, new Product(2, "USB Mouse", 6500, "Accessories"));
        ITEMS.put(3, new Product(3, "Pen Set", 1000, "Stationery"));
    }

    public static synchronized List<Product> findAll(String category) {
        List<Product> matches = new ArrayList<>();
        for (Product item : ITEMS.values()) {
            if (category == null || category.trim().isEmpty()
                    || item.getCategory().equalsIgnoreCase(category.trim())) {
                matches.add(item);
            }
        }
        return matches;
    }

    public static synchronized Product findById(int id) { return ITEMS.get(id); }

    // Resource methods validate first. The same lock covers id selection and insert.
    public static synchronized Product create(ProductInput input) {
        Product saved = fromInput(nextId, input);
        ITEMS.put(nextId, saved);
        nextId++;
        return saved;
    }

    public static synchronized Product replace(int id, ProductInput input) {
        if (!ITEMS.containsKey(id)) { return null; }
        Product replacement = fromInput(id, input);
        ITEMS.put(id, replacement);
        return replacement;
    }

    public static synchronized boolean delete(int id) { return ITEMS.remove(id) != null; }

    private static Product fromInput(int id, ProductInput input) {
        return new Product(id, input.getName().trim(), input.getPriceNgn(),
                input.getCategory().trim());
    }
}
