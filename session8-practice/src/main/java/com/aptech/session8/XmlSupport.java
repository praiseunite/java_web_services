package com.aptech.session8;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import jakarta.xml.bind.Unmarshaller;
import javax.xml.XMLConstants;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

// Adapted from the Session 5 parser helper; this reader returns XmlProductInput.
public class XmlSupport {
    private static final int LIMIT = 262144;

    public static Schema schema() throws Exception {
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        factory.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        factory.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
        try (InputStream input = XmlSupport.class.getResourceAsStream("/product-input.xsd")) {
            if (input == null) { throw new IllegalStateException("product-input.xsd resource is missing."); }
            return factory.newSchema(new StreamSource(input));
        }
    }

    public static XmlProductInput read(Unmarshaller reader, Path path) throws Exception {
        try (InputStream input = Files.newInputStream(path)) {
            return read(reader, input.readNBytes(LIMIT + 1));
        }
    }

    public static XmlProductInput read(Unmarshaller reader, byte[] bytes) throws Exception {
        if (bytes.length > LIMIT) { throw new IllegalArgumentException("XML must be at most 262144 bytes."); }
        XMLInputFactory factory = XMLInputFactory.newFactory();
        factory.setProperty(XMLInputFactory.SUPPORT_DTD, false);
        factory.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);
        factory.setXMLResolver((publicId, systemId, base, namespace) -> {
            throw new XMLStreamException("External XML resources are disabled.");
        });
        try (InputStream input = new ByteArrayInputStream(bytes)) {
            XMLStreamReader xml = factory.createXMLStreamReader(input);
            try {
                while (xml.hasNext() && xml.getEventType() != XMLStreamConstants.START_ELEMENT) {
                    if (xml.getEventType() == XMLStreamConstants.DTD) {
                        throw new XMLStreamException("DOCTYPE is not allowed in this exercise.");
                    }
                    xml.next();
                }
                XmlProductInput result = (XmlProductInput) reader.unmarshal(xml);
                while (xml.hasNext()) { xml.next(); }
                return result;
            } finally { xml.close(); }
        }
    }
}
