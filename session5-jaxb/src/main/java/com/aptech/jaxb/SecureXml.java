package com.aptech.jaxb;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import jakarta.xml.bind.Unmarshaller;
import javax.xml.XMLConstants;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

public class SecureXml {
    public static Schema schema(Path path) throws Exception {
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        factory.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        factory.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
        return factory.newSchema(path.toFile());
    }
    public static Student read(Unmarshaller reader, Path path) throws Exception {
        if (Files.size(path) > 262144)
            throw new IllegalArgumentException("XML must be at most 262144 bytes in this lab.");
        XMLInputFactory factory = XMLInputFactory.newFactory();
        factory.setProperty(XMLInputFactory.SUPPORT_DTD, false);
        factory.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);
        factory.setXMLResolver((publicId, systemId, base, namespace) -> {
            throw new XMLStreamException("External XML resources are disabled.");
        });
        try (InputStream input = Files.newInputStream(path)) {
            XMLStreamReader xml = factory.createXMLStreamReader(input);
            try {
                while (xml.hasNext() && xml.getEventType() != XMLStreamConstants.START_ELEMENT) {
                    if (xml.getEventType() == XMLStreamConstants.DTD)
                        throw new XMLStreamException("DOCTYPE is not allowed in this exercise.");
                    xml.next();
                }
                Student result = (Student) reader.unmarshal(xml);
                // Read the remainder: extra roots or malformed trailing XML are rejected too.
                while (xml.hasNext()) xml.next();
                return result;
            } finally { xml.close(); }
        }
    }
}
