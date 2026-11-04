package com.aptech.session3;

import java.util.Locale;
import jakarta.jws.WebService;

@WebService(endpointInterface = "com.aptech.session3.GreetingService",
    targetNamespace = "urn:aptech:session3",
    serviceName = "AptechGreetingService", portName = "AptechGreetingPort")
public class GreetingServiceImpl implements GreetingService {
    @Override
    public String sayHello(String name) throws CourseFault {
        String cleanName = name == null || name.isBlank() ? "Student" : name.trim();
        if (cleanName.length() > 60) {
            throw new CourseFault("Name must be at most 60 characters.");
        }
        return "Hello, " + cleanName + "! Welcome to Aptech Jakarta Web Services.";
    }

    @Override
    public int calculateCourseFees(String courseName, int numberOfSemesters)
            throws CourseFault {
        if (numberOfSemesters < 1 || numberOfSemesters > 8) {
            throw new CourseFault("Semesters must be between 1 and 8.");
        }
        CourseInfo course = getCourseInfo(courseName);
        return course.getFeePerSemester() * numberOfSemesters;
    }

    @Override
    public CourseInfo getCourseInfo(String courseName) throws CourseFault {
        String code = courseName == null ? "" : courseName.trim().toUpperCase(Locale.ROOT);
        if ("ADSE".equals(code)) {
            return new CourseInfo(code, 850, "USD");
        } else if ("DISM".equals(code)) {
            return new CourseInfo(code, 650, "USD");
        } else if ("CPISM".equals(code)) {
            return new CourseInfo(code, 500, "USD");
        }
        throw new CourseFault("Choose ADSE, DISM or CPISM.");
    }
}
