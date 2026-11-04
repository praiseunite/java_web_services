package com.aptech.session7;

public class LessonJavaDemo {
    public static void main(String[] args) {
        System.out.println("Initial count: " + ProductStore.findAll(null).size());
        ProductInput input = new ProductInput();
        input.setName(" USB Hub ");
        input.setPriceNgn(7500);
        input.setCategory(" Accessories ");
        String problem = ProductRules.validate(input);
        if (problem != null) { throw new IllegalArgumentException(problem); }
        Product saved = ProductStore.create(input);
        System.out.println("Created: " + saved.getId() + " " + saved.getName());
        input.setPriceNgn(8500);
        Product replacement = ProductStore.replace(saved.getId(), input);
        System.out.println("Replaced price: " + replacement.getPriceNgn());
        System.out.println("Deleted: " + ProductStore.delete(saved.getId()));
        System.out.println("Final count: " + ProductStore.findAll(null).size());
        input.setName("   ");
        System.out.println("Rejected: " + ProductRules.validate(input));
    }
}
