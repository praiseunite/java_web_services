import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CourseCollectionPractice {
    static class Course {
        int id;
        String title;

        Course(int id, String title) {
            this.id = id;
            this.title = title;
        }
    }

    public static void main(String[] args) {
        Map<Integer, Course> courses = new LinkedHashMap<>();
        int nextId = 1;

        Course first = new Course(nextId, "Java Foundations");
        courses.put(nextId, first);
        nextId++;

        Course second = new Course(nextId, "Web Services");
        courses.put(nextId, second);
        nextId++;

        for (Course course : courses.values()) {
            System.out.println(course.id + ": " + course.title);
        }

        Course found = courses.get(2);
        if (found != null) {
            System.out.println("Found: " + found.title);
        }

        Course missing = courses.get(999);
        if (missing == null) {
            System.out.println("Course not found.");
        }

        List<Course> matches = new ArrayList<>();
        for (Course course : courses.values()) {
            if (course.title.contains("Java")) {
                matches.add(course);
            }
        }
        System.out.println("Stored: " + courses.size());
        System.out.println("Java matches: " + matches.size());
        System.out.println("Next id: " + nextId);
    }
}
