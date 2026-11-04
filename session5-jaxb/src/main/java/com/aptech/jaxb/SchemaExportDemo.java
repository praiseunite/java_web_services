package com.aptech.jaxb;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.SchemaOutputResolver;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

public class SchemaExportDemo {
    public static void main(String[] args) throws Exception {
        if (args.length > 1) throw new IllegalArgumentException("Supply an optional schema output folder.");
        Path folder = Path.of(args.length == 1 ? args[0] : "target/generated-schema");
        Files.createDirectories(folder);
        JAXBContext context = JAXBContext.newInstance(Student.class);
        List<OutputStream> streams = new ArrayList<>();
        try {
        context.generateSchema(new SchemaOutputResolver() {
            @Override
            public Result createOutput(String namespace, String suggestedName) throws IOException {
                Path file = folder.resolve(suggestedName).toAbsolutePath();
                OutputStream stream = Files.newOutputStream(file);
                streams.add(stream);
                StreamResult output = new StreamResult(stream);
                output.setSystemId(file.toUri().toString());
                System.out.println("Generated schema: " + file);
                return output;
            }
        });
        } finally {
            for (OutputStream stream : streams) stream.close();
        }
    }
}
