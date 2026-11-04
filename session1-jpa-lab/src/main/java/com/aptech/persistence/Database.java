package com.aptech.persistence;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Database {
    public static EntityManagerFactory open() throws IOException {
        Path base = Path.of(System.getProperty("lab.db", "./data/students"))
            .toAbsolutePath().normalize();
        Files.createDirectories(base.getParent());
        String url = "jdbc:h2:file:" + base.toString().replace('\\', '/');
        System.out.println("Database file: " + base + ".mv.db");
        return Persistence.createEntityManagerFactory("school-lab",
            Map.of("jakarta.persistence.jdbc.url", url));
    }
}
