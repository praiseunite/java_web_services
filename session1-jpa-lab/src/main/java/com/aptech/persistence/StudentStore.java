package com.aptech.persistence;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

public class StudentStore {
    private final EntityManagerFactory factory;

    public StudentStore(EntityManagerFactory factory) {
        this.factory = factory;
    }

    public List<Student> list() {
        EntityManager em = factory.createEntityManager();
        try {
            return em.createQuery("SELECT s FROM Student s ORDER BY s.id", Student.class)
                .getResultList();
        } finally {
            em.close();
        }
    }

    public Student find(long id) {
        EntityManager em = factory.createEntityManager();
        try {
            return em.find(Student.class, id);
        } finally {
            em.close();
        }
    }

    public List<Student> search(String name) {
        EntityManager em = factory.createEntityManager();
        try {
            return em.createQuery("SELECT s FROM Student s WHERE s.name = :name ORDER BY s.id", Student.class)
                .setParameter("name", name).getResultList();
        } finally {
            em.close();
        }
    }

    public Student add(String name) {
        Student student = new Student(name);
        EntityManager em = factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(student);
            tx.commit();
            return student;
        } catch (RuntimeException problem) {
            if (tx.isActive()) tx.rollback();
            throw problem;
        } finally {
            em.close();
        }
    }

    public boolean rename(long id, String name, boolean cancel) {
        EntityManager em = factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Student student = em.find(Student.class, id);
            if (student == null) {
                tx.rollback();
                return false;
            }
            student.setName(name);
            if (cancel) {
                em.flush();
                tx.rollback();
            } else {
                tx.commit();
            }
            return true;
        } catch (RuntimeException problem) {
            if (tx.isActive()) tx.rollback();
            throw problem;
        } finally {
            em.close();
        }
    }

    public boolean delete(long id) {
        EntityManager em = factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Student student = em.find(Student.class, id);
            if (student == null) {
                tx.rollback();
                return false;
            }
            em.remove(student);
            tx.commit();
            return true;
        } catch (RuntimeException problem) {
            if (tx.isActive()) tx.rollback();
            throw problem;
        } finally {
            em.close();
        }
    }
}
