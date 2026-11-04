import java.io.StringReader;
import java.nio.file.Path;
import com.aptech.jaxb.generated.Student;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import javax.xml.XMLConstants;
import javax.xml.validation.SchemaFactory;

public class GeneratedStudentDemo {
    public static void main(String[] args) throws Exception {
        // A fixed classroom literal, not arbitrary external input.
        String xml = """
            <student id="205">
                <full_name>Chidi &amp; Ada</full_name>
                <email>chidi@example.com</email>
                <course>DISM</course>
                <gpa>3.0</gpa>
            </student>
            """;
        SchemaFactory schemas = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        schemas.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        schemas.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        schemas.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
        JAXBContext context = JAXBContext.newInstance(Student.class);
        Unmarshaller reader = context.createUnmarshaller();
        reader.setSchema(schemas.newSchema(Path.of("student_schema.xsd").toFile()));
        reader.setEventHandler(event -> false);
        Student student = (Student) reader.unmarshal(new StringReader(xml));
        System.out.println("Generated model: " + student.getClass().getName());
        System.out.println("Full name: " + student.getFullName());
        System.out.println("Course: " + student.getCourse().value());
        System.out.println("GPA: " + student.getGpa());
    }
}
