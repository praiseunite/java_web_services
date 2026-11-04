package com.aptech.jaxb;
import java.nio.file.Path;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;

public class JaxbUnmarshallingDemo {
    public static void main(String[] args) throws Exception {
        if (args.length > 1) throw new IllegalArgumentException("Supply an optional input XML path.");
        Path input = Path.of(args.length == 1 ? args[0] : "examples/valid-student.xml");
        JAXBContext context = JAXBContext.newInstance(Student.class);
        Unmarshaller reader = context.createUnmarshaller();
        // No XSD is attached in this comparison demo. It is not acceptance validation.
        Student student = SecureXml.read(reader, input);
        System.out.println("Mapped without XSD validation: " + student);
        System.out.println("Full name getter: " + student.getFullName());
        System.out.println("Password omitted from XML; getter is null: " + (student.getPassword() == null));
    }
}
