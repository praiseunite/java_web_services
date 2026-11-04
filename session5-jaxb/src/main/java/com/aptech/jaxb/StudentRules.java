package com.aptech.jaxb;
public class StudentRules {
    public static void check(Student student) {
        if (student == null || student.getId() < 1)
            throw new IllegalArgumentException("Student id must be positive.");
        text(student.getFullName(), "Full name", 60);
        text(student.getEmail(), "Email", 80);
        if (!"ADSE".equals(student.getCourse()) && !"DISM".equals(student.getCourse()))
            throw new IllegalArgumentException("Course must be ADSE or DISM.");
        if (!Double.isFinite(student.getGpa()) || student.getGpa() < 0 || student.getGpa() > 4)
            throw new IllegalArgumentException("GPA must be a finite number from 0 to 4.");
    }
    private static void text(String value, String label, int limit) {
        if (value == null || value.isBlank() || value.length() > limit)
            throw new IllegalArgumentException(label + " must contain 1 to " + limit + " characters and not be blank.");
    }
}
