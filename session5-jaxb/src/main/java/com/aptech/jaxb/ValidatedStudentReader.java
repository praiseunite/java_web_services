package com.aptech.jaxb;
import java.nio.file.Path;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;

public class ValidatedStudentReader {
    public static void main(String[] args) {
        try {
            if (args.length > 1) throw new IllegalArgumentException("Supply an optional input XML path.");
            Path input = Path.of(args.length == 1 ? args[0] : "examples/valid-student.xml");
            JAXBContext context = JAXBContext.newInstance(Student.class);
            Unmarshaller reader = context.createUnmarshaller();
            reader.setSchema(SecureXml.schema(Path.of("student_schema.xsd")));
            reader.setEventHandler(event -> false); // Stop on the first validation problem.
            Student student = SecureXml.read(reader, input);
            StudentRules.check(student); // Check application rules before using the object.
            System.out.println("ACCEPTED: " + student);
        } catch (Exception problem) {
            Throwable clue = problem;
            while (clue.getCause() != null) clue = clue.getCause();
            System.err.println("REJECTED: " + clue.getMessage());
            System.exit(2);
        }
    }
}
