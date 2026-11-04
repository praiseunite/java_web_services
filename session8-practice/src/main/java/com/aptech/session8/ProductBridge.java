package com.aptech.session8;

import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;

public class ProductBridge {
    public static void main(String[] args) {
        try {
            if (args.length != 2) {
                throw new IllegalArgumentException("Usage: ProductBridge <input.xml> <new-output-folder>");
            }
            Path source = Path.of(args[0]);
            Path output = Path.of(args[1]);
            if (Files.exists(output)) {
                throw new IllegalArgumentException("Output folder already exists. Choose a new folder; earlier evidence is preserved.");
            }
            JAXBContext context = JAXBContext.newInstance(XmlProductInput.class);
            Schema schema = XmlSupport.schema();
            Unmarshaller reader = context.createUnmarshaller();
            reader.setSchema(schema);
            reader.setEventHandler(event -> false);
            XmlProductInput product = ProductRules.normalized(XmlSupport.read(reader, source));

            Marshaller writer = context.createMarshaller();
            writer.setSchema(schema);
            writer.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            StringWriter xml = new StringWriter();
            writer.marshal(product, xml);
            XmlProductInput reread = XmlSupport.read(reader, xml.toString().getBytes(StandardCharsets.UTF_8));
            ProductRules.check(reread);
            boolean sameValues = Objects.equals(product.getName(), reread.getName())
                    && Objects.equals(product.getPriceNgn(), reread.getPriceNgn())
                    && Objects.equals(product.getCategory(), reread.getCategory());
            if (!sameValues) { throw new IllegalStateException("Round-trip changed the product values."); }

            Map<String, Object> body = new LinkedHashMap<>();
            body.put("name", product.getName());
            body.put("priceNgn", product.getPriceNgn());
            body.put("category", product.getCategory());
            String json = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(body);
            String report = "ACCEPTED\nSchema and application rules passed.\nRound-trip values preserved: " + sameValues
                    + "\nSame Java object: " + (product == reread)
                    + "\nPrepared JSON contains no id.\nNo HTTP request was sent.\n";

            // Do not publish an output folder until validation and conversions have succeeded.
            Path parent = output.toAbsolutePath().getParent();
            Files.createDirectories(parent);
            Files.createDirectory(output);
            Files.writeString(output.resolve("normalized.xml"), xml.toString(), StandardCharsets.UTF_8);
            Files.writeString(output.resolve("product.json"), json, StandardCharsets.UTF_8);
            Files.writeString(output.resolve("report.txt"), report, StandardCharsets.UTF_8);
            System.out.print(report);
            System.out.println("Output: " + output.toAbsolutePath());
        } catch (Exception failure) {
            Throwable clue = failure;
            while (clue.getCause() != null) { clue = clue.getCause(); }
            System.err.println("REJECTED: " + (clue.getMessage() == null ? clue.getClass().getSimpleName() : clue.getMessage()));
            System.exit(2);
        }
    }
}
