package com.aptech.session7;

// Immutable response data: the stored values do not change through setters.
public class Product {
    private final int id;
    private final String name;
    private final int priceNgn;
    private final String category;

    public Product(int id, String name, int priceNgn, String category) {
        this.id = id;
        this.name = name;
        this.priceNgn = priceNgn;
        this.category = category;
    }
    public int getId() { return id; }
    public String getName() { return name; }
    public int getPriceNgn() { return priceNgn; }
    public String getCategory() { return category; }
}
