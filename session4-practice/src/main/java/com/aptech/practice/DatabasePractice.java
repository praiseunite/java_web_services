package com.aptech.practice;

import java.util.List;
import jakarta.persistence.EntityManagerFactory;

public class DatabasePractice {
    public static void main(String[] args) throws Exception {
        if (args.length > 1) throw new IllegalArgumentException("Supply one quoted title to add, or no arguments to list.");
        EntityManagerFactory factory = LibraryDatabase.open();
        try {
            BookStore store = new BookStore(factory);
            if (args.length == 1) {
                Book saved = store.add(args[0]);
                System.out.println("Saved book " + saved.getId() + ": " + saved.getTitle());
            } else {
                List<Book> books = store.list(null);
                for (Book book : books) {
                    System.out.println(book.getId() + ": " + book.getTitle());
                }
                System.out.println("Books: " + books.size());
            }
        } finally {
            factory.close();
        }
    }
}
