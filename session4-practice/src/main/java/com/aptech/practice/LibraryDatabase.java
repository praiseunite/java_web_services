package com.aptech.practice;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class LibraryDatabase {
    public static EntityManagerFactory open() throws Exception {
        Path base = Path.of(System.getProperty("practice.db", "./data/library"))
            .toAbsolutePath().normalize();
        Files.createDirectories(base.getParent());
        System.out.println("Library database: " + base + ".mv.db");
        return Persistence.createEntityManagerFactory("library-practice",
            Map.of("jakarta.persistence.jdbc.url", "jdbc:h2:file:" + base.toString().replace('\\', '/')));
    }
}
