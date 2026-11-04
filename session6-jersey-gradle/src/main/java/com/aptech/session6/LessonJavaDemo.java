package com.aptech.session6;

public class LessonJavaDemo {
    public static void main(String[] args) {
        String learner = "Ada";
        System.out.println(GreetingLogic.message(learner));
        Product item = new Product(1, "Notebook", 1500, "Stationery");
        System.out.println(item.getId() + ": " + item.getName()
                + " costs NGN " + item.getPriceNgn());
    }
}
