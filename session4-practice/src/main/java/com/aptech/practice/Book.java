package com.aptech.practice;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "library_books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;

    @Column(name = "book_title", nullable = false, length = 80)
    private String title;

    public Book() {
    }

    public Book(String title) {
        setTitle(title);
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null || title.isBlank() || title.trim().length() > 80) {
            throw new IllegalArgumentException("Title must contain 1 to 80 characters after trimming.");
        }
        this.title = title.trim();
    }
}
