package com.aptech.webservice.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import com.aptech.webservice.model.Product;

/**
 * Data Access Object (DAO) / Mock Database.
 * 
 * For Beginners:
 * Instead of making you install and configure MySQL or Oracle database today,
 * we store our products in this simple in-memory Java Map.
 * 
 * When your server is running, any product you add, edit, or delete
 * will be saved here in memory.
 */
public class ProductDatabase {

    // Thread-safe in-memory map representing our database table
    private static final Map<Integer, Product> database = new ConcurrentHashMap<>();
    
    // Auto-increment ID generator (like AUTO_INCREMENT in SQL)
    private static final AtomicInteger idGenerator = new AtomicInteger(3);

    // Seed the database with some initial sample products
    static {
        database.put(1, new Product(1, "ThinkPad T14 Laptop", 1199.99, "Computers"));
        database.put(2, new Product(2, "Wireless Ergonomic Mouse", 49.50, "Accessories"));
        database.put(3, new Product(3, "Mechanical Gaming Keyboard", 89.00, "Accessories"));
    }

    /**
     * Fetch all products
     */
    public static List<Product> getAllProducts() {
        return new ArrayList<>(database.values());
    }

    /**
     * Fetch a single product by ID
     */
    public static Product getProductById(int id) {
        return database.get(id);
    }

    /**
     * Add a new product (Generates a new ID automatically)
     */
    public static Product addProduct(Product product) {
        int newId = idGenerator.incrementAndGet();
        product.setId(newId);
        database.put(newId, product);
        return product;
    }

    /**
     * Update an existing product
     */
    public static Product updateProduct(int id, Product updatedProduct) {
        if (!database.containsKey(id)) {
            return null;
        }
        updatedProduct.setId(id);
        database.put(id, updatedProduct);
        return updatedProduct;
    }

    /**
     * Delete a product by ID
     */
    public static boolean deleteProduct(int id) {
        return database.remove(id) != null;
    }

    /**
     * Filter products by category
     */
    public static List<Product> getProductsByCategory(String category) {
        List<Product> matches = new ArrayList<>();
        for (Product p : database.values()) {
            if (p.getCategory() != null && p.getCategory().equalsIgnoreCase(category)) {
                matches.add(p);
            }
        }
        return matches;
    }
}
