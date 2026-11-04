package com.aptech.jaxb;
import java.nio.file.Files;
import java.nio.file.Path;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;

public class RoundTripDemo {
    public static void main(String[] args) throws Exception {
        if (args.length > 1) throw new IllegalArgumentException("Supply an optional output XML path.");
        Path file = Path.of(args.length == 1 ? args[0] : "target/roundtrip.xml");
        Files.createDirectories(file.toAbsolutePath().getParent());
        JAXBContext context = JAXBContext.newInstance(Student.class);
        Schema schema = SecureXml.schema(Path.of("student_schema.xsd"));
        Student before = new Student(102, "Ada <Coder>", "ada@example.com", "DISM", 4.0, "DEMO_ONLY");
        StudentRules.check(before);
        Marshaller writer = context.createMarshaller();
        writer.setSchema(schema);
        writer.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        writer.marshal(before, file.toFile());
        Unmarshaller reader = context.createUnmarshaller();
        reader.setSchema(schema);
        reader.setEventHandler(event -> false);
        Student after = SecureXml.read(reader, file);
        StudentRules.check(after);
        boolean sameValues = before.getId() == after.getId()
            && before.getFullName().equals(after.getFullName())
            && before.getEmail().equals(after.getEmail())
            && before.getCourse().equals(after.getCourse())
            && Double.compare(before.getGpa(), after.getGpa()) == 0;
        System.out.println("Mapped values preserved: " + sameValues);
        System.out.println("Same Java object: " + (before == after));
        System.out.println("Excluded password after read is null: " + (after.getPassword() == null));
        System.out.println("Saved XML: " + file.toAbsolutePath().normalize());
        if (!sameValues || before == after || after.getPassword() != null)
            throw new IllegalStateException("Round-trip expectations failed.");
    }
}
