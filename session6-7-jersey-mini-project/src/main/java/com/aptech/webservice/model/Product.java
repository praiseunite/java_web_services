package com.aptech.webservice.model;

import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * Model Class: Product.
 * 
 * For Beginners:
 * This class represents a single item in our inventory (e.g. Laptop, Phone, Keyboard).
 * 
 * Notice:
 * We add @XmlRootElement so this class can be converted to XML or JSON
 * by Jersey automatically!
 */
@XmlRootElement
public class Product {

    private int id;
    private String name;
    private double price;
    private String category;

    // MANDATORY default no-argument constructor for JSON/XML deserializers
    public Product() {
    }

    public Product(int id, String name, double price, String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Product [id=" + id + ", name=" + name + ", price=$" + price + ", category=" + category + "]";
    }
}
