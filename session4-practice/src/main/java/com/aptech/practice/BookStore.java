package com.aptech.practice;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

public class BookStore {
    private final EntityManagerFactory factory;

    public BookStore(EntityManagerFactory factory) {
        this.factory = factory;
    }

    public List<Book> list(String title) {
        EntityManager em = factory.createEntityManager();
        try {
            if (title == null) {
                return em.createQuery("SELECT b FROM Book b ORDER BY b.id", Book.class).getResultList();
            }
            return em.createQuery("SELECT b FROM Book b WHERE b.title = :title ORDER BY b.id", Book.class)
                .setParameter("title", title).getResultList();
        } finally {
            em.close();
        }
    }

    public Book find(long id) {
        EntityManager em = factory.createEntityManager();
        try {
            return em.find(Book.class, id);
        } finally {
            em.close();
        }
    }

    public Book add(String title) {
        Book book = new Book(title);
        EntityManager em = factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(book);
            tx.commit();
            return book;
        } catch (RuntimeException problem) {
            if (tx.isActive()) tx.rollback();
            throw problem;
        } finally {
            em.close();
        }
    }
}
