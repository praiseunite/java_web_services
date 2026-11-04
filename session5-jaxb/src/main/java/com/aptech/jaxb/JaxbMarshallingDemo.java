package com.aptech.jaxb;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;

public class JaxbMarshallingDemo {
    public static void main(String[] args) throws Exception {
        if (args.length > 1) throw new IllegalArgumentException("Supply an optional output XML path.");
        Path output = Path.of(args.length == 1 ? args[0] : "target/student_output.xml");
        Files.createDirectories(output.toAbsolutePath().getParent());
        Student student = new Student(101, "Amina & Tunde", "learner@example.com", "ADSE", 3.5, "DEMO_ONLY");
        StudentRules.check(student);
        JAXBContext context = JAXBContext.newInstance(Student.class);
        Marshaller writer = context.createMarshaller();
        writer.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        writer.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        StringWriter text = new StringWriter();
        writer.marshal(student, text);
        System.out.println(text);
        writer.marshal(student, output.toFile());
        System.out.println("Saved XML: " + output.toAbsolutePath().normalize());
    }
}
