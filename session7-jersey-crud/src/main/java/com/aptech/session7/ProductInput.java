package com.aptech.session7;

// Incoming JSON has these three fields. The server chooses id separately.
public class ProductInput {
    private String name;
    private Integer priceNgn;
    private String category;

    public ProductInput() { }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Integer getPriceNgn() { return priceNgn; }
    public void setPriceNgn(Integer priceNgn) { this.priceNgn = priceNgn; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}
