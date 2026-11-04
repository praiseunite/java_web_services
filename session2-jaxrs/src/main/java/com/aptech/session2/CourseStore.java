package com.aptech.session2;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CourseStore {
    private final Map<Integer, Course> courses = new LinkedHashMap<>();
    private int nextId = 1;

    public CourseStore() {
        add("Java Foundations");
        add("Creating Services for the Web");
    }

    public synchronized List<Course> list(String search) {
        List<Course> result = new ArrayList<>();
        for (Course course : courses.values()) {
            if (search == null || course.title.toLowerCase(Locale.ROOT)
                    .contains(search.trim().toLowerCase(Locale.ROOT))) {
                result.add(course);
            }
        }
        return result;
    }

    public synchronized Course find(int id) {
        return courses.get(id);
    }

    public synchronized Course add(String title) {
        Course course = new Course(nextId, title);
        courses.put(nextId, course);
        nextId++;
        return course;
    }
}
